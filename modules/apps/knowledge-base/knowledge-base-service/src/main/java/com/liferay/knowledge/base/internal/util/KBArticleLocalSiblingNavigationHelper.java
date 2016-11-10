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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

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
		return _kbArticlePersistence.fetchByG_P_M_First(
			kbArticle.getGroupId(), kbArticle.getResourcePrimKey(), true,
			new KBArticlePriorityComparator(true));
	}

	@Override
	protected KBArticle fetchLastChildKBArticle(KBArticle previousKBArticle) {
		return _kbArticlePersistence.fetchByG_P_M_Last(
			previousKBArticle.getGroupId(),
			previousKBArticle.getResourcePrimKey(), true,
			new KBArticlePriorityComparator(true));
	}

	@Override
	protected List<KBArticle> findChildKBArticles(KBArticle kbArticle) {
		return _kbArticlePersistence.findByG_P_M(
			kbArticle.getGroupId(), kbArticle.getParentResourcePrimKey(), true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new KBArticlePriorityComparator(true));
	}

	@Override
	protected KBArticle findKBArticle(long kbArticleId)
		throws NoSuchArticleException {

		return _kbArticlePersistence.findByPrimaryKey(kbArticleId);
	}

	private final KBArticlePersistence _kbArticlePersistence;

}