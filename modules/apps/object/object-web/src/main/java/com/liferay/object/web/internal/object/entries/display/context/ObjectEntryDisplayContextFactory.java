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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.object.display.context.ObjectEntryDisplayContext;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
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
		HttpServletRequest httpServletRequest) {

		return new ObjectEntryDisplayContextImpl(
			_ddmExpressionFactory, _ddmFormRenderer, httpServletRequest,
			_itemSelector, _objectDefinitionLocalService,
			_objectEntryManagerRegistry, _objectEntryLocalService,
			_objectEntryService, _objectFieldBusinessTypeRegistry,
			_objectFieldLocalService, _objectLayoutLocalService,
			_objectRelationshipLocalService, _objectScopeProviderRegistry);
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}