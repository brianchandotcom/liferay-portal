/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Jorge Garcia Jimenez
 */
public class MethodParameterClassVisitor extends ClassVisitor {

	public List<MethodParameter> getMethodParameters() {
		if (_methodParameterMethodVisitor != null) {
			return _methodParameterMethodVisitor.getMethodParameters();
		}

		return Collections.emptyList();
	}

	@Override
	public MethodVisitor visitMethod(
		int access, String name, String descriptor, String signature,
		String[] exceptions) {

		if (!name.equals(_method.getName()) ||
			!Objects.equals(Type.getMethodDescriptor(_method), descriptor)) {

			return null;
		}

		int parameterCount = _method.getParameterCount();

		if (!Modifier.isStatic(_method.getModifiers())) {
			parameterCount++;
		}

		Class<?>[] parameterTypes = _method.getParameterTypes();

		for (Class<?> parameterType : parameterTypes) {
			if (StringUtil.equalsIgnoreCase(
					parameterType.getName(), double.class.getName()) ||
				StringUtil.equalsIgnoreCase(
					parameterType.getName(), long.class.getName())) {

				parameterCount++;
			}
		}

		_methodParameterMethodVisitor = new MethodParameterMethodVisitor(
			_classLoader, _method, parameterCount);

		return _methodParameterMethodVisitor;
	}

	protected MethodParameterClassVisitor(
		ClassLoader classLoader, Method method) {

		super(Opcodes.ASM9);

		_classLoader = classLoader;
		_method = method;
	}

	private final ClassLoader _classLoader;
	private final Method _method;
	private MethodParameterMethodVisitor _methodParameterMethodVisitor;

}