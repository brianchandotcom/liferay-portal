/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.function.UnsafeConsumer;

/**
 * @author István András Dézsi
 */
public abstract class PreupgradeVerifyProcess extends VerifyProcess {

	protected boolean isSkipDBPartitions() {
		return false;
	}

	@Override
	protected void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		if (isSkipDBPartitions()) {
			unsafeConsumer.accept(null);

			return;
		}

		super.process(unsafeConsumer);
	}

}