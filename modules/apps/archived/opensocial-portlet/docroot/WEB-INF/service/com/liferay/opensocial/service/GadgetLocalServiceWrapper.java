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

package com.liferay.opensocial.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GadgetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GadgetLocalService
 * @generated
 */
public class GadgetLocalServiceWrapper
	implements GadgetLocalService, ServiceWrapper<GadgetLocalService> {

	public GadgetLocalServiceWrapper(GadgetLocalService gadgetLocalService) {
		_gadgetLocalService = gadgetLocalService;
	}

	/**
	 * Adds the gadget to the database. Also notifies the appropriate model listeners.
	 *
	 * @param gadget the gadget
	 * @return the gadget that was added
	 */
	@Override
	public com.liferay.opensocial.model.Gadget addGadget(
		com.liferay.opensocial.model.Gadget gadget) {

		return _gadgetLocalService.addGadget(gadget);
	}

	@Override
	public com.liferay.opensocial.model.Gadget addGadget(
			long companyId, String url, String portletCategoryNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.addGadget(
			companyId, url, portletCategoryNames, serviceContext);
	}

	/**
	 * Creates a new gadget with the primary key. Does not add the gadget to the database.
	 *
	 * @param gadgetId the primary key for the new gadget
	 * @return the new gadget
	 */
	@Override
	public com.liferay.opensocial.model.Gadget createGadget(long gadgetId) {
		return _gadgetLocalService.createGadget(gadgetId);
	}

	/**
	 * Deletes the gadget from the database. Also notifies the appropriate model listeners.
	 *
	 * @param gadget the gadget
	 * @return the gadget that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.opensocial.model.Gadget deleteGadget(
			com.liferay.opensocial.model.Gadget gadget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.deleteGadget(gadget);
	}

	/**
	 * Deletes the gadget with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param gadgetId the primary key of the gadget
	 * @return the gadget that was removed
	 * @throws PortalException if a gadget with the primary key could not be found
	 */
	@Override
	public com.liferay.opensocial.model.Gadget deleteGadget(long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.deleteGadget(gadgetId);
	}

	@Override
	public void deleteGadgets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_gadgetLocalService.deleteGadgets(companyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void destroyGadget(String uuid, long companyId) {
		_gadgetLocalService.destroyGadget(uuid, companyId);
	}

	@Override
	public void destroyGadgets() {
		_gadgetLocalService.destroyGadgets();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _gadgetLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _gadgetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _gadgetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _gadgetLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _gadgetLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _gadgetLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.opensocial.model.Gadget fetchGadget(long gadgetId) {
		return _gadgetLocalService.fetchGadget(gadgetId);
	}

	@Override
	public com.liferay.opensocial.model.Gadget fetchGadget(
		long companyId, String url) {

		return _gadgetLocalService.fetchGadget(companyId, url);
	}

	/**
	 * Returns the gadget with the matching UUID and company.
	 *
	 * @param uuid the gadget's UUID
	 * @param companyId the primary key of the company
	 * @return the matching gadget, or <code>null</code> if a matching gadget could not be found
	 */
	@Override
	public com.liferay.opensocial.model.Gadget fetchGadgetByUuidAndCompanyId(
		String uuid, long companyId) {

		return _gadgetLocalService.fetchGadgetByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _gadgetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _gadgetLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the gadget with the primary key.
	 *
	 * @param gadgetId the primary key of the gadget
	 * @return the gadget
	 * @throws PortalException if a gadget with the primary key could not be found
	 */
	@Override
	public com.liferay.opensocial.model.Gadget getGadget(long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.getGadget(gadgetId);
	}

	@Override
	public com.liferay.opensocial.model.Gadget getGadget(
			long companyId, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.getGadget(companyId, url);
	}

	@Override
	public com.liferay.opensocial.model.Gadget getGadget(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.getGadget(uuid, companyId);
	}

	/**
	 * Returns the gadget with the matching UUID and company.
	 *
	 * @param uuid the gadget's UUID
	 * @param companyId the primary key of the company
	 * @return the matching gadget
	 * @throws PortalException if a matching gadget could not be found
	 */
	@Override
	public com.liferay.opensocial.model.Gadget getGadgetByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.getGadgetByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the gadgets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of gadgets
	 * @param end the upper bound of the range of gadgets (not inclusive)
	 * @return the range of gadgets
	 */
	@Override
	public java.util.List<com.liferay.opensocial.model.Gadget> getGadgets(
		int start, int end) {

		return _gadgetLocalService.getGadgets(start, end);
	}

	@Override
	public java.util.List<com.liferay.opensocial.model.Gadget> getGadgets(
		long companyId, int start, int end) {

		return _gadgetLocalService.getGadgets(companyId, start, end);
	}

	/**
	 * Returns the number of gadgets.
	 *
	 * @return the number of gadgets
	 */
	@Override
	public int getGadgetsCount() {
		return _gadgetLocalService.getGadgetsCount();
	}

	@Override
	public int getGadgetsCount(long companyId) {
		return _gadgetLocalService.getGadgetsCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _gadgetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _gadgetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void initGadget(
			String uuid, long companyId, long gadgetId, String name,
			String portletCategoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		_gadgetLocalService.initGadget(
			uuid, companyId, gadgetId, name, portletCategoryNames);
	}

	@Override
	public void initGadgets()
		throws com.liferay.portal.kernel.exception.PortalException {

		_gadgetLocalService.initGadgets();
	}

	/**
	 * Updates the gadget in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param gadget the gadget
	 * @return the gadget that was updated
	 */
	@Override
	public com.liferay.opensocial.model.Gadget updateGadget(
		com.liferay.opensocial.model.Gadget gadget) {

		return _gadgetLocalService.updateGadget(gadget);
	}

	@Override
	public com.liferay.opensocial.model.Gadget updateGadget(
			long gadgetId, String portletCategoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _gadgetLocalService.updateGadget(gadgetId, portletCategoryNames);
	}

	@Override
	public GadgetLocalService getWrappedService() {
		return _gadgetLocalService;
	}

	@Override
	public void setWrappedService(GadgetLocalService gadgetLocalService) {
		_gadgetLocalService = gadgetLocalService;
	}

	private GadgetLocalService _gadgetLocalService;

}