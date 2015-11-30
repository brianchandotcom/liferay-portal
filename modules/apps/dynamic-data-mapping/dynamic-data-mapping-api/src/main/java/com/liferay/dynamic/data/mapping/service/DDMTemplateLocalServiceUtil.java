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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMTemplate. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLocalService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m template to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was added
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate addDDMTemplate(
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate) {
		return getService().addDDMTemplate(ddmTemplate);
	}

	/**
	* Adds a template.
	*
	* @param userId the primary key of the template's creator/owner
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the template.
	* @return the template
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate addTemplate(
		long userId, long groupId, long classNameId, long classPK,
		long resourceClassNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addTemplate(userId, groupId, classNameId, classPK,
			resourceClassNameId, nameMap, descriptionMap, type, mode, language,
			script, serviceContext);
	}

	/**
	* Adds a template with additional parameters.
	*
	* @param userId the primary key of the template's creator/owner
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
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
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the template.
	* @return the template
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate addTemplate(
		long userId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String templateKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addTemplate(userId, groupId, classNameId, classPK,
			resourceClassNameId, templateKey, nameMap, descriptionMap, type,
			mode, language, script, cacheable, smallImage, smallImageURL,
			smallImageFile, serviceContext);
	}

	/**
	* Adds the resources to the template.
	*
	* @param template the template to add resources to
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	*/
	public static void addTemplateResources(
		com.liferay.dynamic.data.mapping.model.DDMTemplate template,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addTemplateResources(template, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds the model resources with the permissions to the template.
	*
	* @param template the template to add resources to
	* @param modelPermissions the model permissions to be added
	*/
	public static void addTemplateResources(
		com.liferay.dynamic.data.mapping.model.DDMTemplate template,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addTemplateResources(template, modelPermissions);
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
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate copyTemplate(
		long userId, long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .copyTemplate(userId, templateId, nameMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate copyTemplate(
		long userId, long templateId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
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
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param serviceContext the service context to be applied. Can set the
	creation date, modification date, guest permissions, and group
	permissions for the new templates.
	* @return the new templates
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> copyTemplates(
		long userId, long classNameId, long oldClassPK, long newClassPK,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .copyTemplates(userId, classNameId, oldClassPK, newClassPK,
			type, serviceContext);
	}

	/**
	* Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	*
	* @param templateId the primary key for the new d d m template
	* @return the new d d m template
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate createDDMTemplate(
		long templateId) {
		return getService().createDDMTemplate(templateId);
	}

	/**
	* Deletes the d d m template from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was removed
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate deleteDDMTemplate(
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate) {
		return getService().deleteDDMTemplate(ddmTemplate);
	}

	/**
	* Deletes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template that was removed
	* @throws PortalException if a d d m template with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate deleteDDMTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMTemplate(templateId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the template and its resources.
	*
	* @param template the template to be deleted
	*/
	public static void deleteTemplate(
		com.liferay.dynamic.data.mapping.model.DDMTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTemplate(template);
	}

	/**
	* Deletes the template and its resources.
	*
	* @param templateId the primary key of the template to be deleted
	*/
	public static void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTemplate(templateId);
	}

	/**
	* Deletes all the templates of the group.
	*
	* @param groupId the primary key of the group
	*/
	public static void deleteTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTemplates(groupId);
	}

	public static void deleteTemplates(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTemplates(groupId, classNameId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate fetchDDMTemplate(
		long templateId) {
		return getService().fetchDDMTemplate(templateId);
	}

	/**
	* Returns the d d m template matching the UUID and group.
	*
	* @param uuid the d d m template's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate fetchDDMTemplateByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDDMTemplateByUuidAndGroupId(uuid, groupId);
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
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey) {
		return getService().fetchTemplate(groupId, classNameId, templateKey);
	}

	/**
	* Returns the template matching the group and template key, optionally
	* searching ancestor sites (that have sharing enabled) and global scoped
	* sites.
	*
	* <p>
	* This method first searches in the given group. If the template is still
	* not found and <code>includeAncestorTemplates</code> is set to
	* <code>true</code>, this method searches the group's ancestor sites (that
	* have sharing enabled) and lastly searches global scoped sites.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeAncestorTemplates whether to include ancestor sites (that
	have sharing enabled) and include global scoped sites in the
	search in the search
	* @return the matching template, or <code>null</code> if a matching
	template could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate fetchTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeAncestorTemplates)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchTemplate(groupId, classNameId, templateKey,
			includeAncestorTemplates);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the d d m template with the primary key.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template
	* @throws PortalException if a d d m template with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMTemplate(templateId);
	}

	/**
	* Returns the d d m template matching the UUID and group.
	*
	* @param uuid the d d m template's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m template
	* @throws PortalException if a matching d d m template could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplateByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMTemplateByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of d d m templates
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getDDMTemplates(
		int start, int end) {
		return getService().getDDMTemplates(start, end);
	}

	/**
	* Returns all the d d m templates matching the UUID and company.
	*
	* @param uuid the UUID of the d d m templates
	* @param companyId the primary key of the company
	* @return the matching d d m templates, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getDDMTemplatesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getDDMTemplatesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of d d m templates matching the UUID and company.
	*
	* @param uuid the UUID of the d d m templates
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m templates, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getDDMTemplatesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .getDDMTemplatesByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of d d m templates.
	*
	* @return the number of d d m templates
	*/
	public static int getDDMTemplatesCount() {
		return getService().getDDMTemplatesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the template matching the group and template key.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @return the matching template
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplate(groupId, classNameId, templateKey);
	}

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
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param templateKey the unique string identifying the template
	* @param includeAncestorTemplates whether to include ancestor sites (that
	have sharing enabled) and include global scoped sites in the
	search in the search
	* @return the matching template
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long groupId, long classNameId, java.lang.String templateKey,
		boolean includeAncestorTemplates)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getTemplate(groupId, classNameId, templateKey,
			includeAncestorTemplates);
	}

	/**
	* Returns the template with the ID.
	*
	* @param templateId the primary key of the template
	* @return the template with the ID
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplate(templateId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplateBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateBySmallImageId(smallImageId);
	}

	/**
	* Returns all the templates with the class PK.
	*
	* @param classPK the primary key of the template's related entity
	* @return the templates with the class PK
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long classPK) {
		return getService().getTemplates(classPK);
	}

	/**
	* Returns all the templates matching the group and class name ID.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @return the matching templates
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId) {
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
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK) {
		return getService().getTemplates(groupId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK,
		boolean includeAncestorTemplates)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getTemplates(groupId, classNameId, classPK,
			includeAncestorTemplates);
	}

	/**
	* Returns all the templates matching the group, class name ID, class PK,
	* and type.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @return the matching templates
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type) {
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
	* @param type the template's type. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @param mode the template's mode. For more information, see
	DDMTemplateConstants in the dynamic-data-mapping-api module.
	* @return the matching templates
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode) {
		return getService()
				   .getTemplates(groupId, classNameId, classPK, type, mode);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
		long[] groupIds, long classNameId, long classPK) {
		return getService().getTemplates(groupIds, classNameId, classPK);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByClassPK(
		long groupId, long classPK) {
		return getService().getTemplatesByClassPK(groupId, classPK);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByClassPK(
		long groupId, long classPK, int start, int end) {
		return getService().getTemplatesByClassPK(groupId, classPK, start, end);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByClassPK(
		long[] groupIds, long classPK) {
		return getService().getTemplatesByClassPK(groupIds, classPK);
	}

	/**
	* Returns the number of templates matching the group and class PK.
	*
	* @param groupId the primary key of the group
	* @param classPK the primary key of the template's related entity
	* @return the number of templates belonging to the group and class PK
	*/
	public static int getTemplatesByClassPKCount(long groupId, long classPK) {
		return getService().getTemplatesByClassPKCount(groupId, classPK);
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
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplatesByStructureClassNameId(
		long groupId, long structureClassNameId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .getTemplatesByStructureClassNameId(groupId,
			structureClassNameId, status, start, end, orderByComparator);
	}

	/**
	* Returns the number of templates matching the group and structure class
	* name ID, including Generic Templates.
	*
	* @param groupId the primary key of the group
	* @param structureClassNameId the primary key of the class name for the
	template's related structure
	* @return the number of matching templates
	*/
	public static int getTemplatesByStructureClassNameIdCount(long groupId,
		long structureClassNameId, int status) {
		return getService()
				   .getTemplatesByStructureClassNameIdCount(groupId,
			structureClassNameId, status);
	}

	/**
	* Returns the number of templates belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the number of templates belonging to the group
	*/
	public static int getTemplatesCount(long groupId) {
		return getService().getTemplatesCount(groupId);
	}

	/**
	* Returns the number of templates matching the group and class name ID.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @return the number of matching templates
	*/
	public static int getTemplatesCount(long groupId, long classNameId) {
		return getService().getTemplatesCount(groupId, classNameId);
	}

	/**
	* Returns the number of templates matching the group, class name ID, and
	* class PK.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the template's
	related model
	* @param classPK the primary key of the template's related entity
	* @return the number of matching templates
	*/
	public static int getTemplatesCount(long groupId, long classNameId,
		long classPK) {
		return getService().getTemplatesCount(groupId, classNameId, classPK);
	}

	public static void revertTemplate(long userId, long templateId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().revertTemplate(userId, templateId, version, serviceContext);
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
	* @return the range of matching templates ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .search(companyId, groupId, classNameId, classPK,
			resourceClassNameId, keywords, type, mode, status, start, end,
			orderByComparator);
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
	one field
	* @param start the lower bound of the range of templates to return
	* @param end the upper bound of the range of templates to return (not
	inclusive)
	* @param orderByComparator the comparator to order the templates
	(optionally <code>null</code>)
	* @return the range of matching templates ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .search(companyId, groupId, classNameId, classPK,
			resourceClassNameId, name, description, type, mode, language,
			status, andOperator, start, end, orderByComparator);
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
	* @return the range of matching templates ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, classNameIds, classPKs,
			resourceClassNameId, keywords, type, mode, status, start, end,
			orderByComparator);
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
	* @return the range of matching templates ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplate> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, classNameIds, classPKs,
			resourceClassNameId, name, description, type, mode, language,
			status, andOperator, start, end, orderByComparator);
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
	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, long resourceClassNameId,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int status) {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK,
			resourceClassNameId, keywords, type, mode, status);
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
	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, long resourceClassNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, int status, boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK,
			resourceClassNameId, name, description, type, mode, language,
			status, andOperator);
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
	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int status) {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPKs,
			resourceClassNameId, keywords, type, mode, status);
	}

	/**
	* Returns the number of templates matching the group IDs, class name IDs,
	* class PKs, name keyword, description keyword, type, mode, and language.
	*
	* @param companyId the primary key of the templates company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the entity's instance the
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
	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, int status, boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPKs,
			resourceClassNameId, name, description, type, mode, language,
			status, andOperator);
	}

	/**
	* Updates the d d m template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplate the d d m template
	* @return the d d m template that was updated
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate updateDDMTemplate(
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate) {
		return getService().updateDDMTemplate(ddmTemplate);
	}

	/**
	* Updates the template matching the ID.
	*
	* @param userId the primary key of the template's creator/owner
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
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate updateTemplate(
		long userId, long templateId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateTemplate(userId, templateId, classPK, nameMap,
			descriptionMap, type, mode, language, script, cacheable,
			serviceContext);
	}

	/**
	* Updates the template matching the ID.
	*
	* @param userId the primary key of the template's creator/owner
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
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplate updateTemplate(
		long userId, long templateId, long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateTemplate(userId, templateId, classPK, nameMap,
			descriptionMap, type, mode, language, script, cacheable,
			smallImage, smallImageURL, smallImageFile, serviceContext);
	}

	public static DDMTemplateLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMTemplateLocalService service) {
	}

	private static ServiceTracker<DDMTemplateLocalService, DDMTemplateLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMTemplateLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDMTemplateLocalService, DDMTemplateLocalService>(bundle.getBundleContext(),
				DDMTemplateLocalService.class, null);

		_serviceTracker.open();
	}
}