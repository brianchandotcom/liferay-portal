/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.web.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.upload.AttachmentContentUpdater;
import com.liferay.upload.AttachmentElementHandler;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 * @author Jürgen Kappler
 */
@Component(service = AttachmentContentUpdater.class)
public class DefaultAttachmentContentUpdater
	implements AttachmentContentUpdater {

	@Override
	public String updateContent(
			String content, String contentType,
			UnsafeFunction<FileEntry, FileEntry, PortalException>
				saveTempFileUnsafeFunction)
		throws PortalException {

		if (!ContentTypes.TEXT_HTML.equals(contentType)) {
			throw new IllegalArgumentException(
				"Unsupported content type: " + contentType);
		}

		for (AttachmentElementHandler attachmentElementHandler :
				_serviceTrackerList) {

			content = attachmentElementHandler.replaceAttachmentElements(
				content, saveTempFileUnsafeFunction);
		}

		return content;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, AttachmentElementHandler.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<AttachmentElementHandler> _serviceTrackerList;

}