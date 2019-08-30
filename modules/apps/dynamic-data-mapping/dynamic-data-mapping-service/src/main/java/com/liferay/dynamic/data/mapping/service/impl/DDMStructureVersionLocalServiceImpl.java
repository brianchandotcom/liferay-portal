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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureVersionException;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.StructureVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureVersion",
	service = AopService.class
)
public class DDMStructureVersionLocalServiceImpl
	extends DDMStructureVersionLocalServiceBaseImpl {

	@Override
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException {

		List<DDMStructureVersion> structureVersions =
			ddmStructureVersionPersistence.findByStructureId(structureId);

		if (structureVersions.isEmpty()) {
			throw new NoSuchStructureVersionException(
				"No structure versions found for structure ID " + structureId);
		}

		structureVersions = ListUtil.copy(structureVersions);

		Collections.sort(
			structureVersions, new StructureVersionVersionComparator());

		return structureVersions.get(0);
	}

	@Override
	public DDMStructureVersion getStructureVersion(long structureVersionId)
		throws PortalException {

		return ddmStructureVersionPersistence.findByPrimaryKey(
			structureVersionId);
	}

	@Override
	public DDMStructureVersion getStructureVersion(
			long structureId, String version)
		throws PortalException {

		return ddmStructureVersionPersistence.findByS_V(structureId, version);
	}

	@Override
	@Transactional(enabled = false)
	public DDMForm getStructureVersionDDMForm(
			DDMStructureVersion structureVersion)
		throws PortalException {

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				structureVersion.getDefinition());

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(long structureId) {
		return ddmStructureVersionPersistence.findByStructureId(structureId);
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return ddmStructureVersionPersistence.findByStructureId(
			structureId, start, end, orderByComparator);
	}

	@Override
	public int getStructureVersionsCount(long structureId) {
		return ddmStructureVersionPersistence.countByStructureId(structureId);
	}

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

}