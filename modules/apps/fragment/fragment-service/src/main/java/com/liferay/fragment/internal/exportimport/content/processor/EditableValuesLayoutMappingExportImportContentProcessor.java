/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Iterator;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "content.processor.type=FragmentEntryLinkEditableValues",
	service = ExportImportContentProcessor.class
)
public class EditableValuesLayoutMappingExportImportContentProcessor
	implements ExportImportContentProcessor<JSONObject> {

	@Override
	public JSONObject replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		Iterator<String> editableKeysIterator =
			editableProcessorJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			JSONObject editableJSONObject =
				editableProcessorJSONObject.getJSONObject(editableKey);

			JSONObject configJSONObject = editableJSONObject.getJSONObject(
				"config");

			if ((configJSONObject != null) && configJSONObject.has("layout")) {
				_exportLayoutReferences(
					portletDataContext, stagedModel,
					configJSONObject.getJSONObject("layout"),
					exportReferencedContent);
			}
		}

		return editableValuesJSONObject;
	}

	@Override
	public JSONObject replaceImportContentReferences(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		JSONObject editableValuesJSONObject) {

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		Iterator<String> editableKeysIterator =
			editableProcessorJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			JSONObject editableJSONObject =
				editableProcessorJSONObject.getJSONObject(editableKey);

			JSONObject configJSONObject = editableJSONObject.getJSONObject(
				"config");

			if ((configJSONObject != null) && configJSONObject.has("layout")) {
				_replaceImportLayoutReferences(
					configJSONObject.getJSONObject("layout"),
					portletDataContext);
			}
		}

		return editableValuesJSONObject;
	}

	@Override
	public void validateContentReferences(long groupId, JSONObject jsonObject) {
	}

	private void _exportLayoutReferences(
			PortletDataContext portletDataContext,
			StagedModel referrerStagedModel, JSONObject layoutJSONObject,
			boolean exportReferencedContent)
		throws Exception {

		if (layoutJSONObject.length() == 0) {
			return;
		}

		Layout layout = _layoutLocalService.fetchLayout(
			layoutJSONObject.getLong("groupId"),
			layoutJSONObject.getBoolean("privateLayout"),
			layoutJSONObject.getLong("layoutId"));

		if (layout == null) {
			return;
		}

		if ((layout.isPrivateLayout() !=
				portletDataContext.isPrivateLayout()) &&
			!layout.isTypeAssetDisplay() && !_isSkipExportLayout(layout)) {

			return;
		}

		layoutJSONObject.put("plid", layout.getPlid());

		if (exportReferencedContent) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, referrerStagedModel, layout,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
		else {
			Element entityElement = portletDataContext.getExportDataElement(
				referrerStagedModel);

			portletDataContext.addReferenceElement(
				referrerStagedModel, entityElement, layout,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	private boolean _isSkipExportLayout(Layout layout) {
		if (!layout.isTypeContent()) {
			return false;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(layout.getClassPK());
		}

		if (layoutPageTemplateEntry == null) {
			return false;
		}

		if ((layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) ||
			(layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			return true;
		}

		return false;
	}

	private void _replaceImportLayoutReferences(
		JSONObject layoutJSONObject, PortletDataContext portletDataContext) {

		if (layoutJSONObject.length() == 0) {
			return;
		}

		long plid = GetterUtil.getLong(layoutJSONObject.remove("plid"));

		Map<Long, Long> layoutNewPrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class.getName());

		Layout layout = _layoutLocalService.fetchLayout(
			layoutNewPrimaryKeys.getOrDefault(plid, 0L));

		if (layout == null) {
			return;
		}

		layoutJSONObject.put(
			"groupId", layout.getGroupId()
		).put(
			"layoutId", layout.getLayoutId()
		).put(
			"layoutUuid", layout.getUuid()
		).put(
			"privateLayout", layout.isPrivateLayout()
		);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}