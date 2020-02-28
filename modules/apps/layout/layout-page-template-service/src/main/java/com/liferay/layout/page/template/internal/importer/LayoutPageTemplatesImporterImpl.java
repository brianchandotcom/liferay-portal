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

package com.liferay.layout.page.template.internal.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageTemplate;
import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.exception.PageDefinitionValidatorException;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelper;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelperFactory;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.validator.PageDefinitionValidator;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutPageTemplatesImporter.class)
public class LayoutPageTemplatesImporterImpl
	implements LayoutPageTemplatesImporter {

	@Override
	public void importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			Map<String, PageTemplateCollectionEntry>
				pageTemplateCollectionEntryMap =
					_getPageTemplateCollectionEntryMap(zipFile);

			if (MapUtil.isEmpty(pageTemplateCollectionEntryMap)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No valid layout page template entries found in " +
							zipFile.getName());
				}
			}

			for (Map.Entry<String, PageTemplateCollectionEntry> entry :
					pageTemplateCollectionEntryMap.entrySet()) {

				PageTemplateCollectionEntry pageTemplateCollectionEntry =
					entry.getValue();

				LayoutPageTemplateCollection layoutPageTemplateCollection =
					_getLayoutPageTemplateCollection(
						groupId, pageTemplateCollectionEntry, overwrite);

				_processPageTemplateEntries(
					groupId, layoutPageTemplateCollection,
					pageTemplateCollectionEntry.getPageTemplatesEntries(),
					overwrite);
			}
		}
	}

	private LayoutPageTemplateCollection _getLayoutPageTemplateCollection(
			long groupId,
			PageTemplateCollectionEntry pageTemplateCollectionEntry,
			boolean overwrite)
		throws PortalException {

		String layoutPageTemplateCollectionKey =
			pageTemplateCollectionEntry.getKey();

		PageTemplateCollection pageTemplateCollection =
			pageTemplateCollectionEntry.getPageTemplateCollection();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollection(
					groupId, layoutPageTemplateCollectionKey);

		if (layoutPageTemplateCollection == null) {
			return _layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					groupId, pageTemplateCollection.getName(),
					pageTemplateCollection.getDescription(),
					ServiceContextThreadLocal.getServiceContext());
		}

		if (overwrite) {
			return _layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					pageTemplateCollection.getName(),
					pageTemplateCollection.getDescription());
		}

		return layoutPageTemplateCollection;
	}

	private String _getPageDefinitionJSON(String fileName, ZipFile zipFile)
		throws IOException {

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH) + 1);

		ZipEntry zipEntry = zipFile.getEntry(
			path +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_DEFINITION);

		if (zipEntry == null) {
			return null;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

	private Map<String, PageTemplateCollectionEntry>
			_getPageTemplateCollectionEntryMap(ZipFile zipFile)
		throws IOException {

		Map<String, PageTemplateCollectionEntry> pageTemplateCollectionMap =
			new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateCollectionFile(zipEntry.getName())) {

				continue;
			}

			String[] pathParts = StringUtil.split(
				zipEntry.getName(), CharPool.SLASH);

			String pageTemplateCollectionKey = pathParts[1];

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplateCollection pageTemplateCollection =
				_objectMapper.readValue(content, PageTemplateCollection.class);

			pageTemplateCollectionMap.put(
				pageTemplateCollectionKey,
				new PageTemplateCollectionEntry(
					pageTemplateCollectionKey, pageTemplateCollection));
		}

		enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateFile(zipEntry.getName())) {

				continue;
			}

			String[] pathParts = StringUtil.split(
				zipEntry.getName(), CharPool.SLASH);

			PageTemplateCollectionEntry pageTemplateCollectionEntry =
				pageTemplateCollectionMap.get(pathParts[1]);

			if (pageTemplateCollectionEntry == null) {
				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplate pageTemplate = _objectMapper.readValue(
				content, PageTemplate.class);

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				PageDefinition pageDefinition = _objectMapper.readValue(
					pageDefinitionJSON, PageDefinition.class);

				pageTemplateCollectionEntry.addPageTemplateEntry(
					pathParts[2],
					new PageTemplateEntry(pageTemplate, pageDefinition));
			}
			catch (PageDefinitionValidatorException
						pageDefinitionValidatorException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " +
							pageTemplate.getName());
				}
			}
		}

		return pageTemplateCollectionMap;
	}

	private boolean _isPageTemplateCollectionFile(String fileName) {
		String[] pathParts = StringUtil.split(fileName, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], _ROOT_FOLDER) &&
			Objects.equals(
				pathParts[2],
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE_COLLECTION)) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateFile(String fileName) {
		String[] pathParts = StringUtil.split(fileName, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], _ROOT_FOLDER) &&
			Objects.equals(
				pathParts[3],
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private void _processPageDefinition(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			PageDefinition pageDefinition)
		throws Exception {

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		if (pageDefinition != null) {
			PageElement pageElement = pageDefinition.getPageElement();

			if ((pageElement.getType() == PageElement.Type.ROOT) &&
				(pageElement.getPageElements() != null)) {

				int position = 0;

				for (PageElement childPageElement :
						pageElement.getPageElements()) {

					_processPageElement(
						layoutPageTemplateEntry, layoutStructure,
						childPageElement, rootLayoutStructureItem.getItemId(),
						position);

					position++;
				}
			}
		}

		_updateLayoutPageTemplateStructure(
			layoutPageTemplateEntry, layoutStructure);

		_updateLayouts(layoutPageTemplateEntry);
	}

	private void _processPageElement(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		LayoutStructure layoutStructure, PageElement pageElement,
		String parentItemId, int position) {

		LayoutStructureItemHelperFactory layoutStructureItemHelperFactory =
			LayoutStructureItemHelperFactory.getInstance();

		LayoutStructureItemHelper layoutStructureItemHelper =
			layoutStructureItemHelperFactory.getLayoutStructureItemHelper(
				pageElement.getType());

		if (layoutStructureItemHelper == null) {
			return;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructureItemHelper.addLayoutStructureItem(
				_fragmentCollectionContributorTracker, layoutPageTemplateEntry,
				layoutStructure, pageElement, parentItemId, position);

		if ((layoutStructureItem == null) ||
			(pageElement.getPageElements() == null)) {

			return;
		}

		int childPosition = 0;

		for (PageElement childPageElement : pageElement.getPageElements()) {
			_processPageElement(
				layoutPageTemplateEntry, layoutStructure, childPageElement,
				layoutStructureItem.getItemId(), childPosition);

			childPosition++;
		}
	}

	private void _processPageTemplateEntries(
			long groupId,
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			Map<String, PageTemplateEntry> pageTemplateEntryMap,
			boolean overwrite)
		throws Exception {

		for (Map.Entry<String, PageTemplateEntry> entry :
				pageTemplateEntryMap.entrySet()) {

			PageTemplateEntry pageTemplateEntry = entry.getValue();

			PageTemplate pageTemplate = pageTemplateEntry.getPageTemplate();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(groupId, entry.getKey());

			if (layoutPageTemplateEntry == null) {
				layoutPageTemplateEntry =
					_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
						groupId,
						layoutPageTemplateCollection.
							getLayoutPageTemplateCollectionId(),
						pageTemplate.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
						WorkflowConstants.STATUS_APPROVED,
						ServiceContextThreadLocal.getServiceContext());
			}
			else if (overwrite) {
				layoutPageTemplateEntry =
					_layoutPageTemplateEntryService.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntry.
								getLayoutPageTemplateEntryId(),
							pageTemplate.getName());
			}

			_processPageDefinition(
				layoutPageTemplateEntry, pageTemplateEntry.getPageDefinition());
		}
	}

	private void _updateLayoutPageTemplateStructure(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			LayoutStructure layoutStructure)
		throws PortalException {

		long classNameId = _portal.getClassNameId(Layout.class.getName());

		JSONObject jsonObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(), classNameId,
					layoutPageTemplateEntry.getPlid());

		if (layoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getUserId(),
					layoutPageTemplateEntry.getGroupId(), classNameId,
					layoutPageTemplateEntry.getPlid(), jsonObject.toString(),
					ServiceContextThreadLocal.getServiceContext());
		}
		else {
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(), classNameId,
					layoutPageTemplateEntry.getPlid(), jsonObject.toString());
		}
	}

	private void _updateLayouts(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class.getName()), layout.getPlid());

		_layoutCopyHelper.copyLayout(layout, draftLayout);
	}

	private static final String _ROOT_FOLDER = "page-templates";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesImporterImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	private class PageTemplateCollectionEntry {

		public PageTemplateCollectionEntry(
			String key, PageTemplateCollection pageTemplateCollection) {

			_key = key;
			_pageTemplateCollection = pageTemplateCollection;

			_pageTemplateEntries = new HashMap<>();
		}

		public void addPageTemplateEntry(
			String key, PageTemplateEntry pageTemplateEntry) {

			_pageTemplateEntries.put(key, pageTemplateEntry);
		}

		public String getKey() {
			return _key;
		}

		public PageTemplateCollection getPageTemplateCollection() {
			return _pageTemplateCollection;
		}

		public Map<String, PageTemplateEntry> getPageTemplatesEntries() {
			return _pageTemplateEntries;
		}

		private final String _key;
		private final PageTemplateCollection _pageTemplateCollection;
		private final Map<String, PageTemplateEntry> _pageTemplateEntries;

	}

	private class PageTemplateEntry {

		public PageTemplateEntry(
			PageTemplate pageTemplate, PageDefinition pageDefinition) {

			_pageTemplate = pageTemplate;
			_pageDefinition = pageDefinition;
		}

		public PageDefinition getPageDefinition() {
			return _pageDefinition;
		}

		public PageTemplate getPageTemplate() {
			return _pageTemplate;
		}

		private final PageDefinition _pageDefinition;
		private final PageTemplate _pageTemplate;

	}

}