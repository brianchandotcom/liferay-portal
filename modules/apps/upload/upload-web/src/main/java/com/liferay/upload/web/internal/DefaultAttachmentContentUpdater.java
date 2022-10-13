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