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

package com.liferay.document.library.preview.internal.display.context;

import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.preview.renderer.DLPreviewRenderer;
import com.liferay.document.library.preview.renderer.DLPreviewRendererProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DLDisplayContextFactory.class)
public class DLPreviewDLDisplayContextFactory
	implements DLDisplayContextFactory {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileShortcut fileShortcut) {

		return parentDLViewFileVersionDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		return new DLViewFileVersionDisplayContext() {

			@Override
			public String getCssClassFileMimeType() {
				return parentDLViewFileVersionDisplayContext.
					getCssClassFileMimeType();
			}

			@Override
			public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
				throws PortalException {

				return parentDLViewFileVersionDisplayContext.getDDMFormValues(
					ddmStructure);
			}

			@Override
			public DDMFormValues getDDMFormValues(long ddmStorageId)
				throws PortalException {

				return parentDLViewFileVersionDisplayContext.getDDMFormValues(
					ddmStorageId);
			}

			@Override
			public List<DDMStructure> getDDMStructures()
				throws PortalException {

				return parentDLViewFileVersionDisplayContext.getDDMStructures();
			}

			@Override
			public int getDDMStructuresCount() throws PortalException {
				return parentDLViewFileVersionDisplayContext.
					getDDMStructuresCount();
			}

			@Override
			public String getDiscussionClassName() {
				return parentDLViewFileVersionDisplayContext.
					getDiscussionClassName();
			}

			@Override
			public long getDiscussionClassPK() {
				return parentDLViewFileVersionDisplayContext.
					getDiscussionClassPK();
			}

			@Override
			public String getDiscussionLabel(Locale locale) {
				return parentDLViewFileVersionDisplayContext.getDiscussionLabel(
					locale);
			}

			@Override
			public Menu getMenu() throws PortalException {
				return parentDLViewFileVersionDisplayContext.getMenu();
			}

			@Override
			public List<ToolbarItem> getToolbarItems() throws PortalException {
				return parentDLViewFileVersionDisplayContext.getToolbarItems();
			}

			@Override
			public UUID getUuid() {
				return parentDLViewFileVersionDisplayContext.getUuid();
			}

			@Override
			public boolean hasCustomThumbnail() {
				DLPreviewRendererProvider dlPreviewRendererProvider =
					_serviceTrackerMap.getService(fileVersion.getMimeType());

				if (dlPreviewRendererProvider != null) {
					Optional<DLPreviewRenderer> dlPreviewRendererOptional =
						dlPreviewRendererProvider.getThumbnailRenderer(
							fileVersion);

					if (dlPreviewRendererOptional.isPresent()) {
						return true;
					}
				}

				return parentDLViewFileVersionDisplayContext.
					hasCustomThumbnail();
			}

			@Override
			public boolean hasPreview() {
				DLPreviewRendererProvider dlPreviewRendererProvider =
					_serviceTrackerMap.getService(fileVersion.getMimeType());

				if (dlPreviewRendererProvider != null) {
					Optional<DLPreviewRenderer> dlPreviewRendererOptional =
						dlPreviewRendererProvider.getPreviewRenderer(
							fileVersion);

					if (dlPreviewRendererOptional.isPresent()) {
						return true;
					}
				}

				return parentDLViewFileVersionDisplayContext.hasPreview();
			}

			@Override
			public boolean isDownloadLinkVisible() throws PortalException {
				return parentDLViewFileVersionDisplayContext.
					isDownloadLinkVisible();
			}

			@Override
			public boolean isVersionInfoVisible() throws PortalException {
				return parentDLViewFileVersionDisplayContext.
					isVersionInfoVisible();
			}

			@Override
			public void renderCustomThumbnail(
					HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

				String mimeType = fileVersion.getMimeType();

				DLPreviewRendererProvider dlPreviewRendererProvider =
					_serviceTrackerMap.getService(mimeType);

				if (dlPreviewRendererProvider != null) {
					Optional<DLPreviewRenderer> dlPreviewRendererOptional =
						dlPreviewRendererProvider.getThumbnailRenderer(
							fileVersion);

					if (dlPreviewRendererOptional.isPresent()) {
						DLPreviewRenderer dlPreviewRenderer =
							dlPreviewRendererOptional.get();

						dlPreviewRenderer.render(request, response);

						return;
					}
				}

				parentDLViewFileVersionDisplayContext.renderCustomThumbnail(
					request, response);
			}

			@Override
			public void renderPreview(
					HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

				String mimeType = fileVersion.getMimeType();

				DLPreviewRendererProvider dlPreviewRendererProvider =
					_serviceTrackerMap.getService(mimeType);

				if (dlPreviewRendererProvider != null) {
					Optional<DLPreviewRenderer> dlPreviewRendererOptional =
						dlPreviewRendererProvider.getPreviewRenderer(
							fileVersion);

					if (dlPreviewRendererOptional.isPresent()) {
						DLPreviewRenderer dlPreviewRenderer =
							dlPreviewRendererOptional.get();

						dlPreviewRenderer.render(request, response);

						return;
					}
				}

				parentDLViewFileVersionDisplayContext.renderPreview(
					request, response);
			}

		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DLPreviewRendererProvider.class, "content.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DLPreviewRendererProvider>
		_serviceTrackerMap;

}