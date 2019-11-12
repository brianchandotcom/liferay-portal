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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.document.library.kernel.util.DLProcessorThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	service = DLProcessorRegistry.class
)
public class DLProcessorRegistryImpl implements DLProcessorRegistry {

	@Override
	public void cleanUp(FileEntry fileEntry) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		Iterable<String> dlProcessorTypes =
			_dlProcessorServiceTrackerMap.keySet();

		for (String dlProcessorType : dlProcessorTypes) {
			DLProcessor dlProcessor = _dlProcessorServiceTrackerMap.getService(
				dlProcessorType);

			if (dlProcessor.isSupported(fileEntry.getMimeType())) {
				dlProcessor.cleanUp(fileEntry);
			}
		}
	}

	@Override
	public void cleanUp(FileVersion fileVersion) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		Iterable<String> dlProcessorTypes =
			_dlProcessorServiceTrackerMap.keySet();

		for (String dlProcessorType : dlProcessorTypes) {
			DLProcessor dlProcessor = _dlProcessorServiceTrackerMap.getService(
				dlProcessorType);

			if (dlProcessor.isSupported(fileVersion)) {
				dlProcessor.cleanUp(fileVersion);
			}
		}
	}

	@Override
	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		if ((fileEntry == null) || (fileEntry.getSize() == 0)) {
			return;
		}

		FileVersion latestFileVersion = _getLatestFileVersion(fileEntry, true);

		if (latestFileVersion == null) {
			return;
		}

		Iterable<String> dlProcessorTypes =
			_dlProcessorServiceTrackerMap.keySet();

		for (String dlProcessorType : dlProcessorTypes) {
			DLProcessor dlProcessor = _dlProcessorServiceTrackerMap.getService(
				dlProcessorType);

			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.exportGeneratedFiles(
					portletDataContext, fileEntry, fileEntryElement);
			}
		}
	}

	@Override
	public DLProcessor getDLProcessor(String dlProcessorType) {
		return _dlProcessorServiceTrackerMap.getService(dlProcessorType);
	}

	@Override
	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		if ((importedFileEntry == null) || (importedFileEntry.getSize() == 0)) {
			return;
		}

		FileVersion fileVersion = importedFileEntry.getFileVersion();

		if (fileVersion == null) {
			return;
		}

		Iterable<String> dlProcessorTypes =
			_dlProcessorServiceTrackerMap.keySet();

		for (String dlProcessorType : dlProcessorTypes) {
			DLProcessor dlProcessor = _dlProcessorServiceTrackerMap.getService(
				dlProcessorType);

			if (dlProcessor.isSupported(fileVersion)) {
				dlProcessor.importGeneratedFiles(
					portletDataContext, fileEntry, importedFileEntry,
					fileEntryElement);
			}
		}
	}

	@Override
	public boolean isPreviewableSize(FileVersion fileVersion) {
		long fileEntryPreviewableProcessorMaxSize =
			_dlFileEntryConfiguration.previewableProcessorMaxSize();

		if (fileEntryPreviewableProcessorMaxSize == 0) {
			return false;
		}

		if ((fileEntryPreviewableProcessorMaxSize > 0) &&
			(fileVersion.getSize() > fileEntryPreviewableProcessorMaxSize)) {

			return false;
		}

		return true;
	}

	@Modified
	public void modified(Map<String, Object> properties) {
		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	@Override
	public void register(DLProcessor dlProcessor) {
		ServiceRegistration<DLProcessor> previousServiceRegistration =
			_serviceRegistrations.remove(dlProcessor.getType());

		if (previousServiceRegistration != null) {
			previousServiceRegistration.unregister();
		}

		ServiceRegistration<DLProcessor> serviceRegistration =
			_bundleContext.registerService(
				DLProcessor.class, dlProcessor,
				new HashMapDictionary<String, Object>());

		_serviceRegistrations.put(dlProcessor.getType(), serviceRegistration);
	}

	@Override
	public void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		trigger(fileEntry, fileVersion, false);
	}

	@Override
	public void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		if ((fileEntry == null) || (fileEntry.getSize() == 0)) {
			return;
		}

		FileVersion latestFileVersion = _getLatestFileVersion(
			fileEntry, trusted);

		if (latestFileVersion == null) {
			return;
		}

		Iterable<String> dlProcessorTypes =
			_dlProcessorServiceTrackerMap.keySet();

		for (String dlProcessorType : dlProcessorTypes) {
			DLProcessor dlProcessor = _dlProcessorServiceTrackerMap.getService(
				dlProcessorType);

			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.trigger(fileVersion, latestFileVersion);
			}
		}
	}

	@Override
	public void unregister(DLProcessor dlProcessor) {
		ServiceRegistration<DLProcessor> serviceRegistration =
			_serviceRegistrations.remove(dlProcessor.getType());

		serviceRegistration.unregister();
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws Exception {

		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);

		_bundleContext = bundleContext;

		_dlProcessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DLProcessor.class, null,
				new ServiceReferenceMapper<String, DLProcessor>() {

					@Override
					public void map(
						ServiceReference<DLProcessor> serviceReference,
						Emitter<String> emitter) {

						DLProcessor dlProcessor = _bundleContext.getService(
							serviceReference);

						try {
							emitter.emit(dlProcessor.getType());
						}
						finally {
							_bundleContext.ungetService(serviceReference);
						}
					}

				});

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstanceFactory.newInstance(
				classLoader, dlProcessorClassName);

			dlProcessor.afterPropertiesSet();

			register(dlProcessor);

			_dlProcessors.add(dlProcessor);
		}
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_dlProcessorServiceTrackerMap.close();

		UnsafeConsumer.accept(
			_dlProcessors, DLProcessor::destroy, Exception.class);
	}

	private FileVersion _getLatestFileVersion(
		FileEntry fileEntry, boolean trusted) {

		try {
			return fileEntry.getLatestFileVersion(trusted);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isInfoEnabled()) {
				_log.info(nsfee, nsfee);
			}

			return null;
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private static final Log _log = LogFactoryUtil.getLog(
		DLProcessorRegistryImpl.class);

	private BundleContext _bundleContext;
	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;
	private final List<DLProcessor> _dlProcessors = new ArrayList<>();
	private ServiceTrackerMap<String, DLProcessor>
		_dlProcessorServiceTrackerMap;
	private final Map<String, ServiceRegistration<DLProcessor>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}