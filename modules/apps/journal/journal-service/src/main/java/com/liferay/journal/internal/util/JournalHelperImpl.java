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

package com.liferay.journal.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.internal.transformer.JournalTransformerListenerRegistryUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.diff.CompareVersionsException;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(immediate = true, service = JournalHelper.class)
public class JournalHelperImpl implements JournalHelper {

	@Override
	public String diffHtml(
			long groupId, String articleId, double sourceVersion,
			double targetVersion, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws Exception {

		JournalArticle sourceArticle =
			JournalArticleLocalServiceUtil.getArticle(
				groupId, articleId, sourceVersion);

		if (!JournalArticleLocalServiceUtil.isRenderable(
				sourceArticle, portletRequestModel, themeDisplay)) {

			throw new CompareVersionsException(sourceVersion);
		}

		JournalArticleDisplay sourceArticleDisplay =
			JournalArticleLocalServiceUtil.getArticleDisplay(
				sourceArticle, null, Constants.VIEW, languageId, 1,
				portletRequestModel, themeDisplay);

		JournalArticle targetArticle =
			JournalArticleLocalServiceUtil.getArticle(
				groupId, articleId, targetVersion);

		if (!JournalArticleLocalServiceUtil.isRenderable(
				targetArticle, portletRequestModel, themeDisplay)) {

			throw new CompareVersionsException(targetVersion);
		}

		JournalArticleDisplay targetArticleDisplay =
			JournalArticleLocalServiceUtil.getArticleDisplay(
				targetArticle, null, Constants.VIEW, languageId, 1,
				portletRequestModel, themeDisplay);

		return DiffHtmlUtil.diff(
			new UnsyncStringReader(sourceArticleDisplay.getContent()),
			new UnsyncStringReader(targetArticleDisplay.getContent()));
	}

	@Override
	public String getAbsolutePath(PortletRequest portletRequest, long folderId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return themeDisplay.translate("home");
		}

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
			folderId);

		List<JournalFolder> folders = folder.getAncestors();

		Collections.reverse(folders);

		StringBundler sb = new StringBundler((folders.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		for (JournalFolder curFolder : folders) {
			sb.append(StringPool.RAQUO_CHAR);
			sb.append(StringPool.SPACE);
			sb.append(curFolder.getName());
		}

		sb.append(StringPool.RAQUO_CHAR);
		sb.append(StringPool.SPACE);
		sb.append(folder.getName());

		return sb.toString();
	}

	@Override
	public Layout getArticleLayout(String layoutUuid, long groupId) {
		if (Validator.isNull(layoutUuid)) {
			return null;
		}

		// The target page and the article must belong to the same group

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, false);

		if (layout == null) {
			layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, groupId, true);
		}

		return layout;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<JournalArticle> getArticles(Hits hits) throws PortalException {
		List<Document> documents = hits.toList();

		List<JournalArticle> articles = new ArrayList<>(documents.size());

		for (Document document : documents) {
			String articleId = document.get(Field.ARTICLE_ID);
			long groupId = GetterUtil.getLong(
				document.get(Field.SCOPE_GROUP_ID));

			JournalArticle article =
				JournalArticleLocalServiceUtil.fetchLatestArticle(
					groupId, articleId, WorkflowConstants.STATUS_APPROVED);

			if (article == null) {
				articles = null;

				Indexer<JournalArticle> indexer =
					IndexerRegistryUtil.getIndexer(JournalArticle.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (articles != null) {
				articles.add(article);
			}
		}

		return articles;
	}

	@Override
	public int getRestrictionType(long folderId) {
		int restrictionType = JournalFolderConstants.RESTRICTION_TYPE_INHERIT;

		JournalFolder folder = JournalFolderLocalServiceUtil.fetchFolder(
			folderId);

		if (folder != null) {
			restrictionType = folder.getRestrictionType();
		}

		return restrictionType;
	}

	@Override
	public String getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId)
		throws PortalException {

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
			groupId, _portal.getClassNameId(DDMStructure.class), ddmTemplateKey,
			true);

		String script = ddmTemplate.getScript();

		for (TransformerListener transformerListener :
				JournalTransformerListenerRegistryUtil.
					getTransformerListeners()) {

			script = transformerListener.onScript(
				script, null, languageId, tokens);
		}

		return script;
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}