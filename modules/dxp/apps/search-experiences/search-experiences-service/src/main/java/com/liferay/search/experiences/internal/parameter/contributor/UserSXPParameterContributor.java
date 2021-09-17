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

package com.liferay.search.experiences.internal.parameter.contributor;

import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValuesHelper;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.DateParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.LongArrayParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.SXPParameterContributor;
import com.liferay.search.experiences.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.parameter.SXPParameterDefinition;
import com.liferay.search.experiences.parameter.StringParameter;
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
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, SXPBlueprint sxpBlueprint) {

		long userId = _getUserId(sxpAttributes);

		if (userId == 0) {
			ProblemUtil.addWarning(
				getClass().getName(), "user-id-not-found-in-attributes",
				"User ID not found in attributes", null, null, null);

			return;
		}

		_contribute(sxpParameterDataBuilder, sxpAttributes, userId);
	}

	@Override
	public String getCategoryNameKey() {
		return "user";
	}

	@Override
	public List<SXPParameterDefinition> getParameterDefinitions() {
		return ListUtil.fromArray(
			new SXPParameterDefinition(
				_getTemplateVariableName("id"), LongParameter.class.getName(),
				"core.parameter.user.id"),
			new SXPParameterDefinition(
				_getTemplateVariableName("is_signed_in"),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-signed-in"),
			new SXPParameterDefinition(
				_getTemplateVariableName("full_name"),
				StringParameter.class.getName(),
				"core.parameter.user.full-name"),
			new SXPParameterDefinition(
				_getTemplateVariableName("first_name"),
				StringParameter.class.getName(),
				"core.parameter.user.first-name"),
			new SXPParameterDefinition(
				_getTemplateVariableName("last_name"),
				StringParameter.class.getName(),
				"core.parameter.user.last-name"),
			new SXPParameterDefinition(
				_getTemplateVariableName("language_id"),
				StringParameter.class.getName(),
				"core.parameter.user.language-id"),
			new SXPParameterDefinition(
				_getTemplateVariableName("job_title"),
				StringParameter.class.getName(),
				"core.parameter.user.job-title"),
			new SXPParameterDefinition(
				_getTemplateVariableName("create_date"),
				DateParameter.class.getName(),
				"core.parameter.user.create-date"),
			new SXPParameterDefinition(
				_getTemplateVariableName("birthday"),
				DateParameter.class.getName(), "core.parameter.user.birthday"),
			new SXPParameterDefinition(
				_getTemplateVariableName("age"),
				IntegerParameter.class.getName(), "core.parameter.user.age"),
			new SXPParameterDefinition(
				_getTemplateVariableName("is_male"),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-male"),
			new SXPParameterDefinition(
				_getTemplateVariableName("is_female"),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-female"),
			new SXPParameterDefinition(
				_getTemplateVariableName("is_gender_x"),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-gender-x"),
			new SXPParameterDefinition(
				_getTemplateVariableName("email_domain"),
				StringParameter.class.getName(),
				"core.parameter.user.email-domain"),
			new SXPParameterDefinition(
				_getTemplateVariableName("group_ids"),
				LongArrayParameter.class.getName(),
				"core.parameter.user.group-ids"),
			new SXPParameterDefinition(
				_getTemplateVariableName("usergroup_ids"),
				LongArrayParameter.class.getName(),
				"core.parameter.user.usergroup-ids"),
			new SXPParameterDefinition(
				_getTemplateVariableName("regular_role_ids"),
				LongArrayParameter.class.getName(),
				"core.parameter.user.regular-role-ids"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_site_role_ids"),
				LongArrayParameter.class.getName(),
				"core.parameter.user.current-site-role-ids"),
			new SXPParameterDefinition(
				_getTemplateVariableName("active_segment_entry_ids"),
				LongArrayParameter.class.getName(),
				"core.parameter.user.active-segment-entry-ids"));
	}

	private void _addCurrentSiteRoleIds(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, User user) {

		long[] roleIds = _getCurrentSiteRoleIds(
			sxpAttributes, user.getUserId());

		if ((roleIds == null) || (roleIds.length == 0)) {
			return;
		}

		sxpParameterDataBuilder.addParameter(
			new LongArrayParameter(
				"current_site_role_ids",
				_getTemplateVariableName("current_site_role_ids"),
				_toBoxedLongArray(roleIds)));
	}

	private void _addGroupIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		sxpParameterDataBuilder.addParameter(
			new LongArrayParameter(
				"group_ids", _getTemplateVariableName("group_ids"),
				_toBoxedLongArray(user.getGroupIds())));
	}

	private void _addRegularUserRoleIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		long[] roleIds = _getRegularRoleIds(user);

		sxpParameterDataBuilder.addParameter(
			new LongArrayParameter(
				"regular_role_ids",
				_getTemplateVariableName("regular_role_ids"),
				_toBoxedLongArray(roleIds)));
	}

	private void _addUserGroupGroupIds(
		SXPParameterDataBuilder sxpParameterDataBuilder, User user) {

		long[] userGroupIds = _getUserGroupIds(user.getUserId());

		if (userGroupIds.length == 0) {
			return;
		}

		sxpParameterDataBuilder.addParameter(
			new LongArrayParameter(
				"usergroup_ids", _getTemplateVariableName("usergroup_ids"),
				_toBoxedLongArray(_getUserGroupIds(user.getUserId()))));
	}

	private void _addUserInfo(
			SXPParameterDataBuilder sxpParameterDataBuilder, User user)
		throws NumberFormatException, PortalException {

		sxpParameterDataBuilder.addParameter(
			new LongParameter(
				"id", _getTemplateVariableName("id"), user.getUserId()));
		sxpParameterDataBuilder.addParameter(
			new BooleanParameter(
				"is_signed_in", _getTemplateVariableName("is_signed_in"),
				_isSignedIn(user)));
		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"full_name", _getTemplateVariableName("full_name"),
				user.getFullName()));
		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"first_name", _getTemplateVariableName("first_name"),
				user.getFirstName()));
		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"last_name", _getTemplateVariableName("last_name"),
				user.getLastName()));
		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"language_id", _getTemplateVariableName("language_id"),
				user.getLanguageId()));
		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"job_title", _getTemplateVariableName("job_title"),
				user.getJobTitle()));
		sxpParameterDataBuilder.addParameter(
			new DateParameter(
				"create_date", _getTemplateVariableName("create_date"),
				user.getCreateDate()));

		sxpParameterDataBuilder.addParameter(
			new DateParameter(
				"birthday", _getTemplateVariableName("birthday"),
				user.getBirthday()));

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"age", _getTemplateVariableName("age"),
				_getUserAge(user.getBirthday())));
		sxpParameterDataBuilder.addParameter(
			new BooleanParameter(
				"is_male", _getTemplateVariableName("is_male"), user.isMale()));
		sxpParameterDataBuilder.addParameter(
			new BooleanParameter(
				"is_female", _getTemplateVariableName("is_female"),
				user.isFemale()));
		sxpParameterDataBuilder.addParameter(
			new BooleanParameter(
				"is_gender_x", _getTemplateVariableName("is_gender_x"),
				!user.isFemale() && !user.isMale()));

		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"email_domain", _getTemplateVariableName("email_domain"),
				_getUserEmailDomain(user)));
	}

	private void _addUserSegments(
			SXPParameterDataBuilder sxpParameterDataBuilder,
			SXPAttributes sxpAttributes, User user)
		throws PortalException {

		Optional<Long> optional = _getScopeGroupId(sxpAttributes);

		if (!optional.isPresent()) {
			return;
		}

		Locale locale = sxpAttributes.getLocale();

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

		sxpParameterDataBuilder.addParameter(
			new LongArrayParameter(
				"active_segment_entry_ids",
				_getTemplateVariableName("active_segment_entry_ids"),
				_toBoxedLongArray(segmentsEntryIds)));
	}

	private void _contribute(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, long userId) {

		try {
			User user = _userLocalService.getUser(userId);

			_addUserInfo(sxpParameterDataBuilder, user);

			_addGroupIds(sxpParameterDataBuilder, user);

			_addUserGroupGroupIds(sxpParameterDataBuilder, user);

			_addCurrentSiteRoleIds(
				sxpParameterDataBuilder, sxpAttributes, user);

			_addRegularUserRoleIds(sxpParameterDataBuilder, user);

			_addUserSegments(sxpParameterDataBuilder, sxpAttributes, user);
		}
		catch (Exception exception) {
			ProblemUtil.addError(
				getClass().getName(), "there-was-an-unknown-error", null, null,
				null, exception);
		}
	}

	private long[] _getCurrentSiteRoleIds(
		SXPAttributes sxpAttributes, long userId) {

		long[] userGroupRoleIds = _getUserGroupRoleIds(userId);

		Optional<Long> optional = _getScopeGroupId(sxpAttributes);

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

	private Optional<Long> _getScopeGroupId(SXPAttributes sxpAttributes) {
		return _sxpAttributeValuesHelper.getLongOptional(
			sxpAttributes, "scope_group_id");
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
					ProblemUtil.addError(
						getClass().getName(), "there-was-an-unknown-error",
						null, null, null, portalException);
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

	private Long _getUserId(SXPAttributes sxpAttributes) {
		return GetterUtil.getLong(sxpAttributes.getUserId());
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
	private SXPAttributeValuesHelper _sxpAttributeValuesHelper;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}