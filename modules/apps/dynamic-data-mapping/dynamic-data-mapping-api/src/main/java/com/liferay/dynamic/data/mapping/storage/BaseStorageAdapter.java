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
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.dynamic.data.mapping.util.DocumentLibraryDDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.FormsDocumentLibraryDDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.HTMLSanitizerDDMFormFieldValueTransformer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author     Eduardo Lundgren
 * @author     Brian Wing Shun Chan
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public abstract class BaseStorageAdapter implements StorageAdapter {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			transformDDMFormValues(ddmFormValues, serviceContext);

			return doCreate(
				companyId, ddmStructureId, ddmFormValues, serviceContext);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void deleteByClass(long classPK) throws StorageException {
		try {
			doDeleteByClass(classPK);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		try {
			doDeleteByDDMStructure(ddmStructureId);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		try {
			return doGetDDMFormValues(classPK);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			transformDDMFormValues(ddmFormValues, serviceContext);

			doUpdate(classPK, ddmFormValues, serviceContext);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected abstract long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception;

	protected abstract void doDeleteByClass(long classPK) throws Exception;

	protected abstract void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception;

	protected abstract DDMFormValues doGetDDMFormValues(long classPK)
		throws Exception;

	protected abstract void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception;

	protected void transformDDMFormValues(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new DocumentLibraryDDMFormFieldValueTransformer());

		ddmFormValuesTransformer.addTransformer(
			new FormsDocumentLibraryDDMFormFieldValueTransformer());

		ddmFormValuesTransformer.addTransformer(
			new HTMLSanitizerDDMFormFieldValueTransformer(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				serviceContext.getUserId()));

		ddmFormValuesTransformer.transform();
	}

}