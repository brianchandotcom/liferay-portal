/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.bulk.selection.constants.BulkSelectionActionStatusConstants;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBulkActionTaskComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "bulk-actions";
	}

	protected int getBulkActionTaskItemsCount(
			String executionStatus, HttpServletRequest httpServletRequest)
		throws PortalException {

		ObjectEntry cmsBulkActionTaskObjectEntry =
			(ObjectEntry)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

		if (cmsBulkActionTaskObjectEntry == null) {
			return -1;
		}

		Map<String, Serializable> values =
			cmsBulkActionTaskObjectEntry.getValues();

		String status = (String)values.get("executionStatus");

		if (StringUtil.equals(
				status, BulkSelectionActionStatusConstants.COMPLETED)) {

			return GetterUtil.getInteger(values.get("numberOfSuccessfulItems"));
		}
		else if (StringUtil.equals(
					status, BulkSelectionActionStatusConstants.FAILED)) {

			return GetterUtil.getInteger(values.get("numberOfFailedItems"));
		}

		return -1;
	}

}