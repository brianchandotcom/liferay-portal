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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.controller.PortletExportController;
import com.liferay.exportimport.controller.PortletImportController;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutTemplateLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.adapter.impl.StagedThemeImpl;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

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
			"layout-id", String.valueOf(layout.getLayoutId()));
		referenceAttributes.put(
			"private-layout", String.valueOf(layout.isPrivateLayout()));

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

		if (Objects.equals(
				layout.getType(), LayoutConstants.TYPE_SHARED_PORTLET)) {

			return;
		}

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

		if (Objects.equals(
				layout.getType(), LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

			exportLinkedLayout(portletDataContext, layout, layoutElement);
		}
		else if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_PORTLET)) {

			exportLayoutPortlets(portletDataContext, layout, layoutElement);
		}

		fixExportTypeSettings(layout);

		exportTheme(portletDataContext, layout);

		portletDataContext.addClassedModel(
			layoutElement, ExportImportPathUtil.getModelPath(layout),
			LayoutStagingUtil.mergeLayoutRevisionIntoLayout(layout));
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

		final long groupId = portletDataContext.getGroupId();
		long userId = portletDataContext.getUserId(layout.getUserUuid());

		Element layoutElement =
			portletDataContext.getImportDataStagedModelElement(layout);

		long layoutId = GetterUtil.getLong(
			layoutElement.attributeValue("layout-id"));

		long oldLayoutId = layoutId;

		final boolean privateLayout = portletDataContext.isPrivateLayout();

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

			sb.append("Importing layout with layout ID ");
			sb.append(layoutId);
			sb.append(" and parent layout ID ");
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

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) &&
			Validator.isNotNull(layout.getTypeSettings()) &&
			!portletsMergeMode.equals(
				PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

			mergePortlets(
				importedLayout, layout.getTypeSettings(), portletsMergeMode);
		}
		else if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

			importLinkedLayout(
				portletDataContext, layout, importedLayout, layoutElement);

			updateTypeSettings(importedLayout, layout);
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
			try {
				final long finalParentLayoutId = parentLayoutId;

				int priority = TransactionInvokerUtil.invoke(
					_transactionConfig,
					new Callable<Integer>() {

						@Override
						public Integer call() throws Exception {
							return _layoutLocalServiceHelper.getNextPriority(
								groupId, privateLayout, finalParentLayoutId,
								null, -1);
						}

					});

				importedLayout.setPriority(priority);
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
		}

		importedLayout.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		importedLayout.setLayoutPrototypeLinkEnabled(
			layout.isLayoutPrototypeLinkEnabled());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layout);

		importedLayout.setExpandoBridgeAttributes(serviceContext);

		_staging.updateLastImportSettings(
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

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) &&
			Validator.isNotNull(layout.getTypeSettings())) {

			importLayoutPortlets(
				portletDataContext, importedLayout, layoutElement);
		}

		importAssets(portletDataContext, layout, importedLayout);

		importLayoutFriendlyURLs(portletDataContext, layout, importedLayout);

		portletDataContext.importClassedModel(layout, importedLayout);
	}

	protected void exportLayoutIconImage(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		Image image = _imageLocalService.getImage(layout.getIconImageId());

		if ((image != null) && (image.getTextObj() != null)) {
			String iconPath = ExportImportPathUtil.getModelPath(
				portletDataContext.getScopeGroupId(), Image.class.getName(),
				image.getImageId());

			Element iconImagePathElement = layoutElement.addElement(
				"icon-image-path");

			iconImagePathElement.addText(iconPath);

			portletDataContext.addZipEntry(iconPath, image.getTextObj());
		}
		else {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Unable to export icon image ");
				sb.append(layout.getIconImageId());
				sb.append(" to layout ");
				sb.append(layout.getLayoutId());

				_log.warn(sb.toString());
			}

			layout.setIconImageId(0);
		}
	}

	protected void exportLayoutPortlets(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean permissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);

		Element portletsElement = layoutElement.addElement("portlets");

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Map<String, Object[]> portletIds = getPortletids(
			portletDataContext, layout);

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			String portletId = (String)portletIdsEntry.getValue()[0];
			long scopeGroupId = (Long)portletIdsEntry.getValue()[1];
			String scopeType = (String)portletIdsEntry.getValue()[2];
			String scopeLayoutUuid = (String)portletIdsEntry.getValue()[3];

			portletDataContext.setPlid(layout.getPlid());
			portletDataContext.setOldPlid(layout.getPlid());
			portletDataContext.setPortletId(portletId);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			Map<String, Boolean> exportPortletControlsMap =
				ExportImportHelperUtil.getExportPortletControlsMap(
					layout.getCompanyId(), portletId, parameterMap,
					portletDataContext.getType());

			try {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_EXPORT_STARTED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));

				_portletExportController.exportPortlet(
					portletDataContext, layout.getPlid(), portletsElement,
					permissions,
					exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
					exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA),
					exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_SETUP),
					exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_EXPORT_SUCCEEDED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));
			}
			catch (Throwable t) {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_EXPORT_FAILED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext),
					t);

				throw t;
			}
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);
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

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsle, nsle);
				}
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
		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_URL)) {
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

	protected Map<String, Object[]> getPortletids(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		if (!LayoutStagingUtil.prepareLayoutStagingHandler(
				portletDataContext, layout) ||
			!layout.isSupportsEmbeddedPortlets()) {

			// Only portlet type layouts support page scoping

			return Collections.emptyMap();
		}

		Map<String, Object[]> portletIds = new HashMap<>();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		// The getAllPortlets method returns all effective nonsystem portlets
		// for any layout type, including embedded portlets, or in the case of
		// panel type layout, selected portlets

		for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
			String portletId = portlet.getPortletId();

			Settings portletInstanceSettings = SettingsFactoryUtil.getSettings(
				new PortletInstanceSettingsLocator(layout, portletId));

			String scopeType = portletInstanceSettings.getValue(
				"lfrScopeType", null);
			String scopeLayoutUuid = portletInstanceSettings.getValue(
				"lfrScopeLayoutUuid", null);

			long scopeGroupId = portletDataContext.getScopeGroupId();

			if (Validator.isNotNull(scopeType)) {
				Group scopeGroup = null;

				if (scopeType.equals("company")) {
					scopeGroup = _groupLocalService.getCompanyGroup(
						layout.getCompanyId());
				}
				else if (scopeType.equals("layout")) {
					Layout scopeLayout = null;

					scopeLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							scopeLayoutUuid, portletDataContext.getGroupId(),
							portletDataContext.isPrivateLayout());

					if (scopeLayout == null) {
						continue;
					}

					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					throw new IllegalArgumentException(
						"Scope type " + scopeType + " is invalid");
				}

				if (scopeGroup != null) {
					scopeGroupId = scopeGroup.getGroupId();
				}
			}

			String key = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			portletIds.put(
				key,
				new Object[] {
					portletId, scopeGroupId, scopeType, scopeLayoutUuid
				});
		}

		return portletIds;
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

	protected void importLayoutPortlets(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED);

		if (layoutSetPrototypeLinkEnabled &&
			Validator.isNotNull(
				portletDataContext.getLayoutSetPrototypeUuid()) &&
			SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {

			return;
		}

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean permissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		LayoutCache layoutCache = new LayoutCache();

		Element portletsElement = layoutElement.element("portlets");

		for (Element portletElement : portletsElement.elements()) {
			String portletPath = portletElement.attributeValue("path");
			String portletId = portletElement.attributeValue("portlet-id");
			long oldPlid = GetterUtil.getLong(
				portletElement.attributeValue("old-plid"));

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				continue;
			}

			portletDataContext.setPlid(layout.getPlid());
			portletDataContext.setOldPlid(oldPlid);
			portletDataContext.setPortletId(portletId);

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
					"portlet", portletId,
					portletDataContext.getManifestSummary());
			}

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(portletPath));

			portletElement = portletDocument.getRootElement();

			// The order of the import is important. You must always import the
			// portlet preferences first, then the portlet data, then the
			// portlet permissions. The import of the portlet data assumes that
			// portlet preferences already exist.

			setPortletScope(portletDataContext, portletElement);

			long portletPreferencesGroupId = portletDataContext.getGroupId();

			Element portletDataElement = portletElement.element("portlet-data");

			Map<String, Boolean> importPortletControlsMap =
				ExportImportHelperUtil.getImportPortletControlsMap(
					portletDataContext.getCompanyId(), portletId, parameterMap,
					portletDataElement,
					portletDataContext.getManifestSummary());

			if (layout != null) {
				portletPreferencesGroupId = layout.getGroupId();
			}

			try {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_IMPORT_STARTED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));

				// Portlet preferences

				_portletImportController.importPortletPreferences(
					portletDataContext, portletDataContext.getCompanyId(),
					portletPreferencesGroupId, layout, portletElement, false,
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_SETUP),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

				// Portlet data

				if (importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA)) {

					_portletImportController.importPortletData(
						portletDataContext, portletDataElement);
				}

				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_IMPORT_SUCCEEDED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));
			}
			catch (Throwable t) {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_IMPORT_FAILED, getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext),
					t);

				throw t;
			}
			finally {
				_portletImportController.resetPortletScope(
					portletDataContext, portletPreferencesGroupId);
			}

			// Portlet permissions

			if (permissions) {
				_permissionImporter.importPortletPermissions(
					layoutCache, portletDataContext.getCompanyId(),
					portletDataContext.getGroupId(), serviceContext.getUserId(),
					layout, portletElement, portletId);
			}

			// Archived setups

			_portletImportController.importPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), null, portletElement, false,
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
		}
	}

	protected void importLinkedLayout(
			PortletDataContext portletDataContext, Layout layout,
			Layout importedLayout, Element layoutElement)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long linkToLayoutId = GetterUtil.getLong(
			typeSettingsProperties.getProperty(
				"linkToLayoutId", StringPool.BLANK));

		String linkedToLayoutUuid = layoutElement.attributeValue(
			"linked-to-layout-uuid");

		if (Validator.isNull(linkedToLayoutUuid) || (linkToLayoutId <= 0)) {
			return;
		}

		ExportImportProcessCallbackRegistryUtil.registerCallback(
			portletDataContext.getExportImportProcessId(),
			new ImportLinkedLayoutCallable(
				portletDataContext.getScopeGroupId(),
				portletDataContext.isPrivateLayout(), importedLayout.getUuid(),
				linkedToLayoutUuid));
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
		}
		else {
			importedLayout.setColorSchemeId(StringPool.BLANK);
			importedLayout.setCss(StringPool.BLANK);
			importedLayout.setThemeId(StringPool.BLANK);
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

		if (!privateLayout ||
			Objects.equals(
				layout.getType(), LayoutConstants.TYPE_CONTROL_PANEL)) {

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
	protected void setExportImportLifecycleManager(
		ExportImportLifecycleManager exportImportLifecycleManager) {

		_exportImportLifecycleManager = exportImportLifecycleManager;
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
	protected void setPortletExportController(
		PortletExportController portletExportController) {

		_portletExportController = portletExportController;
	}

	@Reference(unbind = "-")
	protected void setPortletImportController(
		PortletImportController portletImportController) {

		_portletImportController = portletImportController;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	protected void setPortletScope(
		PortletDataContext portletDataContext, Element portletElement) {

		// Portlet data scope

		String scopeLayoutUuid = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-uuid"));
		String scopeLayoutType = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-type"));

		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
		portletDataContext.setScopeType(scopeLayoutType);

		// Layout scope

		try {
			Group scopeGroup = null;

			if (scopeLayoutType.equals("company")) {
				scopeGroup = _groupLocalService.getCompanyGroup(
					portletDataContext.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				Layout scopeLayout =
					_layoutLocalService.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, portletDataContext.getGroupId(),
						portletDataContext.isPrivateLayout());

				scopeGroup = _groupLocalService.checkScopeGroup(
					scopeLayout, portletDataContext.getUserId(null));

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						boolean privateLayout = GetterUtil.getBoolean(
							portletElement.attributeValue("private-layout"));

						Layout oldLayout =
							_layoutLocalService.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								portletDataContext.getSourceGroupId(),
								privateLayout);

						Group oldScopeGroup = oldLayout.getScopeGroup();

						if (group.isStagingGroup()) {
							scopeGroup.setLiveGroupId(
								oldScopeGroup.getGroupId());

							_groupLocalService.updateGroup(scopeGroup);
						}
						else {
							oldScopeGroup.setLiveGroupId(
								scopeGroup.getGroupId());

							_groupLocalService.updateGroup(oldScopeGroup);
						}
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsle);
						}
					}
				}
			}

			if (scopeGroup != null) {
				portletDataContext.setScopeGroupId(scopeGroup.getGroupId());

				Map<Long, Long> groupIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						Group.class);

				long oldScopeGroupId = GetterUtil.getLong(
					portletElement.attributeValue("scope-group-id"));

				groupIds.put(oldScopeGroupId, scopeGroup.getGroupId());
			}
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
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

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.SUPPORTS,
			new Class<?>[] {PortalException.class, SystemException.class});

	private CounterLocalService _counterLocalService;
	private ExportImportLifecycleManager _exportImportLifecycleManager;
	private GroupLocalService _groupLocalService;
	private ImageLocalService _imageLocalService;
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutLocalServiceHelper _layoutLocalServiceHelper;
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private LayoutTemplateLocalService _layoutTemplateLocalService;
	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();
	private PortletExportController _portletExportController;
	private PortletImportController _portletImportController;
	private PortletLocalService _portletLocalService;
	private ResourceLocalService _resourceLocalService;

	@Reference
	private Staging _staging;

	private class ImportLinkedLayoutCallable implements Callable<Void> {

		public ImportLinkedLayoutCallable(
			long groupId, boolean privateLayout, String layoutUuid,
			String linkedToLayoutUuid) {

			_groupId = groupId;
			_privateLayout = privateLayout;
			_layoutUuid = layoutUuid;
			_linkedToLayoutUuid = linkedToLayoutUuid;
		}

		@Override
		public Void call() throws Exception {
			Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				_layoutUuid, _groupId, _privateLayout);

			if (layout == null) {
				return null;
			}

			Layout linkedToLayout =
				_layoutLocalService.fetchLayoutByUuidAndGroupId(
					_linkedToLayoutUuid, _groupId, _privateLayout);

			if (linkedToLayout == null) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(6);

					sb.append("Unable to link layout with friendly URL ");
					sb.append(layout.getFriendlyURL());
					sb.append(" and layout ID ");
					sb.append(layout.getLayoutId());
					sb.append(" to layout with layout UUID ");
					sb.append(_linkedToLayoutUuid);

					_log.warn(sb.toString());
				}

				return null;
			}

			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				"privateLayout",
				String.valueOf(linkedToLayout.isPrivateLayout()));
			typeSettingsProperties.setProperty(
				"linkToLayoutId", String.valueOf(linkedToLayout.getLayoutId()));

			layout.setTypeSettingsProperties(typeSettingsProperties);

			_layoutLocalService.updateLayout(layout);

			return null;
		}

		private final long _groupId;
		private final String _layoutUuid;
		private final String _linkedToLayoutUuid;
		private final boolean _privateLayout;

	}

}