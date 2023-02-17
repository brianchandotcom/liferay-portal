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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaConstructorParametersCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaSignature signature = javaTerm.getSignature();

		List<JavaParameter> parameters = signature.getParameters();

		if (!parameters.isEmpty()) {
			_checkConstructorParameterOrder(fileName, javaTerm, parameters);

			String content = _sortAssignCalls(
				javaTerm.getContent(), parameters);

			content = _fixIncorrectEmptyLines(
				content, _missingLineBreakPattern1, parameters);
			content = _fixIncorrectEmptyLines(
				content, _missingLineBreakPattern2, parameters);

			return _checkPassedInVar(content, parameters, fileContent);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR};
	}

	private void _checkConstructorParameterOrder(
		String fileName, JavaTerm javaTerm, List<JavaParameter> parameters) {

		String previousGlobalVariableName = null;
		String previousParameterName = null;
		int previousPos = -1;

		for (JavaParameter parameter : parameters) {
			String parameterName = parameter.getParameterName();

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\\{\n([\\s\\S]*?)((_|this\\.)", parameterName,
					") =[ \t\n]+", parameterName, ";"));

			Matcher matcher = pattern.matcher(javaTerm.getContent());

			if (!matcher.find()) {
				continue;
			}

			String beforeParameter = matcher.group(1);

			if (beforeParameter.contains(parameterName + " =")) {
				continue;
			}

			int pos = matcher.start(2);

			if ((previousPos > pos) &&
				previousGlobalVariableName.startsWith(matcher.group(3))) {

				addMessage(
					fileName,
					StringBundler.concat(
						"'", previousGlobalVariableName, " = ",
						previousParameterName, ";' should come before '",
						matcher.group(2), " = ", parameterName,
						";' to match order of constructor parameters"),
					javaTerm.getLineNumber(previousPos));

				return;
			}

			previousGlobalVariableName = matcher.group(2);
			previousParameterName = parameterName;
			previousPos = pos;
		}
	}

	private ReturnResult _checkMethod(
		String statementBlock, String globalVariableName,
		String parameterName) {

		return _checkMethod(
			statementBlock, globalVariableName, parameterName, false);
	}

	private ReturnResult _checkMethod(
		String statementBlock, String globalVariableName, String parameterName,
		boolean inGetMethod) {

		if (Validator.isNull(statementBlock)) {
			return ReturnResult.continueReturnResult(statementBlock);
		}

		Matcher matcher1 = _methodCallPattern.matcher(statementBlock);

		if (!matcher1.find()) {
			if (StringUtil.equals(globalVariableName, statementBlock) ||
				StringUtil.equals(parameterName, statementBlock)) {

				if (inGetMethod) {
					return ReturnResult.continueReturnResult(parameterName);
				}

				return ReturnResult.finishReturnResult(statementBlock);
			}

			Pattern pattern2 = Pattern.compile(
				StringBundler.concat(
					"\\b(", globalVariableName, "|", parameterName, ")\\b"));

			Matcher matcher2 = pattern2.matcher(statementBlock);

			if (matcher2.find()) {
				return ReturnResult.continueReturnResult(parameterName);
			}
		}

		matcher1.reset();

		while (matcher1.find()) {
			String methodName = matcher1.group(3);

			String methodCall = JavaSourceUtil.getMethodCall(
				statementBlock, matcher1.start());

			List<String> parameters = JavaSourceUtil.getParameterList(
				methodCall);

			inGetMethod =
				methodName.startsWith("get") || methodName.startsWith("_get") ||
				methodCall.startsWith("StringBundler.concat(");

			String newMethodCall = methodCall;

			for (String parameter : parameters) {
				ReturnResult returnResult = _checkMethod(
					parameter, globalVariableName, parameterName, inGetMethod);

				if (returnResult.getFinishFlg()) {
					return ReturnResult.finishReturnResult(newMethodCall);
				}

				String newParameter = returnResult.getNewParameter();

				if (!StringUtil.equals(parameter, newParameter)) {
					newMethodCall = StringUtil.replace(
						newMethodCall, parameter, newParameter);
				}
			}

			if (!StringUtil.equals(methodCall, newMethodCall)) {
				return ReturnResult.continueReturnResult(
					StringUtil.replace(
						statementBlock, methodCall, newMethodCall,
						matcher1.start()));
			}

			String methodCallName = matcher1.group(2);

			if (Validator.isNull(methodCallName) ||
				!(StringUtil.equals(globalVariableName, methodCallName) ||
				  StringUtil.equals(parameterName, methodCallName))) {

				continue;
			}

			if (methodName.startsWith("get")) {
				if (StringUtil.equals(globalVariableName, methodCallName)) {
					return ReturnResult.continueReturnResult(
						StringUtil.replaceFirst(
							statementBlock, globalVariableName, parameterName,
							matcher1.start()));
				}
			}
			else {
				return ReturnResult.finishReturnResult(statementBlock);
			}
		}

		return ReturnResult.continueReturnResult(statementBlock);
	}

	private String _checkNextStatementBlock(
		String content, String indent, int pos, String globalVariableName,
		String parameterName) {

		int curLineNumber = getLineNumber(content, pos);
		String[] lines = StringUtil.splitLines(content);

		int startPos = -1;

		for (int i = curLineNumber; i < (lines.length - 1); i++) {
			String line = lines[i];

			if (Validator.isNull(line)) {
				continue;
			}

			if ((startPos == -1) && indent.equals(SourceUtil.getIndent(line))) {
				startPos = getLineStartPos(content, i + 1);
			}

			String nextLine = lines[i + 1];

			int endPos = -1;

			if (indent.equals(SourceUtil.getIndent(nextLine)) ||
				((indent.length() > 1) &&
				 nextLine.equals(indent.substring(1) + "}")) ||
				Validator.isNull(nextLine)) {

				endPos = getLineStartPos(content, i + 1) + line.length();
			}

			if ((startPos == -1) || (endPos == -1)) {
				continue;
			}

			String nextAdjacentStatementBlock = StringUtil.trim(
				content.substring(startPos, endPos));

			if (nextAdjacentStatementBlock.matches(
					StringBundler.concat(
						globalVariableName, "|", parameterName,
						" =[\\s\\S]+;"))) {

				continue;
			}

			ReturnResult returnResult = _checkMethod(
				nextAdjacentStatementBlock, globalVariableName, parameterName);

			String newStatementBlock = returnResult.getNewParameter();

			if (!StringUtil.equals(
					nextAdjacentStatementBlock, newStatementBlock)) {

				return StringUtil.replaceFirst(
					content, nextAdjacentStatementBlock, newStatementBlock,
					startPos);
			}

			if (returnResult.getFinishFlg()) {
				break;
			}

			startPos = -1;
		}

		return content;
	}

	private String _checkPassedInVar(
		String content, List<JavaParameter> parameters, String fileContent) {

		for (JavaParameter parameter : parameters) {
			String parameterName = parameter.getParameterName();

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\\{\n([\\s\\S]*?)((_|this\\.)", parameterName,
					") =[ \t\n]+", parameterName, ";"));

			Matcher matcher = pattern.matcher(content);

			if (!matcher.find()) {
				continue;
			}

			String globalVariableName = matcher.group(2);

			if (StringUtil.equals(matcher.group(3), "this.")) {
				globalVariableName = globalVariableName.substring(5);
			}

			String globalVariableTypeName = getVariableTypeName(
				content, fileContent, globalVariableName);
			String parameterTypeName = parameter.getParameterType();

			if (!StringUtil.equals(parameterTypeName, globalVariableTypeName)) {
				continue;
			}

			String line = getLine(
				content, getLineNumber(content, matcher.start(2)));

			String newContent = _checkNextStatementBlock(
				content, SourceUtil.getIndent(line), matcher.end(),
				globalVariableName, parameterName);

			if (!StringUtil.equals(content, newContent)) {
				return newContent;
			}
		}

		return content;
	}

	private boolean _containsParameterName(
		List<JavaParameter> parameters, String name) {

		for (JavaParameter parameter : parameters) {
			if (name.equals(parameter.getParameterName())) {
				return true;
			}
		}

		return false;
	}

	private String _fixIncorrectEmptyLines(
		String content, Pattern pattern, List<JavaParameter> parameters) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String name1 = matcher.group(3);
			String name2 = matcher.group(5);

			if (!_containsParameterName(parameters, name1) ||
				!_containsParameterName(parameters, name2)) {

				continue;
			}

			String previousStatementsBlock = _getPreviousStatementsBlock(
				content, matcher.group(1), matcher.start() + 1);

			if (previousStatementsBlock.matches(
					StringBundler.concat(
						"(?s).*\\W(", matcher.group(2), ")?", name1,
						"\\W.*"))) {

				continue;
			}

			String nextStatementsBlock = _getNextStatementsBlock(
				content, matcher.group(1), matcher.start(6));

			if (Validator.isNull(nextStatementsBlock) ||
				!nextStatementsBlock.matches(
					StringBundler.concat(
						"(?s).*\\W(", matcher.group(2), ")?", name2,
						"\\W.*"))) {

				return StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start(4));
			}
		}

		return content;
	}

	private int _getIndex(
		String name, String value, List<JavaParameter> parameters) {

		for (int i = 0; i < parameters.size(); i++) {
			JavaParameter parameter = parameters.get(i);

			String parameterName = parameter.getParameterName();

			if (name.equals(parameterName)) {
				if (value.matches("(?s).*\\W" + parameterName + "\\W.*")) {
					return i;
				}

				return parameters.size();
			}
		}

		return parameters.size();
	}

	private String _getNextStatementsBlock(
		String content, String indent, int pos) {

		int x = pos;

		while (true) {
			x = content.indexOf("\n\n", x + 1);

			if (x == -1) {
				return StringPool.BLANK;
			}

			String nextLine = getLine(content, getLineNumber(content, x + 2));

			if (indent.equals(SourceUtil.getIndent(nextLine))) {
				break;
			}
		}

		int y = x;

		while (true) {
			y = content.indexOf("\n\n", y + 1);

			if (y == -1) {
				return content.substring(x);
			}

			String nextLine = getLine(content, getLineNumber(content, y + 2));

			if (indent.equals(SourceUtil.getIndent(nextLine))) {
				break;
			}
		}

		return content.substring(x, y);
	}

	private String _getPreviousStatementsBlock(
		String content, String indent, int pos) {

		int x = pos;

		while (true) {
			x = content.lastIndexOf("\n\n", x - 1);

			if (x == -1) {
				return StringPool.BLANK;
			}

			String nextLine = getLine(content, getLineNumber(content, x + 2));

			if (indent.equals(SourceUtil.getIndent(nextLine))) {
				break;
			}
		}

		int y = x;

		while (true) {
			y = content.lastIndexOf("\n\n", y - 1);

			if (y == -1) {
				int z = content.indexOf("{\n");

				if (z == -1) {
					return StringPool.BLANK;
				}

				return content.substring(z + 1, x);
			}

			String nextLine = getLine(content, getLineNumber(content, y + 2));

			if (indent.equals(SourceUtil.getIndent(nextLine))) {
				break;
			}
		}

		return content.substring(y, x);
	}

	private String _sortAssignCalls(
		String content, List<JavaParameter> parameters) {

		String firstFollowingStatement = null;

		Matcher assignCallMatcher = _assignCallPattern.matcher(content);

		while (assignCallMatcher.find()) {
			Pattern nextCallPattern = Pattern.compile(
				"^\t+" + assignCallMatcher.group(1) + "(\\w+) (=[^;]+;)\\n");

			String followingCode = content.substring(assignCallMatcher.end());

			Matcher nextCallMatcher = nextCallPattern.matcher(followingCode);

			if (!nextCallMatcher.find()) {
				continue;
			}

			int index1 = _getIndex(
				assignCallMatcher.group(2), assignCallMatcher.group(3),
				parameters);
			int index2 = _getIndex(
				nextCallMatcher.group(1), nextCallMatcher.group(2), parameters);

			if (index1 > index2) {
				String assignment1 = StringUtil.trim(assignCallMatcher.group());
				String assignment2 = StringUtil.trim(nextCallMatcher.group());

				content = StringUtil.replaceFirst(
					content, assignment2, assignment1,
					assignCallMatcher.start());

				content = StringUtil.replaceFirst(
					content, assignment1, assignment2,
					assignCallMatcher.start());

				return _sortAssignCalls(content, parameters);
			}

			if ((index1 != index2) && (index2 == parameters.size())) {
				firstFollowingStatement = nextCallMatcher.group();
			}
		}

		if (firstFollowingStatement != null) {
			return StringUtil.replaceFirst(
				content, firstFollowingStatement,
				"\n" + firstFollowingStatement);
		}

		return content;
	}

	private static final Pattern _assignCallPattern = Pattern.compile(
		"\t(_|this\\.)(\\w+) (=[^;]+;)\n");
	private static final Pattern _methodCallPattern = Pattern.compile(
		"((_?\\w+)\\.\\n?\\s*?)?(_?\\w+)\\(");
	private static final Pattern _missingLineBreakPattern1 = Pattern.compile(
		"\n(\t+)(_)(\\w+) =[ \t\n]+\\3;(?=(\n\n)\\1_(\\w+) =[ \t\n]+\\5(;)\n)");
	private static final Pattern _missingLineBreakPattern2 = Pattern.compile(
		"\n(\t+)(this\\.)(\\w+) =[ \t\n]+\\3;(?=(\n\n)\\1this\\.(\\w+) " +
			"=[ \t\n]+\\5(;)\n)");

	private static class ReturnResult {

		public static ReturnResult continueReturnResult(String parameter) {
			ReturnResult returnResult = new ReturnResult();

			returnResult.setNewParameter(parameter);

			return returnResult;
		}

		public static ReturnResult finishReturnResult(String parameter) {
			ReturnResult returnResult = new ReturnResult();

			returnResult.setFinishFlg(true);
			returnResult.setNewParameter(parameter);

			return returnResult;
		}

		public ReturnResult() {
			_finishFlg = false;
		}

		public boolean getFinishFlg() {
			return _finishFlg;
		}

		public String getNewParameter() {
			return _newParameter;
		}

		public void setFinishFlg(boolean finishFlg) {
			_finishFlg = finishFlg;
		}

		public void setNewParameter(String newParameter) {
			_newParameter = newParameter;
		}

		private boolean _finishFlg;
		private String _newParameter;

	}

}