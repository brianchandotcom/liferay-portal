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

package com.liferay.dynamic.data.mapping.verify;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Marcellus Tavares
 * @author     Rafael Praxedes
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	property = "verify.process.name=com.liferay.dynamic.data.mapping.service",
	service = VerifyProcess.class
)
@Deprecated
public class DDMServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyStructureLinks();
		verifyTemplateLinks();
	}

	protected DDMFormValues getDDMFormValues(
		DDMForm ddmForm, DDMContent ddmContent) {

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				ddmContent.getData(), ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, DDMContent ddmContent)
		throws PortalException {

		return getDDMFormValues(ddmStructure.getDDMForm(), ddmContent);
	}

	protected void verifyStructureLink(DDMStructureLink ddmStructureLink)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			ddmStructureLink.getStructureId());

		if (ddmStructure == null) {
			_ddmStructureLinkLocalService.deleteStructureLink(
				ddmStructureLink.getStructureLinkId());
		}
	}

	protected void verifyStructureLinks() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_ddmStructureLinkLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Object object) -> {
					DDMStructureLink ddmStructureLink =
						(DDMStructureLink)object;

					verifyStructureLink(ddmStructureLink);
				});
		}
	}

	protected void verifyTemplateLink(DDMTemplateLink ddmTemplateLink)
		throws PortalException {

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			ddmTemplateLink.getTemplateId());

		if (ddmTemplate == null) {
			_ddmTemplateLinkLocalService.deleteTemplateLink(
				ddmTemplateLink.getTemplateId());
		}
	}

	protected void verifyTemplateLinks() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_ddmTemplateLinkLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Object object) -> {
					DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)object;

					verifyTemplateLink(ddmTemplateLink);
				});
		}
	}

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

}