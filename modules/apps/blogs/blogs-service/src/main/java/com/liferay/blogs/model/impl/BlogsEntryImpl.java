/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model.impl;

import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class BlogsEntryImpl extends BlogsEntryBaseImpl {

	@Override
	public String getCoverImageAlt() throws PortalException {
		long coverImageFileEntryId = getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntryId);

		return fileEntry.getTitle();
	}

	@Override
	public String getCoverImageURL(ThemeDisplay themeDisplay)
		throws PortalException {

		long coverImageFileEntryId = getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntryId);

		return DLURLHelperUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
	}

	@Override
	public String getSmallImageAlt() throws PortalException {
		if (Validator.isNotNull(getSmallImageURL())) {
			return StringPool.BLANK;
		}

		long smallImageFileEntryId = getSmallImageFileEntryId();

		if (smallImageFileEntryId != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				smallImageFileEntryId);

			return fileEntry.getTitle();
		}

		long smallImageId = getSmallImageId();

		if ((smallImageId != 0) && isSmallImage()) {
			return StringPool.BLANK;
		}

		return getCoverImageAlt();
	}

	@Override
	public String getSmallImageURL(ThemeDisplay themeDisplay)
		throws PortalException {

		if (Validator.isNotNull(getSmallImageURL())) {
			return getSmallImageURL();
		}

		long smallImageFileEntryId = getSmallImageFileEntryId();

		if (smallImageFileEntryId != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				smallImageFileEntryId);

			return DLURLHelperUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK);
		}

		long smallImageId = getSmallImageId();

		if ((smallImageId != 0) && isSmallImage()) {
			return StringBundler.concat(
				themeDisplay.getPathImage(), "/blogs/entry?img_id=",
				smallImageId, "&t=",
				WebServerServletTokenUtil.getToken(smallImageId));
		}

		return getCoverImageURL(themeDisplay);
	}

	@Override
	public boolean isVisible() {
		Date displayDate = getDisplayDate();

		if (isApproved() && displayDate.before(new Date())) {
			return true;
		}

		return false;
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	private String _smallImageType;

}