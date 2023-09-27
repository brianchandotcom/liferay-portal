/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameterContributor {

	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters);

	public String getSXPParameterCategoryNameKey();

	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId, Locale locale);

}