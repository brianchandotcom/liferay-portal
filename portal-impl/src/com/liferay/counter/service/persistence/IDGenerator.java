/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.counter.service.persistence;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.Serializable;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Patrick Brady
 */
public class IDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object) {
		try {
			String name = object.getClass().getName();

			int currentId = (int)CounterLocalServiceUtil.increment(name);

			return new Integer(currentId);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
	}

}