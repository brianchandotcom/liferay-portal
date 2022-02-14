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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.product.content.search.web.internal.configuration.CPSpecificationOptionFacetPortletInstanceConfiguration;
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionsSearchFacetDisplayContext
	implements Serializable {

	public CPSpecificationOptionsSearchFacetDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpSpecificationOptionFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPSpecificationOptionFacetPortletInstanceConfiguration.class);
	}

	public CPSpecificationOption getCPSpecificationOption(String fieldName)
		throws PortalException {

		String key =
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName);

		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest), key);
	}

	public CPSpecificationOptionFacetPortletInstanceConfiguration
		getCPSpecificationOptionFacetPortletInstanceConfiguration() {

		return _cpSpecificationOptionFacetPortletInstanceConfiguration;
	}

	public String getCPSpecificationOptionKey(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getKey();
	}

	public String getCPSpecificationOptionTitle(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getTitle(_locale);
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_cpSpecificationOptionFacetPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public Facet getFacet() {
		return _facet;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterValue() {
		return _parameterValue;
	}

	public List<CPSpecificationOptionsSearchFacetTermDisplayContext>
		getTermDisplayContexts() {

		return _cpSpecificationOptionsSearchFacetTermDisplayContext;
	}

	public boolean isCloud() {
		return _cloud;
	}

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setCloud(boolean cloud) {
		_cloud = cloud;
	}

	public void setCpSpecificationOptionLocalService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String paramValue) {
		_parameterValue = paramValue;
	}

	public void setParameterValues(List<String> parameterValues) {
		_parameterValues = parameterValues;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setTermDisplayContexts(
		List<CPSpecificationOptionsSearchFacetTermDisplayContext>
			assetCPSpecificationOptionsSearchFacetTermDisplayContext) {

		_cpSpecificationOptionsSearchFacetTermDisplayContext =
			assetCPSpecificationOptionsSearchFacetTermDisplayContext;
	}

	private boolean _cloud;
	private final CPSpecificationOptionFacetPortletInstanceConfiguration
		_cpSpecificationOptionFacetPortletInstanceConfiguration;
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private List<CPSpecificationOptionsSearchFacetTermDisplayContext>
		_cpSpecificationOptionsSearchFacetTermDisplayContext;
	private long _displayStyleGroupId;
	private Facet _facet;
	private final HttpServletRequest _httpServletRequest;
	private Locale _locale;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;
	private RenderRequest _renderRequest;

}