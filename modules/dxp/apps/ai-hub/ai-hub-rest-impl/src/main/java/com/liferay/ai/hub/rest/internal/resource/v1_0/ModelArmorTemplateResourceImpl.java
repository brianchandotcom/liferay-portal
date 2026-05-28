/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.ModelArmorTemplate;
import com.liferay.ai.hub.rest.manager.v1_0.ModelArmorTemplateManager;
import com.liferay.ai.hub.rest.resource.v1_0.ModelArmorTemplateResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/model-armor-template.properties",
	scope = ServiceScope.PROTOTYPE, service = ModelArmorTemplateResource.class
)
public class ModelArmorTemplateResourceImpl
	extends BaseModelArmorTemplateResourceImpl {

	@Override
	public void deleteModelArmorTemplateByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		_modelArmorTemplateManager.deleteModelArmorTemplate(
			contextCompany.getCompanyId(),
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			externalReferenceCode);
	}

	@Override
	public ModelArmorTemplate postModelArmorTemplate(
			ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _modelArmorTemplateManager.postModelArmorTemplate(
			contextCompany.getCompanyId(),
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			modelArmorTemplate);
	}

	@Override
	public ModelArmorTemplate putModelArmorTemplateByExternalReferenceCode(
			String externalReferenceCode, ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _modelArmorTemplateManager.putModelArmorTemplate(
			contextCompany.getCompanyId(),
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			externalReferenceCode, modelArmorTemplate);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ModelArmorTemplateManager _modelArmorTemplateManager;

}