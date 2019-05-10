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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMStructureManager {

	public static final int STRUCTURE_COMPARATOR_STRUCTURE_KEY = 0;

	public static final long STRUCTURE_DEFAULT_PARENT_STRUCTURE_ID = 0;

	public static final String STRUCTURE_INDEXER_FIELD_NAMESPACE = "ddm";

	public static final String STRUCTURE_INDEXER_FIELD_PREFIX =
		STRUCTURE_INDEXER_FIELD_NAMESPACE +
			DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR;

	public static final String STRUCTURE_INDEXER_FIELD_SEPARATOR =
		StringPool.DOUBLE_UNDERLINE;

	public static final int STRUCTURE_TYPE_AUTO = 1;

	public static final int STRUCTURE_TYPE_DEFAULT = 0;

	public static final String STRUCTURE_VERSION_DEFAULT = "1.0";

	public void addAttributes(
			long structureId, Document document, DDMFormValues ddmFormValues)
		throws PortalException;

	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException;

	public void deleteStructure(long structureId) throws PortalException;

	public String extractAttributes(
			long structureId, DDMFormValues ddmFormValues, Locale locale)
		throws PortalException;

	public DDMStructure fetchStructure(long structureId);

	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey);

	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId);

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId);

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int structureComparator);

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end);

	public JSONArray getDDMFormFieldsJSONArray(long structureId, String script)
		throws PortalException;

	public Class<?> getDDMStructureModelClass();

	public Serializable getIndexedFieldValue(
			Serializable fieldValue, String fieldType)
		throws Exception;

	public DDMStructure getStructure(long structureId) throws PortalException;

	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException;

	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	public List<DDMStructure> getStructures(long[] groupIds, long classNameId);

	public int getStructureStorageLinksCount(long structureId);

	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException;

	public void updateStructureDefinition(long structureId, String definition)
		throws PortalException;

	public void updateStructureKey(long structureId, String structureKey)
		throws PortalException;

}