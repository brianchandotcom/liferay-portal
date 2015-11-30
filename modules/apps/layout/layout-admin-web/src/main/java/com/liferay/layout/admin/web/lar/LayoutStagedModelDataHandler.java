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

package com.liferay.layout.admin.web.lar;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.adapter.StagedTheme;
import com.liferay.portal.model.adapter.impl.StagedThemeImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutTemplateLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.portlet.exportimport.staging.LayoutStagingUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutStagedModelDataHandler
	extends BaseStagedModelDataHandler<Layout> {

	public static final String[] CLASS_NAMES = {Layout.class.getName()};

	@Override
	public void deleteStagedModel(Layout layout) {
		_layoutLocalService.deleteLayout(layout);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			extraData);

		boolean privateLayout = extraDataJSONObject.getBoolean("privateLayout");

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			uuid, groupId, privateLayout);

		if (layout != null) {
			deleteStagedModel(layout);
		}
	}

	@Override
	public List<Layout> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutLocalService.getLayoutsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Layout>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Layout layout) {
		return layout.getNameCurrentValue();
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, Layout layout) {

		Map<String, String> referenceAttributes = new HashMap<>();

		referenceAttributes.put(
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		referenceAttributes.put(
			"layout-id", String.valueOf(layout.getLayoutId()));

		return referenceAttributes;
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		boolean privateLayout = GetterUtil.getBoolean(
			referenceElement.attributeValue("private-layout"));

		Layout existingLayout = fetchMissingReference(
			uuid, groupId, privateLayout);

		if (existingLayout == null) {
			return false;
		}

		return true;
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

	protected void deleteMissingLayoutFriendlyURLs(
		PortletDataContext portletDataContext, Layout layout) {

		Map<Long, Long> layoutFriendlyURLIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutFriendlyURL.class);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				layout.getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			if (!layoutFriendlyURLIds.containsValue(
					layoutFriendlyURL.getLayoutFriendlyURLId())) {

				_layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(
					layoutFriendlyURL);
			}
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		Element layoutElement = portletDataContext.getExportDataElement(layout);

		populateElementLayoutMetadata(layoutElement, layout);

		layoutElement.addAttribute(Constants.ACTION, Constants.ADD);

		portletDataContext.setPlid(layout.getPlid());

		long parentLayoutId = layout.getParentLayoutId();

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			Layout parentLayout = _layoutLocalService.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			if (parentLayout != null) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, layout, parentLayout,
					PortletDataContext.REFERENCE_TYPE_PARENT);

				layoutElement.addAttribute(
					"parent-layout-uuid", parentLayout.getUuid());
			}
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				layout.getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layout, layoutFriendlyURL,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		if (layout.isIconImage()) {
			exportLayoutIconImage(portletDataContext, layout, layoutElement);
		}

		if (layout.isTypeLinkToLayout()) {
			exportLinkedLayout(portletDataContext, layout, layoutElement);
		}

		fixExportTypeSettings(layout);

		exportTheme(portletDataContext, layout);

		portletDataContext.addClassedModel(
			layoutElement, ExportImportPathUtil.getModelPath(layout), layout);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		boolean privateLayout = GetterUtil.getBoolean(
			referenceElement.attributeValue("private-layout"));

		Layout existingLayout = null;

		existingLayout = fetchMissingReference(uuid, groupId, privateLayout);

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		long layoutId = GetterUtil.getLong(
			referenceElement.attributeValue("layout-id"));

		layouts.put(layoutId, existingLayout);

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		layoutPlids.put(plid, existingLayout.getPlid());
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

		boolean privateLayout = portletDataContext.isPrivateLayout();

		String action = layoutElement.attributeValue(Constants.ACTION);

		if (action.equals(Constants.DELETE)) {
			Layout deletingLayout =
				_layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, groupId, privateLayout);

			_layoutLocalService.deleteLayout(
				deletingLayout, false,
				ServiceContextThreadLocal.getServiceContext());

			return;
		}

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		Layout existingLayout = null;
		Layout importedLayout = null;

		String friendlyURL = layout.getFriendlyURL();

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

			layoutId = _layoutLocalService.getNextLayoutId(
				groupId, privateLayout);
			friendlyURL = StringPool.SLASH + layoutId;
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

			Locale locale = LocaleUtil.getSiteDefault();

			String localizedName = layout.getName(locale);

			List<Layout> previousLayouts = _layoutLocalService.getLayouts(
				groupId, privateLayout);

			for (Layout curLayout : previousLayouts) {
				if (localizedName.equals(curLayout.getName(locale)) ||
					friendlyURL.equals(curLayout.getFriendlyURL())) {

					existingLayout = curLayout;

					break;
				}
			}

			if (existingLayout == null) {
				layoutId = _layoutLocalService.getNextLayoutId(
					groupId, privateLayout);

				friendlyURL = getFriendlyURL(friendlyURL, layoutId);
			}
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			existingLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layout.getUuid(), groupId, privateLayout);

			if (SitesUtil.isLayoutModifiedSinceLastMerge(existingLayout)) {
				layouts.put(oldLayoutId, existingLayout);

				return;
			}

			LayoutFriendlyURL layoutFriendlyURL =
				_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
					groupId, privateLayout, friendlyURL);

			if ((layoutFriendlyURL != null) && (existingLayout == null)) {
				Layout mergeFailFriendlyURLLayout =
					_layoutLocalService.getLayout(layoutFriendlyURL.getPlid());

				SitesUtil.addMergeFailFriendlyURLLayout(
					mergeFailFriendlyURLLayout);

				if (!_log.isWarnEnabled()) {
					return;
				}

				StringBundler sb = new StringBundler(6);

				sb.append("Layout with layout ID ");
				sb.append(layout.getLayoutId());
				sb.append(" cannot be propagated because the friendly URL ");
				sb.append("conflicts with the friendly URL of layout with ");
				sb.append("layout ID ");
				sb.append(mergeFailFriendlyURLLayout.getLayoutId());

				_log.warn(sb.toString());

				return;
			}
		}
		else {

			// The default behavior of import mode is
			// PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID

			existingLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layout.getUuid(), groupId, privateLayout);

			if (existingLayout == null) {
				existingLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
					groupId, privateLayout, friendlyURL);
			}

			if (existingLayout == null) {
				layoutId = _layoutLocalService.getNextLayoutId(
					groupId, privateLayout);

				friendlyURL = getFriendlyURL(friendlyURL, layoutId);
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
			long plid = _counterLocalService.increment();

			importedLayout = _layoutLocalService.createLayout(plid);

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				importedLayout.setSourcePrototypeLayoutUuid(layout.getUuid());

				layoutId = _layoutLocalService.getNextLayoutId(
					groupId, privateLayout);

				friendlyURL = getFriendlyURL(friendlyURL, layoutId);
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
			importedLayout.setUserId(userId);
			importedLayout.setPrivateLayout(privateLayout);
			importedLayout.setLayoutId(layoutId);

			initNewLayoutPermissions(
				portletDataContext.getCompanyId(), groupId, userId, layout,
				importedLayout, privateLayout);

			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, privateLayout);

			importedLayout.setLayoutSet(layoutSet);
		}
		else {
			importedLayout = existingLayout;
		}

		portletDataContext.setPlid(importedLayout.getPlid());
		portletDataContext.setOldPlid(layout.getPlid());

		long parentLayoutId = layout.getParentLayoutId();

		String parentLayoutUuid = GetterUtil.getString(
			layoutElement.attributeValue("parent-layout-uuid"));

		Element parentLayoutElement =
			portletDataContext.getReferenceDataElement(
				layout, Layout.class, layout.getGroupId(), parentLayoutUuid);

		if ((parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) &&
			(parentLayoutElement != null)) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, parentLayoutElement);

			Layout importedParentLayout = layouts.get(parentLayoutId);

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

		if (layout.getLayoutPrototypeUuid() != null) {
			importedLayout.setModifiedDate(new Date());
		}

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

		if (layout.isTypePortlet() &&
			Validator.isNotNull(layout.getTypeSettings()) &&
			!portletsMergeMode.equals(
				PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

			mergePortlets(
				importedLayout, layout.getTypeSettings(), portletsMergeMode);
		}
		else if (layout.isTypeLinkToLayout()) {
			importLinkedLayout(
				portletDataContext, layout, importedLayout, layoutElement,
				layouts);
		}
		else {
			updateTypeSettings(importedLayout, layout);
		}

		importedLayout.setHidden(layout.isHidden());
		importedLayout.setFriendlyURL(
			getUniqueFriendlyURL(
				portletDataContext, importedLayout, friendlyURL));

		if (layout.getIconImageId() > 0) {
			importLayoutIconImage(
				portletDataContext, importedLayout, layoutElement);
		}
		else if (importedLayout.getIconImageId() > 0) {
			_imageLocalService.deleteImage(importedLayout.getIconImageId());
		}

		if (existingLayout == null) {
			int priority = _layoutLocalServiceHelper.getNextPriority(
				groupId, privateLayout, parentLayoutId, null, -1);

			importedLayout.setPriority(priority);
		}

		importedLayout.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		importedLayout.setLayoutPrototypeLinkEnabled(
			layout.isLayoutPrototypeLinkEnabled());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layout);

		importedLayout.setExpandoBridgeAttributes(serviceContext);

		StagingUtil.updateLastImportSettings(
			layoutElement, importedLayout, portletDataContext);

		fixImportTypeSettings(importedLayout);

		importTheme(portletDataContext, layout, importedLayout);

		_layoutLocalService.updateLayout(importedLayout);

		_layoutSetLocalService.updatePageCount(groupId, privateLayout);

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		layoutPlids.put(layout.getPlid(), importedLayout.getPlid());

		layouts.put(oldLayoutId, importedLayout);

		importAssets(portletDataContext, layout, importedLayout);

		importLayoutFriendlyURLs(portletDataContext, layout, importedLayout);

		portletDataContext.importClassedModel(layout, importedLayout);
	}

	protected void exportLayoutIconImage(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		Image image = _imageLocalService.getImage(layout.getIconImageId());

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

	protected void exportLinkedLayout(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long linkToLayoutId = GetterUtil.getLong(
			typeSettingsProperties.getProperty(
				"linkToLayoutId", StringPool.BLANK));

		if (linkToLayoutId > 0) {
			try {
				Layout linkedToLayout = _layoutLocalService.getLayout(
					portletDataContext.getScopeGroupId(),
					layout.isPrivateLayout(), linkToLayoutId);

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, layout, linkedToLayout,
					PortletDataContext.REFERENCE_TYPE_STRONG);

				layoutElement.addAttribute(
					"linked-to-layout-uuid", linkedToLayout.getUuid());
			}
			catch (NoSuchLayoutException nsle) {
			}
		}
	}

	protected void exportTheme(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		boolean exportThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Export theme settings " + exportThemeSettings);
		}

		if (exportThemeSettings &&
			!portletDataContext.isPerformDirectBinaryImport() &&
			!layout.isInheritLookAndFeel()) {

			StagedTheme stagedTheme = new StagedThemeImpl(layout.getTheme());

			Element layoutElement = portletDataContext.getExportDataElement(
				layout);

			portletDataContext.addReferenceElement(
				layout, layoutElement, stagedTheme,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	protected Object[] extractFriendlyURLInfo(Layout layout) {
		if (!layout.isTypeURL()) {
			return null;
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

			return null;
		}

		int x = url.indexOf(CharPool.SLASH, 1);

		if (x == -1) {
			return null;
		}

		int y = url.indexOf(CharPool.SLASH, x + 1);

		if (y == -1) {
			return null;
		}

		return new Object[] {url.substring(x, y), url, x, y};
	}

	protected Layout fetchMissingReference(
		String uuid, long groupId, boolean privateLayout) {

		// Try to fetch the existing layout from the importing group

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			uuid, groupId, privateLayout);

		if (layout != null) {
			return layout;
		}

		try {

			// Try to fetch the existing layout from the parent sites

			Group originalGroup = _groupLocalService.getGroup(groupId);

			Group group = originalGroup.getParentGroup();

			while (group != null) {
				layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					uuid, group.getGroupId(), privateLayout);

				if (layout != null) {
					break;
				}

				group = group.getParentGroup();
			}

			if (layout == null) {
				List<Layout> layouts = fetchStagedModelsByUuidAndCompanyId(
					uuid, originalGroup.getCompanyId());

				if (ListUtil.isEmpty(layouts)) {
					return null;
				}

				layout = layouts.get(0);
			}

			return layout;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch missing reference layout from group " +
						groupId);
			}

			return null;
		}
	}

	protected void fixExportTypeSettings(Layout layout) throws Exception {
		Object[] friendlyURLInfo = extractFriendlyURLInfo(layout);

		if (friendlyURLInfo == null) {
			return;
		}

		String friendlyURL = (String)friendlyURLInfo[0];

		Group group = layout.getGroup();

		String groupFriendlyURL = group.getFriendlyURL();

		if (!friendlyURL.equals(groupFriendlyURL)) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = (String)friendlyURLInfo[1];

		int x = (Integer)friendlyURLInfo[2];
		int y = (Integer)friendlyURLInfo[3];

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + _SAME_GROUP_FRIENDLY_URL + url.substring(y));
	}

	protected void fixImportTypeSettings(Layout layout) throws Exception {
		Object[] friendlyURLInfo = extractFriendlyURLInfo(layout);

		if (friendlyURLInfo == null) {
			return;
		}

		String friendlyURL = (String)friendlyURLInfo[0];

		if (!friendlyURL.equals(_SAME_GROUP_FRIENDLY_URL)) {
			return;
		}

		Group group = layout.getGroup();

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = (String)friendlyURLInfo[1];

		int x = (Integer)friendlyURLInfo[2];
		int y = (Integer)friendlyURLInfo[3];

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + group.getFriendlyURL() + url.substring(y));
	}

	protected String getFriendlyURL(String friendlyURL, long layoutId) {
		if (!Validator.isNumber(friendlyURL.substring(1))) {
			return friendlyURL;
		}

		return StringPool.SLASH + layoutId;
	}

	protected String getUniqueFriendlyURL(
		PortletDataContext portletDataContext, Layout existingLayout,
		String friendlyURL) {

		for (int i = 1;; i++) {
			Layout duplicateFriendlyURLLayout =
				_layoutLocalService.fetchLayoutByFriendlyURL(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout(), friendlyURL);

			if ((duplicateFriendlyURLLayout == null) ||
				(duplicateFriendlyURLLayout.getPlid() ==
					existingLayout.getPlid())) {

				break;
			}

			friendlyURL = friendlyURL + i;
		}

		return friendlyURL;
	}

	protected void importAssets(
			PortletDataContext portletDataContext, Layout layout,
			Layout importedLayout)
		throws Exception {

		long userId = portletDataContext.getUserId(layout.getUserUuid());

		long[] assetCategoryIds = portletDataContext.getAssetCategoryIds(
			Layout.class, layout.getPlid());
		String[] assetTagNames = portletDataContext.getAssetTagNames(
			Layout.class, layout.getPlid());

		_layoutLocalService.updateAsset(
			userId, importedLayout, assetCategoryIds, assetTagNames);
	}

	protected void importLayoutFriendlyURLs(
			PortletDataContext portletDataContext, Layout layout,
			Layout importedLayout)
		throws Exception {

		List<Element> layoutFriendlyURLElements =
			portletDataContext.getReferenceDataElements(
				layout, LayoutFriendlyURL.class);

		for (Element layoutFriendlyURLElement : layoutFriendlyURLElements) {
			String layoutFriendlyURLPath =
				layoutFriendlyURLElement.attributeValue("path");

			LayoutFriendlyURL layoutFriendlyURL =
				(LayoutFriendlyURL)portletDataContext.getZipEntryAsObject(
					layoutFriendlyURLPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutFriendlyURL);
		}

		deleteMissingLayoutFriendlyURLs(portletDataContext, importedLayout);
	}

	protected void importLayoutIconImage(
			PortletDataContext portletDataContext, Layout importedLayout,
			Element layoutElement)
		throws Exception {

		String iconImagePath = layoutElement.elementText("icon-image-path");

		byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(
			iconImagePath);

		if (ArrayUtil.isNotEmpty(iconBytes)) {
			if (importedLayout.getIconImageId() == 0) {
				long iconImageId = _counterLocalService.increment();

				importedLayout.setIconImageId(iconImageId);
			}

			_imageLocalService.updateImage(
				importedLayout.getIconImageId(), iconBytes);
		}
	}

	protected void importLinkedLayout(
			PortletDataContext portletDataContext, Layout layout,
			Layout importedLayout, Element layoutElement,
			Map<Long, Layout> layouts)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long linkToLayoutId = GetterUtil.getLong(
			typeSettingsProperties.getProperty(
				"linkToLayoutId", StringPool.BLANK));

		String linkedToLayoutUuid = layoutElement.attributeValue(
			"linked-to-layout-uuid");

		if (Validator.isNull(linkedToLayoutUuid)) {
			return;
		}

		if (linkToLayoutId <= 0) {
			updateTypeSettings(importedLayout, layout);

			return;
		}

		Element linkedToLayoutElement =
			portletDataContext.getReferenceDataElement(
				layout, Layout.class, layout.getGroupId(), linkedToLayoutUuid);

		if (linkedToLayoutElement != null) {
			String linkedToLayoutPath = linkedToLayoutElement.attributeValue(
				"path");

			Layout linkedToLayout =
				(Layout)portletDataContext.getZipEntryAsObject(
					linkedToLayoutPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, linkedToLayout);

			Layout importedLinkedLayout = layouts.get(linkToLayoutId);

			if (importedLinkedLayout == null) {
				throw new NoSuchLayoutException(
					"Layout with layout id " + linkToLayoutId +
						" that is linked from layout with layout id " +
							layout.getLayoutId() + " does not exist");
			}

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

		updateTypeSettings(importedLayout, layout);
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext, Layout layout) {
	}

	protected void importTheme(
			PortletDataContext portletDataContext, Layout layout,
			Layout importedLayout)
		throws Exception {

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Import theme settings " + importThemeSettings);
		}

		if (importThemeSettings) {
			importedLayout.setColorSchemeId(layout.getColorSchemeId());
			importedLayout.setCss(layout.getCss());
			importedLayout.setThemeId(layout.getThemeId());
			importedLayout.setWapColorSchemeId(layout.getWapColorSchemeId());
			importedLayout.setWapThemeId(layout.getWapThemeId());
		}
		else {
			importedLayout.setColorSchemeId(StringPool.BLANK);
			importedLayout.setCss(StringPool.BLANK);
			importedLayout.setThemeId(StringPool.BLANK);
			importedLayout.setWapColorSchemeId(StringPool.BLANK);
			importedLayout.setWapThemeId(StringPool.BLANK);
		}
	}

	protected void initNewLayoutPermissions(
			long companyId, long groupId, long userId, Layout layout,
			Layout importedLayout, boolean privateLayout)
		throws Exception {

		boolean addGroupPermissions = true;

		Group group = importedLayout.getGroup();

		if (privateLayout && group.isUser()) {
			addGroupPermissions = false;
		}

		boolean addGuestPermissions = false;

		if (!privateLayout || layout.isTypeControlPanel()) {
			addGuestPermissions = true;
		}

		_resourceLocalService.addResources(
			companyId, groupId, userId, Layout.class.getName(),
			importedLayout.getPlid(), false, addGroupPermissions,
			addGuestPermissions);
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
				_layoutTemplateLocalService.getLayoutTemplate(
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

	protected void populateElementLayoutMetadata(
			Element layoutElement, Layout layout)
		throws Exception {

		LayoutStagingHandler layoutStagingHandler =
			LayoutStagingUtil.getLayoutStagingHandler(layout);

		if (layoutStagingHandler != null) {
			LayoutRevision layoutRevision =
				layoutStagingHandler.getLayoutRevision();

			if (layoutRevision != null) {
				layoutElement.addAttribute(
					"layout-revision-id",
					String.valueOf(layoutRevision.getLayoutRevisionId()));
				layoutElement.addAttribute(
					"layout-branch-id",
					String.valueOf(layoutRevision.getLayoutBranchId()));

				LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

				layoutElement.addAttribute(
					"layout-branch-name",
					String.valueOf(layoutBranch.getName()));
			}
		}

		layoutElement.addAttribute("layout-uuid", layout.getUuid());
		layoutElement.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));
		layoutElement.addAttribute(
			"layout-priority", String.valueOf(layout.getPriority()));

		String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

		if (Validator.isNotNull(layoutPrototypeUuid)) {
			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, layout.getCompanyId());

			layoutElement.addAttribute(
				"layout-prototype-uuid", layoutPrototypeUuid);
			layoutElement.addAttribute(
				"layout-prototype-name",
				layoutPrototype.getName(LocaleUtil.getDefault()));
		}
	}

	@Reference(unbind = "-")
	protected void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutFriendlyURLLocalService(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {

		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalServiceHelper(
		LayoutLocalServiceHelper layoutLocalServiceHelper) {

		_layoutLocalServiceHelper = layoutLocalServiceHelper;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutTemplateLocalService(
		LayoutTemplateLocalService layoutTemplateLocalService) {

		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	protected void updateTypeSettings(Layout importedLayout, Layout layout)
		throws PortalException {

		long groupId = layout.getGroupId();

		try {
			LayoutTypePortlet importedLayoutType =
				(LayoutTypePortlet)importedLayout.getLayoutType();

			List<String> importedPortletIds =
				importedLayoutType.getPortletIds();

			layout.setGroupId(importedLayout.getGroupId());

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			importedPortletIds.removeAll(layoutTypePortlet.getPortletIds());

			if (!importedPortletIds.isEmpty()) {
				_portletLocalService.deletePortlets(
					importedLayout.getCompanyId(),
					importedPortletIds.toArray(
						new String[importedPortletIds.size()]),
					importedLayout.getPlid());
			}

			importedLayout.setTypeSettingsProperties(
				layoutTypePortlet.getTypeSettingsProperties());
		}
		finally {
			layout.setGroupId(groupId);
		}
	}

	private static final String _SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStagedModelDataHandler.class);

	private volatile CounterLocalService _counterLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile ImageLocalService _imageLocalService;
	private volatile LayoutFriendlyURLLocalService
		_layoutFriendlyURLLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile LayoutLocalServiceHelper _layoutLocalServiceHelper;
	private volatile LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private volatile LayoutSetLocalService _layoutSetLocalService;
	private volatile LayoutTemplateLocalService _layoutTemplateLocalService;
	private volatile PortletLocalService _portletLocalService;
	private volatile ResourceLocalService _resourceLocalService;

}