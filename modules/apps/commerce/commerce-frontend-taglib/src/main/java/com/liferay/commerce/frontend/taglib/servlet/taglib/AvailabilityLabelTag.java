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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 * @author Ivica Cardic
 */
public class AvailabilityLabelTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			CPSku cpSku = _cpContentHelper.getDefaultCPSku(_cpCatalogEntry);

			boolean hasChildCPDefinitions =
				_cpContentHelper.hasChildCPDefinitions(
					_cpCatalogEntry.getCPDefinitionId());

			if ((cpSku != null) && !hasChildCPDefinitions) {
				ProductSettingsModel productSettingsModel =
					_productHelper.getProductSettingsModel(
						cpSku.getCPInstanceId());

				if (productSettingsModel.isShowAvailabilityDot()) {
					JSONObject availabilityContentContributorValueJSONObject =
						_cpContentHelper.
							getAvailabilityContentContributorValueJSONObject(
								_cpCatalogEntry, getRequest());

					_label =
						availabilityContentContributorValueJSONObject.getString(
							CPContentContributorConstants.AVAILABILITY_NAME,
							StringPool.BLANK);
					_labelType =
						availabilityContentContributorValueJSONObject.getString(
							CPContentContributorConstants.
								AVAILABILITY_DISPLAY_TYPE,
							"default");
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public CPCatalogEntry getCpCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getNamespace() {
		return _namespace;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(httpServletRequest, "label", _label);
		setNamespacedAttribute(httpServletRequest, "labelType", _labelType);
		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
	}

	public void setCpCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_cpContentHelper = ServletContextUtil.getCPContentHelper();
		_productHelper = ServletContextUtil.getProductHelper();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpCatalogEntry = null;
		_cpContentHelper = null;
		_label = StringPool.BLANK;
		_labelType = "default";
		_namespace = StringPool.BLANK;
		_productHelper = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:availability-label:";

	private static final String _PAGE = "/availability_label/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AvailabilityLabelTag.class);

	private CPCatalogEntry _cpCatalogEntry;
	private CPContentHelper _cpContentHelper;
	private String _label = StringPool.BLANK;
	private String _labelType = "default";
	private String _namespace = StringPool.BLANK;
	private ProductHelper _productHelper;

}