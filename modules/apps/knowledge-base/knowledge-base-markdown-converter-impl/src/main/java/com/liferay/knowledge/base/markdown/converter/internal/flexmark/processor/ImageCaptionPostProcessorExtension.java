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

package com.liferay.knowledge.base.markdown.converter.internal.flexmark.processor;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * An extension that registers a post processor that copies image alt text
 * to a paragraph below the image.
 *
 * @author Rich Sezov
 */
public class ImageCaptionPostProcessorExtension
	implements HtmlRenderer.HtmlRendererExtension, Parser.ParserExtension {

	public static Extension create() {
		return new ImageCaptionPostProcessorExtension();
	}

	@Override
	public void extend(
		final HtmlRenderer.Builder rendererBuilder, final String rendererType) {

		rendererBuilder.attributeProviderFactory(
			EmbeddedAttributeProvider.Factory());
	}

	@Override
	public void extend(Parser.Builder parserBuilder) {
		parserBuilder.postProcessorFactory(
			ImageCaptionPostProcessor.Factory(parserBuilder));
	}

	@Override
	public void parserOptions(MutableDataHolder options) {
	}

	@Override
	public void rendererOptions(final MutableDataHolder options) {
	}

	private ImageCaptionPostProcessorExtension() {
	}

}