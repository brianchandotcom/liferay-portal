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

package com.liferay.marketplace.app.manager.web.util;

import java.util.List;

import javax.portlet.MimeResponse;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public interface ModuleGroupDisplay extends Comparable<ModuleGroupDisplay> {

	public static final String MODULE_GROUP_TITLE_UNCATEGORIZED =
		"Uncategorized";

	public void addBundle(Bundle bundle);

	public List<Bundle> getBundles();

	public String getDescription();

	public String getDisplayURL(MimeResponse mimeResponse);

	public int getState();

	public String getTitle();

	public String getVersion();

}