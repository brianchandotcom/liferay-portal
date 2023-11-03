/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.scim.rest.internal.ScimUser;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import org.wso2.charon3.core.attributes.Attribute;
import org.wso2.charon3.core.attributes.ComplexAttribute;
import org.wso2.charon3.core.attributes.DefaultAttributeFactory;
import org.wso2.charon3.core.attributes.SimpleAttribute;
import org.wso2.charon3.core.config.SCIMUserSchemaExtensionBuilder;
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
 */
public class ModelConverterUtil {

	public static ScimUser toScimUser(long companyId, Locale locale, User user)
		throws Exception {

		ScimUser scimUser = new ScimUser();

		scimUser.setActive(user.getActive());
		scimUser.setAutoScreenName(
			PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));
		scimUser.setAutoPassword(user.getPassword() == null);
		scimUser.setBirthday(_getBirthday(locale, user));
		scimUser.setCompanyId(companyId);
		scimUser.setEmailAddress(_getEmailAddress(user));
		scimUser.setExternalReferenceCode(user.getExternalId());

		ScimName scimName = user.getName();

		scimUser.setFirstName(scimName.getGivenName());

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

	public static User toUser(ScimUser scimUser) throws Exception {
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

	private static ComplexAttribute _createLiferayUserExtensionComplexAttribute(
			ScimUser scimUser)
		throws Exception {

		SCIMUserSchemaExtensionBuilder scimUserSchemaExtensionBuilder =
			SCIMUserSchemaExtensionBuilder.getInstance();

		AttributeSchema attributeSchema =
			scimUserSchemaExtensionBuilder.getExtensionSchema();

		ComplexAttribute complexAttribute = new ComplexAttribute(
			attributeSchema.getName());

		complexAttribute.setSubAttributesList(
			HashMapBuilder.<String, Attribute>put(
				"birthday",
				_createSimpleAttribute(
					attributeSchema.getSubAttributeSchema("birthday"),
					DateUtil.getDate(
						scimUser.getBirthday(), "yyyy-MM-dd",
						scimUser.getLocale()))
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

	private static Date _getBirthday(Locale locale, User user) {
		Supplier<Date> supplier = () -> {
			Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar(
				1970, Calendar.JANUARY, 1);

			return birthdayCalendar.getTime();
		};

		try {
			ComplexAttribute complexAttribute =
				(ComplexAttribute)user.getAttribute(
					_LIFERAY_USER_SCHEMA_EXTENSION_URI);

			if (complexAttribute == null) {
				return supplier.get();
			}

			SimpleAttribute simpleAttribute =
				(SimpleAttribute)complexAttribute.getSubAttribute("birthday");

			if (simpleAttribute == null) {
				return supplier.get();
			}

			return DateUtil.parseDate(
				"yyyy-MM-dd", simpleAttribute.getStringValue(), locale);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return supplier.get();
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
					_LIFERAY_USER_SCHEMA_EXTENSION_URI);

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
		}

		return true;
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

	private static final String _LIFERAY_USER_SCHEMA_EXTENSION_URI =
		"urn:ietf:params:scim:schemas:extension:liferay:2.0:User";

	private static final Log _log = LogFactoryUtil.getLog(
		ModelConverterUtil.class);

}