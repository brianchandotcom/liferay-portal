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

package com.liferay.journal.internal.validation;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.exception.DuplicateFolderNameException;
import com.liferay.journal.exception.InvalidDDMStructureException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.journal.util.JournalValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.validation.ModelValidationResults;
import com.liferay.portal.validation.ModelValidator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.journal.model.JournalFolder",
	service = ModelValidator.class
)
public class JournalFolderModelValidator
	implements ModelValidator<JournalFolder> {

	public void validateArticleDDMStructures(
			long folderId, long[] ddmStructureIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(ddmStructureIds)) {
			return;
		}

		JournalFolder folder = _journalFolderPersistence.findByPrimaryKey(
			folderId);

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			folder.getGroupId(), folderId);

		if (!articles.isEmpty()) {
			long classNameId = _classNameLocalService.getClassNameId(
				JournalArticle.class);

			for (JournalArticle article : articles) {
				DDMStructure ddmStructure =
					_ddmStructureLocalService.fetchStructure(
						article.getGroupId(), classNameId,
						article.getDDMStructureKey(), true);

				if (ddmStructure == null) {
					StringBundler sb = new StringBundler(7);

					sb.append("No DDM structure exists for group ");
					sb.append(article.getGroupId());
					sb.append(", class name ");
					sb.append(classNameId);
					sb.append(", and structure key ");
					sb.append(article.getDDMStructureKey());
					sb.append(" that includes ancestor structures");

					throw new InvalidDDMStructureException(sb.toString());
				}

				if (!ArrayUtil.contains(
						ddmStructureIds, ddmStructure.getStructureId())) {

					throw new InvalidDDMStructureException(
						"Invalid DDM structure " +
							ddmStructure.getStructureId());
				}
			}
		}

		List<JournalFolder> folders = _journalFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		if (folders.isEmpty()) {
			return;
		}

		for (JournalFolder curFolder : folders) {
			validateArticleDDMStructures(
				curFolder.getFolderId(), ddmStructureIds);
		}
	}

	public void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException {

		_journalValidator.validateFolderName(name);

		JournalFolder folder = _journalFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException(name);
		}
	}

	public void validateFolderDDMStructures(long folderId, long parentFolderId)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.fetchFolder(folderId);

		int restrictionType =
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW;

		JournalFolder parentFolder = _journalFolderLocalService.fetchFolder(
			parentFolderId);

		if (parentFolder != null) {
			restrictionType = parentFolder.getRestrictionType();
		}

		List<DDMStructure> folderDDMStructures =
			_journalFolderLocalService.getDDMStructures(
				_portal.getCurrentAndAncestorSiteGroupIds(folder.getGroupId()),
				parentFolderId, restrictionType);

		long[] ddmStructureIds = new long[folderDDMStructures.size()];

		for (int i = 0; i < folderDDMStructures.size(); i++) {
			DDMStructure folderDDMStructure = folderDDMStructures.get(i);

			ddmStructureIds[i] = folderDDMStructure.getStructureId();
		}

		validateArticleDDMStructures(folderId, ddmStructureIds);
	}

	@Override
	public ModelValidationResults validateModel(JournalFolder folder) {
		long folderId = folder.getFolderId();

		long[] ddmStructureIds = null;

		try {
			List<DDMStructure> ddmStructures =
				_journalFolderLocalService.getDDMStructures(
					new long[] {folder.getGroupId()}, folder.getFolderId(),
					folder.getRestrictionType());

			ddmStructureIds = new long[ddmStructures.size()];

			int i = 0;

			for (DDMStructure ddmStructure : ddmStructures) {
				ddmStructureIds[i] = ddmStructure.getStructureId();
			}
		}
		catch (PortalException pe) {
			ModelValidationResults.FailureBuilder failureBuilder =
				ModelValidationResults.failure();

			return failureBuilder.exceptionFailure(
				"Unable to retrieve folder structures for validation: " +
					pe.getMessage(),
				pe
			).getResults();
		}

		try {
			validateArticleDDMStructures(folderId, ddmStructureIds);

			validateFolder(
				folderId, folder.getGroupId(), folder.getParentFolderId(),
				folder.getName());
		}
		catch (PortalException pe) {
			ModelValidationResults.FailureBuilder failureBuilder =
				ModelValidationResults.failure();

			return failureBuilder.exceptionFailure(
				pe.getMessage(), pe
			).getResults();
		}

		return ModelValidationResults.success();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

	@Reference
	private JournalFolderPersistence _journalFolderPersistence;

	@Reference
	private JournalValidator _journalValidator;

	@Reference
	private Portal _portal;

}