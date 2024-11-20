/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

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

		PrincipalThreadLocal.setName(
			PermissionThreadLocal.getPermissionChecker(
			).getUserId());
	}

}