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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionDiagramEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntryLocalService
 * @generated
 */
public class CPDefinitionDiagramEntryLocalServiceWrapper
	implements CPDefinitionDiagramEntryLocalService,
			   ServiceWrapper<CPDefinitionDiagramEntryLocalService> {

	public CPDefinitionDiagramEntryLocalServiceWrapper(
		CPDefinitionDiagramEntryLocalService
			cpDefinitionDiagramEntryLocalService) {

		_cpDefinitionDiagramEntryLocalService =
			cpDefinitionDiagramEntryLocalService;
	}

	/**
	 * Adds the cp definition diagram entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 * @return the cp definition diagram entry that was added
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
		addCPDefinitionDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				cpDefinitionDiagramEntry) {

		return _cpDefinitionDiagramEntryLocalService.
			addCPDefinitionDiagramEntry(cpDefinitionDiagramEntry);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			addCPDefinitionDiagramEntry(
				long userId, long cpDefinitionDiagramSettingsId,
				String cpInstanceUuid, long cProductId, int number,
				int quantity, double positionX, double positionY, double radius,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.
			addCPDefinitionDiagramEntry(
				userId, cpDefinitionDiagramSettingsId, cpInstanceUuid,
				cProductId, number, quantity, positionX, positionY, radius,
				serviceContext);
	}

	/**
	 * Creates a new cp definition diagram entry with the primary key. Does not add the cp definition diagram entry to the database.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key for the new cp definition diagram entry
	 * @return the new cp definition diagram entry
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
		createCPDefinitionDiagramEntry(long CPDefinitionDiagramEntryId) {

		return _cpDefinitionDiagramEntryLocalService.
			createCPDefinitionDiagramEntry(CPDefinitionDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cp definition diagram entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
		deleteCPDefinitionDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				cpDefinitionDiagramEntry) {

		return _cpDefinitionDiagramEntryLocalService.
			deleteCPDefinitionDiagramEntry(cpDefinitionDiagramEntry);
	}

	/**
	 * Deletes the cp definition diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 * @throws PortalException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			deleteCPDefinitionDiagramEntry(long CPDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.
			deleteCPDefinitionDiagramEntry(CPDefinitionDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpDefinitionDiagramEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionDiagramEntryLocalService.dynamicQuery();
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

		return _cpDefinitionDiagramEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryModelImpl</code>.
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

		return _cpDefinitionDiagramEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryModelImpl</code>.
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

		return _cpDefinitionDiagramEntryLocalService.dynamicQuery(
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

		return _cpDefinitionDiagramEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _cpDefinitionDiagramEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
		fetchCPDefinitionDiagramEntry(long CPDefinitionDiagramEntryId) {

		return _cpDefinitionDiagramEntryLocalService.
			fetchCPDefinitionDiagramEntry(CPDefinitionDiagramEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpDefinitionDiagramEntryLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of cp definition diagram entries
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry>
			getCPDefinitionDiagramEntries(int start, int end) {

		return _cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry>
			getCPDefinitionDiagramEntries(
				long cpDefinitionDiagramSettingsId, int start, int end) {

		return _cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntries(
				cpDefinitionDiagramSettingsId, start, end);
	}

	/**
	 * Returns the number of cp definition diagram entries.
	 *
	 * @return the number of cp definition diagram entries
	 */
	@Override
	public int getCPDefinitionDiagramEntriesCount() {
		return _cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntriesCount();
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(
		long cpDefinitionDiagramSettingsId) {

		return _cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntriesCount(cpDefinitionDiagramSettingsId);
	}

	/**
	 * Returns the cp definition diagram entry with the primary key.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws PortalException if a cp definition diagram entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			getCPDefinitionDiagramEntry(long CPDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntry(CPDefinitionDiagramEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionDiagramEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cp definition diagram entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 * @return the cp definition diagram entry that was updated
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
		updateCPDefinitionDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				cpDefinitionDiagramEntry) {

		return _cpDefinitionDiagramEntryLocalService.
			updateCPDefinitionDiagramEntry(cpDefinitionDiagramEntry);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			updateCPDefinitionDiagramEntry(
				long cpDefinitionDiagramEntryId, String cpInstanceUuid,
				long cProductId, int number, int quantity, double positionX,
				double positionY, double radius,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryLocalService.
			updateCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, number,
				quantity, positionX, positionY, radius, serviceContext);
	}

	@Override
	public CPDefinitionDiagramEntryLocalService getWrappedService() {
		return _cpDefinitionDiagramEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramEntryLocalService
			cpDefinitionDiagramEntryLocalService) {

		_cpDefinitionDiagramEntryLocalService =
			cpDefinitionDiagramEntryLocalService;
	}

	private CPDefinitionDiagramEntryLocalService
		_cpDefinitionDiagramEntryLocalService;

}