/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.script.Script;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class TermsSetQuery extends Query {

	public abstract String getFieldName();

	public abstract String getMinimumShouldMatchField();

	public abstract Script getMinimumShouldMatchScript();

	public abstract List<Object> getValues();

	public abstract Boolean isCached();

	public abstract void setCached(Boolean cached);

	public abstract void setMinimumShouldMatchField(
		String minimumShouldMatchField);

	public abstract void setMinimumShouldMatchScript(
		Script minimumShouldMatchScript);

}