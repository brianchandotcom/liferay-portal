/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.spi.history;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.List;

/**
 * @author Noor Najjar
 */
public interface CTCollectionHistoryProvider<T> {

	public List<CTCollection> getCTCollections(long classNameId, long classPK)
		throws PortalException;

	public CTEntry getCTEntry(
		long ctCollectionId, long modelClassNameId, long modelClassPK);

	public Class<T> getModelClass();

	public default UnsafeConsumer<SearchUtil.SearchContext, Exception>
		getSearchContextUnsafeConsumer(long classNameId, long classPK) {

		return searchContext -> {
			searchContext.setAttribute(
				"modelClassNameId", new Long[] {classNameId});

			if (classPK > 0) {
				searchContext.setAttribute(
					"modelClassPK", new Long[] {classPK});
			}
		};
	}

}