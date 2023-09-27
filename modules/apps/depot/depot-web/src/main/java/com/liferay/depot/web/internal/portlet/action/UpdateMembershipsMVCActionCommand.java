/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/depot/update_memberships"
	},
	service = MVCActionCommand.class
)
public class UpdateMembershipsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			User user = _portal.getSelectedUser(actionRequest);

			Contact contact = user.getContact();

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(user.getBirthday());

			long[] groupIds = _getGroupIds(actionRequest, user);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				User.class.getName(), actionRequest);

			serviceContext.setAssetCategoryIds(null);
			serviceContext.setAssetTagNames(null);

			_userService.updateUser(
				user.getUserId(), user.getPassword(), null, null,
				user.isPasswordReset(), null, null, user.getScreenName(),
				user.getEmailAddress(), user.getLanguageId(),
				user.getTimeZoneId(), user.getGreeting(), user.getComments(),
				user.getFirstName(), user.getMiddleName(), user.getLastName(),
				contact.getPrefixListTypeId(), contact.getSuffixListTypeId(),
				user.isMale(), birthdayCal.get(Calendar.MONTH),
				birthdayCal.get(Calendar.DATE), birthdayCal.get(Calendar.YEAR),
				contact.getSmsSn(), contact.getFacebookSn(),
				contact.getJabberSn(), contact.getSkypeSn(),
				contact.getTwitterSn(), user.getJobTitle(), groupIds,
				user.getOrganizationIds(), null, null, user.getUserGroupIds(),
				serviceContext);
		}
		catch (NoSuchUserException | PrincipalException exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	private long[] _getGroupIds(PortletRequest portletRequest, User user) {
		Set<Long> groupIds = new HashSet<>();

		for (long groupId : user.getGroupIds()) {
			groupIds.add(groupId);
		}

		long[] addDepotGroupIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "addDepotGroupIds"), 0L);

		for (long addDepotGroupId : addDepotGroupIds) {
			groupIds.add(addDepotGroupId);
		}

		long[] deleteDepotGroupIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "deleteDepotGroupIds"), 0L);

		for (long deleteDepotGroupId : deleteDepotGroupIds) {
			groupIds.remove(deleteDepotGroupId);
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserService _userService;

}