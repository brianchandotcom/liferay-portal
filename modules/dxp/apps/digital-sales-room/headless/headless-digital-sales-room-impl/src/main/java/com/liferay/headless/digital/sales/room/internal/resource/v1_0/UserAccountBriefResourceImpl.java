/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.digital.sales.room.constants.DigitalSalesRoomPortletKeys;
import com.liferay.digital.sales.room.constants.DigitalSalesRoomTicketConstants;
import com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief;
import com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.UserAccountBriefDTOConverterContext;
import com.liferay.headless.digital.sales.room.resource.v1_0.UserAccountBriefResource;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.exception.RoleAssignmentException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.portlet.PortletMode;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.WindowState;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.ValidationException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account-brief.properties",
	scope = ServiceScope.PROTOTYPE, service = UserAccountBriefResource.class
)
public class UserAccountBriefResourceImpl
	extends BaseUserAccountBriefResourceImpl {

	@Override
	public void deleteDigitalSalesRoomUserAccountBrief(
			Long digitalSalesRoomId, Long userAccountBriefId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		LiveUsers.leaveGroup(
			contextCompany.getCompanyId(), group.getGroupId(),
			userAccountBriefId);

		_userGroupRoleLocalService.deleteUserGroupRoles(
			new long[] {userAccountBriefId}, group.getGroupId());

		_userLocalService.deleteGroupUser(
			group.getGroupId(), userAccountBriefId);
	}

	@Override
	public Page<UserAccountBrief> getDigitalSalesRoomUserAccountBriefsPage(
			Long digitalSalesRoomId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		return Page.of(
			null,
			transform(
				_userLocalService.getGroupUsers(
					group.getGroupId(), pagination.getStartPosition(),
					pagination.getEndPosition()),
				user -> _toUserAccountBrief(group.getGroupId(), user)),
			pagination,
			_userLocalService.getGroupUsersCount(group.getGroupId()));
	}

	@Override
	public UserAccountBrief postDigitalSalesRoomUserAccountBrief(
			Long digitalSalesRoomId, UserAccountBrief userAccountBrief)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		if (Validator.isNull(userAccountBrief.getEmailAddress())) {
			throw new ValidationException("Email Address is null");
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		Ticket ticket = _addInviteMemberTicket(
			group.getCompanyId(), group, userAccountBrief);

		User user = _userLocalService.fetchUserByEmailAddress(
			ticket.getCompanyId(), userAccountBrief.getEmailAddress());

		if (user == null) {
			return new UserAccountBrief() {
				{
					setEmailAddress(userAccountBrief::getEmailAddress);
					setId(ticket::getTicketId);
					setRoleKey(userAccountBrief::getRoleKey);
				}
			};
		}

		_userLocalService.addGroupUser(group.getGroupId(), user.getUserId());

		if (Validator.isNotNull(userAccountBrief.getRoleKey())) {
			Role role = _roleLocalService.fetchRole(
				group.getCompanyId(), userAccountBrief.getRoleKey());

			if (role.getType() != RoleConstants.TYPE_SITE) {
				throw new RoleAssignmentException(
					StringBundler.concat(
						"Role type ",
						RoleConstants.getTypeLabel(role.getType()),
						" is not role type ",
						RoleConstants.getTypeLabel(RoleConstants.TYPE_SITE)));
			}

			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), group.getGroupId(),
				new long[] {role.getRoleId()});
		}

		LiveUsers.joinGroup(
			group.getCompanyId(), group.getGroupId(), user.getUserId());

		return _toUserAccountBrief(group.getGroupId(), user);
	}

	private Ticket _addInviteMemberTicket(
			long companyId, Group group, UserAccountBrief userAccountBrief)
		throws Exception {

		Ticket ticket = _ticketLocalService.addTicket(
			companyId, Group.class.getName(), group.getGroupId(),
			DigitalSalesRoomTicketConstants.TYPE_INVITE_MEMBER,
			JSONUtil.put(
				"emailAddress", userAccountBrief.getEmailAddress()
			).put(
				"roleKey", userAccountBrief.getRoleKey()
			).toString(),
			new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48)),
			new ServiceContext());

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.
				getNotificationTemplateByExternalReferenceCode(
					"L_DSR_INVITE_MEMBER_EMAIL_NOTIFICATION_TEMPLATE",
					companyId);

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				notificationTemplate.getType());

		notificationType.sendNotification(
			new NotificationContextBuilder(
			).className(
				Group.class.getName()
			).classPK(
				group.getGroupId()
			).companyId(
				companyId
			).externalReferenceCode(
				group.getExternalReferenceCode()
			).groupId(
				group.getGroupId()
			).notificationTemplate(
				notificationTemplate
			).termValues(
				HashMapBuilder.<String, Object>put(
					"[%DIGITAL_SALES_ROOM_NAME%]",
					group.getName(contextAcceptLanguage.getPreferredLocale())
				).put(
					"[%DIGITAL_SALES_ROOM_URL%]",
					() -> {
						_initThemeDisplay(group.getGroupId());

						return PortletURLBuilder.create(
							PortletURLFactoryUtil.create(
								contextHttpServletRequest,
								DigitalSalesRoomPortletKeys.
									DIGITAL_SALES_ROOM_INVITE_MEMBER,
								_portal.getPlidFromPortletId(
									group.getGroupId(), LoginPortletKeys.LOGIN),
								PortletRequest.RENDER_PHASE)
						).setMVCRenderCommandName(
							"/digital_sales_room/invite_member"
						).setParameter(
							"ticketKey", ticket.getKey()
						).setPortletMode(
							PortletMode.VIEW
						).setWindowState(
							WindowState.MAXIMIZED
						).buildString();
					}
				).put(
					"[%TO%]", userAccountBrief.getEmailAddress()
				).build()
			).userId(
				contextUser.getUserId()
			).build());

		return ticket;
	}

	private void _initThemeDisplay(long groupId) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return;
		}

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			contextHttpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, httpServletResponse);

		themeDisplay = (ThemeDisplay)contextHttpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setScopeGroupId(groupId);
	}

	private UserAccountBrief _toUserAccountBrief(long groupId, User user)
		throws Exception {

		return _userAccountBriefDTOConverter.toDTO(
			new UserAccountBriefDTOConverterContext(
				true, null, _dtoConverterRegistry, groupId, user.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			user);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.UserAccountBriefDTOConverter)"
	)
	private DTOConverter<User, UserAccountBrief> _userAccountBriefDTOConverter;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}