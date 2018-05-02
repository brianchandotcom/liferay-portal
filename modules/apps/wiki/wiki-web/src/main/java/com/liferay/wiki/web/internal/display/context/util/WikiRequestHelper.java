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

package com.liferay.wiki.web.internal.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseStrutsRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.web.configuration.WikiPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class WikiRequestHelper extends BaseStrutsRequestHelper {

	public WikiRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public long getCategoryId() {
		if (_categoryId == null) {
			_categoryId = ParamUtil.getLong(getRequest(), "categoryId", 0);
		}

		return _categoryId;
	}

	public WikiGroupServiceOverriddenConfiguration
		getWikiGroupServiceOverriddenConfiguration() {

		try {
			if (_wikiGroupServiceOverriddenConfiguration == null) {
				String portletResource = getPortletResource();

				if (Validator.isNotNull(portletResource)) {
					_wikiGroupServiceOverriddenConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							WikiGroupServiceOverriddenConfiguration.class,
							new ParameterMapSettingsLocator(
								getRequest().getParameterMap(),
								new GroupServiceSettingsLocator(
									getSiteGroupId(),
									WikiConstants.SERVICE_NAME)));
				}
				else {
					_wikiGroupServiceOverriddenConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							WikiGroupServiceOverriddenConfiguration.class,
							new GroupServiceSettingsLocator(
								getSiteGroupId(), WikiConstants.SERVICE_NAME));
				}
			}

			return _wikiGroupServiceOverriddenConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public WikiPage getWikiPage() {
		if (_wikiPage == null) {
			HttpServletRequest request = getRequest();

			_wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
		}

		return _wikiPage;
	}

	public WikiPortletInstanceConfiguration
		getWikiPortletInstanceConfiguration() {

		try {
			if (_wikiPortletInstanceConfiguration == null) {
				String portletResource = getPortletResource();

				if (Validator.isNotNull(portletResource)) {
					_wikiPortletInstanceConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							WikiPortletInstanceConfiguration.class,
							new ParameterMapSettingsLocator(
								getRequest().getParameterMap(),
								new PortletInstanceSettingsLocator(
									getLayout(), getResourcePortletId())));
				}
				else {
					_wikiPortletInstanceConfiguration =
						ConfigurationProviderUtil.getConfiguration(
							WikiPortletInstanceConfiguration.class,
							new PortletInstanceSettingsLocator(
								getLayout(), getPortletId()));
				}
			}

			return _wikiPortletInstanceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private Long _categoryId;
	private WikiGroupServiceOverriddenConfiguration
		_wikiGroupServiceOverriddenConfiguration;
	private WikiPage _wikiPage;
	private WikiPortletInstanceConfiguration _wikiPortletInstanceConfiguration;

}