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

package com.liferay.nested.portlets.web.internal.display.context;

import com.liferay.nested.portlets.web.configuration.NestedPortletsPortletInstanceConfiguration;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.plugin.PluginUtil;

import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Juergen Kappler
 */
public class NestedPortletsDisplayContext {

	public NestedPortletsDisplayContext(HttpServletRequest request)
		throws ConfigurationException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_nestedPortletsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				NestedPortletsPortletInstanceConfiguration.class);
	}

	/**
	 * @see com.liferay.portal.util.PortalImpl#getOriginalServletRequest
	 */
	public HttpServletRequest getLastForwardRequest() {
		HttpServletRequest currentRequest = _request;
		HttpServletRequestWrapper currentRequestWrapper = null;
		HttpServletRequest originalRequest = null;
		HttpServletRequest nextRequest = null;

		while (currentRequest instanceof HttpServletRequestWrapper) {
			if (currentRequest instanceof
					PersistentHttpServletRequestWrapper) {

				PersistentHttpServletRequestWrapper
					persistentHttpServletRequestWrapper =
						(PersistentHttpServletRequestWrapper)currentRequest;

				persistentHttpServletRequestWrapper =
					persistentHttpServletRequestWrapper.clone();

				if (originalRequest == null) {
					originalRequest =
						persistentHttpServletRequestWrapper.clone();
				}

				if (currentRequestWrapper != null) {
					currentRequestWrapper.setRequest(
						persistentHttpServletRequestWrapper);
				}

				currentRequestWrapper = persistentHttpServletRequestWrapper;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentRequest;

			nextRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();

			if ((currentRequest.getDispatcherType() ==
					DispatcherType.FORWARD) &&
				(nextRequest.getDispatcherType() == DispatcherType.REQUEST)) {

				break;
			}

			currentRequest = nextRequest;
		}

		if ((currentRequestWrapper != null) &&
			!_isVirtualHostRequest(nextRequest)) {

			currentRequestWrapper.setRequest(currentRequest);
		}

		if (originalRequest != null) {
			return originalRequest;
		}

		return currentRequest;
	}

	public String getLayoutTemplateId() {
		if (_layoutTemplateId != null) {
			return _layoutTemplateId;
		}

		_layoutTemplateId =
			_nestedPortletsPortletInstanceConfiguration.layoutTemplateId();

		return _layoutTemplateId;
	}

	public List<LayoutTemplate> getLayoutTemplates() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<LayoutTemplate> layoutTemplates =
			LayoutTemplateLocalServiceUtil.getLayoutTemplates(
				themeDisplay.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(
			layoutTemplates, themeDisplay.getUser());

		final List<String> unSupportedLayoutTemplateIds =
			getUnsupportedLayoutTemplateIds();

		return ListUtil.filter(
			layoutTemplates,
			new PredicateFilter<LayoutTemplate>() {

				@Override
				public boolean filter(LayoutTemplate layoutTemplate) {
					return !unSupportedLayoutTemplateIds.contains(
						layoutTemplate.getLayoutTemplateId());
				}

			});
	}

	protected List<String> getUnsupportedLayoutTemplateIds() {
		return ListUtil.fromArray(
			_nestedPortletsPortletInstanceConfiguration.
				layoutTemplatesUnsupported());
	}

	private boolean _isVirtualHostRequest(HttpServletRequest request) {
		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if ((layoutSet != null) &&
			Validator.isNotNull(layoutSet.getVirtualHostname())) {

			return true;
		}

		return false;
	}

	private String _layoutTemplateId;
	private final NestedPortletsPortletInstanceConfiguration
		_nestedPortletsPortletInstanceConfiguration;
	private final HttpServletRequest _request;

}