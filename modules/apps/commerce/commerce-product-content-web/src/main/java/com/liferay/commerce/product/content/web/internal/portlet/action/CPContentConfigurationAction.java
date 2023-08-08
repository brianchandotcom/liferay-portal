/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.product.content.web.internal.display.context.CPContentConfigurationDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
	service = ConfigurationAction.class
)
public class CPContentConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		try {
			CPContentConfigurationDisplayContext
				cpContentConfigurationDisplayContext =
					new CPContentConfigurationDisplayContext(
						httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpContentConfigurationDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		httpServletRequest.setAttribute(
			CPContentWebKeys.CP_CONTENT_HELPER, _cpContentHelper);

		return "/product_detail/configuration.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentConfigurationAction.class);

	@Reference
	private CPContentHelper _cpContentHelper;

}