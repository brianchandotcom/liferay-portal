/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 * @author Zsigmond Rab
 */
public class EARBuilder {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		if (args.length == 3) {
			new EARBuilder(args[0], StringUtil.split(args[1]), args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public EARBuilder(
		String originalApplicationXML, String[] pluginFileNames,
		String portalContextPath) {

		try {
			Document document = UnsecureSAXReaderUtil.read(
				new File(originalApplicationXML));

			Element rootElement = document.getRootElement();

			for (String pluginFileName : pluginFileNames) {
				Element moduleElement = rootElement.addElement("module");

				Element webElement = moduleElement.addElement("web");

				Element webURIElement = webElement.addElement("web-uri");

				webURIElement.addText(pluginFileName);

				Element contextRootElement = webElement.addElement(
					"context-root");

				contextRootElement.addText(
					_getContextRoot(pluginFileName, portalContextPath));
			}

			FileUtil.write(
				originalApplicationXML, document.formattedString(), true);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private String _getContextRoot(
		String pluginFileName, String portalContextPath) {

		String contextRoot = pluginFileName;

		int pos = contextRoot.lastIndexOf(".war");

		if (pos != -1) {
			contextRoot = contextRoot.substring(0, pos);
		}

		if (contextRoot.equals("liferay-portal")) {
			contextRoot = portalContextPath;

			if (contextRoot.equals(StringPool.SLASH)) {
				contextRoot = StringPool.BLANK;
			}
			else if (contextRoot.startsWith(StringPool.SLASH)) {
				contextRoot = contextRoot.substring(1);
			}
		}

		return StringPool.SLASH.concat(contextRoot);
	}

	private static final Log _log = LogFactoryUtil.getLog(EARBuilder.class);

}