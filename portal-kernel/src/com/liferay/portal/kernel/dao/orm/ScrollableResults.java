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

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public interface ScrollableResults {

	public boolean first() throws ORMException;

	public Object[] get() throws ORMException;

	public Object get(int i) throws ORMException;

	public boolean last() throws ORMException;

	public boolean next() throws ORMException;

	public boolean previous() throws ORMException;

	public boolean scroll(int i) throws ORMException;

}