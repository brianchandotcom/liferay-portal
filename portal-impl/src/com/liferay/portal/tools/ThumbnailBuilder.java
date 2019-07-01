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

package com.liferay.portal.tools;

import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.awt.image.RenderedImage;

import java.io.File;

import java.util.Map;

import javax.imageio.ImageIO;

/**
 * @author Brian Wing Shun Chan
 */
public class ThumbnailBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		File originalFile = new File(arguments.get("thumbnail.original.file"));
		File thumbnailFile = new File(
			arguments.get("thumbnail.thumbnail.file"));
		int height = GetterUtil.getInteger(arguments.get("thumbnail.height"));
		int width = GetterUtil.getInteger(arguments.get("thumbnail.width"));
		boolean overwrite = GetterUtil.getBoolean(
			arguments.get("thumbnail.overwrite"));

		try {
			new ThumbnailBuilder(
				originalFile, thumbnailFile, height, width, overwrite);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public ThumbnailBuilder(
			File originalFile, File thumbnailFile, int height, int width,
			boolean overwrite)
		throws Exception {

		if (!originalFile.exists()) {
			return;
		}

		if (!overwrite &&
			(thumbnailFile.lastModified() > originalFile.lastModified())) {

			return;
		}

		ImageBag imageBag = _imageToolUtil.read(originalFile);

		RenderedImage renderedImage = _imageToolUtil.scale(
			imageBag.getRenderedImage(), height, width);

		ImageIO.write(renderedImage, imageBag.getType(), thumbnailFile);
	}

	private static final ImageTool _imageToolUtil = ImageToolImpl.getInstance();

}