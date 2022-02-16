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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ObjectEntryDisplayContextFactory.class)
public class ObjectEntryDisplayContextFactory {

	public ObjectEntryDisplayContext create(
		HttpServletRequest httpServletRequest, boolean readOnly) {

		return new ObjectEntryDisplayContext(
			_ddmFormRenderer, httpServletRequest, _itemSelector,
			_objectDefinitionLocalService, _objectEntryService,
			_objectFieldBusinessTypeServicesTracker, _objectFieldLocalService,
			_objectLayoutLocalService, _objectRelationshipLocalService,
			readOnly);
	}

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldBusinessTypeServicesTracker
		_objectFieldBusinessTypeServicesTracker;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}