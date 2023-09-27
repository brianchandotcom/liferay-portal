/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.simplecaptcha.gimpy;

import com.jhlabs.image.BlockFilter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.util.ImageUtil;

/**
 * This modified copy of {@code nl.captcha.gimpy.BlockGimpyRenderer} works with the latest
 * version of {@code com.jhlabs} filters.
 *
 * @author James Childers
 * @author Jorge Díaz
 */
public class BlockGimpyRenderer implements GimpyRenderer {

	public BlockGimpyRenderer() {
		this(3);
	}

	public BlockGimpyRenderer(int blockSize) {
		_blockSize = blockSize;
	}

	@Override
	public void gimp(BufferedImage bufferedImage) {
		BlockFilter blockFilter = new BlockFilter();

		blockFilter.setBlockSize(_blockSize);

		ImageUtil.applyFilter(
			bufferedImage, new BufferedImageFilter(blockFilter));
	}

	private final int _blockSize;

}