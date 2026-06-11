/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.style.book.model.StyleBookTokenSet;

/**
 * Provides a wrapper for {@link StyleBookTokenSetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetLocalService
 * @generated
 */
public class StyleBookTokenSetLocalServiceWrapper
	implements ServiceWrapper<StyleBookTokenSetLocalService>,
			   StyleBookTokenSetLocalService {

	public StyleBookTokenSetLocalServiceWrapper() {
		this(null);
	}

	public StyleBookTokenSetLocalServiceWrapper(
		StyleBookTokenSetLocalService styleBookTokenSetLocalService) {

		_styleBookTokenSetLocalService = styleBookTokenSetLocalService;
	}

	@Override
	public StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, long styleBookEntryId,
			String frontendTokenCategoryName, String frontendTokenDefinitionId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.addStyleBookTokenSet(
			externalReferenceCode, styleBookEntryId, frontendTokenCategoryName,
			frontendTokenDefinitionId, name, description, serviceContext);
	}

	/**
	 * Adds the style book token set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was added
	 */
	@Override
	public StyleBookTokenSet addStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return _styleBookTokenSetLocalService.addStyleBookTokenSet(
			styleBookTokenSet);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new style book token set with the primary key. Does not add the style book token set to the database.
	 *
	 * @param styleBookTokenSetId the primary key for the new style book token set
	 * @return the new style book token set
	 */
	@Override
	public StyleBookTokenSet createStyleBookTokenSet(long styleBookTokenSetId) {
		return _styleBookTokenSetLocalService.createStyleBookTokenSet(
			styleBookTokenSetId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the style book token set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set that was removed
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	@Override
	public StyleBookTokenSet deleteStyleBookTokenSet(long styleBookTokenSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.deleteStyleBookTokenSet(
			styleBookTokenSetId);
	}

	/**
	 * Deletes the style book token set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was removed
	 */
	@Override
	public StyleBookTokenSet deleteStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return _styleBookTokenSetLocalService.deleteStyleBookTokenSet(
			styleBookTokenSet);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _styleBookTokenSetLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _styleBookTokenSetLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _styleBookTokenSetLocalService.dynamicQuery();
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

		return _styleBookTokenSetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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

		return _styleBookTokenSetLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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

		return _styleBookTokenSetLocalService.dynamicQuery(
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

		return _styleBookTokenSetLocalService.dynamicQueryCount(dynamicQuery);
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

		return _styleBookTokenSetLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public StyleBookTokenSet fetchStyleBookTokenSet(long styleBookTokenSetId) {
		return _styleBookTokenSetLocalService.fetchStyleBookTokenSet(
			styleBookTokenSetId);
	}

	@Override
	public StyleBookTokenSet fetchStyleBookTokenSetByExternalReferenceCode(
		String externalReferenceCode, long groupId) {

		return _styleBookTokenSetLocalService.
			fetchStyleBookTokenSetByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchStyleBookTokenSetByUuidAndGroupId(
		String uuid, long groupId) {

		return _styleBookTokenSetLocalService.
			fetchStyleBookTokenSetByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _styleBookTokenSetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _styleBookTokenSetLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _styleBookTokenSetLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _styleBookTokenSetLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the style book token set with the primary key.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	@Override
	public StyleBookTokenSet getStyleBookTokenSet(long styleBookTokenSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.getStyleBookTokenSet(
			styleBookTokenSetId);
	}

	@Override
	public StyleBookTokenSet getStyleBookTokenSetByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.
			getStyleBookTokenSetByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set
	 * @throws PortalException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet getStyleBookTokenSetByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookTokenSetLocalService.
			getStyleBookTokenSetByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the style book token sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of style book token sets
	 */
	@Override
	public java.util.List<StyleBookTokenSet> getStyleBookTokenSets(
		int start, int end) {

		return _styleBookTokenSetLocalService.getStyleBookTokenSets(start, end);
	}

	/**
	 * Returns all the style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @return the matching style book token sets, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<StyleBookTokenSet>
		getStyleBookTokenSetsByUuidAndCompanyId(String uuid, long companyId) {

		return _styleBookTokenSetLocalService.
			getStyleBookTokenSetsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching style book token sets, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<StyleBookTokenSet>
		getStyleBookTokenSetsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
				orderByComparator) {

		return _styleBookTokenSetLocalService.
			getStyleBookTokenSetsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of style book token sets.
	 *
	 * @return the number of style book token sets
	 */
	@Override
	public int getStyleBookTokenSetsCount() {
		return _styleBookTokenSetLocalService.getStyleBookTokenSetsCount();
	}

	/**
	 * Updates the style book token set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was updated
	 */
	@Override
	public StyleBookTokenSet updateStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet) {

		return _styleBookTokenSetLocalService.updateStyleBookTokenSet(
			styleBookTokenSet);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _styleBookTokenSetLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<StyleBookTokenSet> getCTPersistence() {
		return _styleBookTokenSetLocalService.getCTPersistence();
	}

	@Override
	public Class<StyleBookTokenSet> getModelClass() {
		return _styleBookTokenSetLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<StyleBookTokenSet>, R, E>
				updateUnsafeFunction)
		throws E {

		return _styleBookTokenSetLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public StyleBookTokenSetLocalService getWrappedService() {
		return _styleBookTokenSetLocalService;
	}

	@Override
	public void setWrappedService(
		StyleBookTokenSetLocalService styleBookTokenSetLocalService) {

		_styleBookTokenSetLocalService = styleBookTokenSetLocalService;
	}

	private StyleBookTokenSetLocalService _styleBookTokenSetLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-876934731