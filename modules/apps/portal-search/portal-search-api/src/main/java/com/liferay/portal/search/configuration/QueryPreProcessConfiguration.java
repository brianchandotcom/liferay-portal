/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.QueryPreProcessConfiguration",
	localization = "content/Language",
	name = "query-pre-process-configuration-name"
)
@ProviderType
public interface QueryPreProcessConfiguration {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no replacement
	 */
	@Deprecated
	@Meta.AD(deflt = "", name = "field-name-patterns", required = false)
	public String[] fieldNamePatterns();

	@Meta.AD(
		deflt = "assetTagNames|entryClassPK|extension|fileEntryTypeId|screenName",
		name = "keyword-field-names", required = false
	)
	public String[] keywordFieldNames();

}