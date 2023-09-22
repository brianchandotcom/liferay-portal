/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLFileEntryConfigurationProvider;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.document.library.kernel.util.DLProcessorThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = DLProcessorRegistry.class)
public class DLProcessorRegistryImpl implements DLProcessorRegistry {

	@Override
	public void cleanUp(FileEntry fileEntry) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessorServiceTrackerMap.values()) {
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

		for (DLProcessor dlProcessor : _dlProcessorServiceTrackerMap.values()) {
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

		for (DLProcessor dlProcessor : _dlProcessorServiceTrackerMap.values()) {
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
	public long getPreviewableProcessorMaxSize(long groupId) {
		return _dlFileEntryConfigurationProvider.
			getGroupPreviewableProcessorMaxSize(groupId);
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

		for (DLProcessor dlProcessor : _dlProcessorServiceTrackerMap.values()) {
			if (dlProcessor.isSupported(fileVersion)) {
				dlProcessor.importGeneratedFiles(
					portletDataContext, fileEntry, importedFileEntry,
					fileEntryElement);
			}
		}
	}

	@Override
	public boolean isPreviewableSize(FileVersion fileVersion) {
		long previewableProcessorMaxSize =
			_dlFileEntryConfigurationProvider.
				getGroupPreviewableProcessorMaxSize(fileVersion.getGroupId());

		if ((previewableProcessorMaxSize == 0) ||
			((previewableProcessorMaxSize > 0) &&
			 (fileVersion.getSize() > previewableProcessorMaxSize))) {

			return false;
		}

		return true;
	}

	@Override
	public void register(DLProcessor dlProcessor) {
		Class<?>[] classes = ReflectionUtil.getInterfaces(dlProcessor);

		String[] classNames = new String[classes.length];

		for (int i = 0; i < classes.length; i++) {
			classNames[i] = classes[i].getName();
		}

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				classNames, dlProcessor,
				MapUtil.singletonDictionary("type", dlProcessor.getType()));

		ServiceRegistration<?> previousServiceRegistration =
			_serviceRegistrations.put(dlProcessor, serviceRegistration);

		if (previousServiceRegistration != null) {
			previousServiceRegistration.unregister();
		}
	}

	@Override
	public void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		trigger(fileEntry, fileVersion, false);
	}

	@Override
	public void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		if (!DLProcessorThreadLocal.isEnabled() || (fileEntry == null) ||
			(fileEntry.getSize() == 0)) {

			return;
		}

		FileVersion latestFileVersion = _getLatestFileVersion(
			fileEntry, trusted);

		if (latestFileVersion == null) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessorServiceTrackerMap.values()) {
			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.trigger(fileVersion, latestFileVersion);
			}
		}
	}

	@Override
	public void unregister(DLProcessor dlProcessor) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(dlProcessor);

		serviceRegistration.unregister();
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws Exception {

		_bundleContext = bundleContext;

		_dlProcessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DLProcessor.class, "type");

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
			_dlProcessors,
			dlProcessor -> {
				unregister(dlProcessor);

				dlProcessor.destroy();
			},
			Exception.class);

		_dlProcessors.clear();
	}

	private FileVersion _getLatestFileVersion(
		FileEntry fileEntry, boolean trusted) {

		try {
			return fileEntry.getLatestFileVersion(trusted);
		}
		catch (NoSuchFileEntryException | NoSuchFileVersionException
					exception) {

			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}

			return null;
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private static final Log _log = LogFactoryUtil.getLog(
		DLProcessorRegistryImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private DLFileEntryConfigurationProvider _dlFileEntryConfigurationProvider;

	private final List<DLProcessor> _dlProcessors = new ArrayList<>(
		_DL_FILE_ENTRY_PROCESSORS.length);
	private ServiceTrackerMap<String, DLProcessor>
		_dlProcessorServiceTrackerMap;
	private final Map<DLProcessor, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}