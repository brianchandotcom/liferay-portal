/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.TemplateNameException;
import com.liferay.portlet.dynamicdatamapping.TemplateScriptException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMTemplateLocalServiceImpl
	extends DDMTemplateLocalServiceBaseImpl {

	public DDMTemplate addTemplate(
			long userId, long groupId, long structureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Template

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(nameMap, script);

		long templateId = counterLocalService.increment();

		DDMTemplate template = ddmTemplatePersistence.create(templateId);

		template.setUuid(serviceContext.getUuid());
		template.setGroupId(groupId);
		template.setCompanyId(user.getCompanyId());
		template.setUserId(user.getUserId());
		template.setUserName(user.getFullName());
		template.setCreateDate(serviceContext.getCreateDate(now));
		template.setModifiedDate(serviceContext.getModifiedDate(now));
		template.setStructureId(structureId);
		template.setNameMap(nameMap);
		template.setDescriptionMap(descriptionMap);
		template.setType(type);
		template.setMode(mode);
		template.setLanguage(language);
		template.setScript(script);

		ddmTemplatePersistence.update(template, false);

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addTemplateResources(
				template, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addTemplateResources(
				template, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return template;
	}

	public void addTemplateResources(
			DDMTemplate template, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	public void addTemplateResources(
			DDMTemplate template, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), groupPermissions, guestPermissions);
	}

	public void deleteTemplate(DDMTemplate template)
		throws PortalException, SystemException {

		// Template

		ddmTemplatePersistence.remove(template);

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), DDMTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, template.getTemplateId());
	}

	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		deleteTemplate(template);
	}

	public void deleteTemplates(long groupId)
		throws PortalException, SystemException {

		List<DDMTemplate> templates = ddmTemplatePersistence.findByGroupId(
			groupId);

		for (DDMTemplate template : templates) {
			deleteTemplate(template);
		}
	}

	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	public List<DDMTemplate> getTemplates(long structureId)
		throws SystemException {

		return ddmTemplatePersistence.findByStructureId(structureId);
	}

	public List<DDMTemplate> getTemplates(long structureId, String type)
		throws SystemException {

		return ddmTemplatePersistence.findByS_T(structureId, type);
	}

	public List<DDMTemplate> getTemplates(
			long structureId, String type, String mode)
		throws SystemException {

		return ddmTemplatePersistence.findByS_T_M(structureId, type, mode);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, long structureId, String keywords,
			String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupId, structureId, keywords, type, mode, start, end,
			orderByComparator);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, long structureId, String name,
			String description, String type, String mode, String language,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, name, description, type, mode,
			language, andOperator, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, long structureId, String keywords,
			String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupId, structureId, keywords, type, mode);
	}

	public int searchCount(
			long companyId, long groupId, long structureId, String name,
			String description, String type, String mode, String language,
			boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, name, description, type, mode,
			language, andOperator);
	}

	public DDMTemplate updateTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(nameMap, script);

		DDMTemplate template = ddmTemplateLocalService.getDDMTemplate(
			templateId);

		template.setModifiedDate(serviceContext.getModifiedDate(null));
		template.setNameMap(nameMap);
		template.setDescriptionMap(descriptionMap);
		template.setType(type);
		template.setMode(mode);
		template.setLanguage(language);
		template.setScript(script);

		ddmTemplatePersistence.update(template, false);

		return template;
	}

	protected void validate(Map<Locale, String> nameMap, String script)
		throws PortalException {

		validateName(nameMap);

		if (Validator.isNull(script)) {
			throw new TemplateScriptException();
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(name)) {
			throw new TemplateNameException();
		}
	}

}