/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.verify;

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
 */
@Component(service = VerifyProcess.class)
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