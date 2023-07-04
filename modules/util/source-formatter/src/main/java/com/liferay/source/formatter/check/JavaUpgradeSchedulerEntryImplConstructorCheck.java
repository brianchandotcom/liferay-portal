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
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeSchedulerEntryImplConstructorCheck
	extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (!extendedClassNames.contains("SchedulerEntryImpl")) {
			return javaTerm.getContent();
		}

		return _formatConstructor((JavaConstructor)javaTerm);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR};
	}

	private String _formatConstructor(JavaConstructor javaConstructor) {
		String content = javaConstructor.getContent();

		if (!content.contains("super();")) {
			return content;
		}

		String newSuperMethodCall = "super(\"\", null, \"\");";

		JavaSignature javaSignature = javaConstructor.getSignature();

		for (JavaParameter javaParameter : javaSignature.getParameters()) {
			if (Objects.equals(
					javaParameter.getParameterType(), "SchedulerEntryImpl")) {

				newSuperMethodCall = _getNewSuperImplementation(
					javaParameter.getParameterName());

				break;
			}
		}

		return StringUtil.replace(content, "super();", newSuperMethodCall);
	}

	private String _getNewSuperImplementation(String parameterName) {
		StringBundler sb = new StringBundler(7);

		sb.append("super(");
		sb.append(parameterName);
		sb.append(".getEventListenerClass(), ");
		sb.append(parameterName);
		sb.append(".getTrigger(), ");
		sb.append(parameterName);
		sb.append(".getDescription());");

		return sb.toString();
	}

}