/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "blogs")
@Meta.OCD(
	id = "com.liferay.blogs.configuration.BlogsConfiguration",
	localization = "content/Language", name = "blogs-configuration-name"
)
public interface BlogsConfiguration {

	/**
	 * Set the location of the XML file containing the configuration of the
	 * default display templates for the Blogs portlet.
	 */
	@Meta.AD(
		deflt = "com/liferay/blogs/web/portlet/display/template/dependencies/portlet-display-templates.xml",
		name = "display-templates-config", required = false
	)
	public String displayTemplatesConfig();

	/**
	 * Set the interval in minutes on how often to check for and display blog
	 * entries scheduled to display.
	 */
	@Meta.AD(deflt = "1", name = "entry-check-interval", required = false)
	public int entryCheckInterval();

	/**
	 * Set the interval on how often to check for new link backs. The value is
	 * set in one minute increments.
	 */
	@Meta.AD(deflt = "5", name = "linkback-job-interval", required = false)
	public int linkbackJobInterval();

}