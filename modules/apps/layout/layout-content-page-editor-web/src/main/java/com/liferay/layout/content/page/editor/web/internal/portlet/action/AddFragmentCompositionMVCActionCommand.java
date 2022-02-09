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

import com.liferay.dynamic.data.mapping.exception.StorageFieldValueException;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.layout.page.template.serializer.LayoutStructureItemJSONSerializer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/add_fragment_composition"
	},
	service = MVCActionCommand.class
)
public class AddFragmentCompositionMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fragmentCollection == null) {
			String fragmentCollectionName = LanguageUtil.get(
				themeDisplay.getRequest(), "saved-fragments");

			fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					themeDisplay.getScopeGroupId(), fragmentCollectionName,
					fragmentCollectionName, serviceContext);
		}

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		String itemId = ParamUtil.getString(actionRequest, "itemId");
		boolean saveInlineContent = ParamUtil.getBoolean(
			actionRequest, "saveInlineContent");
		boolean saveMappingConfiguration = ParamUtil.getBoolean(
			actionRequest, "saveMappingConfiguration");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		String layoutStructureItemJSON =
			_layoutStructureItemJSONSerializer.toJSONString(
				_layoutLocalService.getLayout(themeDisplay.getPlid()), itemId,
				saveInlineContent, saveMappingConfiguration,
				segmentsExperienceId);

		FragmentComposition fragmentComposition =
			_fragmentCompositionService.addFragmentComposition(
				themeDisplay.getScopeGroupId(),
				fragmentCollection.getFragmentCollectionId(), null, name,
				description, layoutStructureItemJSON, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		String previewImageURL = ParamUtil.getString(
			actionRequest, "previewImageURL");

		if (Validator.isNotNull(previewImageURL)) {
			FileEntry previewFileEntry = _addPreviewImage(
				fragmentComposition.getFragmentCompositionId(), previewImageURL,
				serviceContext, themeDisplay);

			if (previewFileEntry != null) {
				fragmentComposition =
					_fragmentCompositionService.updateFragmentComposition(
						fragmentComposition.getFragmentCompositionId(),
						previewFileEntry.getFileEntryId());
			}
		}

		return JSONUtil.put(
			"fragmentCollectionId",
			String.valueOf(fragmentCollection.getFragmentCollectionId())
		).put(
			"fragmentCollectionName", fragmentCollection.getName()
		).put(
			"fragmentEntryKey", fragmentComposition.getFragmentCompositionKey()
		).put(
			"groupId", fragmentComposition.getGroupId()
		).put(
			"icon", "edit-layout"
		).put(
			"imagePreviewURL",
			fragmentComposition.getImagePreviewURL(themeDisplay)
		).put(
			"name", fragmentComposition.getName()
		).put(
			"type", ContentPageEditorConstants.TYPE_COMPOSITION
		);
	}

	private FileEntry _addPreviewImage(
			long fragmentCompositionId, String url,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws Exception {

		byte[] bytes = {};

		try {
			if (url.startsWith("data:image/")) {
				String[] urlParts = url.split(";base64,");

				bytes = Base64.decode(urlParts[1]);
			}
			else if (Validator.isUrl(url, true)) {
				if (StringUtil.startsWith(url, StringPool.SLASH)) {
					url = _portal.getPortalURL(themeDisplay) + url;
				}

				URL imageURL = new URL(url);

				bytes = FileUtil.getBytes(imageURL.openStream());
			}

			if (bytes.length == 0) {
				return null;
			}

			Repository repository =
				PortletFileRepositoryUtil.fetchPortletRepository(
					themeDisplay.getScopeGroupId(),
					FragmentPortletKeys.FRAGMENT);

			if (repository == null) {
				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				repository = PortletFileRepositoryUtil.addPortletRepository(
					themeDisplay.getScopeGroupId(),
					FragmentPortletKeys.FRAGMENT, serviceContext);
			}

			return _portletFileRepository.addPortletFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				FragmentComposition.class.getName(), fragmentCompositionId,
				FragmentPortletKeys.FRAGMENT, repository.getDlFolderId(), bytes,
				fragmentCompositionId + "_preview",
				MimeTypesUtil.getContentType(url), false);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			throw new StorageFieldValueException(
				LanguageUtil.format(
					themeDisplay.getRequest(), "the-file-x-cannot-be-saved",
					url));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddFragmentCompositionMVCActionCommand.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutStructureItemJSONSerializer
		_layoutStructureItemJSONSerializer;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}