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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaUpgradeDropTableCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		if (!_isUpgradeJavaClass(javaClass)) {
			return javaClass.getContent();
		}

		String content = javaTerm.getContent();

		Matcher matcher1 = _runSqlPattern.matcher(content);

		while (matcher1.find()) {
			String runSqlMethodCall = JavaSourceUtil.getMethodCall(
				content, matcher1.start());

			Matcher matcher2 = _dropTablePattern.matcher(runSqlMethodCall);

			if (!matcher2.find()) {
				continue;
			}

			String template = String.format(
				"DROP_TABLE_IF_EXISTS(%s)", matcher2.group(1));

			return StringUtil.replaceFirst(
				content, matcher2.group(0), template, matcher2.start());
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _isUpgradeJavaClass(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (String extendedClassName : extendedClassNames) {
			if (extendedClassName.endsWith("UpgradeProcess")) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _dropTablePattern = Pattern.compile(
		"drop table if exists ([\\w,\\s]+)");
	private static final Pattern _runSqlPattern = Pattern.compile(
		"\\brunSQL\\(");

}