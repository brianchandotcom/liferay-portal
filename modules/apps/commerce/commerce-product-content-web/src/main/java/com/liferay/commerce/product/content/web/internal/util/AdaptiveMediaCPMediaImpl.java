/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Alec Sloan
 */
public class AdaptiveMediaCPMediaImpl extends CPMediaImpl {

	public AdaptiveMediaCPMediaImpl(
			String adaptiveMediaImageHTMLTag, long commerceAccountId,
			CPAttachmentFileEntry cpAttachmentFileEntry,
			ThemeDisplay themeDisplay)
		throws PortalException {

		super(commerceAccountId, cpAttachmentFileEntry, themeDisplay);

		_adaptiveMediaImageHTMLTag = adaptiveMediaImageHTMLTag;
	}

	public String getAdaptiveMediaImageHTMLTag() {
		return _adaptiveMediaImageHTMLTag;
	}

	private final String _adaptiveMediaImageHTMLTag;

}