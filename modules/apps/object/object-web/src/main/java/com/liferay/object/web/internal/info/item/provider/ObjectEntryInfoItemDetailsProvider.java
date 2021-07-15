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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.object.model.ObjectEntry;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Guilherme Camacho
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class ObjectEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<ObjectEntry> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(ObjectEntry.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(ObjectEntry objectEntry) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				ObjectEntry.class.getName(), objectEntry.getObjectEntryId()));
	}

}