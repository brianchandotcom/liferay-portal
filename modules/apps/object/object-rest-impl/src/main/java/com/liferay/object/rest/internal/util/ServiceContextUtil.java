/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Sergio Jiménez del Coso
 */
public class ServiceContextUtil {

	public static ServiceContext createServiceContext(
		Locale locale, ModelPermissions modelPermissions,
		ObjectEntry objectEntry, long userId) {

		ServiceContext serviceContext = createServiceContext(
			objectEntry, userId);

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(locale));
		serviceContext.setModelPermissions(modelPermissions);

		return serviceContext;
	}

	public static ServiceContext createServiceContext(long objectEntryId) {
		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			ObjectEntryLocalServiceUtil.fetchObjectEntry(objectEntryId);

		if (serviceBuilderObjectEntry == null) {
			return new ServiceContext();
		}

		ServiceContext serviceContext = new ServiceContext();

		if (serviceBuilderObjectEntry.getStatus() ==
				WorkflowConstants.STATUS_DRAFT) {

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return serviceContext;
	}

	public static ServiceContext createServiceContext(
		ObjectEntry objectEntry, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (Validator.isNotNull(objectEntry.getTaxonomyCategoryIds())) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toArray(objectEntry.getTaxonomyCategoryIds()));
		}

		if (Validator.isNotNull(objectEntry.getKeywords())) {
			serviceContext.setAssetTagNames(objectEntry.getKeywords());
		}

		serviceContext.setUserId(userId);

		if (_isObjectEntryDraft(objectEntry.getStatus())) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return serviceContext;
	}

	private static boolean _isObjectEntryDraft(Status status) {
		if ((status != null) &&
			(status.getCode() == WorkflowConstants.STATUS_DRAFT)) {

			return true;
		}

		return false;
	}

}