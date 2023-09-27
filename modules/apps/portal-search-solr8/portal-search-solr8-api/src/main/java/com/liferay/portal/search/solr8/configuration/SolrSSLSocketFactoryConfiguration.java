/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.solr8.configuration.SolrSSLSocketFactoryConfiguration",
	localization = "content/Language",
	name = "solr8-ssl-socket-factory-configuration-name"
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