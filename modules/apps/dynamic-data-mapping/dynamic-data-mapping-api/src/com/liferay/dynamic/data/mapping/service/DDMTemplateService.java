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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for DDMTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=ddm", "json.web.service.context.path=DDMTemplate"}, service = DDMTemplateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMTemplateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateServiceUtil} to access the d d m template remote service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds a template.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param nameMap the template's locales and localized names
	* @param descriptionMap the template's locales and localized descriptions
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param mode the template's mode. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param language the template's script language. For more information,
	see DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param script the template's script
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	*/
	public com.liferay.dynamic.data.mapping.model.DDMTemplate addTemplate(
		long groupId, long classNameId, long classPK, long resourceClassNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a template with additional parameters.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param templateKey the unique string identifying the template
	(optionally <code>null</code>)
	* @param nameMap the template's locales and localized names
	* @param descriptionMap the template's locales and localized descriptions
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param mode the template's mode. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param language the template's script language. For more information,
	see DDMTemplateConstants in the dynamic-data-mapping-api module.
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
	*/
	public com.liferay.dynamic.data.mapping.model.DDMTemplate addTemplate(
		long groupId, long classNameId, long classPK, long resourceClassNameId,
		java.lang.String templateKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	*/
	public com.liferay.dynamic.data.mapping.model.DDMTemplate copyTemplate(
		long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMTemplate copyTemplate(
		long templateId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Copies all the templates matching the class name ID, class PK, and type.
	* This method creates new templates, extracting all the values from the old
	* ones and updating their class PKs.
	*
	* @param classNameId the primary key of the class name for template's
	related model
	* @param oldClassPK the primary key of the old template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param newClassPK the primary key of the new template's related entity
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param serviceContext the service context to be applied. Must have the
	<code>ddmResource</code> attribute to check permissions. Can set
	the UUID, creation date, modification date, guest permissions,
	and group permissions for the template.
	* @return the new template
	* @throws PortalException if the user did not have permission to add the
	template or if a portal exception occurred
	*/
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> copyTemplates(
		long classNameId, long oldClassPK, long resourceClassNameId,
		long newClassPK, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Deletes the template and its resources.
	*
	* @param templateId the primary key of the template to be deleted
	* @throws PortalException if the user did not have permission to delete the
	template or if a portal exception occurred
	*/
	public void deleteTemplate(long templateId) throws PortalException;

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws PortalException;

	/**
	* Returns the template matching the group and template key, optionally
	* searching ancestor sites (that have sharing enabled) and global scoped
	* sites.
	*
	* <p>
	* This method first searches in the group. If the template is still not
	* found and <code>includeAncestorTemplates</code> is set to
	* <code>true</code>, this method searches the group's ancestor sites (that
	* have sharing enabled) and lastly searches global scoped sites.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeAncestorTemplates whether to include ancestor sites (that
	have sharing enabled) and include global scoped sites in the
	search
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeAncestorTemplates) throws PortalException;

	/**
	* Returns the template with the ID.
	*
	* @param templateId the primary key of the template
	* @return the template with the ID
	* @throws PortalException if the user did not have permission to view the
	template or if a matching template could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long templateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, boolean includeAncestorTemplates, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String type, java.lang.String mode,
		int status);

	/**
	* Returns all the templates matching the group, class name ID, class PK,
	* resource class name ID, and type.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for the
	template's resource model
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @return the matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long companyId, long groupId, long classNameId,
		long resourceClassNameId, int status);

	/**
	* Returns all the templates matching the group, class PK, and resource
	* class name ID.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for the
	template's resource model
	* @param status
	* @return the matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByClassPK(
		long companyId, long groupId, long classPK, long resourceClassNameId,
		int status);

	/**
	* Returns an ordered range of all the templates matching the group and
	* structure class name ID and all the generic templates matching the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param structureClassNameId the primary key of the class name for the
	template's related structure (optionally <code>0</code>). Specify
	<code>0</code> to return generic templates only.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the range of matching templates ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByStructureClassNameId(
		long groupId, long structureClassNameId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator);

	/**
	* Returns the number of templates matching the group and structure class
	* name ID plus the number of generic templates matching the group.
	*
	* @param groupId the primary key of the group
	* @param structureClassNameId the primary key of the class name for the
	template's related structure (optionally <code>0</code>). Specify
	<code>0</code> to count generic templates only.
	* @return the number of matching templates plus the number of matching
	generic templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTemplatesByStructureClassNameIdCount(long groupId,
		long structureClassNameId, int status);

	public void revertTemplate(long templateId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator);

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
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator);

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
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator);

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
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the matching templates ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator);

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
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @return the number of matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, long resourceClassNameId, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int status);

	/**
	* Returns the number of templates matching the group, class name ID, class
	* PK, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @return the number of matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId, long classNameId,
		long classPK, long resourceClassNameId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, int status,
		boolean andOperator);

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
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param keywords the keywords (space separated), which may occur in the
	template's name or description (optionally <code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @return the number of matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int status);

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PK, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instances the
	templates are related to
	* @param classPKs the primary keys of the template's related entities
	* @param resourceClassNameId the primary key of the class name for
	template's resource model
	* @param name the name keywords (optionally <code>null</code>)
	* @param description the description keywords (optionally
	<code>null</code>)
	* @param type the template's type (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param mode the template's mode (optionally <code>null</code>). For more
	information, see DDMTemplateConstants in the
	dynamic-data-mapping-api module.
	* @param language the template's script language (optionally
	<code>null</code>). For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param andOperator whether every field must match its keywords, or just
	one field.
	* @return the number of matching templates
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, int status, boolean andOperator);

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Updates the template matching the ID.
	*
	* @param templateId the primary key of the template
	* @param classPK the primary key of the template's related entity
	* @param nameMap the template's new locales and localized names
	* @param descriptionMap the template's new locales and localized
	description
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param mode the template's mode. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param language the template's script language. For more information,
	see DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param script the template's script
	* @param cacheable whether the template is cacheable
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated template
	* @throws PortalException if the user did not have permission to update the
	template or if a portal exception occurred
	*/
	public com.liferay.dynamic.data.mapping.model.DDMTemplate updateTemplate(
		long templateId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the template matching the ID.
	*
	* @param templateId the primary key of the template
	* @param classPK the primary key of the template's related entity
	* @param nameMap the template's new locales and localized names
	* @param descriptionMap the template's new locales and localized
	description
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param mode the template's mode. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param language the template's script language. For more information,
	see DDMTemplateConstants in the dynamic-data-mapping-api module.
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
	*/
	public com.liferay.dynamic.data.mapping.model.DDMTemplate updateTemplate(
		long templateId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}