/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.rescore;

import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.RescoreBuilder;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = RescoreBuilderFactory.class)
public class RescoreBuilderFactoryImpl implements RescoreBuilderFactory {

	@Override
	public RescoreBuilder builder(Query query) {
		return new RescoreBuilderImpl(query);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #builder(Query)}
	 */
	@Deprecated
	@Override
	public RescoreBuilder getRescoreBuilder() {
		return new RescoreBuilderImpl(null);
	}

}