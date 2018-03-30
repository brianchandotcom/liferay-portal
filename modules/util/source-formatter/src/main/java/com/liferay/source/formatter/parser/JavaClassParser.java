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

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaClassParser {

	public static List<JavaClass> parseAnonymousClasses(String content)
		throws Exception {

		List<JavaClass> anonymousClasses = new ArrayList<>();

		Matcher matcher = _anonymousClassPattern.matcher(content);

		while (matcher.find()) {
			String s = content.substring(matcher.start(2), matcher.end());

			if (JavaSourceUtil.getLevel(s) != 0) {
				continue;
			}

			int x = matcher.start() + 1;
			int y = matcher.end();

			while (true) {
				String classContent = content.substring(x, y);

				if (JavaSourceUtil.getLevel(classContent, "{", "}") != 0) {
					y++;

					continue;
				}

				anonymousClasses.add(
					_parseJavaClass(
						StringPool.BLANK, classContent,
						JavaTerm.ACCESS_MODIFIER_PRIVATE, false, false));

				break;
			}
		}

		return anonymousClasses;
	}

	public static JavaClass parseJavaClass(String fileName, String content)
		throws Exception {

		String className = JavaSourceUtil.getClassName(fileName);

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"\n(public\\s+)?(abstract\\s+)?(final\\s+)?@?",
				"(class|enum|interface)\\s+", className,
				"([<|\\s][^\\{]*)\\{"));

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			throw new ParseException("Parsing error");
		}

		int x = content.lastIndexOf("\n\n", matcher.start() + 1);

		String classContent = content.substring(x + 2);

		boolean isAbstract = false;

		if (matcher.group(2) != null) {
			isAbstract = true;
		}

		JavaClass javaClass = _parseJavaClass(
			className, classContent, JavaTerm.ACCESS_MODIFIER_PUBLIC,
			isAbstract, false);

		javaClass.setPackageName(JavaSourceUtil.getPackageName(content));

		String[] importLines = StringUtil.splitLines(
			JavaImportsFormatter.getImports(content));

		for (String importLine : importLines) {
			if (Validator.isNotNull(importLine)) {
				javaClass.addImport(
					importLine.substring(7, importLine.length() - 1));
			}
		}

		return _parseExtendsImplements(
			javaClass, StringUtil.trim(matcher.group(5)));
	}

	private static String _getClassName(String line) {
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

	private static String _getConstructorOrMethodName(String line, int pos) {
		line = line.substring(0, pos);

		int x = line.lastIndexOf(CharPool.SPACE);

		return line.substring(x + 1);
	}

	private static JavaTerm _getJavaTerm(String javaTermContent, String indent)
		throws Exception {

		Pattern pattern = Pattern.compile(
			"(\n|^)" + indent +
				"(private|protected|public|static)[ \n].*?[{;]\n",
			Pattern.DOTALL);

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return null;
		}

		String s = javaTermContent.substring(matcher.end(1), matcher.end() - 1);

		s = StringUtil.replace(
			s, new String[] {"\t", "(\n", "\n", " synchronized "},
			new String[] {"", "(", " ", " "});

		for (String accessModifier : JavaTerm.ACCESS_MODIFIERS) {
			JavaTerm javaTerm = _getJavaTerm(
				javaTermContent, s, accessModifier);

			if (javaTerm != null) {
				return javaTerm;
			}
		}

		return null;
	}

	private static JavaTerm _getJavaTerm(
			String javaTermContent, String startLine, String accessModifier)
		throws Exception {

		if (startLine.startsWith("static {")) {
			return new JavaStaticBlock(javaTermContent);
		}

		if (!startLine.startsWith(accessModifier)) {
			return null;
		}

		boolean isAbstract = startLine.contains(" abstract ");
		boolean isStatic = startLine.contains(" static ");

		int x = startLine.indexOf(CharPool.EQUAL);
		int y = startLine.indexOf(CharPool.OPEN_PARENTHESIS);

		if (startLine.contains(" @interface ") ||
			startLine.contains(" class ") || startLine.contains(" enum ") ||
			startLine.contains(" interface ")) {

			return _parseJavaClass(
				_getClassName(startLine), javaTermContent, accessModifier,
				isAbstract, isStatic);
		}

		if (((x > 0) && ((y == -1) || (y > x))) ||
			(startLine.endsWith(StringPool.SEMICOLON) && (y == -1))) {

			return new JavaVariable(
				_getVariableName(startLine), javaTermContent, accessModifier,
				isAbstract, isStatic);
		}

		if (y == -1) {
			return null;
		}

		int spaceCount = StringUtil.count(
			startLine.substring(0, y), CharPool.SPACE);

		if (isStatic || (spaceCount > 1)) {
			return new JavaMethod(
				_getConstructorOrMethodName(startLine, y), javaTermContent,
				accessModifier, isAbstract, isStatic);
		}

		if (spaceCount == 1) {
			return new JavaConstructor(
				_getConstructorOrMethodName(startLine, y), javaTermContent,
				accessModifier, isAbstract, isStatic);
		}

		return null;
	}

	private static int _getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	private static String _getVariableName(String line) {
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

	private static JavaClass _parseExtendsImplements(
			JavaClass javaClass, String s)
		throws Exception {

		if (SourceUtil.getLevel(s, "<", ">") != 0) {
			throw new ParseException("Parsing error around class declaration");
		}

		outerLoop:
		while (true) {
			int x = s.indexOf("<");

			if (x == -1) {
				break;
			}

			int y = x;

			while (true) {
				y = s.indexOf(">", y + 1);

				if (SourceUtil.getLevel(s.substring(x, y + 1), "<", ">") == 0) {
					s = StringUtil.trim(s.substring(0, x) + s.substring(y + 1));

					continue outerLoop;
				}
			}
		}

		Matcher matcher = _implementsPattern.matcher(s);

		if (matcher.find()) {
			javaClass.addImplementedClassNames(
				StringUtil.split(s.substring(matcher.end())));

			s = StringUtil.trim(s.substring(0, matcher.start()));
		}

		if (s.startsWith("extends")) {
			javaClass.addExtendedClassNames(s.substring(7));
		}

		return javaClass;
	}

	private static JavaClass _parseJavaClass(
			String className, String classContent, String accessModifier,
			boolean isAbstract, boolean isStatic)
		throws Exception {

		JavaClass javaClass = new JavaClass(
			className, classContent, accessModifier, isAbstract, isStatic);

		String indent = SourceUtil.getIndent(classContent) + StringPool.TAB;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(classContent));

		String line = null;

		int javaTermStartPos = -1;
		int level = 0;
		int lineCount = 0;
		int metadataAnnotationLevel = 0;
		int metadataBlockCommentLevel = 0;

		boolean insideJavaTerm = false;
		boolean insideMetadataAnnotation = false;
		boolean insideMetadataBlockComment = false;
		boolean multiLineComment = false;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			if (!insideJavaTerm && line.startsWith(indent + "@")) {
				insideMetadataAnnotation = true;

				metadataAnnotationLevel = SourceUtil.getLevel(line);
			}
			else if (insideMetadataAnnotation) {
				if ((metadataAnnotationLevel == 0) &&
					Validator.isNotNull(line)) {

					insideMetadataAnnotation = false;
				}

				metadataAnnotationLevel += SourceUtil.getLevel(line);
			}

			if (!insideJavaTerm && line.startsWith(indent + "/*")) {
				insideMetadataBlockComment = true;

				metadataBlockCommentLevel = SourceUtil.getLevel(
					line, "/*", "*/");
			}
			else if (insideMetadataBlockComment) {
				if ((metadataBlockCommentLevel == 0) &&
					Validator.isNotNull(line)) {

					insideMetadataBlockComment = false;
				}

				metadataBlockCommentLevel += SourceUtil.getLevel(
					line, "/*", "*/");
			}

			if (!insideJavaTerm) {
				if (javaTermStartPos == -1) {
					if (line.matches(indent + "\\S+.*")) {
						javaTermStartPos = _getLineStartPos(
							classContent, lineCount);
					}
				}
				else if (Validator.isNull(line) && !insideMetadataAnnotation &&
						 !insideMetadataBlockComment) {

					javaTermStartPos = -1;
				}
			}

			if (line.matches("\\s*//.*")) {
				continue;
			}

			if (multiLineComment) {
				if (line.matches(".*\\*/")) {
					multiLineComment = false;
				}

				continue;
			}

			if (line.matches("\\s*/\\*.*")) {
				multiLineComment = true;

				continue;
			}

			level += SourceUtil.getLevel(line, "{", "}");

			if (line.matches(
					indent +
						"((private|protected|public)( .*|$)|static \\{)")) {

				insideJavaTerm = true;
				insideMetadataAnnotation = false;
				insideMetadataBlockComment = false;
			}

			if (insideJavaTerm && line.matches(".*[};]") && (level == 1)) {
				int nextLineStartPos = _getLineStartPos(
					classContent, lineCount + 1);

				String javaTermContent = classContent.substring(
					javaTermStartPos, nextLineStartPos);

				JavaTerm javaTerm = _getJavaTerm(javaTermContent, indent);

				if (javaTerm == null) {
					throw new ParseException(
						"Parsing error at line '" + StringUtil.trim(line) +
							"'");
				}

				javaClass.addChildJavaTerm(javaTerm);

				insideJavaTerm = false;
				insideMetadataAnnotation = false;
				insideMetadataBlockComment = false;

				javaTermStartPos = nextLineStartPos;
			}
		}

		return javaClass;
	}

	private static final Pattern _anonymousClassPattern = Pattern.compile(
		"\n\t+(\\S.* )?new ((.|\\(\n)*\\)) \\{\n\n");
	private static final Pattern _implementsPattern = Pattern.compile(
		"(\\A|\\s)implements\\s");

}