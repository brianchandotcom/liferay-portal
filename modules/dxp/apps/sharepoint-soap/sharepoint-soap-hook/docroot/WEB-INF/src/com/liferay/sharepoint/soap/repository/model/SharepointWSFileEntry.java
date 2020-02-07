/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.sharepoint.connector.SharepointObject;

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