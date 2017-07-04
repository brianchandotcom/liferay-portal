/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.internal.service.permission;

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