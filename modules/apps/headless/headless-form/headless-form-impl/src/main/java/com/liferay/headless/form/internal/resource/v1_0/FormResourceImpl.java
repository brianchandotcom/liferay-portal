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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormContext;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.form.internal.dto.v1_0.util.FormContextUtil;
import com.liferay.headless.form.internal.dto.v1_0.util.FormDocumentUtil;
import com.liferay.headless.form.internal.dto.v1_0.util.StructureUtil;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Victor Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form.properties",
	scope = ServiceScope.PROTOTYPE, service = FormResource.class
)
public class FormResourceImpl extends BaseFormResourceImpl {

	@Override
	public Form getForm(Long formId) throws Exception {
		return _toForm(_ddmFormInstanceService.getFormInstance(formId));
	}

	@Override
	public Page<Form> getSiteFormsPage(Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmFormInstanceService.getFormInstances(
					contextCompany.getCompanyId(), siteId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toForm),
			pagination,
			_ddmFormInstanceService.getFormInstancesCount(
				contextCompany.getCompanyId(), siteId));
	}

	@Override
	public FormContext postFormEvaluateContext(
			Long formId, FormContext formContext)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setGroupId(ddmFormInstance.getGroupId());
		ddmFormRenderingContext.setHttpServletRequest(
			contextHttpServletRequest);

		return FormContextUtil.evaluateContext(
			ddmFormInstance, ddmFormRenderingContext,
			_ddmFormTemplateContextFactory, formContext.getFormFieldValues(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public FormDocument postFormFormDocument(
			Long formId, MultipartBody multipartBody)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		FormDocument formDocument = multipartBody.getValueAsInstance(
			"formDocument", FormDocument.class);

		long folderId = Optional.ofNullable(
			formDocument.getFolderId()
		).orElse(
			0L
		);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		return FormDocumentUtil.toFormDocument(
			_dlurlHelper,
			_dlAppService.addFileEntry(
				ddmFormInstance.getGroupId(), folderId,
				binaryFile.getFileName(), binaryFile.getContentType(),
				formDocument.getTitle(), formDocument.getDescription(), null,
				binaryFile.getInputStream(), binaryFile.getSize(),
				new ServiceContext()));
	}

	private Form _toForm(DDMFormInstance ddmFormInstance) throws Exception {
		if (ddmFormInstance == null) {
			return null;
		}

		return new Form() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmFormInstance.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(ddmFormInstance.getUserId()));
				dateCreated = ddmFormInstance.getCreateDate();
				dateModified = ddmFormInstance.getModifiedDate();
				datePublished = ddmFormInstance.getLastPublishDate();
				defaultLanguage = ddmFormInstance.getDefaultLanguageId();
				description = ddmFormInstance.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				description_i18n = LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					ddmFormInstance.getDescriptionMap());
				id = ddmFormInstance.getFormInstanceId();
				name = ddmFormInstance.getName(
					contextAcceptLanguage.getPreferredLocale());
				name_i18n = LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					ddmFormInstance.getNameMap());
				structure = StructureUtil.toFormStructure(
					contextAcceptLanguage.isAcceptAllLanguages(),
					ddmFormInstance.getStructure(),
					contextAcceptLanguage.getPreferredLocale(), _portal,
					_userLocalService);
			}
		};
	}

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}