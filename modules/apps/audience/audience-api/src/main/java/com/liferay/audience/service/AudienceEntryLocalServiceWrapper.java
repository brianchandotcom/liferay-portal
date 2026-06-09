/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link AudienceEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryLocalService
 * @generated
 */
public class AudienceEntryLocalServiceWrapper
	implements AudienceEntryLocalService,
			   ServiceWrapper<AudienceEntryLocalService> {

	public AudienceEntryLocalServiceWrapper() {
		this(null);
	}

	public AudienceEntryLocalServiceWrapper(
		AudienceEntryLocalService audienceEntryLocalService) {

		_audienceEntryLocalService = audienceEntryLocalService;
	}

	/**
	 * Adds the audience entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was added
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry addAudienceEntry(
		com.liferay.audience.model.AudienceEntry audienceEntry) {

		return _audienceEntryLocalService.addAudienceEntry(audienceEntry);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json,
			java.util.Map<java.util.Locale, String> nameMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.addAudienceEntry(
			externalReferenceCode, json, nameMap, serviceContext);
	}

	/**
	 * Creates a new audience entry with the primary key. Does not add the audience entry to the database.
	 *
	 * @param audienceEntryId the primary key for the new audience entry
	 * @return the new audience entry
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry createAudienceEntry(
		long audienceEntryId) {

		return _audienceEntryLocalService.createAudienceEntry(audienceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the audience entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry deleteAudienceEntry(
			com.liferay.audience.model.AudienceEntry audienceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.deleteAudienceEntry(audienceEntry);
	}

	/**
	 * Deletes the audience entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry that was removed
	 * @throws PortalException if a audience entry with the primary key could not be found
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry deleteAudienceEntry(
			long audienceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.deleteAudienceEntry(audienceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _audienceEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _audienceEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _audienceEntryLocalService.dynamicQuery();
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

		return _audienceEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
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

		return _audienceEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
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

		return _audienceEntryLocalService.dynamicQuery(
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

		return _audienceEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _audienceEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry fetchAudienceEntry(
		long audienceEntryId) {

		return _audienceEntryLocalService.fetchAudienceEntry(audienceEntryId);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry
		fetchAudienceEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return _audienceEntryLocalService.
			fetchAudienceEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	/**
	 * Returns the audience entry with the matching UUID and company.
	 *
	 * @param uuid the audience entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry
		fetchAudienceEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _audienceEntryLocalService.fetchAudienceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _audienceEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the audience entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of audience entries
	 */
	@Override
	public java.util.List<com.liferay.audience.model.AudienceEntry>
		getAudienceEntries(int start, int end) {

		return _audienceEntryLocalService.getAudienceEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.audience.model.AudienceEntry>
		getAudienceEntries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.audience.model.AudienceEntry> orderByComparator) {

		return _audienceEntryLocalService.getAudienceEntries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.audience.model.AudienceEntry>
			getAudienceEntries(
				long companyId, String name, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.audience.model.AudienceEntry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.getAudienceEntries(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the number of audience entries.
	 *
	 * @return the number of audience entries
	 */
	@Override
	public int getAudienceEntriesCount() {
		return _audienceEntryLocalService.getAudienceEntriesCount();
	}

	@Override
	public int getAudienceEntriesCount(long companyId) {
		return _audienceEntryLocalService.getAudienceEntriesCount(companyId);
	}

	@Override
	public int getAudienceEntriesCount(long companyId, String name) {
		return _audienceEntryLocalService.getAudienceEntriesCount(
			companyId, name);
	}

	/**
	 * Returns the audience entry with the primary key.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry
	 * @throws PortalException if a audience entry with the primary key could not be found
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry getAudienceEntry(
			long audienceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.getAudienceEntry(audienceEntryId);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry
			getAudienceEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.
			getAudienceEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	/**
	 * Returns the audience entry with the matching UUID and company.
	 *
	 * @param uuid the audience entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching audience entry
	 * @throws PortalException if a matching audience entry could not be found
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry
			getAudienceEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.getAudienceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _audienceEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _audienceEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _audienceEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the audience entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was updated
	 */
	@Override
	public com.liferay.audience.model.AudienceEntry updateAudienceEntry(
		com.liferay.audience.model.AudienceEntry audienceEntry) {

		return _audienceEntryLocalService.updateAudienceEntry(audienceEntry);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json,
			java.util.Map<java.util.Locale, String> nameMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryLocalService.updateAudienceEntry(
			audienceEntryId, json, nameMap, serviceContext);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _audienceEntryLocalService.getBasePersistence();
	}

	@Override
	public AudienceEntryLocalService getWrappedService() {
		return _audienceEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AudienceEntryLocalService audienceEntryLocalService) {

		_audienceEntryLocalService = audienceEntryLocalService;
	}

	private AudienceEntryLocalService _audienceEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:340398818