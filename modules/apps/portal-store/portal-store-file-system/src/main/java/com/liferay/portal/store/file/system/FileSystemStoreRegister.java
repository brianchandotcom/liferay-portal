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

package com.liferay.portal.store.file.system;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.convert.documentlibrary.FileSystemStoreRootDirException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.file.system.configuration.FileSystemStoreConfiguration;
import com.liferay.portal.store.file.system.safe.file.name.SafeFileNameStore;

import java.io.File;

import java.util.Map;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.store.file.system.configuration.FileSystemStoreConfiguration",
	service = {}
)
public class FileSystemStoreRegister {

	@Activate
	public FileSystemStoreRegister(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_fileSystemStoreConfiguration = ConfigurableUtil.createConfigurable(
			FileSystemStoreConfiguration.class, properties);

		if (Validator.isBlank(_fileSystemStoreConfiguration.rootDir())) {
			throw new IllegalArgumentException(
				"File system root directory is not set",
				new FileSystemStoreRootDirException());
		}

		_serviceRegistration = _bundleContext.registerService(
			Store.class,
			new SafeFileNameStore(
				new FileSystemStore(_fileSystemStoreConfiguration)),
			MapUtil.singletonDictionary(
				"store.type", FileSystemStore.class.getName()));
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(file.system.store=default)"
	)
	protected void addInitializer(Consumer<File> initializer) {
		if (_serviceRegistration != null) {
			try {
				_serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {

				// This can be safely ignored

			}
		}

		_serviceRegistration = _bundleContext.registerService(
			Store.class,
			new SafeFileNameStore(
				new FileSystemStore(
					_fileSystemStoreConfiguration, initializer)),
			MapUtil.singletonDictionary(
				"store.type", FileSystemStore.class.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	protected void removeInitializer(Consumer<File> initializer) {
		try {
			_serviceRegistration.unregister();
		}
		catch (IllegalStateException illegalStateException) {

			// This can be safely ignored

		}

		_serviceRegistration = _bundleContext.registerService(
			Store.class,
			new SafeFileNameStore(
				new FileSystemStore(_fileSystemStoreConfiguration)),
			MapUtil.singletonDictionary(
				"store.type", FileSystemStore.class.getName()));
	}

	private final BundleContext _bundleContext;
	private final FileSystemStoreConfiguration _fileSystemStoreConfiguration;
	private volatile ServiceRegistration<Store> _serviceRegistration;

}