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

import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class SyndModelFactoryUtil {

	public static SyndContent createSyndContent() {
		return _syndModelFactory.createSyndContent();
	}

	public static SyndEntry createSyndEntry() {
		return _syndModelFactory.createSyndEntry();
	}

	public static SyndFeed createSyndFeed() {
		return _syndModelFactory.createSyndFeed();
	}

	public static SyndLink createSyndLink() {
		return _syndModelFactory.createSyndLink();
	}

	@Reference(unbind = "-")
	protected void setSyndModelFactory(SyndModelFactory syndModelFactory) {
		_syndModelFactory = syndModelFactory;
	}

	private static SyndModelFactory _syndModelFactory;

}