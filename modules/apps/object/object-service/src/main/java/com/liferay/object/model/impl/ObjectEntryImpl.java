/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.entry.util.ObjectEntryValuesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryImpl extends ObjectEntryBaseImpl {

	@Override
	public ObjectEntry cloneWithOriginalValues() {
		ObjectEntry objectEntry = super.cloneWithOriginalValues();

		objectEntry.setValues(_transientValues);

		return objectEntry;
	}

	@Override
	public String getModelClassName() {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				getObjectDefinitionId());

		if (objectDefinition == null) {
			return StringPool.BLANK;
		}

		return objectDefinition.getClassName();
	}

	@Override
	public long getNonzeroGroupId() throws PortalException {

		// TODO If permission checking works with the group's company ID, then
		// we should ensure it is always set and remove this workaround

		long groupId = getGroupId();

		if (groupId == 0) {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			groupId = company.getGroupId();
		}

		return groupId;
	}

	@Override
	public String getTitleValue() throws PortalException {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.getObjectDefinition(
				getObjectDefinitionId());

		if ((objectDefinition != null) &&
			(objectDefinition.getTitleObjectFieldId() > 0)) {

			ObjectField objectField =
				ObjectFieldLocalServiceUtil.fetchObjectField(
					objectDefinition.getTitleObjectFieldId());

			if (objectField != null) {
				String title = ObjectEntryValuesUtil.getValueString(
					objectField, getValues());

				if (Validator.isNotNull(title)) {
					return title;
				}

				if (!Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) &&
					!Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT) &&
					Objects.equals(objectField.getName(), "id")) {

					return String.valueOf(getObjectEntryId());
				}

				return ObjectEntryValuesUtil.getValueString(
					objectField,
					ObjectEntryLocalServiceUtil.getSystemValues(this));
			}
		}

		return String.valueOf(getObjectEntryId());
	}

	@Override
	public String getURLTitle(Locale locale) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-21926")) {
			return null;
		}

		FriendlyURLEntry friendlyURLEntry =
			FriendlyURLEntryLocalServiceUtil.fetchMainFriendlyURLEntry(
				ClassNameLocalServiceUtil.getClassNameId(getModelClassName()),
				getObjectEntryId());

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLEntry.getUrlTitle(LocaleUtil.toLanguageId(locale));
	}

	@Override
	public Map<String, String> getURLTitleMap() throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-21926")) {
			return null;
		}

		FriendlyURLEntry friendlyURLEntry =
			FriendlyURLEntryLocalServiceUtil.fetchMainFriendlyURLEntry(
				ClassNameLocalServiceUtil.getClassNameId(getModelClassName()),
				getObjectEntryId());

		if (friendlyURLEntry == null) {
			return null;
		}

		return friendlyURLEntry.getLanguageIdToUrlTitleMap();
	}

	@Override
	public Map<String, Serializable> getValues() {
		if (_values == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Get values for object entry " + getObjectEntryId());
			}

			try {
				_values = ObjectEntryLocalServiceUtil.getValues(this);
			}
			catch (Exception exception) {
				_log.error(exception);

				return new HashMap<>();
			}
		}
		else if (_log.isDebugEnabled()) {
			_log.debug(
				"Use cached values for object entry " + getObjectEntryId());
		}

		return _values;
	}

	@Override
	public void setTransientValues(Map<String, Serializable> values) {
		_transientValues = values;
	}

	@Override
	public void setValues(Map<String, Serializable> values) {
		_values = values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryImpl.class);

	private Map<String, Serializable> _transientValues;
	private Map<String, Serializable> _values;

}