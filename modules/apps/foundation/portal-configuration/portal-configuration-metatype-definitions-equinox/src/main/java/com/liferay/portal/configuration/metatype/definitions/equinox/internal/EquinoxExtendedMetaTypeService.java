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

package com.liferay.portal.configuration.metatype.definitions.equinox.internal;

import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Iván Zaera
 */
//@Component(service = ExtendedMetaTypeService.class)
public class EquinoxExtendedMetaTypeService implements ExtendedMetaTypeService {

	@Override
	public ExtendedMetaTypeInformation getMetaTypeInformation(Bundle bundle) {
		return new EquinoxExtendedMetaTypeInformation(
			_metaTypeService.getMetaTypeInformation(bundle));
	}

	@Reference(unbind = "-")
	protected void setMetaTypeService(MetaTypeService metaTypeService) {
		_metaTypeService = metaTypeService;
	}

	private MetaTypeService _metaTypeService;

}