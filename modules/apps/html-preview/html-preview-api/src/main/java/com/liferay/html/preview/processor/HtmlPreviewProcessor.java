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

package com.liferay.html.preview.processor;

import java.io.File;

/**
 * @author Pavel Savinov
 */
public interface HtmlPreviewProcessor {

	public static final int WIDTH_DEFAULT = 1024;

	public File generateContentHtmlPreview(String content);

	public File generateContentHtmlPreview(String content, int width);

	public File generateURLHtmlPreview(String url);

	public File generateURLHtmlPreview(String url, int width);

	public String getMimeType();

}