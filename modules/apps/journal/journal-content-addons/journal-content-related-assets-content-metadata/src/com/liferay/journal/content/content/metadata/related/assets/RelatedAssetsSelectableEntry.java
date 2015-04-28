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

package com.liferay.journal.content.content.metadata.related.assets;

import com.liferay.journal.content.web.util.ContentMetadataEntry;
import com.liferay.portal.model.BaseJSPSelectableEntry;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true, service = ContentMetadataEntry.class
)
public class RelatedAssetsSelectableEntry
	extends BaseJSPSelectableEntry implements ContentMetadataEntry {

	@Override
	public String getIcon() {
		return "file";
	}

	@Override
	public String getJSPPath() {
		return _JSP_PATH;
	}

	@Override
	public String getKey() {
		return "enableRelatedAssets";
	}

	@Override
	public String getLabel() {
		return "related-assets";
	}

	@Override
	public Double getWeight() {
		return 1.0;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.content.metadata.related.assets)"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String _JSP_PATH =
		"/META-INF/resources/related_assets.jsp";

}