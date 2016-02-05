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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.expando.kernel.model.ExpandoBridge;

/**
 * @author Alexander Chow
 */
public abstract class LiferayModel {

	public abstract long getCompanyId();

	public abstract ExpandoBridge getExpandoBridge();

	public abstract String getModelClassName();

	public abstract long getPrimaryKey();

}