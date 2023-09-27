/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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