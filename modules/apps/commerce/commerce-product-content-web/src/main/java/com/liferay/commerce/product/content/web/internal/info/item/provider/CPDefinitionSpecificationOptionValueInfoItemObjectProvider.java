/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"item.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class CPDefinitionSpecificationOptionValueInfoItemObjectProvider
	implements InfoItemObjectProvider<CPDefinitionSpecificationOptionValue> {

	@Override
	public CPDefinitionSpecificationOptionValue getInfoItem(
			InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		try {
			return _cpDefinitionSpecificationOptionValueLocalService.
				getCPDefinitionSpecificationOptionValue(
					classPKInfoItemIdentifier.getClassPK());
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(
				"Unable to get commerce product definition specification " +
					"option value " + classPKInfoItemIdentifier.getClassPK(),
				portalException);
		}
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

}