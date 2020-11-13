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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentUrl;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.AttachmentDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.AttachmentUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/attachment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AttachmentResource.class, NestedFieldSupport.class}
)
public class AttachmentResourceImpl
	extends BaseAttachmentResourceImpl implements NestedFieldSupport {

	@Override
	public Page<Attachment> getProductByExternalReferenceCodeAttachmentsPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _getAttachmentPage(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			pagination);
	}

	@Override
	public Page<Attachment> getProductByExternalReferenceCodeImagesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _getAttachmentPage(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			pagination);
	}

	@NestedField(parentClass = Product.class, value = "attachments")
	@Override
	public Page<Attachment> getProductIdAttachmentsPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _getAttachmentPage(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			pagination);
	}

	@NestedField(parentClass = Product.class, value = "images")
	@Override
	public Page<Attachment> getProductIdImagesPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _getAttachmentPage(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			pagination);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeAttachment(
			String externalReferenceCode, Attachment attachment)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductAttachment(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeAttachmentByBase64(
			@NotNull String externalReferenceCode,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductAttachment(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeAttachmentByUrl(
			@NotNull String externalReferenceCode, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductAttachment(cpDefinition, attachmentUrl);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImage(
			String externalReferenceCode, Attachment attachment)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductImage(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImageByBase64(
			@NotNull String externalReferenceCode,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductImage(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImageByUrl(
			@NotNull String externalReferenceCode, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _upsertProductImage(cpDefinition, attachmentUrl);
	}

	@Override
	public Attachment postProductIdAttachment(Long id, Attachment attachment)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductAttachment(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductIdAttachmentByBase64(
			@NotNull Long id, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductAttachment(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductIdAttachmentByUrl(
			@NotNull Long id, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductAttachment(cpDefinition, attachmentUrl);
	}

	@Override
	public Attachment postProductIdImage(Long id, Attachment attachment)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductImage(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductIdImageByBase64(
			@NotNull Long id, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductImage(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductIdImageByUrl(
			@NotNull Long id, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _upsertProductImage(cpDefinition, attachmentUrl);
	}

	private Page<Attachment> _getAttachmentPage(
			CPDefinition cpDefinition, int type, Pagination pagination)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), type,
				WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), type,
				WorkflowConstants.STATUS_APPROVED);

		return Page.of(
			_toAttachments(cpAttachmentFileEntries), pagination, totalItems);
	}

	private Attachment _toAttachment(Long cpAttachmentFileEntryId)
		throws Exception {

		return _attachmentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpAttachmentFileEntryId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Attachment> _toAttachments(
			List<CPAttachmentFileEntry> cpAttachmentFileEntries)
		throws Exception {

		List<Attachment> attachments = new ArrayList<>();

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			attachments.add(
				_toAttachment(
					cpAttachmentFileEntry.getCPAttachmentFileEntryId()));
		}

		return attachments;
	}

	private Attachment _upsertAttachment(
			CPDefinition cpDefinition, int type, Attachment attachment)
		throws Exception {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.upsertCPAttachmentFileEntry(
				cpDefinition.getGroupId(), _cpAttachmentFileEntryService,
				_uniqueFileNameProvider, attachment,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type,
				_serviceContextHelper.getServiceContext(
					cpDefinition.getGroupId()));

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _upsertAttachment(
			CPDefinition cpDefinition, int type,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.upsertCPAttachmentFileEntry(
				_cpAttachmentFileEntryService, _uniqueFileNameProvider,
				attachmentBase64,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type,
				_serviceContextHelper.getServiceContext(
					cpDefinition.getGroupId()));

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _upsertAttachment(
			CPDefinition cpDefinition, int type, AttachmentUrl attachmentUrl)
		throws Exception {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.upsertCPAttachmentFileEntry(
				_cpAttachmentFileEntryService, _uniqueFileNameProvider,
				attachmentUrl,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type,
				_serviceContextHelper.getServiceContext(
					cpDefinition.getGroupId()));

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _upsertProductAttachment(
			CPDefinition cpDefinition, Attachment attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _upsertProductAttachment(
			CPDefinition cpDefinition, AttachmentBase64 attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _upsertProductAttachment(
			CPDefinition cpDefinition, AttachmentUrl attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _upsertProductImage(
			CPDefinition cpDefinition, Attachment attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
	}

	private Attachment _upsertProductImage(
			CPDefinition cpDefinition, AttachmentBase64 attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
	}

	private Attachment _upsertProductImage(
			CPDefinition cpDefinition, AttachmentUrl attachment)
		throws Exception {

		return _upsertAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
	}

	@Reference
	private AttachmentDTOConverter _attachmentDTOConverter;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}