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

import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentUrl;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.AttachmentDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.AttachmentUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@CTAware
public class AttachmentResourceImpl
	extends BaseAttachmentResourceImpl implements NestedFieldSupport {

	@Override
	public Page<Attachment> getProductByExternalReferenceCodeAttachmentsPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
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
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
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
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductAttachment(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeAttachmentByBase64(
			String externalReferenceCode, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductAttachment(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeAttachmentByUrl(
			String externalReferenceCode, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductAttachment(cpDefinition, attachmentUrl);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImage(
			String externalReferenceCode, Attachment attachment)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductImage(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImageByBase64(
			String externalReferenceCode, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductImage(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductByExternalReferenceCodeImageByUrl(
			String externalReferenceCode, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateProductImage(cpDefinition, attachmentUrl);
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

		return _addOrUpdateProductAttachment(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductIdAttachmentByBase64(
			Long id, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addOrUpdateProductAttachment(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductIdAttachmentByUrl(
			Long id, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addOrUpdateProductAttachment(cpDefinition, attachmentUrl);
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

		return _addOrUpdateProductImage(cpDefinition, attachment);
	}

	@Override
	public Attachment postProductIdImageByBase64(
			Long id, AttachmentBase64 attachmentBase64)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addOrUpdateProductImage(cpDefinition, attachmentBase64);
	}

	@Override
	public Attachment postProductIdImageByUrl(
			Long id, AttachmentUrl attachmentUrl)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addOrUpdateProductImage(cpDefinition, attachmentUrl);
	}

	private Attachment _addOrUpdateAttachment(
			CPDefinition cpDefinition, int type, Attachment attachment)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpDefinition.getGroupId());

		Map<String, Serializable> expandoBridgeAttributes =
			_getExpandoBridgeAttributes(attachment);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.addOrUpdateCPAttachmentFileEntry(
				cpDefinition.getGroupId(), _cpAttachmentFileEntryService,
				_uniqueFileNameProvider, attachment,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type, serviceContext);

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _addOrUpdateAttachment(
			CPDefinition cpDefinition, int type,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpDefinition.getGroupId());

		Map<String, Serializable> expandoBridgeAttributes =
			_getExpandoBridgeAttributes(attachmentBase64);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.addOrUpdateCPAttachmentFileEntry(
				_cpAttachmentFileEntryService, _uniqueFileNameProvider,
				attachmentBase64,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type, serviceContext);

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _addOrUpdateAttachment(
			CPDefinition cpDefinition, int type, AttachmentUrl attachmentUrl)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpDefinition.getGroupId());

		Map<String, Serializable> expandoBridgeAttributes =
			_getExpandoBridgeAttributes(attachmentUrl);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			AttachmentUtil.addOrUpdateCPAttachmentFileEntry(
				_cpAttachmentFileEntryService, _uniqueFileNameProvider,
				attachmentUrl,
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName()),
				cpDefinition.getCPDefinitionId(), type, serviceContext);

		return _toAttachment(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	private Attachment _addOrUpdateProductAttachment(
			CPDefinition cpDefinition, Attachment attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _addOrUpdateProductAttachment(
			CPDefinition cpDefinition, AttachmentBase64 attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _addOrUpdateProductAttachment(
			CPDefinition cpDefinition, AttachmentUrl attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_OTHER,
			attachment);
	}

	private Attachment _addOrUpdateProductImage(
			CPDefinition cpDefinition, Attachment attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
	}

	private Attachment _addOrUpdateProductImage(
			CPDefinition cpDefinition, AttachmentBase64 attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
	}

	private Attachment _addOrUpdateProductImage(
			CPDefinition cpDefinition, AttachmentUrl attachment)
		throws Exception {

		return _addOrUpdateAttachment(
			cpDefinition, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			attachment);
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

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		Attachment attachment) {

		return CustomFieldsUtil.toMap(
			CPAttachmentFileEntry.class.getName(),
			contextCompany.getCompanyId(), attachment.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		AttachmentBase64 attachmentBase64) {

		return CustomFieldsUtil.toMap(
			CPAttachmentFileEntry.class.getName(),
			contextCompany.getCompanyId(), attachmentBase64.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		AttachmentUrl attachmentUrl) {

		return CustomFieldsUtil.toMap(
			CPAttachmentFileEntry.class.getName(),
			contextCompany.getCompanyId(), attachmentUrl.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
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