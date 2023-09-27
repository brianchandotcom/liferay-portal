/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.image;

import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.model.Image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
@ProviderType
public interface ImageTool {

	public static final String ORIENTATION_VALUE_HORIZONTAL_NORMAL = "1";

	public static final String ORIENTATION_VALUE_MIRROR_HORIZONTAL = "2";

	public static final String
		ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_90_CW = "7";

	public static final String
		ORIENTATION_VALUE_MIRROR_HORIZONTAL_ROTATE_270_CW = "5";

	public static final String ORIENTATION_VALUE_MIRROR_VERTICAL = "4";

	public static final String ORIENTATION_VALUE_ROTATE_90_CW = "6";

	public static final String ORIENTATION_VALUE_ROTATE_180 = "3";

	public static final String ORIENTATION_VALUE_ROTATE_270_CW = "8";

	public static final String TYPE_BMP = "bmp";

	public static final String TYPE_GIF = "gif";

	public static final String TYPE_JPEG = "jpg";

	public static final String TYPE_NOT_AVAILABLE = "na";

	public static final String TYPE_PNG = "png";

	public static final String TYPE_TIFF = "tiff";

	public BufferedImage convertImageType(BufferedImage sourceImage, int type);

	public RenderedImage crop(
		RenderedImage renderedImage, int height, int width, int x, int y);

	public void encodeGIF(
			RenderedImage renderedImage, OutputStream outputStream)
		throws IOException;

	public void encodeWBMP(
			RenderedImage renderedImage, OutputStream outputStream)
		throws IOException;

	public RenderedImage flipHorizontal(RenderedImage renderedImage);

	public RenderedImage flipVertical(RenderedImage renderedImage);

	public BufferedImage getBufferedImage(RenderedImage renderedImage);

	public byte[] getBytes(RenderedImage renderedImage, String contentType)
		throws IOException;

	public Image getDefaultCompanyLogo();

	public Image getDefaultOrganizationLogo();

	public Image getDefaultSpacer();

	public Image getDefaultUserFemalePortrait();

	public Image getDefaultUserMalePortrait();

	public Image getDefaultUserPortrait();

	public Image getImage(byte[] bytes)
		throws ImageResolutionException, IOException;

	public Image getImage(File file)
		throws ImageResolutionException, IOException;

	public Image getImage(InputStream inputStream)
		throws ImageResolutionException, IOException;

	public Image getImage(InputStream inputStream, boolean cleanUpStream)
		throws ImageResolutionException, IOException;

	public boolean isNullOrDefaultSpacer(byte[] bytes);

	public ImageBag read(byte[] bytes)
		throws ImageResolutionException, IOException;

	public ImageBag read(File file)
		throws ImageResolutionException, IOException;

	public ImageBag read(InputStream inputStream)
		throws ImageResolutionException, IOException;

	public RenderedImage rotate(RenderedImage renderedImage, int degrees);

	public RenderedImage scale(RenderedImage renderedImage, int width);

	public RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth);

	public void write(
			RenderedImage renderedImage, String contentType,
			OutputStream outputStream)
		throws IOException;

}