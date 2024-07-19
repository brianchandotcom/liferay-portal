/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.helper;

import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class KBArticleLocalSiblingNavigationHelper
	extends BaseKBArticleSiblingNavigationHelper {

	public KBArticleLocalSiblingNavigationHelper(
		KBArticlePersistence kbArticlePersistence) {

		_kbArticlePersistence = kbArticlePersistence;
	}

	@Override
	protected KBArticle fetchFirstChildKBArticle(KBArticle kbArticle) {
		return _kbArticlePersistence.fetchByG_P_M_S_First(
			kbArticle.getGroupId(), kbArticle.getResourcePrimKey(), true,
			WorkflowConstants.STATUS_APPROVED,
			KBArticlePriorityComparator.getInstance(true));
	}

	@Override
	protected KBArticle fetchLastChildKBArticle(KBArticle previousKBArticle) {
		return _kbArticlePersistence.fetchByG_P_M_S_Last(
			previousKBArticle.getGroupId(),
			previousKBArticle.getResourcePrimKey(), true,
			WorkflowConstants.STATUS_APPROVED,
			KBArticlePriorityComparator.getInstance(true));
	}

	@Override
	protected List<KBArticle> findChildKBArticles(KBArticle kbArticle) {
		return _kbArticlePersistence.findByG_P_M_S(
			kbArticle.getGroupId(), kbArticle.getParentResourcePrimKey(), true,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, KBArticlePriorityComparator.getInstance(true));
	}

	@Override
	protected KBArticle findKBArticle(long kbArticleId)
		throws NoSuchArticleException {

		return _kbArticlePersistence.findByPrimaryKey(kbArticleId);
	}

	private final KBArticlePersistence _kbArticlePersistence;

}