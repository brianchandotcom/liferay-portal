/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyCloudStore extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (!(StringUtil.equals(PropsValues.DL_STORE_IMPL, _AZURE_STORE) ||
			  StringUtil.equals(PropsValues.DL_STORE_IMPL, _GCS_STORE) ||
			  StringUtil.equals(PropsValues.DL_STORE_IMPL, _S3_STORE) ||
			  StringUtil.equals(PropsValues.DL_STORE_IMPL, _IBM_S3_STORE)) ||
			StartupHelperUtil.isDBNew()) {

			return;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<Store>> serviceReferences =
			bundleContext.getServiceReferences(
				Store.class, "(store.type=" + PropsValues.DL_STORE_IMPL + ")");

		Store store = null;

		for (ServiceReference<Store> serviceReference : serviceReferences) {
			store = bundleContext.getService(serviceReference);

			break;
		}

		if (store == null) {
			throw new VerifyException(
				"The remote store object could not be retrieved");
		}

		store.addFile(
			12345, 12345, "test", Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(new byte[1024 * 65]));

		if (!store.hasFile(12345, 12345, "test", Store.VERSION_DEFAULT)) {
			throw new VerifyException(
				"Failed to create file on remote storage");
		}

		store.deleteFile(12345, 12345, "test", Store.VERSION_DEFAULT);
	}

	@Override
	protected boolean isSkipDBPartitions() {
		return true;
	}

	private static final String _AZURE_STORE =
		"com.liferay.portal.store.azure.AzureStore";

	private static final String _GCS_STORE =
		"com.liferay.portal.store.gcs.GCSStore";

	private static final String _IBM_S3_STORE =
		"com.liferay.portal.store.s3.IBMS3Store";

	private static final String _S3_STORE =
		"com.liferay.portal.store.s3.S3Store";

}