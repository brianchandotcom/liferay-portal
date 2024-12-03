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

	public List<MethodParameter> getMethodParameters() {
		return _methodParameters;
	}

	@Override
	public void visitLocalVariable(
		String name, String descriptor, String signature, Label start,
		Label end, int index) {

		if ((!Modifier.isStatic(_method.getModifiers()) && (index == 0)) ||
			(index >= _parameterCount)) {

			return;
		}

		if (signature != null) {
			descriptor = StringUtil.removeSubstring(signature, descriptor);
		}

		Class<?>[] parameterTypes = _method.getParameterTypes();

		_methodParameters.add(
			new MethodParameter(
				_classLoader, name, descriptor,
				parameterTypes[_methodParameters.size()]));
	}

	protected MethodParameterMethodVisitor(
		ClassLoader classLoader, Method method, int parameterCount) {

		super(Opcodes.ASM9);

		_classLoader = classLoader;
		_method = method;
		_parameterCount = parameterCount;
	}

	private final ClassLoader _classLoader;
	private final Method _method;
	private final List<MethodParameter> _methodParameters = new ArrayList<>();
	private final int _parameterCount;

}