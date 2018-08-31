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

package com.liferay.portal.minifier;

import com.google.javascript.jscomp.BasicErrorManager;
import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.DiagnosticGroup;
import com.google.javascript.jscomp.DiagnosticGroups;
import com.google.javascript.jscomp.DiagnosticType;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.MessageFormatter;
import com.google.javascript.jscomp.PropertyRenamingPolicy;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.VariableRenamingPolicy;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author     Carlos Sierra Andrés
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class GoogleJavaScriptMinifier implements JavaScriptMinifier {

	@Override
	public String compress(String resourceName, String content) {
		Compiler compiler = new Compiler(new LogErrorManager());

		compiler.disableThreads();

		SourceFile sourceFile = SourceFile.fromCode(resourceName, content);

		CompilerOptions compilerOptions = new CompilerOptions();

		compilerOptions.setLanguageIn(CompilerOptions.LanguageMode.ECMASCRIPT5);
		compilerOptions.setWarningLevel(
			DiagnosticGroups.NON_STANDARD_JSDOC, CheckLevel.OFF);
		compilerOptions.setWarningLevel(
			DiagnosticGroup.forType(
				DiagnosticType.error("JSC_MISSING_PROVIDE_ERROR", "")),
			CheckLevel.OFF);
		compilerOptions.setWarningLevel(
			DiagnosticGroup.forType(
				DiagnosticType.error("JSC_NON_GLOBAL_DEFINE_INIT_ERROR", "")),
			CheckLevel.OFF);

		setCompileOptions(compilerOptions);

		compiler.compile(
			SourceFile.fromCode("extern", StringPool.BLANK), sourceFile,
			compilerOptions);

		return compiler.toSource();
	}

	protected void setCompileOptions(CompilerOptions compilerOptions) {
		compilerOptions.checkGlobalThisLevel = CheckLevel.OFF;
		compilerOptions.closurePass = true;
		compilerOptions.coalesceVariableNames = true;
		compilerOptions.collapseVariableDeclarations = true;
		compilerOptions.convertToDottedProperties = true;
		compilerOptions.deadAssignmentElimination = true;
		compilerOptions.flowSensitiveInlineVariables = true;
		compilerOptions.foldConstants = true;
		compilerOptions.labelRenaming = true;
		compilerOptions.removeDeadCode = true;
		compilerOptions.optimizeArgumentsArray = true;
		compilerOptions.setAssumeClosuresOnlyCaptureReferences(false);
		compilerOptions.setInlineFunctions(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.setInlineVariables(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.setRenamingPolicy(
			VariableRenamingPolicy.LOCAL, PropertyRenamingPolicy.OFF);
		compilerOptions.setRemoveUnusedVariables(
			CompilerOptions.Reach.LOCAL_ONLY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleJavaScriptMinifier.class);

	private static class SimpleMessageFormatter implements MessageFormatter {

		@Override
		public String formatError(JSError jsError) {
			return String.format(
				"(%s:%d): %s", jsError.sourceName, jsError.lineNumber,
				jsError.description);
		}

		@Override
		public String formatWarning(JSError jsError) {
			return formatError(jsError);
		}

	}

	private class LogErrorManager extends BasicErrorManager {

		@Override
		public void println(CheckLevel checkLevel, JSError jsError) {
			if (checkLevel == CheckLevel.ERROR) {
				_log.error(jsError.format(checkLevel, _simpleMessageFormatter));
			}
			else if (checkLevel == CheckLevel.WARNING) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						jsError.format(checkLevel, _simpleMessageFormatter));
				}
			}
		}

		@Override
		protected void printSummary() {
			if (getErrorCount() > 0) {
				_log.error(_buildMessage());
			}
			else if (_log.isWarnEnabled() && (getWarningCount() > 0)) {
				_log.warn(_buildMessage());
			}
		}

		private String _buildMessage() {
			return String.format(
				"{0} error(s), {1} warning(s)", getErrorCount(),
				getWarningCount());
		}

		private final MessageFormatter _simpleMessageFormatter =
			new SimpleMessageFormatter();

	}

}