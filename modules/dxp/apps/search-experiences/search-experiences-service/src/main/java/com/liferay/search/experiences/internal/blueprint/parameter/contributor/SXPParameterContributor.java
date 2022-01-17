/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.List;
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
		getSXPParameterContributorDefinitions(long companyId);

}