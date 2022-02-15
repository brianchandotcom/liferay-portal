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

import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionsSearchFacetDisplayContext
	implements Serializable {

	public CPSpecificationOption getCPSpecificationOption(String fieldName) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest),
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName));
	}

	public String getCPSpecificationOptionTitle(String fieldName) {
		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getTitle(_locale);
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

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setTermDisplayContexts(
		List<CPSpecificationOptionsSearchFacetTermDisplayContext>
			assetCPSpecificationOptionsSearchFacetTermDisplayContext) {

		_cpSpecificationOptionsSearchFacetTermDisplayContext =
			assetCPSpecificationOptionsSearchFacetTermDisplayContext;
	}

	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private List<CPSpecificationOptionsSearchFacetTermDisplayContext>
		_cpSpecificationOptionsSearchFacetTermDisplayContext;
	private Facet _facet;
	private Locale _locale;
	private String _paginationStartParameterName;
	private String _parameterName;
	private String _parameterValue;
	private RenderRequest _renderRequest;

}