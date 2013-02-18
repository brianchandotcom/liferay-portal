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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.templateparser.BaseTemplateParser;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xsl.XSLTemplateResource;
import com.liferay.portal.xsl.XSLURIResolver;
import com.liferay.portlet.journal.util.JournalXSLURIResolver;

/**
 * @author Marcellus Tavares
 * @author Tina Tian
 */
public class XSLTemplateParser extends BaseTemplateParser {

	@Override
	protected String getErrorTemplateId() {
		return PropsValues.DYNAMIC_DATA_LISTS_ERROR_TEMPLATE_XSL;
	}

	@Override
	protected TemplateContext getTemplateContext() throws Exception {
		XSLURIResolver xslURIResolver = new JournalXSLURIResolver(
			getTokens(), getLanguageId());

		TemplateResource templateResource = new XSLTemplateResource(
			getTemplateId(), getScript(), xslURIResolver, getXML());

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_XSL, templateResource,
			getErrorTemplateResource(), TemplateContextType.EMPTY);
	}

}