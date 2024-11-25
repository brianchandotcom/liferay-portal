/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 */
@Component(service = ImportTaskPostAction.class)
public class ObjectEntryImportTaskPostAction implements ImportTaskPostAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			ImportTaskContext importTaskContext, Object item,
			Object persistedItem)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-11036") ||
			!(item instanceof ObjectEntry) ||
			!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		if (Validator.isNotNull(importTaskContext.getOriginalUserId())) {
			PrincipalThreadLocal.setName(importTaskContext.getOriginalUserId());

			importTaskContext.setOriginalUserId(null);
		}
	}

}