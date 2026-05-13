/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.SearchTerm;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelSearchTermsPageResponse;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leslie Wong
 */
@Component(
	property = "dto.class.name=com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupSearchTermsPageResponse$Composition",
	service = DTOConverter.class
)
public class SearchTermDTOConverter
	implements DTOConverter
		<GetWorkspaceGroupChannelSearchTermsPageResponse.Composition,
		 SearchTerm> {

	@Override
	public String getContentType() {
		return SearchTerm.class.getSimpleName();
	}

	@Override
	public SearchTerm toDTO(
		DTOConverterContext dtoConverterContext,
		GetWorkspaceGroupChannelSearchTermsPageResponse.Composition
			composition) {

		if (composition == null) {
			return null;
		}

		return new SearchTerm() {
			{
				setCount(composition::getCount);
				setName(composition::getName);
			}
		};
	}

}