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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.upload.AttachmentContentUpdater;
import com.liferay.upload.AttachmentElementHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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
				_attachmentElementHandlers) {

			content = attachmentElementHandler.replaceAttachmentElements(
				content, saveTempFileUnsafeFunction);
		}

		return content;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addAttachmentElementHandler(
		AttachmentElementHandler attachmentElementHandler) {

		_attachmentElementHandlers.add(attachmentElementHandler);
	}

	protected void removeAttachmentElementHandler(
		AttachmentElementHandler attachmentElementHandler) {

		_attachmentElementHandlers.remove(attachmentElementHandler);
	}

	private static final List<AttachmentElementHandler>
		_attachmentElementHandlers = new CopyOnWriteArrayList<>();

}