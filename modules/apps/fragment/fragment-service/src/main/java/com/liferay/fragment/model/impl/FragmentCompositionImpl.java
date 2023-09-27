/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.model.impl;

import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;

/**
 * @author Pavel Savinov
 */
public class FragmentCompositionImpl extends FragmentCompositionBaseImpl {

	@Override
	public JSONObject getDataJSONObject() throws Exception {
		return JSONFactoryUtil.createJSONObject(getData());
	}

	@Override
	public String getIcon() {
		return _icon;
	}

	@Override
	public String getImagePreviewURL(ThemeDisplay themeDisplay) {
		if (Validator.isNotNull(_imagePreviewURL) &&
			!_imagePreviewURL.endsWith(StringPool.SLASH)) {

			return _imagePreviewURL;
		}

		try {
			FileEntry fileEntry = _getPreviewFileEntry();

			if (fileEntry == null) {
				return StringPool.BLANK;
			}

			return DLURLHelperUtil.getImagePreviewURL(fileEntry, themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to get image preview URL", exception);
		}

		return StringPool.BLANK;
	}

	@Override
	public void populateZipWriter(ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + getFragmentCompositionKey();

		JSONObject jsonObject = JSONUtil.put(
			"description", getDescription()
		).put(
			"fragmentCompositionDefinitionPath",
			"fragment-composition-definition.json"
		).put(
			"name", getName()
		);

		FileEntry previewFileEntry = _getPreviewFileEntry();

		if (previewFileEntry != null) {
			jsonObject.put(
				"thumbnailPath",
				"thumbnail." + previewFileEntry.getExtension());
		}

		zipWriter.addEntry(
			path + StringPool.SLASH +
				FragmentExportImportConstants.FILE_NAME_FRAGMENT_COMPOSITION,
			jsonObject.toString());

		zipWriter.addEntry(
			path + "/fragment-composition-definition.json", getData());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				path + "/thumbnail." + previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	@Override
	public void setIcon(String icon) {
		_icon = icon;
	}

	@Override
	public void setImagePreviewURL(String imagePreviewURL) {
		_imagePreviewURL = imagePreviewURL;
	}

	private FileEntry _getPreviewFileEntry() {
		if (getPreviewFileEntryId() <= 0) {
			return null;
		}

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				getPreviewFileEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get file entry preview ", portalException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCompositionImpl.class);

	private String _icon = "edit-layout";
	private String _imagePreviewURL;

}