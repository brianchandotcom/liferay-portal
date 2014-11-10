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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldNameException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesTransformer;
import com.liferay.portlet.dynamicdatamapping.util.DocumentLibraryDDMFormFieldValueTransformer;
import com.liferay.portlet.dynamicdatamapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.portlet.dynamicdatamapping.util.HTMLSanitizerDDMFormFieldValueTransformer;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public abstract class BaseStorageAdapter implements StorageAdapter {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			validateDDMFormValues(ddmFormValues);

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
	public long create(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			validateDDMStructureFields(ddmStructureId, fields);

			fields = transformFields(ddmStructureId, fields, serviceContext);

			return doCreate(companyId, ddmStructureId, fields, serviceContext);
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
	public Fields getFields(long classPK) throws StorageException {
		return getFields(classPK, null);
	}

	@Override
	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		try {
			DDMStorageLink ddmStorageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			Map<Long, Fields> fieldsMapByClasses = getFieldsMap(
				ddmStorageLink.getStructureId(), new long[] {classPK},
				fieldNames);

			return fieldsMapByClasses.get(classPK);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames)
		throws StorageException {

		return getFieldsList(ddmStructureId, fieldNames, null);
	}

	@Override
	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		try {
			return doGetFieldsListByDDMStructure(
				ddmStructureId, fieldNames, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		try {
			return doGetFieldsListByClasses(
				ddmStructureId, classPKs, fieldNames, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		return getFieldsList(ddmStructureId, classPKs, null, orderByComparator);
	}

	@Override
	public Map<Long, Fields> getFieldsMap(long ddmStructureId, long[] classPKs)
		throws StorageException {

		return getFieldsMap(ddmStructureId, classPKs, null);
	}

	@Override
	public Map<Long, Fields> getFieldsMap(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		try {
			return doGetFieldsMapByClasses(
				ddmStructureId, classPKs, fieldNames);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public List<Fields> query(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator<Fields> orderByComparator)
		throws StorageException {

		try {
			return doQuery(
				ddmStructureId, fieldNames, condition, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public int queryCount(long ddmStructureId, Condition condition)
		throws StorageException {

		try {
			return doQueryCount(ddmStructureId, condition);
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
			validateDDMFormValues(ddmFormValues);

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

	@Override
	public void update(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			DDMStorageLink ddmStorageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			validateDDMStructureFields(ddmStorageLink.getStructureId(), fields);

			fields = transformFields(
				ddmStorageLink.getStructureId(), fields, serviceContext);

			doUpdate(classPK, fields, mergeFields, serviceContext);
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
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		update(classPK, fields, false, serviceContext);
	}

	protected long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
			ddmStructure, ddmFormValues);

		return doCreate(companyId, ddmStructureId, fields, serviceContext);
	}

	protected abstract long doCreate(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception;

	protected abstract void doDeleteByClass(long classPK) throws Exception;

	protected abstract void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception;

	protected DDMFormValues doGetDDMFormValues(long classPK) throws Exception {
		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

		DDMStructure ddmStructure = ddmStorageLink.getStructure();

		Fields fields = getFields(classPK);

		return FieldsToDDMFormValuesConverterUtil.convert(ddmStructure, fields);
	}

	protected abstract List<Fields> doGetFieldsListByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws Exception;

	protected abstract List<Fields> doGetFieldsListByDDMStructure(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
		throws Exception;

	protected abstract Map<Long, Fields> doGetFieldsMapByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws Exception;

	protected abstract List<Fields> doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator<Fields> orderByComparator)
		throws Exception;

	protected abstract int doQueryCount(
			long ddmStructureId, Condition condition)
		throws Exception;

	protected void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

		DDMStructure ddmStructure = ddmStorageLink.getStructure();

		Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
			ddmStructure, ddmFormValues);

		doUpdate(classPK, fields, false, serviceContext);
	}

	protected abstract void doUpdate(
			long classPK, Fields fields, boolean mergeFields,
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
			new HTMLSanitizerDDMFormFieldValueTransformer(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				serviceContext.getUserId()));

		ddmFormValuesTransformer.transform();
	}

	protected Fields transformFields(
			long ddmStructureId, Fields fields, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

		DDMFormValues ddmFormValues =
			FieldsToDDMFormValuesConverterUtil.convert(ddmStructure, fields);

		transformDDMFormValues(ddmFormValues, serviceContext);

		return DDMFormValuesToFieldsConverterUtil.convert(
			ddmStructure, ddmFormValues);
	}

	protected void validateDDMFormValues(DDMFormValues ddmFormValues)
		throws PortalException {

		DDMFormValuesValidatorUtil.validate(ddmFormValues);
	}

	protected void validateDDMStructureFields(
			long ddmStructureId, Fields fields)
		throws PortalException {

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

		for (Field field : fields) {
			if (!ddmStructure.hasField(field.getName())) {
				throw new StorageFieldNameException();
			}

			if (ddmStructure.getFieldRequired(field.getName()) &&
				Validator.isNull(field.getValue())) {

				throw new StorageFieldRequiredException();
			}
		}
	}

}