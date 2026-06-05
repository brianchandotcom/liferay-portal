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
	id = "com.liferay.portal.search.solr8.configuration.SolrHttpClientFactoryConfiguration",
	localization = "content/Language",
	name = "solr8-http-client-factory-configuration-name"
)
public interface SolrHttpClientFactoryConfiguration {

	@Meta.AD(deflt = "solr", required = false)
	public String basicAuthPassword();

	@Meta.AD(deflt = "solr", required = false)
	public String basicAuthUserName();

	@Meta.AD(deflt = "20", required = false)
	public int defaultMaxConnectionsPerRoute();

	@Meta.AD(deflt = "20", required = false)
	public int maxTotalConnections();

}