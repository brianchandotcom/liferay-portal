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

package com.liferay.commerce.product.internal.portlet.action;

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = ActionHelper.class)
public class ActionHelperImpl implements ActionHelper {

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			PortletRequest portletRequest)
		throws PortalException {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries = new ArrayList<>();

		long[] cpAttachmentFileEntryIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpAttachmentFileEntryId : cpAttachmentFileEntryIds) {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				_cpAttachmentFileEntryService.fetchCPAttachmentFileEntry(
					cpAttachmentFileEntryId);

			if (cpAttachmentFileEntry != null) {
				cpAttachmentFileEntries.add(cpAttachmentFileEntry);
			}
		}

		return cpAttachmentFileEntries;
	}

	public CPAttachmentFileEntry getCPAttachmentFileEntry(
			PortletRequest portletRequest)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			(CPAttachmentFileEntry)portletRequest.getAttribute(
				CPWebKeys.CP_ATTACHMENT_FILE_ENTRY);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			portletRequest, "cpAttachmentFileEntryId");

		if (cpAttachmentFileEntryId > 0) {
			cpAttachmentFileEntry =
				_cpAttachmentFileEntryService.fetchCPAttachmentFileEntry(
					cpAttachmentFileEntryId);
		}

		if (cpAttachmentFileEntry != null) {
			portletRequest.setAttribute(
				CPWebKeys.CP_ATTACHMENT_FILE_ENTRY, cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntry;
	}

	public CPDefinition getCPDefinition(PortletRequest portletRequest)
		throws PortalException {

		CPDefinition cpDefinition = (CPDefinition)portletRequest.getAttribute(
			CPWebKeys.CP_DEFINITION);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		long cpDefinitionId = ParamUtil.getLong(
			portletRequest, "cpDefinitionId");

		cpDefinition = _validateCPDefinition(cpDefinitionId);

		if (cpDefinition != null) {
			portletRequest.setAttribute(CPWebKeys.CP_DEFINITION, cpDefinition);
		}

		return cpDefinition;
	}

	public CPDefinitionLink getCPDefinitionLink(PortletRequest portletRequest)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			(CPDefinitionLink)portletRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_LINK);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		long cpDefinitionLinkId = ParamUtil.getLong(
			portletRequest, "cpDefinitionLinkId");

		if (cpDefinitionLinkId > 0) {
			cpDefinitionLink = _cpDefinitionLinkService.fetchCPDefinitionLink(
				cpDefinitionLinkId);
		}

		if (cpDefinitionLink != null) {
			portletRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_LINK, cpDefinitionLink);
		}

		return cpDefinitionLink;
	}

	public List<CPDefinitionLink> getCPDefinitionLinks(
			PortletRequest portletRequest)
		throws PortalException {

		List<CPDefinitionLink> cpDefinitionLinks = new ArrayList<>();

		long[] cpDefinitionLinkIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpDefinitionLinkId : cpDefinitionLinkIds) {
			cpDefinitionLinks.add(
				_cpDefinitionLinkService.getCPDefinitionLink(
					cpDefinitionLinkId));
		}

		return cpDefinitionLinks;
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			PortletRequest portletRequest)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)portletRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_REL);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			portletRequest, "cpDefinitionOptionRelId");

		if (cpDefinitionOptionRelId <= 0) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				getCPDefinitionOptionValueRel(portletRequest);

			if (cpDefinitionOptionValueRel != null) {
				cpDefinitionOptionRelId =
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId();
			}
		}

		if (cpDefinitionOptionRelId > 0) {
			cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);
		}

		if (cpDefinitionOptionRel != null) {
			portletRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_REL, cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRel;
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			PortletRequest portletRequest)
		throws PortalException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>();

		long[] cpDefinitionOptionRelIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpDefinitionOptionRelId : cpDefinitionOptionRelIds) {
			cpDefinitionOptionRels.add(
				_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId));
		}

		return cpDefinitionOptionRels;
	}

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			PortletRequest portletRequest)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			(CPDefinitionOptionValueRel)portletRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_VALUE_REL);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			portletRequest, "cpDefinitionOptionValueRelId");

		if (cpDefinitionOptionValueRelId > 0) {
			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					fetchCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId);
		}

		if (cpDefinitionOptionValueRel != null) {
			portletRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_VALUE_REL,
				cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionValueRel;
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId)
		throws PortalException {

		int total =
			_cpDefinitionOptionValueRelService.
				getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);

		return _cpDefinitionOptionValueRelService.
			getCPDefinitionOptionValueRels(cpDefinitionOptionRelId, 0, total);
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			PortletRequest portletRequest)
		throws PortalException {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		long[] cpDefinitionOptionValueRelIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpDefinitionOptionValueRelId :
				cpDefinitionOptionValueRelIds) {

			cpDefinitionOptionValueRels.add(
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId));
		}

		return cpDefinitionOptionValueRels;
	}

	public List<CPDefinition> getCPDefinitions(PortletRequest portletRequest)
		throws PortalException {

		List<CPDefinition> cpDefinitions = new ArrayList<>();

		long[] cpDefinitionIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpDefinitionId : cpDefinitionIds) {
			cpDefinitions.add(
				_cpDefinitionService.getCPDefinition(cpDefinitionId));
		}

		return cpDefinitions;
	}

	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue(
				PortletRequest portletRequest)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				(CPDefinitionSpecificationOptionValue)
					portletRequest.getAttribute(
						CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUE);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		long cpDefinitionSpecificationOptionValueId = ParamUtil.getLong(
			portletRequest, "cpDefinitionSpecificationOptionValueId");

		if (cpDefinitionSpecificationOptionValueId > 0) {
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueService.
					fetchCPDefinitionSpecificationOptionValue(
						cpDefinitionSpecificationOptionValueId);
		}

		if (cpDefinitionSpecificationOptionValue != null) {
			portletRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUE,
				cpDefinitionSpecificationOptionValue);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(
				PortletRequest portletRequest)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues = new ArrayList<>();

		long[] cpDefinitionSpecificationOptionValueIds =
			ParamUtil.getLongValues(portletRequest, "rowIds");

		for (long cpDefinitionSpecificationOptionValueId :
				cpDefinitionSpecificationOptionValueIds) {

			cpDefinitionSpecificationOptionValues.add(
				_cpDefinitionSpecificationOptionValueService.
					getCPDefinitionSpecificationOptionValue(
						cpDefinitionSpecificationOptionValueId));
		}

		return cpDefinitionSpecificationOptionValues;
	}

	public CPInstance getCPInstance(PortletRequest portletRequest)
		throws PortalException {

		CPInstance cpInstance = (CPInstance)portletRequest.getAttribute(
			CPWebKeys.CP_INSTANCE);

		if (cpInstance != null) {
			return cpInstance;
		}

		long cpInstanceId = ParamUtil.getLong(portletRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		if (cpInstance != null) {
			portletRequest.setAttribute(CPWebKeys.CP_INSTANCE, cpInstance);
		}

		return cpInstance;
	}

	public List<CPInstance> getCPInstances(PortletRequest portletRequest)
		throws PortalException {

		List<CPInstance> cpInstances = new ArrayList<>();

		long[] cpInstanceIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long cpInstanceId : cpInstanceIds) {
			cpInstances.add(_cpInstanceService.getCPInstance(cpInstanceId));
		}

		return cpInstances;
	}

	public CPType getCPType(String name) {
		return _cpTypeServicesTracker.getCPType(name);
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
			long cpDefinitionId)
		throws PortalException {

		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(
			cpDefinitionId, true);
	}

	public void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private CPDefinition _validateCPDefinition(Long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition != null) {
			CProduct cProduct = _cProductLocalService.getCProduct(
				cpDefinition.getCProductId());

			if ((cpDefinition.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) &&
				(cpDefinitionId != cProduct.getPublishedCPDefinitionId())) {

				cpDefinition = _cpDefinitionService.fetchCPDefinition(
					cProduct.getPublishedCPDefinitionId());
			}
		}

		return cpDefinition;
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private Portal _portal;

}