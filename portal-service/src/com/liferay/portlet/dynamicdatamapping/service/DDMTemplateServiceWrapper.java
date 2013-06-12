/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link DDMTemplateService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateService
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
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddmTemplateService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmTemplateService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a template.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param nameMap the template's locales and localized names
	* @param descriptionMap the template's locales and localized descriptions
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language. For more information,
	see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param script the template's script
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
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

	/**
	* Adds a template with additional parameters.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param templateKey the unique string identifying the template
	(optionally <code>null</code>)
	* @param nameMap the template's locales and localized names
	* @param descriptionMap the template's locales and localized descriptions
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language. For more information,
	see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param script the template's script
	* @param cacheable whether the template is cacheable
	* @param smallImage whether the template has a small image
	* @param smallImageURL the template's small image URL (optionally
	<code>null</code>)
	* @param smallImageFile the template's small image file (optionally
	<code>null</code>)
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
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

	/**
	* Copies the template, creating a new template with all the values
	* extracted from the original one. This method supports defining a new name
	* and description.
	*
	* @param templateId the primary key of the template to be copied
	* @param nameMap the new template's locales and localized names
	* @param descriptionMap the new template's locales and localized
	descriptions
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the new template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.copyTemplate(templateId, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		long templateId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.copyTemplate(templateId, serviceContext);
	}

	/**
	* Copies all the templates matching the class name ID, class PK, and type.
	* This method creates new templates, extracting all the values from the old
	* ones and updating their class PKs.
	*
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the original template's related entity
	* @param newClassPK the primary key of the new template's related entity
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the new template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> copyTemplates(
		long classNameId, long classPK, long newClassPK, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.copyTemplates(classNameId, classPK,
			newClassPK, type, serviceContext);
	}

	/**
	* Deletes the template and its resources.
	*
	* @param templateId the primary key of the template to be deleted
	* @throws PortalException if the user did not have permission to delete the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmTemplateService.deleteTemplate(templateId);
	}

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template, or <code>null</code> if a matching
	template could not be found
	* @throws PortalException if the user did not have permission to view the
	template
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.fetchTemplate(groupId, classNameId,
			templateKey);
	}

	/**
	* Returns the template with the ID.
	*
	* @param templateId the primary key of the template
	* @return the template with the ID
	* @throws PortalException if the user did not have permission to view the
	template or if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(templateId);
	}

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(groupId, classNameId, templateKey);
	}

	/**
	* Returns the template matching the group and template key, optionally in
	* the global scope.
	*
	* <p>
	* This method first searches in the group. If the template is still not
	* found and <code>includeGlobalTemplates</code> is set to
	* <code>true</code>, this method searches the global group.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeGlobalTemplates whether to include the global scope in the
	search
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplate(groupId, classNameId,
			templateKey, includeGlobalTemplates);
	}

	/**
	* Returns all the templates matching the group and class name ID.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId);
	}

	/**
	* Returns all the templates matching the group, class name ID, and class
	* PK.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId, classPK);
	}

	/**
	* Returns all the templates matching the class name ID, class PK, type, and
	* mode.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId, classPK,
			type);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplates(groupId, classNameId, classPK,
			type, mode);
	}

	/**
	* Returns all the templates matching the group and class PK.
	*
	* @param groupId the primary key of the group
	* @param classPK the primary key of the template's related entity
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByClassPK(
		long groupId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplatesByClassPK(groupId, classPK);
	}

	/**
	* Returns an ordered range of all the templates matching the group and
	* structure class name ID.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param structureClassNameId the primary key of the class name for the
	template's related structure
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the range of matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByStructureClassNameId(
		long groupId, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.getTemplatesByStructureClassNameId(groupId,
			structureClassNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the templates matching the group, class
	* name ID, class PK, type, and mode, and matching the keywords in the
	* template names and descriptions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>) For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupId, classNameId,
			classPK, keywords, type, mode, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the templates matching the group, class
	* name ID, class PK, name keyword, description keyword, type, mode, and
	* language.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	@Override
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

	/**
	* Returns an ordered range of all the templates matching the group IDs,
	* class name IDs, class PK, type, and mode, and matching the keywords in
	* the template names and descriptions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupIds, classNameIds,
			classPKs, keywords, type, mode, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the templates matching the group IDs,
	* class name IDs, class PK, name keyword, description keyword, type, mode,
	* and language.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.search(companyId, groupIds, classNameIds,
			classPKs, name, description, type, mode, language, andOperator,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of templates matching the group, class name ID, class
	* PK, type, and mode, and matching the keywords in the template names and
	* descriptions.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @return the number of matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String keywords, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupId, classNameId,
			classPK, keywords, type, mode);
	}

	/**
	* Returns the number of templates matching the group, class name ID, class
	* PK, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @return the number of matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupId, classNameId,
			classPK, name, description, type, mode, language, andOperator);
	}

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PK, type, and mode, and matching the keywords in the template names
	* and descriptions.
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @return the number of matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupIds,
			classNameIds, classPKs, keywords, type, mode);
	}

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PK, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @return the number of matching templates
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplateService.searchCount(companyId, groupIds,
			classNameIds, classPKs, name, description, type, mode, language,
			andOperator);
	}

	/**
	* Updates the template matching the ID.
	*
	* @param templateId the primary key of the template
	* @param nameMap the template's new locales and localized names
	* @param descriptionMap the template's new locales and localized
	description
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param language the template's script language. For more information,
	see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param script the template's script
	* @param cacheable whether the template is cacheable
	* @param smallImage whether the template has a small image
	* @param smallImageURL the template's small image URL (optionally
	<code>null</code>)
	* @param smallImageFile the template's small image file (optionally
	<code>null</code>)
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated template
	* @throws PortalException if the user did not have permission to update the
	template or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
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
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DDMTemplateService getWrappedDDMTemplateService() {
		return _ddmTemplateService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDDMTemplateService(
		DDMTemplateService ddmTemplateService) {
		_ddmTemplateService = ddmTemplateService;
	}

	@Override
	public DDMTemplateService getWrappedService() {
		return _ddmTemplateService;
	}

	@Override
	public void setWrappedService(DDMTemplateService ddmTemplateService) {
		_ddmTemplateService = ddmTemplateService;
	}

	private DDMTemplateService _ddmTemplateService;
}