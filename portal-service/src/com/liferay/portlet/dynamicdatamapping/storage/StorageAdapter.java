/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public interface StorageAdapter {

	public long create(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException;

	public void deleteByClass(long classPK) throws StorageException;

	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException;

	public Fields getFields(long ddmStructureId, long classPK)
		throws StorageException;

	public Fields getFields(
			long ddmStructureId, long classPK, List<String> fieldNames)
		throws StorageException;

	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames)
		throws StorageException;

	public List<Fields> getFieldsList(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException;

	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException;

	public List<Fields> getFieldsList(
			long ddmStructureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException;

	public Map<Long, Fields> getFieldsMap(long ddmStructureId, long[] classPKs)
		throws StorageException;

	public Map<Long, Fields> getFieldsMap(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws StorageException;

	public List<Fields> query(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws StorageException;

	public int queryCount(long ddmStructureId, Condition condition)
		throws StorageException;

	public void update(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws StorageException;

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException;

}