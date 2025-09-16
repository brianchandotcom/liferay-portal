/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public abstract class DataCleanupPreupgradeProcess extends UpgradeProcess {

	@Override
	public void upgrade() throws DataCleanupPreupgradeException {
		try {
			super.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new DataCleanupPreupgradeException(upgradeException);
		}
	}

	@Override
	public void upgrade(UpgradeProcess upgradeProcess)
		throws DataCleanupPreupgradeException {

		try {
			upgradeProcess.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new DataCleanupPreupgradeException(upgradeException);
		}
	}

	@SafeVarargs
	protected final List<UnsafeRunnable<Exception>> dependsOn(
		UnsafeRunnable<Exception>... unsafeRunnables) {

		return ListUtil.fromArray(unsafeRunnables);
	}

	protected void run(
			Map<UnsafeRunnable<Exception>, List<UnsafeRunnable<Exception>>>
				unsafeRunnableMap)
		throws Exception {

		List<UnsafeRunnable<Exception>> unsafeRunnableList = new ArrayList<>();

		while (unsafeRunnableList.size() != unsafeRunnableMap.size()) {
			int size = unsafeRunnableList.size();

			for (Map.Entry
					<UnsafeRunnable<Exception>, List<UnsafeRunnable<Exception>>>
						entry : unsafeRunnableMap.entrySet()) {

				UnsafeRunnable<Exception> unsafeRunnable = entry.getKey();

				if (unsafeRunnableList.contains(unsafeRunnable) ||
					!unsafeRunnableList.containsAll(entry.getValue())) {

					continue;
				}

				unsafeRunnable.run();

				unsafeRunnableList.add(unsafeRunnable);
			}

			if (size == unsafeRunnableList.size()) {
				throw new RuntimeException("Circular dependency");
			}
		}
	}

}