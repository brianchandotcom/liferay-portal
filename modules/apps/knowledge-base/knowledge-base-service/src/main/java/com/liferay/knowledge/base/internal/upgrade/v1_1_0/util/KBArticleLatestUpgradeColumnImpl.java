/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Peter Shin
 */
public class KBArticleLatestUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public KBArticleLatestUpgradeColumnImpl(
		KBArticleAttachmentsHelper kbArticleAttachmentsHelper,
		UpgradeColumn kbArticleIdColumn, UpgradeColumn resourcePrimKeyColumn) {

		super("latest");

		_kbArticleAttachmentsHelper = kbArticleAttachmentsHelper;
		_kbArticleIdColumn = kbArticleIdColumn;
		_resourcePrimKeyColumn = resourcePrimKeyColumn;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		Long kbArticleId = (Long)_kbArticleIdColumn.getOldValue();

		Long resourcePrimKey = (Long)_resourcePrimKeyColumn.getOldValue();

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);

		if (kbArticle.getKbArticleId() != kbArticleId) {
			return Boolean.FALSE;
		}

		_kbArticleAttachmentsHelper.updateAttachments(kbArticle);

		return Boolean.TRUE;
	}

	private final KBArticleAttachmentsHelper _kbArticleAttachmentsHelper;
	private final UpgradeColumn _kbArticleIdColumn;
	private final UpgradeColumn _resourcePrimKeyColumn;

}