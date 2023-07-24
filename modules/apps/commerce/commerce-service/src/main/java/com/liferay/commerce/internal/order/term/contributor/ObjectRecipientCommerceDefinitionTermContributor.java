/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ObjectRecipientCommerceDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public ObjectRecipientCommerceDefinitionTermContributor(
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		_objectDefinitionId = objectDefinitionId;
		_objectFieldLocalService = objectFieldLocalService;
		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;

		List<ObjectField> objectFields =
			objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			_objectFieldIds.put(
				StringBundler.concat(
					"[%",
					StringUtil.toUpperCase(
						StringUtil.replace(objectField.getName(), ' ', '_')),
					"%]"),
				objectField.getObjectFieldId());
		}
	}

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		if (!(object instanceof ObjectEntry)) {
			return term;
		}

		ObjectEntry objectEntry = (ObjectEntry)object;

		if (term.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return String.valueOf(objectEntry.getUserId());
		}

		if (term.equals("[%OBJECT_ENTRY_ID%]")) {
			return String.valueOf(objectEntry.getObjectEntryId());
		}

		if (term.startsWith("[%USER_GROUP_")) {
			String[] termParts = term.split("_");

			return _getUserIds(
				_userGroupLocalService.getUserGroup(
					objectEntry.getCompanyId(),
					StringUtil.removeChars(termParts[2], '%', ']')));
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectEntry.getObjectEntryId());

		if (objectField == null) {
			return term;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing term for object field " + objectField.getName());
		}

		Map<String, Serializable> values = objectEntry.getValues();

		return String.valueOf(values.get(objectField.getName()));
	}

	@Override
	public String getLabel(String term, Locale locale) {
		if (term.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return LanguageUtil.get(locale, "creator");
		}

		if (term.equals("[%OBJECT_ENTRY_ID%]")) {
			return LanguageUtil.get(locale, "id");
		}

		if (term.equals("[%USER_GROUP_NAME%]")) {
			return LanguageUtil.get(locale, "user-group-name");
		}

		long objectFieldId = _objectFieldIds.get(term);

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldId);

		return objectField.getLabel(locale);
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_objectFieldIds.keySet());
	}

	private String _getUserIds(UserGroup userGroup) throws PortalException {
		long[] userIds = _userGroupLocalService.getUserPrimaryKeys(
			userGroup.getUserGroupId());

		StringBundler sb = new StringBundler();

		for (long userId : userIds) {
			sb.append(userId);
			sb.append(",");
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectRecipientCommerceDefinitionTermContributor.class);

	private final long _objectDefinitionId;
	private final Map<String, Long> _objectFieldIds = HashMapBuilder.put(
		"[%OBJECT_ENTRY_CREATOR%]", 0L
	).put(
		"[%OBJECT_ENTRY_ID%]", 0L
	).put(
		"[%USER_GROUP_NAME%]", 0L
	).build();
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;

}