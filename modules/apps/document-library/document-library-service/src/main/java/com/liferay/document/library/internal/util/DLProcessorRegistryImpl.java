/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.document.library.kernel.util.DLProcessorThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.framework.BundleContext;
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
		long fileEntryPreviewableProcessorMaxSize =
			_dlFileEntryConfiguration.previewableProcessorMaxSize();

		if ((fileEntryPreviewableProcessorMaxSize == 0) ||
			((fileEntryPreviewableProcessorMaxSize > 0) &&
			 (fileVersion.getSize() > fileEntryPreviewableProcessorMaxSize))) {

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

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws Exception {

		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);

		_dlProcessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DLProcessor.class, "type");
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_dlProcessorServiceTrackerMap.close();
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

	private static final Log _log = LogFactoryUtil.getLog(
		DLProcessorRegistryImpl.class);

	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;
	private ServiceTrackerMap<String, DLProcessor>
		_dlProcessorServiceTrackerMap;

}