/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Creator;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ImportTaskPreAction.class)
public class KeywordImportTaskPreAction implements ImportTaskPreAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			ImportTaskContext importTaskContext, Object item)
		throws Exception {

		if (!(item instanceof Keyword) ||
			!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		Keyword keyword = (Keyword)item;

		User user = _getCreatorUser(keyword);

		if (user == null) {
			return;
		}

		String name = PrincipalThreadLocal.getName();

		if (GetterUtil.getLong(name) == user.getUserId()) {
			return;
		}

		PrincipalThreadLocal.setName(user.getUserId());

		importTaskContext.setOriginalUserId(name);
	}

	private User _getCreatorUser(Keyword keyword) {
		if (keyword == null) {
			return null;
		}

		Creator creator = keyword.getCreator();

		if (creator == null) {
			return null;
		}

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