/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.helper;

import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseKBArticleSiblingNavigationHelper {

	public KBArticle[] getPreviousAndNextKBArticles(long kbArticleId)
		throws PortalException {

		KBArticle kbArticle = findKBArticle(kbArticleId);

		KBArticle[] previousAndNextKBArticles = getPreviousAndNextKBArticles(
			kbArticle);

		KBArticle previousKBArticle = _getPreviousKBArticle(
			kbArticle, previousAndNextKBArticles[0]);
		KBArticle nextKBArticle = _getNextKBArticle(
			kbArticle, previousAndNextKBArticles[2]);

		return new KBArticle[] {previousKBArticle, kbArticle, nextKBArticle};
	}

	protected abstract KBArticle fetchFirstChildKBArticle(KBArticle kbArticle);

	protected abstract KBArticle fetchLastChildKBArticle(
		KBArticle previousKBArticle);

	protected abstract List<KBArticle> findChildKBArticles(KBArticle kbArticle);

	protected abstract KBArticle findKBArticle(long kbArticleId)
		throws NoSuchArticleException;

	protected KBArticle[] getPreviousAndNextKBArticles(KBArticle kbArticle) {
		List<KBArticle> kbArticles = findChildKBArticles(kbArticle);

		int index = kbArticles.indexOf(kbArticle);

		KBArticle[] previousAndNextKBArticles = {null, kbArticle, null};

		if (index > 0) {
			previousAndNextKBArticles[0] = kbArticles.get(index - 1);
		}

		if (index < (kbArticles.size() - 1)) {
			previousAndNextKBArticles[2] = kbArticles.get(index + 1);
		}

		return previousAndNextKBArticles;
	}

	private KBArticle _getNextAncestorKBArticle(
			long kbArticleId, KBArticle nextKBArticle)
		throws PortalException {

		if (nextKBArticle != null) {
			return nextKBArticle;
		}

		KBArticle kbArticle = findKBArticle(kbArticleId);

		KBArticle parentKBArticle = kbArticle.getParentKBArticle();

		if (parentKBArticle == null) {
			return null;
		}

		KBArticle[] previousAndNextKBArticles = getPreviousAndNextKBArticles(
			parentKBArticle);

		return _getNextAncestorKBArticle(
			parentKBArticle.getKbArticleId(), previousAndNextKBArticles[2]);
	}

	private KBArticle _getNextKBArticle(
			KBArticle kbArticle, KBArticle nextKBArticle)
		throws PortalException {

		KBArticle firstChildKBArticle = fetchFirstChildKBArticle(kbArticle);

		if (firstChildKBArticle != null) {
			return firstChildKBArticle;
		}

		return _getNextAncestorKBArticle(
			kbArticle.getKbArticleId(), nextKBArticle);
	}

	private KBArticle _getPreviousKBArticle(
			KBArticle kbArticle, KBArticle previousKBArticle)
		throws PortalException {

		if (previousKBArticle == null) {
			return kbArticle.getParentKBArticle();
		}

		KBArticle lastSiblingChildKBArticle = fetchLastChildKBArticle(
			previousKBArticle);

		if (lastSiblingChildKBArticle == null) {
			return previousKBArticle;
		}

		return lastSiblingChildKBArticle;
	}

}