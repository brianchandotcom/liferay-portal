/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import jargs.gnu.CmdLineParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * Verifies required Javadoc tags and comments printing messages regarding any
 * that are missing.
 *
 * @author James Hinkey
 */
public class JavadocVerifier {

	/**
	 * Invokes the JavadocVerifier on a single Java file.
	 *
	 * @param args Should be -Dfile=SomeJavaFileName (omit file path and
	 * suffix)
	 */
	public static void main(String[] args) {
		try {
			new JavadocVerifier(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifies a single Java file.
	 *
	 * @param args Should be -Dfile=SomeJavaFileName (omit file path and
	 * suffix)
	 * @throws Exception if an exception occurred
	 */
	public JavadocVerifier(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option fileOption = cmdLineParser.addStringOption(
			"file");

		cmdLineParser.parse(args);

		String file = (String)cmdLineParser.getOptionValue(fileOption);

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_basedir);
		ds.setExcludes(
			new String[] {"**\\classes\\**", "**\\portal-client\\**"});

		List<String> includes = new ArrayList<String>();

		if (Validator.isNotNull(file) && !file.startsWith("$")) {
			String[] fileArray = StringUtil.split(file, '/');

			for (String curFile : fileArray) {
				includes.add(
					"**\\" + StringUtil.replace(curFile, ".", "\\") +
					"\\**\\*.java");
				includes.add("**\\" + curFile + ".java");
			}
		}
		else {
			System.out.println("Must specify a file");
			return;
		}

		ds.setIncludes(includes.toArray(new String[includes.size()]));

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		if ((fileNames.length == 0) &&
			Validator.isNotNull(file) &&
			!file.startsWith("$")) {

			StringBuilder sb = new StringBuilder("File not found: ");

			sb.append(file);

			if (file.contains(".")) {
				sb.append(" Specify filename without package path or ");
				sb.append("file type suffix.");
			}

			System.out.println(sb.toString());
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(fileName, "\\", "/");

			_verifyJavadoc(_basedir, fileName);
		}
	}

	private void _checkClassEntity(JavaClass javaClass) {

		String comment = _getCDATA(javaClass);

		if (_isPublicEntity(javaClass)) {
			if (Validator.isNull(comment)) {
				System.out.println("missing class comment for " +
					javaClass.getName() + " line " +
					javaClass.getLineNumber());
			} else {
				_checkTodos(javaClass, comment);
			}
		}
	}

	private void _checkExceptions(JavaMethod javaMethod) throws Exception {

		Type[] exceptions = javaMethod.getExceptions();

		DocletTag[] throwsDocletTags = javaMethod.getTagsByName("throws");

		for (Type exception : exceptions) {
			if (_isPublicEntity(javaMethod)) {
				_checkNamedEntity(throwsDocletTags, javaMethod, "throws",
					exception.getJavaClass().getName());
			}
		}
	}

	private void _checkField(JavaField javaField)
		throws Exception {

		String comment = _getCDATA(javaField);

		if (_isPublicEntity(javaField) && Validator.isNull(comment)) {
			StringBuffer sb = new StringBuffer();
			sb.append("missing field comment for ");
			sb.append(javaField.getName());
			sb.append(" line ");
			sb.append(javaField.getLineNumber());
			System.out.println(sb.toString());
		}
	}

	private void _checkMethod(JavaMethod javaMethod)
		throws Exception {

		String comment = _getCDATA(javaMethod);

		if (_isPublicEntity(javaMethod)) {
			if (Validator.isNull(comment)) {
				StringBuffer sb = new StringBuffer();
				sb.append("missing method comment for ");
				sb.append(javaMethod.getName());
				sb.append(" line ");
				sb.append(javaMethod.getLineNumber());
				System.out.println(sb.toString());
			} else {
				_checkTodos(javaMethod, comment);
			}
		}

		_checkParams(javaMethod);
		_checkReturn(javaMethod);
		_checkExceptions(javaMethod);
	}

	private void _checkNamedEntity(
			DocletTag[] docletTags, AbstractJavaEntity parentEntity,
			String tagName, String entityName) {

		String value = null;

		for (DocletTag docletTag : docletTags) {
			String curValue = docletTag.getValue();

			if (!curValue.startsWith(entityName)) {
				continue;
			}
			else {
				value = curValue;

				break;
			}
		}

		if (Validator.isNull(value)) {
			StringBuffer sb = new StringBuffer();
			sb.append("missing ");
			sb.append(tagName);
			sb.append(" comment for ");
			sb.append(entityName);
			sb.append(" of ");
			sb.append(parentEntity.getName());
			sb.append(" line ");
			sb.append(parentEntity.getLineNumber());
			System.out.println(sb.toString());
		}
		else if (!_checkTodos(parentEntity, value)) {
			_checkPeriods(parentEntity, value);
		}
	}

	private void _checkParams(JavaMethod javaMethod) {

		JavaParameter[] javaParameters = javaMethod.getParameters();

		DocletTag[] paramDocletTags = javaMethod.getTagsByName("param");

		for (JavaParameter javaParameter : javaParameters) {
			if (_isPublicEntity(javaMethod)) {
				_checkNamedEntity(paramDocletTags,	javaMethod, "param",
					javaParameter.getName());
			}
		}
	}

	private boolean _checkPeriods(AbstractJavaEntity javaEntity,
			String comment) {

		if (comment.endsWith(".")) {
			System.out.println("\'.\' to remove at end of comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else {
			return false;
		}
	}

	private void _checkReturn(JavaMethod javaMethod)
		throws Exception {

		Type returns = javaMethod.getReturns();

		if ((returns == null) || returns.getValue().equals("void")) {
			return;
		}

		if (_isPublicEntity(javaMethod)) {
			DocletTag[] returnDocletTags = javaMethod.getTagsByName("return");

			if (returnDocletTags.length == 0 ||
				Validator.isNull(returnDocletTags[0].getValue())) {

				StringBuffer sb = new StringBuffer();
				sb.append("missing return comment for ");
				sb.append(javaMethod.getName());
				sb.append(" line ");
				sb.append(javaMethod.getLineNumber());
				System.out.println(sb.toString());
			}
			else if (!_checkTodos(javaMethod,
				returnDocletTags[0].getValue())) {

				_checkPeriods(javaMethod, returnDocletTags[0].getValue());
			}
		}
	}

	private boolean _checkTodos(AbstractJavaEntity javaEntity,
			String comment) {

		if (comment.contains("TODO")) {
			System.out.println("TODO in comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else if (comment.contains("something")) {
			System.out.println(
				"\"something\" to replace in comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else if (comment.contains("someParam")) {
			System.out.println(
				"\"someParam\" to replace in comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else if (comment.contains("?")) {
			System.out.println(
				"\'?\' to address in comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else if (comment.contains("[") || comment.contains("]")) {
			System.out.println(
				"\"[ ]\" to replace in comment for " +
				javaEntity.getName() + " line " +
				javaEntity.getLineNumber());
			return true;
		} else {
			return false;
		}
	}

	private String _getCDATA(AbstractJavaEntity abstractJavaEntity) {
		return _getCDATA(abstractJavaEntity.getComment());
	}

	private String _getCDATA(String cdata) {
		if (cdata == null) {
			return StringPool.BLANK;
		}

		cdata = cdata.replaceAll(
			"(?s)\\s*<(p|pre|[ou]l)>\\s*(.*?)\\s*</\\1>\\s*",
			"\n\n<$1>\n$2\n</$1>\n\n");
		cdata = cdata.replaceAll(
			"(?s)\\s*<li>\\s*(.*?)\\s*</li>\\s*", "\n<li>\n$1\n</li>\n");
		cdata = StringUtil.replace(cdata, "</li>\n\n<li>", "</li>\n<li>");
		cdata = cdata.replaceAll("\n\\s+\n", "\n\n");
		cdata = cdata.replaceAll(" +", " ");

		// Trim whitespace inside paragraph tags or in the first paragraph

		Pattern pattern = Pattern.compile(
			"(^.*?(?=\n\n|$)+|(?<=<p>\n).*?(?=\n</p>))", Pattern.DOTALL);

		Matcher matcher = pattern.matcher(cdata);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String trimmed = _trimMultilineText(matcher.group());

			// Escape dollar signs so they are not treated as replacement groups

			trimmed = trimmed.replaceAll("\\$", "\\\\\\$");

			matcher.appendReplacement(sb, trimmed);
		}

		matcher.appendTail(sb);

		cdata = sb.toString();

		return cdata.trim();
	}

	private String _getClassName(String fileName) {
		int pos = fileName.indexOf("src/");

		if (pos == -1) {
			pos = fileName.indexOf("test/");
		}

		if (pos == -1) {
			pos = fileName.indexOf("service/");
		}

		if (pos == -1) {
			throw new RuntimeException(fileName);
		}

		pos = fileName.indexOf("/", pos);

		String srcFile = fileName.substring(pos + 1, fileName.length());

		return StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");
	}

	private JavaClass _getJavaClass(String fileName, Reader reader)
		throws Exception {

		String className = _getClassName(fileName);

		JavaDocBuilder builder = new JavaDocBuilder();

		if (reader == null) {
			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}

			builder.addSource(file);
		}
		else {
			builder.addSource(reader);
		}

		return builder.getClassByName(className);
	}

	private boolean _isGenerated(String content) {
		if (content.contains("* @generated") || content.contains("$ANTLR")) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isPublicEntity(AbstractJavaEntity javaEntity) {
		boolean isPublic = false;

		String[] modifiers = javaEntity.getModifiers();
		if (modifiers != null) {
			for (int ii=0; ii<modifiers.length; ii++) {
				if (modifiers[ii].equals("public")) {
					isPublic = true;
					break;
				}
			}
		}
		return isPublic;
	}

	private String _trimMultilineText(String text) {
		String[] textArray = StringUtil.splitLines(text);

		for (int i = 0; i < textArray.length; i++) {
			textArray[i] = textArray[i].trim();
		}

		return StringUtil.merge(textArray, " ");
	}

	private void _verifyJavadoc(String baseDir, String fileName)
		throws Exception {

		FileInputStream fis = new FileInputStream(
			new File(baseDir + fileName));

		byte[] bytes = new byte[fis.available()];

		fis.read(bytes);

		fis.close();

		String originalContent = new String(bytes);

		if (fileName.endsWith("JavadocFormatter.java") ||
			fileName.endsWith("SourceFormatter.java") ||
			_isGenerated(originalContent)) {

			return;
		}

		JavaClass javaClass = _getJavaClass(
			fileName, new UnsyncStringReader(originalContent));

		_checkClassEntity(javaClass);

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			_checkMethod(javaMethod);
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			_checkField(javaField);
		}
	}

	private String _basedir = "./";

}