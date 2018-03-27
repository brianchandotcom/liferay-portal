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

package com.liferay.asset.display.template.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetDisplayTemplate. This utility wraps
 * {@link com.liferay.asset.display.template.service.impl.AssetDisplayTemplateLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplateLocalService
 * @see com.liferay.asset.display.template.service.base.AssetDisplayTemplateLocalServiceBaseImpl
 * @see com.liferay.asset.display.template.service.impl.AssetDisplayTemplateLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.display.template.service.impl.AssetDisplayTemplateLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset display template to the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplate the asset display template
	* @return the asset display template that was added
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate addAssetDisplayTemplate(
		com.liferay.asset.display.template.model.AssetDisplayTemplate assetDisplayTemplate) {
		return getService().addAssetDisplayTemplate(assetDisplayTemplate);
	}

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate addAssetDisplayTemplate(
		long groupId, long userId, java.lang.String name, long classNameId,
		java.lang.String language, java.lang.String scriptContent,
		boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAssetDisplayTemplate(groupId, userId, name, classNameId,
			language, scriptContent, main, serviceContext);
	}

	/**
	* Creates a new asset display template with the primary key. Does not add the asset display template to the database.
	*
	* @param assetDisplayTemplateId the primary key for the new asset display template
	* @return the new asset display template
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate createAssetDisplayTemplate(
		long assetDisplayTemplateId) {
		return getService().createAssetDisplayTemplate(assetDisplayTemplateId);
	}

	/**
	* Deletes the asset display template from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplate the asset display template
	* @return the asset display template that was removed
	* @throws PortalException
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate deleteAssetDisplayTemplate(
		com.liferay.asset.display.template.model.AssetDisplayTemplate assetDisplayTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetDisplayTemplate(assetDisplayTemplate);
	}

	/**
	* Deletes the asset display template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template that was removed
	* @throws PortalException if a asset display template with the primary key could not be found
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate deleteAssetDisplayTemplate(
		long assetDisplayTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetDisplayTemplate(assetDisplayTemplateId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.template.model.impl.AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.template.model.impl.AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate fetchAssetDisplayTemplate(
		long assetDisplayTemplateId) {
		return getService().fetchAssetDisplayTemplate(assetDisplayTemplateId);
	}

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate fetchAssetDisplayTemplate(
		long groupId, long classNameId) {
		return getService().fetchAssetDisplayTemplate(groupId, classNameId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the asset display template with the primary key.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template
	* @throws PortalException if a asset display template with the primary key could not be found
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate getAssetDisplayTemplate(
		long assetDisplayTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetDisplayTemplate(assetDisplayTemplateId);
	}

	/**
	* Returns a range of all the asset display templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.template.model.impl.AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of asset display templates
	*/
	public static java.util.List<com.liferay.asset.display.template.model.AssetDisplayTemplate> getAssetDisplayTemplates(
		int start, int end) {
		return getService().getAssetDisplayTemplates(start, end);
	}

	public static java.util.List<com.liferay.asset.display.template.model.AssetDisplayTemplate> getAssetDisplayTemplates(
		long groupId) {
		return getService().getAssetDisplayTemplates(groupId);
	}

	public static java.util.List<com.liferay.asset.display.template.model.AssetDisplayTemplate> getAssetDisplayTemplates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.display.template.model.AssetDisplayTemplate> orderByComparator) {
		return getService()
				   .getAssetDisplayTemplates(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.asset.display.template.model.AssetDisplayTemplate> getAssetDisplayTemplates(
		long groupId, java.lang.String name) {
		return getService().getAssetDisplayTemplates(groupId, name);
	}

	public static java.util.List<com.liferay.asset.display.template.model.AssetDisplayTemplate> getAssetDisplayTemplates(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.display.template.model.AssetDisplayTemplate> orderByComparator) {
		return getService()
				   .getAssetDisplayTemplates(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of asset display templates.
	*
	* @return the number of asset display templates
	*/
	public static int getAssetDisplayTemplatesCount() {
		return getService().getAssetDisplayTemplatesCount();
	}

	public static int getAssetDisplayTemplatesCount(long groupId) {
		return getService().getAssetDisplayTemplatesCount(groupId);
	}

	public static int getAssetDisplayTemplatesCount(long groupId,
		java.lang.String name) {
		return getService().getAssetDisplayTemplatesCount(groupId, name);
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset display template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplate the asset display template
	* @return the asset display template that was updated
	*/
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate updateAssetDisplayTemplate(
		com.liferay.asset.display.template.model.AssetDisplayTemplate assetDisplayTemplate) {
		return getService().updateAssetDisplayTemplate(assetDisplayTemplate);
	}

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate updateAssetDisplayTemplate(
		long assetDisplayTemplateId, java.lang.String name, long classNameId,
		java.lang.String language, java.lang.String scriptContent,
		boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateAssetDisplayTemplate(assetDisplayTemplateId, name,
			classNameId, language, scriptContent, main, serviceContext);
	}

	public static AssetDisplayTemplateLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetDisplayTemplateLocalService, AssetDisplayTemplateLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetDisplayTemplateLocalService.class);

		ServiceTracker<AssetDisplayTemplateLocalService, AssetDisplayTemplateLocalService> serviceTracker =
			new ServiceTracker<AssetDisplayTemplateLocalService, AssetDisplayTemplateLocalService>(bundle.getBundleContext(),
				AssetDisplayTemplateLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}