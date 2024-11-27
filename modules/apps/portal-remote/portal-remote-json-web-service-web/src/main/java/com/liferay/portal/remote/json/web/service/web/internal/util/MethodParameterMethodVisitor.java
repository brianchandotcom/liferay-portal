/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.List;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Jorge Garcia Jimenez
 */
public class MethodParameterMethodVisitor
	extends MethodVisitor {

	public List<ExtractedParameter> getExtractedParameterList() {
		return _extractedParameterList;
	}

	@Override
	public void visitLocalVariable(
		String name, String descriptor, String signature, Label start,
		Label end, int index) {

		if (!Modifier.isStatic(_method.getModifiers()) && (index == 0)) {
			return;
		}

		int count = 0;

		for (Class<?> parameterType : _method.getParameterTypes()) {
			if (parameterType.getName(
				).equalsIgnoreCase(
					long.class.getName()
				) ||
				parameterType.getName(
				).equalsIgnoreCase(
					double.class.getName()
				)) {

				count++;
			}
		}

		if (index <
				(_method.getParameterCount() +
					(Modifier.isStatic(_method.getModifiers()) ? 0 : 1) +
						count)) {

			if (signature != null) {
				String parameterSignature = StringUtil.replace(signature,descriptor,
					StringPool.BLANK);

				_extractedParameterList.add(
					new ExtractedParameter(name, parameterSignature));
			}
			else {
				_extractedParameterList.add(
					new ExtractedParameter(name, descriptor));
			}
		}
	}

	protected MethodParameterMethodVisitor(Method method) {
		super(Opcodes.ASM9);

		_method = method;
	}

	private final List<ExtractedParameter> _extractedParameterList =
		new ArrayList<>();
	private final Method _method;

}