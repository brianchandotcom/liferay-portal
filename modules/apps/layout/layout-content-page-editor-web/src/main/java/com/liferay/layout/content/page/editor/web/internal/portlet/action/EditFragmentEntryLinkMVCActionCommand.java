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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Iterator;
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
		"mvc.command.name=/content_layout/edit_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class EditFragmentEntryLinkMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<FragmentEntryLink> callable =
			new EditFragmentEntryLinkCallable(actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable t) {
			_log.error(t, t);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private FragmentEntryLink _editFragmentEntryLink(
			ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");

		String editableValues = ParamUtil.getString(
			actionRequest, "editableValues");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLinkId, editableValues);

		_assetEntryUsageLocalService.deleteAssetEntryUsages(
			_portal.getClassNameId(FragmentEntryLink.class),
			String.valueOf(fragmentEntryLinkId), themeDisplay.getPlid());

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> editableKeysIterator =
				editableProcessorJSONObject.keys();

			while (editableKeysIterator.hasNext()) {
				String editableKey = editableKeysIterator.next();

				JSONObject editableJSONObject =
					editableProcessorJSONObject.getJSONObject(editableKey);

				long classPK = GetterUtil.getLong(
					editableJSONObject.getLong("classPK"));
				long classNameId = GetterUtil.getLong(
					editableJSONObject.getLong("classNameId"));
				String fieldId = editableJSONObject.getString("fieldId");

				if ((classNameId <= 0) || (classPK <= 0) ||
					Validator.isNull(fieldId)) {

					continue;
				}

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					classNameId, classPK);

				if (assetEntry == null) {
					continue;
				}

				AssetEntryUsage assetEntryUsage =
					_assetEntryUsageLocalService.fetchAssetEntryUsage(
						assetEntry.getEntryId(),
						_portal.getClassNameId(FragmentEntryLink.class),
						String.valueOf(fragmentEntryLinkId),
						themeDisplay.getPlid());

				if (assetEntryUsage != null) {
					continue;
				}

				_assetEntryUsageLocalService.addAssetEntryUsage(
					themeDisplay.getScopeGroupId(), assetEntry.getEntryId(),
					_portal.getClassNameId(FragmentEntryLink.class),
					String.valueOf(fragmentEntryLinkId), themeDisplay.getPlid(),
					ServiceContextThreadLocal.getServiceContext());
			}
		}

		return fragmentEntryLink;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditFragmentEntryLinkMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Portal _portal;

	private class EditFragmentEntryLinkCallable
		implements Callable<FragmentEntryLink> {

		@Override
		public FragmentEntryLink call() throws Exception {
			return _editFragmentEntryLink(_actionRequest);
		}

		private EditFragmentEntryLinkCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}