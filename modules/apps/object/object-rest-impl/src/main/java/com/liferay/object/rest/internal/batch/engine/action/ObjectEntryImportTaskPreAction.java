/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 */
@Component(service = ImportTaskPreAction.class)
public class ObjectEntryImportTaskPreAction implements ImportTaskPreAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			ImportTaskContext importTaskContext, Object item)
		throws Exception {

		if (!(item instanceof ObjectEntry)) {
			return;
		}

		Map<String, Serializable> parameters =
			batchEngineImportTask.getParameters();

		String importCreatorStrategy = (String)parameters.getOrDefault(
			"importCreatorStrategy", "OVERWRITE_CREATOR");

		if (!importCreatorStrategy.equals("KEEP_CREATOR")) {
			return;
		}

		ObjectEntry objectEntry = (ObjectEntry)item;

		User user = _getCreatorUser(objectEntry);

		if (user == null) {
			return;
		}

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private User _getCreatorUser(ObjectEntry objectEntry) {
		if ((objectEntry == null) || (objectEntry.getCreator() == null)) {
			return null;
		}

		Creator creator = objectEntry.getCreator();
		User user = null;

		if (Validator.isNotNull(creator.getExternalReferenceCode())) {
			user = _userLocalService.fetchUserByExternalReferenceCode(
				creator.getExternalReferenceCode(),
				CompanyThreadLocal.getCompanyId());
		}

		if ((user == null) && Validator.isNotNull(creator.getId())) {
			user = _userLocalService.fetchUser(creator.getId());
		}

		return user;
	}

	@Reference
	private UserLocalService _userLocalService;

}