/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer;

import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Transforms the original content to include Adaptive Media. This logic is
 * invoked before the content is rendered.
 *
 * <p>
 * Implementations of this interface must specify the {@link
 * ContentTransformerContentType} that they can manage and the implementation of
 * the transform function.
 * </p>
 *
 * @author Alejandro Tardín
 */
public interface ContentTransformer<T> {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public default ContentTransformerContentType<T>
		getContentTransformerContentType() {

		return (ContentTransformerContentType<T>)
			ContentTransformerContentTypes.HTML;
	}

	public T transform(T content) throws PortalException;

}