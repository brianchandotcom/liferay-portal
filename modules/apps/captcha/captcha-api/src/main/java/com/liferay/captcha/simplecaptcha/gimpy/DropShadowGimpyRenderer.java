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

package com.liferay.captcha.simplecaptcha.gimpy;

import static nl.captcha.util.ImageUtil.applyFilter;

import com.jhlabs.image.ShadowFilter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

import nl.captcha.gimpy.GimpyRenderer;

/**
 * Copy of nl.captcha.gimpy.DropShadowGimpyRenderer modified to work with the
 * latest version of com.jhlabs filters.
 *
 * @author James Childers
 * @author Jorge Díaz
 * @review
 */
public class DropShadowGimpyRenderer implements GimpyRenderer {

	public DropShadowGimpyRenderer() {
		this(3, 75);
	}

	public DropShadowGimpyRenderer(int radius, int opacity) {
		_radius = radius;
		_opacity = opacity;
	}

	@Override
	public void gimp(BufferedImage bufferedImage) {
		ShadowFilter shadowFilter = new ShadowFilter();

		shadowFilter.setOpacity(_opacity / 100F);
		shadowFilter.setRadius(_radius);

		applyFilter(bufferedImage, new BufferedImageFilter(shadowFilter));
	}

	private final int _opacity;
	private final int _radius;

}