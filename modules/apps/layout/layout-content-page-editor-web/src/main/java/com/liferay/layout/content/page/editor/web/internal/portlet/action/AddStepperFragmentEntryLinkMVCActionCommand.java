/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
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
		"mvc.command.name=/layout_content_page_editor/add_stepper_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class AddStepperFragmentEntryLinkMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected FragmentEntryLink addFragmentEntryLink(
			ActionRequest actionRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String fragmentEntryKey = ParamUtil.getString(
			actionRequest, "fragmentEntryKey");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentEntry fragmentEntry =
			_fragmentEntryLinkManager.getFragmentEntry(
				groupId, fragmentEntryKey, serviceContext.getLocale());

		FragmentRenderer fragmentRenderer =
			_fragmentRendererRegistry.getFragmentRenderer(fragmentEntryKey);

		if ((fragmentEntry == null) && (fragmentRenderer == null)) {
			throw new NoSuchEntryException();
		}

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		if (fragmentEntry != null) {
			String contributedRendererKey = null;

			if (fragmentEntry.getFragmentEntryId() == 0) {
				contributedRendererKey = fragmentEntryKey;
			}

			return _fragmentEntryLinkService.addFragmentEntryLink(
				null, serviceContext.getScopeGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				serviceContext.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				contributedRendererKey, fragmentEntry.getType(),
				serviceContext);
		}

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(null);

		return _fragmentEntryLinkService.addFragmentEntryLink(
			null, serviceContext.getScopeGroupId(), 0, 0, segmentsExperienceId,
			serviceContext.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK,
			fragmentRenderer.getConfiguration(defaultFragmentRendererContext),
			StringPool.BLANK, StringPool.BLANK, 0, fragmentEntryKey,
			fragmentRenderer.getType(), serviceContext);
	}

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _processAddFragmentEntryLink(
			actionRequest, actionResponse);

		SessionMessages.add(actionRequest, "fragmentEntryLinkAdded");

		return jsonObject;
	}

	@Override
	protected JSONObject processException(
		ActionRequest actionRequest, Exception exception) {

		if (exception instanceof LockedLayoutException) {
			return processLockedLayoutException(actionRequest);
		}

		String errorMessage = "an-unexpected-error-occurred";

		if (exception instanceof FragmentEntryContentException) {
			errorMessage = exception.getMessage();
		}
		else if (exception instanceof NoSuchEntryException) {
			errorMessage =
				"the-fragment-can-no-longer-be-added-because-it-has-been-" +
					"deleted";
		}

		return JSONUtil.put(
			"error",
			_language.get(
				_portal.getHttpServletRequest(actionRequest), errorMessage));
	}

	private JSONObject _processAddFragmentEntryLink(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		List<LayoutStructureItem> addedLayoutStructureItems = new ArrayList<>();
		List<LayoutStructureItem> movedLayoutStructureItems = new ArrayList<>();
		List<LayoutStructureItem> removedLayoutStructureItems =
			new ArrayList<>();

		FragmentEntryLink stepperFragmentEntryLink = addFragmentEntryLink(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String parentItemId = ParamUtil.getString(
			actionRequest, "parentItemId");
		int numberOfSteps = ParamUtil.getInteger(
			actionRequest, "numberOfSteps");
		int position = ParamUtil.getInteger(actionRequest, "position");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.addFragmentStyledLayoutStructureItem(
				stepperFragmentEntryLink.getFragmentEntryLinkId(), parentItemId,
				position);

		jsonObject.put("addedItemId", layoutStructureItem.getItemId());

		addedLayoutStructureItems.add(layoutStructureItem);

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

		for (FragmentEntryLinkListener fragmentEntryLinkListener :
				_fragmentEntryLinkListenerRegistry.
					getFragmentEntryLinkListeners()) {

			fragmentEntryLinkListener.onAddFragmentEntryLink(
				stepperFragmentEntryLink);
		}

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
			"fragmentEntryLinks",
			JSONUtil.put(
				_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
					stepperFragmentEntryLink,
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse),
					layoutStructure))
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
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentRendererRegistry _fragmentRendererRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

}