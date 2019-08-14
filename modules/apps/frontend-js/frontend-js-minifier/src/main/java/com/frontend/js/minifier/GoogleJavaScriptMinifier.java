/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.frontend.js.minifier;

import com.google.javascript.jscomp.BasicErrorManager;
import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.DiagnosticType;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.MessageFormatter;
import com.google.javascript.jscomp.SourceFile;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.minifier.JavaScriptMinifier;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = "service.ranking:Integer=100", service = JavaScriptMinifier.class
)
public class GoogleJavaScriptMinifier implements JavaScriptMinifier {

	@Override
	public String compress(String resourceName, String content) {
		try {
			Compiler compiler = new Compiler(new LogErrorManager(resourceName));

			compiler.disableThreads();

			SourceFile sourceFile = SourceFile.fromCode(resourceName, content);

			CompilerOptions compilerOptions = new CompilerOptions();

			CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(
				compilerOptions);

			compilerOptions.setEmitUseStrict(false);
			compilerOptions.setLanguageIn(
				CompilerOptions.LanguageMode.ECMASCRIPT_NEXT);

			compiler.compile(
				SourceFile.fromCode("extern", StringPool.BLANK), sourceFile,
				compilerOptions);

			if (compiler.hasErrors()) {
				return content;
			}

			return compiler.toSource();
		}
		finally {
			if (_clearThreadTraceMethod != null) {
				try {
					_clearThreadTraceMethod.invoke(null);
				}
				catch (ReflectiveOperationException roe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to clear thread local for ThreadTrace",
							roe);
					}
				}
			}
		}
	}

	@Activate
	protected void activate() {
		try {
			ClassLoader classLoader = Compiler.class.getClassLoader();

			_clearThreadTraceMethod = ReflectionUtil.getDeclaredMethod(
				classLoader.loadClass("com.google.javascript.jscomp.Tracer"),
				"clearThreadTrace");
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find clear ThreadTrace method", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleJavaScriptMinifier.class);

	private static final Set<String> _ignoredErrors = new HashSet<>(
		Arrays.asList(
			"JSC_BAD_JSDOC_ANNOTATION", "JSC_DUPLICATE_OBJECT_KEY",
			"JSC_GOOG_MODULE_IN_NON_MODULE", "JSC_INVALID_PARAM",
			"JSC_JSDOC_IN_BLOCK_COMMENT", "JSC_JSDOC_MISSING_BRACES_WARNING",
			"JSC_MISPLACED_ANNOTATION"));

	private Method _clearThreadTraceMethod;

	private static class SimpleMessageFormatter implements MessageFormatter {

		@Override
		public String formatError(JSError jsError) {
			DiagnosticType diagnosticType = jsError.getType();

			return String.format(
				"(%s:%d): %s [%s]", jsError.sourceName, jsError.lineNumber,
				jsError.description, diagnosticType.key);
		}

		@Override
		public String formatWarning(JSError jsError) {
			return formatError(jsError);
		}

	}

	private class LogErrorManager extends BasicErrorManager {

		public LogErrorManager(String resourceName) {
			_resourceName = resourceName;
		}

		@Override
		public void println(CheckLevel checkLevel, JSError jsError) {
			DiagnosticType diagnosticType = jsError.getType();

			if (_ignoredErrors.contains(diagnosticType.key)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						jsError.format(checkLevel, _simpleMessageFormatter));
				}

				if (checkLevel == CheckLevel.ERROR) {
					_ignoredErrorCount++;
				}
				else if (checkLevel == CheckLevel.WARNING) {
					_ignoredWarningCount++;
				}
			}
			else {
				if (checkLevel == CheckLevel.ERROR) {
					_log.error(
						jsError.format(checkLevel, _simpleMessageFormatter));
				}
				else if (checkLevel == CheckLevel.WARNING) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							jsError.format(
								checkLevel, _simpleMessageFormatter));
					}
				}
			}
		}

		@Override
		protected void printSummary() {
			int errorCount = getErrorCount() - _ignoredErrorCount;
			int warningCount = getWarningCount() - _ignoredWarningCount;

			if (errorCount > 0) {
				_log.error(_buildMessage(errorCount, warningCount));
			}
			else if (_log.isWarnEnabled() && (warningCount > 0)) {
				_log.warn(_buildMessage(errorCount, warningCount));
			}
		}

		private String _buildMessage(int errorCount, int warningCount) {
			return String.format(
				"(%s): %d error(s), %d warning(s)", _resourceName, errorCount,
				warningCount);
		}

		private int _ignoredErrorCount;
		private int _ignoredWarningCount;
		private final String _resourceName;
		private final MessageFormatter _simpleMessageFormatter =
			new SimpleMessageFormatter();

	}

}