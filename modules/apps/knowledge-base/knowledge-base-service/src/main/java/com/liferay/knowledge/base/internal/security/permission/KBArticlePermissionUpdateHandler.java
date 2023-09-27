/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.security.permission;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = PermissionUpdateHandler.class
)
public class KBArticlePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		KBArticle kbArticle = _kbArticleLocalService.fetchLatestKBArticle(
			GetterUtil.getLong(primKey), WorkflowConstants.STATUS_ANY);

		if (kbArticle == null) {
			return;
		}

		kbArticle.setModifiedDate(new Date());

		_kbArticleLocalService.updateKBArticle(kbArticle);
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

}