/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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