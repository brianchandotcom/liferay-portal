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

package com.liferay.commerce.discount.web.internal.portlet;

import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscount"
)
public class CommerceDiscountPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return CommerceDiscountPortletKeys.COMMERCE_DISCOUNT;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request, Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = _portal.getPlidFromPortletId(
			group.getGroupId(), getPortletName());

		long controlPanelPlid = _portal.getControlPanelPlid(
			themeDisplay.getCompanyId());

		if ((plid == controlPanelPlid) ||
			(plid == LayoutConstants.DEFAULT_PLID)) {

			return _portal.getControlPanelPortletURL(
				request, group, getPortletName(), 0, 0,
				PortletRequest.RENDER_PHASE);
		}
		else {
			return PortletURLFactoryUtil.create(
				request, getPortletName(), plid, PortletRequest.RENDER_PHASE);
		}
	}

	@Reference
	private Portal _portal;

}