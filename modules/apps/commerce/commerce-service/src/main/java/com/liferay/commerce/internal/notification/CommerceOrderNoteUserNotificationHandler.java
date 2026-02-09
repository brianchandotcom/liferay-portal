/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "jakarta.portlet.name=com.liferay.commerce.model.CommerceOrderNote",
	service = UserNotificationHandler.class
)
public class CommerceOrderNoteUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public CommerceOrderNoteUserNotificationHandler() {
		setPortletId(CommerceOrderNote.class.getName());
	}

	@Override
	public boolean hasPermission(long classPK, User user)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteLocalService.getCommerceOrderNote(classPK);

		return _commerceOrderModelResourcePermission.contains(
			_permissionCheckerFactory.create(user),
			commerceOrderNote.getCommerceOrderId(), ActionKeys.VIEW);
	}

	@Override
	public boolean isDeliver(
		long userId, long classNameId, int notificationType, int deliveryType,
		ServiceContext serviceContext) {

		return true;
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(getBodyContent(jsonObject), 70)),
				getTitle(userNotificationEvent, serviceContext)
			});
	}

	@Override
	protected String getBodyContent(JSONObject jsonObject) {
		return HtmlUtil.escape(jsonObject.getString("notificationMessage"));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		CommerceOrderNote commerceOrderNote = _fetchCommerceOrderNote(
			jsonObject);

		if (commerceOrderNote == null) {
			return super.getLink(userNotificationEvent, serviceContext);
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(
				commerceOrderNote.getCommerceOrderId());

		if (commerceOrder == null) {
			return super.getLink(userNotificationEvent, serviceContext);
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		Company company = _companyLocalService.getCompany(
			commerceOrder.getCompanyId());

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				company.getGroupId(), ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

			return PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceOrder.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_order/edit_commerce_order"
			).setParameter(
				"commerceOrderId", commerceOrder.getCommerceOrderId()
			).setParameter(
				"screenNavigationCategoryKey", "notes"
			).setParameter(
				"screenNavigationEntryKey", "order-notes"
			).buildString();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelByGroupClassPK(
				commerceOrder.getGroupId());

		if (commerceChannel == null) {
			return super.getLink(userNotificationEvent, serviceContext);
		}

		Layout layout = _layoutLocalService.fetchDefaultLayout(
			commerceChannel.getSiteGroupId(), false);

		if (layout == null) {
			layout = _layoutLocalService.fetchDefaultLayout(
				commerceChannel.getSiteGroupId(), true);
		}

		if (layout == null) {
			return super.getLink(userNotificationEvent, serviceContext);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout originalLayout = themeDisplay.getLayout();

		try {
			themeDisplay.setLayout(layout);

			return _commerceOrderHttpHelper.getCommerceCartPortletURL(
				commerceChannel.getSiteGroupId(), httpServletRequest,
				commerceOrder);
		}
		finally {
			themeDisplay.setLayout(originalLayout);
		}
	}

	@Override
	protected String getTitle(
			JSONObject jsonObject, AssetRenderer<?> assetRenderer,
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String message = StringPool.BLANK;

		CommerceOrderNote commerceOrderNote = _fetchCommerceOrderNote(
			jsonObject);

		if (commerceOrderNote == null) {
			return message;
		}

		int notificationType = jsonObject.getInt("notificationType");

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			message = "x-added-a-new-x";
		}
		else if (notificationType ==
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			message = "x-updated-a-x";
		}

		User user = commerceOrderNote.getUser();

		return _language.format(
			serviceContext.getLocale(), message,
			new String[] {
				HtmlUtil.escape(user.getFullName()),
				_language.get(serviceContext.getLocale(), "order-note")
			},
			false);
	}

	@Override
	protected String getTitle(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		return getTitle(
			jsonObject, null, userNotificationEvent, serviceContext);
	}

	private CommerceOrderNote _fetchCommerceOrderNote(JSONObject jsonObject) {
		long classPK = jsonObject.getLong("classPK");

		try {
			return _commerceOrderNoteLocalService.fetchCommerceOrderNote(
				classPK);
		}
		catch (SystemException systemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(systemException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderNoteUserNotificationHandler.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceOrderNoteLocalService _commerceOrderNoteLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference(
		target = "(resource.name=" + CommercePortletKeys.COMMERCE_ORDER + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}