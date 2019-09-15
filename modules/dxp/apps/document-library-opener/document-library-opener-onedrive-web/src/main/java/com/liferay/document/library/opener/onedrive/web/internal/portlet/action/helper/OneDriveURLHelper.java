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

package com.liferay.document.library.opener.onedrive.web.internal.portlet.action.helper;

import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = OneDriveURLHelper.class)
public class OneDriveURLHelper {

	public String getBackgroundTaskStatusURL(
		PortletRequest portletRequest,
		DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference) {

		ResourceURL resourceURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			PortletRequest.RESOURCE_PHASE);

		resourceURL.setParameter(
			"backgroundTaskId",
			String.valueOf(
				dlOpenerOneDriveFileReference.getBackgroundTaskId()));
		resourceURL.setParameter(
			"fileEntryId",
			String.valueOf(dlOpenerOneDriveFileReference.getFileEntryId()));
		resourceURL.setResourceID(
			"/document_library/onedrive_background_task_status");

		return resourceURL.toString();
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}