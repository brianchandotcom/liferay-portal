/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;

/**
 * @author Tamyris Torres
 */
public class JavaUpgradeOnAfterUpdateParameterCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		String content = javaTerm.getContent();

		if (!_extendsBaseModelListener(javaTerm.getParentJavaClass())) {
			return content;
		}

		JavaMethod javaMethod = (JavaMethod)javaTerm;

		JavaSignature javaSignature = javaMethod.getSignature();

		List<JavaParameter> javaParameters = javaSignature.getParameters();

		if (!Objects.equals(javaMethod.getName(), "onAfterUpdate") ||
			!Objects.equals(javaSignature.getReturnType(), "") ||
			(javaParameters.size() != 1)) {

			return content;
		}

		return StringUtil.replace(
			content, JavaSourceUtil.getParameters(javaMethod.getContent()),
			_getNewParameters(javaParameters.get(0)));
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private boolean _extendsBaseModelListener(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.contains("BaseModelListener")) {
			return true;
		}

		return false;
	}

	private String _getNewParameters(JavaParameter parameter) {
		StringBundler sb = new StringBundler(7);

		sb.append(parameter.getParameterType());
		sb.append(" original");
		sb.append(
			StringUtil.upperCaseFirstLetter(parameter.getParameterName()));
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(parameter.getParameterType());
		sb.append(StringPool.SPACE);
		sb.append(parameter.getParameterName());

		return sb.toString();
	}

}