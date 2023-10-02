/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
public abstract class BaseIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		if (_shouldCreateIndexes(
				(boolean)properties.get("productionModeEnabled"))) {

			_companyLocalService.forEachCompanyId(
				companyId -> createIndex(companyId));
		}
	}

	protected abstract void createIndex(long companyId);

	private boolean _shouldCreateIndexes(boolean productionModeEnabled) {
		File dataFile = _bundleContext.getDataFile(
			"elasticsearch_configuration_production_mode_enabled.data");

		if (dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(dataFile)));

				if (deserializer.readBoolean() == productionModeEnabled) {
					return false;
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

		try (OutputStream outputStream = new FileOutputStream(dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update Elasticsearch configuration", exception);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexPortalInstanceLifecycleListener.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

}