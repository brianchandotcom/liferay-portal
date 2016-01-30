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

package com.liferay.dynamic.data.mapping.exportimport.content.processor;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portlet.documentlibrary.exception.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	property = {"model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues"},
	service = {
		DDMFormValuesExportImportContentProcessor.class,
		ExportImportContentProcessor.class
	}
)
public class DDMFormValuesExportImportContentProcessor
	implements ExportImportContentProcessor<DDMFormValues> {

	public DDMFormValues replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			DDMFormValues ddmFormValues, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new FileEntryExportDDMFormFieldValueTransformer(
				exportReferencedContent, portletDataContext, stagedModel));
		ddmFormValuesTransformer.addTransformer(
			new LayoutExportDDMFormFieldValueTransformer(
				portletDataContext, stagedModel));

		ddmFormValuesTransformer.transform();

		return ddmFormValues;
	}

	public DDMFormValues replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new FileEntryImportDDMFormFieldValueTransformer(
				portletDataContext));
		ddmFormValuesTransformer.addTransformer(
			new LayoutImportDDMFormFieldValueTransformer(portletDataContext));

		ddmFormValuesTransformer.transform();

		return ddmFormValues;
	}

	@Override
	public void validateContentReferences(
			long groupId, DDMFormValues ddmFormValues)
		throws PortalException {
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesExportImportContentProcessor.class);

	private DLAppService _dlAppService;
	private LayoutLocalService _layoutLocalService;

	private static class LayoutImportDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public LayoutImportDDMFormFieldValueTransformer(
			PortletDataContext portletDataContext) {

			_portletDataContext = portletDataContext;
		}

		@Override
		public String getFieldType() {
			return DDMFormFieldType.LINK_TO_PAGE;
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				Layout importedLayout = fetchImportedLayout(
					_portletDataContext, jsonObject);

				if (importedLayout == null) {
					continue;
				}

				value.addString(locale, toJSON(importedLayout));
			}
		}

		protected Layout fetchImportedLayout(
			PortletDataContext portletDataContext, JSONObject jsonObject) {

			Map<Long, Layout> layouts =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			long layoutId = jsonObject.getLong("layoutId");

			Layout layout = layouts.get(layoutId);

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find layout with ID " + layoutId);
				}
			}

			return layout;
		}

		protected String toJSON(Layout layout) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", layout.getGroupId());
			jsonObject.put("layoutId", layout.getLayoutId());
			jsonObject.put("privateLayout", layout.isPrivateLayout());

			return jsonObject.toString();
		}

		private final PortletDataContext _portletDataContext;

	}

	private class FileEntryExportDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public FileEntryExportDDMFormFieldValueTransformer(
			boolean exportReferencedContent,
			PortletDataContext portletDataContext, StagedModel stagedModel) {

			_exportReferencedContent = exportReferencedContent;
			_portletDataContext = portletDataContext;
			_stagedModel = stagedModel;
		}

		@Override
		public String getFieldType() {
			return DDMFormFieldType.DOCUMENT_LIBRARY;
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				long groupId = GetterUtil.getLong(jsonObject.get("groupId"));
				String uuid = jsonObject.getString("uuid");

				FileEntry fileEntry =
					_dlAppService.getFileEntryByUuidAndGroupId(uuid, groupId);

				if (_exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						_portletDataContext, _stagedModel, fileEntry,
						_portletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					Element entityElement =
						_portletDataContext.getExportDataElement(_stagedModel);

					_portletDataContext.addReferenceElement(
						_stagedModel, entityElement, fileEntry,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}
			}
		}

		private final boolean _exportReferencedContent;
		private final PortletDataContext _portletDataContext;
		private final StagedModel _stagedModel;

	}

	private class FileEntryImportDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public FileEntryImportDDMFormFieldValueTransformer(
			PortletDataContext portletDataContext) {

			_portletDataContext = portletDataContext;
		}

		@Override
		public String getFieldType() {
			return DDMFormFieldType.DOCUMENT_LIBRARY;
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				String type = jsonObject.getString("type");

				FileEntry importedFileEntry = fetchImportedFileEntry(
					_portletDataContext, jsonObject);

				if (importedFileEntry == null) {
					continue;
				}

				value.addString(locale, toJSON(importedFileEntry, type));
			}
		}

		protected FileEntry fetchImportedFileEntry(
				PortletDataContext portletDataContext, JSONObject jsonObject)
			throws PortalException {

			Map<Long, Long> groupIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Group.class);

			long groupId = jsonObject.getLong("groupId");
			String uuid = jsonObject.getString("uuid");

			groupId = MapUtil.getLong(groupIds, groupId, groupId);

			try {
				return _dlAppService.getFileEntryByUuidAndGroupId(
					uuid, groupId);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find file entry with uuid " + uuid +
							" and groupId " + groupId);
				}

				return null;
			}
		}

		protected String toJSON(FileEntry fileEntry, String type) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("title", fileEntry.getTitle());
			jsonObject.put("type", type);
			jsonObject.put("uuid", fileEntry.getUuid());

			return jsonObject.toString();
		}

		private final PortletDataContext _portletDataContext;

	}

	private class LayoutExportDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public LayoutExportDDMFormFieldValueTransformer(
			PortletDataContext portletDataContext, StagedModel stagedModel) {

			_portletDataContext = portletDataContext;
			_stagedModel = stagedModel;
		}

		@Override
		public String getFieldType() {
			return DDMFormFieldType.LINK_TO_PAGE;
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				long groupId = GetterUtil.getLong(jsonObject.get("groupId"));
				long layoutId = GetterUtil.getLong(
					jsonObject.getLong("layoutId"));
				boolean privateLayout = jsonObject.getBoolean("privateLayout");

				Layout layout = _layoutLocalService.getLayout(
					groupId, privateLayout, layoutId);

				Element entityElement =
					_portletDataContext.getExportDataElement(_stagedModel);

				_portletDataContext.addReferenceElement(
					_stagedModel, entityElement, layout,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}

		private final PortletDataContext _portletDataContext;
		private final StagedModel _stagedModel;

	}

}