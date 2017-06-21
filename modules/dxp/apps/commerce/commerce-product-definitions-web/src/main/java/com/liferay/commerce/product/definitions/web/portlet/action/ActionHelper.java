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

package com.liferay.commerce.product.definitions.web.portlet.action;

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries = new ArrayList<>();

		long[] cpAttachmentFileEntryIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

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
			RenderRequest renderRequest)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			(CPAttachmentFileEntry)renderRequest.getAttribute(
				CPWebKeys.CP_ATTACHMENT_FILE_ENTRY);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			renderRequest, "cpAttachmentFileEntryId");

		if (cpAttachmentFileEntryId > 0) {
			cpAttachmentFileEntry =
				_cpAttachmentFileEntryService.fetchCPAttachmentFileEntry(
					cpAttachmentFileEntryId);
		}

		if (cpAttachmentFileEntry != null) {
			renderRequest.setAttribute(
				CPWebKeys.CP_ATTACHMENT_FILE_ENTRY, cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntry;
	}

	public CPDefinition getCPDefinition(RenderRequest renderRequest)
		throws PortalException {

		CPDefinition cpDefinition = (CPDefinition)renderRequest.getAttribute(
			CPWebKeys.CP_DEFINITION);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId <= 0) {
			CPDefinitionOptionRel cpDefinitionOptionRel =
				getCPDefinitionOptionRel(renderRequest);

			if (cpDefinitionOptionRel != null) {
				cpDefinitionId = cpDefinitionOptionRel.getCPDefinitionId();
			}

			CPDefinitionLink cpDefinitionLink = getCPDefinitionLink(
				renderRequest);

			if (cpDefinitionLink != null) {
				cpDefinitionId = cpDefinitionLink.getCPDefinitionId1();
			}
		}

		if (cpDefinitionId > 0) {
			cpDefinition = _cpDefinitionService.fetchCPDefinition(
				cpDefinitionId);
		}

		if (cpDefinition != null) {
			renderRequest.setAttribute(CPWebKeys.CP_DEFINITION, cpDefinition);
		}

		return cpDefinition;
	}

	public CPDefinitionLink getCPDefinitionLink(RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			(CPDefinitionLink)renderRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_LINK);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		long cpDefinitionLinkId = ParamUtil.getLong(
			renderRequest, "cpDefinitionLinkId");

		if (cpDefinitionLinkId > 0) {
			cpDefinitionLink = _cpDefinitionLinkService.fetchCPDefinitionLink(
				cpDefinitionLinkId);
		}

		if (cpDefinitionLink != null) {
			renderRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_LINK, cpDefinitionLink);
		}

		return cpDefinitionLink;
	}

	public List<CPDefinitionLink> getCPDefinitionLinks(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionLink> cpDefinitionLinks = new ArrayList<>();

		long[] cpDefinitionLinkIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionLinkId : cpDefinitionLinkIds) {
			CPDefinitionLink cpDefinitionLink =
				_cpDefinitionLinkService.getCPDefinitionLink(
					cpDefinitionLinkId);

			cpDefinitionLinks.add(cpDefinitionLink);
		}

		return cpDefinitionLinks;
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)renderRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_REL);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			renderRequest, "cpDefinitionOptionRelId");

		if (cpDefinitionOptionRelId <= 0) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				getCPDefinitionOptionValueRel(renderRequest);

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
			renderRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_REL, cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRel;
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>();

		long[] cpDefinitionOptionRelIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionOptionRelId : cpDefinitionOptionRelIds) {
			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			cpDefinitionOptionRels.add(cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRels;
	}

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			(CPDefinitionOptionValueRel)renderRequest.getAttribute(
				CPWebKeys.CP_DEFINITION_OPTION_VALUE_REL);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			renderRequest, "cpDefinitionOptionValueRelId");

		if (cpDefinitionOptionValueRelId > 0) {
			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					fetchCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId);
		}

		if (cpDefinitionOptionValueRel != null) {
			renderRequest.setAttribute(
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
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		long[] cpDefinitionOptionValueRelIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionOptionValueRelId :
				cpDefinitionOptionValueRelIds) {

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionValueRels;
	}

	public List<CPDefinition> getCPDefinitions(ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinition> cpDefinitions = new ArrayList<>();

		long[] cpDefinitionIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionId : cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			cpDefinitions.add(cpDefinition);
		}

		return cpDefinitions;
	}

	public CPInstance getCPInstance(RenderRequest renderRequest)
		throws PortalException {

		CPInstance cpInstance = (CPInstance)renderRequest.getAttribute(
			CPWebKeys.CP_INSTANCE);

		if (cpInstance != null) {
			return cpInstance;
		}

		long cpInstanceId = ParamUtil.getLong(renderRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		if (cpInstance != null) {
			renderRequest.setAttribute(CPWebKeys.CP_INSTANCE, cpInstance);
		}

		return cpInstance;
	}

	public List<CPInstance> getCPInstances(ResourceRequest resourceRequest)
		throws PortalException {

		List<CPInstance> cpInstances = new ArrayList<>();

		long[] cpInstanceIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpInstanceId : cpInstanceIds) {
			CPInstance cpInstance = _cpInstanceService.getCPInstance(
				cpInstanceId);

			cpInstances.add(cpInstance);
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

		return _cpDefinitionOptionRelService.
			getSkuContributorCPDefinitionOptionRels(cpDefinitionId);
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
	private CPInstanceService _cpInstanceService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

}