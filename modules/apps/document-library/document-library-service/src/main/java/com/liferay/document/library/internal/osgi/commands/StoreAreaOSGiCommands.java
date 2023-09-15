/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.osgi.commands;

import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.util.PropsValues;

import java.time.Duration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.command.function=cleanUp", "osgi.command.scope=documentLibrary"
	},
	service = StoreAreaOSGiCommands.class
)
public class StoreAreaOSGiCommands {

	public void cleanUp(long companyId) {
		StoreAreaProcessor storeAreaProcessor = _serviceTracker.getService();

		if (storeAreaProcessor == null) {
			System.out.println(
				"Do nothing because the selected store " +
					PropsValues.DL_STORE_IMPL +
						" does not support store areas.");

			return;
		}

		storeAreaProcessor.cleanUpDeletedStoreArea(
			companyId, Integer.MAX_VALUE,
			name -> !_isDLFileVersionReferenced(companyId, name),
			StringPool.BLANK, Duration.ofSeconds(1));
		storeAreaProcessor.cleanUpNewStoreArea(
			companyId, Integer.MAX_VALUE,
			name -> !_isDLFileVersionReferenced(companyId, name),
			StringPool.BLANK, Duration.ofSeconds(1));
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", StoreAreaProcessor.class.getName(),
					")(store.type=", PropsValues.DL_STORE_IMPL, "))")),
			null);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private boolean _isDLFileVersionReferenced(Long companyId, String name) {
		int index = name.lastIndexOf(StringPool.TILDE);

		if (index == -1) {
			return true;
		}

		int count = _dlFileVersionLocalService.getFileVersionsCount(
			companyId, name.substring(index + 1));

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	private ServiceTracker<StoreAreaProcessor, StoreAreaProcessor>
		_serviceTracker;

}