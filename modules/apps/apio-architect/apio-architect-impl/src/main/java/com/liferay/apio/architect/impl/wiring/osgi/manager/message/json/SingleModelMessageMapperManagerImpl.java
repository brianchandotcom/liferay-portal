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

package com.liferay.apio.architect.impl.wiring.osgi.manager.message.json;

import com.liferay.apio.architect.impl.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.impl.wiring.osgi.manager.base.MessageMapperBaseManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.cache.ManagerCache;

import java.util.Optional;

import javax.ws.rs.core.Request;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component
public class SingleModelMessageMapperManagerImpl
	extends MessageMapperBaseManager<SingleModelMessageMapper>
	implements SingleModelMessageMapperManager {

	public SingleModelMessageMapperManagerImpl() {
		super(
			SingleModelMessageMapper.class,
			ManagerCache.INSTANCE::putSingleModelMessageMapper);
	}

	@Override
	public <T> Optional<SingleModelMessageMapper<T>>
		getSingleModelMessageMapperOptional(Request request) {

		return ManagerCache.INSTANCE.getSingleModelMessageMapperOptional(
			request, this::computeMessageMappers);
	}

}