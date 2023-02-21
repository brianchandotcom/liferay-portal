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

package com.liferay.portal.log4j.internal;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

/**
 * @author Hai Yu
 */
@Plugin(
	category = Core.CATEGORY_NAME,
	name = CompanyLogRoutingFilePattern.PLUGIN_NAME, printObject = true
)
public final class CompanyLogRoutingFilePattern {

	public static final String PLUGIN_NAME = "FilePattern";

	@PluginBuilderFactory
	public static Builder newBuilder() {
		return new Builder();
	}

	public String getFileNamePattern() {
		return _fileNamePattern;
	}

	public Layout<?> getLayout() {
		return _layout;
	}

	public static class Builder
		implements org.apache.logging.log4j.core.util.Builder
			<CompanyLogRoutingFilePattern> {

		@Override
		public CompanyLogRoutingFilePattern build() {
			return new CompanyLogRoutingFilePattern(_fileNamePattern, _layout);
		}

		@PluginBuilderAttribute("fileNamePattern")
		@Required
		private String _fileNamePattern;

		@PluginElement("Layout")
		@Required
		private Layout<?> _layout;

	}

	private CompanyLogRoutingFilePattern(
		String fileNamePattern, Layout<?> layout) {

		_fileNamePattern = fileNamePattern;
		_layout = layout;
	}

	private final String _fileNamePattern;
	private final Layout<?> _layout;

}