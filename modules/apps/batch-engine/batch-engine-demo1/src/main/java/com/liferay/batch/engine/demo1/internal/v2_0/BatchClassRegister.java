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

package com.liferay.batch.engine.demo1.internal.v2_0;

import com.liferay.batch.engine.ClassRegistry;
import com.liferay.batch.engine.demo1.internal.v2_0.model.Product;
import com.liferay.batch.engine.demo1.internal.v2_0.model.Sku;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = {})
public class BatchClassRegister {

	@Activate
	public void activate() {
		_classRegistry.register(Product.class, Sku.class);
	}

	@Deactivate
	public void deactivate() {
		_classRegistry.unregister(Product.class, Sku.class);
	}

	@Reference
	private ClassRegistry _classRegistry;

}