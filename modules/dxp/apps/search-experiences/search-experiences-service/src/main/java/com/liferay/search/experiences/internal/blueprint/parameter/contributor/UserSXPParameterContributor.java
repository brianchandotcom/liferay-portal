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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=user",
	service = SXPParameterContributor.class
)
public class UserSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint, SXPParameterDataBuilder sxpParameterDataBuilder
		) {

		long userId = _getUserId(searchRequestBuilder);

		if (userId == 0) {
			ProblemUtil.addWarning(
				getClass().getName(), "user-id-not-found-in-attributes",
				null, null, null);

			return;
		}

		_contribute(searchRequestBuilder, sxpParameterDataBuilder, userId);
	}

	@Override
	public String getCategoryNameKey() {
		return "user";
	}

	@Override
	public List<SXPParameterContributionDefinition>
		getSXPParameterContributionDefinitions() {

		return ListUtil.fromArray(
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "user-id", "user.id"),
			new SXPParameterContributionDefinition(
				BooleanSXPParameter.class.getName(), "is-signed-in",
				"user.is_signed_in"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "full-name",
				"user.full_name"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "first-name",
				"user.first_name"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "last-name",
				"user.last_name"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "language-id",
				"user.language_id"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "job-title",
				_getTemplateVariableName("job_title")),
			new SXPParameterContributionDefinition(
				DateSXPParameter.class.getName(), "create-date",
				"user.create_date"),
			new SXPParameterContributionDefinition(
				DateSXPParameter.class.getName(), "birthday", "user.birthday"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "age", "user.age"),
			new SXPParameterContributionDefinition(
				BooleanSXPParameter.class.getName(), "is-male", "user.is_male"),
			new SXPParameterContributionDefinition(
				BooleanSXPParameter.class.getName(), "is-female",
				"user.is_female"),
			new SXPParameterContributionDefinition(
				BooleanSXPParameter.class.getName(), "is-gender-x",
				_getTemplateVariableName("is_gender_x")),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "email-domain",
				"user.email_domain"),
			new SXPParameterContributionDefinition(
				LongArraySXPParameter.class.getName(), "group-ids",
				"user.group_ids"),
			new SXPParameterContributionDefinition(
				LongArraySXPParameter.class.getName(), "usergroup-ids",
				"user.usergroup_ids"),
			new SXPParameterContributionDefinition(
				LongArraySXPParameter.class.getName(), "regular-role-ids",
				_getTemplateVariableName("regular_role_ids")),
			new SXPParameterContributionDefinition(
				LongArraySXPParameter.class.getName(), "current-site-role-ids",
				"user.current_site_role_ids"),
			new SXPParameterContributionDefinition(
				LongArraySXPParameter.class.getName(),
				"active-segment-entry-ids", "user.active_segment_entry_ids"));
	}

	private void _addCurrentSiteRoleIds(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		long[] roleIds = _getCurrentSiteRoleIds(
			searchRequestBuilder, user.getUserId());

		if ((roleIds == null) || (roleIds.length == 0)) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new LongArraySXPParameter(
				"user.current_site_role_ids", true,
				_toBoxedLongArray(roleIds)));
	}

	private void _addGroupIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		sxpParameterDataBuilder.addSXPParameter(
			new LongArraySXPParameter(
				"user.group_ids", true, _toBoxedLongArray(user.getGroupIds())));
	}

	private void _addRegularUserRoleIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		long[] roleIds = _getRegularRoleIds(user);

		sxpParameterDataBuilder.addSXPParameter(
			new LongArraySXPParameter(
				"user.regular_role_ids", true, _toBoxedLongArray(roleIds)));
	}

	private void _addUserGroupGroupIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		long[] userGroupIds = _getUserGroupIds(user.getUserId());

		if (userGroupIds.length == 0) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new LongArraySXPParameter(
				"user.usergroup_ids", true,
				_toBoxedLongArray(_getUserGroupIds(user.getUserId()))));
	}

	private void _addUserInfo(
			SXPParameterDataBuilder sxpParameterDataBuilder, User user)
		throws NumberFormatException, PortalException {

		sxpParameterDataBuilder.addSXPParameter(
			new LongSXPParameter("user.id", true, user.getUserId()));
		sxpParameterDataBuilder.addSXPParameter(
			new BooleanSXPParameter(
				"user.is_signed_in", true, _isSignedIn(user)));
		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter("user.full_name", true, user.getFullName()));
		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"user.first_name", true, user.getFirstName()));
		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter("user.last_name", true, user.getLastName()));
		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"user.language_id", true, user.getLanguageId()));
		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter("user.job_title", true, user.getJobTitle()));
		sxpParameterDataBuilder.addSXPParameter(
			new DateSXPParameter(
				"user.create_date", true, user.getCreateDate()));

		sxpParameterDataBuilder.addSXPParameter(
			new DateSXPParameter("user.birthday", true, user.getBirthday()));

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"user.age", true, _getUserAge(user.getBirthday())));
		sxpParameterDataBuilder.addSXPParameter(
			new BooleanSXPParameter("user.is_male", true, user.isMale()));
		sxpParameterDataBuilder.addSXPParameter(
			new BooleanSXPParameter("user.is_female", true, user.isFemale()));
		sxpParameterDataBuilder.addSXPParameter(
			new BooleanSXPParameter(
				"user.is_gender_x", true, !user.isFemale() && !user.isMale()));

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"user.email_domain", true, _getUserEmailDomain(user)));
	}

	private void _addUserSegments(
			SearchRequestBuilder searchRequestBuilder,
			SXPParameterDataBuilder sxpParameterDataBuilder, User user)
		throws PortalException {

		Optional<Long> optional = _getScopeGroupId(searchRequestBuilder);

		if (!optional.isPresent()) {
			return;
		}

		Locale locale = SearchContextUtil.getLocale(searchRequestBuilder);

		Context context = new Context();

		context.put(Context.SIGNED_IN, !user.isDefaultUser());
		context.put(Context.LANGUAGE_ID, locale.toString());

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			optional.get(), user.getUserId(), context);

		long[] filteredArray = LongStream.of(
			segmentsEntryIds
		).filter(
			value -> value > 0
		).toArray();

		if (filteredArray.length == 0) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new LongArraySXPParameter(
				"user.active_segment_entry_ids", true,
				_toBoxedLongArray(segmentsEntryIds)));
	}

	private void _contribute(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder, long userId) {

		try {
			User user = _userLocalService.getUser(userId);

			_addUserInfo(sxpParameterDataBuilder, user);

			_addGroupIds(sxpParameterDataBuilder, user);

			_addUserGroupGroupIds(sxpParameterDataBuilder, user);

			_addCurrentSiteRoleIds(
				searchRequestBuilder, sxpParameterDataBuilder, user);

			_addRegularUserRoleIds(sxpParameterDataBuilder, user);

			_addUserSegments(
				searchRequestBuilder, sxpParameterDataBuilder, user);
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);

			ProblemUtil.addUnknownError(getClass().getName(), exception);
		}
	}

	private long[] _getCurrentSiteRoleIds(
		SearchRequestBuilder searchRequestBuilder, long userId) {

		long[] userGroupRoleIds = _getUserGroupRoleIds(userId);

		Optional<Long> optional = _getScopeGroupId(searchRequestBuilder);

		if (!optional.isPresent()) {
			return userGroupRoleIds;
		}

		return LongStream.concat(
			Arrays.stream(userGroupRoleIds),
			Arrays.stream(_getUserGroupGroupRoleIds(userId, optional.get()))
		).toArray();
	}

	private long[] _getRegularRoleIds(User user) {
		long[] regularRoleIds = user.getRoleIds();

		long[] userGroupRoleIds = _getUserGroupInheritedRoleIds(
			user.getUserId());

		if ((userGroupRoleIds == null) || (userGroupRoleIds.length == 0)) {
			return regularRoleIds;
		}

		return LongStream.concat(
			Arrays.stream(regularRoleIds),
			Arrays.stream(_getUserGroupInheritedRoleIds(user.getUserId()))
		).toArray();
	}

	private Optional<Long> _getScopeGroupId(
		SearchRequestBuilder searchRequestBuilder) {

		return Optional.ofNullable(
			SearchContextUtil.getLongAttribute(
				"scope_group_id", searchRequestBuilder));
	}

	private String _getTemplateVariableName(String key) {
		StringBundler sb = new StringBundler(3);

		sb.append("${user.");
		sb.append(key);
		sb.append("}");

		return sb.toString();
	}

	private int _getUserAge(Date birthday) {
		Date now = new Date();

		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		int d1 = GetterUtil.getInteger(formatter.format(birthday));

		int d2 = GetterUtil.getInteger(formatter.format(now));

		return (d2 - d1) / 10000;
	}

	private String _getUserEmailDomain(User user) {
		String email = user.getEmailAddress();

		return email.substring(email.indexOf("@") + 1);
	}

	private long[] _getUserGroupGroupRoleIds(long userId, long groupId) {
		List<UserGroupGroupRole> userGroupGroupRoles =
			_userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
				userId, groupId);

		Stream<UserGroupGroupRole> stream = userGroupGroupRoles.stream();

		return stream.mapToLong(
			UserGroupGroupRole::getRoleId
		).toArray();
	}

	private long[] _getUserGroupIds(long userId) {
		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(
			userId);

		Stream<UserGroup> stream = userGroups.stream();

		return stream.mapToLong(
			UserGroup::getUserGroupId
		).toArray();
	}

	private long[] _getUserGroupInheritedRoleIds(long userId) {
		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(
			userId);

		if (userGroups.isEmpty()) {
			return null;
		}

		List<Role> roles = new ArrayList<>();

		userGroups.forEach(
			userGroup -> {
				try {
					roles.addAll(
						_roleLocalService.getGroupRoles(
							userGroup.getGroupId()));
				}
				catch (PortalException portalException) {
					_log.error(portalException.getMessage(), portalException);

					ProblemUtil.addUnknownError(
						getClass().getName(), portalException);
				}
			});

		if (roles.isEmpty()) {
			return null;
		}

		Stream<Role> stream = roles.stream();

		return stream.mapToLong(
			Role::getRoleId
		).toArray();
	}

	private long[] _getUserGroupRoleIds(long userId) {
		List<UserGroupRole> roles =
			_userGroupRoleLocalService.getUserGroupRoles(userId);

		Stream<UserGroupRole> stream = roles.stream();

		return stream.mapToLong(
			UserGroupRole::getRoleId
		).toArray();
	}

	private Long _getUserId(SearchRequestBuilder searchRequestBuilder) {
		return SearchContextUtil.getUserId(searchRequestBuilder);
	}

	private Boolean _isSignedIn(User user) {
		return !user.isDefaultUser();
	}

	private Long[] _toBoxedLongArray(long[] arr) {
		return LongStream.of(
			arr
		).boxed(
		).toArray(
			Long[]::new
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserSXPParameterContributor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}