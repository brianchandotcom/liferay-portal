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

package com.liferay.journal.internal.upgrade.v_1_1_5;

import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.internal.upgrade.util.JournalArticleImageUpgradeUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeContentImages extends UpgradeProcess {

	public UpgradeContentImages(
		ImageLocalService imageLocalService,
		JournalArticleImageUpgradeUtil journalArticleImageUpgradeUtil) {

		_imageLocalService = imageLocalService;
		_journalArticleImageUpgradeUtil = journalArticleImageUpgradeUtil;
	}

	protected String convertTypeImageElements(
			long userId, long groupId, String content, long resourcePrimKey)
		throws Exception {

		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPath.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				String id = dynamicContentEl.attributeValue("id");

				if (Validator.isNull(id)) {
					continue;
				}

				long folderId = _journalArticleImageUpgradeUtil.getFolderId(
					userId, groupId, resourcePrimKey);

				FileEntry fileEntry = null;

				try {
					fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
						groupId, folderId, id);
				}
				catch (PortalException pe) {
					_log.error(
						StringBundler.concat(
							"Unable to get file entry with group ID ",
							String.valueOf(groupId), ", folder ID ",
							String.valueOf(folderId), ", and file name ", id),
						pe);
				}

				if (fileEntry == null) {
					continue;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("alt", StringPool.BLANK);
				jsonObject.put("groupId", fileEntry.getGroupId());
				jsonObject.put("name", fileEntry.getFileName());
				jsonObject.put("resourcePrimKey", resourcePrimKey);
				jsonObject.put("title", fileEntry.getTitle());
				jsonObject.put("type", "journal");
				jsonObject.put("uuid", fileEntry.getUuid());

				dynamicContentEl.clearContent();

				dynamicContentEl.addCDATA(jsonObject.toString());
			}
		}

		return contentDocument.formattedString();
	}

	protected void copyJournalArticleImagesToJournalRepository()
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("select JournalArticleImage.articleImageId, ");
		sb.append("JournalArticleImage.groupId, ");
		sb.append("JournalArticle.resourcePrimKey, JournalArticle.userId ");
		sb.append("from JournalArticleImage inner join JournalArticle on ");
		sb.append("(JournalArticle.groupId=JournalArticleImage.groupId and ");
		sb.append("JournalArticle.articleId=JournalArticleImage.articleId ");
		sb.append("and JournalArticle.version=JournalArticleImage.version)");

		List<SaveImageFileEntryCallable> saveImageFileEntryCallables =
			new ArrayList<>();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sb.toString())) {

			while (rs1.next()) {
				long articleImageId = rs1.getLong(1);
				long groupId = rs1.getLong(2);
				long resourcePrimKey = rs1.getLong(3);
				long userId = rs1.getLong(4);

				long folderId = _journalArticleImageUpgradeUtil.getFolderId(
					userId, groupId, resourcePrimKey);

				SaveImageFileEntryCallable saveImageFileEntryCallable =
					new SaveImageFileEntryCallable(
						articleImageId, folderId, groupId, resourcePrimKey,
						userId);

				saveImageFileEntryCallables.add(saveImageFileEntryCallable);
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				saveImageFileEntryCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to copy journal article images to the file " +
							"repository");
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		copyJournalArticleImagesToJournalRepository();

		updateContentImages();
	}

	protected void updateContentImages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select content, groupId, id_, resourcePrimKey, userId from " +
					"JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"image\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				String content = rs1.getString(1);
				long groupId = rs1.getLong(2);
				long id = rs1.getLong(3);
				long resourcePrimKey = rs1.getLong(4);
				long userId = rs1.getLong(5);

				String newContent = convertTypeImageElements(
					userId, groupId, content, resourcePrimKey);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					ps2.setString(1, newContent);
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeContentImages.class);

	private final ImageLocalService _imageLocalService;
	private final JournalArticleImageUpgradeUtil
		_journalArticleImageUpgradeUtil;

	private class SaveImageFileEntryCallable implements Callable<Boolean> {

		public SaveImageFileEntryCallable(
			long articleImageId, long folderId, long groupId,
			long resourcePrimaryKey, long userId) {

			_articleImageId = articleImageId;
			_folderId = folderId;
			_groupId = groupId;
			_resourcePrimaryKey = resourcePrimaryKey;
			_userId = userId;
		}

		@Override
		public Boolean call() throws Exception {
			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					_groupId, _folderId, String.valueOf(_articleImageId));

			if (fileEntry != null) {
				return null;
			}

			try {
				Image image = _imageLocalService.getImage(_articleImageId);

				if (image == null) {
					return null;
				}

				PortletFileRepositoryUtil.addPortletFileEntry(
					_groupId, _userId, JournalArticle.class.getName(),
					_resourcePrimaryKey, JournalConstants.SERVICE_NAME,
					_folderId, image.getTextObj(),
					String.valueOf(_articleImageId), image.getType(), false);
			}
			catch (Exception e) {
				_log.error(
					"Unable to add the journal article image " +
						_articleImageId + " into the file repository",
					e);

				return false;
			}

			return true;
		}

		private final long _articleImageId;
		private final long _folderId;
		private final long _groupId;
		private final long _resourcePrimaryKey;
		private final long _userId;

	}

}