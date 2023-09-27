/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.store;

import com.liferay.document.library.kernel.store.Store;

/**
 * @author Shuyang Zhou
 */
public interface CTStoreFactory {

	public Store createCTStore(Store store, String storeType);

}