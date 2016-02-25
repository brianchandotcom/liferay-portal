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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * Tracks database updates and flushes them from the session in batches.
 *
 * <p>
 * Although all database updates ultimately pass through this class, its
 * batching functionality is only used for large sets of contiguous updates. For
 * usage examples see {@link
 * com.liferay.portal.service.impl.LayoutLocalServiceImpl#importLayouts(long,
 * long, boolean, java.util.Map, java.io.File)}, and {@link
 * com.liferay.portal.verify.VerifyProcessUtil#verifyProcess(boolean, boolean,
 * boolean)}.
 * </p>
 *
 * @author     Raymond Augé
 * @author     Brian Wing Shun Chan
 * @deprecated As of 6.2.0, see LPS-30598.
 */
@Deprecated
public interface BatchSession {

	/**
	 * Deletes the model instance in the database, and possibly flushes the
	 * session.
	 *
	 * <p>
	 * The session will be flushed if one of the following is <code>true</code>:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Update batching is disabled
	 * </li>
	 * <li>
	 * The batch size is set to zero
	 * </li>
	 * <li>
	 * Enough updates and/or deletions have been queued to fill another batch
	 * </li>
	 * </ul>
	 *
	 * <p>
	 * The batch size may be set in portal.properties with the key
	 * <code>hibernate.jdbc.batch_size</code>.
	 * </p>
	 *
	 * @param session the session to perform the update on
	 * @param model the model instance to update
	 */
	public void delete(Session session, BaseModel<?> model) throws ORMException;

	/**
	 * Returns <code>true</code> if update batching is enabled
	 *
	 * @return <code>true</code> if update batching is enabled;
	 *         <code>false</code> otherwise
	 */
	public boolean isEnabled();

	/**
	 * Sets whether update batching is enabled.
	 *
	 * @param enabled whether update batching is enabled.
	 */
	public void setEnabled(boolean enabled);

	/**
	 * Updates the model instance in the database or adds it if it does not yet
	 * exist, and possibly flushes the session.
	 *
	 * <p>
	 * The session will be flushed if one of the following is <code>true</code>:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Update batching is disabled
	 * </li>
	 * <li>
	 * The batch size is set to zero
	 * </li>
	 * <li>
	 * Enough updates/or deletions have been queued to fill another batch
	 * </li>
	 * </ul>
	 *
	 * <p>
	 * The batch size may be set in portal.properties with the key
	 * <code>hibernate.jdbc.batch_size</code>.
	 * </p>
	 *
	 * <p>
	 * The <code>merge</code> parameter controls a special case of persistence.
	 * If the session that a model instance was originally loaded from is
	 * closed, that instance becomes &quot;detached&quot;, and changes to it
	 * will not be persisted automatically. To persist its changes, the detached
	 * instance must be merged with the current session. This will load a new
	 * copy of the model instance from the database, copy the changes to it, and
	 * persist it.
	 * </p>
	 *
	 * <p>
	 * This process is most commonly necessary if a model instance is modified
	 * in the controller or view, as the database session is closed when control
	 * leaves the model layer. However, local service update model methods use
	 * merging by default, so in most cases this nuance is handled
	 * automatically. See {@link
	 * com.liferay.portal.kernel.service.UserLocalService#updateUser(
	 * com.liferay.portal.kernel.model.User)} for an example.
	 * </p>
	 *
	 * @param session the session
	 * @param model the model instance
	 * @param merge whether to merge the model instance with the current session
	 */
	public void update(Session session, BaseModel<?> model, boolean merge)
		throws ORMException;

}