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

package com.liferay.layout.seo.web.internal.context.contributor;

import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.web.internal.util.TitleProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextContributor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"lang.type=" + TemplateConstants.LANG_TYPE_FTL,
		"type=" + TemplateContextContributor.TYPE_GLOBAL
	},
	service = TemplateContextContributor.class
)
public class HTMLTitleTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		try {
			contextObjects.put(
				"htmlTitle", _titleProvider.getTitle(httpServletRequest));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to fill htmlTitle: " + portalException.getMessage(),
				portalException);
		}
	}

	@Activate
	protected void activate() {
		_titleProvider = new TitleProvider(_layoutSEOLinkManager);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HTMLTitleTemplateContextContributor.class);

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	private TitleProvider _titleProvider;

}