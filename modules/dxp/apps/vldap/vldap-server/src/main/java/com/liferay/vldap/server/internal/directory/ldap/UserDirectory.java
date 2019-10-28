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

package com.liferay.vldap.server.internal.directory.ldap;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageServiceUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerThreadLocal;
import com.liferay.vldap.server.internal.portal.security.samba.PortalSambaUtil;
import com.liferay.vldap.server.internal.util.LdapUtil;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.text.Format;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Igor Beslic
 */
public class UserDirectory extends Directory {

	public UserDirectory(String top, Company company, User user)
		throws PortalException {

		addAttribute("cn", user.getScreenName());

		Date createDate = user.getCreateDate();

		if (createDate == null) {
			createDate = new Date();
		}

		addAttribute("createTimestamp", _format.format(createDate));

		addAttribute("displayName", user.getFullName());

		if (user.isMale()) {
			addAttribute("gender", "M");
		}
		else {
			addAttribute("gender", "F");
		}

		addAttribute("gidNumber", PortletPropsValues.POSIX_GROUP_ID);
		addAttribute("givenName", user.getFirstName());

		if (user.getPortraitId() != 0) {
			Image image = ImageServiceUtil.getImage(user.getPortraitId());

			addAttribute("jpegphoto", image.getTextObj());
		}

		addAttribute("mail", user.getEmailAddress());

		Date modifyDate = user.getModifiedDate();

		if (modifyDate == null) {
			modifyDate = new Date();
		}

		addAttribute("modifyTimestamp", _format.format(modifyDate));

		addAttribute("objectclass", "groupOfNames");
		addAttribute("objectclass", "inetOrgPerson");
		addAttribute("objectclass", "liferayPerson");
		addAttribute("objectclass", "top");
		addAttribute("sn", user.getLastName());
		addAttribute("status", String.valueOf(user.getStatus()));
		addAttribute("uid", user.getScreenName());
		addAttribute("uidNumber", String.valueOf(user.getUserId()));
		addAttribute("uuid", user.getUuid());

		if (LdapHandlerThreadLocal.isHostAllowed(
				PortletPropsValues.SAMBA_HOSTS_ALLOWED)) {

			addSambaAttributes(company, user);
		}

		setName(top, company, "Users", "cn=" + user.getScreenName());

		long groupClassNameId = PortalUtil.getClassNameId(
			Group.class.getName());

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersGroups", user.getUserId()
			).build();

		List<Group> groups = GroupLocalServiceUtil.search(
			user.getCompanyId(), new long[] {groupClassNameId}, null, null,
			params, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			String value = LdapUtil.buildName(
				group.getName(), top, company, "Communities", group.getName());

			addAttribute("member", value);
		}

		for (Organization organization : user.getOrganizations()) {
			String value = LdapUtil.buildName(
				organization.getName(), top, company, "Organizations",
				organization.getName());

			addAttribute("member", value);
		}

		for (Role role : user.getRoles()) {
			String value = LdapUtil.buildName(
				role.getName(), top, company, "Roles", role.getName());

			addAttribute("member", value);
		}

		for (UserGroup userGroup : user.getUserGroups()) {
			String value = LdapUtil.buildName(
				userGroup.getName(), top, company, "User Groups",
				userGroup.getName());

			addAttribute("member", value);
		}
	}

	protected void addSambaAttributes(Company company, User user)
		throws PortalException {

		addAttribute("objectclass", "sambaSamAccount");

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		StringBundler sb = new StringBundler(13);

		sb.append(CharPool.OPEN_BRACKET);
		sb.append('U');

		if (!user.isActive()) {
			sb.append('D');
		}

		if (!passwordPolicy.isExpireable()) {
			sb.append('X');
		}

		for (int i = sb.length(); i < 12; i++) {
			sb.append(CharPool.SPACE);
		}

		sb.append(CharPool.CLOSE_BRACKET);

		addAttribute("sambaAcctFlags", sb.toString());

		addAttribute("sambaForceLogoff", "-1");
		addAttribute(
			"sambaLMPassword", PortalSambaUtil.getSambaLMPassword(user));

		long sambaLockoutDuration =
			passwordPolicy.getLockoutDuration() / Time.MINUTE;

		if (!passwordPolicy.isLockout()) {
			sambaLockoutDuration = 0;
		}

		addAttribute(
			"sambaLockoutDuration", String.valueOf(sambaLockoutDuration));

		long sambaLockoutObservationWindow =
			passwordPolicy.getResetFailureCount() / Time.MINUTE;

		if (passwordPolicy.isRequireUnlock()) {
			sambaLockoutObservationWindow = Integer.MAX_VALUE;
		}

		addAttribute(
			"sambaLockoutObservationWindow",
			String.valueOf(sambaLockoutObservationWindow));

		addAttribute(
			"sambaLockoutThreshold",
			String.valueOf(passwordPolicy.getGraceLimit()));
		addAttribute("sambaLogonToChgPwd", "2");

		long sambaMaxPwdAge = passwordPolicy.getMaxAge() / Time.MINUTE;

		if (!passwordPolicy.isExpireable()) {
			sambaMaxPwdAge = -1;
		}

		addAttribute("sambaMaxPwdAge", String.valueOf(sambaMaxPwdAge));

		addAttribute(
			"sambaMinPwdAge",
			String.valueOf(passwordPolicy.getMinAge() / Time.MINUTE));
		addAttribute(
			"sambaMinPwdLength", String.valueOf(passwordPolicy.getMinLength()));
		addAttribute(
			"sambaNTPassword", PortalSambaUtil.getSambaNTPassword(user));

		int sambaPwdHistoryLength = passwordPolicy.getHistoryCount();

		if (!passwordPolicy.isHistory()) {
			sambaPwdHistoryLength = 0;
		}

		addAttribute(
			"sambaPwdHistoryLength", String.valueOf(sambaPwdHistoryLength));

		addAttribute("sambaRefuseMachinePwdChange", "0");
		addAttribute(
			"sambaSID",
			StringBundler.concat(
				"S-1-5-21-", company.getCompanyId(), "-", user.getUserId()));
	}

	private static final Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss.SSSZ");

}