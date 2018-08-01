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

package com.liferay.expando.exportimport.internal.model.adapter.builder;

import com.liferay.expando.exportimport.internal.model.adapter.StagedExpandoTableImpl;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelAdapterBuilder.class)
public class StagedExpandoTableModelAdapterBuilder
	implements ModelAdapterBuilder<ExpandoTable, StagedExpandoTable> {

	@Override
	public StagedExpandoTable build(ExpandoTable expandoTable) {
		return new StagedExpandoTableImpl(expandoTable);
	}

}