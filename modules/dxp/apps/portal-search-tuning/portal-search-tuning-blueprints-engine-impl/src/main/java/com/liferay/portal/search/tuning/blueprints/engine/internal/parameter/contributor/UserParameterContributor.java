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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.BooleanParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DateParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=user",
	service = ParameterContributor.class
)
public class UserParameterContributor implements ParameterContributor {

	@Override
	public void contribute(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		User user = _getUser(blueprintsAttributes, messages);

		_addUserInfo(parameterDataBuilder, messages, user);

		_addUserGroupIds(parameterDataBuilder, user);

		_addUserRoleIds(parameterDataBuilder, user);

		_addUserSegments(parameterDataBuilder, blueprintsAttributes, user);
	}

	@Override
	public String getCategoryNameKey() {
		return "user";
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_ID.getKey()),
				LongParameter.class.getName(), "core.parameter.user.id"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_IS_SIGNED_IN.getKey()),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-signed-in"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_FULL_NAME.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.full-name"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_FIRST_NAME.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.first-name"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_LAST_NAME.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.last-name"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_LANGUAGE_ID.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.language-id"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_JOB_TITLE.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.job-title"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_CREATE_DATE.getKey()),
				DateParameter.class.getName(),
				"core.parameter.user.create-date"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_BIRTHDAY.getKey()),
				DateParameter.class.getName(), "core.parameter.user.birthday"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_AGE.getKey()),
				IntegerParameter.class.getName(), "core.parameter.user.age"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_IS_MALE.getKey()),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-male"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_IS_FEMALE.getKey()),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-female"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_IS_GENDER_X.getKey()),
				BooleanParameter.class.getName(),
				"core.parameter.user.is-gender-x"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_EMAIL_DOMAIN.getKey()),
				StringParameter.class.getName(),
				"core.parameter.user.email-domain"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_GROUP_IDS.getKey()),
				LongArrayParameter.class.getName(),
				"core.parameter.user.group-ids"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_ROLE_IDS.getKey()),
				LongArrayParameter.class.getName(),
				"core.parameter.user.role-ids"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_SEGMENT_ENTRY_IDS.getKey()),
				LongArrayParameter.class.getName(),
				"core.parameter.user.segment-entry-ids"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.USER_SEGMENT_ENTRY_LOCALE_NAMES.
						getKey()),
				StringArrayParameter.class.getName(),
				"core.parameter.user.segment-entry-locale-names"));

		return parameterDefinitions;
	}

	private void _addUserGroupIds(
		ParameterDataBuilder parameterDataBuilder, User user) {

		parameterDataBuilder.addParameter(
			new LongArrayParameter(
				ReservedParameterNames.USER_GROUP_IDS.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_GROUP_IDS.getKey()),
				LongStream.of(
					user.getGroupIds()
				).boxed(
				).toArray(
					Long[]::new
				)));
	}

	private void _addUserInfo(
		ParameterDataBuilder parameterDataBuilder, Messages messages,
		User user) {

		parameterDataBuilder.addParameter(
			new LongParameter(
				ReservedParameterNames.USER_ID.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_ID.getKey()),
				user.getUserId()));
		parameterDataBuilder.addParameter(
			new BooleanParameter(
				ReservedParameterNames.USER_IS_SIGNED_IN.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_IS_SIGNED_IN.getKey()),
				_isSignedIn(user)));
		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_FULL_NAME.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_FULL_NAME.getKey()),
				user.getFullName()));
		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_FIRST_NAME.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_FIRST_NAME.getKey()),
				user.getFirstName()));
		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_LAST_NAME.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_LAST_NAME.getKey()),
				user.getLastName()));
		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_LANGUAGE_ID.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_LANGUAGE_ID.getKey()),
				user.getLanguageId()));
		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_JOB_TITLE.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_JOB_TITLE.getKey()),
				user.getJobTitle()));
		parameterDataBuilder.addParameter(
			new DateParameter(
				ReservedParameterNames.USER_CREATE_DATE.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_CREATE_DATE.getKey()),
				user.getCreateDate()));

		try {
			parameterDataBuilder.addParameter(
				new DateParameter(
					ReservedParameterNames.USER_BIRTHDAY.getKey(),
					_getTemplateVariableName(
						ReservedParameterNames.USER_BIRTHDAY.getKey()),
					user.getBirthday()));

			parameterDataBuilder.addParameter(
				new IntegerParameter(
					ReservedParameterNames.USER_AGE.getKey(),
					_getTemplateVariableName(
						ReservedParameterNames.USER_AGE.getKey()),
					_getUserAge(user.getBirthday())));
			parameterDataBuilder.addParameter(
				new BooleanParameter(
					ReservedParameterNames.USER_IS_MALE.getKey(),
					_getTemplateVariableName(
						ReservedParameterNames.USER_IS_MALE.getKey()),
					user.isMale()));
			parameterDataBuilder.addParameter(
				new BooleanParameter(
					ReservedParameterNames.USER_IS_FEMALE.getKey(),
					_getTemplateVariableName(
						ReservedParameterNames.USER_IS_FEMALE.getKey()),
					user.isFemale()));
			parameterDataBuilder.addParameter(
				new BooleanParameter(
					ReservedParameterNames.USER_IS_GENDER_X.getKey(),
					_getTemplateVariableName(
						ReservedParameterNames.USER_IS_GENDER_X.getKey()),
					!user.isFemale() && !user.isMale()));
		}
		catch (NumberFormatException numberFormatException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-exception"
				).msg(
					numberFormatException.getMessage()
				).rootValue(
					String.valueOf(user.getUserId())
				).severity(
					Severity.ERROR
				).throwable(
					numberFormatException
				).build());

			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}
		catch (PortalException portalException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-exception"
				).msg(
					portalException.getMessage()
				).rootValue(
					String.valueOf(user.getUserId())
				).severity(
					Severity.ERROR
				).throwable(
					portalException
				).build());

			_log.error(portalException.getMessage(), portalException);
		}

		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.USER_EMAIL_DOMAIN.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_EMAIL_DOMAIN.getKey()),
				_getUserEmailDomain(user)));
	}

	private void _addUserRoleIds(
		ParameterDataBuilder parameterDataBuilder, User user) {

		parameterDataBuilder.addParameter(
			new LongArrayParameter(
				ReservedParameterNames.USER_ROLE_IDS.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_ROLE_IDS.getKey()),
				LongStream.of(
					user.getRoleIds()
				).boxed(
				).toArray(
					Long[]::new
				)));
	}

	private void _addUserSegments(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes, User user) {

		long[] groupIds = _getUserAccessibleSiteGroupIds(
			blueprintsAttributes.getCompanyId(), user);

		if (groupIds.length == 0) {
			return;
		}

		List<Long> segmentEntryIds = new ArrayList<>();
		List<String> segmentEntryNames = new ArrayList<>();

		for (long groupId : groupIds) {
			List<SegmentsEntry> segmentsEntries =
				_segmentsEntryLocalService.getSegmentsEntries(
					groupId, true, User.class.getName(), 0, 25, null);

			segmentsEntries.forEach(
				entry -> {
					segmentEntryIds.add(entry.getSegmentsEntryId());
					segmentEntryNames.add(
						entry.getName(blueprintsAttributes.getLocale(), true));
				});
		}

		if (segmentEntryIds.isEmpty()) {
			return;
		}

		Stream<Long> stream1 = segmentEntryIds.stream();

		parameterDataBuilder.addParameter(
			new LongArrayParameter(
				ReservedParameterNames.USER_SEGMENT_ENTRY_IDS.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_SEGMENT_ENTRY_IDS.getKey()),
				stream1.toArray(Long[]::new)));

		Stream<String> stream2 = segmentEntryNames.stream();

		parameterDataBuilder.addParameter(
			new StringArrayParameter(
				ReservedParameterNames.USER_SEGMENT_ENTRY_LOCALE_NAMES.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.USER_SEGMENT_ENTRY_LOCALE_NAMES.
						getKey()),
				stream2.toArray(String[]::new)));
	}

	private String _getTemplateVariableName(String key) {
		StringBundler sb = new StringBundler(3);

		sb.append("${user.");
		sb.append(key);
		sb.append("}");

		return sb.toString();
	}

	private User _getUser(
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		long userId = blueprintsAttributes.getUserId();

		if (userId == 0) {
			return null;
		}

		try {
			return _userLocalService.getUser(userId);
		}
		catch (PortalException portalException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.user-not-found"
				).msg(
					portalException.getMessage()
				).rootValue(
					String.valueOf(userId)
				).severity(
					Severity.ERROR
				).throwable(
					portalException
				).build());

			_log.error(portalException.getMessage(), portalException);
		}

		return null;
	}

	private long[] _getUserAccessibleSiteGroupIds(long companyId, User user) {
		List<Long> groupIds = new ArrayList<>();

		try {
			Company company = _companyLocalService.getCompany(companyId);

			long companyGroupId = company.getGroupId();

			groupIds.add(companyGroupId);

			for (Group group :
					_groupLocalService.getGroups(companyId, 0, true)) {

				if (group.isActive() && !group.isStagingGroup() &&
					group.hasPublicLayouts()) {

					groupIds.add(group.getGroupId());
				}
			}

			for (Group group : user.getSiteGroups()) {
				if (!groupIds.contains(group.getGroupId()) &&
					group.isActive() && !group.isStagingGroup()) {

					groupIds.add(group.getGroupId());
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);
		}

		groupIds.toArray(new Long[0]);

		Stream<Long> stream = groupIds.stream();

		return stream.mapToLong(
			l -> l
		).toArray();
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

	private Boolean _isSignedIn(User user) {
		return !user.isDefaultUser();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserParameterContributor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}