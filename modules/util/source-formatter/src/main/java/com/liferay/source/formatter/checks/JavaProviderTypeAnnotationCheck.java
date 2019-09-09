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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaProviderTypeAnnotationCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (!javaTerm.hasAnnotation("ProviderType")) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (!javaClass.isInterface() && !javaClass.isAbstract()) {
			return StringUtil.replaceFirst(
				javaTerm.getContent(), "@ProviderType\n", StringPool.BLANK);
		}

		if (javaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		List<String> exportPackages = BNDSourceUtil.getDefinitionValues(
			bndSettings.getContent(), "Export-Package");

		String packageName = javaClass.getPackageName();

		if (exportPackages.contains(packageName)) {
			return javaTerm.getContent();
		}

		int x = -1;

		while (true) {
			x = packageName.indexOf(StringPool.PERIOD, x + 1);

			if (x == -1) {
				return StringUtil.replaceFirst(
					javaTerm.getContent(), "@ProviderType\n", StringPool.BLANK);
			}

			if (exportPackages.contains(
					packageName.substring(0, x + 1) + StringPool.STAR)) {

				return javaTerm.getContent();
			}
		}
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}