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

package com.liferay.layout.internal.exportimport.data.handler;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistry;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.lar.ThemeExporter;
import com.liferay.exportimport.lar.ThemeImporter;
import com.liferay.layout.internal.exportimport.staged.model.repository.StagedLayoutSetStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.impl.ThemeSettingImpl;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedLayoutSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedLayoutSet> {

	public static final String[] CLASS_NAMES = {
		StagedLayoutSet.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		_exportLayouts(portletDataContext, stagedLayoutSet);
		_exportLogo(portletDataContext, stagedLayoutSet);
		_exportTheme(portletDataContext, stagedLayoutSet);

		// Layout set prototype settings

		boolean layoutSetPrototypeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS);

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		if (!layoutSetPrototypeSettings) {
			layoutSet.setLayoutSetPrototypeUuid(StringPool.BLANK);
			layoutSet.setLayoutSetPrototypeLinkEnabled(false);
		}

		// Layout set settings

		boolean layoutSetSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (!layoutSetSettings) {
			layoutSet.setSettings(StringPool.BLANK);
		}

		// Serialization

		Element stagedLayoutSetElement =
			portletDataContext.getExportDataElement(stagedLayoutSet);

		// Last publish date must not be exported

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		settingsUnicodeProperties.remove("last-publish-date");

		// Page versioning

		stagedLayoutSet = _unwrapLayoutSetStagingHandler(stagedLayoutSet);

		portletDataContext.addClassedModel(
			stagedLayoutSetElement,
			ExportImportPathUtil.getModelPath(stagedLayoutSet),
			stagedLayoutSet);

		// Last publish date

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate) {

			_exportImportProcessCallbackRegistry.registerCallback(
				portletDataContext.getExportImportProcessId(),
				new UpdateLayoutSetLastPublishDateCallable(
					portletDataContext.getDateRange(),
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout()));
		}
	}

	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		Optional<StagedLayoutSet> existingLayoutSetOptional =
			_stagedLayoutSetStagedModelRepository.fetchExistingLayoutSet(
				portletDataContext.getScopeGroupId(),
				layoutSet.isPrivateLayout());

		layoutSet.setPrivateLayout(portletDataContext.isPrivateLayout());

		StagedLayoutSet importedStagedLayoutSet =
			(StagedLayoutSet)stagedLayoutSet.clone();

		importedStagedLayoutSet.setGroupId(
			portletDataContext.getScopeGroupId());

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);

		if (existingLayoutSetOptional.isPresent() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			StagedLayoutSet existingStagedLayoutSet =
				existingLayoutSetOptional.get();

			LayoutSet existingLayoutSet =
				existingStagedLayoutSet.getLayoutSet();

			LayoutSet importedLayoutSet =
				importedStagedLayoutSet.getLayoutSet();

			importedLayoutSet.setLayoutSetId(
				existingLayoutSet.getLayoutSetId());

			importedStagedLayoutSet =
				_stagedLayoutSetStagedModelRepository.updateStagedModel(
					portletDataContext, importedStagedLayoutSet);
		}

		_importLogo(portletDataContext);
		_importTheme(portletDataContext, stagedLayoutSet);

		portletDataContext.importClassedModel(
			stagedLayoutSet, importedStagedLayoutSet);

		Element layoutsElement = portletDataContext.getImportDataGroupElement(
			Layout.class);

		List<Element> layoutElements = layoutsElement.elements();

		// Delete missing pages

		_deleteMissingLayouts(portletDataContext, layoutElements);

		// Remove layouts that were deleted from the layout set prototype

		Set<Layout> modifiedLayouts = new HashSet<>();

		_checkLayoutSetPrototypeLayouts(portletDataContext, modifiedLayouts);

		_updateLayoutSetSettingsProperties(
			portletDataContext, importedStagedLayoutSet);

		// Last merge time

		LayoutSet importedLayoutSet = importedStagedLayoutSet.getLayoutSet();

		Group group = importedLayoutSet.getGroup();

		if (!group.isLayoutSetPrototype()) {
			_updateLastMergeTime(portletDataContext, modifiedLayouts);
		}

		// Page priorities

		_updateLayoutPriorities(
			portletDataContext, layoutElements,
			portletDataContext.isPrivateLayout());
	}

	private void _checkLayoutSetPrototypeLayouts(
			PortletDataContext portletDataContext, Set<Layout> modifiedLayouts)
		throws Exception {

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED);

		if (!layoutSetPrototypeLinkEnabled ||
			Validator.isNull(portletDataContext.getLayoutSetPrototypeUuid())) {

			return;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					portletDataContext.getLayoutSetPrototypeUuid(),
					portletDataContext.getCompanyId());

		List<Layout> layoutSetLayouts = _layoutLocalService.getLayouts(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		for (Layout layout : layoutSetLayouts) {
			if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
				continue;
			}

			if (SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {
				modifiedLayouts.add(layout);

				continue;
			}

			Layout sourcePrototypeLayout = _layoutLocalService.fetchLayout(
				layout.getSourcePrototypeLayoutUuid(),
				layoutSetPrototype.getGroupId(), true);

			if ((sourcePrototypeLayout == null) &&
				_layoutLocalService.hasLayout(
					layout.getUuid(), layout.getGroupId(),
					layout.isPrivateLayout())) {

				_layoutLocalService.deleteLayout(
					layout, ServiceContextThreadLocal.getServiceContext());
			}
		}
	}

	private void _deleteMissingLayouts(
		PortletDataContext portletDataContext, List<Element> layoutElements) {

		boolean deleteMissingLayouts = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());

		if (!deleteMissingLayouts) {
			return;
		}

		List<Layout> previousLayouts = _layoutLocalService.getLayouts(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		Stream<Element> layoutElementsStream = layoutElements.stream();

		List<String> sourceLayoutUuids = layoutElementsStream.map(
			layoutElement -> layoutElement.attributeValue("uuid")
		).collect(
			Collectors.toList()
		);

		if (_log.isDebugEnabled() && !sourceLayoutUuids.isEmpty()) {
			_log.debug("Delete missing layouts");
		}

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (Layout layout : previousLayouts) {
			if (!sourceLayoutUuids.contains(layout.getUuid()) &&
				!layoutPlids.containsValue(layout.getPlid())) {

				layout = _layoutLocalService.fetchLayout(layout.getPlid());

				if (layout == null) {
					continue;
				}

				String layoutUUID = layout.getUuid();
				long stagingGroupID = portletDataContext.getSourceGroupId();

				try {
					Layout stagedLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							layoutUUID, stagingGroupID,
							!layout.isPublicLayout());

					if ((stagedLayout != null) &&
						_exportImportHelper.isLayoutRevisionInReview(
							stagedLayout)) {

						continue;
					}

					_layoutLocalService.deleteLayout(layout, serviceContext);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to delete layout with UUID " + layoutUUID,
							exception);
					}
				}
			}
		}
	}

	private void _exportLayouts(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		// Force to always export layout deletions

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(Layout.class));

		// Force to always have a layout group element

		portletDataContext.getExportDataGroupElement(Layout.class);

		long[] layoutIds = portletDataContext.getLayoutIds();

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		Group group = layoutSet.getGroup();

		if (group.isLayoutPrototype()) {
			layoutIds = _exportImportHelper.getAllLayoutIds(
				group.getGroupId(), portletDataContext.isPrivateLayout());
		}

		List<StagedModel> stagedModels =
			_stagedLayoutSetStagedModelRepository.fetchChildrenStagedModels(
				portletDataContext, stagedLayoutSet);

		for (StagedModel stagedModel : stagedModels) {
			Layout layout = (Layout)stagedModel;

			if (!ArrayUtil.contains(layoutIds, layout.getLayoutId())) {
				Element layoutElement = portletDataContext.getExportDataElement(
					layout);

				layoutElement.addAttribute(Constants.ACTION, Constants.SKIP);
				layoutElement.addAttribute(
					"layout-parent-layout-id",
					String.valueOf(layout.getParentLayoutId()));

				continue;
			}

			try {
				if (!LayoutStagingUtil.prepareLayoutStagingHandler(
						portletDataContext, layout)) {

					continue;
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedLayoutSet, layout,
					PortletDataContext.REFERENCE_TYPE_CHILD);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export layout " + layout.getName(),
						exception);
				}

				throw exception;
			}
		}
	}

	private void _exportLogo(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		boolean logo = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.LOGO);

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		if (!logo) {
			layoutSet.setLogoId(0);

			return;
		}

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		Image image = null;

		if (layoutSetBranch != null) {
			try {
				image = _imageLocalService.getImage(
					layoutSetBranch.getLogoId());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get logo for layout set branch " +
							layoutSetBranch.getLayoutSetBranchId(),
						portalException);
				}
			}
		}
		else {
			try {
				image = _imageLocalService.getImage(layoutSet.getLogoId());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get logo for layout set " +
							layoutSet.getLayoutSetId(),
						portalException);
				}
			}
		}

		if ((image != null) && (image.getTextObj() != null)) {
			String logoPath = ExportImportPathUtil.getModelPath(
				stagedLayoutSet,
				image.getImageId() + StringPool.PERIOD + image.getType());

			Element rootElement = portletDataContext.getExportDataRootElement();

			Element headerElement = rootElement.element("header");

			headerElement.addAttribute("logo-path", logoPath);

			portletDataContext.addZipEntry(logoPath, image.getTextObj());
		}
	}

	private void _exportTheme(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		boolean exportThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		if (!exportThemeSettings) {
			layoutSet.setThemeId(
				ThemeFactoryUtil.getDefaultRegularThemeId(
					stagedLayoutSet.getCompanyId()));
			layoutSet.setColorSchemeId(
				_colorSchemeFactory.getDefaultRegularColorSchemeId());
			layoutSet.setCss(StringPool.BLANK);

			return;
		}

		String css =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedLayoutSet, layoutSet.getCss(),
					true, false);

		layoutSet.setCss(css);

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		if (layoutSetBranch != null) {
			try {
				_themeExporter.exportTheme(portletDataContext, layoutSetBranch);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export theme reference for layout set " +
							"branch " + layoutSetBranch.getLayoutSetBranchId(),
						exception);
				}
			}
		}
		else {
			try {
				_themeExporter.exportTheme(portletDataContext, layoutSet);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export theme reference for layout set " +
							layoutSet.getLayoutSetId(),
						exception);
				}
			}
		}
	}

	private boolean _hasSiblingLayoutWithSamePriority(
		Layout layout, List<Layout> siblingLayouts) {

		for (Layout siblingLayout : siblingLayouts) {
			if ((layout.getPlid() != siblingLayout.getPlid()) &&
				(layout.getPriority() == siblingLayout.getPriority())) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasSkippedSiblingLayout(
		Element layoutElement, Map<Long, List<String>> siblingActionsMap) {

		long parentLayoutId = GetterUtil.getLong(
			layoutElement.attributeValue("layout-parent-layout-id"));

		List<String> actions = siblingActionsMap.get(parentLayoutId);

		if (actions.contains(Constants.SKIP)) {
			return true;
		}

		return false;
	}

	private void _importLogo(PortletDataContext portletDataContext) {
		boolean logo = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.LOGO);

		if (!logo) {
			return;
		}

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element headerElement = rootElement.element("header");

		String logoPath = headerElement.attributeValue("logo-path");

		byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(logoPath);

		try {
			if (ArrayUtil.isNotEmpty(iconBytes)) {
				_layoutSetLocalService.updateLogo(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout(), true, iconBytes);
			}
			else {
				_layoutSetLocalService.updateLogo(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout(), false, (File)null);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to import logo", portalException);
			}
		}
	}

	private void _importTheme(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		try {
			String css =
				_dlReferencesExportImportContentProcessor.
					replaceImportContentReferences(
						portletDataContext, stagedLayoutSet,
						layoutSet.getCss());

			layoutSet.setCss(css);

			_themeImporter.importTheme(portletDataContext, layoutSet);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import theme reference " +
						layoutSet.getThemeId(),
					exception);
			}
		}
	}

	private StagedLayoutSet _unwrapLayoutSetStagingHandler(
		StagedLayoutSet stagedLayoutSet) {

		LayoutSet layoutSet = ModelAdapterUtil.adapt(
			stagedLayoutSet, StagedLayoutSet.class, LayoutSet.class);

		layoutSet = LayoutStagingUtil.mergeLayoutSetRevisionIntoLayoutSet(
			layoutSet);

		return ModelAdapterUtil.adapt(
			layoutSet, LayoutSet.class, StagedLayoutSet.class);
	}

	private void _updateLastMergeTime(
			PortletDataContext portletDataContext, Set<Layout> modifiedLayouts)
		throws Exception {

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);

		if (!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			return;
		}

		// Last merge time is updated only if there aren not any modified
		// layouts

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		long lastMergeTime = System.currentTimeMillis();

		for (Layout layout : layouts.values()) {
			layout = _layoutLocalService.getLayout(layout.getPlid());

			if (modifiedLayouts.contains(layout)) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				layout.getTypeSettingsProperties();

			typeSettingsUnicodeProperties.setProperty(
				Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

			_layoutLocalService.updateLayout(layout);
		}

		// The layout set may be stale because LayoutUtil#update(layout)
		// triggers LayoutSetPrototypeLayoutModelListener and that may have
		// updated this layout set

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		settingsUnicodeProperties.setProperty(
			Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

		long lastMergeVersion = MapUtil.getLong(
			portletDataContext.getParameterMap(), "lastMergeVersion");

		settingsUnicodeProperties.setProperty(
			Sites.LAST_MERGE_VERSION, String.valueOf(lastMergeVersion));

		_layoutSetLocalService.updateLayoutSet(layoutSet);
	}

	private void _updateLayoutPriorities(
			PortletDataContext portletDataContext, List<Element> layoutElements,
			boolean privateLayout)
		throws Exception {

		if (ExportImportThreadLocal.isInitialLayoutStagingInProcess()) {
			return;
		}

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		Map<Long, Integer> layoutPriorities = new HashMap<>();

		Map<Long, List<String>> siblingActionsMap = new HashMap<>();

		for (Element layoutElement : layoutElements) {
			long elementParentLayoutId = GetterUtil.getLong(
				layoutElement.attributeValue("layout-parent-layout-id"));

			List<String> actions = siblingActionsMap.get(elementParentLayoutId);

			if (actions == null) {
				actions = new ArrayList<>();
			}
			else if (actions.contains(Constants.SKIP)) {
				continue;
			}

			actions.add(layoutElement.attributeValue(Constants.ACTION));

			siblingActionsMap.put(elementParentLayoutId, actions);
		}

		for (Element layoutElement : layoutElements) {
			String action = layoutElement.attributeValue(Constants.ACTION);

			if (action.equals(Constants.SKIP) ||
				_hasSkippedSiblingLayout(layoutElement, siblingActionsMap)) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Do not update priority for layout ",
							layoutElement.attributeValue("uuid"),
							" because there are elements at the same level of ",
							"the page hierarchy with the SKIP action"));
				}

				continue;
			}

			if (action.equals(Constants.ADD)) {
				long layoutId = GetterUtil.getLong(
					layoutElement.attributeValue("layout-id"));

				Layout layout = layouts.get(layoutId);

				if (layout == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Layout ", layoutElement.attributeValue("uuid"),
								" might not have been imported due to a ",
								"controlled error. See ",
								"SitesImpl#addMergeFailFriendlyURLLayout."));
					}

					continue;
				}

				int layoutPriority = GetterUtil.getInteger(
					layoutElement.attributeValue("layout-priority"));

				layoutPriority = _layoutLocalServiceHelper.getNextPriority(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getParentLayoutId(),
					layout.getSourcePrototypeLayoutUuid(), layoutPriority);

				layoutPriorities.put(layout.getPlid(), layoutPriority);
			}
		}

		Set<Long> parentLayoutIds = new HashSet<>();

		Set<Long> updatedPlids = layoutPriorities.keySet();

		for (long plid : updatedPlids) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			int newLayoutPriority = layoutPriorities.get(plid);

			if (layout.getPriority() == newLayoutPriority) {
				continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Updated priority for layout ", layout.getUuid(),
						" from ", layout.getPriority(), " to ",
						newLayoutPriority));
			}

			layout.setPriority(newLayoutPriority);

			_layoutLocalService.updateLayout(layout);

			parentLayoutIds.add(layout.getParentLayoutId());
		}

		for (long parentLayoutId : parentLayoutIds) {
			List<Layout> siblingLayouts = _layoutLocalService.getLayouts(
				portletDataContext.getGroupId(), privateLayout, parentLayoutId);

			for (Layout layout : siblingLayouts) {
				if (!updatedPlids.contains(layout.getPlid()) &&
					_hasSiblingLayoutWithSamePriority(layout, siblingLayouts)) {

					do {
						int priority = layout.getPriority();

						layout.setPriority(++priority);
					}
					while (_hasSiblingLayoutWithSamePriority(
								layout, siblingLayouts));

					_layoutLocalService.updateLayout(layout);
				}
			}
		}
	}

	private void _updateLayoutSetSettingsProperties(
			PortletDataContext portletDataContext,
			StagedLayoutSet importedLayoutSet)
		throws Exception {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		String mergeFailFriendlyURLLayouts =
			settingsUnicodeProperties.getProperty(
				Sites.MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		if (Validator.isNull(mergeFailFriendlyURLLayouts)) {
			boolean changed = false;

			LayoutSet stagedLayoutSet = importedLayoutSet.getLayoutSet();

			UnicodeProperties importedSettingsUnicodeProperties =
				stagedLayoutSet.getSettingsProperties();

			Theme importedTheme = stagedLayoutSet.getTheme();

			Map<String, ThemeSetting> themeSettings =
				importedTheme.getConfigurableSettings();

			Set<Map.Entry<String, ThemeSetting>> themeSettingsEntries =
				themeSettings.entrySet();

			Stream<Map.Entry<String, ThemeSetting>> themeSettingsEntriesStream =
				themeSettingsEntries.stream();

			Map<String, String> defaultsMap =
				themeSettingsEntriesStream.collect(
					Collectors.toMap(
						entry -> ThemeSettingImpl.namespaceProperty(
							"regular", entry.getKey()),
						entry -> {
							ThemeSetting themeSetting = entry.getValue();

							return themeSetting.getValue();
						}));

			defaultsMap.put(Sites.SHOW_SITE_NAME, Boolean.TRUE.toString());
			defaultsMap.put("javascript", null);

			for (Map.Entry<String, String> entry : defaultsMap.entrySet()) {
				String propertyKey = entry.getKey();
				String defaultValue = entry.getValue();

				String currentValue = settingsUnicodeProperties.getProperty(
					propertyKey, defaultValue);

				String importedValue =
					importedSettingsUnicodeProperties.getProperty(
						propertyKey, defaultValue);

				if (!Objects.equals(currentValue, importedValue)) {
					if (Objects.equals(defaultValue, importedValue)) {
						settingsUnicodeProperties.remove(propertyKey);
					}
					else {
						settingsUnicodeProperties.setProperty(
							propertyKey, importedValue);
					}

					changed = true;
				}
			}

			if (changed) {
				_layoutSetLocalService.updateLayoutSet(layoutSet);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetStagedModelDataHandler.class);

	@Reference
	private ColorSchemeFactory _colorSchemeFactory;

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportProcessCallbackRegistry
		_exportImportProcessCallbackRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutLocalServiceHelper _layoutLocalServiceHelper;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private StagedLayoutSetStagedModelRepository
		_stagedLayoutSetStagedModelRepository;

	@Reference
	private ThemeExporter _themeExporter;

	@Reference
	private ThemeImporter _themeImporter;

	private class UpdateLayoutSetLastPublishDateCallable
		implements Callable<Void> {

		public UpdateLayoutSetLastPublishDateCallable(
			DateRange dateRange, long groupId, boolean privateLayout) {

			_dateRange = dateRange;
			_groupId = groupId;
			_privateLayout = privateLayout;
		}

		@Override
		public Void call() throws PortalException {
			Group group = _groupLocalService.getGroup(_groupId);

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				ExportImportDateUtil.updateLastPublishDate(
					stagingGroup.getGroupId(), _privateLayout, _dateRange,
					endDate);
			}
			else {
				ExportImportDateUtil.updateLastPublishDate(
					_groupId, _privateLayout, _dateRange, endDate);
			}

			return null;
		}

		private final DateRange _dateRange;
		private final long _groupId;
		private final boolean _privateLayout;

	}

}