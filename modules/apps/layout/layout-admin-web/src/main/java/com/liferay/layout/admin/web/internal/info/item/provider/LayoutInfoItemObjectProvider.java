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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
	service = InfoItemObjectProvider.class
)
public class LayoutInfoItemObjectProvider
	implements InfoItemObjectProvider<Layout> {

	@Override
	public Layout getInfoItem(long classPK) throws NoSuchInfoItemException {
		try {
			return _layoutLocalService.getLayout(classPK);
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(
				"No layout found with PLID " + classPK, portalException);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}