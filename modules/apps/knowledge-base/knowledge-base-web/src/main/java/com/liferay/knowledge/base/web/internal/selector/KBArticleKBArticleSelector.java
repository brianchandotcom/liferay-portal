/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.selector;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = KBArticleSelector.class
)
public class KBArticleKBArticleSelector implements KBArticleSelector {

	@Override
	public KBArticleSelection findByResourcePrimKey(
			long groupId, String preferredKBFolderUrlTitle,
			long ancestorResourcePrimKey, long resourcePrimKey)
		throws PortalException {

		KBArticle ancestorKBArticle = _kbArticleService.fetchLatestKBArticle(
			ancestorResourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		if (ancestorKBArticle == null) {
			return new KBArticleSelection(null, true);
		}

		if (resourcePrimKey ==
				KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY) {

			return new KBArticleSelection(ancestorKBArticle, true);
		}

		KBArticle kbArticle = _kbArticleService.fetchLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		return _getClosestMatchingDescendantKBArticle(
			groupId, ancestorKBArticle, kbArticle);
	}

	@Override
	public KBArticleSelection findByUrlTitle(
			long groupId, String preferredKBFolderUrlTitle,
			long ancestorResourcePrimKey, String kbFolderUrlTitle,
			String urlTitle)
		throws PortalException {

		KBArticle ancestorKBArticle = _kbArticleService.fetchLatestKBArticle(
			ancestorResourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		if (ancestorKBArticle == null) {
			return new KBArticleSelection(null, true);
		}

		KBArticle kbArticle = _kbArticleService.fetchLatestKBArticleByUrlTitle(
			groupId, ancestorKBArticle.getKbFolderId(), urlTitle,
			WorkflowConstants.STATUS_APPROVED);

		return _getClosestMatchingDescendantKBArticle(
			groupId, ancestorKBArticle, kbArticle);
	}

	protected KBArticleSelection findClosestMatchingKBArticle(
			long groupId, KBArticle ancestorKBArticle, KBArticle kbArticle)
		throws PortalException {

		KBArticle candidateKBArticle = kbArticle;

		while (candidateKBArticle != null) {
			KBArticle matchingKBArticle =
				_kbArticleService.fetchKBArticleByUrlTitle(
					groupId, ancestorKBArticle.getKbFolderId(),
					candidateKBArticle.getUrlTitle());

			if (matchingKBArticle != null) {
				return new KBArticleSelection(matchingKBArticle, false);
			}

			candidateKBArticle = candidateKBArticle.getParentKBArticle();
		}

		return new KBArticleSelection(ancestorKBArticle, false);
	}

	protected boolean isDescendant(
			KBArticle kbArticle, KBArticle ancestorKBArticle)
		throws PortalException {

		if (kbArticle.getKbArticleId() == ancestorKBArticle.getKbArticleId()) {
			return true;
		}

		KBArticle parentKBArticle = kbArticle.getParentKBArticle();

		while ((parentKBArticle != null) &&
			   !parentKBArticle.equals(ancestorKBArticle)) {

			parentKBArticle = parentKBArticle.getParentKBArticle();
		}

		if (parentKBArticle != null) {
			return true;
		}

		return false;
	}

	private KBArticleSelection _getClosestMatchingDescendantKBArticle(
			long groupId, KBArticle ancestorKBArticle, KBArticle kbArticle)
		throws PortalException {

		if (kbArticle == null) {
			return new KBArticleSelection(ancestorKBArticle, false);
		}

		if (isDescendant(kbArticle, ancestorKBArticle)) {
			return new KBArticleSelection(kbArticle, true);
		}

		return findClosestMatchingKBArticle(
			groupId, ancestorKBArticle, kbArticle);
	}

	@Reference
	private KBArticleService _kbArticleService;

}