/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Definition. This utility wraps
 * {@link com.liferay.portal.reports.engine.console.service.impl.DefinitionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionLocalService
 * @see com.liferay.portal.reports.engine.console.service.base.DefinitionLocalServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.DefinitionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DefinitionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.DefinitionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the definition to the database. Also notifies the appropriate model listeners.
	*
	* @param definition the definition
	* @return the definition that was added
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition addDefinition(
		com.liferay.portal.reports.engine.console.model.Definition definition) {
		return getService().addDefinition(definition);
	}

	public static com.liferay.portal.reports.engine.console.model.Definition addDefinition(
		long userId, long groupId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDefinition(userId, groupId, nameMap, descriptionMap,
			sourceId, reportParameters, fileName, inputStream, serviceContext);
	}

	/**
	* Creates a new definition with the primary key. Does not add the definition to the database.
	*
	* @param definitionId the primary key for the new definition
	* @return the new definition
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition createDefinition(
		long definitionId) {
		return getService().createDefinition(definitionId);
	}

	/**
	* Deletes the definition from the database. Also notifies the appropriate model listeners.
	*
	* @param definition the definition
	* @return the definition that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition deleteDefinition(
		com.liferay.portal.reports.engine.console.model.Definition definition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDefinition(definition);
	}

	/**
	* Deletes the definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param definitionId the primary key of the definition
	* @return the definition that was removed
	* @throws PortalException if a definition with the primary key could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition deleteDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDefinition(definitionId);
	}

	public static void deleteDefinitions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDefinitions(groupId);
	}

	public static void deleteDefinitionTemplates(long companyId,
		String attachmentsDirectory)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDefinitionTemplates(companyId, attachmentsDirectory);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.reports.engine.console.model.Definition fetchDefinition(
		long definitionId) {
		return getService().fetchDefinition(definitionId);
	}

	/**
	* Returns the definition matching the UUID and group.
	*
	* @param uuid the definition's UUID
	* @param groupId the primary key of the group
	* @return the matching definition, or <code>null</code> if a matching definition could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition fetchDefinitionByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchDefinitionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the definition with the primary key.
	*
	* @param definitionId the primary key of the definition
	* @return the definition
	* @throws PortalException if a definition with the primary key could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition getDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefinition(definitionId);
	}

	/**
	* Returns the definition matching the UUID and group.
	*
	* @param uuid the definition's UUID
	* @param groupId the primary key of the group
	* @return the matching definition
	* @throws PortalException if a matching definition could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition getDefinitionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefinitionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of definitions
	* @param end the upper bound of the range of definitions (not inclusive)
	* @return the range of definitions
	*/
	public static java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitions(
		int start, int end) {
		return getService().getDefinitions(start, end);
	}

	public static java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitions(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getDefinitions(groupId, definitionName, description,
			sourceId, reportName, andSearch, start, end, orderByComparator);
	}

	/**
	* Returns all the definitions matching the UUID and company.
	*
	* @param uuid the UUID of the definitions
	* @param companyId the primary key of the company
	* @return the matching definitions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService().getDefinitionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of definitions matching the UUID and company.
	*
	* @param uuid the UUID of the definitions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of definitions
	* @param end the upper bound of the range of definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching definitions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Definition> orderByComparator) {
		return getService()
				   .getDefinitionsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of definitions.
	*
	* @return the number of definitions
	*/
	public static int getDefinitionsCount() {
		return getService().getDefinitionsCount();
	}

	public static int getDefinitionsCount(long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch) {
		return getService()
				   .getDefinitionsCount(groupId, definitionName, description,
			sourceId, reportName, andSearch);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param definition the definition
	* @return the definition that was updated
	*/
	public static com.liferay.portal.reports.engine.console.model.Definition updateDefinition(
		com.liferay.portal.reports.engine.console.model.Definition definition) {
		return getService().updateDefinition(definition);
	}

	public static com.liferay.portal.reports.engine.console.model.Definition updateDefinition(
		long definitionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDefinition(definitionId, nameMap, descriptionMap,
			sourceId, reportParameters, fileName, inputStream, serviceContext);
	}

	public static void updateDefinitionResources(
		com.liferay.portal.reports.engine.console.model.Definition definition,
		String[] communityPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateDefinitionResources(definition, communityPermissions,
			guestPermissions);
	}

	public static DefinitionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DefinitionLocalService, DefinitionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DefinitionLocalService.class);

		ServiceTracker<DefinitionLocalService, DefinitionLocalService> serviceTracker =
			new ServiceTracker<DefinitionLocalService, DefinitionLocalService>(bundle.getBundleContext(),
				DefinitionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}