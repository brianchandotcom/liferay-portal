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

package com.liferay.layout.set.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.lar.ThemeExporter;
import com.liferay.exportimport.lar.ThemeImporter;
import com.liferay.layout.set.internal.exportimport.staged.model.repository.StagedLayoutSetStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedLayoutSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedLayoutSet> {

	public static final String[] CLASS_NAMES =
		{StagedLayoutSet.class.getName()};

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void checkLayoutSetPrototypeLayouts(
			PortletDataContext portletDataContext, Set<Layout> modifiedLayouts)
		throws PortalException {

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
			String sourcePrototypeLayoutUuid =
				layout.getSourcePrototypeLayoutUuid();

			if (Validator.isNull(sourcePrototypeLayoutUuid)) {
				continue;
			}

			if (SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {
				modifiedLayouts.add(layout);

				continue;
			}

			Layout sourcePrototypeLayout = LayoutUtil.fetchByUUID_G_P(
				layout.getSourcePrototypeLayoutUuid(),
				layoutSetPrototype.getGroupId(), true);

			if (sourcePrototypeLayout == null) {
				_layoutLocalService.deleteLayout(
					layout, false,
					ServiceContextThreadLocal.getServiceContext());
			}
		}
	}

	protected void deleteMissingLayouts(
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

		List<String> sourceLayoutUuids = layoutElements.stream().map(
			(layoutElement) -> layoutElement.attributeValue("uuid")).collect(
				Collectors.toList());

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

				try {
					_layoutLocalService.deleteLayout(
						layout, false, serviceContext);
				}
				catch (Exception e) {
				}
			}
		}
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		exportLayouts(portletDataContext, stagedLayoutSet);
		exportLogo(portletDataContext, stagedLayoutSet);
		exportTheme(portletDataContext, stagedLayoutSet);

		// Serialization

		Element stagedLayoutSetElement =
			portletDataContext.getExportDataElement(stagedLayoutSet);

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

			ExportImportProcessCallbackRegistryUtil.registerCallback(
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

		Optional<StagedLayoutSet> existingLayoutSetOptional =
			_stagedLayoutSetStagedModelRepository.fetchExistingLayoutSet(
				portletDataContext.getScopeGroupId(),
				stagedLayoutSet.isPrivateLayout());

		StagedLayoutSet importedStagedLayoutSet =
			(StagedLayoutSet)stagedLayoutSet.clone();

		importedStagedLayoutSet.setGroupId(
			portletDataContext.getScopeGroupId());

		if (existingLayoutSetOptional.isPresent()) {
			StagedLayoutSet existingLayoutSet = existingLayoutSetOptional.get();

			importedStagedLayoutSet.setLayoutSetId(
				existingLayoutSet.getLayoutSetId());

			importedStagedLayoutSet =
				_stagedLayoutSetStagedModelRepository.updateStagedModel(
					portletDataContext, importedStagedLayoutSet);
		}

		importLogo(portletDataContext);
		importTheme(portletDataContext, stagedLayoutSet);

		portletDataContext.importClassedModel(
			stagedLayoutSet, importedStagedLayoutSet);

		Element layoutsElement = portletDataContext.getImportDataGroupElement(
			Layout.class);

		List<Element> layoutElements = layoutsElement.elements();

		// Delete missing pages

		deleteMissingLayouts(portletDataContext, layoutElements);

		// Remove layouts that were deleted from the layout set prototype

		Set<Layout> modifiedLayouts = new HashSet<>();

		checkLayoutSetPrototypeLayouts(portletDataContext, modifiedLayouts);

		// Last merge time

		updateLastMergeTime(portletDataContext, modifiedLayouts);

		// Page priorities

		updateLayoutPriorities(
			portletDataContext, layoutElements,
			portletDataContext.isPrivateLayout());

		// Page count

		_layoutSetLocalService.updatePageCount(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());
	}

	protected void exportLayouts(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		// Force to always export layout deletions

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(Layout.class));

		// Force to always have a layout group element

		portletDataContext.getExportDataGroupElement(Layout.class);

		long[] layoutIds = portletDataContext.getLayoutIds();
		List<StagedModel> stagedModels =
			_stagedLayoutSetStagedModelRepository.fetchChildrenStagedModels(
				portletDataContext, stagedLayoutSet);

		for (StagedModel stagedModel : stagedModels) {
			Layout layout = (Layout)stagedModel;

			if (!ArrayUtil.contains(layoutIds, layout.getLayoutId())) {
				Element layoutElement = portletDataContext.getExportDataElement(
					layout);

				layoutElement.addAttribute(Constants.ACTION, Constants.SKIP);

				continue;
			}

			try {
				if (!LayoutStagingUtil.prepareLayoutStagingHandler(
						portletDataContext, layout)) {

					return;
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedLayoutSet, layout,
					PortletDataContext.REFERENCE_TYPE_CHILD);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to export layout " + layout.getName(), e);
				}
			}
		}
	}

	protected void exportLogo(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		boolean logo = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.LOGO);

		if (!logo) {
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
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get logo for layout set branch " +
							layoutSetBranch.getLayoutSetBranchId(),
						pe);
				}
			}
		}
		else {
			try {
				image = _imageLocalService.getImage(
					stagedLayoutSet.getLogoId());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get logo for layout set " +
							stagedLayoutSet.getLayoutSetId(),
						pe);
				}
			}
		}

		if ((image != null) && (image.getTextObj() != null)) {
			String logoPath = ExportImportPathUtil.getRootPath(
				portletDataContext);

			logoPath += "/logo";

			Element rootElement = portletDataContext.getExportDataRootElement();

			Element headerElement = rootElement.element("header");

			headerElement.addAttribute("logo-path", logoPath);

			portletDataContext.addZipEntry(logoPath, image.getTextObj());
		}
	}

	protected void exportTheme(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		ThemeExporter themeExporter = ThemeExporter.getInstance();

		if (layoutSetBranch != null) {
			try {
				themeExporter.exportTheme(portletDataContext, layoutSetBranch);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export theme reference for layout set " +
							"branch " + layoutSetBranch.getLayoutSetBranchId(),
						e);
				}
			}
		}
		else {
			try {
				themeExporter.exportTheme(portletDataContext, stagedLayoutSet);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export theme reference for layout set " +
							stagedLayoutSet.getLayoutSetId(),
						e);
				}
			}
		}
	}

	protected void importLogo(PortletDataContext portletDataContext) {
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
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to import logo", pe);
			}
		}
	}

	protected void importTheme(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		ThemeImporter themeImporter = ThemeImporter.getInstance();

		try {
			themeImporter.importTheme(portletDataContext, stagedLayoutSet);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import theme reference " +
						stagedLayoutSet.getThemeId(),
					e);
			}
		}
	}

	protected void updateLastMergeTime(
			PortletDataContext portletDataContext, Set<Layout> modifiedLayouts)
		throws PortalException {

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

			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

			LayoutUtil.update(layout);
		}

		// The layout set may be stale because LayoutUtil#update(layout)
		// triggers LayoutSetPrototypeLayoutModelListener and that may have
		// updated this layout set

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		String mergeFailFriendlyURLLayouts = settingsProperties.getProperty(
			Sites.MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		if (Validator.isNull(mergeFailFriendlyURLLayouts) &&
			modifiedLayouts.isEmpty()) {

			settingsProperties.setProperty(
				Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

			_layoutSetLocalService.updateLayoutSet(layoutSet);
		}
	}

	protected void updateLayoutPriorities(
		PortletDataContext portletDataContext, List<Element> layoutElements,
		boolean privateLayout) {

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		Map<Long, Integer> layoutPriorities = new HashMap<>();

		int maxPriority = Integer.MIN_VALUE;

		for (Element layoutElement : layoutElements) {
			String action = layoutElement.attributeValue(Constants.ACTION);

			if (action.equals(Constants.SKIP)) {

				// We only want to update priorites if there are no elements
				// with the SKIP action

				return;
			}

			if (action.equals(Constants.ADD)) {
				long layoutId = GetterUtil.getLong(
					layoutElement.attributeValue("layout-id"));

				Layout layout = layouts.get(layoutId);

				// Layout might not have been imported due to a controlled
				// error. See SitesImpl#addMergeFailFriendlyURLLayout.

				if (layout == null) {
					continue;
				}

				int layoutPriority = GetterUtil.getInteger(
					layoutElement.attributeValue("layout-priority"));

				layoutPriorities.put(layout.getPlid(), layoutPriority);

				if (maxPriority < layoutPriority) {
					maxPriority = layoutPriority;
				}
			}
		}

		List<Layout> layoutSetLayouts = _layoutLocalService.getLayouts(
			portletDataContext.getGroupId(), privateLayout);

		for (Layout layout : layoutSetLayouts) {
			if (layoutPriorities.containsKey(layout.getPlid())) {
				layout.setPriority(layoutPriorities.get(layout.getPlid()));
			}
			else {
				layout.setPriority(++maxPriority);
			}

			_layoutLocalService.updateLayout(layout);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetStagedModelDataHandler.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private StagedLayoutSetStagedModelRepository
		_stagedLayoutSetStagedModelRepository;

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