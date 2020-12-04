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

package com.liferay.document.library.external.video.internal.resolver;

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoPortletKeys;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelper;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelperFactory;
import com.liferay.document.library.external.video.provider.DLExternalVideoProvider;
import com.liferay.document.library.external.video.resolver.DLExternalVideoResolver;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLExternalVideoResolver.class)
public class DLExternalVideoResolverImpl implements DLExternalVideoResolver {

	@Override
	public DLExternalVideo resolve(FileEntry fileEntry) {
		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper =
			_dlExternalVideoMetadataHelperFactory.
				getDLExternalVideoMetadataHelper(fileEntry);

		if (dlExternalVideoMetadataHelper != null) {
			return _getDLExternalVideo(dlExternalVideoMetadataHelper);
		}

		return _getFileEntryDLExternalVideo(fileEntry);
	}

	@Override
	public DLExternalVideo resolve(String url) {
		for (DLExternalVideoProvider dlExternalVideoProvider :
				_dlExternalVideoProviders) {

			DLExternalVideo dlExternalVideo =
				dlExternalVideoProvider.getDLExternalVideo(url);

			if (dlExternalVideo != null) {
				return dlExternalVideo;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExternalVideoProviders = ServiceTrackerListFactory.open(
			bundleContext, DLExternalVideoProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlExternalVideoProviders.close();
	}

	private DLExternalVideo _getDLExternalVideo(
		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper) {

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION);
			}

			@Override
			public String getEmbeddableHTML() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_HTML);
			}

			@Override
			public String getThumbnailURL() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL);
			}

			@Override
			public String getTitle() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_TITLE);
			}

			@Override
			public String getURL() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_URL);
			}

		};
	}

	private String _getEmbedVideoURL(
		FileEntry fileEntry, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL getEmbedVideoURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO);

		try {
			getEmbedVideoURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getEmbedVideoURL.setParameter(
			"mvcRenderCommandName",
			"/document_library_external_video/embed_video");
		getEmbedVideoURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		return getEmbedVideoURL.toString();
	}

	private DLExternalVideo _getFileEntryDLExternalVideo(FileEntry fileEntry) {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return fileEntry.getDescription();
			}

			@Override
			public String getEmbeddableHTML() {
				return StringBundler.concat(
					"<iframe height=\"315\" frameborder=\"0\" src=\"",
					_getEmbedVideoURL(fileEntry, httpServletRequest), "&",
					"\" width=\"560\"></iframe>");
			}

			@Override
			public String getThumbnailURL() {
				return null;
			}

			@Override
			public String getTitle() {
				return fileEntry.getTitle();
			}

			@Override
			public String getURL() {
				try {
					return _dlURLHelper.getPreviewURL(
						fileEntry, fileEntry.getFileVersion(), themeDisplay,
						StringPool.BLANK, false, true);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return null;
				}
			}

		};
	}

	private HttpServletRequest _getHttpServletRequest() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getRequest();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLExternalVideoResolverImpl.class);

	@Reference
	private DLExternalVideoMetadataHelperFactory
		_dlExternalVideoMetadataHelperFactory;

	private ServiceTrackerList<DLExternalVideoProvider, DLExternalVideoProvider>
		_dlExternalVideoProviders;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Portal _portal;

}