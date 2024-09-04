/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.manager.FormItemManager;
import com.liferay.layout.content.page.editor.web.internal.manager.FragmentEntryLinkManager;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
public class UpdateFormItemConfigMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		return _updateFormStyledLayoutStructureItemConfig(
			actionRequest, actionResponse);
	}

	private Map<String, String> _getCurrentInputFields(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem,
		LayoutStructure layoutStructure, ThemeDisplay themeDisplay) {

		Map<String, String> inputFields = new HashMap<>();

		for (String itemId :
				LayoutStructureItemUtil.getChildrenItemIds(
					formStyledLayoutStructureItem.getItemId(),
					layoutStructure)) {

			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(itemId);

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			String inputFieldId = GetterUtil.getString(
				_fragmentEntryConfigurationParser.getFieldValue(
					fragmentEntryLink.getEditableValues(),
					new FragmentConfigurationField(
						"inputFieldId", "string", "", false, "text"),
					themeDisplay.getLocale()));

			if (Validator.isNotNull(inputFieldId)) {
				inputFields.put(
					inputFieldId,
					fragmentStyledLayoutStructureItem.getItemId());
			}
		}

		return inputFields;
	}

	private JSONObject _updateFormStyledLayoutStructureItemConfig(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String itemConfig = ParamUtil.getString(actionRequest, "itemConfig");
		String formItemId = ParamUtil.getString(actionRequest, "itemId");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), themeDisplay.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		FormStyledLayoutStructureItem previousFormStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(formItemId);

		long previousClassNameId =
			previousFormStyledLayoutStructureItem.getClassNameId();
		long previousClassTypeId =
			previousFormStyledLayoutStructureItem.getClassTypeId();
		String previousFormType =
			previousFormStyledLayoutStructureItem.getFormType();
		int previousNumberOfSteps =
			previousFormStyledLayoutStructureItem.getNumberOfSteps();

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)layoutStructure.updateItemConfig(
				_jsonFactory.createJSONObject(itemConfig), formItemId);

		JSONArray removedLayoutStructureItemsJSONArray =
			_jsonFactory.createJSONArray();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		addedFragmentEntryLinks.addAll(
			_updateFormStyledLayoutStructureItemFormType(
				formStyledLayoutStructureItem,
				formStyledLayoutStructureItem.getFormType(),
				themeDisplay.getLayout(), layoutStructure,
				themeDisplay.getLocale(),
				formStyledLayoutStructureItem.getNumberOfSteps(),
				previousFormType, previousNumberOfSteps, segmentsExperienceId,
				ServiceContextFactory.getInstance(httpServletRequest)));

		if (!Objects.equals(
				formStyledLayoutStructureItem.getClassNameId(),
				previousClassNameId) ||
			!Objects.equals(
				formStyledLayoutStructureItem.getClassTypeId(),
				previousClassTypeId)) {

			removedLayoutStructureItemsJSONArray =
				_formItemManager.removeLayoutStructureItemsJSONArray(
					formStyledLayoutStructureItem, layoutStructure, null);

			if (formStyledLayoutStructureItem.getClassNameId() > 0) {
				String[] uniqueInfoFieldIds = StringUtil.split(
					ParamUtil.getString(actionRequest, "fields"));

				if (!FeatureFlagManagerUtil.isEnabled("LPD-20213") ||
					ArrayUtil.isNotEmpty(uniqueInfoFieldIds)) {

					addedFragmentEntryLinks.addAll(
						_formItemManager.addFragmentEntryLinks(
							jsonObject, formStyledLayoutStructureItem, true,
							themeDisplay.getLayout(), layoutStructure,
							themeDisplay.getLocale(), segmentsExperienceId,
							ServiceContextFactory.getInstance(
								httpServletRequest),
							uniqueInfoFieldIds));
				}
			}
		}
		else {
			Map<String, String[]> parameterMap =
				actionRequest.getParameterMap();

			if (parameterMap.containsKey("fields") &&
				FeatureFlagManagerUtil.isEnabled("LPD-20213") &&
				(formStyledLayoutStructureItem.getClassNameId() > 0)) {

				List<String> newUniqueInfoFieldIds = new ArrayList<>();

				Map<String, String> currentInputFields = _getCurrentInputFields(
					formStyledLayoutStructureItem, layoutStructure,
					themeDisplay);

				Set<String> currentUniqueInfoFieldIds =
					currentInputFields.keySet();

				String[] uniqueInfoFieldIds = StringUtil.split(
					ParamUtil.getString(actionRequest, "fields"));

				for (String uniqueInfoFieldId : uniqueInfoFieldIds) {
					if (!currentUniqueInfoFieldIds.contains(
							uniqueInfoFieldId)) {

						newUniqueInfoFieldIds.add(uniqueInfoFieldId);
					}
				}

				if (ListUtil.isNotEmpty(newUniqueInfoFieldIds)) {
					addedFragmentEntryLinks.addAll(
						_formItemManager.addFragmentEntryLinks(
							jsonObject, formStyledLayoutStructureItem, false,
							themeDisplay.getLayout(), layoutStructure,
							themeDisplay.getLocale(), segmentsExperienceId,
							ServiceContextFactory.getInstance(
								httpServletRequest),
							newUniqueInfoFieldIds.toArray(new String[0])));
				}

				List<String> removedItemIds = new ArrayList<>();

				for (String currentUniqueInfoFieldId :
						currentUniqueInfoFieldIds) {

					if (!ArrayUtil.contains(
							uniqueInfoFieldIds, currentUniqueInfoFieldId)) {

						removedItemIds.add(
							currentInputFields.get(currentUniqueInfoFieldId));
					}
				}

				if (ListUtil.isNotEmpty(removedItemIds)) {
					removedLayoutStructureItemsJSONArray =
						_formItemManager.removeLayoutStructureItemsJSONArray(
							formStyledLayoutStructureItem, layoutStructure,
							removedItemIds);
				}
			}
		}

		layoutPageTemplateStructure =
			_layoutPageTemplateStructureService.
				updateLayoutPageTemplateStructureData(
					themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
					segmentsExperienceId, layoutStructure.toString());

		for (FragmentEntryLink addedFragmentEntryLink :
				addedFragmentEntryLinks) {

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					_fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

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
				String.valueOf(addedFragmentEntryLink.getFragmentEntryLinkId()),
				_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
					addedFragmentEntryLink, httpServletRequest,
					httpServletResponse, updatedLayoutStructure));
		}

		return jsonObject.put(
			"addedFragmentEntryLinks", addedFragmentEntryLinksJSONObject
		).put(
			"layoutData", updatedLayoutStructure.toJSONObject()
		).put(
			"removedFragmentEntryLinkIds", removedLayoutStructureItemsJSONArray
		);
	}

	private List<FragmentEntryLink>
			_updateFormStyledLayoutStructureItemFormType(
				FormStyledLayoutStructureItem formStyledLayoutStructureItem,
				String formType, Layout layout, LayoutStructure layoutStructure,
				Locale locale, int numberOfSteps, String previousFormType,
				int previousNumberOfSteps, long segmentsExperienceId,
				ServiceContext serviceContext)
		throws Exception {

		if (!Objects.equals(formType, previousFormType)) {
			if (Objects.equals(formType, "multistep")) {
				return _formItemManager.changeToMultistepFormType(
					formStyledLayoutStructureItem, layout, layoutStructure,
					locale, numberOfSteps, segmentsExperienceId,
					serviceContext);
			}

			return _formItemManager.changeToSimpleFormType(
				formStyledLayoutStructureItem, layout, layoutStructure, locale,
				segmentsExperienceId, serviceContext);
		}

		if (numberOfSteps != previousNumberOfSteps) {
			if (numberOfSteps > previousNumberOfSteps) {
				return _formItemManager.addFormButtonsFragmentEntryLinks(
					formStyledLayoutStructureItem, layout, layoutStructure,
					locale, numberOfSteps, segmentsExperienceId,
					serviceContext);
			}

			return _formItemManager.removeFormButtonsFragmentEntryLinks(
				formStyledLayoutStructureItem, layout, layoutStructure, locale,
				numberOfSteps, segmentsExperienceId, serviceContext);
		}

		return Collections.emptyList();
	}

	@Reference
	private FormItemManager _formItemManager;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

}