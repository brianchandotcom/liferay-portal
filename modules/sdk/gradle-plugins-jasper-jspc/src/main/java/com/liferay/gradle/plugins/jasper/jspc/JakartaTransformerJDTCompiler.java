/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.jasper.jspc;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.Map;

import org.apache.jasper.compiler.JDTCompiler;
import org.apache.jasper.compiler.SmapStratum;

/**
 * @author Shuyang Zhou
 */
public class JakartaTransformerJDTCompiler extends JDTCompiler {

	@Override
	protected Map<String, SmapStratum> generateJava() throws Exception {
		Map<String, SmapStratum> smaps = super.generateJava();

		File javaFile = new File(ctxt.getServletJavaFileName());

		String content = new String(
			Files.readAllBytes(javaFile.toPath()), "UTF-8");

		String newContent = content;

		newContent = newContent.replace(
			"javax.activation", "jakarta.activation");
		newContent = newContent.replace("javax.el", "jakarta.el");
		newContent = newContent.replace("javax.mail", "jakarta.mail");
		newContent = newContent.replace("javax.servlet", "jakarta.servlet");

		if (!newContent.equals(content)) {
			Files.write(
				javaFile.toPath(), newContent.getBytes("UTF-8"),
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		}

		return smaps;
	}

}