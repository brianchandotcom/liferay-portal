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

package com.liferay.fragment.entry.processor.resources.util;

import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eudaldo Alonso
 */
public class ResourcesFragmentEntryProcessorUtil {

	public static String processResources(
			FragmentEntryLink fragmentEntryLink, String code)
		throws PortalException {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry == null) {
			return code;
		}

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentEntry.getFragmentCollectionId());

		Matcher matcher = _pattern.matcher(code);

		while (matcher.find()) {
			if (fragmentEntry.getGroupId() <= 0) {
				continue;
			}

			FileEntry fileEntry = fragmentCollection.getResource(
				matcher.group(1));

			String fileEntryURL = StringPool.BLANK;

			if (fileEntry != null) {
				fileEntryURL = DLURLHelperUtil.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false);
			}

			code = StringUtil.replace(code, matcher.group(), fileEntryURL);
		}

		return code;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\[resources:(.+?)\\]");

}