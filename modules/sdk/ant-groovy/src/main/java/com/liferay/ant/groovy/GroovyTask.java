/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.groovy;

import groovy.lang.GroovyClassLoader;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;

/**
 * @author Kenji Heigel
 */
public class GroovyTask extends Task {

	public void addText(String text) {
		_text = text;
	}

	@Override
	public void execute() throws BuildException {
		ClassLoader classLoader = _getClassLoader();

		CompilerConfiguration compilerConfiguration =
			new CompilerConfiguration();

		compilerConfiguration.setSourceEncoding("UTF-8");

		String className =
			"GroovyScript_" + _classNameCounter.getAndIncrement();

		WrappedSource wrappedSource = _getWrappedSource(_text, className);

		try (GroovyClassLoader groovyClassLoader = new GroovyClassLoader(
				classLoader, compilerConfiguration)) {

			if (_compileOnly) {
				groovyClassLoader.parseClass(
					wrappedSource.getSource(), className + ".groovy");

				return;
			}

			Class<?> groovyScriptClass = groovyClassLoader.parseClass(
				wrappedSource.getSource(), className + ".groovy");

			Field projectField = groovyScriptClass.getDeclaredField("project");

			projectField.setAccessible(true);
			projectField.set(null, getProject());

			Field groovyMapField = groovyScriptClass.getDeclaredField(
				"groovyMap");

			groovyMapField.setAccessible(true);
			groovyMapField.set(null, _getMap());

			Method executeMethod = groovyScriptClass.getMethod("_execute");

			executeMethod.invoke(null);
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable throwable = invocationTargetException.getCause();

			if (throwable instanceof BuildException) {
				throw (BuildException)throwable;
			}

			throw new BuildException(
				(throwable != null) ? throwable : invocationTargetException);
		}
		catch (IOException | ReflectiveOperationException exception) {
			throw new BuildException(exception);
		}
		catch (MultipleCompilationErrorsException
					multipleCompilationErrorsException) {

			String message = _fixLineNumbers(
				multipleCompilationErrorsException.getMessage(),
				wrappedSource.getHeaderLineCount());

			throw new BuildException(
				"Groovy compilation failed:\n" + message,
				multipleCompilationErrorsException);
		}
	}

	public void setClasspathRef(String classpathRef) {
		_classpathRef = classpathRef;
	}

	public void setCompileOnly(boolean compileOnly) {
		_compileOnly = compileOnly;
	}

	public void setMapId(String mapId) {
		_mapId = mapId;
	}

	private int _appendClassBlock(
		String[] lines, StringBuilder sb, int startIndex) {

		int depth = 0;
		boolean opened = false;

		int index = startIndex;

		while (index < lines.length) {
			String line = lines[index];

			if (index == startIndex) {
				sb.append(_formatClassLine(line));
			}
			else {
				sb.append(line);
			}

			sb.append('\n');

			depth += _getBraceDelta(line);

			if (depth > 0) {
				opened = true;
			}

			if (opened && (depth == 0)) {
				return index + 1;
			}

			index++;
		}

		return index;
	}

	private int _appendMethodBlock(
		String[] lines, StringBuilder sb, int startIndex) {

		int depth = 0;
		boolean opened = false;

		int index = startIndex;

		while (index < lines.length) {
			String line = lines[index];

			if (index == startIndex) {
				sb.append(_formatMethodLine(line));
			}
			else {
				sb.append(line);
			}

			sb.append('\n');

			depth += _getBraceDelta(line);

			if (depth > 0) {
				opened = true;
			}

			if (opened && (depth == 0)) {
				return index + 1;
			}

			index++;
		}

		return index;
	}

	private String _fixLineNumbers(String message, int offset) {
		if ((message == null) || (offset <= 0)) {
			return message;
		}

		StringBuilder sb = new StringBuilder(message.length());

		int i = 0;

		while (i < message.length()) {
			char c = message.charAt(i);

			if ((c == '@') && ((i + 9) < message.length()) &&
				message.regionMatches(i, "@ line ", 0, 7)) {

				int start = i + 7;

				int end = start;

				while ((end < message.length()) &&
					   Character.isDigit(message.charAt(end))) {

					end++;
				}

				if (end > start) {
					int lineNumber = Integer.parseInt(
						message.substring(start, end));

					sb.append("@ line ");
					sb.append(Math.max(1, lineNumber - offset));

					i = end;

					continue;
				}
			}

			sb.append(c);

			i++;
		}

		return sb.toString();
	}

	private String _formatClassLine(String line) {
		int index = 0;

		while ((index < line.length()) &&
			   Character.isWhitespace(line.charAt(index))) {

			index++;
		}

		String whitespace = line.substring(0, index);

		String content = line.substring(index);

		if (content.startsWith("public static ")) {
			return line;
		}

		if (content.startsWith("public ")) {
			return whitespace + "static " +
				content.substring("public ".length());
		}

		if (content.startsWith("static ")) {
			return line;
		}

		if (content.startsWith("class ")) {
			return whitespace + "static " + content;
		}

		return line;
	}

	private String _formatMethodLine(String line) {
		int index = 0;

		while ((index < line.length()) &&
			   Character.isWhitespace(line.charAt(index))) {

			index++;
		}

		String whitespace = line.substring(0, index);

		String content = line.substring(index);

		if (content.startsWith("public static ") ||
			content.startsWith("private static ") ||
			content.startsWith("protected static ")) {

			return whitespace + content.substring("public ".length());
		}

		if (content.startsWith("public ")) {
			return whitespace + "static " +
				content.substring("public ".length());
		}

		if (content.startsWith("private ")) {
			return whitespace + "private static " +
				content.substring("private ".length());
		}

		if (content.startsWith("protected ")) {
			return whitespace + "protected static " +
				content.substring("protected ".length());
		}

		if (content.startsWith("static ")) {
			return line;
		}

		return whitespace + "static " + content;
	}

	private int _getBraceDelta(String line) {
		int delta = 0;

		boolean inDoubleQuote = false;
		boolean inSingleQuote = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (inDoubleQuote) {
				if (c == '\\') {
					i++;

					continue;
				}

				if (c == '"') {
					inDoubleQuote = false;
				}

				continue;
			}

			if (inSingleQuote) {
				if (c == '\\') {
					i++;

					continue;
				}

				if (c == '\'') {
					inSingleQuote = false;
				}

				continue;
			}

			if ((c == '/') && ((i + 1) < line.length()) &&
				(line.charAt(i + 1) == '/')) {

				break;
			}

			if (c == '"') {
				inDoubleQuote = true;
			}

			if (c == '\'') {
				inSingleQuote = true;
			}

			if (c == '{') {
				delta++;
			}

			if (c == '}') {
				delta--;
			}
		}

		return delta;
	}

	private ClassLoader _getClassLoader() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		if (_classpathRef == null) {
			return classLoader;
		}

		Project project = getProject();

		Path path = project.getReference(_classpathRef);

		if (path == null) {
			throw new BuildException(
				"Invalid Groovy class path reference: " + _classpathRef);
		}

		return new AntClassLoader(classLoader, project, path, true);
	}

	private int _getLineCount(CharSequence charSequence) {
		int count = 0;

		for (int i = 0; i < charSequence.length(); i++) {
			if (charSequence.charAt(i) == '\n') {
				count++;
			}
		}

		return count;
	}

	private Map<String, Object> _getMap() {
		if (_mapId == null) {
			return new HashMap<>();
		}

		Map<String, Object> map = _maps.get(_mapId);

		if (map == null) {
			map = new HashMap<>();

			_maps.put(_mapId, map);
		}

		return map;
	}

	private WrappedSource _getWrappedSource(String body, String className) {
		if (body == null) {
			body = "";
		}

		Matcher matcher = _unescapedDollarPattern.matcher(body);

		body = matcher.replaceAll("\\\\\\$");

		String[] lines = body.split("\n", -1);

		StringBuilder bodySB = new StringBuilder();
		StringBuilder fieldsSB = new StringBuilder();
		StringBuilder importsSB = new StringBuilder();
		StringBuilder methodsSB = new StringBuilder();
		StringBuilder nestedClassesSB = new StringBuilder();

		int depth = 0;
		int index = 0;

		while (index < lines.length) {
			String line = lines[index];

			String trimmedLine = line.trim();

			if (depth == 0) {
				if (trimmedLine.startsWith("import ")) {
					importsSB.append(line);
					importsSB.append('\n');

					index++;

					continue;
				}

				Matcher classDeclarationMatcher =
					_classDeclarationPattern.matcher(trimmedLine);

				if (classDeclarationMatcher.matches()) {
					index = _appendClassBlock(lines, nestedClassesSB, index);

					continue;
				}

				Matcher methodSignatureMatcher =
					_methodSignaturePattern.matcher(trimmedLine);
				Matcher methodSignatureNoModifierMatcher =
					_methodSignatureNoModifierPattern.matcher(trimmedLine);

				if (methodSignatureMatcher.matches() ||
					methodSignatureNoModifierMatcher.matches()) {

					index = _appendMethodBlock(lines, methodsSB, index);

					continue;
				}

				String[] variableDeclaration =
					_matchTopLevelVariableDeclaration(trimmedLine);

				if (variableDeclaration != null) {
					fieldsSB.append("\tstatic ");
					fieldsSB.append(variableDeclaration[0]);
					fieldsSB.append(" ");
					fieldsSB.append(variableDeclaration[1]);
					fieldsSB.append(";\n");

					if (variableDeclaration[2] != null) {
						int leadingEnd = 0;

						while ((leadingEnd < line.length()) &&
							   Character.isWhitespace(
								   line.charAt(leadingEnd))) {

							leadingEnd++;
						}

						bodySB.append(line.substring(0, leadingEnd));
						bodySB.append(variableDeclaration[1]);
						bodySB.append(" ");
						bodySB.append(variableDeclaration[2]);
						bodySB.append('\n');
					}

					depth += _getBraceDelta(line);

					index++;

					continue;
				}
			}

			bodySB.append(line);
			bodySB.append('\n');

			depth += _getBraceDelta(line);

			index++;
		}

		StringBuilder sourceSB = new StringBuilder();

		sourceSB.append(importsSB);
		sourceSB.append("import org.apache.tools.ant.Project\n");
		sourceSB.append("import java.util.Map\n");
		sourceSB.append("\n");
		sourceSB.append("@groovy.transform.CompileStatic\n");
		sourceSB.append("class ");
		sourceSB.append(className);
		sourceSB.append(" {\n");
		sourceSB.append("\tstatic Project project\n");
		sourceSB.append("\tstatic Map<String, Object> groovyMap\n");

		if (fieldsSB.length() > 0) {
			sourceSB.append(fieldsSB);
		}

		sourceSB.append("\n");
		sourceSB.append("\tstatic void _execute() {\n");

		int headerLineCount = _getLineCount(sourceSB);

		sourceSB.append(bodySB);
		sourceSB.append("\t}\n");

		if (methodsSB.length() > 0) {
			sourceSB.append("\n");
			sourceSB.append(methodsSB);
		}

		if (nestedClassesSB.length() > 0) {
			sourceSB.append("\n");
			sourceSB.append(nestedClassesSB);
		}

		sourceSB.append("}\n");

		return new WrappedSource(headerLineCount, sourceSB.toString());
	}

	private String[] _matchTopLevelVariableDeclaration(String line) {
		if (line.isEmpty() || (line.charAt(0) == '/') ||
			(line.charAt(0) == '*')) {

			return null;
		}

		while (true) {
			boolean modifierRemoved = false;

			for (String modifier : _modifiers) {
				if (line.startsWith(modifier + " ")) {
					line = line.substring(modifier.length() + 1);

					int index = 0;

					while ((index < line.length()) &&
						   Character.isWhitespace(line.charAt(index))) {

						index++;
					}

					line = line.substring(index);

					modifierRemoved = true;

					break;
				}
			}

			if (!modifierRemoved) {
				break;
			}
		}

		if (line.isEmpty() ||
			(!Character.isUpperCase(line.charAt(0)) &&
			 !_startsWithPrimitiveType(line))) {

			return null;
		}

		Matcher matcher = _variableDeclarationPattern.matcher(line);

		if (!matcher.matches()) {
			return null;
		}

		String name = matcher.group(2);

		String type = matcher.group(1);

		type = type.trim();

		if (_reservedKeywords.contains(name) ||
			_reservedKeywords.contains(type)) {

			return null;
		}

		String initializer = matcher.group(3);

		if ((initializer != null) && !initializer.isEmpty()) {
			initializer = initializer.trim();
		}
		else {
			initializer = null;
		}

		return new String[] {type, name, initializer};
	}

	private boolean _startsWithPrimitiveType(String text) {
		for (String primitiveType : _primitiveTypes) {
			if (text.equals(primitiveType) ||
				text.startsWith(primitiveType + " ") ||
				text.startsWith(primitiveType + "[")) {

				return true;
			}
		}

		return false;
	}

	private static final Pattern _classDeclarationPattern = Pattern.compile(
		"^(private\\s+|protected\\s+|public\\s+|static\\s+)*class\\s+\\w+" +
			"(\\s+extends\\s+[\\w<>,\\.\\s]+?)?" +
				"(\\s+implements\\s+[\\w<>,\\.\\s]+?)?\\s*\\{?\\s*$");
	private static final AtomicLong _classNameCounter = new AtomicLong();
	private static final Map<String, Map<String, Object>> _maps =
		new HashMap<>();
	private static final Pattern _methodSignatureNoModifierPattern =
		Pattern.compile(
			"^([A-Z][\\w<>,\\[\\]\\s\\?]*|void)\\s+\\w+\\s*\\([^)]*\\)\\s*" +
				"(throws\\s[\\w\\s,\\.]+)?\\s*\\{?\\s*$");
	private static final Pattern _methodSignaturePattern = Pattern.compile(
		"^(private|protected|public|static)\\s+[\\w<>,\\[\\]\\s\\?]+\\s+\\w+" +
			"\\s*\\(.*");
	private static final List<String> _modifiers = Arrays.asList(
		"final", "private", "protected", "public", "static");
	private static final List<String> _primitiveTypes = Arrays.asList(
		"boolean", "byte", "char", "double", "float", "int", "long", "short");
	private static final Set<String> _reservedKeywords = new HashSet<>(
		Arrays.asList(
			"catch", "do", "else", "finally", "for", "if", "new", "return",
			"super", "switch", "this", "throw", "try", "while"));
	private static final Pattern _unescapedDollarPattern = Pattern.compile(
		"(?<!\\\\)\\$");
	private static final Pattern _variableDeclarationPattern = Pattern.compile(
		"^([A-Za-z][\\w]*(?:\\s*<[^>]*>)?(?:\\s*\\[\\s*\\])*)\\s+(\\w+)\\s*" +
			"(=.*?|;)?\\s*$");

	private String _classpathRef;
	private boolean _compileOnly;
	private String _mapId;
	private String _text;

	private static final class WrappedSource {

		public int getHeaderLineCount() {
			return _headerLineCount;
		}

		public String getSource() {
			return _source;
		}

		private WrappedSource(int headerLineCount, String source) {
			_headerLineCount = headerLineCount;
			_source = source;
		}

		private final int _headerLineCount;
		private final String _source;

	}

}