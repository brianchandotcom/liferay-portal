/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.portal.kernel.util.MethodParameter;

import java.lang.reflect.Method;

import java.util.ArrayList;
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

	public List<MethodParameter> getExtractedParameters() {
		if (_methodVisitor != null) {
			return _methodVisitor.getExtractedParameterList();
		}

		return new ArrayList<>();
	}

	@Override
	public MethodVisitor visitMethod(
		int access, String name, String descriptor, String signature,
		String[] exceptions) {

		if (!name.equals(_method.getName()) ||
			!Objects.equals(Type.getMethodDescriptor(_method), descriptor)) {

			return null;
		}

		_methodVisitor = new MethodParameterMethodVisitor(
			_classLoader, _method);

		return _methodVisitor;
	}

	protected MethodParameterClassVisitor(
		ClassLoader classLoader, Method method) {

		super(Opcodes.ASM9);

		_classLoader = classLoader;
		_method = method;

		_methodVisitor = null;
	}

	private final ClassLoader _classLoader;
	private final Method _method;
	private MethodParameterMethodVisitor _methodVisitor;

}