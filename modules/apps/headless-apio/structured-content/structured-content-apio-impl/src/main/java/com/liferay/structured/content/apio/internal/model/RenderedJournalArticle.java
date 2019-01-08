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

package com.liferay.structured.content.apio.internal.model;

import java.util.Locale;
import java.util.function.Function;

/**
 * @author Eduardo Pérez
 */
public interface RenderedJournalArticle {

	public static RenderedJournalArticle create(
		Function<Locale, String> templateNameFunction,
		Function<Locale, String> renderedContentFunction) {

		return new RenderedJournalArticle() {

			@Override
			public String getRenderedContent(Locale locale) {
				return renderedContentFunction.apply(locale);
			}

			@Override
			public String getTemplateName(Locale locale) {
				return templateNameFunction.apply(locale);
			}

		};
	}

	public String getRenderedContent(Locale locale);

	public String getTemplateName(Locale locale);

}