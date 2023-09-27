/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.demo.data.creator.internal;

import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.demo.data.creator.DDMStructureDemoDataCreator;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
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

		ddmFormLayout.setPaginationMode(DDMFormLayout.WIZARD_MODE);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			userId, groupId, 0,
			_portal.getClassNameId(DDMFormInstance.class.getName()), null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "Test Structure"
			).build(),
			null, ddmForm, ddmFormLayout, StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT, new ServiceContext());

		_ddmStructureIds.add(ddmStructure.getStructureId());

		return ddmStructure;
	}

	@Override
	public void delete() throws PortalException {
		for (Long ddmStructureId : _ddmStructureIds) {
			_ddmStructureIds.remove(ddmStructureId);

			_ddmStructureLocalService.deleteStructure(ddmStructureId);
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

	private final List<Long> _ddmStructureIds = new CopyOnWriteArrayList<>();

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}