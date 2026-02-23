/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author David Truong
 */
public class CTRemoteModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTRemote> {

	public CTRemoteModelIndexerWriterContributor(
		CTRemoteLocalService ctRemoteLocalService) {

		_ctRemoteLocalService = ctRemoteLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ctRemoteLocalService.getIndexableActionableDynamicQuery();
	}

	private final CTRemoteLocalService _ctRemoteLocalService;

}