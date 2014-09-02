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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class DefaultRepositoryInstanceDefinition
	implements CapabilityRegistry, RepositoryInstanceDefinition {

	public DefaultRepositoryInstanceDefinition(
		DocumentRepository documentRepository) {

		_documentRepository = documentRepository;

		_exportedCapabilities = new HashSet<Class<? extends Capability>>();

		_supportedCapabilities =
			new HashMap<Class<? extends Capability>, Capability>();
	}

	@Override
	public <S extends Capability, T extends S> void addExportedCapability(
		Class<S> capabilityClass, T capability) {

		_exportedCapabilities.add(capabilityClass);

		addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability, T extends S> void addSupportedCapability(
		Class<S> capabilityClass, T capability) {

		_supportedCapabilities.put(capabilityClass, capability);
	}

	@Override
	public DocumentRepository getDocumentRepository() {
		return _documentRepository;
	}

	@Override
	public Set<Class<? extends Capability>> getExportedCapabilities() {
		return _exportedCapabilities;
	}

	@Override
	public Map<Class<? extends Capability>, Capability>
		getSupportedCapabilities() {

		return _supportedCapabilities;
	}

	private DocumentRepository _documentRepository;
	private Set<Class<? extends Capability>> _exportedCapabilities;
	private Map<Class<? extends Capability>, Capability> _supportedCapabilities;

}