/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.dto.v1_0.converter;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentSet;
import com.liferay.headless.admin.user.dto.v1_0.Creator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.fragment.model.FragmentCollection",
	service = DTOConverter.class
)
public class FragmentSetDTOConverter
	implements DTOConverter<FragmentCollection, FragmentSet> {

	@Override
	public String getContentType() {
		return FragmentSet.class.getSimpleName();
	}

	@Override
	public FragmentSet toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentCollection fragmentCollection)
		throws Exception {

		return new FragmentSet() {
			{
				setCreator(
					() -> {
						User user = _userLocalService.fetchUser(
							fragmentCollection.getUserId());

						if (user == null) {
							return null;
						}

						return new Creator() {
							{
								setExternalReferenceCode(
									user::getExternalReferenceCode);
							}
						};
					});
				setDateCreated(fragmentCollection::getCreateDate);
				setDateModified(fragmentCollection::getModifiedDate);
				setDescription(fragmentCollection::getDescription);
				setExternalReferenceCode(
					fragmentCollection::getExternalReferenceCode);
				setKey(fragmentCollection::getFragmentCollectionKey);
				setMarketplace(fragmentCollection::isMarketplace);
				setName(fragmentCollection::getName);
			}
		};
	}

	@Reference
	private UserLocalService _userLocalService;

}