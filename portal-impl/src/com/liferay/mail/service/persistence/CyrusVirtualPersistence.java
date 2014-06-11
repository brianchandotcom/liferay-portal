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

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchCyrusVirtualException;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.BasePersistence;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public interface CyrusVirtualPersistence extends BasePersistence<Dummy> {

	public CyrusVirtual findByPrimaryKey(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException;

	public List<CyrusVirtual> findByUserId(long userId);

	public void remove(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException;

	public void removeByUserId(long userId);

	public void update(CyrusVirtual virtual);

}