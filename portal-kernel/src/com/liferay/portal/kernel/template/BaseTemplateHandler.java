/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public abstract class BaseTemplateHandler implements TemplateHandler {

	@Override
	public Map<String, Object> getCustomContextObjects() {
		return Collections.emptyMap();
	}

	@Override
	public List<Element> getDefaultTemplateElements() throws Exception {
		String templatesConfigPath = getTemplatesConfigPath();

		if (Validator.isNull(templatesConfigPath)) {
			return Collections.emptyList();
		}

		Class<?> clazz = getClass();

		String xml = StringUtil.read(
			clazz.getClassLoader(), templatesConfigPath, false);

		Document document = UnsecureSAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("template");
	}

	@Override
	public String getDefaultTemplateKey() {
		return null;
	}

	@Override
	public String[] getRestrictedVariables(String language) {
		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(language);

		if (templateManager != null) {
			return templateManager.getRestrictedVariables();
		}

		return new String[0];
	}

	@Override
	public String getTemplatesHelpContent(String language) {
		String content = StringPool.BLANK;

		try {
			Class<?> clazz = getClass();

			content = StringUtil.read(
				clazz.getClassLoader(), getTemplatesHelpPath(language));
		}
		catch (IOException ioException1) {
			try {
				content = StringUtil.read(
					PortalClassLoaderUtil.getClassLoader(),
					getTemplatesHelpPath(language));
			}
			catch (IOException ioException2) {
			}
		}

		return content;
	}

	@Override
	public String getTemplatesHelpPath(String language) {
		return PropsUtil.get(
			getTemplatesHelpPropertyKey(), new Filter(language));
	}

	@Override
	public String getTemplatesHelpPropertyKey() {
		return PropsKeys.PORTLET_DISPLAY_TEMPLATES_HELP;
	}

	protected String getTemplatesConfigPath() {
		return null;
	}

}