/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;

/**
 * @author Iván Zaera
 */
public class SharepointWSFileEntry
	extends SharepointWSObject implements ExtRepositoryFileEntry {

	public SharepointWSFileEntry(SharepointObject sharepointObject) {
		super(sharepointObject);
	}

	@Override
	public String getCheckedOutBy() {
		return sharepointObject.getCheckedOutBy();
	}

	@Override
	public String getMimeType() {
		return null;
	}

	@Override
	public String getTitle() {
		return sharepointObject.getName();
	}

}