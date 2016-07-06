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

package com.liferay.wiki.engine.mediawiki.processor;

import com.liferay.wiki.processor.BaseWikiPageRenameContentProcessor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
public class WikiPageRenameMediaWikiContentProcessor
	extends BaseWikiPageRenameContentProcessor {

	@Activate
	@Modified
	public void activate() {
		regexps.put("\\[\\[Image:@old_title@/", "[[Image:@new_title@/");
	}

}