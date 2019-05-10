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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Rafael Praxedes
 */
@ProviderType
public interface StorageEngineManager {

	public static final String STORAGE_TYPE_DEFAULT = "json";

	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteByClass(long classPK) throws PortalException;

	public DDMFormValues getDDMFormValues(long classPK) throws PortalException;

	public DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException;

	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException;

}