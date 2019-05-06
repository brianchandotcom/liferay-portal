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

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author     Eduardo Lundgren
 * @author     Brian Wing Shun Chan
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x), replaced by {@link DDMStorageAdapter}
 */
@Deprecated
public interface StorageAdapter {

	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException;

	public void deleteByClass(long classPK) throws StorageException;

	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException;

	public DDMFormValues getDDMFormValues(long classPK) throws StorageException;

	public String getStorageType();

	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException;

}