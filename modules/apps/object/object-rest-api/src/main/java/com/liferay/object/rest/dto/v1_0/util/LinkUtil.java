/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.dto.v1_0.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Carolina Barbosa
 */
public class LinkUtil {

	public static Link toLink(
		DLAppService dlAppService, DLFileEntry dlFileEntry,
		DLURLHelper dlURLHelper, String objectDefinitionExternalReferenceCode,
		String objectEntryExternalReferenceCode, Portal portal) {

		return new Link() {
			{
				label = dlFileEntry.getFileName();

				setHref(
					() -> {
						try {
							FileEntry fileEntry = dlAppService.getFileEntry(
								dlFileEntry.getFileEntryId());

							String downloadURL = dlURLHelper.getDownloadURL(
								fileEntry, fileEntry.getFileVersion(), null,
								StringPool.BLANK);

							downloadURL = HttpComponentsUtil.addParameter(
								downloadURL,
								"objectDefinitionExternalReferenceCode",
								objectDefinitionExternalReferenceCode);
							downloadURL = HttpComponentsUtil.addParameter(
								downloadURL, "objectEntryExternalReferenceCode",
								objectEntryExternalReferenceCode);

							return downloadURL;
						}
						catch (Exception exception) {
							if (_log.isWarnEnabled()) {
								_log.warn(exception);
							}
						}

						return StringBundler.concat(
							portal.getPathContext(), portal.getPathMain(),
							"/portal/login");
					});
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(LinkUtil.class);

}