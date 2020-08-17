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

package com.liferay.change.tracking.spi.listener;

import com.liferay.change.tracking.spi.exception.CTEventException;

/**
 * CT event listeners are used for extending change tracking behavior into
 * additional types of storage. Examples include: file system, message bus, and
 * search index.
 *
 * @author Preston Crary
 */
public interface CTEventListener {

	/**
	 * Called after copying the source CTCollection into the target
	 * CTCollection.
	 *
	 * @param  sourceCTCollectionId the ctCollectionId for the source
	 *         CTCollection
	 * @param  targetCTCollectionId the ctCollectionId for the target
	 *         CTCollection
	 * @throws CTEventException if a exception occurred
	 */
	public default void onAfterCopy(
			long sourceCTCollectionId, long targetCTCollectionId)
		throws CTEventException {
	}

	/**
	 * Called after checking conflicts and publishing the CTCollection.
	 *
	 * @param  ctCollectionId belonging to the CTCollection being published
	 * @throws CTEventException if a exception occurred
	 */
	public default void onAfterPublish(long ctCollectionId)
		throws CTEventException {
	}

	/**
	 * Called before checking conflicts and publishing the CTCollection.
	 *
	 * @param  ctCollectionId belonging to the CTCollection being published
	 * @throws CTEventException if a exception occurred
	 */
	public default void onBeforePublish(long ctCollectionId)
		throws CTEventException {
	}

	/**
	 * Called before deleting a CTCollection.
	 *
	 * @param  ctCollectionId belonging to the CTCollection being deleted
	 * @throws CTEventException if a exception occurred
	 */
	public default void onBeforeRemove(long ctCollectionId)
		throws CTEventException {
	}

}