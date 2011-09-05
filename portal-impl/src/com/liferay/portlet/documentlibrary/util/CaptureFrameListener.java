/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 */
public class CaptureFrameListener extends MediaListenerAdapter {

	public CaptureFrameListener(
		File file, String extension, int height, int width) {

		_file = file;
		_extension = extension;
		_height = height;
		_width = width;
	}

	@Override
	public void onVideoPicture(IVideoPictureEvent iVideoPictureEvent) {
		try {
			if (_written) {
				return;
			}

			if (iVideoPictureEvent.getStreamIndex() != _streamIndex) {
				if (_streamIndex == -1) {
					_streamIndex = iVideoPictureEvent.getStreamIndex();
				}
				else {
					return;
				}
			}

			_file.createNewFile();

			RenderedImage renderedImage = ImageProcessorUtil.scale(
				iVideoPictureEvent.getImage(), _height, _width);

			ImageIO.write(
				renderedImage, _extension, new FileOutputStream(_file));

			_written = true;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public boolean isWritten() {
		return _written;
	}

	private static Log _log = LogFactoryUtil.getLog(CaptureFrameListener.class);

	private String _extension;
	private File _file;
	private int _height;
	private int _streamIndex = -1;
	private int _width;
	private boolean _written;

}