/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class WorkflowHandlerUtil {

	public static String[] getSearchableAssetTypes() {
		List<String> assetTypes = new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (!workflowHandler.isAssetTypeSearchable()) {
				continue;
			}

			assetTypes.add(workflowHandler.getClassName());
		}

		return assetTypes.toArray(new String[0]);
	}

}