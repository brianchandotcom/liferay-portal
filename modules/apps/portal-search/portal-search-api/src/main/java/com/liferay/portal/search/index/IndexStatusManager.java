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

package com.liferay.portal.search.index;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface IndexStatusManager {

	/**
	 * Returns whether all model indexing is disabled.
	 *
	 * @return <code>true</code> if indexing is disabled; <code>false</code> otherwise
	 * @see com.liferay.portal.search.configuration.IndexStatusManagerConfiguration#indexReadOnly()
	 */
	public boolean isIndexReadOnly();

	/**
	 * Returns whether indexing is disabled for a given model.
	 *
	 * @param className the class name of the model
	 * @return <code>true</code> if indexing is disabled; <code>false</code> otherwise
	 * @see com.liferay.portal.search.configuration.IndexStatusManagerConfiguration#indexReadOnly()
	 */
	public boolean isIndexReadOnly(String className);

	/**
	 * Disables all model indexing.
	 *
	 * This is strongly discouraged as it may interfere with other running
	 * processes expecting newly created entities to be indexed.
	 *
	 * Instead, any processes requiring indexing to be off should run in a
	 * separate session with configuration in place to disable indexing.
	 *
	 * This API will be removed in a future version.
	 *
	 * @deprecated As of Athanasius (7.3.x)
	 * @param indexReadOnly <code>true</code> to disable or <code>false</code> to enable indexing
	 * @see com.liferay.portal.search.configuration.IndexStatusManagerConfiguration#indexReadOnly()
	 */
	@Deprecated
	public void setIndexReadOnly(boolean indexReadOnly);

	/**
	 * Disables indexing for a given model.
	 *
	 * This is strongly discouraged as it may interfere with other running
	 * processes expecting newly created entities to be indexed.
	 *
	 * Instead, any processes requiring indexing to be off should run in a
	 * separate session with configuration in place to disable indexing.
	 *
	 * This API will be removed in a future version.
	 *
	 * @deprecated As of Athanasius (7.3.x)
	 * @param className the class name of the model to disable indexing for
	 * @param indexReadOnly <code>true</code> to disable or <code>false</code> to enable indexing
	 * @see com.liferay.portal.search.configuration.IndexStatusManagerConfiguration#indexReadOnly()
	 */
	@Deprecated
	public void setIndexReadOnly(String className, boolean indexReadOnly);

}