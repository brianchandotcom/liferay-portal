/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplateService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMTemplateService
 * @generated
 */
public class DDMTemplateServiceWrapper implements DDMTemplateService,
	ServiceWrapper<DDMTemplateService> {
	public DDMTemplateServiceWrapper(DDMTemplateService ddmTemplateService) {
		_ddmTemplateService = ddmTemplateService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmTemplateService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmTemplateService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		long groupId, long classNameId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.addTemplate(groupId, classNameId, classPK,
			nameMap, descriptionMap, type, mode, language, script,
			serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		long groupId, long classNameId, long classPK,
		java.lang.String templateKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.addTemplate(groupId, classNameId, classPK,
			templateKey, nameMap, descriptionMap, type, mode, language, script,
			cacheable, smallImage, smallImageURL, smallImageFile, serviceContext);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> copyTemplates(
		long classNameId, long classPK, long newClassPK, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.copyTemplates(classNameId, classPK,
			newClassPK, type, serviceContext);
	}

	public void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmTemplateService.deleteTemplate(templateId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.fetchTemplate(groupId, templateKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(templateId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(groupId, templateKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, java.lang.String templateKey,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(groupId, templateKey,
			includeGlobalTemplates);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId, classPK);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(classNameId, classPK, type, mode);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupId, classNameId,
			classPK, keywords, type, mode, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupId, classNameId,
			classPK, name, description, type, mode, language, andOperator,
			start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupIds, classNameIds,
			classPK, keywords, type, mode, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupIds, classNameIds,
			classPK, name, description, type, mode, language, andOperator,
			start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String keywords, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupId, classNameId,
			classPK, keywords, type, mode);
	}

	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupId, classNameId,
			classPK, name, description, type, mode, language, andOperator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupIds,
			classNameIds, classPK, keywords, type, mode);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupIds,
			classNameIds, classPK, name, description, type, mode, language,
			andOperator);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateTemplate(
		long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.updateTemplate(templateId, nameMap,
			descriptionMap, type, mode, language, script, cacheable,
			smallImage, smallImageURL, smallImageFile, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DDMTemplateService getWrappedDDMTemplateService() {
		return _ddmTemplateService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDDMTemplateService(
		DDMTemplateService ddmTemplateService) {
		_ddmTemplateService = ddmTemplateService;
	}

	public DDMTemplateService getWrappedService() {
		return _ddmTemplateService;
	}

	public void setWrappedService(DDMTemplateService ddmTemplateService) {
		_ddmTemplateService = ddmTemplateService;
	}

	private DDMTemplateService _ddmTemplateService;
}