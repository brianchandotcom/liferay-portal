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

package com.liferay.adaptive.media.content.transformer;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Transforms the original content to include Adaptive Media where necessary.
 * This logic will be invoked before rendering the content.
 *
 * <p>
 * Implementations of this interface need to specify the {@link
 * ContentTransformerContentType} that they can manage and the implementation of
 * the transform function.
 * </p>
 *
 * @review
 *
 * @author Alejandro Tardín
 */
public interface ContentTransformer<T> {

	public ContentTransformerContentType<T> getContentType();

	public T transform(T content) throws PortalException;

}