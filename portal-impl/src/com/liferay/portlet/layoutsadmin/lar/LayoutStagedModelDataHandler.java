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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.lar.LayoutCache;
import com.liferay.portal.lar.LayoutExporter;
import com.liferay.portal.lar.PermissionExporter;
import com.liferay.portal.lar.PermissionImporter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class LayoutStagedModelDataHandler
	extends BaseStagedModelDataHandler<Layout> {

	public static final String[] CLASS_NAMES = {Layout.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected String[] appendPortletIds(
		String[] portletIds, String[] newPortletIds, String portletsMergeMode) {

		for (String portletId : newPortletIds) {
			if (ArrayUtil.contains(portletIds, portletId)) {
				continue;
			}

			if (portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM)) {

				portletIds = ArrayUtil.append(portletIds, portletId);
			}
			else {
				portletIds = ArrayUtil.append(
					new String[] {portletId}, portletIds);
			}
		}

		return portletIds;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		LayoutRevision layoutRevision = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean exportLAR = ParamUtil.getBoolean(serviceContext, "exportLAR");

		if (!exportLAR && LayoutStagingUtil.isBranchingLayout(layout) &&
			!layout.isTypeURL()) {

			long layoutSetBranchId = ParamUtil.getLong(
				serviceContext, "layoutSetBranchId");

			if (layoutSetBranchId <= 0) {
				return;
			}

			layoutRevision = LayoutRevisionUtil.fetchByL_H_P(
				layoutSetBranchId, true, layout.getPlid());

			if (layoutRevision == null) {
				return;
			}

			LayoutStagingHandler layoutStagingHandler =
				LayoutStagingUtil.getLayoutStagingHandler(layout);

			layoutStagingHandler.setLayoutRevision(layoutRevision);
		}

		Element layoutElement = portletDataContext.getExportDataElement(layout);

		if (layoutRevision != null) {
			layoutElement.addAttribute(
				"layout-revision-id",
				String.valueOf(layoutRevision.getLayoutRevisionId()));
			layoutElement.addAttribute(
				"layout-branch-id",
				String.valueOf(layoutRevision.getLayoutBranchId()));

			LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

			layoutElement.addAttribute(
				"layout-branch-name", String.valueOf(layoutBranch.getName()));
		}

		layoutElement.addAttribute("layout-uuid", layout.getUuid());
		layoutElement.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));

		long parentLayoutId = layout.getParentLayoutId();

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			Layout parentLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			if (parentLayout != null) {
				exportStagedModel(portletDataContext, parentLayout);

				portletDataContext.addReferenceElement(
					layout, layoutElement, parentLayout,
					PortletDataContext.REFERENCE_TYPE_PARENT, false);

				layoutElement.addAttribute(
					"parent-layout-uuid", parentLayout.getUuid());
			}
		}

		String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

		if (Validator.isNotNull(layoutPrototypeUuid)) {
			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, portletDataContext.getCompanyId());

			layoutElement.addAttribute(
				"layout-prototype-uuid", layoutPrototypeUuid);
			layoutElement.addAttribute(
				"layout-prototype-name",
				layoutPrototype.getName(LocaleUtil.getDefault()));
		}

		boolean deleteLayout = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), "delete_" + layout.getPlid());

		if (deleteLayout) {
			layoutElement.addAttribute("delete", String.valueOf(true));

			return;
		}

		portletDataContext.setPlid(layout.getPlid());

		// Layout ratings

		portletDataContext.addRatingsEntries(Layout.class, layout.getPlid());

		if (layout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layout.getIconImageId());

			if (image != null) {
				String iconPath = ExportImportPathUtil.getModelPath(
					portletDataContext.getScopeGroupId(), Image.class.getName(),
					image.getImageId());

				Element iconImagePathElement = layoutElement.addElement(
					"icon-image-path");

				iconImagePathElement.addText(iconPath);

				portletDataContext.addZipEntry(iconPath, image.getTextObj());
			}
		}

		boolean exportPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (exportPermissions) {
			_permissionExporter.exportLayoutPermissions(
				portletDataContext, new LayoutCache(),
				portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), layout, layoutElement);
		}

		if (layout.isTypeArticle()) {
			exportJournalArticle(portletDataContext, layout, layoutElement);
		}

		if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			if (linkToLayoutId > 0) {
				try {
					Layout linkedToLayout = LayoutLocalServiceUtil.getLayout(
						portletDataContext.getScopeGroupId(),
						layout.isPrivateLayout(), linkToLayoutId);

					exportStagedModel(portletDataContext, linkedToLayout);

					portletDataContext.addReferenceElement(
						layout, layoutElement, linkedToLayout,
						PortletDataContext.REFERENCE_TYPE_STRONG, false);

					layoutElement.addAttribute(
						"linked-to-layout-uuid", linkedToLayout.getUuid());
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		fixTypeSettings(layout);

		String layoutPath = ExportImportPathUtil.getModelPath(layout);

		layoutElement.addAttribute("path", layoutPath);

		portletDataContext.addExpando(layoutElement, layoutPath, layout);

		portletDataContext.addZipEntry(layoutPath, layout);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		long groupId = portletDataContext.getGroupId();
		long userId = portletDataContext.getUserId(layout.getUserUuid());

		Element layoutElement =
			portletDataContext.getImportDataStagedModelElement(layout);

		String layoutUuid = GetterUtil.getString(
			layoutElement.attributeValue("layout-uuid"));

		long layoutId = GetterUtil.getInteger(
			layoutElement.attributeValue("layout-id"));

		long oldLayoutId = layoutId;

		boolean deleteLayout = GetterUtil.getBoolean(
			layoutElement.attributeValue("delete"));

		boolean privateLayout = portletDataContext.isPrivateLayout();

		Map<Long, Layout> newLayoutsMap =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		if (deleteLayout) {
			Layout deletingLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					layoutUuid, groupId, privateLayout);

			if (layout != null) {
				newLayoutsMap.put(oldLayoutId, layout);

				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				LayoutLocalServiceUtil.deleteLayout(
					deletingLayout, false, serviceContext);
			}

			return;
		}

		Layout existingLayout = null;
		Layout importedLayout = null;

		String friendlyURL = layout.getFriendlyURL();

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

			layoutId = LayoutLocalServiceUtil.getNextLayoutId(
				groupId, privateLayout);
			friendlyURL = StringPool.SLASH + layoutId;
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

			Locale locale = LocaleUtil.getDefault();

			String localizedName = layout.getName(locale);

			List<Layout> previousLayouts = LayoutUtil.findByG_P(
				groupId, privateLayout);

			for (Layout curLayout : previousLayouts) {
				if (localizedName.equals(curLayout.getName(locale)) ||
					friendlyURL.equals(curLayout.getFriendlyURL())) {

					existingLayout = curLayout;

					break;
				}
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			existingLayout = LayoutUtil.fetchByG_P_SPLU(
				groupId, privateLayout, layout.getUuid());

			if (SitesUtil.isLayoutModifiedSinceLastMerge(existingLayout)) {
				newLayoutsMap.put(oldLayoutId, existingLayout);

				return;
			}
		}
		else {

			// The default behaviour of import mode is
			// PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID

			existingLayout = LayoutUtil.fetchByUUID_G_P(
				layout.getUuid(), groupId, privateLayout);

			if (existingLayout == null) {
				existingLayout = LayoutUtil.fetchByG_P_F(
					groupId, privateLayout, friendlyURL);
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("Layout with {groupId=");
			sb.append(groupId);
			sb.append(",privateLayout=");
			sb.append(privateLayout);
			sb.append(",layoutId=");
			sb.append(layoutId);

			if (existingLayout == null) {
				sb.append("} does not exist");

				_log.debug(sb.toString());
			}
			else {
				sb.append("} exists");

				_log.debug(sb.toString());
			}
		}

		if (existingLayout == null) {
			long plid = CounterLocalServiceUtil.increment();

			importedLayout = LayoutUtil.create(plid);

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				importedLayout.setSourcePrototypeLayoutUuid(layout.getUuid());

				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
			else {
				importedLayout.setCreateDate(layout.getCreateDate());
				importedLayout.setModifiedDate(layout.getModifiedDate());
				importedLayout.setLayoutPrototypeUuid(
					layout.getLayoutPrototypeUuid());
				importedLayout.setLayoutPrototypeLinkEnabled(
					layout.isLayoutPrototypeLinkEnabled());
				importedLayout.setSourcePrototypeLayoutUuid(
					layout.getSourcePrototypeLayoutUuid());
			}

			importedLayout.setUuid(layout.getUuid());
			importedLayout.setGroupId(groupId);
			importedLayout.setPrivateLayout(privateLayout);
			importedLayout.setLayoutId(layoutId);

			boolean addGroupPermissions = true;

			Group group = importedLayout.getGroup();

			if (privateLayout && group.isUser()) {
				addGroupPermissions = false;
			}

			boolean addGuestPermissions = false;

			if (!privateLayout || layout.isTypeControlPanel()) {
				addGuestPermissions = true;
			}

			ResourceLocalServiceUtil.addResources(
				portletDataContext.getCompanyId(), groupId, userId,
				Layout.class.getName(), importedLayout.getPlid(), false,
				addGroupPermissions, addGuestPermissions);

			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, privateLayout);

			importedLayout.setLayoutSet(layoutSet);
		}
		else {
			importedLayout = existingLayout;
		}

		newLayoutsMap.put(oldLayoutId, importedLayout);

		long parentLayoutId = layout.getParentLayoutId();

		String parentLayoutUuid = GetterUtil.getString(
			layoutElement.attributeValue("parent-layout-uuid"));

		Element parentLayoutElement =
			portletDataContext.getReferenceDataElement(
				layout, Layout.class, layout.getGroupId(), parentLayoutUuid);

		if ((parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) &&
			(parentLayoutElement != null)) {

			String parentLayoutPath = parentLayoutElement.attributeValue(
				"path");

			Layout parentLayout =
				(Layout)portletDataContext.getZipEntryAsObject(
					parentLayoutPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, parentLayout);

			Layout importedParentLayout = newLayoutsMap.get(parentLayoutId);

			parentLayoutId = importedParentLayout.getLayoutId();
		}

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Importing layout with layout id ");
			sb.append(layoutId);
			sb.append(" and parent layout id ");
			sb.append(parentLayoutId);

			_log.debug(sb.toString());
		}

		importedLayout.setCompanyId(portletDataContext.getCompanyId());
		importedLayout.setParentLayoutId(parentLayoutId);
		importedLayout.setName(layout.getName());
		importedLayout.setTitle(layout.getTitle());
		importedLayout.setDescription(layout.getDescription());
		importedLayout.setKeywords(layout.getKeywords());
		importedLayout.setRobots(layout.getRobots());
		importedLayout.setType(layout.getType());

		String portletsMergeMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE);

		if (layout.isTypeArticle()) {
			importJournalArticle(portletDataContext, layout, layoutElement);

			updateTypeSettings(importedLayout, layout);
		}
		else if (layout.isTypePortlet() &&
				 Validator.isNotNull(layout.getTypeSettings()) &&
				 !portletsMergeMode.equals(
					 PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

			mergePortlets(
				importedLayout, layout.getTypeSettings(), portletsMergeMode);
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			String linkedToLayoutUuid = layoutElement.attributeValue(
				"linked-to-layout-uuid");

			if (linkToLayoutId > 0) {
				Element linkedToLayoutElement =
					portletDataContext.getReferenceDataElement(
						layout, Layout.class, layout.getGroupId(),
						linkedToLayoutUuid);

				if (linkedToLayoutElement != null) {
					String linkedToLayoutPath =
						linkedToLayoutElement.attributeValue("path");

					Layout linkedToLayout =
						(Layout)portletDataContext.getZipEntryAsObject(
							linkedToLayoutPath);

					StagedModelDataHandlerUtil.importStagedModel(
						portletDataContext, linkedToLayout);

					Layout importedLinkedLayout = newLayoutsMap.get(
						linkToLayoutId);

					typeSettingsProperties.setProperty(
						"privateLayout",
						String.valueOf(importedLinkedLayout.isPrivateLayout()));
					typeSettingsProperties.setProperty(
						"linkToLayoutId",
						String.valueOf(importedLinkedLayout.getLayoutId()));
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(6);

						sb.append("Unable to link layout with friendly URL ");
						sb.append(layout.getFriendlyURL());
						sb.append(" and layout id ");
						sb.append(layout.getLayoutId());
						sb.append(" to layout with layout id ");
						sb.append(linkToLayoutId);

						_log.warn(sb.toString());
					}
				}
			}

			updateTypeSettings(importedLayout, layout);
		}
		else {
			updateTypeSettings(importedLayout, layout);
		}

		importedLayout.setHidden(layout.isHidden());
		importedLayout.setFriendlyURL(friendlyURL);

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (importThemeSettings) {
			importedLayout.setThemeId(layout.getThemeId());
			importedLayout.setColorSchemeId(layout.getColorSchemeId());
		}
		else {
			importedLayout.setThemeId(StringPool.BLANK);
			importedLayout.setColorSchemeId(StringPool.BLANK);
		}

		importedLayout.setWapThemeId(layout.getWapThemeId());
		importedLayout.setWapColorSchemeId(layout.getWapColorSchemeId());
		importedLayout.setCss(layout.getCss());
		importedLayout.setPriority(layout.getPriority());
		importedLayout.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		importedLayout.setLayoutPrototypeLinkEnabled(
			layout.isLayoutPrototypeLinkEnabled());

		StagingUtil.updateLastImportSettings(
			layoutElement, importedLayout, portletDataContext);

		fixTypeSettings(importedLayout);

		importedLayout.setIconImage(false);

		if (layout.isIconImage()) {
			String iconImagePath = layoutElement.elementText("icon-image-path");

			byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(
				iconImagePath);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				importedLayout.setIconImage(true);

				if (importedLayout.getIconImageId() == 0) {
					long iconImageId = CounterLocalServiceUtil.increment();

					importedLayout.setIconImageId(iconImageId);
				}

				ImageLocalServiceUtil.updateImage(
					importedLayout.getIconImageId(), iconBytes);
			}
		}
		else {
			ImageLocalServiceUtil.deleteImage(importedLayout.getIconImageId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutElement, importedLayout, null);

		importedLayout.setExpandoBridgeAttributes(serviceContext);

		LayoutUtil.update(importedLayout);

		portletDataContext.setPlid(importedLayout.getPlid());
		portletDataContext.setOldPlid(layout.getPlid());

		List<Layout> newLayouts = portletDataContext.getNewLayouts();

		newLayouts.add(importedLayout);

		// Layout ratings

		portletDataContext.importRatingsEntries(
			Layout.class, layout.getPlid(), importedLayout.getPlid());

		boolean importPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (importPermissions) {
			_permissionImporter.importLayoutPermissions(
				new LayoutCache(), portletDataContext.getCompanyId(), groupId,
				userId, importedLayout, layoutElement,
				portletDataContext.getImportDataRootElement());
		}

		boolean importPublicLayoutPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PUBLIC_LAYOUT_PERMISSIONS);

		if (importPublicLayoutPermissions) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(importedLayout.getPlid());

			Role guestRole = RoleLocalServiceUtil.getRole(
				importedLayout.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				importedLayout.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
				guestRole.getRoleId(), new String[]{ActionKeys.VIEW});
		}
	}

	protected void exportJournalArticle(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String articleId = typeSettingsProperties.getProperty(
			"article-id", StringPool.BLANK);

		long articleGroupId = layout.getGroupId();

		if (Validator.isNull(articleId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No article id found in typeSettings of layout " +
						layout.getPlid());
			}
		}

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getLatestArticle(
				articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("No approved article found with group id ");
				sb.append(articleGroupId);
				sb.append(" and layout id ");
				sb.append(articleId);

				_log.warn(sb.toString());
			}
		}

		if (article == null) {
			return;
		}

		portletDataContext.setExportDataRootElement(layoutElement.getParent());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, article);

		portletDataContext.addReferenceElement(
			layout, layoutElement, article,
			PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);
	}

	protected void fixTypeSettings(Layout layout) throws Exception {
		if (!layout.isTypeURL()) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = GetterUtil.getString(typeSettings.getProperty("url"));

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		if (!url.startsWith(friendlyURLPrivateGroupPath) &&
			!url.startsWith(friendlyURLPrivateUserPath) &&
			!url.startsWith(friendlyURLPublicPath)) {

			return;
		}

		int x = url.indexOf(CharPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(CharPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);

		Group group = layout.getGroup();

		String groupFriendlyURL = group.getFriendlyURL();

		if (!friendlyURL.equals(groupFriendlyURL)) {
			return;
		}

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + LayoutExporter.SAME_GROUP_FRIENDLY_URL +
				url.substring(y));
	}

	protected void importJournalArticle(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String articleId = typeSettingsProperties.getProperty(
			"article-id", StringPool.BLANK);

		if (Validator.isNull(articleId)) {
			return;
		}

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				layoutElement, JournalArticle.class);

		if (!referenceDataElements.isEmpty()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElements.get(0));
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		articleId = MapUtil.getString(articleIds, articleId, articleId);

		typeSettingsProperties.setProperty("article-id", articleId);

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			portletDataContext.getScopeGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), StringPool.BLANK, articleId, true);
	}

	protected void mergePortlets(
		Layout layout, String newTypeSettings, String portletsMergeMode) {

		try {
			UnicodeProperties previousTypeSettingsProperties =
				layout.getTypeSettingsProperties();

			LayoutTypePortlet previousLayoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			LayoutTemplate previousLayoutTemplate =
				previousLayoutType.getLayoutTemplate();

			List<String> previousColumns = previousLayoutTemplate.getColumns();

			UnicodeProperties newTypeSettingsProperties = new UnicodeProperties(
				true);

			newTypeSettingsProperties.load(newTypeSettings);

			String layoutTemplateId = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

			previousTypeSettingsProperties.setProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
				layoutTemplateId);

			String nestedColumnIds = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS);

			if (Validator.isNotNull(nestedColumnIds)) {
				previousTypeSettingsProperties.setProperty(
					LayoutTypePortletConstants.NESTED_COLUMN_IDS,
					nestedColumnIds);

				String[] nestedColumnIdsArray = StringUtil.split(
					nestedColumnIds);

				for (String nestedColumnId : nestedColumnIdsArray) {
					String nestedColumnValue =
						newTypeSettingsProperties.getProperty(nestedColumnId);

					previousTypeSettingsProperties.setProperty(
						nestedColumnId, nestedColumnValue);
				}
			}

			LayoutTemplate newLayoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, null);

			String[] newPortletIds = new String[0];

			for (String columnId : newLayoutTemplate.getColumns()) {
				String columnValue = newTypeSettingsProperties.getProperty(
					columnId);

				String[] portletIds = StringUtil.split(columnValue);

				if (!previousColumns.contains(columnId)) {
					newPortletIds = ArrayUtil.append(newPortletIds, portletIds);
				}
				else {
					String[] previousPortletIds = StringUtil.split(
						previousTypeSettingsProperties.getProperty(columnId));

					portletIds = appendPortletIds(
						previousPortletIds, portletIds, portletsMergeMode);

					previousTypeSettingsProperties.setProperty(
						columnId, StringUtil.merge(portletIds));
				}
			}

			// Add portlets in non-existent column to the first column

			String columnId = previousColumns.get(0);

			String[] portletIds = StringUtil.split(
				previousTypeSettingsProperties.getProperty(columnId));

			appendPortletIds(portletIds, newPortletIds, portletsMergeMode);

			previousTypeSettingsProperties.setProperty(
				columnId, StringUtil.merge(portletIds));

			layout.setTypeSettings(previousTypeSettingsProperties.toString());
		}
		catch (IOException ioe) {
			layout.setTypeSettings(newTypeSettings);
		}
	}

	protected void updateTypeSettings(Layout importedLayout, Layout layout)
		throws PortalException, SystemException {

		LayoutTypePortlet importedLayoutType =
			(LayoutTypePortlet)importedLayout.getLayoutType();

		List<String> importedPortletIds = importedLayoutType.getPortletIds();

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		importedPortletIds.removeAll(layoutType.getPortletIds());

		if (!importedPortletIds.isEmpty()) {
			PortletLocalServiceUtil.deletePortlets(
				importedLayout.getCompanyId(),
				importedPortletIds.toArray(
					new String[importedPortletIds.size()]),
				importedLayout.getPlid());
		}

		importedLayout.setTypeSettings(layout.getTypeSettings());
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutStagedModelDataHandler.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PermissionImporter _permissionImporter = new PermissionImporter();

}