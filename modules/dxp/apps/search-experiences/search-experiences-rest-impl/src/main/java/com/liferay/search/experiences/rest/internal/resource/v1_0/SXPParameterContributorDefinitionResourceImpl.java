/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
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
							description = _language.get(
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
	private Language _language;

	@Reference
	private SXPParameterContributorDefinitionProvider
		_sxpParameterContributorDefinitionProvider;

}