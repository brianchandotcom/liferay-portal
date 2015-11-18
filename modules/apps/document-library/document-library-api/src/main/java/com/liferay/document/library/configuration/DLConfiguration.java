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

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Sergio González
 */
@ConfigurationAdmin(category = "collaboration")
@Meta.OCD(id = "com.liferay.document.library.configuration.DLConfiguration")
public interface DLConfiguration {

	/**
	 * Set the location of the XML file containing the configuration of the
	 * default display templates for the Document Library portlet.
	 */
	@Meta.AD(
		deflt = "com/liferay/document/library/web/template/dependencies/portlet-display-templates.xml",
		required = false
	)
	public String displayTemplatesConfig();

	/**
	 * Set the interval in hours on how often
	 * TemporaryFileEntriesMessageListener will run to check for expired
	 * temporary file entries.
	 */
	@Meta.AD(deflt = "1", required = false)
	public int temporaryFileEntriesCheckInterval();

}