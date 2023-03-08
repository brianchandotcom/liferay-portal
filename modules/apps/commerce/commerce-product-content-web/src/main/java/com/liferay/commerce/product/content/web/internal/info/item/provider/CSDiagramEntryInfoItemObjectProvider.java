/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"item.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class CSDiagramEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<CSDiagramEntry> {

	@Override
	public CSDiagramEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		try {
			return _csDiagramEntryLocalService.getCSDiagramEntry(
				classPKInfoItemIdentifier.getClassPK());
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(
				"Unable to get commerce shop by diagram entry " +
					classPKInfoItemIdentifier.getClassPK(),
				portalException);
		}
	}

	@Override
	public CSDiagramEntry getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		return getInfoItem(classPKInfoItemIdentifier);
	}

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

}