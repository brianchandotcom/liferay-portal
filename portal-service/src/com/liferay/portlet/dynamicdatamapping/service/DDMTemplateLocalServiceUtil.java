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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DDMTemplate. This utility wraps
 * {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl
 * @generated
 */
public class DDMTemplateLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m template to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addDDMTemplate(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMTemplate(ddmTemplate);
	}

	/**
	* Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	*
	* @param templateId the primary key for the new d d m template
	* @return the new d d m template
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate createDDMTemplate(
		long templateId) {
		return getService().createDDMTemplate(templateId);
	}

	/**
	* Deletes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template that was removed
	* @throws PortalException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate deleteDDMTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDMTemplate(templateId);
	}

	/**
	* Deletes the d d m template from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate deleteDDMTemplate(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDMTemplate(ddmTemplate);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchDDMTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDDMTemplate(templateId);
	}

	/**
	* Returns the d d m template with the primary key.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template
	* @throws PortalException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getDDMTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMTemplate(templateId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the d d m template matching the UUID and group.
	*
	* @param uuid the d d m template's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m template
	* @throws PortalException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getDDMTemplateByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMTemplateByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getDDMTemplates(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMTemplates(start, end);
	}

	/**
	* Returns the number of d d m templates.
	*
	* @return the number of d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMTemplatesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMTemplatesCount();
	}

	/**
	* Updates the d d m template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateDDMTemplate(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMTemplate(ddmTemplate);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a template.
	*
	* @param userId the primary key of the template's creator/owner
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the template.
	* @return the template
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		long userId, long groupId, long classNameId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTemplate(userId, groupId, classNameId, classPK, nameMap,
			descriptionMap, type, mode, language, script, serviceContext);
	}

	/**
	* Adds a template with additional parameters.
	*
	* @param userId the primary key of the template's creator/owner
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the template.
	* @return the template
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		long userId, long groupId, long classNameId, long classPK,
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
		return getService()
				   .addTemplate(userId, groupId, classNameId, classPK,
			templateKey, nameMap, descriptionMap, type, mode, language, script,
			cacheable, smallImage, smallImageURL, smallImageFile, serviceContext);
	}

	/**
	* Adds the resources to the template.
	*
	* @param template the template to add resources to
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addTemplateResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addTemplateResources(template, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds the model resources with the permissions to the template.
	*
	* @param template the template to add resources to
	* @param groupPermissions the group permissions to be added
	* @param guestPermissions the guest permissions to be added
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void addTemplateResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addTemplateResources(template, groupPermissions, guestPermissions);
	}

	/**
	* Copies the template, creating a new template with all the values
	* extracted from the original one. This method supports defining a new name
	* and description.
	*
	* @param userId the primary key of the template's creator/owner
	* @param templateId the primary key of the template to be copied
	* @param nameMap the new template's locales and localized names
	* @param descriptionMap the new template's locales and localized
	descriptions
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the template.
	* @return the new template
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		long userId, long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyTemplate(userId, templateId, nameMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		long userId, long templateId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().copyTemplate(userId, templateId, serviceContext);
	}

	/**
	* Copies all the templates matching the class name ID, class PK, and type.
	* This method creates new templates, extracting all the values from the old
	* ones and updating their class PKs.
	*
	* @param userId the primary key of the template's creator/owner
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param oldClassPK the primary key of the old template's related entity
	* @param newClassPK the primary key of the new template's related entity
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param serviceContext the service context to be applied. Can set the
	creation date, modification date, guest permissions, and group
	permissions for the new templates.
	* @return the new templates
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> copyTemplates(
		long userId, long classNameId, long oldClassPK, long newClassPK,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyTemplates(userId, classNameId, oldClassPK, newClassPK,
			type, serviceContext);
	}

	/**
	* Deletes the template and its resources.
	*
	* @param template the template to be deleted
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTemplate(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplate(template);
	}

	/**
	* Deletes the template and its resources.
	*
	* @param templateId the primary key of the template to be deleted
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplate(templateId);
	}

	/**
	* Deletes all the templates of the group.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplates(groupId);
	}

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template, or <code>null</code> if a matching
	template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTemplate(groupId, classNameId, templateKey);
	}

	/**
	* Returns the template matching the group and template key, optionally in
	* the global scope.
	*
	* <p>
	* This method first searches in the given group. If the template is still
	* not found and <code>includeGlobalTemplates</code> is set to
	* <code>true</code>, this method searches the global group.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeGlobalTemplates whether to include the global scope in the
	search
	* @return the matching template, or <code>null</code> if a matching
	template could not be found
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchTemplate(groupId, classNameId, templateKey,
			includeGlobalTemplates);
	}

	/**
	* Returns the template matching the UUID and group.
	*
	* @param uuid the unique string identifying the template
	* @param groupId the primary key of the group
	* @return the matching template, or <code>null</code> if a matching
	template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTemplate(uuid, groupId);
	}

	/**
	* Returns the template with the ID.
	*
	* @param templateId the primary key of the template
	* @return the template with the ID
	* @throws PortalException if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplate(templateId);
	}

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplate(groupId, classNameId, templateKey);
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
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeGlobalTemplates whether to include the global scope in the
	search
	* @return the matching template
	* @throws PortalException if a matching template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTemplate(groupId, classNameId, templateKey,
			includeGlobalTemplates);
	}

	/**
	* Returns all the templates with the class PK.
	*
	* @param classPK the primary key of the template's related entity
	* @return the templates with the class PK
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(classPK);
	}

	/**
	* Returns all the templates matching the group and class name ID.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(groupId, classNameId);
	}

	/**
	* Returns all the templates matching the group, class name ID, and class
	* PK.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(groupId, classNameId, classPK);
	}

	/**
	* Returns all the templates matching the group, class name ID, class PK,
	* and type.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(groupId, classNameId, classPK, type);
	}

	/**
	* Returns all the templates matching the group, class name ID, class PK,
	* type, and mode.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param type the template's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @param mode the template's mode. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTemplates(groupId, classNameId, classPK, type, mode);
	}

	/**
	* Returns all the templates matching the group and class PK.
	*
	* @param groupId the primary key of the group
	* @param classPK the primary key of the template's related entity
	* @return the matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByClassPK(
		long groupId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplatesByClassPK(groupId, classPK);
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
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByStructureClassNameId(
		long groupId, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTemplatesByStructureClassNameId(groupId,
			structureClassNameId, start, end, orderByComparator);
	}

	/**
	* Returns the number of templates belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the number of templates belonging to the group
	* @throws SystemException if a system exception occurred
	*/
	public static int getTemplatesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplatesCount(groupId);
	}

	/**
	* Returns the number of templates matching the group and class name ID.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @return the number of matching templates
	* @throws SystemException if a system exception occurred
	*/
	public static int getTemplatesCount(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplatesCount(groupId, classNameId);
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
	* @param classNameId the primary key of the class name for the template's
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
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the range of matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, classNameId, classPK, keywords,
			type, mode, start, end, orderByComparator);
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
	* @param classNameId the primary key of the class name for the template's
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
	one field
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the range of matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, classNameId, classPK, name,
			description, type, mode, language, andOperator, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the templates matching the group IDs,
	* class Name IDs, class PK, type, and mode, and include the keywords on its
	* names and descriptions.
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
	* @param classPK the primary key of the template's related entity
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
	* @return the range of matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, classNameIds, classPK,
			keywords, type, mode, start, end, orderByComparator);
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
	* @return the range of matching templates ordered by the comparator
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, classNameIds, classPK, name,
			description, type, mode, language, andOperator, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of templates matching the group, class name ID, class
	* PK, type, and matching the keywords in the template names and
	* descriptions.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK,
			keywords, type, mode);
	}

	/**
	* Returns the number of templates matching the group, class name ID, class
	* PK, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the template's company
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK, name,
			description, type, mode, language, andOperator);
	}

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PK, type, and mode, and matching the keywords in the template names
	* and descriptions.
	*
	* @param companyId the primary key of the template's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instance the
	templates are related to
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
	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPK,
			keywords, type, mode);
	}

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PKs, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the templates company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instance the
	templates are related to
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
	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPK,
			name, description, type, mode, language, andOperator);
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
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateTemplate(
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
		return getService()
				   .updateTemplate(templateId, nameMap, descriptionMap, type,
			mode, language, script, cacheable, smallImage, smallImageURL,
			smallImageFile, serviceContext);
	}

	public static DDMTemplateLocalService getService() {
		if (_service == null) {
			_service = (DDMTemplateLocalService)PortalBeanLocatorUtil.locate(DDMTemplateLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMTemplateLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DDMTemplateLocalService service) {
	}

	private static DDMTemplateLocalService _service;
}