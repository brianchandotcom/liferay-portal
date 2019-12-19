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

package com.liferay.app.builder.internal.content.type;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "data.definition.content.type=app-builder",
	service = DataDefinitionContentType.class
)
public class AppBuilderDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(AppBuilderApp.class);
	}

	@Reference
	private Portal _portal;

}