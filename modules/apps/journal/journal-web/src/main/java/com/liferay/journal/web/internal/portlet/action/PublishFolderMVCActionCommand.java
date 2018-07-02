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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommand;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/publish_folder"
	},
	service = MVCActionCommand.class
)
public class PublishFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		Changeset.Builder builder = Changeset.create();

		Changeset changeset = builder.addStagedModel(
			() -> _journalFolderLocalService.fetchJournalFolder(folderId)
		).addStagedModelHierarchy(
			() -> _journalFolderLocalService.fetchJournalFolder(folderId),
			folder -> _getFoldersAndArticles(folder)
		).build();

		_exportImportChangesetMVCActionCommand.processPublishAction(
			actionRequest, actionResponse, changeset);
	}

	private List<StagedModel> _getFoldersAndArticles(JournalFolder folder) {
		if (folder == null) {
			return Collections.emptyList();
		}

		List<StagedModel> stagedModels = new ArrayList<>();

		try {
			List<Object> childObjects =
				_journalFolderLocalService.getFoldersAndArticles(
					folder.getGroupId(), folder.getFolderId());

			for (Object childObject : childObjects) {
				if (childObject instanceof JournalFolder) {
					JournalFolder childFolder = (JournalFolder)childObject;

					stagedModels.add(childFolder);

					stagedModels.addAll(_getFoldersAndArticles(childFolder));
				}
				else if (childObject instanceof JournalArticle) {
					JournalArticle journalArticle = (JournalArticle)childObject;

					boolean includeVersionHistory = false;

					try {
						JournalServiceConfiguration
							journalServiceConfiguration =
								ConfigurationProviderUtil.
									getCompanyConfiguration(
										JournalServiceConfiguration.class,
										CompanyThreadLocal.getCompanyId());

						includeVersionHistory =
							journalServiceConfiguration.
								singleAssetPublishIncludeVersionHistory();
					}
					catch (Exception e) {
						_log.error(e, e);
					}

					if (_journalArticleStagedModelDataHandler == null) {
						_journalArticleStagedModelDataHandler =
							StagedModelDataHandlerRegistryUtil.
								getStagedModelDataHandler(
									JournalArticle.class.getName());
					}

					List<JournalArticle> journalArticles = new ArrayList<>();

					if (includeVersionHistory) {
						journalArticles.addAll(
							_journalArticleLocalService.getArticles(
								journalArticle.getGroupId(),
								journalArticle.getArticleId()));
					}
					else {
						journalArticles.add(
							_journalArticleLocalService.getArticle(
								journalArticle.getGroupId(),
								journalArticle.getArticleId()));
					}

					for (JournalArticle article : journalArticles) {
						if (ArrayUtil.contains(
								_journalArticleStagedModelDataHandler.
									getExportableStatuses(),
								article.getStatus())) {

							stagedModels.add(article);
						}
					}
				}
				else {
					stagedModels.add((StagedModel)childObject);
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get folders, file entries, file shortcuts for " +
						"folder " +
							folder.getFolderId(),
					pe);
			}
		}

		return stagedModels;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishFolderMVCActionCommand.class);

	@Reference
	private ExportImportChangesetMVCActionCommand
		_exportImportChangesetMVCActionCommand;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	private StagedModelDataHandler _journalArticleStagedModelDataHandler;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

}