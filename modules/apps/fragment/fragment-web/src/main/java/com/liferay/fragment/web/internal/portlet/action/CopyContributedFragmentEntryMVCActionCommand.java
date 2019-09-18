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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;

import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/copy_contributed_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class CopyContributedFragmentEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		String[] fragmentEntryKeys = StringUtil.split(
			ParamUtil.getString(actionRequest, "fragmentEntryKeys"));

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (String fragmentEntryKey : fragmentEntryKeys) {
			FragmentEntry fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryKey);

			StringBundler sb = new StringBundler(5);

			sb.append(fragmentEntry.getName());
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "copy"));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			long previewFileEntryId = 0;

			String imagePreviewURL = fragmentEntry.getImagePreviewURL(
				themeDisplay);

			if (Validator.isNotNull(imagePreviewURL)) {
				previewFileEntryId = _getPreviewFileEntryId(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					fragmentCollectionId,
					themeDisplay.getPortalURL() + imagePreviewURL);
			}

			_fragmentEntryService.addFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentCollectionId,
				StringPool.BLANK, sb.toString(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), previewFileEntryId,
				fragmentEntry.getType(), WorkflowConstants.STATUS_APPROVED,
				serviceContext);
		}

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter(
			"fragmentCollectionId", String.valueOf(fragmentCollectionId));

		sendRedirect(actionRequest, actionResponse, redirectURL.toString());
	}

	private long _getPreviewFileEntryId(
		long userId, long groupId, long fragmentCollectionId,
		String imagePreviewURL) {

		try {
			URL url = new URL(imagePreviewURL);

			byte[] bytes = null;

			try (InputStream is = url.openStream()) {
				bytes = FileUtil.getBytes(is);
			}

			String shortFileName = FileUtil.getShortFileName(imagePreviewURL);

			Repository repository =
				PortletFileRepositoryUtil.fetchPortletRepository(
					groupId, FragmentPortletKeys.FRAGMENT);

			if (repository == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				repository = PortletFileRepositoryUtil.addPortletRepository(
					groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
			}

			FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
				groupId, userId, FragmentCollection.class.getName(),
				fragmentCollectionId, FragmentPortletKeys.FRAGMENT,
				repository.getDlFolderId(), bytes, shortFileName,
				MimeTypesUtil.getContentType(shortFileName), false);

			return fileEntry.getFileEntryId();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get preview entry image URL", e);
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyContributedFragmentEntryMVCActionCommand.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

}