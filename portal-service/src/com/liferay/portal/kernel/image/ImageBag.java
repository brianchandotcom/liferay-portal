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

package com.liferay.portal.kernel.image;

import java.awt.image.RenderedImage;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageBag {

	public ImageBag(RenderedImage renderedImage, String type) {
		_renderedImage = renderedImage;
		_type = type;
	}

	public RenderedImage getRenderedImage() {
		return _renderedImage;
	}

	public String getType() {
		return _type;
	}

	private final RenderedImage _renderedImage;
	private final String _type;

}