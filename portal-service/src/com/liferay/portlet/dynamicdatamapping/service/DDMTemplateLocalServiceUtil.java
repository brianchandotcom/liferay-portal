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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m template local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
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
	* Returns the d d m template with the UUID in the group.
	*
	* @param uuid the UUID of d d m template
	* @param groupId the group id of the d d m template
	* @return the d d m template
	* @throws PortalException if a d d m template with the UUID in the group could not be found
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

	public static void addTemplateResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addTemplateResources(template, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addTemplateResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addTemplateResources(template, groupPermissions, guestPermissions);
	}

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

	public static void deleteTemplate(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplate(template);
	}

	public static void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplate(templateId);
	}

	public static void deleteTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplates(groupId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTemplate(groupId, templateKey);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplate(templateId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplate(groupId, templateKey);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long groupId, java.lang.String templateKey,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTemplate(groupId, templateKey, includeGlobalTemplates);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(classPK);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(groupId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long classNameId, long classPK, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(classNameId, classPK, type);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(classNameId, classPK, type, mode);
	}

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

	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK,
			keywords, type, mode);
	}

	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, classNameId, classPK, name,
			description, type, mode, language, andOperator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPK,
			keywords, type, mode);
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, classPK,
			name, description, type, mode, language, andOperator);
	}

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
	 * @deprecated
	 */
	public void setService(DDMTemplateLocalService service) {
	}

	private static DDMTemplateLocalService _service;
}