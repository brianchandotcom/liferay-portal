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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Manuel de la Peña
 */
@ExtendedObjectClassDefinition(category = "file-storage")
@Meta.OCD(
	id = "com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration",
	localization = "content/Language", name = "jcr-store-configuration-name"
)
public interface JCRStoreConfiguration {

	@Meta.AD(deflt = "false", name = "initialize-on-startup")
	public boolean initializeOnStartup();

	@Meta.AD(deflt = "true", name = "wrap-session")
	public boolean wrapSession();

	@Meta.AD(deflt = "false", name = "move-version-labels")
	public boolean moveVersionLabels();

	@Meta.AD(deflt = "liferay", name = "workspace-name")
	public String workspaceName();

	@Meta.AD(deflt = "documentlibrary", name = "node-documentlibrary")
	public String nodeDocumentlibrary();

	@Meta.AD(deflt = "data/jackrabbit", name = "jackrabbit-repository-root")
	public String jackrabbitRepositoryRoot();

	@Meta.AD(deflt = "repository.xml", name = "jackrabbit-config-file-path")
	public String jackrabbitConfigFilePath();

	@Meta.AD(deflt = "home", name = "jackrabbit-repository-home")
	public String jackrabbitRepositoryHome();

	@Meta.AD(deflt = "none", name = "jackrabbit-credentials-username")
	public String jackrabbitCredentialsUsername();

	@Meta.AD(deflt = "none", name = "jackrabbit-credentials-password")
	public String jackrabbitCredentialsPassword();

}