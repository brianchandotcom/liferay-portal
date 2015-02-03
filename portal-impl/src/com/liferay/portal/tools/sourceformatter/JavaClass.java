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

package com.liferay.portal.tools.sourceformatter;

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
			String absolutePath, String content, int lineCount, String indent,
			JavaClass outerClass,
			List<String> javaTermAccessLevelModifierExclusionFiles)
		throws Exception {

		_name = name;
		_packagePath = packagePath;
		_file = file;
		_fileName = fileName;
		_absolutePath = absolutePath;
		_content = content;
		_lineCount = lineCount;
		_indent = indent;
		_outerClass = outerClass;
		_javaTermAccessLevelModifierExclusionFiles =
			javaTermAccessLevelModifierExclusionFiles;

		_javaTerms = getJavaTerms();
	}

	public String formatJavaTerms(
			Set<String> annotationsExclusions, Set<String> immutableFieldTypes,
			List<String> checkJavaFieldTypesExclusionFiles,
			List<String> javaTermSortExclusionFiles,
			List<String> testAnnotationsExclusionFiles)
		throws Exception {

		if ((_javaTerms == null) || _javaTerms.isEmpty()) {
			return _content;
		}

		String originalContent = _content;

		JavaTerm previousJavaTerm = null;

		Iterator<JavaTerm> itr = _javaTerms.iterator();

		while (itr.hasNext()) {
			JavaTerm javaTerm = itr.next();

			if (javaTerm.isConstructor()) {
				checkConstructor(javaTerm);
			}

			checkUnusedParameters(javaTerm);

			if (!BaseSourceProcessor.isExcludedFile(
					checkJavaFieldTypesExclusionFiles, _absolutePath)) {

				checkJavaFieldType(
					javaTerm, annotationsExclusions, immutableFieldTypes);
			}

			if (!originalContent.equals(_content)) {
				return _content;
			}

			sortJavaTerms(
				previousJavaTerm, javaTerm, javaTermSortExclusionFiles);
			fixTabsAndIncorrectEmptyLines(javaTerm);
			formatAnnotations(javaTerm, testAnnotationsExclusionFiles);

			if (!originalContent.equals(_content)) {
				return _content;
			}

			previousJavaTerm = javaTerm;
		}

		for (JavaClass innerClass : _innerClasses) {
			String innerClassContent = innerClass.getContent();

			String newInnerClassContent = innerClass.formatJavaTerms(
				annotationsExclusions, immutableFieldTypes,
				checkJavaFieldTypesExclusionFiles, javaTermSortExclusionFiles,
				testAnnotationsExclusionFiles);

			if (!innerClassContent.equals(newInnerClassContent)) {
				_content = StringUtil.replace(
					_content, innerClassContent, newInnerClassContent);

				return _content;
			}
		}

		fixJavaTermsDividers(_javaTerms, javaTermSortExclusionFiles);

		return _content;
	}

	public String getContent() {
		return _content;
	}

	protected Set<JavaTerm> addStaticBlocks(
		Set<JavaTerm> javaTerms, List<JavaTerm> staticBlocks) {

		Set<JavaTerm> newJavaTerms = new TreeSet<JavaTerm>(
			new JavaTermComparator());

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

		String methodContent = javaTerm.getContent();
		String methodName = javaTerm.getName();

		Pattern pattern = Pattern.compile(requiredMethodNameRegex);

		Matcher matcher = pattern.matcher(methodName);

		if (methodContent.contains(
				_indent + StringPool.AT + annotation + "\n") ||
			methodContent.contains(
				_indent + StringPool.AT + annotation +
					StringPool.OPEN_PARENTHESIS)) {

			if (!matcher.find()) {
				BaseSourceProcessor.processErrorMessage(
					fileName,
					"LPS-36303: Incorrect method name: " + methodName + " " +
						fileName);
			}
			else if (javaTerm.getType() != requiredMethodType) {
				BaseSourceProcessor.processErrorMessage(
					fileName,
					"LPS-36303: Incorrect method type for " + methodName + " " +
						fileName);
			}
		}
		else if (matcher.find() &&
				 !methodContent.contains(_indent + "@Override")) {

			BaseSourceProcessor.processErrorMessage(
				fileName,
				"Annotation @" + annotation + " required for " + methodName +
					" " + fileName);
		}
	}

	protected void checkConstructor(JavaTerm javaTerm) throws Exception {
		String javaTermContent = javaTerm.getContent();

		if (javaTermContent.contains(StringPool.TAB + "super();")) {
			String newJavaTermContent = StringUtil.replace(
				javaTermContent, StringPool.TAB + "super();", StringPool.BLANK);

			_content = StringUtil.replace(
				_content, javaTermContent, newJavaTermContent);

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

		Matcher matcher = pattern.matcher(_content);

		if (!matcher.find()) {
			return;
		}

		JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		javaDocBuilder.addSource(_file);

		com.thoughtworks.qdox.model.JavaClass javaClass =
			javaDocBuilder.getClassByName(
				_packagePath + StringPool.PERIOD + _name);

		com.thoughtworks.qdox.model.JavaClass superJavaClass =
			javaClass.getSuperJavaClass();

		JavaMethod superJavaClassConstructor =
			superJavaClass.getMethodBySignature(superJavaClass.getName(), null);

		if ((superJavaClassConstructor != null) &&
			ArrayUtil.isEmpty(superJavaClassConstructor.getExceptions())) {

			_content = StringUtil.replace(
				_content, javaTermContent, StringPool.BLANK);
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
				BaseSourceProcessor.processErrorMessage(
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

		String javaTermContent = javaTerm.getContent();

		for (String annotation : annotationsExclusions) {
			if (javaTermContent.contains(
					_indent + StringPool.AT + annotation)) {

				return;
			}
		}

		StringBundler sb = new StringBundler(8);

		sb.append("(((\\+\\+( ?))|(--( ?)))");
		sb.append(javaTerm.getName());
		sb.append(")");
		sb.append("|((\\b|\\.)");
		sb.append(javaTerm.getName());
		sb.append("((( )((=)|(\\+=)|(-=)|(\\*=)|(/=)|(%=)))");
		sb.append("|(\\+\\+)|(--)");
		sb.append("|(( )((\\|=)|(&=)|(^=)))))");

		Pattern pattern = Pattern.compile(sb.toString());

		if (!isFinalableField(javaTerm, _name, pattern, true)) {
			return;
		}

		String newJavaTermContent = StringUtil.replaceFirst(
			javaTermContent, modifierDefinition, modifierDefinition + "final ");

		_content = StringUtil.replace(
			_content, javaTermContent, newJavaTermContent);
	}

	protected void checkImmutableFieldType(JavaTerm javaTerm) {
		String oldName = javaTerm.getName();

		if (oldName.equals("serialVersionUID")) {
			return;
		}

		Matcher matcher = _camelCasePattern.matcher(oldName);

		String newName = matcher.replaceAll("$1_$2");

		newName = StringUtil.toUpperCase(newName);

		if (newName.charAt(0) != CharPool.UNDERLINE) {
			newName = StringPool.UNDERLINE.concat(newName);
		}

		_content = _content.replaceAll(
			"(?<=[\\W&&[^.\"]])(" + oldName + ")\\b", newName);
	}

	protected void checkJavaFieldType(
			JavaTerm javaTerm, Set<String> annotationsExclusions,
			Set<String> immutableFieldTypes)
		throws Exception {

		if (!BaseSourceProcessor.portalSource || !javaTerm.isVariable()) {
			return;
		}

		Pattern pattern = Pattern.compile(
			"\t(private |protected |public )(static )?(transient )?(final)?" +
				"([\\s\\S]*?)" + javaTerm.getName());

		String javaTermContent = javaTerm.getContent();

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return;
		}

		boolean isFinal = Validator.isNotNull(matcher.group(4));
		boolean isStatic = Validator.isNotNull(matcher.group(2));
		String javaFieldType = StringUtil.trim(matcher.group(5));

		if (isFinal && isStatic && javaFieldType.startsWith("Map<")) {
			checkMutableFieldType(javaTerm);
		}

		if (!javaTerm.isPrivate()) {
			return;
		}

		if (isFinal) {
			if (immutableFieldTypes.contains(javaFieldType)) {
				if (isStatic) {
					checkImmutableFieldType(javaTerm);
				}
				else {
					checkStaticableFieldType(javaTerm);
				}
			}
		}
		else {
			String modifierDefinition = javaTermContent.substring(
				matcher.start(1), matcher.start(5));

			checkFinalableFieldType(
				javaTerm, annotationsExclusions, modifierDefinition);
		}
	}

	protected void checkMutableFieldType(JavaTerm javaTerm) {
		String oldName = javaTerm.getName();

		String newName = oldName;

		if (javaTerm.isPrivate() && (newName.charAt(0) != CharPool.UNDERLINE)) {
			newName = StringPool.UNDERLINE.concat(newName);
		}

		if (StringUtil.isUpperCase(newName)) {
			StringBundler sb = new StringBundler(newName.length());

			for (int i = 0; i < newName.length(); i++) {
				char c = newName.charAt(i);

				if (i > 1) {
					if (c == CharPool.UNDERLINE) {
						continue;
					}

					if (newName.charAt(i - 1) == CharPool.UNDERLINE) {
						sb.append(c);

						continue;
					}
				}

				sb.append(Character.toLowerCase(c));
			}

			newName = sb.toString();
		}


		if (!newName.equals(oldName)) {
			_content = _content.replaceAll(
				"(?<=[\\W&&[^.\"]])(" + oldName + ")\\b", newName);
		}
	}

	protected void checkStaticableFieldType(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();

		if (!javaTermContent.contains(StringPool.EQUAL)) {
			return;
		}

		String newJavaTermContent = StringUtil.replaceFirst(
			javaTermContent, "private final", "private static final");

		_content = StringUtil.replace(
			_content, javaTermContent, newJavaTermContent);
	}

	protected void checkTestAnnotations(JavaTerm javaTerm) {
		int methodType = javaTerm.getType();

		if ((methodType != JavaTerm.TYPE_METHOD_PUBLIC) &&
			(methodType != JavaTerm.TYPE_METHOD_PUBLIC_STATIC)) {

			return;
		}

		checkAnnotationForMethod(
			javaTerm, "After", "^.*tearDown\\z", JavaTerm.TYPE_METHOD_PUBLIC,
			_fileName);
		checkAnnotationForMethod(
			javaTerm, "AfterClass", "^.*tearDownClass\\z",
			JavaTerm.TYPE_METHOD_PUBLIC_STATIC, _fileName);
		checkAnnotationForMethod(
			javaTerm, "Before", "^.*setUp\\z", JavaTerm.TYPE_METHOD_PUBLIC,
			_fileName);
		checkAnnotationForMethod(
			javaTerm, "BeforeClass", "^.*setUpClass\\z",
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
				BaseSourceProcessor.processErrorMessage(
					_fileName,
					"Unused parameter " + parameterName + ": " + _fileName +
						" " + javaTerm.getLineCount());
			}
		}
	}

	protected void fixJavaTermsDividers(
		Set<JavaTerm> javaTerms, List<String> javaTermSortExclusionFiles) {

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

			if (BaseSourceProcessor.isExcludedFile(
					javaTermSortExclusionFiles, _absolutePath,
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
				if (!_content.contains("\n\n" + javaTermContent)) {
					_content = StringUtil.replace(
						_content, "\n" + javaTermContent,
						"\n\n" + javaTermContent);

					return;
				}
			}
			else if (_content.contains("\n\n" + javaTermContent)) {
				_content = StringUtil.replace(
					_content, "\n\n" + javaTermContent, "\n" + javaTermContent);

				return;
			}

			previousJavaTerm = javaTerm;
		}

		String lastJavaTermContent = previousJavaTerm.getContent();

		if (!lastJavaTermContent.endsWith("\n\n")) {
			int x = _content.lastIndexOf(CharPool.CLOSE_CURLY_BRACE);

			_content = StringUtil.insert(
				_content, "\n", x - _indent.length() + 1);
		}
	}

	protected String fixLeadingTabs(
		String content, String line, int expectedTabCount) {

		int leadingTabCount = JavaSourceProcessor.getLeadingTabCount(line);

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

					_content = StringUtil.replace(
						_content, methodNameAndParameters + "\n",
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

			_content = StringUtil.replace(
				_content, methodNameAndParameters,
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
					expectedTabCount =
						Math.max(
							JavaSourceProcessor.getLeadingTabCount(line),
							_indent.length()) +
								1;

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
						JavaSourceProcessor.getLeadingTabCount(previousLine) +
							1);
				}
			}
		}

		_content = StringUtil.replace(
			_content, methodNameAndParameters, newMethodNameAndParameters);
	}

	protected void formatAnnotations(
			JavaTerm javaTerm, List<String> testAnnotationsExclusionFiles)
		throws Exception {

		if ((_indent.length() == 1) &&
			!BaseSourceProcessor.isExcludedFile(
				testAnnotationsExclusionFiles, _absolutePath) &&
			_fileName.endsWith("Test.java")) {

			checkTestAnnotations(javaTerm);
		}

		String javaTermContent = javaTerm.getContent();

		String newJavaTermContent = JavaSourceProcessor.formatAnnotations(
			_fileName, javaTerm.getName(), javaTermContent, _indent);

		if (!javaTermContent.equals(newJavaTermContent)) {
			_content = _content.replace(javaTermContent, newJavaTermContent);
		}
	}

	protected String getAccessModifier() {
		Matcher matcher = _classPattern.matcher(_content);

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
			pos = line.indexOf(StringPool.OPEN_CURLY_BRACE);
		}

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		pos = line.indexOf(StringPool.LESS_THAN);

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		line = line.trim();

		pos = line.lastIndexOf(StringPool.SPACE);

		return line.substring(pos + 1);
	}

	protected String getConstructorOrMethodName(String line, int pos) {
		line = line.substring(0, pos);

		int x = line.lastIndexOf(StringPool.SPACE);

		return line.substring(x + 1);
	}

	protected JavaTerm getJavaTerm(
			String name, int type, int lineCount, int startPos, int endPos)
		throws Exception {

		String javaTermContent = _content.substring(startPos, endPos);

		if (Validator.isNull(name) || !isValidJavaTerm(javaTermContent)) {
			return null;
		}

		JavaTerm javaTerm = new JavaTerm(
			name, type, javaTermContent, lineCount);

		if (javaTerm.isConstructor()) {
			_constructorCount++;
		}

		if (!javaTerm.isClass()) {
			return javaTerm;
		}

		JavaClass innerClass = new JavaClass(
			name, _packagePath, _file, _fileName, _absolutePath,
			javaTermContent, lineCount, _indent + StringPool.TAB, this,
			_javaTermAccessLevelModifierExclusionFiles);

		_innerClasses.add(innerClass);

		return javaTerm;
	}

	protected Set<JavaTerm> getJavaTerms() throws Exception {
		Set<JavaTerm> javaTerms = new TreeSet<JavaTerm>(
			new JavaTermComparator(false));
		List<JavaTerm> staticBlocks = new ArrayList<JavaTerm>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(_content));

		int index = 0;
		int lineCount = _lineCount - 1;

		String line = null;

		String javaTermName = null;
		int javaTermLineCount = -1;
		int javaTermStartPosition = -1;
		int javaTermType = -1;

		int lastCommentOrAnnotationPos = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			if (JavaSourceProcessor.getLeadingTabCount(line) !=
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

				Tuple tuple = getJavaTermTuple(line, _content, index);

				if (tuple == null) {
					return Collections.emptySet();
				}

				int javaTermEndPosition = 0;

				if (lastCommentOrAnnotationPos == -1) {
					javaTermEndPosition = index;
				}
				else {
					javaTermEndPosition = lastCommentOrAnnotationPos;
				}

				if ((javaTermStartPosition != -1) &&
					(javaTermEndPosition < _content.length())) {

					JavaTerm javaTerm = getJavaTerm(
						javaTermName, javaTermType, javaTermLineCount,
						javaTermStartPosition, javaTermEndPosition);

					if (javaTerm == null) {
						return Collections.emptySet();
					}

					if (javaTermType == JavaTerm.TYPE_STATIC_BLOCK) {
						staticBlocks.add(javaTerm);
					}
					else {
						javaTerms.add(javaTerm);
					}
				}

				javaTermLineCount = lineCount;
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
					 !BaseSourceProcessor.isExcludedFile(
						 _javaTermAccessLevelModifierExclusionFiles,
						 _absolutePath, lineCount)) {

				Matcher matcher = _classPattern.matcher(_content);

				if (matcher.find()) {
					String insideClass = _content.substring(matcher.end());

					if (insideClass.contains(line)) {
						BaseSourceProcessor.processErrorMessage(
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
				_content.lastIndexOf(StringPool.CLOSE_CURLY_BRACE) -
					_indent.length() + 1;

			JavaTerm javaTerm = getJavaTerm(
				javaTermName, javaTermType, javaTermLineCount,
				javaTermStartPosition, javaTermEndPosition);

			if (javaTerm == null) {
				return Collections.emptySet();
			}

			if (javaTermType == JavaTerm.TYPE_STATIC_BLOCK) {
				staticBlocks.add(javaTerm);
			}
			else {
				javaTerms.add(javaTerm);
			}
		}

		return addStaticBlocks(javaTerms, staticBlocks);
	}

	protected Tuple getJavaTermTuple(String line, String accessModifier) {
		if (!line.startsWith(_indent + accessModifier + StringPool.SPACE)) {
			return null;
		}

		int x = line.indexOf(StringPool.EQUAL);
		int y = line.indexOf(StringPool.OPEN_PARENTHESIS);

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
				line.substring(0, y), StringPool.SPACE);

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

	protected Tuple getJavaTermTuple(String line, String content, int index) {
		int posStartNextLine = index;

		while (!line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			   !line.endsWith(StringPool.SEMICOLON)) {

			posStartNextLine =
				content.indexOf(StringPool.NEW_LINE, posStartNextLine) + 1;

			int posEndNextline = content.indexOf(
				StringPool.NEW_LINE, posStartNextLine);

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

		line = StringUtil.replace(line, " synchronized " , StringPool.SPACE);

		for (String accessModifier : _ACCESS_MODIFIERS) {
			Tuple tuple = getJavaTermTuple(line, accessModifier);

			if (tuple != null) {
				return tuple;
			}
		}

		if (line.startsWith(_indent + "static {")) {
			return new Tuple("static", JavaTerm.TYPE_STATIC_BLOCK);
		}

		return null;
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
		int x = line.indexOf(StringPool.EQUAL);
		int y = line.lastIndexOf(StringPool.SPACE);

		if (x != -1) {
			line = line.substring(0, x);
			line = StringUtil.trim(line);

			y = line.lastIndexOf(StringPool.SPACE);

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

	protected boolean isFinalableField(
		JavaTerm javaTerm, String javaTermClassName, Pattern pattern,
		boolean checkOuterClass) {

		if (checkOuterClass && (_outerClass != null)) {
			return _outerClass.isFinalableField(
				javaTerm, javaTermClassName, pattern, true);
		}

		for (JavaTerm curJavaTerm : _javaTerms) {
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

			content = content.substring(content.indexOf("\n") + 1);
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
		List<String> javaTermSortExclusionFiles) {

		if (previousJavaTerm == null) {
			return;
		}

		String javaTermName = javaTerm.getName();

		if (BaseSourceProcessor.isExcludedFile(
				javaTermSortExclusionFiles, _absolutePath, -1, javaTermName)) {

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
			_content = StringUtil.replaceFirst(
				_content, "\n" + javaTerm.getContent(),
				"\n" + previousJavaTerm.getContent());
			_content = StringUtil.replaceLast(
				_content, "\n" + previousJavaTerm.getContent(),
				"\n" + javaTerm.getContent());
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

	private String _absolutePath;
	private Pattern _camelCasePattern = Pattern.compile("([a-z])([A-Z0-9])");
	private Pattern _classPattern = Pattern.compile(
		"(private |protected |public )(static )*class ([\\s\\S]*?) \\{\n");
	private int _constructorCount = 0;
	private String _content;
	private File _file;
	private String _fileName;
	private String _indent;
	private List<JavaClass> _innerClasses = new ArrayList<JavaClass>();
	private List<String> _javaTermAccessLevelModifierExclusionFiles;
	private Set<JavaTerm> _javaTerms;
	private int _lineCount;
	private String _name;
	private JavaClass _outerClass;
	private String _packagePath;

}