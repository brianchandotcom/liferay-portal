/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;

/**
 * @author Sergio Jiménez del Coso
 */
public class ServiceContextUtil {

	public static ServiceContext createServiceContext(
		ObjectEntry objectEntry, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (Validator.isNotNull(objectEntry.getTaxonomyCategoryIds())) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toArray(objectEntry.getTaxonomyCategoryIds()));
		}

		Map<String, Object> properties = objectEntry.getProperties();

		if (properties.get("categoryIds") != null) {
			serviceContext.setAssetCategoryIds(
				ListUtil.toLongArray(
					(List<String>)properties.get("categoryIds"),
					Long::parseLong));
		}

		if (Validator.isNotNull(objectEntry.getKeywords())) {
			serviceContext.setAssetTagNames(objectEntry.getKeywords());
		}

		if (properties.get("tagNames") != null) {
			serviceContext.setAssetTagNames(
				ArrayUtil.toStringArray(
					(List<String>)properties.get("tagNames")));
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