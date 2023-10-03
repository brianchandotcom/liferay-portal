/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.creation.instance.lifecycle;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;

/**
 * @author Bryan Engler
 */
public abstract class BaseIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public long getLastModifiedTime() {
		return _dataFile.lastModified();
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		boolean productionModeEnabled = GetterUtil.getBoolean(
			properties.get("productionModeEnabled"));

		Class<?> clazz = getClass();

		_dataFile = bundleContext.getDataFile(clazz.getSimpleName() + ".data");

		if (_dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(_dataFile)));

				if (deserializer.readBoolean() == productionModeEnabled) {
					return;
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to read Elasticsearch configuration",
						exception);
				}
			}
		}

		Serializer serializer = new Serializer();

		serializer.writeBoolean(productionModeEnabled);

		try (OutputStream outputStream = new FileOutputStream(_dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update Elasticsearch configuration", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexPortalInstanceLifecycleListener.class);

	private File _dataFile;

}