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

package com.liferay.account.internal.model.listener;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_validateAccountEntry(
				objectEntry.getUserId(), objectEntry.getObjectDefinitionId(),
				objectEntry.getValues());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_validateAccountEntry(
				objectEntry.getUserId(), objectEntry.getObjectDefinitionId(),
				objectEntry.getValues());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _validateAccountEntry(
			long userId, long objectDefinitionId,
			Map<String, Serializable> values)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		if (!objectDefinition.isAccountEntryRestricted()) {
			return;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		if (!values.containsKey(objectField.getName())) {
			return;
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		long accountEntryId = GetterUtil.getLong(
			values.get(objectField.getName()));

		for (AccountEntry accountEntry : accountEntries) {
			if (accountEntryId == accountEntry.getAccountEntryId()) {
				return;
			}
		}

		throw new ObjectDefinitionAccountEntryRestrictedException(
			StringBundler.concat(
				"The account entry ", accountEntryId,
				" does not exist or the user ", userId,
				" does not belong to it"));
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}