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

package com.liferay.portal.store.jcr.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Manuel de la Peña
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration",
	localization = "content/Language", name = "%jcr.store.configuration.name"
)
public interface JCRStoreConfiguration {

	@Meta.AD(deflt = "false", required = true)
	public boolean initializeOnStartup();

	@Meta.AD(deflt = "repository.xml", required = true)
	public String jackrabbitConfigFilePath();

	@Meta.AD(deflt = "none", required = true)
	public String jackrabbitCredentialsPassword();

	@Meta.AD(deflt = "none", required = true)
	public String jackrabbitCredentialsUsername();

	@Meta.AD(deflt = "home", required = true)
	public String jackrabbitRepositoryHome();

	@Meta.AD(deflt = "data/jackrabbit", required = true)
	public String jackrabbitRepositoryRoot();

	@Meta.AD(deflt = "false", required = true)
	public boolean moveVersionLabels();

	@Meta.AD(deflt = "documentlibrary", required = true)
	public String nodeDocumentlibrary();

	@Meta.AD(deflt = "liferay", required = true)
	public String workspaceName();

	@Meta.AD(deflt = "true", required = true)
	public boolean wrapSession();

}