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

import java.util.Optional;

/**
 * Renders file previews in conjunction with {@link DLPreviewRenderer}.
 *
 * <p>
 * Implementations must specify at least one value for the OSGi property {@code
 * content.type} so they can be called only for the content types they can
 * handle.
 *
 * For example, an {@code DLPreviewRendererProvider} that can provide previews
 * for PDF files would have these OSGi property settings:
 * </p>
 *
 * <p>
 * {@code
 * content.type=application/pdf
 * content.type=application/x-pdf}
 * </p>
 *
 * @author Alejandro Tardín
 * @review
 */
public interface DLPreviewRendererProvider {

	/**
	 * Returns the {@link DLPreviewRenderer} responsible for rendering the
	 * preview of the file. If the value is empty, the default preview will be
	 * rendered.
	 *
	 * @param  fileVersion the file version to preview
	 * @return an optional of {@link DLPreviewRenderer}
	 * @review
	 */
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion);

	/**
	 * Returns the {@link DLPreviewRenderer} responsible for rendering the
	 * thumbnail of the file in the card view. If the value is empty, the
	 * default thumbnail will be rendered.
	 *
	 * @param  fileVersion the file version to preview
	 * @return an optional of {@link DLPreviewRenderer}
	 * @review
	 */
	public Optional<DLPreviewRenderer> getThumbnailDLPreviewRendererOptional(
		FileVersion fileVersion);

}