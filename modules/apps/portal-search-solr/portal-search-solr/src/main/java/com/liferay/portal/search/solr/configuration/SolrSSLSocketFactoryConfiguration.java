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

package com.liferay.portal.search.solr.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.search.solr.configuration.SolrSSLSocketFactoryConfiguration",
	localization = "content/Language",
	name = "solr-ssl-socket-factory-configuration-name"
)
public interface SolrSSLSocketFactoryConfiguration {

	@Meta.AD(deflt = "secret", required = false)
	public String keyStorePassword();

	@Meta.AD(deflt = "classpath:/keystore.jks", required = false)
	public String keyStorePath();

	@Meta.AD(deflt = "JKS", required = false)
	public String keyStoreType();

	@Meta.AD(deflt = "secret", required = false)
	public String trustStorePassword();

	@Meta.AD(deflt = "classpath:/truststore.jks", required = false)
	public String trustStorePath();

	@Meta.AD(deflt = "JKS", required = false)
	public String trustStoreType();

	@Meta.AD(deflt = "true", required = false)
	public boolean verifyServerCertificate();

	@Meta.AD(deflt = "true", required = false)
	public boolean verifyServerName();

}