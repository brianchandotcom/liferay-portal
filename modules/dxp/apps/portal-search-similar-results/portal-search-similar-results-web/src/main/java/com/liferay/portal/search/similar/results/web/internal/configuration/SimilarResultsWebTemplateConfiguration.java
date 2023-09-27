/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kevin Tan
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.search.similar.results.web.internal.configuration.SimilarResultsWebTemplateConfiguration"
)
public interface SimilarResultsWebTemplateConfiguration {

	@Meta.AD(
		deflt = "similar-results-compact-ftl",
		name = "similar-results-template-key-default", required = false
	)
	public String similarResultsTemplateKeyDefault();

}