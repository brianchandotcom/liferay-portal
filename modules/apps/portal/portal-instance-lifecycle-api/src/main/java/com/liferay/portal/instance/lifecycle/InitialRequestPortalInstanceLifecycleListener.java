/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instance.lifecycle;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.InitialRequestSyncUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public abstract class InitialRequestPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_portalInstanceRegisteredUnsafeConsumer.accept(company.getCompanyId());
	}

	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		Class<?> clazz = getClass();

		_dataFile = _bundleContext.getDataFile(clazz.getName() + ".data");

		_loadCompanyIds();

		for (long companyId : _companyIds) {
			_registerPortalInstanceRegisteredSyncCallable(companyId);
		}
	}

	protected abstract void doPortalInstanceRegistered(long companyId)
		throws Exception;

	private void _loadCompanyIds() {
		if (_dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(_dataFile)));

				Bundle bundle = _bundleContext.getBundle();

				if (deserializer.readLong() == bundle.getLastModified()) {
					int size = deserializer.readInt();

					for (int i = 0; i < size; i++) {
						_companyIds.add(deserializer.readLong());
					}
				}
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to load company ids", ioException);
				}
			}
		}
	}

	private void _registerPortalInstanceRegisteredSyncCallable(long companyId) {
		if (_companyIds.add(companyId)) {
			_saveCompanyIds(_companyIds);
		}

		InitialRequestSyncUtil.registerSyncCallable(
			() -> {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(companyId)) {

					doPortalInstanceRegistered(companyId);
				}
				finally {
					if (_companyIds.remove(companyId)) {
						_saveCompanyIds(_companyIds);
					}

					_portalInstanceRegisteredUnsafeConsumer =
						this::doPortalInstanceRegistered;
				}

				return null;
			});
	}

	private void _saveCompanyIds(Set<Long> companyIds) {
		if (companyIds.isEmpty()) {
			_dataFile.delete();

			return;
		}

		Bundle bundle = _bundleContext.getBundle();

		Serializer serializer = new Serializer();

		serializer.writeLong(bundle.getLastModified());

		serializer.writeInt(companyIds.size());

		for (long companyId : companyIds) {
			serializer.writeLong(companyId);
		}

		try (OutputStream outputStream = new FileOutputStream(_dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to write company IDs", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InitialRequestPortalInstanceLifecycleListener.class);

	private BundleContext _bundleContext;
	private final Set<Long> _companyIds = new HashSet<>();
	private File _dataFile;
	private volatile UnsafeConsumer<Long, Exception>
		_portalInstanceRegisteredUnsafeConsumer =
			this::_registerPortalInstanceRegisteredSyncCallable;

}