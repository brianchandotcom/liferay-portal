/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.tools.comparator.JavaMethodComparator;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class CopyInterfaceBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new CopyInterfaceBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CopyInterfaceBuilder(String parentDir, String srcFile) {
		try {
			_copyInterface(parentDir, srcFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _copyInterface(String parentDir, String srcFile)
		throws IOException {

		JavaClass javaClass = _getJavaClass(parentDir, srcFile);

		JavaMethod[] methods = javaClass.getMethods();

		Arrays.sort(methods, new JavaMethodComparator());

		StringBundler sb = new StringBundler();

		// Package

		sb.append("package ");
		sb.append(javaClass.getPackage().getName());
		sb.append(";");

		// Imports

		sb.append("[$IMPORTS$]");

		// Class declaration

		sb.append("public class Copy");
		sb.append(javaClass.getName());
		sb.append(" implements ");
		sb.append(javaClass.getName());
		sb.append(" {");

		String varName = "_" + TextFormatter.format(
			javaClass.getName(), TextFormatter.I);

		// Methods

		Set<String> imports = new TreeSet<String>();

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (javaMethod.isPublic()) {
				String returnValueName = javaMethod.getReturns().getValue();

				imports.add(returnValueName);

				sb.append("public ");
				sb.append(javaMethod.getReturns().getJavaClass().getName());
				sb.append(_getDimensions(javaMethod.getReturns()));
				sb.append(" ");
				sb.append(methodName);
				sb.append("(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sb.append(javaParameter.getType().getJavaClass().getName());
					sb.append(_getDimensions(javaParameter.getType()));
					sb.append(" ");
					sb.append(javaParameter.getName());

					imports.add(javaParameter.getType().getValue());

					sb.append(", ");
				}

				if (parameters.length > 0) {
					sb.setIndex(sb.index() - 1);
				}

				sb.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set<String> newExceptions = new LinkedHashSet<String>();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getJavaClass().getName());

					imports.add(thrownException.getValue());
				}

				if (newExceptions.size() > 0) {
					sb.append(" throws ");

					for (String newException : newExceptions) {
						sb.append(newException);
						sb.append(", ");
					}

					sb.setIndex(sb.index() - 1);
				}

				sb.append("{");

				if (!returnValueName.equals("void")) {
					sb.append("return ");
				}

				sb.append(varName);
				sb.append(".");
				sb.append(methodName);
				sb.append("(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sb.append(javaParameter.getName());
					sb.append(", ");
				}

				if (parameters.length > 0) {
					sb.setIndex(sb.index() - 1);
				}

				sb.append(");");
				sb.append("}");
			}
		}

		// Fields

		sb.append("private ");
		sb.append(javaClass.getName());
		sb.append(" ");
		sb.append(varName);
		sb.append(";");

		// Class close brace

		sb.append("}");

		// Imports

		String content = sb.toString();

		sb = new StringBundler(imports.size() * 3);

		for (String importClass : imports) {
			if (!importClass.equals("boolean") && !importClass.equals("double") && !importClass.equals("int") && !importClass.equals("long") && !importClass.equals("short") && !importClass.equals("void")) {
				sb.append("import ");
				sb.append(importClass);
				sb.append(";");
			}
		}

		content = StringUtil.replace(content, "[$IMPORTS$]", sb.toString());

		// Write file

		File file = new File(parentDir + "/" + StringUtil.replace(javaClass.getPackage().getName(), ".", "/") + "/Copy" + javaClass.getName() + ".java");

		ServiceBuilder.writeFile(file, content);
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private JavaClass _getJavaClass(String parentDir, String srcFile)
		throws IOException {

		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(new File(parentDir + "/" + srcFile));

		return builder.getClassByName(className);
	}

}