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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDefinitionDiagramEntry. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntryLocalService
 * @generated
 */
public class CPDefinitionDiagramEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return getService().addCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry);
	}

	public static CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long userId, long cpDefinitionDiagramSettingsId,
			String cpInstanceUuid, long cProductId, int number, int quantity,
			double positionX, double positionY, double radius,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionDiagramEntry(
			userId, cpDefinitionDiagramSettingsId, cpInstanceUuid, cProductId,
			number, quantity, positionX, positionY, radius, serviceContext);
	}

	/**
	 * Creates a new cp definition diagram entry with the primary key. Does not add the cp definition diagram entry to the database.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key for the new cp definition diagram entry
	 * @return the new cp definition diagram entry
	 */
	public static CPDefinitionDiagramEntry createCPDefinitionDiagramEntry(
		long CPDefinitionDiagramEntryId) {

		return getService().createCPDefinitionDiagramEntry(
			CPDefinitionDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static CPDefinitionDiagramEntry deleteCPDefinitionDiagramEntry(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return getService().deleteCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry);
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
	public static CPDefinitionDiagramEntry deleteCPDefinitionDiagramEntry(
			long CPDefinitionDiagramEntryId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramEntry(
			CPDefinitionDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CPDefinitionDiagramEntry fetchCPDefinitionDiagramEntry(
		long CPDefinitionDiagramEntryId) {

		return getService().fetchCPDefinitionDiagramEntry(
			CPDefinitionDiagramEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	public static List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
		int start, int end) {

		return getService().getCPDefinitionDiagramEntries(start, end);
	}

	public static List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
		long cpDefinitionDiagramSettingsId, int start, int end) {

		return getService().getCPDefinitionDiagramEntries(
			cpDefinitionDiagramSettingsId, start, end);
	}

	/**
	 * Returns the number of cp definition diagram entries.
	 *
	 * @return the number of cp definition diagram entries
	 */
	public static int getCPDefinitionDiagramEntriesCount() {
		return getService().getCPDefinitionDiagramEntriesCount();
	}

	public static int getCPDefinitionDiagramEntriesCount(
		long cpDefinitionDiagramSettingsId) {

		return getService().getCPDefinitionDiagramEntriesCount(
			cpDefinitionDiagramSettingsId);
	}

	/**
	 * Returns the cp definition diagram entry with the primary key.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws PortalException if a cp definition diagram entry with the primary key could not be found
	 */
	public static CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long CPDefinitionDiagramEntryId)
		throws PortalException {

		return getService().getCPDefinitionDiagramEntry(
			CPDefinitionDiagramEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return getService().updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry);
	}

	public static CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId, String cpInstanceUuid,
			long cProductId, int number, int quantity, double positionX,
			double positionY, double radius,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, number,
			quantity, positionX, positionY, radius, serviceContext);
	}

	public static CPDefinitionDiagramEntryLocalService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramEntryLocalService _service;

}