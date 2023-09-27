/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.timeline;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.spi.history.CTCollectionHistoryProvider;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Noor Najjar
 */
public class DefaultCTCollectionHistoryProvider<T>
	implements CTCollectionHistoryProvider<T> {

	@Override
	public List<CTCollection> getCTCollections(long classNameId, long classPK)
		throws PortalException {

		return CTCollectionLocalServiceUtil.getExclusivePublishedCTCollections(
			classNameId, classPK);
	}

	@Override
	public Class<T> getModelClass() {
		return null;
	}

}