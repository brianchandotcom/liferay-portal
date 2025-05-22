/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseImportTaskPostAction<T>
	implements ImportTaskPostAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
			ImportTaskContext importTaskContext, Object item,
			Object persistedItem)
		throws Exception {

		if (!Objects.equals(item.getClass(), getItemClass()) ||
			!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		User originalUser = importTaskContext.getOriginalUser();

		if (originalUser != null) {
			PrincipalThreadLocal.setName(originalUser.getUserId());
			importTaskContext.setOriginalUser(null);
		}
	}

	protected abstract Class<T> getItemClass();

}