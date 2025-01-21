/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTagProperty;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutSEOEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSEOEntry> {

	public static final String[] CLASS_NAMES = {LayoutSEOEntry.class.getName()};

	@Override
	public void deleteStagedModel(LayoutSEOEntry layoutSEOEntry) {
		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(layoutSEOEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(uuid, groupId);
	}

	@Override
	public List<LayoutSEOEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutSEOEntryLocalService.
			getLayoutSEOEntriesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		FileEntry openGraphImageFileEntry = _fetchFileEntry(
			layoutSEOEntry.getOpenGraphImageFileEntryId());

		if (openGraphImageFileEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutSEOEntry, openGraphImageFileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		Element layoutSEOEntryElement = portletDataContext.getExportDataElement(
			layoutSEOEntry);

		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags =
			_layoutSEOEntryLocalService.getLayoutSEOEntryCustomMetaTags(
				layoutSEOEntry.getGroupId(),
				layoutSEOEntry.getLayoutSEOEntryId());

		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				layoutSEOEntryCustomMetaTags) {

			Element customMetaTagElement = layoutSEOEntryElement.addElement(
				"custom-meta-tag");

			customMetaTagElement.addAttribute(
				"property", layoutSEOEntryCustomMetaTag.getProperty());

			Map<Locale, String> contentMap =
				layoutSEOEntryCustomMetaTag.getContentMap();

			for (Map.Entry<Locale, String> entry : contentMap.entrySet()) {
				Element contentElement = customMetaTagElement.addElement(
					"content");

				contentElement.addAttribute(
					"language-id", LocaleUtil.toLanguageId(entry.getKey()));
				contentElement.addText(entry.getValue());
			}
		}

		portletDataContext.addClassedModel(
			layoutSEOEntryElement,
			ExportImportPathUtil.getModelPath(layoutSEOEntry), layoutSEOEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long openGraphImageFileEntryId = MapUtil.getLong(
			fileEntryIds, layoutSEOEntry.getOpenGraphImageFileEntryId(), 0);

		LayoutSEOEntry existingLayoutSEOEntry =
			fetchStagedModelByUuidAndGroupId(
				layoutSEOEntry.getUuid(), layoutSEOEntry.getGroupId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSEOEntry);

		if (portletDataContext.isDataStrategyMirror() &&
			(existingLayoutSEOEntry == null)) {

			serviceContext.setUuid(layoutSEOEntry.getUuid());
		}

		if (existingLayoutSEOEntry == null) {
			Layout layout = _layoutLocalService.getLayout(
				portletDataContext.getPlid());

			existingLayoutSEOEntry =
				_layoutSEOEntryLocalService.updateLayoutSEOEntry(
					layoutSEOEntry.getUserId(), layout.getGroupId(),
					layout.isPrivateLayout(), layout.getLayoutId(),
					layoutSEOEntry.isCanonicalURLEnabled(),
					layoutSEOEntry.getCanonicalURLMap(),
					layoutSEOEntry.isOpenGraphDescriptionEnabled(),
					layoutSEOEntry.getOpenGraphDescriptionMap(),
					layoutSEOEntry.getOpenGraphImageAltMap(),
					openGraphImageFileEntryId,
					layoutSEOEntry.isOpenGraphTitleEnabled(),
					layoutSEOEntry.getOpenGraphTitleMap(), serviceContext);
		}
		else {
			existingLayoutSEOEntry =
				_layoutSEOEntryLocalService.updateLayoutSEOEntry(
					existingLayoutSEOEntry.getUserId(),
					portletDataContext.getScopeGroupId(),
					layoutSEOEntry.isPrivateLayout(),
					existingLayoutSEOEntry.getLayoutId(),
					layoutSEOEntry.isCanonicalURLEnabled(),
					layoutSEOEntry.getCanonicalURLMap(),
					layoutSEOEntry.isOpenGraphDescriptionEnabled(),
					layoutSEOEntry.getOpenGraphDescriptionMap(),
					layoutSEOEntry.getOpenGraphImageAltMap(),
					openGraphImageFileEntryId,
					layoutSEOEntry.isOpenGraphTitleEnabled(),
					layoutSEOEntry.getOpenGraphTitleMap(), serviceContext);
		}

		_layoutSEOEntryLocalService.updateCustomMetaTags(
			existingLayoutSEOEntry.getUserId(),
			existingLayoutSEOEntry.getGroupId(),
			existingLayoutSEOEntry.isPrivateLayout(),
			existingLayoutSEOEntry.getLayoutId(),
			_getLayoutSEOEntryCustomMetaTagProperties(
				portletDataContext.getImportDataElement(layoutSEOEntry)),
			serviceContext);
	}

	private FileEntry _fetchFileEntry(long fileEntryId) {
		if (fileEntryId <= 0) {
			return null;
		}

		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get file entry " + fileEntryId, portalException);
			}

			return null;
		}
	}

	private List<LayoutSEOEntryCustomMetaTagProperty>
		_getLayoutSEOEntryCustomMetaTagProperties(
			Element layoutSEOEntryElement) {

		List<LayoutSEOEntryCustomMetaTagProperty>
			layoutSEOEntryCustomMetaTagProperties = new ArrayList<>();

		for (Element customMetaTagElement :
				layoutSEOEntryElement.elements("custom-meta-tag")) {

			String property = customMetaTagElement.attributeValue("property");

			if (Validator.isNull(property)) {
				continue;
			}

			Map<Locale, String> contentMap = new HashMap<>();

			for (Element contentElement : customMetaTagElement.elements()) {
				contentMap.put(
					LocaleUtil.fromLanguageId(
						contentElement.attributeValue("language-id")),
					contentElement.getText());
			}

			if (MapUtil.isNotEmpty(contentMap)) {
				layoutSEOEntryCustomMetaTagProperties.add(
					new LayoutSEOEntryCustomMetaTagProperty(
						property, contentMap));
			}
		}

		return layoutSEOEntryCustomMetaTagProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOEntryStagedModelDataHandler.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}