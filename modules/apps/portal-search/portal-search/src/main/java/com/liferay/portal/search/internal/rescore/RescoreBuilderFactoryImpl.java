/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal.rescore;

import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.RescoreBuilder;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = RescoreBuilderFactory.class)
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