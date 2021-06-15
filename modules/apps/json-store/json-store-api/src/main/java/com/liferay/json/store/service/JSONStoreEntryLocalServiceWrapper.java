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

package com.liferay.json.store.service;

import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link JSONStoreEntryLocalService}.
 *
 * @author Preston Crary
 * @see JSONStoreEntryLocalService
 * @generated
 */
public class JSONStoreEntryLocalServiceWrapper
	implements JSONStoreEntryLocalService,
			   ServiceWrapper<JSONStoreEntryLocalService> {

	public JSONStoreEntryLocalServiceWrapper(
		JSONStoreEntryLocalService jsonStoreEntryLocalService) {

		_jsonStoreEntryLocalService = jsonStoreEntryLocalService;
	}

	@Override
	public void addJSON(
		long companyId, long classNameId, long classPK, String json) {

		_jsonStoreEntryLocalService.addJSON(
			companyId, classNameId, classPK, json);
	}

	/**
	 * Adds the json store entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JSONStoreEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param jsonStoreEntry the json store entry
	 * @return the json store entry that was added
	 */
	@Override
	public JSONStoreEntry addJSONStoreEntry(JSONStoreEntry jsonStoreEntry) {
		return _jsonStoreEntryLocalService.addJSONStoreEntry(jsonStoreEntry);
	}

	/**
	 * Creates a new json store entry with the primary key. Does not add the json store entry to the database.
	 *
	 * @param jsonStoreEntryId the primary key for the new json store entry
	 * @return the new json store entry
	 */
	@Override
	public JSONStoreEntry createJSONStoreEntry(long jsonStoreEntryId) {
		return _jsonStoreEntryLocalService.createJSONStoreEntry(
			jsonStoreEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _jsonStoreEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteJSON(long classNameId, long classPK) {
		_jsonStoreEntryLocalService.deleteJSON(classNameId, classPK);
	}

	/**
	 * Deletes the json store entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JSONStoreEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param jsonStoreEntry the json store entry
	 * @return the json store entry that was removed
	 */
	@Override
	public JSONStoreEntry deleteJSONStoreEntry(JSONStoreEntry jsonStoreEntry) {
		return _jsonStoreEntryLocalService.deleteJSONStoreEntry(jsonStoreEntry);
	}

	/**
	 * Deletes the json store entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JSONStoreEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry that was removed
	 * @throws PortalException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry deleteJSONStoreEntry(long jsonStoreEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _jsonStoreEntryLocalService.deleteJSONStoreEntry(
			jsonStoreEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _jsonStoreEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _jsonStoreEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _jsonStoreEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _jsonStoreEntryLocalService.dynamicQuery();
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

		return _jsonStoreEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.json.store.model.impl.JSONStoreEntryModelImpl</code>.
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

		return _jsonStoreEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.json.store.model.impl.JSONStoreEntryModelImpl</code>.
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

		return _jsonStoreEntryLocalService.dynamicQuery(
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

		return _jsonStoreEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _jsonStoreEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public JSONStoreEntry fetchJSONStoreEntry(long jsonStoreEntryId) {
		return _jsonStoreEntryLocalService.fetchJSONStoreEntry(
			jsonStoreEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _jsonStoreEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<Long> getClassPKs(
		long companyId, long classNameId, Object[] pathParts, Object value,
		int start, int end) {

		return _jsonStoreEntryLocalService.getClassPKs(
			companyId, classNameId, pathParts, value, start, end);
	}

	@Override
	public int getClassPKsCount(
		long companyId, long classNameId, Object[] pathParts, Object value) {

		return _jsonStoreEntryLocalService.getClassPKsCount(
			companyId, classNameId, pathParts, value);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _jsonStoreEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getJSONArray(
		long classNameId, long classPK) {

		return _jsonStoreEntryLocalService.getJSONArray(classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONObject(
		long classNameId, long classPK) {

		return _jsonStoreEntryLocalService.getJSONObject(classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONSerializable getJSONSerializable(
		long classNameId, long classPK) {

		return _jsonStoreEntryLocalService.getJSONSerializable(
			classNameId, classPK);
	}

	/**
	 * Returns a range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.json.store.model.impl.JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of json store entries
	 */
	@Override
	public java.util.List<JSONStoreEntry> getJSONStoreEntries(
		int start, int end) {

		return _jsonStoreEntryLocalService.getJSONStoreEntries(start, end);
	}

	/**
	 * Returns the number of json store entries.
	 *
	 * @return the number of json store entries
	 */
	@Override
	public int getJSONStoreEntriesCount() {
		return _jsonStoreEntryLocalService.getJSONStoreEntriesCount();
	}

	/**
	 * Returns the json store entry with the primary key.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry
	 * @throws PortalException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry getJSONStoreEntry(long jsonStoreEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _jsonStoreEntryLocalService.getJSONStoreEntry(jsonStoreEntryId);
	}

	@Override
	public String getJSONString(long classNameId, long classPK) {
		return _jsonStoreEntryLocalService.getJSONString(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _jsonStoreEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _jsonStoreEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void updateJSON(
		long companyId, long classNameId, long classPK, String json) {

		_jsonStoreEntryLocalService.updateJSON(
			companyId, classNameId, classPK, json);
	}

	/**
	 * Updates the json store entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JSONStoreEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param jsonStoreEntry the json store entry
	 * @return the json store entry that was updated
	 */
	@Override
	public JSONStoreEntry updateJSONStoreEntry(JSONStoreEntry jsonStoreEntry) {
		return _jsonStoreEntryLocalService.updateJSONStoreEntry(jsonStoreEntry);
	}

	@Override
	public CTPersistence<JSONStoreEntry> getCTPersistence() {
		return _jsonStoreEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<JSONStoreEntry> getModelClass() {
		return _jsonStoreEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<JSONStoreEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _jsonStoreEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public JSONStoreEntryLocalService getWrappedService() {
		return _jsonStoreEntryLocalService;
	}

	@Override
	public void setWrappedService(
		JSONStoreEntryLocalService jsonStoreEntryLocalService) {

		_jsonStoreEntryLocalService = jsonStoreEntryLocalService;
	}

	private JSONStoreEntryLocalService _jsonStoreEntryLocalService;

}