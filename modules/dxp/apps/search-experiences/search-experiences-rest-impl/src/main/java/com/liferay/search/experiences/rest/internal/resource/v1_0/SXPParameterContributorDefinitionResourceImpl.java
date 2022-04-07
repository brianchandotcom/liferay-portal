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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinitionProvider;
import com.liferay.search.experiences.rest.dto.v1_0.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.resource.v1_0.SXPParameterContributorDefinitionResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/sxp-parameter-contributor-definition.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SXPParameterContributorDefinitionResource.class
)
public class SXPParameterContributorDefinitionResourceImpl
	extends BaseSXPParameterContributorDefinitionResourceImpl {

	@Override
	public Page<SXPParameterContributorDefinition>
			getSXPParameterContributorDefinitionsPage()
		throws Exception {

		return Page.of(
			transform(
				_sxpParameterContributorDefinitionProvider.
					getSXPParameterContributorDefinitions(
						contextCompany.getCompanyId(),
						contextAcceptLanguage.getPreferredLocale()),
				sxpParameterContributorDefinition ->
					new SXPParameterContributorDefinition() {
						{
							className =
								sxpParameterContributorDefinition.
									getClassName();
							description = LanguageUtil.get(
								contextAcceptLanguage.getPreferredLocale(),
								sxpParameterContributorDefinition.
									getLanguageKey());
							templateVariable =
								sxpParameterContributorDefinition.
									getTemplateVariable();
						}
					}));
	}

	@Reference
	private SXPParameterContributorDefinitionProvider
		_sxpParameterContributorDefinitionProvider;

}