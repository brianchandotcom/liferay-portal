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

import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.dynamic.data.mapping.service"},
	service = {DDMServiceVerifyProcess.class, VerifyProcess.class}
)
public class DDMServiceVerifyProcess extends VerifyProcess {

	@Activate
	@Override
	protected void doVerify() throws Exception {
		verifyStructures();
		verifyStructureLinks();
		verifyTemplateLinks();

		verifyContents();
	}

	protected DDMFormValues getDDMFormValues(
			DDMStructure structure, DDMContent content)
		throws PortalException {

		return _ddmFormValuesJSONDeserializer.deserialize(
			structure.getDDMForm(), content.getData());
	}

	@Reference(unbind = "-")
	protected void setDDMContentLocalService(
		DDMContentLocalService ddmContentLocalService) {

		_ddmContentLocalService = ddmContentLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormLayoutValidator(
		DDMFormLayoutValidator ddmFormLayoutValidator) {

		_ddmFormLayoutValidator = ddmFormLayoutValidator;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValidator(DDMFormValidator ddmFormValidator) {
		_ddmFormValidator = ddmFormValidator;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesValidator(
		DDMFormValuesValidator ddmFormValuesValidator) {

		_ddmFormValuesValidator = ddmFormValuesValidator;
	}

	@Reference(unbind = "-")
	protected void setDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {

		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {

		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLinkLocalService(
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService) {

		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	protected void verifyContent(DDMContent content) throws PortalException {
		DDMStorageLink ddmStorageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(
				content.getContentId());

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			ddmStorageLink.getStructureId());

		try {
			DDMFormValues ddmFormValues = getDDMFormValues(structure, content);

			_ddmFormValuesValidator.validate(ddmFormValues);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"There is stale or invalid data in the DDM content " +
							"with ID %d according to its structure %d. " +
								"Caused by: {%s}. Please verify it manually.",
						content.getContentId(), structure.getStructureId(),
						e.getMessage()),
					e);
			}
		}
	}

	protected void verifyContents() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_ddmContentLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DDMContent content = (DDMContent)object;

					verifyContent(content);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void verifyDDMForm(DDMForm ddmForm) throws PortalException {
		_ddmFormValidator.validate(ddmForm);
	}

	protected void verifyDDMFormLayout(DDMFormLayout ddmFormLayout)
		throws PortalException {

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	protected void verifyStructure(DDMStructure structure)
		throws PortalException {

		verifyDDMForm(structure.getDDMForm());
		verifyDDMFormLayout(structure.getDDMFormLayout());
	}

	protected void verifyStructureLink(DDMStructureLink structureLink)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.fetchStructure(
			structureLink.getStructureId());

		if (structure == null) {
			_ddmStructureLinkLocalService.deleteStructureLink(
				structureLink.getStructureLinkId());
		}
	}

	protected void verifyStructureLinks() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_ddmStructureLinkLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DDMStructureLink structureLink = (DDMStructureLink)object;

					verifyStructureLink(structureLink);
				}

			});
	}

	protected void verifyStructures() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_ddmStructureLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DDMStructure structure = (DDMStructure)object;

					verifyStructure(structure);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void verifyTemplateLink(DDMTemplateLink templateLink)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.fetchTemplate(
			templateLink.getTemplateId());

		if (template == null) {
			_ddmTemplateLinkLocalService.deleteTemplateLink(
				templateLink.getTemplateId());
		}
	}

	protected void verifyTemplateLinks() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_ddmTemplateLinkLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DDMTemplateLink templateLink = (DDMTemplateLink)object;

					verifyTemplateLink(templateLink);
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMServiceVerifyProcess.class);

	private volatile DDMContentLocalService _ddmContentLocalService;
	private volatile DDMFormLayoutValidator _ddmFormLayoutValidator;
	private volatile DDMFormValidator _ddmFormValidator;
	private volatile DDMFormValuesJSONDeserializer
		_ddmFormValuesJSONDeserializer;
	private volatile DDMFormValuesValidator _ddmFormValuesValidator;
	private volatile DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private volatile DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private volatile DDMTemplateLocalService _ddmTemplateLocalService;

}