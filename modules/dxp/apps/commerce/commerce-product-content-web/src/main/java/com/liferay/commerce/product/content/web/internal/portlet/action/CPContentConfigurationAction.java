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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.web.configuration.CPContentConfigurationHelper;
import com.liferay.commerce.product.content.web.internal.display.context.CPContentConfigurationDisplayContext;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
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
						_cpContentConfigurationHelper, _cpTypeServicesTracker,
						httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpContentConfigurationDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return "/product_detail/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentConfigurationAction.class);

	@Reference
	private CPContentConfigurationHelper _cpContentConfigurationHelper;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

}