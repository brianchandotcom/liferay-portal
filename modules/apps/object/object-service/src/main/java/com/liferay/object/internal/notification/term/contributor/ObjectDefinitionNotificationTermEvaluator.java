/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.notification.term.contributor;

import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeTriFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public ObjectDefinitionNotificationTermEvaluator(
		ListTypeLocalService listTypeLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		UserLocalService userLocalService) {

		_listTypeLocalService = listTypeLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map)) {
			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		for (EvaluatorFunction evaluatorFunction : _evaluatorFunctions) {
			String termValue = evaluatorFunction.apply(
				context, termName, termValues);

			if (termValue != null) {
				return termValue;
			}
		}

		return termName;
	}

	@FunctionalInterface
	public interface EvaluatorFunction
		extends UnsafeTriFunction
			<Context, String, Map<String, Object>, String, PortalException> {
	}

	private String _evaluateAuthor(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		String prefix = StringUtil.toUpperCase(
			_objectDefinition.getShortName());

		if (!termName.equals("[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_FIRST_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_ID%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_LAST_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_MIDDLE_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_PREFIX%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_SUFFIX%]") &&
			!termName.equals("[%" + prefix + "_CREATOR%]")) {

			return null;
		}

		User user = _userLocalService.getUser(
			GetterUtil.getLong(termValues.get("creator")));

		if (termName.equals("[%" + prefix + "_CREATOR%]")) {
			if (context.equals(Context.RECIPIENT)) {
				return String.valueOf(termValues.get("creator"));
			}

			return user.getFullName(true, true);
		}

		return _getTermValue(
			StringUtil.removeSubstring(termName, "[%" + prefix + "_AUTHOR_"),
			user);
	}

	private String _evaluateCurrentUser(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		if (!termName.equals("[%CURRENT_USER_EMAIL_ADDRESS%]") &&
			!termName.equals("[%CURRENT_USER_FIRST_NAME%]") &&
			!termName.equals("[%CURRENT_USER_ID%]") &&
			!termName.equals("[%CURRENT_USER_LAST_NAME%]") &&
			!termName.equals("[%CURRENT_USER_MIDDLE_NAME%]") &&
			!termName.equals("[%CURRENT_USER_PREFIX%]") &&
			!termName.equals("[%CURRENT_USER_SUFFIX%]")) {

			return null;
		}

		return _getTermValue(
			StringUtil.removeSubstring(termName, "[%CURRENT_USER_"),
			_userLocalService.getUser(
				GetterUtil.getLong(termValues.get("currentUserId"))));
	}

	private String _evaluateObjectFields(
		Context context, String termName, Map<String, Object> termValues) {

		if (termName.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return termName;
		}

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId())) {

			if (!Objects.equals(
					ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
						_objectDefinition.getShortName(),
						objectField.getName()),
					termName)) {

				continue;
			}

			String termValue = String.valueOf(
				termValues.get(objectField.getName()));

			if (Validator.isNotNull(termValue)) {
				return termValue;
			}

			return String.valueOf(
				termValues.get(objectField.getDBColumnName()));
		}

		return null;
	}

	private String _evaluateParentObjectAuthor(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		ObjectDefinition objectDefinition = null;
		User user = null;

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.
					getObjectRelationshipsByObjectDefinitionId2(
						_objectDefinition.getObjectDefinitionId())) {

			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			String prefix = ObjectRelationshipUtil.getNotificationTermPrefix(
				objectDefinition, objectRelationship);

			if (!termName.equals("[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_FIRST_NAME%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_ID%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_LAST_NAME%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_MIDDLE_NAME%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_PREFIX%]") &&
				!termName.equals("[%" + prefix + "_AUTHOR_SUFFIX%]") &&
				!termName.equals("[%" + prefix + "_CREATOR%]")) {

				continue;
			}

			ObjectField objectField = _objectFieldLocalService.getObjectField(
				objectRelationship.getObjectFieldId2());

			if (!objectDefinition.isSystem()) {
				ObjectEntry objectEntry =
					_objectEntryLocalService.getObjectEntry(
						GetterUtil.getLong(
							termValues.get(objectField.getName())));

				user = _userLocalService.getUser(objectEntry.getUserId());
			}
			else {
				user = _userLocalService.getUser(
					MapUtil.getLong(
						_objectEntryLocalService.getSystemModelAttributes(
							objectDefinition,
							GetterUtil.getLong(
								termValues.get(objectField.getName()))),
						"creator"));
			}

			if (termName.equals("[%" + prefix + "_CREATOR%]")) {
				if (context.equals(Context.RECIPIENT)) {
					return String.valueOf(termValues.get("creator"));
				}

				return user.getFullName(true, true);
			}

			return _getTermValue(
				StringUtil.removeSubstring(
					termName, "[%" + prefix + "_AUTHOR_"),
				user);
		}

		return null;
	}

	private String _evaluateParentObjectFields(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		ObjectDefinition objectDefinition = null;
		ObjectField objectField2 = null;
		String objectFieldName = null;

		outerLoop:
		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.
					getObjectRelationshipsByObjectDefinitionId2(
						_objectDefinition.getObjectDefinitionId())) {

			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			String prefix = ObjectRelationshipUtil.getNotificationTermPrefix(
				objectDefinition, objectRelationship);

			for (ObjectField objectField :
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId())) {

				if (!Objects.equals(
						termName,
						ObjectDefinitionNotificationTermUtil.
							getObjectFieldTermName(
								prefix, objectField.getName()))) {

					continue;
				}

				objectField2 = _objectFieldLocalService.getObjectField(
					objectRelationship.getObjectFieldId2());

				if (Validator.isNull(
						GetterUtil.getLong(
							termValues.get(objectField2.getName())))) {

					return null;
				}

				objectFieldName = objectField.getName();

				break outerLoop;
			}
		}

		if (objectFieldName == null) {
			return null;
		}

		if (objectDefinition.isSystem()) {
			return MapUtil.getString(
				_objectEntryLocalService.getSystemModelAttributes(
					objectDefinition,
					GetterUtil.getLong(termValues.get(objectField2.getName()))),
				objectFieldName);
		}

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			GetterUtil.getLong(termValues.get(objectField2.getName())));

		return MapUtil.getString(
			HashMapBuilder.putAll(
				objectEntry.getValues()
			).put(
				"createDate", objectEntry.getCreateDate()
			).put(
				"externalReferenceCode", objectEntry.getExternalReferenceCode()
			).put(
				"id", objectEntry.getObjectEntryId()
			).put(
				"modifiedDate", objectEntry.getModifiedDate()
			).put(
				"status", objectEntry.getStatus()
			).build(),
			objectFieldName);
	}

	private String _getTermValue(String partialTermName, User user)
		throws PortalException {

		if (partialTermName.equals("EMAIL_ADDRESS%]")) {
			return user.getEmailAddress();
		}
		else if (partialTermName.equals("FIRST_NAME%]")) {
			return user.getFirstName();
		}
		else if (partialTermName.equals("ID%]")) {
			return String.valueOf(user.getUserId());
		}
		else if (partialTermName.equals("LAST_NAME%]")) {
			return user.getLastName();
		}
		else if (partialTermName.equals("MIDDLE_NAME%]")) {
			return user.getMiddleName();
		}
		else if (partialTermName.equals("PREFIX%]") ||
				 partialTermName.equals("SUFFIX%]")) {

			Contact contact = user.fetchContact();

			if (contact == null) {
				return StringPool.BLANK;
			}

			long listTypeId = contact.getPrefixListTypeId();

			if (partialTermName.equals("SUFFIX%]")) {
				listTypeId = contact.getSuffixListTypeId();
			}

			if (listTypeId == 0) {
				return StringPool.BLANK;
			}

			ListType listType = _listTypeLocalService.getListType(listTypeId);

			return listType.getName();
		}

		return null;
	}

	private final List<EvaluatorFunction> _evaluatorFunctions = Arrays.asList(
		this::_evaluateAuthor, this::_evaluateCurrentUser,
		this::_evaluateObjectFields, this::_evaluateParentObjectAuthor,
		this::_evaluateParentObjectFields);
	private final ListTypeLocalService _listTypeLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final UserLocalService _userLocalService;

}