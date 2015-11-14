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

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.search.solr.configuration.SolrHttpClientFactoryConfiguration"
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