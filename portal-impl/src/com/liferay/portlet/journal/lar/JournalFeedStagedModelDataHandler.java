/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class JournalFeedStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFeed> {

	public static final String[] CLASS_NAMES = {JournalFeed.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		Element feedElement = portletDataContext.getExportDataElement(feed);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			feed.getGroupId(), PortalUtil.getClassNameId(JournalArticle.class),
			feed.getStructureId(), true);

		if (ddmStructure != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmStructure);

			portletDataContext.addReferenceElement(
				feed, feedElement, ddmStructure, false);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM structure with key " +
						feed.getStructureId());
			}
		}

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			feed.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			feed.getTemplateId());

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmTemplate);

			portletDataContext.addReferenceElement(
				feed, feedElement, ddmTemplate, false);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getTemplateId());
			}
		}

		DDMTemplate rendererDDMTemplate =
			DDMTemplateLocalServiceUtil.fetchTemplate(
				feed.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				feed.getRendererTemplateId());

		if (rendererDDMTemplate != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, rendererDDMTemplate);

			Element rendererDDMTemplateElement =
				portletDataContext.addReferenceElement(
					feed, feedElement, rendererDDMTemplate, false);

			rendererDDMTemplateElement.addAttribute(
				"rendererDDMTemplate", "true");
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getRendererTemplateId());
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (newGroupFriendlyURL.equals(oldGroupFriendlyURL)) {
			String targetLayoutFriendlyUrl = StringUtil.replaceFirst(
				feed.getTargetLayoutFriendlyUrl(),
				StringPool.SLASH + newGroupFriendlyURL + StringPool.SLASH,
				"/@data_handler_group_friendly_url@/");

			feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		portletDataContext.addClassedModel(
			feedElement, ExportImportPathUtil.getModelPath(feed), feed,
			JournalPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (oldGroupFriendlyURL.equals("@data_handler_group_friendly_url@")) {
			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(),
					"@data_handler_group_friendly_url@", newGroupFriendlyURL));
		}

		String feedId = feed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId) ||
			(JournalFeedUtil.fetchByG_F(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		List<Element> ddmStructureElements =
			portletDataContext.getReferenceDataElements(
				feed, DDMStructure.class);

		String parentDDMStructureKey = StringPool.BLANK;

		if (!ddmStructureElements.isEmpty()) {
			Element ddmStructureElement = ddmStructureElements.get(0);

			String ddmStructurePath = ddmStructureElement.attributeValue(
				"path");

			DDMStructure ddmStructure =
				(DDMStructure)portletDataContext.getZipEntryAsObject(
					ddmStructurePath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmStructure);

			Map<String, String> ddmStructureKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class + ".ddmStructureKey");

			parentDDMStructureKey = MapUtil.getString(
				ddmStructureKeys, ddmStructure.getStructureKey(),
				ddmStructure.getStructureKey());
		}

		List<Element> ddmTemplateElements =
			portletDataContext.getReferenceDataElements(
				feed, DDMTemplate.class);

		String parentDDMTemplateKey = StringPool.BLANK;
		String parentRendererDDMTemplateKey = StringPool.BLANK;

		for (Element ddmTemplateElement : ddmTemplateElements) {
			String ddmTemplatePath = ddmTemplateElement.attributeValue("path");

			DDMTemplate ddmTemplate =
				(DDMTemplate)portletDataContext.getZipEntryAsObject(
					ddmTemplatePath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmTemplate);

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			boolean rendererDDMTemplate = GetterUtil.getBoolean(
				ddmTemplateElement.attributeValue("rendererDDMTemplate"));

			String ddmTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplate.getTemplateKey(),
				ddmTemplate.getTemplateKey());

			if (rendererDDMTemplate) {
				parentDDMTemplateKey = ddmTemplateKey;
			}
			else {
				parentRendererDDMTemplateKey = ddmTemplateKey;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			feed, JournalPortletDataHandler.NAMESPACE);

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, feed);

		serviceContext.setAddGroupPermissions(addGroupPermissions);

		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, feed);

		serviceContext.setAddGuestPermissions(addGuestPermissions);

		JournalFeed importedFeed = null;

		try {
			if (portletDataContext.isDataStrategyMirror()) {
				JournalFeed existingFeed = JournalFeedUtil.fetchByUUID_G(
					feed.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFeed == null) {
					serviceContext.setUuid(feed.getUuid());

					importedFeed = JournalFeedLocalServiceUtil.addFeed(
						userId, portletDataContext.getScopeGroupId(), feedId,
						autoFeedId, feed.getName(), feed.getDescription(),
						feed.getType(), parentDDMStructureKey,
						parentDDMTemplateKey, parentRendererDDMTemplateKey,
						feed.getDelta(), feed.getOrderByCol(),
						feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
				else {
					importedFeed = JournalFeedLocalServiceUtil.updateFeed(
						existingFeed.getGroupId(), existingFeed.getFeedId(),
						feed.getName(), feed.getDescription(), feed.getType(),
						parentDDMStructureKey, parentDDMTemplateKey,
						parentRendererDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
			}
			else {
				importedFeed = JournalFeedLocalServiceUtil.addFeed(
					userId, portletDataContext.getScopeGroupId(), feedId,
					autoFeedId, feed.getName(), feed.getDescription(),
					feed.getType(), parentDDMStructureKey, parentDDMTemplateKey,
					parentRendererDDMTemplateKey, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedFormat(), feed.getFeedVersion(),
					serviceContext);
			}

			portletDataContext.importClassedModel(
				feed, importedFeed, JournalPortletDataHandler.NAMESPACE);

			if (!feedId.equals(importedFeed.getFeedId())) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("A feed with the ID ");
					sb.append(feedId);
					sb.append(" already exists. The new generated ID is ");
					sb.append(importedFeed.getFeedId());
					sb.append(".");

					_log.warn(sb.toString());
				}
			}
		}
		catch (FeedTargetLayoutFriendlyUrlException ftlfurle) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("A feed with the ID ");
				sb.append(feedId);
				sb.append(" cannot be imported because layout with friendly ");
				sb.append("URL ");
				sb.append(feed.getTargetLayoutFriendlyUrl());
				sb.append(" does not exist");

				_log.warn(sb.toString());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalFeedStagedModelDataHandler.class);

}