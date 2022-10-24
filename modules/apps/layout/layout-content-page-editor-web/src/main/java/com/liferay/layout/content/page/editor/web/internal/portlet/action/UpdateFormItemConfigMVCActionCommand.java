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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_form_item_config"
	},
	service = MVCActionCommand.class
)
public class UpdateFormItemConfigMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_updateFormStyledLayoutStructureItemConfig(
				actionRequest, actionResponse));
	}

	private FragmentEntryLink _addFragmentEntryLink(
			String formItemId, FragmentEntry fragmentEntry,
			InfoField<?> infoField, LayoutStructure layoutStructure,
			long segmentsExperienceId, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				themeDisplay.getScopeGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				themeDisplay.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				serviceContext);

		if (infoField != null) {
			JSONObject editableValuesJSONObject =
				_jsonFactory.createJSONObject();

			if (Validator.isNotNull(fragmentEntryLink.getEditableValues())) {
				editableValuesJSONObject = _jsonFactory.createJSONObject(
					fragmentEntryLink.getEditableValues());
			}

			JSONObject jsonObject = editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if (jsonObject == null) {
				jsonObject = _jsonFactory.createJSONObject();

				editableValuesJSONObject.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					jsonObject);
			}

			jsonObject.put("inputFieldId", infoField.getUniqueId());

			fragmentEntryLink =
				_fragmentEntryLinkService.updateFragmentEntryLink(
					fragmentEntryLink.getFragmentEntryLinkId(),
					editableValuesJSONObject.toString());
		}

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), formItemId, -1);

		return fragmentEntryLink;
	}

	private List<FragmentEntryLink> _addFragmentEntryLinks(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			HttpServletRequest httpServletRequest, JSONObject jsonObject,
			LayoutStructure layoutStructure, long segmentsExperienceId,
			ThemeDisplay themeDisplay)
		throws Exception {

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributor("INPUTS");

		if (fragmentCollectionContributor == null) {
			jsonObject.put(
				"errorMessage",
				_language.get(
					themeDisplay.getLocale(),
					"your-form-could-not-be-loaded-because-fragments-are-not-" +
						"available"));

			return Collections.emptyList();
		}

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem =
			_getMasterDropZoneLayoutStructureItem(themeDisplay.getLayout());
		TreeSet<String> missingInputTypes = new TreeSet<>();
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		for (InfoField<?> infoField :
				_getInfoFields(
					formStyledLayoutStructureItem,
					themeDisplay.getScopeGroupId())) {

			if (!infoField.isEditable()) {
				continue;
			}

			InfoFieldType infoFieldType = infoField.getInfoFieldType();

			FragmentEntry fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					_getFragmentEntryKey(infoFieldType));

			if ((fragmentEntry == null) ||
				!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey(),
					masterDropZoneLayoutStructureItem)) {

				missingInputTypes.add(
					infoFieldType.getLabel(themeDisplay.getLocale()));

				continue;
			}

			addedFragmentEntryLinks.add(
				_addFragmentEntryLink(
					formStyledLayoutStructureItem.getItemId(), fragmentEntry,
					infoField, layoutStructure, segmentsExperienceId,
					serviceContext, themeDisplay));
		}

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorTracker.getFragmentEntry(
				"INPUTS-submit-button");

		if ((fragmentEntry == null) ||
			!_isAllowedFragmentEntryKey(
				fragmentEntry.getFragmentEntryKey(),
				masterDropZoneLayoutStructureItem)) {

			missingInputTypes.add(
				_language.get(themeDisplay.getLocale(), "submit-button"));
		}
		else {
			addedFragmentEntryLinks.add(
				_addFragmentEntryLink(
					formStyledLayoutStructureItem.getItemId(), fragmentEntry,
					null, layoutStructure, segmentsExperienceId, serviceContext,
					themeDisplay));
		}

		if (missingInputTypes.size() == 1) {
			jsonObject.put(
				"errorMessage",
				_language.format(
					themeDisplay.getLocale(),
					"some-fragments-are-missing.-x-fields-cannot-have-an-" +
						"associated-fragment-or-cannot-be-available-in-master",
					missingInputTypes.first()));
		}
		else if (missingInputTypes.size() > 1) {
			jsonObject.put(
				"errorMessage",
				_language.format(
					themeDisplay.getLocale(),
					"some-fragments-are-missing.-x-and-x-fields-cannot-have-" +
						"an-associated-fragment-or-cannot-be-available-in-" +
							"master",
					new String[] {
						StringUtil.merge(
							missingInputTypes.headSet(missingInputTypes.last()),
							StringPool.COMMA_AND_SPACE),
						missingInputTypes.last()
					}));
		}

		return addedFragmentEntryLinks;
	}

	private String _getFragmentEntryKey(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof BooleanInfoFieldType) {
			return "INPUTS-checkbox";
		}

		if (infoFieldType instanceof DateInfoFieldType) {
			return "INPUTS-date-input";
		}

		if (infoFieldType instanceof FileInfoFieldType) {
			return "INPUTS-file-upload";
		}

		if (infoFieldType instanceof NumberInfoFieldType) {
			return "INPUTS-numeric-input";
		}

		if (infoFieldType instanceof RelationshipInfoFieldType ||
			infoFieldType instanceof SelectInfoFieldType) {

			return "INPUTS-select-from-list";
		}

		if (infoFieldType instanceof TextInfoFieldType) {
			return "INPUTS-text-input";
		}

		return null;
	}

	private List<InfoField<?>> _getInfoFields(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			long groupId)
		throws Exception {

		String itemClassName = _infoSearchClassMapperTracker.getClassName(
			_portal.getClassName(
				formStyledLayoutStructureItem.getClassNameId()));

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, itemClassName);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						itemClassName);
			}

			return Collections.emptyList();
		}

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			String.valueOf(formStyledLayoutStructureItem.getClassTypeId()),
			groupId);

		return infoForm.getAllInfoFields();
	}

	private DropZoneLayoutStructureItem _getMasterDropZoneLayoutStructureItem(
		Layout layout) {

		if (layout.getMasterLayoutPlid() <= 0) {
			return null;
		}

		try {
			LayoutStructure masterLayoutStructure =
				LayoutStructureUtil.getLayoutStructure(
					layout.getGroupId(), layout.getMasterLayoutPlid(),
					SegmentsExperienceConstants.KEY_DEFAULT);

			return (DropZoneLayoutStructureItem)
				masterLayoutStructure.getDropZoneLayoutStructureItem();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get master layout structure", exception);
			}
		}

		return null;
	}

	private boolean _isAllowedFragmentEntryKey(
		String fragmentEntryKey,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem) {

		if (masterDropZoneLayoutStructureItem == null) {
			return true;
		}

		List<String> fragmentEntryKeys =
			masterDropZoneLayoutStructureItem.getFragmentEntryKeys();

		if (masterDropZoneLayoutStructureItem.isAllowNewFragmentEntries()) {
			if (ListUtil.isEmpty(fragmentEntryKeys) ||
				!fragmentEntryKeys.contains(fragmentEntryKey)) {

				return true;
			}

			return false;
		}

		if (ListUtil.isNotEmpty(fragmentEntryKeys) &&
			fragmentEntryKeys.contains(fragmentEntryKey)) {

			return true;
		}

		return false;
	}

	private JSONArray _removeLayoutStructureItemsJSONArray(
		LayoutStructure layoutStructure,
		FormStyledLayoutStructureItem formStyledLayoutStructureItem) {

		JSONArray fragmentEntryLinkIdsJSONArray =
			_jsonFactory.createJSONArray();

		for (String itemId :
				ListUtil.copy(
					formStyledLayoutStructureItem.getChildrenItemIds())) {

			layoutStructure.markLayoutStructureItemForDeletion(
				itemId, Collections.emptyList());

			LayoutStructureItem removedLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(itemId);

			if (!(removedLayoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)
						removedLayoutStructureItem;

			fragmentEntryLinkIdsJSONArray.put(
				String.valueOf(
					fragmentStyledLayoutStructureItem.
						getFragmentEntryLinkId()));
		}

		return fragmentEntryLinkIdsJSONArray;
	}

	private JSONObject _updateFormStyledLayoutStructureItemConfig(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String itemConfig = ParamUtil.getString(actionRequest, "itemConfig");
		String formItemId = ParamUtil.getString(actionRequest, "itemId");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						themeDisplay.getScopeGroupId(), themeDisplay.getPlid());

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(segmentsExperienceId));

			FormStyledLayoutStructureItem
				previousFormStyledLayoutStructureItem =
					(FormStyledLayoutStructureItem)
						layoutStructure.getLayoutStructureItem(formItemId);

			long previousClassNameId =
				previousFormStyledLayoutStructureItem.getClassNameId();
			long previousClassTypeId =
				previousFormStyledLayoutStructureItem.getClassTypeId();

			FormStyledLayoutStructureItem formStyledLayoutStructureItem =
				(FormStyledLayoutStructureItem)layoutStructure.updateItemConfig(
					_jsonFactory.createJSONObject(itemConfig), formItemId);

			JSONArray removedLayoutStructureItemsJSONArray =
				_jsonFactory.createJSONArray();

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

			if (!Objects.equals(
					formStyledLayoutStructureItem.getClassNameId(),
					previousClassNameId) ||
				!Objects.equals(
					formStyledLayoutStructureItem.getClassTypeId(),
					previousClassTypeId)) {

				removedLayoutStructureItemsJSONArray =
					_removeLayoutStructureItemsJSONArray(
						layoutStructure, formStyledLayoutStructureItem);

				if (formStyledLayoutStructureItem.getClassNameId() > 0) {
					addedFragmentEntryLinks = _addFragmentEntryLinks(
						formStyledLayoutStructureItem, httpServletRequest,
						jsonObject, layoutStructure, segmentsExperienceId,
						themeDisplay);
				}
			}

			layoutPageTemplateStructure =
				_layoutPageTemplateStructureService.
					updateLayoutPageTemplateStructureData(
						themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
						segmentsExperienceId, layoutStructure.toString());

			List<FragmentEntryLinkListener> fragmentEntryLinkListeners =
				_fragmentEntryLinkListenerTracker.
					getFragmentEntryLinkListeners();

			for (FragmentEntryLink addedFragmentEntryLink :
					addedFragmentEntryLinks) {

				for (FragmentEntryLinkListener fragmentEntryLinkListener :
						fragmentEntryLinkListeners) {

					fragmentEntryLinkListener.onAddFragmentEntryLink(
						addedFragmentEntryLink);
				}
			}

			JSONObject addedFragmentEntryLinksJSONObject =
				_jsonFactory.createJSONObject();

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			LayoutStructure updatedLayoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(segmentsExperienceId));

			for (FragmentEntryLink addedFragmentEntryLink :
					addedFragmentEntryLinks) {

				addedFragmentEntryLinksJSONObject.put(
					String.valueOf(
						addedFragmentEntryLink.getFragmentEntryLinkId()),
					_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
						addedFragmentEntryLink, httpServletRequest,
						httpServletResponse, updatedLayoutStructure));
			}

			jsonObject.put(
				"addedFragmentEntryLinks", addedFragmentEntryLinksJSONObject
			).put(
				"layoutData", updatedLayoutStructure.toJSONObject()
			).put(
				"removedFragmentEntryLinkIds",
				removedLayoutStructureItemsJSONArray
			);
		}
		catch (Exception exception) {
			_log.error(exception);

			jsonObject.put(
				"error",
				_language.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateFormItemConfigMVCActionCommand.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkListenerTracker _fragmentEntryLinkListenerTracker;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

}