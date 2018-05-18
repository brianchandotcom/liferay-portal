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

package com.liferay.commerce.wish.list.web.internal.health.status;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.health.status.display.order:Integer=20",
		"commerce.health.status.key=" + WishListContentCommerceHealthStatus.KEY
	},
	service = CommerceHealthStatus.class
)
public class WishListContentCommerceHealthStatus
	implements CommerceHealthStatus {

	public static final String KEY = "wish-list-content";

	@Override
	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		if (isFixed(groupId)) {
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), httpServletRequest);

		Layout layout = _layoutService.addLayout(
			groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"Wish List", "Wish List", null, LayoutConstants.TYPE_PORTLET, true,
			"/wishlist", serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(
			_portal.getUserId(httpServletRequest),
			CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT);

		_layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, "wish-list-content-health-status-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, "wish-list-content-health-status-name");
	}

	@Override
	public boolean isFixed(long groupId) throws PortalException {
		long plid = _portal.getPlidFromPortletId(
			groupId, CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT);

		if (plid > 0) {
			return true;
		}

		return false;
	}

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}