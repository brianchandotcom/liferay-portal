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

package com.liferay.dynamic.data.mapping.demo.data.creator.internal;

import com.liferay.dynamic.data.mapping.demo.data.creator.DDMStructureDemoDataCreator;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = DDMStructureDemoDataCreator.class)
public class DDMStructureDemoDataCreatorImpl
	implements DDMStructureDemoDataCreator {

	@Override
	public DDMStructure create(long groupId, long userId)
		throws PortalException {

		String definition = StringUtil.read(
			DDMStructureDemoDataCreatorImpl.class,
			"dependencies/test-structure.xsd");

		DDMForm ddmForm = _toDDMForm(definition);

		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

		ServiceContext serviceContext = new ServiceContext();

		DDMStructure structure = _ddmStructureLocalService.addStructure(
			userId, groupId, 0,
			_portal.getClassNameId(DDMFormInstance.class.getName()), null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "Test Structure"
			).build(),
			null, ddmForm, ddmFormLayout, StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);

		_structureIds.add(structure.getStructureId());

		return structure;
	}

	@Override
	public void deleted() throws PortalException {
		for (Long structureId : _structureIds) {
			_structureIds.remove(structureId);

			_ddmStructureLocalService.deleteStructure(structureId);
		}
	}

	private DDMForm _toDDMForm(String definition) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	@Reference
	private DDM _ddm;

	@Reference(target = "(ddm.form.deserializer.type=xsd)")
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

	private final List<Long> _structureIds = new CopyOnWriteArrayList<>();

}