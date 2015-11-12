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
@Meta.OCD(id = "com.liferay.portal.search.solr.configuration.SolrConfiguration")
public interface SolrConfiguration {

	@Meta.AD(
		deflt = "BASIC", optionLabels = {"Basic", "Cert"},
		optionValues = {"BASIC", "CERT"},
		required = false
	)
	public String authenticationMode();

	@Meta.AD(
		deflt = "REPLICATED", optionLabels = {"Cloud", "Replicated"},
		optionValues = {"CLOUD", "REPLICATED"}, required = false
	)
	public String clientType();

	@Meta.AD(
		deflt = "true",
		description = "Set to true to only log exceptions from Solr and not rethrow them.",
		required = false
	)
	public boolean logExceptionsOnly();

	@Meta.AD(deflt = "http://localhost:8080/solr/liferay", required = false)
	public String[] readURL();

	@Meta.AD(deflt = "http://localhost:8080/solr/liferay", required = false)
	public String[] writeURL();

	@Meta.AD(deflt = "localhost:9983", required = false)
	public String zkHost();

}