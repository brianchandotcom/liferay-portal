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

package com.liferay.commerce.punchout.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.punchout.configuration.PunchOutConfiguration;
import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.commerce.punchout.web.internal.display.context.CommercePunchOutDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(
	property = "screen.navigation.entry.order:Integer=" + Integer.MAX_VALUE,
	service = ScreenNavigationEntry.class
)
public class CommerceChannelPunchOutScreenNavigationEntry
	extends CommerceChannelPunchOutScreenNavigationCategory
	implements ScreenNavigationEntry<CommerceChannel> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceChannel commerceChannel = _getCommerceChannel(
			httpServletRequest);

		PunchOutConfiguration punchOutConfiguration = _getPunchOutConfiguration(
			commerceChannel.getGroupId());

		CommercePunchOutDisplayContext commerceShippingMethodsDisplayContext =
			new CommercePunchOutDisplayContext(
				commerceChannel.getCommerceChannelId(), punchOutConfiguration);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceShippingMethodsDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	private CommerceChannel _getCommerceChannel(
		HttpServletRequest httpServletRequest) {

		try {
			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			return _commerceChannelLocalService.getCommerceChannel(
				commerceChannelId);
		}
		catch (Exception exception) {
			_log.error("Unable to get commerce channel", exception);
		}

		return null;
	}

	private PunchOutConfiguration _getPunchOutConfiguration(
		long commerceChannelGroupId) {

		try {
			return _configurationProvider.getConfiguration(
				PunchOutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannelGroupId, PunchOutConstants.SERVICE_NAME));
		}
		catch (Exception exception) {
			_log.error("Unable to get punch out configuration", exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelPunchOutScreenNavigationEntry.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.punchout.web)"
	)
	private ServletContext _servletContext;

}