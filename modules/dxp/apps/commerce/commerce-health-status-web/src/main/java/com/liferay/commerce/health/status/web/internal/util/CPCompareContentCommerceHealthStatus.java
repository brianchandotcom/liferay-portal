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

package com.liferay.commerce.health.status.web.internal.util;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.web.internal.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.health.status.display.order:Integer=10",
		"commerce.health.status.key=" + CommerceHealthStatusConstants.CP_COMPARE_CONTENT_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceHealthStatus.class
)
public class CPCompareContentCommerceHealthStatus
	implements CommerceHealthStatus {

	@Override
	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		if (isFixed(groupId)) {
			return;
		}

		String name = "Compare";

		String friendlyURL =
			StringPool.FORWARD_SLASH + StringUtil.toLowerCase(name);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), httpServletRequest);

		Layout layout = _layoutService.addLayout(
			groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name,
			name, null, LayoutConstants.TYPE_PORTLET, true, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(
			_portal.getUserId(httpServletRequest),
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);

		_layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				CP_COMPARE_CONTENT_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceHealthStatusConstants.
			CP_COMPARE_CONTENT_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				CP_COMPARE_CONTENT_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public boolean isFixed(long groupId) throws PortalException {
		long plid = _portal.getPlidFromPortletId(
			groupId, CPPortletKeys.CP_COMPARE_CONTENT_WEB);

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