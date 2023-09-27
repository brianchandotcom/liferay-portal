/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.simplecaptcha.gimpy;

import com.jhlabs.image.ShadowFilter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.util.ImageUtil;

/**
 * This modified copy of {@code nl.captcha.gimpy.DropShadowGimpyRenderer} works with the
 * latest version of {@code com.jhlabs} filters.
 *
 * @author James Childers
 * @author Jorge Díaz
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

		ImageUtil.applyFilter(
			bufferedImage, new BufferedImageFilter(shadowFilter));
	}

	private final int _opacity;
	private final int _radius;

}