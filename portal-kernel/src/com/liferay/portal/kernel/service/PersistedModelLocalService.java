/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public interface PersistedModelLocalService {

	public default PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public <T> T dslQuery(DSLQuery dslQuery);

	public int dslQueryCount(DSLQuery dslQuery);

	public default BasePersistence<?> getBasePersistence() {
		throw new UnsupportedOperationException();
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

}