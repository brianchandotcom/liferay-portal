/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.simplecaptcha.gimpy;

import com.jhlabs.image.RippleFilter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.util.ImageUtil;

/**
 * This modified copy of {@code nl.captcha.gimpy.RippleGimpyRenderer} works with the latest
 * version of {@code com.jhlabs} filters.
 *
 * @author James Childers
 * @author Jorge Díaz
 */
public class RippleGimpyRenderer implements GimpyRenderer {

	@Override
	public void gimp(BufferedImage bufferedImage) {
		RippleFilter rippleFilter = new RippleFilter();

		rippleFilter.setEdgeAction(RippleFilter.CLAMP);
		rippleFilter.setXAmplitude(2.6F);
		rippleFilter.setXWavelength(15);
		rippleFilter.setYAmplitude(1.7F);
		rippleFilter.setYWavelength(5);

		ImageUtil.applyFilter(
			bufferedImage, new BufferedImageFilter(rippleFilter));
	}

}