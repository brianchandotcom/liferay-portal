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

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class AddFragmentEntryLinkMVCActionCommand extends BaseMVCActionCommand {

	protected FragmentEntryLink addFragmentEntryLink(
			ActionRequest actionRequest)
		throws PortalException {

		String fragmentEntryKey = ParamUtil.getString(
			actionRequest, "fragmentKey");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentEntry fragmentEntry = _getFragmentEntry(
			serviceContext, fragmentEntryKey);

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if ((fragmentEntry == null) && (fragmentRenderer == null)) {
			throw new NoSuchEntryException();
		}

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		FragmentEntryLink fragmentEntryLink = null;

		if (fragmentEntry != null) {
			String contributedRendererKey = null;

			if (fragmentEntry.getFragmentEntryId() == 0) {
				contributedRendererKey = fragmentEntryKey;
			}

			fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), 0,
					fragmentEntry.getFragmentEntryId(), classNameId, classPK,
					fragmentEntry.getCss(), fragmentEntry.getHtml(),
					fragmentEntry.getJs(), null, 0, contributedRendererKey,
					serviceContext);
		}
		else {
			fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), 0, 0, classNameId,
					classPK, fragmentEntryKey, serviceContext);
		}

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);
		String data = ParamUtil.getString(actionRequest, "data");

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				serviceContext.getScopeGroupId(), classNameId, classPK,
				segmentsExperienceId, data);

		return fragmentEntryLink;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<FragmentEntryLink> callable = new AddFragmentEntryLinkCallable(
			actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			FragmentEntryLink fragmentEntryLink = TransactionInvokerUtil.invoke(
				_transactionConfig, callable);

			jsonObject.put(
				"editableValues", fragmentEntryLink.getEditableValues());
			jsonObject.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId());

			SessionMessages.add(actionRequest, "fragmentEntryLinkAdded");
		}
		catch (Throwable t) {
			_log.error(t, t);

			String errorMessage = "an-unexpected-error-occurred";

			if (t.getCause() instanceof NoSuchEntryException) {
				errorMessage =
					"the-fragment-can-no-longer-be-added-because-it-has-been-" +
						"deleted";
			}

			jsonObject.put(
				"error",
				LanguageUtil.get(themeDisplay.getRequest(), errorMessage));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private FragmentEntry _getContributedFragmentEntry(
		String fragmentEntryKey) {

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributorEntries();

		return fragmentEntries.get(fragmentEntryKey);
	}

	private FragmentEntry _getFragmentEntry(
		ServiceContext serviceContext, String fragmentEntryKey) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				serviceContext.getScopeGroupId(), fragmentEntryKey);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		return _getContributedFragmentEntry(fragmentEntryKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddFragmentEntryLinkMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private class AddFragmentEntryLinkCallable
		implements Callable<FragmentEntryLink> {

		@Override
		public FragmentEntryLink call() throws Exception {
			return addFragmentEntryLink(_actionRequest);
		}

		private AddFragmentEntryLinkCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}