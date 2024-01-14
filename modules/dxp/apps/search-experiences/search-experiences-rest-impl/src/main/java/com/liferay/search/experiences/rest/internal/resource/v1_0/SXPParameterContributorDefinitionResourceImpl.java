/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorProvider;
import com.liferay.search.experiences.rest.dto.v1_0.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.resource.v1_0.SXPParameterContributorDefinitionResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
				_getSXPParameterContributorDefinitions(
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale()),
				sxpParameterContributorDefinition ->
					new SXPParameterContributorDefinition() {
						{
							setClassName(
								() ->
									sxpParameterContributorDefinition.
										getClassName());
							setDescription(
								() -> _language.get(
									contextAcceptLanguage.getPreferredLocale(),
									sxpParameterContributorDefinition.
										getLanguageKey()));
							setTemplateVariable(
								() ->
									sxpParameterContributorDefinition.
										getTemplateVariable());
						}
					}));
	}

	private List
		<com.liferay.search.experiences.blueprint.parameter.contributor.
			SXPParameterContributorDefinition>
				_getSXPParameterContributorDefinitions(
					long companyId, Locale locale) {

		if (ArrayUtil.isEmpty(
				_sxpParameterContributorProvider.
					getSxpParameterContributors())) {

			return Collections.emptyList();
		}

		List
			<com.liferay.search.experiences.blueprint.parameter.contributor.
				SXPParameterContributorDefinition>
					sxpParameterContributorDefinitions = new ArrayList<>();

		for (SXPParameterContributor sxpParameterContributor :
				_sxpParameterContributorProvider.
					getSxpParameterContributors()) {

			sxpParameterContributorDefinitions.addAll(
				sxpParameterContributor.getSXPParameterContributorDefinitions(
					companyId, locale));
		}

		return sxpParameterContributorDefinitions;
	}

	@Reference
	private Language _language;

	@Reference
	private SXPParameterContributorProvider _sxpParameterContributorProvider;

}