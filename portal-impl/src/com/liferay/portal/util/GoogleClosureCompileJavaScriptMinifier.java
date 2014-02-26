/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.google.javascript.jscomp.BasicErrorManager;
import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.DiagnosticGroups;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.MessageFormatter;
import com.google.javascript.jscomp.PropertyRenamingPolicy;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.VariableRenamingPolicy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Carlos Sierra Andrés
 */
public class GoogleClosureCompileJavaScriptMinifier
	implements JavaScriptMinifier {

	@Override
	public String compress(String resource, String content) {
		Compiler compiler = new Compiler(new LogErrorManager(_log));

		SourceFile sourceFile = SourceFile.fromCode(resource, content);

		CompilerOptions compilerOptions = new CompilerOptions();

		compilerOptions.setWarningLevel(
			DiagnosticGroups.NON_STANDARD_JSDOC, CheckLevel.OFF);

		_applySimpleCompileLevelOptions(compilerOptions);

		compiler.compile(
			SourceFile.fromCode(
				"extern", StringPool.BLANK), sourceFile, compilerOptions);

		return compiler.toSource();
	}

	private void _applySimpleCompileLevelOptions(
		CompilerOptions compilerOptions) {

		compilerOptions.closurePass = true;
		compilerOptions.setRenamingPolicy(
			VariableRenamingPolicy.LOCAL, PropertyRenamingPolicy.OFF);
		compilerOptions.setInlineVariables(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.flowSensitiveInlineVariables = true;
		compilerOptions.setInlineFunctions(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.setAssumeClosuresOnlyCaptureReferences(false);
		compilerOptions.checkGlobalThisLevel = CheckLevel.OFF;
		compilerOptions.foldConstants = true;
		compilerOptions.coalesceVariableNames = true;
		compilerOptions.deadAssignmentElimination = true;
		compilerOptions.collapseVariableDeclarations = true;
		compilerOptions.convertToDottedProperties = true;
		compilerOptions.labelRenaming = true;
		compilerOptions.removeDeadCode = true;
		compilerOptions.optimizeArgumentsArray = true;
		compilerOptions.setRemoveUnusedVariables(
			CompilerOptions.Reach.LOCAL_ONLY);
	}

	private static Log _log = LogFactoryUtil.getLog(
		GoogleClosureCompileJavaScriptMinifier.class);

	private static class LogErrorManager extends BasicErrorManager {

		private MessageFormatter _simpleMessageFormatter =
			new SimpleMessageFormatter();

		private LogErrorManager(Log log) {
			_log = log;
		}

		@Override
		public void println(CheckLevel level, JSError error) {
			switch (level) {
				case ERROR:
					if (_log.isErrorEnabled()) {
						_log.error(
							error.format(level, _simpleMessageFormatter));
					}

				case WARNING:
					if (_log.isWarnEnabled()) {
						_log.warn(error.format(level, _simpleMessageFormatter));
					}
			}
		}

		@Override
		protected void printSummary() {
			if ((getErrorCount() > 0) && _log.isErrorEnabled()) {
				_log.error(generateMessage());
			}
			else if ((getWarningCount() > 0) && _log.isWarnEnabled()) {
				_log.warn(generateMessage());
			}
		}

		private String generateMessage() {
			return String.format(
				"{0} error(s), {1} warning(s)",
				getErrorCount(), getWarningCount()
			);
		}

		private static Log _log;

	}

	private static class SimpleMessageFormatter implements MessageFormatter {

		private String _format(JSError error) {
			return String.format(
				"(%s:%d): %s", error.sourceName, error.lineNumber,
				error.description);
		}

		@Override
		public String formatError(JSError error) {
			return _format(error);
		}

		@Override
		public String formatWarning(JSError warning) {
			return _format(warning);
		}
	}

}