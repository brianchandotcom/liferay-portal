/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link XMLSitemapRegenerationEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see XMLSitemapRegenerationEntryLocalService
 * @generated
 */
public class XMLSitemapRegenerationEntryLocalServiceWrapper
	implements ServiceWrapper<XMLSitemapRegenerationEntryLocalService>,
			   XMLSitemapRegenerationEntryLocalService {

	public XMLSitemapRegenerationEntryLocalServiceWrapper() {
		this(null);
	}

	public XMLSitemapRegenerationEntryLocalServiceWrapper(
		XMLSitemapRegenerationEntryLocalService
			xmlSitemapRegenerationEntryLocalService) {

		_xmlSitemapRegenerationEntryLocalService =
			xmlSitemapRegenerationEntryLocalService;
	}

	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		addXMLSitemapRegenerationEntry(
			String assetTypeKey, long companyId, long groupId) {

		return _xmlSitemapRegenerationEntryLocalService.
			addXMLSitemapRegenerationEntry(assetTypeKey, companyId, groupId);
	}

	/**
	 * Adds the xml sitemap regeneration entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect XMLSitemapRegenerationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param xmlSitemapRegenerationEntry the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was added
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		addXMLSitemapRegenerationEntry(
			com.liferay.site.model.XMLSitemapRegenerationEntry
				xmlSitemapRegenerationEntry) {

		return _xmlSitemapRegenerationEntryLocalService.
			addXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _xmlSitemapRegenerationEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new xml sitemap regeneration entry with the primary key. Does not add the xml sitemap regeneration entry to the database.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key for the new xml sitemap regeneration entry
	 * @return the new xml sitemap regeneration entry
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		createXMLSitemapRegenerationEntry(long xmlSitemapRegenerationEntryId) {

		return _xmlSitemapRegenerationEntryLocalService.
			createXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _xmlSitemapRegenerationEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteXMLSitemapRegenerationEntries(long companyId) {
		_xmlSitemapRegenerationEntryLocalService.
			deleteXMLSitemapRegenerationEntries(companyId);
	}

	/**
	 * Deletes the xml sitemap regeneration entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect XMLSitemapRegenerationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was removed
	 * @throws PortalException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
			deleteXMLSitemapRegenerationEntry(
				long xmlSitemapRegenerationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _xmlSitemapRegenerationEntryLocalService.
			deleteXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntryId);
	}

	/**
	 * Deletes the xml sitemap regeneration entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect XMLSitemapRegenerationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param xmlSitemapRegenerationEntry the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was removed
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		deleteXMLSitemapRegenerationEntry(
			com.liferay.site.model.XMLSitemapRegenerationEntry
				xmlSitemapRegenerationEntry) {

		return _xmlSitemapRegenerationEntryLocalService.
			deleteXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _xmlSitemapRegenerationEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _xmlSitemapRegenerationEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _xmlSitemapRegenerationEntryLocalService.dynamicQuery();
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

		return _xmlSitemapRegenerationEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
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

		return _xmlSitemapRegenerationEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
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

		return _xmlSitemapRegenerationEntryLocalService.dynamicQuery(
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

		return _xmlSitemapRegenerationEntryLocalService.dynamicQueryCount(
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

		return _xmlSitemapRegenerationEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		fetchXMLSitemapRegenerationEntry(long xmlSitemapRegenerationEntryId) {

		return _xmlSitemapRegenerationEntryLocalService.
			fetchXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _xmlSitemapRegenerationEntryLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _xmlSitemapRegenerationEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _xmlSitemapRegenerationEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _xmlSitemapRegenerationEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the xml sitemap regeneration entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @return the range of xml sitemap regeneration entries
	 */
	@Override
	public java.util.List<com.liferay.site.model.XMLSitemapRegenerationEntry>
		getXMLSitemapRegenerationEntries(int start, int end) {

		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.site.model.XMLSitemapRegenerationEntry>
		getXMLSitemapRegenerationEntries(long companyId) {

		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntries(companyId);
	}

	/**
	 * Returns the number of xml sitemap regeneration entries.
	 *
	 * @return the number of xml sitemap regeneration entries
	 */
	@Override
	public int getXMLSitemapRegenerationEntriesCount() {
		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntriesCount();
	}

	@Override
	public int getXMLSitemapRegenerationEntriesCount(long companyId) {
		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntriesCount(companyId);
	}

	/**
	 * Returns the xml sitemap regeneration entry with the primary key.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry
	 * @throws PortalException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
			getXMLSitemapRegenerationEntry(long xmlSitemapRegenerationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntryId);
	}

	/**
	 * Updates the xml sitemap regeneration entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect XMLSitemapRegenerationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param xmlSitemapRegenerationEntry the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was updated
	 */
	@Override
	public com.liferay.site.model.XMLSitemapRegenerationEntry
		updateXMLSitemapRegenerationEntry(
			com.liferay.site.model.XMLSitemapRegenerationEntry
				xmlSitemapRegenerationEntry) {

		return _xmlSitemapRegenerationEntryLocalService.
			updateXMLSitemapRegenerationEntry(xmlSitemapRegenerationEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _xmlSitemapRegenerationEntryLocalService.getBasePersistence();
	}

	@Override
	public XMLSitemapRegenerationEntryLocalService getWrappedService() {
		return _xmlSitemapRegenerationEntryLocalService;
	}

	@Override
	public void setWrappedService(
		XMLSitemapRegenerationEntryLocalService
			xmlSitemapRegenerationEntryLocalService) {

		_xmlSitemapRegenerationEntryLocalService =
			xmlSitemapRegenerationEntryLocalService;
	}

	private XMLSitemapRegenerationEntryLocalService
		_xmlSitemapRegenerationEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:417648972