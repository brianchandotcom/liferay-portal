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

package com.liferay.knowledge.base.markdown.converter;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.zip.ZipReader;

import java.io.IOException;

import java.util.List;

/**
 * @author James Hinkey
 * @author Rich Sezov
 */
public interface MarkdownConverter {

	public abstract String convert(String markdown) throws IOException;

	public String convert(
			String markdown, KBArticle article, ZipReader zip, long userId)
		throws IOException;

	public abstract List<String> getImageNames();

	public abstract String getURLTitle();

	public void parse(String markdown) throws IOException;

}