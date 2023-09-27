/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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