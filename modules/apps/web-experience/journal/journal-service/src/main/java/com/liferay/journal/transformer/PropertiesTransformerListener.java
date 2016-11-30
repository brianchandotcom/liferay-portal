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

package com.liferay.journal.transformer;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.xml.Document;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 4.0.0, replaced by {@link
 *             #com.liferay.journal.properties.trasnformer.listener.JournalPropertiesTransformerListener}
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = TransformerListener.class
)
@Deprecated
public class PropertiesTransformerListener extends BaseTransformerListener {

	@Override
	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		return super.onOutput(output, languageId, tokens);
	}

	@Override
	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens) {

		return super.onScript(script, document, languageId, tokens);
	}

	/**
	 * Replace the properties in a given string with their values fetched from
	 * the template GLOBAL-PROPERTIES.
	 *
	 * @return the processed string
	 */
	protected String replace(
		String s, String languageId, Map<String, String> tokens) {

		return null;
	}

}