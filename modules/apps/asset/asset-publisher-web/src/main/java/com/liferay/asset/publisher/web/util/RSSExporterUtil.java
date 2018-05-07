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

package com.liferay.asset.publisher.web.util;

import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndFeed;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class RSSExporterUtil {

	public static String export(SyndFeed syndFeed) {
		return _rssExporter.export(syndFeed);
	}

	@Reference(unbind = "-")
	protected void setRSSExporter(RSSExporter rssExporter) {
		_rssExporter = rssExporter;
	}

	private static RSSExporter _rssExporter;

}