/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.spi.history;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Noor Najjar
 */
public interface CTCollectionHistoryProvider<T> {

	public List<CTCollection> getCTCollections(long classNameId, long classPK)
		throws PortalException;

	public Class<T> getModelClass();

}