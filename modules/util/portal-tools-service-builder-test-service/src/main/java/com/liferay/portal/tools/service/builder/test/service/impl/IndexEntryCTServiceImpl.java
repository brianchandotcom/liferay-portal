/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryPersistence;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@CTAware
public class IndexEntryCTServiceImpl implements CTService<IndexEntry> {

	@Override
	public CTPersistence<IndexEntry> getCTPersistence() {
		return _indexEntryPersistence;
	}

	@Override
	public Class<IndexEntry> getModelClass() {
		return IndexEntry.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<IndexEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_indexEntryPersistence);
	}

	@BeanReference(type = IndexEntryPersistence.class)
	private IndexEntryPersistence _indexEntryPersistence;

}