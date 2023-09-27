/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.document.library.kernel.util.PDFProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.io.InputStream;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Samuel Trong Tran
 */
@Component(service = CTDisplayRenderer.class)
public class DLFileEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileEntry> {

	@Override
	public InputStream getDownloadInputStream(
			DLFileEntry dlFileEntry, String key)
		throws PortalException {

		return DLFileVersionCTDisplayRenderer.getDownloadInputStream(
			_store, _audioProcessor, _dlAppLocalService,
			dlFileEntry.getFileVersion(), _imageProcessor, key, _pdfProcessor,
			_videoProcessor);
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, DLFileEntry dlFileEntry)
		throws Exception {

		Group group = _groupLocalService.getGroup(dlFileEntry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/document_library/edit_file_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"fileEntryId", dlFileEntry.getFileEntryId()
		).buildString();
	}

	@Override
	public Class<DLFileEntry> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileEntry dlFileEntry) {
		if (dlFileEntry.isInTrash()) {
			return _trashHelper.getOriginalTitle(dlFileEntry.getTitle());
		}

		return dlFileEntry.getTitle();
	}

	@Override
	public boolean isHideable(DLFileEntry dlFileEntry) {
		String title = dlFileEntry.getTitle();

		return title.contains(TempFileEntryUtil.TEMP_RANDOM_SUFFIX);
	}

	@Override
	public void render(DisplayContext<DLFileEntry> displayContext)
		throws Exception {

		DLFileEntry dlFileEntry = displayContext.getModel();

		displayContext.render(
			dlFileEntry.getFileVersion(), displayContext.getLocale());
	}

	@Override
	public String renderPreview(DisplayContext<DLFileEntry> displayContext)
		throws Exception {

		DLFileEntry dlFileEntry = displayContext.getModel();

		return displayContext.renderPreview(
			dlFileEntry.getFileVersion(), displayContext.getLocale());
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private AudioProcessor _audioProcessor;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ImageProcessor _imageProcessor;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private PDFProcessor _pdfProcessor;

	@Reference
	private Portal _portal;

	@Reference(target = "(default=true)")
	private Store _store;

	@Reference
	private TrashHelper _trashHelper;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private VideoProcessor _videoProcessor;

}