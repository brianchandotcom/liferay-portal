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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public interface DDMStructureManager {

	public static final long STRUCTURE_DEFAULT_PARENT_STRUCTURE_ID = 0;

	public static final String STRUCTURE_DEFAULT_STORAGE_TYPE = "json";

	public static final int STRUCTURE_TYPE_DEFAULT = 0;

	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteStructure(long structureId) throws PortalException;

	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey);

	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId);

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end);

	public DDMStructure getStructure(long structureId) throws PortalException;

	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException;

	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException;

}