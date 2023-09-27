/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0.util;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.change.tracking.store.CTStoreFactory;

/**
 * @author Shuyang Zhou
 */
public class DummyCTStoreFactoryImpl implements CTStoreFactory {

	@Override
	public Store createCTStore(Store store, String storeType) {
		return store;
	}

}