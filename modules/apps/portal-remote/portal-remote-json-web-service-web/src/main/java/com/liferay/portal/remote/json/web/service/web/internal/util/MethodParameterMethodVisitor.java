/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Jorge Garcia Jimenez
 */
public class MethodParameterMethodVisitor extends MethodVisitor {

	public List<MethodParameter> getExtractedParameterList() {
		return _methodParameterList;
	}

	@Override
	public void visitLocalVariable(
		String name, String descriptor, String signature, Label start,
		Label end, int index) {

		if (!Modifier.isStatic(_method.getModifiers()) && (index == 0)) {
			return;
		}

		Class<?>[] parameterTypes = _method.getParameterTypes();

		int count = 0;

		for (Class<?> parameterType : parameterTypes) {
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

			Class<?> parameterType =
				parameterTypes[_methodParameterList.size()];

			if (signature != null) {
				String parameterSignature = StringUtil.removeSubstring(
					signature, descriptor);

				_methodParameterList.add(
					new MethodParameter(
						_classLoader, name, parameterSignature, parameterType));
			}
			else {
				_methodParameterList.add(
					new MethodParameter(
						_classLoader, name, descriptor, parameterType));
			}
		}
	}

	protected MethodParameterMethodVisitor(
		ClassLoader classLoader, Method method) {

		super(Opcodes.ASM9);

		_classLoader = classLoader;
		_method = method;
	}

	private final ClassLoader _classLoader;
	private final Method _method;
	private final List<MethodParameter> _methodParameterList =
		new ArrayList<>();

}