/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.manager.FormItemManager;
import com.liferay.layout.content.page.editor.web.internal.manager.FragmentEntryLinkManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/move_stepper_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class MoveStepperFragmentEntryLinkMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		List<LayoutStructureItem> addedLayoutStructureItems = new ArrayList<>();
		List<LayoutStructureItem> movedLayoutStructureItems = new ArrayList<>();
		List<LayoutStructureItem> removedLayoutStructureItems =
			new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");
		String itemId = ParamUtil.getString(actionRequest, "itemId");
		int numberOfSteps = ParamUtil.getInteger(
			actionRequest, "numberOfSteps");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String parentItemId = ParamUtil.getString(
			actionRequest, "parentItemId");
		int position = ParamUtil.getInteger(actionRequest, "position");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		movedLayoutStructureItems.add(layoutStructureItem.clone());

		layoutStructure.moveLayoutStructureItem(itemId, parentItemId, position);

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(parentItemId);

		if (Objects.equals(
				formStyledLayoutStructureItem.getFormType(), "simple")) {

			formStyledLayoutStructureItem.setFormType("multistep");

			formStyledLayoutStructureItem.setNumberOfSteps(numberOfSteps);

			FormItemManager.LayoutStructureItemChanges
				layoutStructureItemChanges =
					_formItemManager.changeToMultistepFormType(
						formStyledLayoutStructureItem, layoutStructure,
						themeDisplay.getLocale(), numberOfSteps);

			addedLayoutStructureItems.addAll(
				layoutStructureItemChanges.getAddedLayoutStructureItems());
			movedLayoutStructureItems.addAll(
				layoutStructureItemChanges.getMovedLayoutStructureItems());
			removedLayoutStructureItems.addAll(
				layoutStructureItemChanges.getRemovedLayoutStructureItems());
		}

		_layoutPageTemplateStructureService.
			updateLayoutPageTemplateStructureData(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId, layoutStructure.toString());

		FragmentEntryLink stepperFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		JSONObject editableValuesJSONObject =
			_fragmentEntryLinkManager.mergeEditableValuesJSONObject(
				_jsonFactory.createJSONObject(
					stepperFragmentEntryLink.getEditableValues()),
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put("numberOfSteps", numberOfSteps)));

		stepperFragmentEntryLink =
			_fragmentEntryLinkService.updateFragmentEntryLink(
				stepperFragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());

		FragmentEntryProcessorContext fragmentEntryProcessorContext =
			new DefaultFragmentEntryProcessorContext(
				_portal.getHttpServletRequest(actionRequest),
				_portal.getHttpServletResponse(actionResponse),
				FragmentEntryLinkConstants.EDIT,
				LocaleUtil.getMostRelevantLocale());

		String processedHTML =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				stepperFragmentEntryLink, fragmentEntryProcessorContext);

		JSONObject newEditableValuesJSONObject =
			_fragmentEntryLinkManager.mergeEditableValuesJSONObject(
				_fragmentEntryProcessorRegistry.
					getDefaultEditableValuesJSONObject(
						processedHTML,
						stepperFragmentEntryLink.getConfiguration()),
				editableValuesJSONObject);

		stepperFragmentEntryLink =
			_fragmentEntryLinkService.updateFragmentEntryLink(
				stepperFragmentEntryLink.getFragmentEntryLinkId(),
				newEditableValuesJSONObject.toString());

		for (FragmentEntryLinkListener fragmentEntryLinkListener :
				_fragmentEntryLinkListenerRegistry.
					getFragmentEntryLinkListeners()) {

			fragmentEntryLinkListener.
				onUpdateFragmentEntryLinkConfigurationValues(
					stepperFragmentEntryLink);
		}

		return jsonObject.put(
			"addedItemIds",
			_jsonFactory.createJSONArray(
				TransformUtil.transform(
					addedLayoutStructureItems, LayoutStructureItem::getItemId))
		).put(
			"layoutData", layoutStructure.toJSONObject()
		).put(
			"movedItemIds",
			() -> {
				JSONArray jsonArray = _jsonFactory.createJSONArray();

				for (LayoutStructureItem movedLayoutStructureItem :
						movedLayoutStructureItems) {

					jsonArray.put(
						JSONUtil.put(
							"itemId", movedLayoutStructureItem.getItemId()
						).put(
							"parentId",
							movedLayoutStructureItem.getParentItemId()
						));
				}

				return jsonArray;
			}
		).put(
			"removedItemIds",
			_jsonFactory.createJSONArray(
				TransformUtil.transform(
					removedLayoutStructureItems,
					LayoutStructureItem::getItemId))
		);
	}

	@Reference
	private FormItemManager _formItemManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

}