/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Creator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseImportTaskPreAction<T>
	implements ImportTaskPreAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
			ImportTaskContext importTaskContext, Object item)
		throws Exception {

		if (!Objects.equals(item.getClass(), getItemClass()) ||
			!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		User user = _getCreatorUser(getCreator(item));

		if (user == null) {
			return;
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId == user.getUserId()) {
			return;
		}

		importTaskContext.setOriginalUser(userLocalService.getUser(userId));

		PrincipalThreadLocal.setName(user.getUserId());

		batchEngineTaskItemDelegate.setContextUser(user);
	}

	protected abstract Creator getCreator(Object item);

	protected abstract Class<T> getItemClass();

	@Reference
	protected UserLocalService userLocalService;

	private User _getCreatorUser(Creator creator) {
		if (creator == null) {
			return null;
		}

		User user = null;

		if (Validator.isNotNull(creator.getExternalReferenceCode())) {
			user = userLocalService.fetchUserByExternalReferenceCode(
				creator.getExternalReferenceCode(),
				CompanyThreadLocal.getCompanyId());
		}

		if ((user == null) && Validator.isNotNull(creator.getId())) {
			user = userLocalService.fetchUser(creator.getId());
		}

		return user;
	}

}