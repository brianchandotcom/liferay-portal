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

package com.liferay.questions.web.internal.layout.seo;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.questions.web.internal.configuration.QuestionsConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	configurationPid = "com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	enabled = false, property = "service.ranking:Integer=100",
	service = LayoutSEOLinkManager.class
)
public class QuestionsLayoutSEOLinkManagerImpl implements LayoutSEOLinkManager {

	@Override
	public LayoutSEOLink getCanonicalLayoutSEOLink(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		return _layoutSEOLinkManager.getCanonicalLayoutSEOLink(
			layout, locale, canonicalURL, alternateURLs);
	}

	@Override
	public String getFullPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, String companyName,
			Locale locale)
		throws PortalException {

		return _layoutSEOLinkManager.getFullPageTitle(
			layout, portletId, tilesTitle, titleListMergeable,
			subtitleListMergeable, companyName, locale);
	}

	@Override
	public List<LayoutSEOLink> getLocalizedLayoutSEOLinks(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String currentURL = serviceContext.getCurrentURL();

		if (currentURL.contains(
				_questionsConfiguration.historyRouterBasePath())) {

			return new ArrayList<>();
		}

		return _layoutSEOLinkManager.getLocalizedLayoutSEOLinks(
			layout, locale, canonicalURL, alternateURLs);
	}

	@Override
	public String getPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, Locale locale)
		throws PortalException {

		return _layoutSEOLinkManager.getPageTitle(
			layout, portletId, tilesTitle, titleListMergeable,
			subtitleListMergeable, locale);
	}

	@Override
	public String getPageTitleSuffix(Layout layout, String companyName)
		throws PortalException {

		return _layoutSEOLinkManager.getPageTitleSuffix(layout, companyName);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * OpenGraphConfiguration#isOpenGraphEnabled(Group)}
	 */
	@Deprecated
	@Override
	public boolean isOpenGraphEnabled(Layout layout) throws PortalException {
		return _layoutSEOLinkManager.isOpenGraphEnabled(layout);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_questionsConfiguration = ConfigurableUtil.createConfigurable(
			QuestionsConfiguration.class, properties);
	}

	@Reference(
		target = "(component.name=com.liferay.layout.seo.internal.LayoutSEOLinkManagerImpl)"
	)
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	private QuestionsConfiguration _questionsConfiguration;

}