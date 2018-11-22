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

package com.liferay.knowledge.base.web.internal.selector;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.util.comparator.KBFolderNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.knowledge.base.model.KBFolder",
	service = KBArticleSelector.class
)
public class KBFolderKBArticleSelector implements KBArticleSelector {

	@Override
	public KBArticleSelection findByResourcePrimKey(
			long groupId, String preferredKBFolderUrlTitle,
			long ancestorResourcePrimKey, long resourcePrimKey)
		throws PortalException {

		KBFolder ancestorKBFolder = _rootKBFolder;

		if (ancestorResourcePrimKey !=
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			ancestorKBFolder = _kbFolderService.fetchKBFolder(
				ancestorResourcePrimKey);

			if (ancestorKBFolder == null) {
				return new KBArticleSelection(null, false);
			}
		}

		KBArticle kbArticle = _kbArticleService.fetchLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		if ((kbArticle == null) || !isDescendant(kbArticle, ancestorKBFolder)) {
			KBArticleSelection kbArticleSelection = findFirstKBArticle(
				groupId, ancestorKBFolder, preferredKBFolderUrlTitle);

			if (resourcePrimKey == 0) {
				kbArticleSelection.setExactMatch(true);
			}

			return kbArticleSelection;
		}

		return new KBArticleSelection(kbArticle, true);
	}

	@Override
	public KBArticleSelection findByUrlTitle(
			long groupId, String preferredKBFolderUrlTitle,
			long ancestorResourcePrimKey, String kbFolderUrlTitle,
			String urlTitle)
		throws PortalException {

		KBFolder ancestorKBFolder = _rootKBFolder;

		if (ancestorResourcePrimKey !=
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			ancestorKBFolder = _kbFolderService.fetchKBFolder(
				ancestorResourcePrimKey);

			if (ancestorKBFolder == null) {
				return new KBArticleSelection(null, false);
			}
		}

		KBFolder kbFolder = _rootKBFolder;

		if (Validator.isNotNull(kbFolderUrlTitle)) {
			if (kbFolderUrlTitle.equals(ancestorKBFolder.getUrlTitle())) {
				kbFolder = ancestorKBFolder;
			}
			else {
				kbFolder = _kbFolderService.fetchKBFolderByUrlTitle(
					groupId, ancestorKBFolder.getKbFolderId(),
					kbFolderUrlTitle);
			}
		}

		KBArticle kbArticle = null;

		if (kbFolder != null) {
			kbArticle = _kbArticleService.fetchKBArticleByUrlTitle(
				groupId, kbFolder.getKbFolderId(), urlTitle);
		}

		if ((kbArticle == null) || !isDescendant(kbArticle, ancestorKBFolder)) {
			return findClosestMatchingKBArticle(
				groupId, ancestorKBFolder, preferredKBFolderUrlTitle,
				kbFolderUrlTitle, urlTitle);
		}

		return new KBArticleSelection(kbArticle, true);
	}

	@Activate
	protected void activate() {
		_rootKBFolder = _kbFolderLocalService.createKBFolder(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	protected KBArticleSelection findClosestMatchingKBArticle(
			long groupId, KBFolder ancestorKBFolder,
			String preferredKBFolderUrlTitle, String kbFolderUrlTitle,
			String urlTitle)
		throws PortalException {

		KBFolder kbFolder = getCandidateKBFolder(
			groupId, preferredKBFolderUrlTitle, ancestorKBFolder,
			kbFolderUrlTitle);

		KBArticle kbArticle = _kbArticleService.fetchKBArticleByUrlTitle(
			groupId, kbFolder.getKbFolderId(), urlTitle);

		if (kbArticle != null) {
			return new KBArticleSelection(kbArticle, false);
		}

		kbArticle = _kbArticleService.fetchFirstChildKBArticle(
			groupId, kbFolder.getKbFolderId());

		String[] keywords = StringUtil.split(urlTitle, '-');

		return new KBArticleSelection(kbArticle, keywords);
	}

	protected KBArticleSelection findFirstKBArticle(
			long groupId, KBFolder ancestorKBFolder,
			String preferredKBFolderUrlTitle)
		throws PortalException {

		KBFolder kbFolder = null;

		int kbArticlesCount = _kbArticleService.getKBArticlesCount(
			groupId, ancestorKBFolder.getKbFolderId(),
			WorkflowConstants.STATUS_APPROVED);

		if (Validator.isNotNull(preferredKBFolderUrlTitle) &&
			(kbArticlesCount == 0)) {

			kbFolder = _kbFolderService.fetchKBFolderByUrlTitle(
				groupId, ancestorKBFolder.getKbFolderId(),
				preferredKBFolderUrlTitle);
		}

		if ((kbFolder == null) && (kbArticlesCount == 0)) {
			kbFolder = _kbFolderService.fetchFirstChildKBFolder(
				groupId, ancestorKBFolder.getKbFolderId(),
				new KBFolderNameComparator(false));
		}

		if (kbFolder == null) {
			kbFolder = ancestorKBFolder;
		}

		KBArticle kbArticle = _kbArticleService.fetchFirstChildKBArticle(
			groupId, kbFolder.getKbFolderId(),
			WorkflowConstants.STATUS_APPROVED);

		return new KBArticleSelection(kbArticle, true);
	}

	protected KBFolder getCandidateKBFolder(
			long groupId, String preferredKBFolderUrlTitle,
			KBFolder ancestorKBFolder, String kbFolderUrlTitle)
		throws PortalException {

		KBFolder kbFolder = null;

		if (Validator.isNotNull(kbFolderUrlTitle)) {
			kbFolder = _kbFolderService.fetchKBFolderByUrlTitle(
				groupId, ancestorKBFolder.getKbFolderId(), kbFolderUrlTitle);
		}

		if ((kbFolder == null) &&
			Validator.isNotNull(preferredKBFolderUrlTitle)) {

			kbFolder = _kbFolderService.fetchKBFolderByUrlTitle(
				groupId, ancestorKBFolder.getKbFolderId(),
				preferredKBFolderUrlTitle);
		}

		int kbArticlesCount = _kbArticleService.getKBArticlesCount(
			groupId, ancestorKBFolder.getKbFolderId(),
			WorkflowConstants.STATUS_APPROVED);

		if ((kbFolder == null) && (kbArticlesCount == 0)) {
			kbFolder = _kbFolderService.fetchFirstChildKBFolder(
				groupId, ancestorKBFolder.getKbFolderId(),
				new KBFolderNameComparator(false));
		}

		if (kbFolder == null) {
			return ancestorKBFolder;
		}

		return kbFolder;
	}

	protected boolean isDescendant(KBArticle kbArticle, KBFolder kbFolder)
		throws PortalException {

		if (kbFolder.getKbFolderId() ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		if (kbArticle.getKbFolderId() ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return false;
		}

		KBFolder parentKBFolder = _kbFolderService.getKBFolder(
			kbArticle.getKbFolderId());

		List<Long> ancestorKBFolderIds =
			parentKBFolder.getAncestorKBFolderIds();

		if (ancestorKBFolderIds.contains(kbFolder.getKbFolderId())) {
			return true;
		}

		return false;
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Reference
	private KBFolderService _kbFolderService;

	private KBFolder _rootKBFolder;

}