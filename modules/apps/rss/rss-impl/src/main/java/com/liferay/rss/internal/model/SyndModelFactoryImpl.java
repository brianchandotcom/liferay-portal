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

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEnclosure;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = SyndModelFactory.class)
public class SyndModelFactoryImpl implements SyndModelFactory {

	@Override
	public SyndContent createSyndContent() {
		return new SyndContentImpl();
	}

	@Override
	public SyndEnclosure createSyndEnclosure() {
		return new SyndEnclosureImpl();
	}

	@Override
	public SyndEntry createSyndEntry() {
		return new SyndEntryImpl();
	}

	@Override
	public SyndFeed createSyndFeed() {
		return new SyndFeedImpl();
	}

	@Override
	public SyndLink createSyndLink() {
		return new SyndLinkImpl();
	}

}