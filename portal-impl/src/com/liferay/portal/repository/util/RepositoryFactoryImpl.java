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

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.repository.BaseRepository;

/**
 * @author Mika Koivisto
 *
 * @deprecated As of 7.0.0, replaced by {@link com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl}
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	public RepositoryFactoryImpl(String className) {
		_externalRepositoryFactory = new ExternalRepositoryFactoryImpl(
			className);
	}

	public RepositoryFactoryImpl(String className, ClassLoader classLoader) {
		_externalRepositoryFactory = new ExternalRepositoryFactoryImpl(
			className, classLoader);
	}

	@Override
	public BaseRepository getInstance() throws Exception {
		return _externalRepositoryFactory.getInstance();
	}

	private ExternalRepositoryFactory _externalRepositoryFactory;

}