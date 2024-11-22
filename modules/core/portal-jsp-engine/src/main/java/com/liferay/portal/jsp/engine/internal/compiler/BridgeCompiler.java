/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.jsp.engine.internal.compiler;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.compiler.SmapStratum;
import org.apache.jasper.servlet.JspServletWrapper;

/**
 * @author Shuyang Zhou
 */
public class BridgeCompiler extends Compiler {

	public static void init(Supplier<Compiler> supplier) {
		_supplier = supplier;
	}

	public BridgeCompiler() {
		Supplier<Compiler> supplier = _supplier;

		if (supplier == null) {
			throw new IllegalStateException(
				"Compiler supplier is not initialized");
		}

		_compiler = supplier.get();
	}

	@Override
	public void compile(boolean compileClass) throws Exception {
		_compiler.compile(compileClass);
	}

	@Override
	public void init(
		JspCompilationContext jspCompilationContext,
		JspServletWrapper jspServletWrapper) {

		_compiler.init(jspCompilationContext, jspServletWrapper);
	}

	@Override
	public boolean isOutDated() {
		return _compiler.isOutDated();
	}

	@Override
	public void removeGeneratedFiles() {
		_compiler.removeGeneratedFiles();
	}

	@Override
	protected void generateClass(Map<String, SmapStratum> smaps)
		throws Exception {

		_generateClassMethod.invoke(_compiler, smaps);
	}

	private static final Method _generateClassMethod;
	private static volatile Supplier<Compiler> _supplier;

	static {
		try {
			_generateClassMethod = Compiler.class.getDeclaredMethod(
				"generateClass", Map.class);

			_generateClassMethod.setAccessible(true);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new ExceptionInInitializerError(reflectiveOperationException);
		}
	}

	private final Compiler _compiler;

}