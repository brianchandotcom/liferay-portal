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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.DDMXMLUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author André de Oliveira
 * @author Marcellus Tavares
 */
public class DDMStructureTestHelper {

	public static Map<Locale, String> getDefaultLocaleMap(String value) {
		Map<Locale, String> map = new HashMap<>();

		map.put(LocaleUtil.getSiteDefault(), value);

		return map;
	}

	public DDMStructureTestHelper(Group group) throws Exception {
		_classNameId = PortalUtil.getClassNameId(StringUtil.randomString());
		_group = group;
	}

	public DDMStructure addStructure(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout)
		throws Exception {

		return addStructure(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, _classNameId,
			null, "Test Structure", StringPool.BLANK, ddmForm, ddmFormLayout,
			StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT);
	}

	public DDMStructure addStructure(DDMForm ddmForm, String storageType)
		throws Exception {

		return addStructure(
			_classNameId, null, "Test Structure", ddmForm, storageType,
			DDMStructureConstants.TYPE_DEFAULT);
	}

	public DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String description, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type)
		throws Exception {

		return addStructure(
			parentStructureId, classNameId, structureKey, name, description,
			ddmForm, ddmFormLayout, storageType, type,
			WorkflowConstants.STATUS_APPROVED);
	}

	public DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String description, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			int status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute("status", status);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), _group.getGroupId(), parentStructureId,
			classNameId, structureKey, getDefaultLocaleMap(name),
			getDefaultLocaleMap(description), ddmForm, ddmFormLayout,
			storageType, type, serviceContext);
	}

	public DDMStructure addStructure(
			long classNameId, String structureKey, String name, DDMForm ddmForm,
			String storageType, int type)
		throws Exception {

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return addStructure(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, classNameId,
			structureKey, name, StringPool.BLANK, ddmForm, ddmFormLayout,
			storageType, type);
	}

	public DDMStructure addStructure(
			long classNameId, String structureKey, String name,
			String definition, String storageType, int type)
		throws Exception {

		DDMForm ddmForm = toDDMForm(definition);

		return addStructure(
			classNameId, structureKey, name, ddmForm, storageType, type);
	}

	public DDMForm toDDMForm(String definition) throws Exception {
		DDMXMLUtil.validateXML(definition);

		return DDMFormXSDDeserializerUtil.deserialize(definition);
	}

	private final long _classNameId;
	private final Group _group;

}