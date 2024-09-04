/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.manager;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = FormItemManager.class)
public class FormItemManager {

	public List<FragmentEntryLink> addFormStepLayoutStructureItems(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			Layout layout, LayoutStructure layoutStructure, Locale locale,
			int numberOfSteps, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutStructureItem formStepContainerStyledLayoutStructureItem =
			_findFormStepContainerStyledLayoutStructureItem(
				formStyledLayoutStructureItem, layoutStructure);

		if (formStepContainerStyledLayoutStructureItem == null) {
			return Collections.emptyList();
		}

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		List<String> childrenItemIds =
			formStepContainerStyledLayoutStructureItem.getChildrenItemIds();

		int initialStepIndex = childrenItemIds.size() - 1;

		for (int i = initialStepIndex; i < numberOfSteps; i++) {
			LayoutStructureItem formStepLayoutStructureItem = null;

			if (i == initialStepIndex) {
				formStepLayoutStructureItem =
					layoutStructure.getLayoutStructureItem(
						childrenItemIds.get(initialStepIndex));
			}
			else {
				formStepLayoutStructureItem =
					layoutStructure.addFormStepLayoutStructureItem(
						formStepContainerStyledLayoutStructureItem.getItemId(),
						-1);
			}

			addedFragmentEntryLinks.addAll(
				_addFormButtons(
					formStepLayoutStructureItem, formStyledLayoutStructureItem,
					layout, locale, layoutStructure, numberOfSteps - 1,
					segmentsExperienceId, i, serviceContext));
		}

		return addedFragmentEntryLinks;
	}

	public List<FragmentEntryLink> addFragmentEntryLinks(
			JSONObject errorJSONObject,
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			boolean includeSubmitButton, Layout layout,
			LayoutStructure layoutStructure, Locale locale,
			long segmentsExperienceId, ServiceContext serviceContext,
			String[] uniqueInfoFieldIds)
		throws PortalException {

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorRegistry.
				getFragmentCollectionContributor("INPUTS");

		if (fragmentCollectionContributor == null) {
			errorJSONObject.put(
				"errorMessage",
				_language.get(
					locale,
					"your-form-could-not-be-loaded-because-fragments-are-not-" +
						"available"));

			return Collections.emptyList();
		}

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem =
			_getMasterDropZoneLayoutStructureItem(layout);
		TreeSet<String> missingInputTypes = new TreeSet<>();

		JSONObject defaultInputFragmentEntryKeysJSONObject =
			_defaultInputFragmentEntryConfigurationProvider.
				getDefaultInputFragmentEntryKeysJSONObject(layout.getGroupId());

		for (InfoField<?> infoField :
				_getInfoFields(
					formStyledLayoutStructureItem, layout.getGroupId())) {

			if (!infoField.isEditable() ||
				(ArrayUtil.isNotEmpty(uniqueInfoFieldIds) &&
				 !ArrayUtil.contains(
					 uniqueInfoFieldIds, infoField.getUniqueId()))) {

				continue;
			}

			InfoFieldType infoFieldType = infoField.getInfoFieldType();

			FragmentEntry fragmentEntry = _getFragmentEntry(
				layout.getCompanyId(), defaultInputFragmentEntryKeysJSONObject,
				infoFieldType.getName());

			if ((fragmentEntry == null) ||
				!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey(),
					masterDropZoneLayoutStructureItem)) {

				missingInputTypes.add(infoFieldType.getLabel(locale));

				continue;
			}

			addedFragmentEntryLinks.add(
				_addFragmentEntryLink(
					formStyledLayoutStructureItem.getItemId(), fragmentEntry,
					infoField, layout, layoutStructure, segmentsExperienceId,
					serviceContext));
		}

		if (includeSubmitButton) {
			FragmentEntry fragmentEntry = _getFragmentEntry(
				layout.getCompanyId(), defaultInputFragmentEntryKeysJSONObject,
				DefaultInputFragmentEntryConfigurationProvider.
					FORM_INPUT_SUBMIT_BUTTON);

			if ((fragmentEntry == null) ||
				!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey(),
					masterDropZoneLayoutStructureItem)) {

				missingInputTypes.add(_language.get(locale, "submit-button"));
			}
			else {
				addedFragmentEntryLinks.add(
					_addFragmentEntryLink(
						formStyledLayoutStructureItem.getItemId(),
						fragmentEntry, null, layout, layoutStructure,
						segmentsExperienceId, serviceContext));
			}
		}

		if (missingInputTypes.size() == 1) {
			errorJSONObject.put(
				"errorMessage",
				_language.format(
					locale,
					"some-fragments-are-missing.-x-fields-cannot-have-an-" +
						"associated-fragment-or-cannot-be-available-in-master",
					missingInputTypes.first()));
		}
		else if (missingInputTypes.size() > 1) {
			errorJSONObject.put(
				"errorMessage",
				_language.format(
					locale,
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

	public List<FragmentEntryLink> changeToMultiStepFormType(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			Layout layout, LayoutStructure layoutStructure, Locale locale,
			int numberOfSteps, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		List<String> childrenItemIds = new ArrayList<>(
			formStyledLayoutStructureItem.getChildrenItemIds());

		LayoutStructureItem formStepContainerStyledLayoutStructureItem =
			layoutStructure.addFormStepContainerStyledLayoutStructureItem(
				formStyledLayoutStructureItem.getItemId(), -1);

		LayoutStructureItem formStepLayoutStructureItem =
			layoutStructure.addFormStepLayoutStructureItem(
				formStepContainerStyledLayoutStructureItem.getItemId(), 0);

		for (String childrenItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			if (layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)layoutStructureItem;

				Set<String> fieldTypes =
					_fragmentEntryLinkManager.getFragmentEntryLinkFieldTypes(
						fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkId(),
						locale);

				if (fieldTypes.contains("stepper")) {
					continue;
				}

				if (fieldTypes.contains("formButton")) {
					layoutStructure.markLayoutStructureItemForDeletion(
						Collections.singletonList(childrenItemId),
						Collections.emptyList());

					continue;
				}
			}

			layoutStructure.moveLayoutStructureItem(
				childrenItemId, formStepLayoutStructureItem.getItemId(), -1);
		}

		addedFragmentEntryLinks.addAll(
			_addFormButtons(
				formStepLayoutStructureItem, formStyledLayoutStructureItem,
				layout, locale, layoutStructure, numberOfSteps,
				segmentsExperienceId, 0, serviceContext));

		for (int i = 1; i < numberOfSteps; i++) {
			formStepLayoutStructureItem =
				layoutStructure.addFormStepLayoutStructureItem(
					formStepContainerStyledLayoutStructureItem.getItemId(), i);

			addedFragmentEntryLinks.addAll(
				_addFormButtons(
					formStepLayoutStructureItem, formStyledLayoutStructureItem,
					layout, locale, layoutStructure, numberOfSteps,
					segmentsExperienceId, 1, serviceContext));
		}

		return addedFragmentEntryLinks;
	}

	public List<FragmentEntryLink> changeToSimpleFormType(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			Layout layout, LayoutStructure layoutStructure, Locale locale,
			long segmentsExperienceId, ServiceContext serviceContext)
		throws PortalException {

		LayoutStructureItem formStepContainerStyledLayoutStructureItem =
			_findFormStepContainerStyledLayoutStructureItem(
				formStyledLayoutStructureItem, layoutStructure);

		if (formStepContainerStyledLayoutStructureItem == null) {
			return Collections.emptyList();
		}

		for (String childrenItemId :
				new ArrayList<>(
					formStepContainerStyledLayoutStructureItem.
						getChildrenItemIds())) {

			LayoutStructureItem formStepLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			for (String formStepLayoutStructureItemChildrenItemId :
					new ArrayList<>(
						formStepLayoutStructureItem.getChildrenItemIds())) {

				if (!_isFormButtonsContainer(
						formStepLayoutStructureItem,
						formStyledLayoutStructureItem,
						layoutStructure.getLayoutStructureItem(
							formStepLayoutStructureItemChildrenItemId))) {

					layoutStructure.moveLayoutStructureItem(
						formStepLayoutStructureItemChildrenItemId,
						formStyledLayoutStructureItem.getItemId(), -1);
				}
			}
		}

		layoutStructure.markLayoutStructureItemForDeletion(
			Collections.singletonList(
				formStepContainerStyledLayoutStructureItem.getItemId()),
			Collections.emptyList());

		FragmentEntryLink fragmentEntryLink = _addFormButtonFragmentEntryLink(
			layout, locale, _SUBMIT, segmentsExperienceId, serviceContext);

		if (fragmentEntryLink == null) {
			return Collections.emptyList();
		}

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			formStyledLayoutStructureItem.getItemId(), -1);

		return Collections.singletonList(fragmentEntryLink);
	}

	public List<FragmentEntryLink> removeFormStepLayoutStructureItems(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			Layout layout, LayoutStructure layoutStructure, Locale locale,
			int numberOfSteps, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutStructureItem formStepContainerStyledLayoutStructureItem =
			_findFormStepContainerStyledLayoutStructureItem(
				formStyledLayoutStructureItem, layoutStructure);

		if (formStepContainerStyledLayoutStructureItem == null) {
			return Collections.emptyList();
		}

		List<String> childrenItemIds = new ArrayList<>(
			formStepContainerStyledLayoutStructureItem.getChildrenItemIds());

		LayoutStructureItem previousFormStepLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				childrenItemIds.get(numberOfSteps - 1));

		for (int i = numberOfSteps; i < childrenItemIds.size(); i++) {
			LayoutStructureItem formStepLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemIds.get(i));

			for (String childrenItemId :
					new ArrayList<>(
						formStepLayoutStructureItem.getChildrenItemIds())) {

				if (!_isFormButtonsContainer(
						formStepLayoutStructureItem,
						formStyledLayoutStructureItem,
						layoutStructure.getLayoutStructureItem(
							childrenItemId))) {

					layoutStructure.moveLayoutStructureItem(
						childrenItemId,
						previousFormStepLayoutStructureItem.getItemId(), -1);
				}
			}

			layoutStructure.markLayoutStructureItemForDeletion(
				Collections.singletonList(
					formStepLayoutStructureItem.getItemId()),
				Collections.emptyList());
		}

		return _addFormButtons(
			previousFormStepLayoutStructureItem, formStyledLayoutStructureItem,
			layout, locale, layoutStructure, numberOfSteps,
			segmentsExperienceId, numberOfSteps - 1, serviceContext);
	}

	public JSONArray removeLayoutStructureItemsJSONArray(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem,
		LayoutStructure layoutStructure, List<String> removedItemIds) {

		JSONArray fragmentEntryLinkIdsJSONArray =
			_jsonFactory.createJSONArray();

		for (String itemId :
				LayoutStructureItemUtil.getChildrenItemIds(
					formStyledLayoutStructureItem.getItemId(),
					layoutStructure)) {

			if (ListUtil.isNotEmpty(removedItemIds) &&
				!removedItemIds.contains(itemId)) {

				continue;
			}

			layoutStructure.markLayoutStructureItemForDeletion(
				Collections.singletonList(itemId), Collections.emptyList());

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

	private FragmentEntryLink _addFormButtonFragmentEntryLink(
			Layout layout, Locale locale, String type,
			long segmentsExperienceId, ServiceContext serviceContext)
		throws PortalException {

		FragmentEntry fragmentEntry = _getFragmentEntry(
			layout.getCompanyId(),
			_defaultInputFragmentEntryConfigurationProvider.
				getDefaultInputFragmentEntryKeysJSONObject(layout.getGroupId()),
			DefaultInputFragmentEntryConfigurationProvider.
				FORM_INPUT_SUBMIT_BUTTON);

		if (fragmentEntry == null) {
			return null;
		}

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, layout.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				layout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				serviceContext);

		JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject freemarkerJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (freemarkerJSONObject == null) {
			freemarkerJSONObject = _jsonFactory.createJSONObject();

			editableValuesJSONObject.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				freemarkerJSONObject);
		}

		freemarkerJSONObject.put("type", type);

		JSONObject editableJSONObject = editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (editableJSONObject == null) {
			editableJSONObject = _jsonFactory.createJSONObject();

			editableValuesJSONObject.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				editableJSONObject);
		}

		editableJSONObject.put(
			type + "-button-text",
			JSONUtil.put("defaultValue", _language.get(locale, type)));

		return _fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(),
			editableValuesJSONObject.toString());
	}

	private List<FragmentEntryLink> _addFormButtons(
			LayoutStructureItem formStepLayoutStructureItem,
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			Layout layout, Locale locale, LayoutStructure layoutStructure,
			int numberOfSteps, long segmentsExperienceId, int stepIndex,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutStructureItem layoutStructureItem = _findFormButtonsContainer(
			formStepLayoutStructureItem, formStyledLayoutStructureItem,
			layoutStructure);

		if (layoutStructureItem != null) {
			layoutStructure.markLayoutStructureItemForDeletion(
				Collections.singletonList(layoutStructureItem.getItemId()),
				Collections.emptyList());
		}

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerStyledLayoutStructureItem(
					_getFormButtonsContainerId(
						formStepLayoutStructureItem,
						formStyledLayoutStructureItem),
					formStepLayoutStructureItem.getItemId(), -1);

		containerStyledLayoutStructureItem.setContentDisplay("flex-row");

		if (stepIndex == 0) {
			FragmentEntryLink nextFormButtonFragmentEntryLink =
				_addFormButtonFragmentEntryLink(
					layout, locale, _NEXT, segmentsExperienceId,
					serviceContext);

			if (nextFormButtonFragmentEntryLink == null) {
				return Collections.emptyList();
			}

			layoutStructure.addFragmentStyledLayoutStructureItem(
				nextFormButtonFragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), -1);

			return Collections.singletonList(nextFormButtonFragmentEntryLink);
		}

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>();

		FragmentEntryLink previousFormButtonFragmentEntryLink =
			_addFormButtonFragmentEntryLink(
				layout, locale, _PREVIOUS, segmentsExperienceId,
				serviceContext);

		if (previousFormButtonFragmentEntryLink != null) {
			fragmentEntryLinks.add(previousFormButtonFragmentEntryLink);

			layoutStructure.addFragmentStyledLayoutStructureItem(
				previousFormButtonFragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), -1);
		}

		String type = _SUBMIT;

		if (stepIndex < numberOfSteps) {
			type = _NEXT;
		}

		FragmentEntryLink submitFormButtonFragmentEntryLink =
			_addFormButtonFragmentEntryLink(
				layout, locale, type, segmentsExperienceId, serviceContext);

		if (submitFormButtonFragmentEntryLink != null) {
			fragmentEntryLinks.add(submitFormButtonFragmentEntryLink);

			layoutStructure.addFragmentStyledLayoutStructureItem(
				submitFormButtonFragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), -1);
		}

		return fragmentEntryLinks;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			String formItemId, FragmentEntry fragmentEntry,
			InfoField<?> infoField, Layout layout,
			LayoutStructure layoutStructure, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, layout.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				layout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				serviceContext);

		if (infoField != null) {
			JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

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

	private LayoutStructureItem _findFormButtonsContainer(
		LayoutStructureItem formStepLayoutStructureItem,
		LayoutStructureItem formStyledLayoutStructureItem,
		LayoutStructure layoutStructure) {

		for (String childrenItemId :
				formStepLayoutStructureItem.getChildrenItemIds()) {

			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			if (_isFormButtonsContainer(
					formStepLayoutStructureItem, formStyledLayoutStructureItem,
					layoutStructureItem)) {

				return layoutStructureItem;
			}
		}

		return null;
	}

	private LayoutStructureItem _findFormStepContainerStyledLayoutStructureItem(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem,
		LayoutStructure layoutStructure) {

		for (String childrenItemId :
				formStyledLayoutStructureItem.getChildrenItemIds()) {

			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			if (layoutStructureItem instanceof
					FormStepContainerStyledLayoutStructureItem) {

				return layoutStructureItem;
			}
		}

		return null;
	}

	private String _getFormButtonsContainerId(
		LayoutStructureItem formStepLayoutStructureItem,
		LayoutStructureItem formStyledLayoutStructureItem) {

		return formStyledLayoutStructureItem.getItemId() + StringPool.DASH +
			formStepLayoutStructureItem.getItemId();
	}

	private FragmentEntry _getFragmentEntry(
		long companyId, JSONObject defaultInputFragmentEntryKeysJSONObject,
		String name) {

		JSONObject jsonObject =
			defaultInputFragmentEntryKeysJSONObject.getJSONObject(name);

		if (jsonObject == null) {
			return null;
		}

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				jsonObject.getString("key"));

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Group group = _groupLocalService.fetchGroup(
			companyId, jsonObject.getString("groupKey"));

		if (group == null) {
			return null;
		}

		return _fragmentEntryLocalService.fetchFragmentEntry(
			group.getGroupId(), jsonObject.getString("key"));
	}

	private List<InfoField<?>> _getInfoFields(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			long groupId)
		throws PortalException {

		String className = formStyledLayoutStructureItem.getClassName();

		if (Validator.isNull(className)) {
			return Collections.emptyList();
		}

		String itemClassName = _infoSearchClassMapperRegistry.getClassName(
			className);

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
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

	private boolean _isFormButtonsContainer(
		LayoutStructureItem formStepLayoutStructureItem,
		LayoutStructureItem formStyledLayoutStructureItem,
		LayoutStructureItem layoutStructureItem) {

		if ((layoutStructureItem instanceof
				ContainerStyledLayoutStructureItem) &&
			Objects.equals(
				layoutStructureItem.getItemId(),
				_getFormButtonsContainerId(
					formStepLayoutStructureItem,
					formStyledLayoutStructureItem))) {

			return true;
		}

		return false;
	}

	private static final String _NEXT = "next";

	private static final String _PREVIOUS = "previous";

	private static final String _SUBMIT = "submit";

	private static final Log _log = LogFactoryUtil.getLog(
		FormItemManager.class);

	@Reference
	private DefaultInputFragmentEntryConfigurationProvider
		_defaultInputFragmentEntryConfigurationProvider;

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}