/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class XMLWorkflowDefinitionFileNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.contains("\n<workflow-definition\n") &&
			!fileName.endsWith("workflow-definition.xml")) {

			addMessage(
				fileName,
				"The file name of workflow definition should end with " +
					"'workflow-definition.xml'");
		}

		return content;
	}

}