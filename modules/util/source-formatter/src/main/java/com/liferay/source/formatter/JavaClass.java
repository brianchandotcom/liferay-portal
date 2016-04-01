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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaMethod;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaClass {

	public JavaClass(
			String name, String packagePath, File file, String fileName,
			String absolutePath, String content, String classContent,
			int lineCount, String indent, JavaClass outerClass,
			List<String> javaTermAccessLevelModifierExcludes,
			JavaSourceProcessor javaSourceProcessor)
		throws Exception {

		_name = name;
		_packagePath = packagePath;
		_file = file;
		_fileName = fileName;
		_absolutePath = absolutePath;
		_content = content;
		_classContent = classContent;
		_lineCount = lineCount;
		_indent = indent;
		_outerClass = outerClass;
		_javaTermAccessLevelModifierExcludes =
			javaTermAccessLevelModifierExcludes;
		_javaSourceProcessor = javaSourceProcessor;
	}

	public String formatJavaTerms(
			Set<String> annotationsExclusions, Set<String> immutableFieldTypes,
			List<String> checkJavaFieldTypesExcludes,
			List<String> javaTermSortExcludes,
			List<String> testAnnotationsExcludes)
		throws Exception {

		Set<JavaTerm> javaTerms = Collections.emptySet();

		try {
			javaTerms = getJavaTerms();
		}
		catch (InvalidJavaTermException ijte) {
			if (!_javaSourceProcessor.isExcludedPath(
					_javaTermAccessLevelModifierExcludes, _absolutePath) &&
				!_javaSourceProcessor.isExcludedPath(
					javaTermSortExcludes, _absolutePath)) {

				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Parsing error around line " + ijte.getLineCount() + ": " +
						_fileName);
			}

			return _classContent;
		}

		if (javaTerms.isEmpty()) {
			return _classContent;
		}

		String originalContent = _classContent;

		JavaTerm previousJavaTerm = null;

		Iterator<JavaTerm> itr = javaTerms.iterator();

		while (itr.hasNext()) {
			JavaTerm javaTerm = itr.next();

			if (javaTerm.isConstructor()) {
				checkConstructor(javaTerm);
			}

			checkUnusedParameters(javaTerm);

			_formatReturnStatements(javaTerm);

			if (javaTerm.isMethod() || javaTerm.isConstructor()) {
				checkChaining(javaTerm);
				checkLineBreak(javaTerm);
			}

			if (_fileName.endsWith("LocalServiceImpl.java") &&
				javaTerm.hasAnnotation("Indexable") &&
				!javaTerm.hasReturnType()) {

				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Missing return type for method with @Indexable: " +
						_fileName + " " + javaTerm.getLineCount());
			}

			if (!_javaSourceProcessor.isExcludedPath(
					checkJavaFieldTypesExcludes, _absolutePath)) {

				checkJavaFieldType(
					javaTerms, javaTerm, annotationsExclusions,
					immutableFieldTypes);
			}

			if (!originalContent.equals(_classContent)) {
				return _classContent;
			}

			sortJavaTerms(previousJavaTerm, javaTerm, javaTermSortExcludes);
			fixTabsAndIncorrectEmptyLines(javaTerm);
			formatAnnotations(javaTerm, testAnnotationsExcludes);

			if (!originalContent.equals(_classContent)) {
				return _classContent;
			}

			previousJavaTerm = javaTerm;
		}

		for (JavaClass innerClass : _innerClasses) {
			String innerClassContent = innerClass.getContent();

			String newInnerClassContent = innerClass.formatJavaTerms(
				annotationsExclusions, immutableFieldTypes,
				checkJavaFieldTypesExcludes, javaTermSortExcludes,
				testAnnotationsExcludes);

			if (!innerClassContent.equals(newInnerClassContent)) {
				_classContent = StringUtil.replace(
					_classContent, innerClassContent, newInnerClassContent);

				return _classContent;
			}
		}

		fixJavaTermsDividers(javaTerms, javaTermSortExcludes);

		return _classContent;
	}

	public String getClassName() {
		if (_outerClass != null) {
			return _outerClass.getClassName() + StringPool.DOLLAR + _name;
		}

		return _packagePath + StringPool.PERIOD + _name;
	}

	public String getContent() {
		return _classContent;
	}

	protected Set<JavaTerm> addStaticBlocks(
		Set<JavaTerm> javaTerms, List<JavaTerm> staticBlocks) {

		Set<JavaTerm> newJavaTerms = new TreeSet<>(new JavaTermComparator());

		Iterator<JavaTerm> javaTermsIterator = javaTerms.iterator();

		while (javaTermsIterator.hasNext()) {
			JavaTerm javaTerm = javaTermsIterator.next();

			if (!javaTerm.isStatic() || !javaTerm.isVariable()) {
				newJavaTerms.add(javaTerm);

				continue;
			}

			Iterator<JavaTerm> staticBlocksIterator = staticBlocks.iterator();

			while (staticBlocksIterator.hasNext()) {
				JavaTerm staticBlock = staticBlocksIterator.next();

				String staticBlockContent = staticBlock.getContent();

				if (staticBlockContent.contains(javaTerm.getName())) {
					staticBlock.setType(javaTerm.getType() + 1);

					newJavaTerms.add(staticBlock);

					staticBlocksIterator.remove();
				}
			}

			newJavaTerms.add(javaTerm);
		}

		if (!staticBlocks.isEmpty()) {
			newJavaTerms.addAll(staticBlocks);
		}

		return newJavaTerms;
	}

	protected void checkAnnotationForMethod(
		JavaTerm javaTerm, String annotation, String requiredMethodNameRegex,
		int requiredMethodType, String fileName) {

		String methodName = javaTerm.getName();

		Pattern pattern = Pattern.compile(requiredMethodNameRegex);

		Matcher matcher = pattern.matcher(methodName);

		if (javaTerm.hasAnnotation(annotation)) {
			if (!matcher.find()) {
				_javaSourceProcessor.processErrorMessage(
					fileName,
					"LPS-36303: Incorrect method name: " + methodName + " " +
						fileName);
			}
			else if (javaTerm.getType() != requiredMethodType) {
				_javaSourceProcessor.processErrorMessage(
					fileName,
					"LPS-36303: Incorrect method type for " + methodName + " " +
						fileName);
			}
		}
		else if (matcher.find() && !javaTerm.hasAnnotation("Override")) {
			_javaSourceProcessor.processErrorMessage(
				fileName,
				"Annotation @" + annotation + " required for " + methodName +
					" " + fileName);
		}
	}

	protected void checkChaining(JavaTerm javaTerm) {
		Matcher matcher = _chainingPattern.matcher(javaTerm.getContent());

		while (matcher.find()) {
			int lineCount =
				javaTerm.getLineCount() +
					_javaSourceProcessor.getLineCount(
						javaTerm.getContent(), matcher.end()) - 1;

			_javaSourceProcessor.processErrorMessage(
				_fileName, "chaining: " + _fileName + " " + lineCount);
		}
	}

	protected void checkConstructor(JavaTerm javaTerm) throws Exception {
		String javaTermContent = javaTerm.getContent();

		if (javaTermContent.contains(StringPool.TAB + "super();")) {
			String newJavaTermContent = StringUtil.replace(
				javaTermContent, StringPool.TAB + "super();", StringPool.BLANK);

			_classContent = StringUtil.replace(
				_classContent, javaTermContent, newJavaTermContent);

			return;
		}

		if (!ListUtil.isEmpty(javaTerm.getParameterTypes())) {
			checkConstructorParameterOrder(javaTerm);

			return;
		}

		if ((_packagePath == null) || (_constructorCount > 1) ||
			!javaTermContent.contains("{\n" + _indent + "}\n")) {

			return;
		}

		String accessModifier = getAccessModifier();

		if ((javaTerm.isPrivate() &&
			 !accessModifier.equals(_ACCESS_MODIFIER_PRIVATE)) ||
			(javaTerm.isProtected() &&
			 !accessModifier.equals(_ACCESS_MODIFIER_PRIVATE) &&
			 !accessModifier.equals(_ACCESS_MODIFIER_PROTECTED))) {

			return;
		}

		Pattern pattern = Pattern.compile("class " + _name + "[ \t\n]+extends");

		Matcher matcher = pattern.matcher(_classContent);

		if (!matcher.find()) {
			return;
		}

		JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		javaDocBuilder.addSource(_file);

		com.thoughtworks.qdox.model.JavaClass javaClass =
			javaDocBuilder.getClassByName(getClassName());

		com.thoughtworks.qdox.model.JavaClass superJavaClass =
			javaClass.getSuperJavaClass();

		JavaMethod superJavaClassConstructor =
			superJavaClass.getMethodBySignature(superJavaClass.getName(), null);

		if ((superJavaClassConstructor != null) &&
			ArrayUtil.isEmpty(superJavaClassConstructor.getExceptions())) {

			_classContent = StringUtil.replace(
				_classContent, javaTermContent, StringPool.BLANK);
		}
	}

	protected void checkConstructorParameterOrder(JavaTerm javaTerm) {
		int previousPos = -1;

		for (String parameterName : javaTerm.getParameterNames()) {
			Pattern pattern = Pattern.compile(
				"\\{\n([\\s\\S]*?)(_" + parameterName + " =[ \t\n]+" +
					parameterName + ";)");

			Matcher matcher = pattern.matcher(javaTerm.getContent());

			if (!matcher.find()) {
				continue;
			}

			String beforeParameter = matcher.group(1);

			if (beforeParameter.contains(parameterName + " =")) {
				continue;
			}

			int pos = matcher.start(2);

			if (previousPos > pos) {
				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Constructor parameter order " + parameterName + ": " +
						_fileName);

				return;
			}

			previousPos = pos;
		}
	}

	protected void checkFinalableFieldType(
			JavaTerm javaTerm, Set<String> annotationsExclusions,
			String modifierDefinition)
		throws Exception {

		for (String annotation : annotationsExclusions) {
			if (javaTerm.hasAnnotation(annotation)) {
				return;
			}
		}

		StringBundler sb = new StringBundler(6);

		sb.append("(((\\+\\+( ?))|(--( ?)))");
		sb.append(javaTerm.getName());
		sb.append(")|((\\b|\\.)");
		sb.append(javaTerm.getName());
		sb.append("((( )((=)|(\\+=)|(-=)|(\\*=)|(/=)|(%=)))");
		sb.append("|(\\+\\+)|(--)|(( )((\\|=)|(&=)|(^=)))))");

		Pattern pattern = Pattern.compile(sb.toString());

		if (!isFinalableField(javaTerm, _name, pattern, true)) {
			return;
		}

		String javaTermContent = javaTerm.getContent();

		String newJavaTermContent = StringUtil.replaceFirst(
			javaTermContent, modifierDefinition, modifierDefinition + " final");

		_classContent = StringUtil.replace(
			_classContent, javaTermContent, newJavaTermContent);
	}

	protected void checkImmutableFieldType(String javaTermName) {
		if (javaTermName.equals("serialVersionUID")) {
			return;
		}

		Matcher matcher = _camelCasePattern.matcher(javaTermName);

		String newName = matcher.replaceAll("$1_$2");

		newName = StringUtil.toUpperCase(newName);

		_classContent = _classContent.replaceAll(
			"(?<=[\\W&&[^.\"]])(" + javaTermName + ")\\b", newName);
	}

	protected void checkJavaFieldType(
			Set<JavaTerm> javaTerms, JavaTerm javaTerm,
			Set<String> annotationsExclusions, Set<String> immutableFieldTypes)
		throws Exception {

		if (!_javaSourceProcessor.portalSource ||
			(!javaTerm.isVariable() && !javaTerm.isMethod())) {

			return;
		}

		String javaTermName = javaTerm.getName();

		if (javaTerm.isMethod() &&
			_underscoreNotAllowedMethodNames.contains(javaTermName)) {

			return;
		}

		Pattern pattern = Pattern.compile(
			"\t(private |protected |public )" +
				"(((final|static|transient)( |\n))*)([\\s\\S]*?)" +
					javaTermName);

		String javaTermContent = javaTerm.getContent();

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return;
		}

		if ((javaTerm.isPrivate() && !javaTermName.equals("serialVersionUID")) ^
			(javaTermName.charAt(0) == CharPool.UNDERLINE)) {

			if (javaTerm.isPrivate()) {
				if (!javaTermContent.contains("@Reference")) {
					if (getJavaTermCount(javaTerms, javaTermName) > 1) {
						_javaSourceProcessor.processErrorMessage(
							_fileName,
							"Private method or variable should start with " +
								"underscore: " + _fileName + " " +
									javaTerm.getLineCount());
					}
					else {
						_classContent = _classContent.replaceAll(
							"(?<=[\\W&&[^.\"]])(" + javaTermName + ")\\b",
							StringPool.UNDERLINE.concat(javaTermName));
					}
				}
			}
			else {
				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Only private method or variable should start with " +
						"underscore: " + _fileName + " " +
							javaTerm.getLineCount());
			}
		}

		if (!javaTerm.isVariable()) {
			return;
		}

		String modifierDefinition = StringUtil.trim(
			javaTermContent.substring(matcher.start(1), matcher.start(6)));

		boolean isFinal = modifierDefinition.contains("final");
		boolean isStatic = modifierDefinition.contains("static");
		String javaFieldType = StringUtil.trim(matcher.group(6));

		if (isFinal && isStatic) {
			checkMutableFieldType(javaTerm, javaFieldType);
		}

		if (!isFinal && !javaTerm.isPublic() &&
			!_fileName.endsWith("ObjectGraphUtilTest.java")) {

			matcher = _isNullPattern.matcher(javaTermContent);

			if (matcher.find()) {
				_classContent = StringUtil.replace(
					_classContent, javaTermContent, matcher.replaceFirst(";$1"));
			}
		}

		if (!javaTerm.isPrivate()) {
			return;
		}

		if (isFinal) {
			if (immutableFieldTypes.contains(javaFieldType)) {
				if (isStatic) {
					checkImmutableFieldType(javaTerm.getName());
				}
				else {
					checkStaticableFieldType(javaTerm.getContent());
				}
			}
		}
		else {
			checkFinalableFieldType(
				javaTerm, annotationsExclusions, modifierDefinition);
		}
	}

	protected void checkLineBreak(JavaTerm javaTerm) {
		Matcher matcher = _lineBreakPattern.matcher(javaTerm.getContent());

		while (matcher.find()) {
			if (_javaSourceProcessor.getLevel(matcher.group(2)) >= 0) {
				continue;
			}

			int lineCount =
				javaTerm.getLineCount() +
					_javaSourceProcessor.getLineCount(
						javaTerm.getContent(), matcher.end(1));

			_javaSourceProcessor.processErrorMessage(
				_fileName,
				"Create a new var for " + StringUtil.trim(matcher.group(1)) +
					" for better readability: " + _fileName + " " + lineCount);
		}
	}

	protected void checkMutableFieldType(
		JavaTerm javaTerm, String javaFieldType) {

		if (!javaFieldType.startsWith("List<") &&
			!javaFieldType.startsWith("Map<") &&
			!javaFieldType.startsWith("Set<")) {

			return;
		}

		String javaTermName = javaTerm.getName();

		if (!StringUtil.isUpperCase(javaTermName)) {
			return;
		}

		StringBuilder sb = new StringBuilder(javaTermName.length());

		for (int i = 0; i < javaTermName.length(); i++) {
			char c = javaTermName.charAt(i);

			if (i > 1) {
				if (c == CharPool.UNDERLINE) {
					continue;
				}

				if (javaTermName.charAt(i - 1) == CharPool.UNDERLINE) {
					sb.append(c);

					continue;
				}
			}

			sb.append(Character.toLowerCase(c));
		}

		String newName = sb.toString();

		if (!newName.equals(javaTermName)) {
			if (javaTerm.isPrivate()) {
				_classContent = _classContent.replaceAll(
					"(?<=[\\W&&[^.\"]])(" + javaTermName + ")\\b", newName);
			}
			else {
				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Rename " + javaTermName + " to " + newName + " " +
						javaTerm.getLineCount());
			}
		}
	}

	protected void checkStaticableFieldType(String javaTermContent) {
		if (!javaTermContent.contains(StringPool.EQUAL)) {
			return;
		}

		String newJavaTermContent = StringUtil.replaceFirst(
			javaTermContent, "private final", "private static final");

		_classContent = StringUtil.replace(
			_classContent, javaTermContent, newJavaTermContent);
	}

	protected void checkTestAnnotations(JavaTerm javaTerm) {
		int methodType = javaTerm.getType();

		if ((methodType != JavaTerm.TYPE_METHOD_PUBLIC) &&
			(methodType != JavaTerm.TYPE_METHOD_PUBLIC_STATIC)) {

			return;
		}

		checkAnnotationForMethod(
			javaTerm, "After", "\\btearDown(?!Class)",
			JavaTerm.TYPE_METHOD_PUBLIC, _fileName);
		checkAnnotationForMethod(
			javaTerm, "AfterClass", "\\btearDownClass",
			JavaTerm.TYPE_METHOD_PUBLIC_STATIC, _fileName);
		checkAnnotationForMethod(
			javaTerm, "Before", "\\bsetUp(?!Class)",
			JavaTerm.TYPE_METHOD_PUBLIC, _fileName);
		checkAnnotationForMethod(
			javaTerm, "BeforeClass", "\\bsetUpClass",
			JavaTerm.TYPE_METHOD_PUBLIC_STATIC, _fileName);
		checkAnnotationForMethod(
			javaTerm, "Test", "^.*test", JavaTerm.TYPE_METHOD_PUBLIC,
			_fileName);
	}

	protected void checkUnusedParameters(JavaTerm javaTerm) {
		if (!javaTerm.isPrivate() || !javaTerm.isMethod()) {
			return;
		}

		for (String parameterName : javaTerm.getParameterNames()) {
			if (StringUtil.count(javaTerm.getContent(), parameterName) == 1) {
				_javaSourceProcessor.processErrorMessage(
					_fileName,
					"Unused parameter " + parameterName + ": " + _fileName +
						" " + javaTerm.getLineCount());
			}
		}
	}

	protected void fixJavaTermsDividers(
		Set<JavaTerm> javaTerms, List<String> javaTermSortExcludes) {

		JavaTerm previousJavaTerm = null;

		Iterator<JavaTerm> itr = javaTerms.iterator();

		while (itr.hasNext()) {
			JavaTerm javaTerm = itr.next();

			if (previousJavaTerm == null) {
				previousJavaTerm = javaTerm;

				continue;
			}

			String javaTermContent = javaTerm.getContent();

			if (javaTermContent.startsWith(_indent + "//")) {
				previousJavaTerm = javaTerm;

				continue;
			}

			String previousJavaTermContent = previousJavaTerm.getContent();

			if (previousJavaTermContent.startsWith(_indent + "//")) {
				previousJavaTerm = javaTerm;

				continue;
			}

			String javaTermName = javaTerm.getName();

			if (_javaSourceProcessor.isExcludedPath(
					javaTermSortExcludes, _absolutePath,
					javaTerm.getLineCount(), javaTermName)) {

				previousJavaTerm = javaTerm;

				continue;
			}

			String previousJavaTermName = previousJavaTerm.getName();

			boolean requiresEmptyLine = false;

			if (previousJavaTerm.getType() != javaTerm.getType()) {
				requiresEmptyLine = true;
			}
			else if (!javaTerm.isVariable()) {
				requiresEmptyLine = true;
			}
			else if ((StringUtil.isUpperCase(javaTermName) &&
					  !StringUtil.isLowerCase(javaTermName)) ||
					 (StringUtil.isUpperCase(previousJavaTermName) &&
					  !StringUtil.isLowerCase(previousJavaTermName))) {

				requiresEmptyLine = true;
			}
			else if (hasAnnotationCommentOrJavadoc(javaTermContent) ||
					 hasAnnotationCommentOrJavadoc(previousJavaTermContent)) {

				requiresEmptyLine = true;
			}
			else if ((previousJavaTerm.getType() ==
						JavaTerm.TYPE_VARIABLE_PRIVATE_STATIC) &&
					 (previousJavaTermName.equals("_instance") ||
					  previousJavaTermName.equals("_log") ||
					  previousJavaTermName.equals("_logger"))) {

				requiresEmptyLine = true;
			}
			else if (previousJavaTermContent.contains("\n\n\t") ||
					 javaTermContent.contains("\n\n\t")) {

				requiresEmptyLine = true;
			}

			if (requiresEmptyLine) {
				if (!_classContent.contains("\n\n" + javaTermContent)) {
					_classContent = StringUtil.replace(
						_classContent, "\n" + javaTermContent,
						"\n\n" + javaTermContent);

					return;
				}
			}
			else if (_classContent.contains("\n\n" + javaTermContent)) {
				_classContent = StringUtil.replace(
					_classContent, "\n\n" + javaTermContent,
					"\n" + javaTermContent);

				return;
			}

			previousJavaTerm = javaTerm;
		}

		String lastJavaTermContent = previousJavaTerm.getContent();

		if (!lastJavaTermContent.endsWith("\n\n")) {
			int x = _classContent.lastIndexOf(CharPool.CLOSE_CURLY_BRACE);

			_classContent = StringUtil.insert(
				_classContent, "\n", x - _indent.length() + 1);
		}
	}

	protected String fixLeadingTabs(
		String content, String line, int expectedTabCount) {

		int leadingTabCount = _javaSourceProcessor.getLeadingTabCount(line);

		String newLine = line;

		while (leadingTabCount != expectedTabCount) {
			if (leadingTabCount > expectedTabCount) {
				newLine = StringUtil.replaceFirst(
					newLine, StringPool.TAB, StringPool.BLANK);

				leadingTabCount--;
			}
			else {
				newLine = StringPool.TAB + newLine;

				leadingTabCount++;
			}
		}

		return StringUtil.replace(content, line, newLine);
	}

	protected void fixTabsAndIncorrectEmptyLines(JavaTerm javaTerm) {
		if (!javaTerm.isConstructor() && !javaTerm.isMethod()) {
			return;
		}

		String javaTermContent = "\n" + javaTerm.getContent();

		Pattern methodNameAndParametersPattern = Pattern.compile(
			"\n" + _indent + "(private |protected |public ).*?(\\{|;)\n",
			Pattern.DOTALL);

		Matcher matcher = methodNameAndParametersPattern.matcher(
			javaTermContent);

		if (!matcher.find()) {
			return;
		}

		String methodNameAndParameters = matcher.group();

		String[] lines = StringUtil.splitLines(methodNameAndParameters);

		if (lines.length == 1) {
			if (methodNameAndParameters.endsWith("{\n") &&
				javaTermContent.contains(methodNameAndParameters + "\n") &&
				!javaTermContent.contains(
					methodNameAndParameters + "\n" + _indent + StringPool.TAB +
						"/*") &&
				!javaTermContent.contains(
					methodNameAndParameters + "\n" + _indent + StringPool.TAB +
						"// ")) {

				String trimmedJavaTermContent = StringUtil.trimTrailing(
					javaTermContent);

				if (!trimmedJavaTermContent.endsWith(
						"\n\n" + _indent + StringPool.CLOSE_CURLY_BRACE)) {

					_classContent = StringUtil.replace(
						_classContent, methodNameAndParameters + "\n",
						methodNameAndParameters);
				}
			}

			return;
		}

		if (methodNameAndParameters.endsWith("{\n") &&
			!javaTermContent.contains(methodNameAndParameters + "\n") &&
			!javaTermContent.contains(
				methodNameAndParameters + _indent +
					StringPool.CLOSE_CURLY_BRACE)) {

			_classContent = StringUtil.replace(
				_classContent, methodNameAndParameters,
				methodNameAndParameters + "\n");
		}

		boolean throwsException = methodNameAndParameters.contains(
			_indent + "throws ");

		String newMethodNameAndParameters = methodNameAndParameters;

		int expectedTabCount = -1;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (line.contains(_indent + "throws ")) {
				newMethodNameAndParameters = fixLeadingTabs(
					newMethodNameAndParameters, line, _indent.length() + 1);

				break;
			}

			if (expectedTabCount == -1) {
				if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
					expectedTabCount = Math.max(
						_javaSourceProcessor.getLeadingTabCount(line),
						_indent.length()) + 1;

					if (throwsException &&
						(expectedTabCount == (_indent.length() + 1))) {

						expectedTabCount += 1;
					}
				}
			}
			else {
				String previousLine = lines[i - 1];

				if (previousLine.endsWith(StringPool.COMMA) ||
					previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {

					newMethodNameAndParameters = fixLeadingTabs(
						newMethodNameAndParameters, line, expectedTabCount);
				}
				else {
					newMethodNameAndParameters = fixLeadingTabs(
						newMethodNameAndParameters, line,
						_javaSourceProcessor.getLeadingTabCount(previousLine) +
							1);
				}
			}
		}

		_classContent = StringUtil.replace(
			_classContent, methodNameAndParameters, newMethodNameAndParameters);
	}

	protected void formatAnnotations(
			JavaTerm javaTerm, List<String> testAnnotationsExcludes)
		throws Exception {

		if ((_indent.length() == 1) &&
			!_javaSourceProcessor.isExcludedPath(
				testAnnotationsExcludes, _absolutePath) &&
			_fileName.endsWith("Test.java")) {

			checkTestAnnotations(javaTerm);
		}

		String javaTermContent = javaTerm.getContent();

		String newJavaTermContent = _javaSourceProcessor.formatAnnotations(
			_fileName, javaTerm.getName(), javaTermContent, _indent);

		if (!javaTermContent.equals(newJavaTermContent)) {
			_classContent = _classContent.replace(
				javaTermContent, newJavaTermContent);
		}
	}

	protected String getAccessModifier() {
		Matcher matcher = _classPattern.matcher(_classContent);

		if (matcher.find()) {
			String accessModifier = matcher.group(1);

			if (accessModifier.equals(_ACCESS_MODIFIER_PRIVATE) ||
				accessModifier.equals(_ACCESS_MODIFIER_PROTECTED) ||
				accessModifier.equals(_ACCESS_MODIFIER_PUBLIC)) {

				return accessModifier;
			}
		}

		return _ACCESS_MODIFIER_UNKNOWN;
	}

	protected String getClassName(String line) {
		int pos = line.indexOf(" extends ");

		if (pos == -1) {
			pos = line.indexOf(" implements ");
		}

		if (pos == -1) {
			pos = line.indexOf(CharPool.OPEN_CURLY_BRACE);
		}

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		pos = line.indexOf(CharPool.LESS_THAN);

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		line = line.trim();

		pos = line.lastIndexOf(CharPool.SPACE);

		return line.substring(pos + 1);
	}

	protected String getConstructorOrMethodName(String line, int pos) {
		line = line.substring(0, pos);

		int x = line.lastIndexOf(CharPool.SPACE);

		return line.substring(x + 1);
	}

	protected JavaTerm getJavaTerm(
			String name, int type, int startPos, int endPos)
		throws Exception {

		String javaTermContent = _classContent.substring(startPos, endPos);

		int lineCount =
			_lineCount +
				_javaSourceProcessor.getLineCount(_classContent, startPos) - 1;

		if (Validator.isNull(name) || !isValidJavaTerm(javaTermContent)) {
			throw new InvalidJavaTermException(lineCount);
		}

		JavaTerm javaTerm = new JavaTerm(
			name, type, javaTermContent, lineCount, _indent);

		if (javaTerm.isConstructor()) {
			_constructorCount++;
		}

		if (!javaTerm.isClass()) {
			return javaTerm;
		}

		JavaClass innerClass = new JavaClass(
			name, _packagePath, _file, _fileName, _absolutePath, _content,
			javaTermContent, lineCount, _indent + StringPool.TAB, this,
			_javaTermAccessLevelModifierExcludes, _javaSourceProcessor);

		_innerClasses.add(innerClass);

		return javaTerm;
	}

	protected int getJavaTermCount(
		Set<JavaTerm> javaTerms, String javaTermName) {

		int count = 0;

		for (JavaTerm javaTerm : javaTerms) {
			String curJavaTermName = javaTerm.getName();

			if (curJavaTermName.equals(javaTermName)) {
				count += 1;
			}
		}

		return count;
	}

	protected Set<JavaTerm> getJavaTerms() throws Exception {
		if (_javaTerms != null) {
			return _javaTerms;
		}

		Set<JavaTerm> javaTerms = new TreeSet<>(new JavaTermComparator(false));
		List<JavaTerm> staticBlocks = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(_classContent));

		int index = 0;

		String line = null;

		String javaTermName = null;
		int javaTermStartPosition = -1;
		int javaTermType = -1;

		int lastCommentOrAnnotationPos = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (_javaSourceProcessor.getLeadingTabCount(line) !=
					_indent.length()) {

				index = index + line.length() + 1;

				continue;
			}

			if (line.startsWith(_indent + "private ") ||
				line.equals(_indent + "private") ||
				line.startsWith(_indent + "protected ") ||
				line.equals(_indent + "protected") ||
				line.startsWith(_indent + "public ") ||
				line.equals(_indent + "public") ||
				line.equals(_indent + "static {")) {

				Tuple tuple = getJavaTermTuple(line, _classContent, index);

				int javaTermEndPosition = 0;

				if (lastCommentOrAnnotationPos == -1) {
					javaTermEndPosition = index;
				}
				else {
					javaTermEndPosition = lastCommentOrAnnotationPos;
				}

				if ((javaTermStartPosition != -1) &&
					(javaTermEndPosition < _classContent.length())) {

					JavaTerm javaTerm = getJavaTerm(
						javaTermName, javaTermType, javaTermStartPosition,
						javaTermEndPosition);

					if (javaTermType == JavaTerm.TYPE_STATIC_BLOCK) {
						staticBlocks.add(javaTerm);
					}
					else if (!javaTerms.add(javaTerm)) {
						_javaSourceProcessor.processErrorMessage(
							_fileName,
							"Duplicate " + javaTermName + ": " + _fileName);

						_javaTerms = Collections.emptySet();

						return _javaTerms;
					}
				}

				javaTermName = (String)tuple.getObject(0);
				javaTermStartPosition = javaTermEndPosition;
				javaTermType = (Integer)tuple.getObject(1);

				lastCommentOrAnnotationPos = -1;
			}
			else if (hasAnnotationCommentOrJavadoc(line)) {
				if (lastCommentOrAnnotationPos == -1) {
					lastCommentOrAnnotationPos = index;
				}
			}
			else if (!line.startsWith(_indent + StringPool.CLOSE_CURLY_BRACE) &&
					 !line.startsWith(_indent + StringPool.CLOSE_PARENTHESIS) &&
					 !line.startsWith(_indent + "extends") &&
					 !line.startsWith(_indent + "implements") &&
					 !_javaSourceProcessor.isExcludedPath(
						 _javaTermAccessLevelModifierExcludes, _absolutePath)) {

				Matcher matcher = _classPattern.matcher(_classContent);

				if (matcher.find()) {
					String insideClass = _classContent.substring(matcher.end());

					if (insideClass.contains(line) &&
						!isEnumType(line, matcher.group(4))) {

						int lineCount =
							_lineCount +
								_javaSourceProcessor.getLineCount(
									_classContent, index) - 1;

						_javaSourceProcessor.processErrorMessage(
							_fileName,
							"Missing access level modifier: " + _fileName +
								" " + lineCount);
					}
				}
			}

			index = index + line.length() + 1;
		}

		if (javaTermStartPosition != -1) {
			int javaTermEndPosition =
				_classContent.lastIndexOf(CharPool.CLOSE_CURLY_BRACE) -
					_indent.length() + 1;

			JavaTerm javaTerm = getJavaTerm(
				javaTermName, javaTermType, javaTermStartPosition,
				javaTermEndPosition);

			if (javaTermType == JavaTerm.TYPE_STATIC_BLOCK) {
				staticBlocks.add(javaTerm);
			}
			else if (!javaTerms.add(javaTerm)) {
				_javaSourceProcessor.processErrorMessage(
					_fileName, "Duplicate " + javaTermName + ": " + _fileName);

				_javaTerms = Collections.emptySet();

				return _javaTerms;
			}
		}

		_javaTerms = addStaticBlocks(javaTerms, staticBlocks);

		return _javaTerms;
	}

	protected Tuple getJavaTermTuple(String line, String accessModifier) {
		if (!line.startsWith(_indent + accessModifier + StringPool.SPACE)) {
			return null;
		}

		int x = line.indexOf(CharPool.EQUAL);
		int y = line.indexOf(CharPool.OPEN_PARENTHESIS);

		if (line.startsWith(_indent + accessModifier + " static ")) {
			if (line.contains(" class ") || line.contains(" enum ")) {
				return getJavaTermTuple(
					getClassName(line), accessModifier,
					JavaTerm.TYPE_CLASS_PRIVATE_STATIC,
					JavaTerm.TYPE_CLASS_PROTECTED_STATIC,
					JavaTerm.TYPE_CLASS_PUBLIC_STATIC);
			}

			if (((x > 0) && ((y == -1) || (y > x))) ||
				(line.endsWith(StringPool.SEMICOLON) && (y == -1))) {

				return getJavaTermTuple(
					getVariableName(line), accessModifier,
					JavaTerm.TYPE_VARIABLE_PRIVATE_STATIC,
					JavaTerm.TYPE_VARIABLE_PROTECTED_STATIC,
					JavaTerm.TYPE_VARIABLE_PUBLIC_STATIC);
			}

			if (y != -1) {
				return getJavaTermTuple(
					getConstructorOrMethodName(line, y), accessModifier,
					JavaTerm.TYPE_METHOD_PRIVATE_STATIC,
					JavaTerm.TYPE_METHOD_PROTECTED_STATIC,
					JavaTerm.TYPE_METHOD_PUBLIC_STATIC);
			}

			return null;
		}

		if (line.contains(" @interface ") || line.contains(" class ") ||
			line.contains(" enum ") || line.contains(" interface ")) {

			return getJavaTermTuple(
				getClassName(line), accessModifier, JavaTerm.TYPE_CLASS_PRIVATE,
				JavaTerm.TYPE_CLASS_PROTECTED, JavaTerm.TYPE_CLASS_PUBLIC);
		}

		if (((x > 0) && ((y == -1) || (y > x))) ||
			(line.endsWith(StringPool.SEMICOLON) && (y == -1))) {

			return getJavaTermTuple(
				getVariableName(line), accessModifier,
				JavaTerm.TYPE_VARIABLE_PRIVATE,
				JavaTerm.TYPE_VARIABLE_PROTECTED,
				JavaTerm.TYPE_VARIABLE_PUBLIC);
		}

		if (y != -1) {
			int spaceCount = StringUtil.count(
				line.substring(0, y), CharPool.SPACE);

			if (spaceCount == 1) {
				return getJavaTermTuple(
					getConstructorOrMethodName(line, y), accessModifier,
					JavaTerm.TYPE_CONSTRUCTOR_PRIVATE,
					JavaTerm.TYPE_CONSTRUCTOR_PROTECTED,
					JavaTerm.TYPE_CONSTRUCTOR_PUBLIC);
			}

			if (spaceCount > 1) {
				return getJavaTermTuple(
					getConstructorOrMethodName(line, y), accessModifier,
					JavaTerm.TYPE_METHOD_PRIVATE,
					JavaTerm.TYPE_METHOD_PROTECTED,
					JavaTerm.TYPE_METHOD_PUBLIC);
			}
		}

		return null;
	}

	protected Tuple getJavaTermTuple(String line, String content, int index)
		throws Exception {

		int posStartNextLine = index;

		while (!line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			   !line.endsWith(StringPool.SEMICOLON)) {

			posStartNextLine =
				content.indexOf(CharPool.NEW_LINE, posStartNextLine) + 1;

			int posEndNextline = content.indexOf(
				CharPool.NEW_LINE, posStartNextLine);

			String nextLine = content.substring(
				posStartNextLine, posEndNextline);

			nextLine = StringUtil.trimLeading(nextLine);

			if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
				line += nextLine;
			}
			else {
				line += StringPool.SPACE + nextLine;
			}
		}

		line = StringUtil.replace(line, " synchronized ", StringPool.SPACE);

		for (String accessModifier : _ACCESS_MODIFIERS) {
			Tuple tuple = getJavaTermTuple(line, accessModifier);

			if (tuple != null) {
				return tuple;
			}
		}

		if (!line.startsWith(_indent + "static {")) {
			int lineCount =
				_lineCount + _javaSourceProcessor.getLineCount(content, index) -
					1;

			throw new InvalidJavaTermException(lineCount);
		}

		return new Tuple("static", JavaTerm.TYPE_STATIC_BLOCK);
	}

	protected Tuple getJavaTermTuple(
		String javaTermName, String accessModifier, int privateJavaTermType,
		int protectedJavaTermType, int publicJavaTermType) {

		if (accessModifier.equals(_ACCESS_MODIFIER_PRIVATE)) {
			return new Tuple(javaTermName, privateJavaTermType);
		}

		if (accessModifier.equals(_ACCESS_MODIFIER_PROTECTED)) {
			return new Tuple(javaTermName, protectedJavaTermType);
		}

		return new Tuple(javaTermName, publicJavaTermType);
	}

	protected String getVariableName(String line) {
		int x = line.indexOf(CharPool.EQUAL);
		int y = line.lastIndexOf(CharPool.SPACE);

		if (x != -1) {
			line = line.substring(0, x);
			line = StringUtil.trim(line);

			y = line.lastIndexOf(CharPool.SPACE);

			return line.substring(y + 1);
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			return line.substring(y + 1, line.length() - 1);
		}

		return StringPool.BLANK;
	}

	protected boolean hasAnnotationCommentOrJavadoc(String s) {
		if (s.startsWith(_indent + StringPool.AT) ||
			s.startsWith(_indent + StringPool.SLASH) ||
			s.startsWith(_indent + " *")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isEnumType(String line, String javaClassType) {
		if (!javaClassType.equals("enum")) {
			return false;
		}

		Matcher matcher = _enumTypePattern.matcher(line + "\n");

		return matcher.find();
	}

	protected boolean isFinalableField(
			JavaTerm javaTerm, String javaTermClassName, Pattern pattern,
			boolean checkOuterClass)
		throws Exception {

		if (checkOuterClass && (_outerClass != null)) {
			return _outerClass.isFinalableField(
				javaTerm, javaTermClassName, pattern, true);
		}

		for (JavaTerm curJavaTerm : getJavaTerms()) {
			if (!curJavaTerm.isMethod() &&
				(!curJavaTerm.isConstructor() ||
				 javaTermClassName.equals(_name))) {

				continue;
			}

			String content = curJavaTerm.getContent();

			Matcher matcher = pattern.matcher(content);

			if (content.contains(javaTerm.getName()) && matcher.find()) {
				return false;
			}
		}

		for (JavaClass innerClass : _innerClasses) {
			if (!innerClass.isFinalableField(
					javaTerm, javaTermClassName, pattern, false)) {

				return false;
			}
		}

		return true;
	}

	protected boolean isValidJavaTerm(String content) {
		if (content.startsWith(_indent + "static {")) {
			return true;
		}

		while (!content.startsWith(_indent + "private") &&
			   !content.startsWith(_indent + "protected") &&
			   !content.startsWith(_indent + "public")) {

			content = content.substring(content.indexOf(CharPool.NEW_LINE) + 1);
		}

		int indentLinesCount =
			StringUtil.count(content, "\n" + _indent) -
				StringUtil.count(content, "\n" + _indent + StringPool.TAB);

		content = StringUtil.trim(content);

		if (content.endsWith(StringPool.CLOSE_CURLY_BRACE) &&
			((indentLinesCount == 1) ||
			 (((indentLinesCount == 2) || (indentLinesCount == 3)) &&
			  content.contains("\n" + _indent + "static {")))) {

			return true;
		}
		else if ((content.endsWith("};") && (indentLinesCount == 1)) ||
				 (content.endsWith(StringPool.SEMICOLON) &&
				  (indentLinesCount == 0))) {

			return true;
		}

		return false;
	}

	protected void sortJavaTerms(
		JavaTerm previousJavaTerm, JavaTerm javaTerm,
		List<String> javaTermSortExcludes) {

		if ((previousJavaTerm == null) || _content.contains("@Meta.OCD(")) {
			return;
		}

		String javaTermName = javaTerm.getName();

		if (_javaSourceProcessor.isExcludedPath(
				javaTermSortExcludes, _absolutePath, -1, javaTermName)) {

			return;
		}

		if (previousJavaTerm.getLineCount() <= javaTerm.getLineCount()) {
			return;
		}

		String previousJavaTermName = previousJavaTerm.getName();

		String javaTermNameLowerCase = StringUtil.toLowerCase(javaTermName);
		String previousJavaTermNameLowerCase = StringUtil.toLowerCase(
			previousJavaTermName);

		if (_fileName.contains("persistence") &&
			((previousJavaTermName.startsWith("doCount") &&
			  javaTermName.startsWith("doCount")) ||
			 (previousJavaTermName.startsWith("doFind") &&
			  javaTermName.startsWith("doFind")) ||
			 (previousJavaTermNameLowerCase.startsWith("count") &&
			  javaTermNameLowerCase.startsWith("count")) ||
			 (previousJavaTermNameLowerCase.startsWith("filter") &&
			  javaTermNameLowerCase.startsWith("filter")) ||
			 (previousJavaTermNameLowerCase.startsWith("find") &&
			  javaTermNameLowerCase.startsWith("find")) ||
			 (previousJavaTermNameLowerCase.startsWith("join") &&
			  javaTermNameLowerCase.startsWith("join")))) {
		}
		else {
			_classContent = StringUtil.replaceFirst(
				_classContent, "\n" + javaTerm.getContent(),
				"\n" + previousJavaTerm.getContent());
			_classContent = StringUtil.replaceLast(
				_classContent, "\n" + previousJavaTerm.getContent(),
				"\n" + javaTerm.getContent());
		}
	}

	private void _formatReturnStatements(JavaTerm javaTerm) {
		String returnType = javaTerm.getReturnType();

		if (!returnType.equals("boolean")) {
			return;
		}

		String javaTermContent = javaTerm.getContent();

		Matcher matcher = _returnPattern.matcher(javaTermContent);

		while (matcher.find()) {
			String returnStatement = matcher.group();

			if (returnStatement.contains(" {\n") ||
				(!returnStatement.contains("|\n") &&
				 !returnStatement.contains("&\n"))) {

				continue;
			}

			String tabs = matcher.group(1);

			StringBundler sb = new StringBundler(11);

			sb.append(javaTermContent.substring(0, matcher.end(1)));
			sb.append("if (");
			sb.append(matcher.group(2));
			sb.append(") {\n\n");
			sb.append(tabs);
			sb.append("\treturn true;\n");
			sb.append(tabs);
			sb.append("}\n\n");
			sb.append(tabs);
			sb.append("return false;\n");
			sb.append(javaTermContent.substring(matcher.end()));

			String newJavaTermContent = sb.toString();

			_classContent = _classContent.replace(
				javaTermContent, newJavaTermContent);

			return;
		}
	}

	private static final String _ACCESS_MODIFIER_PRIVATE = "private";

	private static final String _ACCESS_MODIFIER_PROTECTED = "protected";

	private static final String _ACCESS_MODIFIER_PUBLIC = "public";

	private static final String _ACCESS_MODIFIER_UNKNOWN = "unknown";

	private static final String[] _ACCESS_MODIFIERS = {
		_ACCESS_MODIFIER_PRIVATE, _ACCESS_MODIFIER_PROTECTED,
		_ACCESS_MODIFIER_PUBLIC
	};

	private static final List<String> _underscoreNotAllowedMethodNames =
		ListUtil.fromArray(new String[] {"readObject", "writeObject"});

	private final String _absolutePath;
	private final Pattern _camelCasePattern = Pattern.compile(
		"([a-z])([A-Z0-9])");
	private final Pattern _chainingPattern = Pattern.compile(
		"^((?!this\\().)*\\WgetClass\\(\\)\\..", Pattern.DOTALL);
	private String _classContent;
	private final Pattern _classPattern = Pattern.compile(
		"(private|protected|public) ((abstract|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?) \\{\n");
	private int _constructorCount = 0;
	private final String _content;
	private final Pattern _enumTypePattern = Pattern.compile(
		"\t[A-Z0-9]+[ _,;\\(\n]");
	private final File _file;
	private final String _fileName;
	private final String _indent;
	private final List<JavaClass> _innerClasses = new ArrayList<>();
	private final Pattern _isNullPattern = Pattern.compile(
		" =\\s+null;(\\s+)$");
	private final JavaSourceProcessor _javaSourceProcessor;
	private final List<String> _javaTermAccessLevelModifierExcludes;
	private Set<JavaTerm> _javaTerms;
	private final Pattern _lineBreakPattern = Pattern.compile(
		"\n(.*)\\(\n((.+,\n)*.*\\)) \\+\n");
	private final int _lineCount;
	private final String _name;
	private final JavaClass _outerClass;
	private String _packagePath;
	private Pattern _returnPattern = Pattern.compile(
		"\n(\t+)return (.*?);\n", Pattern.DOTALL);

}