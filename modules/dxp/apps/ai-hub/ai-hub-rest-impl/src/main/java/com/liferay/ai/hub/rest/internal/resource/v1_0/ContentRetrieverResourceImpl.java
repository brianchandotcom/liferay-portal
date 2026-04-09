/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.ContentRetriever;
import com.liferay.ai.hub.rest.manager.v1_0.ContentRetrieverManager;
import com.liferay.ai.hub.rest.resource.v1_0.ContentRetrieverResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/content-retriever.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentRetrieverResource.class
)
public class ContentRetrieverResourceImpl
	extends BaseContentRetrieverResourceImpl {

	@Override
	public void deleteContentRetrieverByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		_contentRetrieverManager.deleteContentRetriever(
			contextCompany.getCompanyId(),
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			externalReferenceCode);
	}

	@Override
	public ContentRetriever putContentRetrieverByExternalReferenceCode(
			String externalReferenceCode, ContentRetriever contentRetriever)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _contentRetrieverManager.putContentRetriever(
			contextCompany.getCompanyId(), contentRetriever,
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			externalReferenceCode);
	}

	@Reference
	private ContentRetrieverManager _contentRetrieverManager;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}