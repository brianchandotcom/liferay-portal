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

package com.liferay.document.library.preview;

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Renders file previews in conjunction with {@link DLPreviewRenderer}.
 *
 * <p>
 * Implementations must specify at least one value for the OSGi property {@code
 * content.type}, and can be called only for those supported content types. For
 * example, a {@code DLPreviewRendererProvider} that can provide previews for
 * PDF files would have the content type settings {@code
 * content.type=application/pdf} and {@code content.type=application/x-pdf}.
 * </p>
 *
 * @author Alejandro Tardín
 */
@ProviderType
public interface DLPreviewRendererProvider {

	public Set<String> getMimeTypes();

	public DLPreviewRenderer getPreviewDLPreviewRenderer(
		FileVersion fileVersion);

	public DLPreviewRenderer getThumbnailDLPreviewRenderer(
		FileVersion fileVersion);

}