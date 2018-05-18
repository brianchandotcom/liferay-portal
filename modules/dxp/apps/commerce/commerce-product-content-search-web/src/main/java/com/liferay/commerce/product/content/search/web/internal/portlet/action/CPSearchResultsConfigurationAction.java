/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.search.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSearchResultsDisplayContext;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.links.CPDefinitionLinkTypeRegistry;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
	service = ConfigurationAction.class
)
public class CPSearchResultsConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		try {
			CPSearchResultsDisplayContext cpSearchResultsDisplayContext =
				new CPSearchResultsDisplayContext(
					_cpDefinitionHelper, _cpDefinitionLinkTypeRegistry,
					_cpInstanceHelper, _dlAppService, httpServletRequest,
					portletSharedSearchResponse);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpSearchResultsDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return "/search_results/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.search.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPSearchResultsConfigurationAction.class);

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLinkTypeRegistry _cpDefinitionLinkTypeRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}