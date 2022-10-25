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

package com.liferay.object.entry.permission.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryPermissionUtil {

	public static void checkAccountEntryPermission(
			AccountEntryLocalService accountEntryLocalService, String actionId,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			ObjectFieldLocalService objectFieldLocalService, long userId)
		throws PortalException {

		if (!objectDefinition.isAccountEntryRestricted()) {
			return;
		}

		ObjectField objectField = objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		long accountEntryId = MapUtil.getLong(
			objectEntry.getValues(), objectField.getName());

		if (accountEntryId == 0) {
			return;
		}

		long[] accountEntryIds = ListUtil.toLongArray(
			accountEntryLocalService.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			AccountEntry::getAccountEntryId);

		if (ArrayUtil.contains(accountEntryIds, accountEntryId)) {
			return;
		}

		if (StringUtil.equals(actionId, ActionKeys.DELETE) ||
			StringUtil.equals(actionId, ActionKeys.VIEW)) {

			throw new ObjectDefinitionAccountEntryRestrictedException(
				StringBundler.concat(
					"User ", userId, " must have ", actionId,
					" permission for ", objectDefinition.getClassName(),
					objectEntry.getObjectEntryId()));
		}

		throw new ObjectDefinitionAccountEntryRestrictedException(
			StringBundler.concat(
				"The account entry ", accountEntryId,
				" does not exist or the user ", userId,
				" does not belong to it"));
	}

}