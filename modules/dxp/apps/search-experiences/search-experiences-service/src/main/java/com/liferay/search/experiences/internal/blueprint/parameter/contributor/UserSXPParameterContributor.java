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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.Context;

import java.beans.ExceptionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class UserSXPParameterContributor implements SXPParameterContributor {

	public UserSXPParameterContributor(
		Language language, RoleLocalService roleLocalService,
		SegmentsEntryRetriever segmentsEntryRetriever,
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService,
		UserGroupLocalService userGroupLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService,
		UserLocalService userLocalService) {

		_language = language;
		_roleLocalService = roleLocalService;
		_segmentsEntryRetriever = segmentsEntryRetriever;
		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
		_userGroupLocalService = userGroupLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {

		try {
			_contribute(searchContext, sxpParameters);
		}
		catch (PortalException portalException) {
			exceptionListener.exceptionThrown(portalException);

			_log.error(portalException, portalException);
		}
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "user";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId) {

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				LongArraySXPParameter.class, "active-segment-entry-ids",
				"user.active_segment_entry_ids"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "age", "user.age"),
			new SXPParameterContributorDefinition(
				DateSXPParameter.class, "birthday", "user.birthday"),
			new SXPParameterContributorDefinition(
				DateSXPParameter.class, "create-date", "user.create_date"),
			new SXPParameterContributorDefinition(
				LongArraySXPParameter.class, "current-site-role-ids",
				"user.current_site_role_ids"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "email-domain", "user.email_domain"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "first-name", "user.first_name"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "full-name", "user.full_name"),
			new SXPParameterContributorDefinition(
				LongArraySXPParameter.class, "group-ids", "user.group_ids"),
			new SXPParameterContributorDefinition(
				LongSXPParameter.class, "user-id", "user.id"),
			new SXPParameterContributorDefinition(
				BooleanSXPParameter.class, "is-female", "user.is_female"),
			new SXPParameterContributorDefinition(
				BooleanSXPParameter.class, "is-gender-x", "user.is_gender_x"),
			new SXPParameterContributorDefinition(
				BooleanSXPParameter.class, "is-male", "user.is_male"),
			new SXPParameterContributorDefinition(
				BooleanSXPParameter.class, "is-signed-in", "user.is_signed_in"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "job-title", "user.job_title"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "language-id", "user.language_id"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "last-name", "user.last_name"),
			new SXPParameterContributorDefinition(
				LongArraySXPParameter.class, "regular-role-ids",
				"user.regular_role_ids"),
			new SXPParameterContributorDefinition(
				LongArraySXPParameter.class, "user-group-ids",
				"user.user_group_ids"));
	}

	private void _contribute(
			SearchContext searchContext, Set<SXPParameter> sxpParameters)
		throws PortalException {

		long userId = searchContext.getUserId();

		if (userId == 0) {
			return;
		}

		User user = _userLocalService.fetchUserById(userId);

		if (user == null) {
			return;
		}

		Long scopeGroupId = (Long)searchContext.getAttribute(
			"search.experiences.current.group.id");

		if (scopeGroupId != null) {
			long[] segmentsEntryIds =
				_segmentsEntryRetriever.getSegmentsEntryIds(
					scopeGroupId, user.getUserId(),
					new Context() {
						{
							put(
								Context.LANGUAGE_ID,
								_language.getLanguageId(
									searchContext.getLocale()));
							put(Context.SIGNED_IN, !user.isDefaultUser());
						}
					});

			segmentsEntryIds = ArrayUtil.filter(
				segmentsEntryIds, segmentsEntryId -> segmentsEntryId > 0);

			if (segmentsEntryIds.length > 0) {
				sxpParameters.add(
					new LongArraySXPParameter(
						"user.active_segment_entry_ids", true,
						ArrayUtil.toLongArray(segmentsEntryIds)));
			}
		}

		sxpParameters.add(
			new IntegerSXPParameter(
				"user.age", true, _getAge(user.getBirthday())));
		sxpParameters.add(
			new DateSXPParameter("user.birthday", true, user.getBirthday()));
		sxpParameters.add(
			new DateSXPParameter(
				"user.create_date", true, user.getCreateDate()));
		sxpParameters.add(
			new LongArraySXPParameter(
				"user.current_site_role_ids", true,
				_getCurrentSiteRoleIds(scopeGroupId, user)));
		sxpParameters.add(
			new StringSXPParameter(
				"user.email_domain", true, _getEmailAddressDomain(user)));
		sxpParameters.add(
			new StringSXPParameter(
				"user.first_name", true, user.getFirstName()));
		sxpParameters.add(
			new StringSXPParameter("user.full_name", true, user.getFullName()));
		sxpParameters.add(
			new LongArraySXPParameter(
				"user.group_ids", true,
				ArrayUtil.toLongArray(user.getGroupIds())));
		sxpParameters.add(
			new LongSXPParameter("user.id", true, user.getUserId()));
		sxpParameters.add(
			new BooleanSXPParameter("user.is_female", true, user.isFemale()));
		sxpParameters.add(
			new BooleanSXPParameter(
				"user.is_gender_x", true, !user.isFemale() && !user.isMale()));
		sxpParameters.add(
			new BooleanSXPParameter("user.is_male", true, user.isMale()));
		sxpParameters.add(
			new BooleanSXPParameter(
				"user.is_signed_in", true, !user.isDefaultUser()));
		sxpParameters.add(
			new StringSXPParameter("user.job_title", true, user.getJobTitle()));
		sxpParameters.add(
			new StringSXPParameter(
				"user.language_id", true, user.getLanguageId()));
		sxpParameters.add(
			new StringSXPParameter("user.last_name", true, user.getLastName()));
		sxpParameters.add(
			new LongArraySXPParameter(
				"user.regular_role_ids", true, _getRegularRoleIds(user)));

		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(
			userId);

		if (!userGroups.isEmpty()) {
			Stream<UserGroup> stream = userGroups.stream();

			sxpParameters.add(
				new LongArraySXPParameter(
					"user.user_group_ids", true,
					stream.map(
						UserGroup::getUserGroupId
					).toArray(
						Long[]::new
					)));
		}
	}

	private int _getAge(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		int x = GetterUtil.getInteger(formatter.format(date));
		int y = GetterUtil.getInteger(formatter.format(new Date()));

		return (y - x) / 10000;
	}

	private Long[] _getCurrentSiteRoleIds(Long scopeGroupId, User user) {
		List<Long> roleIds = new ArrayList<>();

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(user.getUserId());

		for (UserGroupRole userGroupRole : userGroupRoles) {
			roleIds.add(userGroupRole.getRoleId());
		}

		if (scopeGroupId != null) {
			List<UserGroupGroupRole> userGroupGroupRoles =
				_userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
					user.getUserId(), scopeGroupId);

			for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
				roleIds.add(userGroupGroupRole.getRoleId());
			}
		}

		return roleIds.toArray(new Long[0]);
	}

	private String _getEmailAddressDomain(User user) {
		String emailAddress = user.getEmailAddress();

		return emailAddress.substring(emailAddress.indexOf("@") + 1);
	}

	private Long[] _getRegularRoleIds(User user) throws PortalException {
		List<Long> roleIds = ListUtil.fromArray(user.getRoleIds());

		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(
			user.getUserId());

		for (UserGroup userGroup : userGroups) {
			List<Role> roles = _roleLocalService.getGroupRoles(
				userGroup.getGroupId());

			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			}
		}

		return roleIds.toArray(new Long[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserSXPParameterContributor.class);

	private final Language _language;
	private final RoleLocalService _roleLocalService;
	private final SegmentsEntryRetriever _segmentsEntryRetriever;
	private final UserGroupGroupRoleLocalService
		_userGroupGroupRoleLocalService;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;
	private final UserLocalService _userLocalService;

}