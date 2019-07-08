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

package com.liferay.portal.search.solr7.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	localization = "content/Language", name = "solr7-configuration-name"
)
public interface SolrConfiguration {

	@Meta.AD(
		deflt = "BASIC", optionLabels = {"basic", "cert"},
		optionValues = {"BASIC", "CERT"}, required = false
	)
	public String authenticationMode();

	@Meta.AD(
		deflt = "REPLICATED", optionLabels = {"cloud", "replicated"},
		optionValues = {"CLOUD", "REPLICATED"}, required = false
	)
	public String clientType();

	@Meta.AD(deflt = "liferay", required = false)
	public String defaultCollection();

	@Meta.AD(
		deflt = "true", description = "log-exceptions-only-help",
		required = false
	)
	public boolean logExceptionsOnly();

	@Meta.AD(deflt = "http://localhost:8983/solr/liferay", required = false)
	public String[] readURL();

	@Meta.AD(deflt = "http://localhost:8983/solr/liferay", required = false)
	public String[] writeURL();

	@Meta.AD(deflt = "localhost:9983", required = false)
	public String zkHost();

}