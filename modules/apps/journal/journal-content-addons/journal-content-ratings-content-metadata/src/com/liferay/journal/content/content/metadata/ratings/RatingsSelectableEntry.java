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

package com.liferay.journal.content.content.metadata.ratings;

import com.liferay.journal.content.web.util.ContentMetadataEntry;
import com.liferay.portal.model.BaseSelectableEntry;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
@Component(
	immediate = true, service = ContentMetadataEntry.class
)
public class RatingsSelectableEntry
	extends BaseSelectableEntry implements ContentMetadataEntry {

	@Override
	public String getIcon() {
		return "star-half-full";
	}

	@Override
	public String getJSPPath() {
		return _JSP_PATH;
	}

	@Override
	public String getKey() {
		return "enableRatings";
	}

	@Override
	public String getLabel() {
		return "ratings";
	}

	@Override
	public Double getWeight() {
		return 2.0;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.content.metadata.ratings)"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String _JSP_PATH = "/META-INF/resources/ratings.jsp";

}