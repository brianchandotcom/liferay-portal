/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.util;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.scim.rest.internal.model.ScimUser;

import java.io.File;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.wso2.charon3.core.attributes.Attribute;
import org.wso2.charon3.core.attributes.ComplexAttribute;
import org.wso2.charon3.core.attributes.DefaultAttributeFactory;
import org.wso2.charon3.core.attributes.SimpleAttribute;
import org.wso2.charon3.core.config.SCIMUserSchemaExtensionBuilder;
import org.wso2.charon3.core.objects.Group;
import org.wso2.charon3.core.objects.User;
import org.wso2.charon3.core.objects.plainobjects.MultiValuedComplexType;
import org.wso2.charon3.core.objects.plainobjects.ScimName;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.schema.AttributeSchema;
import org.wso2.charon3.core.schema.SCIMConstants;
import org.wso2.charon3.core.schema.SCIMResourceSchemaManager;
import org.wso2.charon3.core.utils.AttributeUtil;

/**
 * @author Rafael Praxedes
 * @author Olivér Kecskeméty
 */
public class ScimUtil {

	public static final String LIFERAY_USER_SCHEMA_EXTENSION_URI =
		"urn:ietf:params:scim:schemas:extension:liferay:2.0:User";

	public static Group toGroup(List<ScimUser> scimUsers, UserGroup userGroup)
		throws Exception {

		Group group = new Group();

		group.replaceDisplayName(userGroup.getName());

		Date createDate = _truncateDate(userGroup.getCreateDate());

		group.setCreatedInstant(createDate.toInstant());

		group.setExternalId(userGroup.getExternalReferenceCode());
		group.setId(String.valueOf(userGroup.getPrimaryKey()));

		Date modifiedDate = _truncateDate(userGroup.getModifiedDate());

		group.setLastModifiedInstant(modifiedDate.toInstant());

		group.setLocation(
			StringBundler.concat(
				AbstractResourceManager.getResourceEndpointURL(
					SCIMConstants.GROUP_ENDPOINT),
				CharPool.FORWARD_SLASH, userGroup.getPrimaryKey()));

		for (ScimUser scimUser : scimUsers) {
			group.setMember(toUser(Collections.emptyList(), scimUser));
		}

		group.setResourceType(SCIMConstants.GROUP);
		group.setSchemas();

		return group;
	}

	public static ScimUser toScimUser(long companyId, Locale locale, User user)
		throws Exception {

		ScimUser scimUser = new ScimUser();

		scimUser.setActive(user.getActive());
		scimUser.setAutoScreenName(
			PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));
		scimUser.setAutoPassword(user.getPassword() == null);
		scimUser.setBirthday(_getBirthday(user));
		scimUser.setCompanyId(companyId);
		scimUser.setEmailAddress(_getEmailAddress(user));
		scimUser.setExternalReferenceCode(user.getExternalId());

		ScimName scimName = user.getName();

		scimUser.setFirstName(scimName.getGivenName());

		scimUser.setId(user.getId());
		scimUser.setJobTitle(user.getTitle());
		scimUser.setLastName(scimName.getFamilyName());
		scimUser.setLocale(locale);
		scimUser.setMale(_isMale(user));
		scimUser.setMiddleName(scimName.getMiddleName());
		scimUser.setPassword(user.getPassword());
		scimUser.setScreenName(user.getUserName());

		_validate(scimUser);

		return scimUser;
	}

	public static ScimUser toScimUser(
		com.liferay.portal.kernel.model.User portalUser) {

		try {
			ScimUser scimUser = new ScimUser();

			scimUser.setActive(portalUser.isActive());
			scimUser.setBirthday(portalUser.getBirthday());
			scimUser.setCompanyId(portalUser.getCompanyId());
			scimUser.setCreateDate(_truncateDate(portalUser.getCreateDate()));
			scimUser.setFirstName(portalUser.getFirstName());
			scimUser.setEmailAddress(portalUser.getEmailAddress());
			scimUser.setExternalReferenceCode(
				portalUser.getExternalReferenceCode());
			scimUser.setId(String.valueOf(portalUser.getUserId()));
			scimUser.setJobTitle(portalUser.getJobTitle());
			scimUser.setLastName(portalUser.getLastName());
			scimUser.setLocale(portalUser.getLocale());
			scimUser.setMale(portalUser.isMale());
			scimUser.setMiddleName(portalUser.getMiddleName());
			scimUser.setModifiedDate(
				_truncateDate(portalUser.getModifiedDate()));
			scimUser.setScreenName(portalUser.getScreenName());

			return scimUser;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to convert portal user to a SCIM user",
					portalException);
			}

			return ReflectionUtil.throwException(portalException);
		}
	}

	public static User toUser(List<Group> groups, ScimUser scimUser)
		throws Exception {

		User user = new User();

		user.replaceActive(scimUser.isActive());
		user.replaceEmails(
			Collections.singletonList(
				new MultiValuedComplexType(
					"default", true, null, scimUser.getEmailAddress(), null)));

		ScimName scimName = new ScimName();

		scimName.setFamilyName(scimUser.getLastName());
		scimName.setGivenName(scimUser.getFirstName());
		scimName.setMiddleName(scimUser.getMiddleName());

		user.replaceName(scimName);

		user.replaceTitle(scimUser.getJobTitle());

		SCIMResourceSchemaManager scimResourceSchemaManager =
			SCIMResourceSchemaManager.getInstance();

		user.setAttribute(
			_createLiferayUserExtensionComplexAttribute(scimUser),
			scimResourceSchemaManager.getUserResourceSchema());

		Date createDate = scimUser.getCreateDate();

		user.setCreatedInstant(createDate.toInstant());

		user.setExternalId(scimUser.getExternalReferenceCode());

		for (Group group : groups) {
			user.setGroup("direct", group);
		}

		user.setId(scimUser.getId());

		Date modifiedDate = scimUser.getModifiedDate();

		user.setLastModifiedInstant(modifiedDate.toInstant());

		user.setLocation(
			StringBundler.concat(
				AbstractResourceManager.getResourceEndpointURL(
					SCIMConstants.USER_ENDPOINT),
				CharPool.FORWARD_SLASH, scimUser.getId()));
		user.setResourceType(SCIMConstants.USER);
		user.setSchemas();
		user.setUserName(scimUser.getScreenName());

		return user;
	}

	private static AttributeSchema _createAttributeSchema() {
		SCIMUserSchemaExtensionBuilder scimUserSchemaExtensionBuilder =
			SCIMUserSchemaExtensionBuilder.getInstance();

		String json = JSONUtil.putAll(
			JSONUtil.put(
				"attributeName", "birthday"
			).put(
				"attributeURI", LIFERAY_USER_SCHEMA_EXTENSION_URI + ":birthday"
			).put(
				"canonicalValues", JSONFactoryUtil.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "string"
			).put(
				"description", "User's birthday"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", JSONFactoryUtil.createJSONArray()
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "null"
			).put(
				"uniqueness", "none"
			),
			JSONUtil.put(
				"attributeName", "male"
			).put(
				"attributeURI", LIFERAY_USER_SCHEMA_EXTENSION_URI + ":male"
			).put(
				"canonicalValues", JSONFactoryUtil.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "boolean"
			).put(
				"description", "User's gender"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", JSONFactoryUtil.createJSONArray()
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "null"
			).put(
				"uniqueness", "none"
			),
			JSONUtil.put(
				"attributeName", LIFERAY_USER_SCHEMA_EXTENSION_URI
			).put(
				"attributeURI", LIFERAY_USER_SCHEMA_EXTENSION_URI
			).put(
				"canonicalValues", JSONFactoryUtil.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "complex"
			).put(
				"description", "Liferay's User Schema Extension"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", JSONUtil.put("external")
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "birthday male"
			).put(
				"uniqueness", "none"
			)
		).toString();

		try {
			File file = FileUtil.createTempFile(json.getBytes());

			scimUserSchemaExtensionBuilder.buildUserSchemaExtension(
				file.getPath());
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}

		return scimUserSchemaExtensionBuilder.getExtensionSchema();
	}

	private static ComplexAttribute _createLiferayUserExtensionComplexAttribute(
			ScimUser scimUser)
		throws Exception {

		AttributeSchema attributeSchema =
			_attributeSchemaDCLSingleton.getSingleton(
				ScimUtil::_createAttributeSchema);

		ComplexAttribute complexAttribute = new ComplexAttribute(
			attributeSchema.getName());

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_PATTERN);

		complexAttribute.setSubAttributesList(
			HashMapBuilder.<String, Attribute>put(
				"birthday",
				_createSimpleAttribute(
					attributeSchema.getSubAttributeSchema("birthday"),
					dateFormat.format(scimUser.getBirthday()))
			).put(
				"male",
				_createSimpleAttribute(
					attributeSchema.getSubAttributeSchema("male"),
					scimUser.isMale())
			).build());

		return (ComplexAttribute)DefaultAttributeFactory.createAttribute(
			attributeSchema, complexAttribute);
	}

	private static SimpleAttribute _createSimpleAttribute(
			AttributeSchema attributeSchema, Object attributeValue)
		throws Exception {

		return (SimpleAttribute)DefaultAttributeFactory.createAttribute(
			attributeSchema,
			new SimpleAttribute(
				attributeSchema.getName(),
				AttributeUtil.getAttributeValueFromString(
					attributeValue, attributeSchema.getType())));
	}

	private static Date _getBirthday() {
		Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar(
			1970, Calendar.JANUARY, 1);

		return birthdayCalendar.getTime();
	}

	private static Date _getBirthday(User user) {
		try {
			ComplexAttribute complexAttribute =
				(ComplexAttribute)user.getAttribute(
					LIFERAY_USER_SCHEMA_EXTENSION_URI);

			if (complexAttribute == null) {
				return _getBirthday();
			}

			SimpleAttribute simpleAttribute =
				(SimpleAttribute)complexAttribute.getSubAttribute("birthday");

			if (simpleAttribute == null) {
				return _getBirthday();
			}

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				_PATTERN);

			return dateFormat.parse(simpleAttribute.getStringValue());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _getBirthday();
		}
	}

	private static String _getEmailAddress(User user) {
		List<MultiValuedComplexType> multiValuedComplexTypes = user.getEmails();

		if (ListUtil.isEmpty(multiValuedComplexTypes)) {
			return null;
		}

		for (MultiValuedComplexType multiValuedComplexType :
				multiValuedComplexTypes) {

			if (multiValuedComplexType.isPrimary()) {
				return multiValuedComplexType.getValue();
			}
		}

		MultiValuedComplexType multiValuedComplexType =
			multiValuedComplexTypes.get(0);

		return multiValuedComplexType.getValue();
	}

	private static boolean _isMale(User user) {
		try {
			ComplexAttribute complexAttribute =
				(ComplexAttribute)user.getAttribute(
					LIFERAY_USER_SCHEMA_EXTENSION_URI);

			if (complexAttribute == null) {
				return true;
			}

			SimpleAttribute simpleAttribute =
				(SimpleAttribute)complexAttribute.getSubAttribute("male");

			if (simpleAttribute == null) {
				return true;
			}

			return simpleAttribute.getBooleanValue();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return true;
		}
	}

	private static Date _truncateDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	private static void _validate(ScimUser scimUser) throws Exception {
		if (!scimUser.isAutoScreenName() &&
			Validator.isNull(scimUser.getScreenName())) {

			throw new UserScreenNameException.MustNotBeNull(
				ContactConstants.getFullName(
					scimUser.getFirstName(), scimUser.getMiddleName(),
					scimUser.getLastName()));
		}

		if (Validator.isNull(scimUser.getEmailAddress()) &&
			PrefsPropsUtil.getBoolean(
				scimUser.getCompanyId(),
				PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED)) {

			throw new UserEmailAddressException.MustNotBeNull(
				ContactConstants.getFullName(
					scimUser.getFirstName(), scimUser.getMiddleName(),
					scimUser.getLastName()));
		}
	}

	private static final String _PATTERN = "yyyy-MM-dd'T'HH:mm:ssXX";

	private static final Log _log = LogFactoryUtil.getLog(ScimUtil.class);

	private static final DCLSingleton<AttributeSchema>
		_attributeSchemaDCLSingleton = new DCLSingleton<>();

}