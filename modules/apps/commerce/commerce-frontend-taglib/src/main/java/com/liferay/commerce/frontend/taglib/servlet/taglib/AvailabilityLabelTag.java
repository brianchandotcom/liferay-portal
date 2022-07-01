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

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 * @author Ivica Cardic
 * @author Alec Sloan
 */
public class AvailabilityLabelTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			InfoItemRenderer<CPDefinition> infoItemRenderer =
				(InfoItemRenderer<CPDefinition>)
					_infoItemRendererTracker.getInfoItemRenderer(
						"cpDefinition-availability-label");

			infoItemRenderer.render(
				CPDefinitionLocalServiceUtil.getCPDefinition(
					_cpCatalogEntry.getCPDefinitionId()),
				(HttpServletRequest)pageContext.getRequest(),
				PipingServletResponseFactory.createPipingServletResponse(
					pageContext));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return SKIP_BODY;
		}

		return SKIP_BODY;
	}

	public CPCatalogEntry getCPCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getNamespace() {
		return _namespace;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		_infoItemRendererTracker =
			ServletContextUtil.getInfoItemRendererTracker();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpCatalogEntry = null;
		_infoItemRendererTracker = null;
		_namespace = StringPool.BLANK;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:availability-label:";

	private static final Log _log = LogFactoryUtil.getLog(
		AvailabilityLabelTag.class);

	private CPCatalogEntry _cpCatalogEntry;
	private InfoItemRendererTracker _infoItemRendererTracker;
	private String _namespace = StringPool.BLANK;

}