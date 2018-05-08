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

package com.liferay.apio.architect.wiring.osgi.internal.manager.message.json;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.MessageMapperBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.message.json.DocumentationMessageMapperManager;

import java.util.Optional;

import javax.ws.rs.core.Request;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class DocumentationMessageMapperManagerImpl
	extends MessageMapperBaseManager<DocumentationMessageMapper>
	implements DocumentationMessageMapperManager {

	public DocumentationMessageMapperManagerImpl() {
		super(
			DocumentationMessageMapper.class,
			INSTANCE::putDocumentationMessageMapper);
	}

	@Override
	public Optional<DocumentationMessageMapper>
		getDocumentationMessageMapperOptional(Request request) {

		return INSTANCE.getDocumentationMessageMapperOptional(
			request, this::computeMessageMappers);
	}

	@Override
	protected Optional<ApioLogger> getLoggerOptional() {
		return Optional.ofNullable(_apioLogger);
	}

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private ApioLogger _apioLogger;

}