/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.action;

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.util.VulcanBatchEngineTaskItemDelegateThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
@Component(service = ImportTaskPostAction.class)
public class ItemImportTaskPostAction implements ImportTaskPostAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			ImportTaskContext importTaskContext, Object item,
			Object persistedItem)
		throws Exception {

		if (!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		if (Validator.isNotNull(importTaskContext.getOriginalUserId())) {
			PrincipalThreadLocal.setName(importTaskContext.getOriginalUserId());

			VulcanBatchEngineTaskItemDelegate<?> delegate =
				VulcanBatchEngineTaskItemDelegateThreadLocal.get();

			if (delegate != null) {
				User user = _userLocalService.fetchUser(
					GetterUtil.getLong(importTaskContext.getOriginalUserId()));

				delegate.setContextUser(user);
			}

			importTaskContext.setOriginalUserId(null);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}