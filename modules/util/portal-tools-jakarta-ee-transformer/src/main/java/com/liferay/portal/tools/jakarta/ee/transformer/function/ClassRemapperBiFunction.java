/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.jakarta.ee.transformer.function;

import com.liferay.portal.tools.jakarta.ee.transformer.TransformerAgent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/**
 * @author Shuyang Zhou
 */
public class ClassRemapperBiFunction
	implements BiFunction<String, byte[], byte[]> {

	public static final BiFunction<String, byte[], byte[]> INSTANCE =
		new ClassRemapperBiFunction();

	@Override
	public byte[] apply(String invoker, byte[] classData) {
		Set<String> messages = new HashSet<>();

		ClassWriter classWriter = new ClassWriter(0);

		ClassVisitor classVisitor = new ClassRemapper(
			classWriter,
			new Remapper() {

				@Override
				public String map(String name) {
					String newName = TransformerAgent.replace(
						TransformerAgent.replacementSlashMap, name);

					if (!Objects.equals(name, newName)) {
						messages.add(name.concat(" -> " + newName));
					}

					return newName;
				}

				@Override
				public Object mapValue(Object value) {
					value = super.mapValue(value);

					if (value instanceof String) {
						String name = (String)value;

						String newName = TransformerAgent.replace(
							TransformerAgent.replacementDashDotMap, name);

						if (!Objects.equals(name, newName)) {
							messages.add(name.concat(" -> " + newName));
						}

						value = newName;
					}

					return value;
				}

			});

		ClassReader classReader = new ClassReader(classData);

		classReader.accept(classVisitor, 0);

		if (!messages.isEmpty()) {
			classData = classWriter.toByteArray();

			if (!_JAKARTA_EE_TRANSFORMER_CLASS_REMAPPER_LOGGING_DISABLED) {
				System.err.println(
					"JakartaEETransformer#ClassRemapper#" + invoker + "#" +
						messages);
			}
		}

		return classData;
	}

	private static final boolean
		_JAKARTA_EE_TRANSFORMER_CLASS_REMAPPER_LOGGING_DISABLED =
			Boolean.getBoolean(
				"jakarta.ee.transformer.class.remapper.logging.disabled");

}