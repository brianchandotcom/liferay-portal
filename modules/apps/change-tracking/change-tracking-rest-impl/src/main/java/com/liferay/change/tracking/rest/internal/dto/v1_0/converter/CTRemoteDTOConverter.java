/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.rest.dto.v1_0.CTRemote;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "dto.class.name=com.liferay.change.tracking.model.CTRemote",
	service = DTOConverter.class
)
public class CTRemoteDTOConverter
	implements DTOConverter
		<com.liferay.change.tracking.model.CTRemote, CTRemote> {

	@Override
	public String getContentType() {
		return CTRemote.class.getSimpleName();
	}

	@Override
	public CTRemote toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTRemote ctRemote)
		throws Exception {

		if (ctRemote == null) {
			return null;
		}

		return new CTRemote() {
			{
				actions = dtoConverterContext.getActions();
				clientId = ctRemote.getClientId();
				clientSecret = ctRemote.getClientSecret();
				dateCreated = ctRemote.getCreateDate();
				dateModified = ctRemote.getModifiedDate();
				description = ctRemote.getDescription();
				id = ctRemote.getCtRemoteId();
				name = ctRemote.getName();
				ownerName = ctRemote.getUserName();
				url = ctRemote.getUrl();
			}
		};
	}

}