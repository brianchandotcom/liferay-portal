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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMTemplateLinkLocalServiceImpl
	extends DDMTemplateLinkLocalServiceBaseImpl {

	@Override
	public DDMTemplateLink addTemplateLink(
		long classNameId, long classPK, long templateId) {

		long templateLinkId = counterLocalService.increment();

		DDMTemplateLink templateLink = ddmTemplateLinkPersistence.create(
			templateLinkId);

		templateLink.setClassNameId(classNameId);
		templateLink.setClassPK(classPK);
		templateLink.setTemplateId(templateId);

		return ddmTemplateLinkPersistence.update(templateLink);
	}

	@Override
	public DDMTemplateLink deleteTemplateLink(DDMTemplateLink templateLink) {
		return ddmTemplateLinkPersistence.remove(templateLink);
	}

	@Override
	public DDMTemplateLink deleteTemplateLink(long templateLinkId)
		throws PortalException {

		DDMTemplateLink templateLink =
			ddmTemplateLinkPersistence.findByPrimaryKey(templateLinkId);

		return deleteDDMTemplateLink(templateLink);
	}

	@Override
	public DDMTemplateLink deleteTemplateLink(long classNameId, long classPK) {
		DDMTemplateLink templateLink = ddmTemplateLinkPersistence.fetchByC_C(
			classNameId, classPK);

		if (templateLink != null) {
			deleteDDMTemplateLink(templateLink);
		}

		return templateLink;
	}

	@Override
	public DDMTemplateLink getTemplateLink(long templateLinkId)
		throws PortalException {

		return ddmTemplateLinkPersistence.findByPrimaryKey(templateLinkId);
	}

	@Override
	public DDMTemplateLink getTemplateLink(long classNameId, long classPK)
		throws PortalException {

		return ddmTemplateLinkPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<DDMTemplateLink> getTemplateLinks(long classNameId) {
		return ddmTemplateLinkPersistence.findByClassNameId(classNameId);
	}

	@Override
	public DDMTemplateLink updateTemplateLink(
			long templateLinkId, long templateId)
		throws PortalException {

		DDMTemplateLink templateLink =
			ddmTemplateLinkPersistence.findByPrimaryKey(templateLinkId);

		templateLink.setTemplateId(templateId);

		return ddmTemplateLinkPersistence.update(templateLink);
	}

	@Override
	public DDMTemplateLink updateTemplateLink(
		long classNameId, long classPK, long templateId) {

		DDMTemplateLink templateLink = ddmTemplateLinkPersistence.fetchByC_C(
			classNameId, classPK);

		if (templateLink == null) {
			return addTemplateLink(classNameId, classPK, templateId);
		}

		templateLink.setTemplateId(templateId);

		return ddmTemplateLinkPersistence.update(templateLink);
	}

}