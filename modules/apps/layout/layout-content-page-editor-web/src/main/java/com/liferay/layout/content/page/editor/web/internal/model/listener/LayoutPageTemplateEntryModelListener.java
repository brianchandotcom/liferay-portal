/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.UpdateLayoutStatusThreadLocal;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateEntryModelListener
	extends BaseModelListener<LayoutPageTemplateEntry> {

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateEntry originalLayoutPageTemplateEntry,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws ModelListenerException {

		if (FeatureFlagManagerUtil.isEnabled("LPS-195263") &&
			Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE) &&
			(originalLayoutPageTemplateEntry.getClassNameId() != 0) &&
			(!Objects.equals(
				originalLayoutPageTemplateEntry.getClassNameId(),
				layoutPageTemplateEntry.getClassNameId()) ||
			 !Objects.equals(
				 originalLayoutPageTemplateEntry.getClassTypeId(),
				 layoutPageTemplateEntry.getClassTypeId()))) {

			try {
				_removeContextReferences(layoutPageTemplateEntry);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}
	}

	private void _removeContextReferences(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		for (SegmentsExperience segmentsExperience :
				_segmentsExperienceLocalService.getSegmentsExperiences(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getPlid())) {

			_updateLayoutPageTemplateStructureData(
				layout, segmentsExperience.getSegmentsExperienceId());

			if (draftLayout != null) {
				_updateLayoutPageTemplateStructureData(
					draftLayout, segmentsExperience.getSegmentsExperienceId());
			}
		}

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					layout.getGroupId(), layout.getPlid())) {

			_updateFragmentEntryLinkEditableValues(fragmentEntryLink);
		}

		if (draftLayout != null) {
			for (FragmentEntryLink fragmentEntryLink :
					_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
						draftLayout.getGroupId(), draftLayout.getPlid())) {

				_updateFragmentEntryLinkEditableValues(fragmentEntryLink);
			}
		}
	}

	private JSONObject _removeMappedFields(JSONObject jsonObject) {
		jsonObject.remove("mappedField");

		for (String key : jsonObject.keySet()) {
			Object object = jsonObject.get(key);

			if (object instanceof JSONObject) {
				_removeMappedFields((JSONObject)object);
			}
		}

		return jsonObject;
	}

	private void _updateFragmentEntryLinkEditableValues(
		FragmentEntryLink fragmentEntryLink) {

		if (fragmentEntryLink.getType() == FragmentConstants.TYPE_INPUT) {
			return;
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

			for (String fragmentEntryProcessorKey :
					_FRAGMENT_ENTRY_PROCESSOR_KEYS) {

				JSONObject editableFragmentEntryProcessorJSONObject =
					editableValuesJSONObject.getJSONObject(
						fragmentEntryProcessorKey);

				if (editableFragmentEntryProcessorJSONObject == null) {
					continue;
				}

				_removeMappedFields(editableFragmentEntryProcessorJSONObject);
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		if (editableValuesJSONObject == null) {
			return;
		}

		try (SafeCloseable safeCloseable =
				UpdateLayoutStatusThreadLocal.setWithSafeCloseable(false)) {

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	private void _updateLayoutPageTemplateStructureData(
		Layout layout, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		if (layoutPageTemplateStructure == null) {
			return;
		}

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			layoutStructure.updateItemConfig(
				_removeMappedFields(
					layoutStructureItem.getItemConfigJSONObject()),
				layoutStructureItem.getItemId());
		}

		try (SafeCloseable safeCloseable =
				UpdateLayoutStatusThreadLocal.setWithSafeCloseable(false)) {

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					layout.getGroupId(), layout.getPlid(), segmentsExperienceId,
					layoutStructure.toString());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	private static final String[] _FRAGMENT_ENTRY_PROCESSOR_KEYS = {
		FragmentEntryProcessorConstants.KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryModelListener.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}