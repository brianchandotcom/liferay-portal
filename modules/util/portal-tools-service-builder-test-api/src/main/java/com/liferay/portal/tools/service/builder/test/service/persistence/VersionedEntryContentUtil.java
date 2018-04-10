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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the versioned entry content service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentPersistenceImpl
 * @generated
 */
@ProviderType
public class VersionedEntryContentUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(VersionedEntryContent versionedEntryContent) {
		getPersistence().clearCache(versionedEntryContent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VersionedEntryContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VersionedEntryContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VersionedEntryContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VersionedEntryContent update(
		VersionedEntryContent versionedEntryContent) {
		return getPersistence().update(versionedEntryContent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VersionedEntryContent update(
		VersionedEntryContent versionedEntryContent,
		ServiceContext serviceContext) {
		return getPersistence().update(versionedEntryContent, serviceContext);
	}

	/**
	* Returns all the versioned entry contents where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the matching versioned entry contents
	*/
	public static List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId) {
		return getPersistence().findByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns a range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @return the range of matching versioned entry contents
	*/
	public static List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry contents
	*/
	public static List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry contents
	*/
	public static List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent findByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence()
				   .findByVersionedEntryId_First(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_First(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent findByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence()
				   .findByVersionedEntryId_Last(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_Last(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the versioned entry contents before and after the current versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryContentId the primary key of the current versioned entry content
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public static VersionedEntryContent[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentId, long versionedEntryId,
		OrderByComparator<VersionedEntryContent> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence()
				   .findByVersionedEntryId_PrevAndNext(versionedEntryContentId,
			versionedEntryId, orderByComparator);
	}

	/**
	* Removes all the versioned entry contents where versionedEntryId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	*/
	public static void removeByVersionedEntryId(long versionedEntryId) {
		getPersistence().removeByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns the number of versioned entry contents where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the number of matching versioned entry contents
	*/
	public static int countByVersionedEntryId(long versionedEntryId) {
		return getPersistence().countByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId(versionedEntryId,
			languageId, retrieveFromCache);
	}

	/**
	* Removes the versioned entry content where versionedEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the versioned entry content that was removed
	*/
	public static VersionedEntryContent removeByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence()
				   .removeByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns the number of versioned entry contents where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the number of matching versioned entry contents
	*/
	public static int countByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId) {
		return getPersistence()
				   .countByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns the versioned entry content where headId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param headId the head ID
	* @return the matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent findByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence().findByHeadId(headId);
	}

	/**
	* Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	* Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public static VersionedEntryContent fetchByHeadId(long headId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByHeadId(headId, retrieveFromCache);
	}

	/**
	* Removes the versioned entry content where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the versioned entry content that was removed
	*/
	public static VersionedEntryContent removeByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence().removeByHeadId(headId);
	}

	/**
	* Returns the number of versioned entry contents where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching versioned entry contents
	*/
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	* Caches the versioned entry content in the entity cache if it is enabled.
	*
	* @param versionedEntryContent the versioned entry content
	*/
	public static void cacheResult(VersionedEntryContent versionedEntryContent) {
		getPersistence().cacheResult(versionedEntryContent);
	}

	/**
	* Caches the versioned entry contents in the entity cache if it is enabled.
	*
	* @param versionedEntryContents the versioned entry contents
	*/
	public static void cacheResult(
		List<VersionedEntryContent> versionedEntryContents) {
		getPersistence().cacheResult(versionedEntryContents);
	}

	/**
	* Creates a new versioned entry content with the primary key. Does not add the versioned entry content to the database.
	*
	* @param versionedEntryContentId the primary key for the new versioned entry content
	* @return the new versioned entry content
	*/
	public static VersionedEntryContent create(long versionedEntryContentId) {
		return getPersistence().create(versionedEntryContentId);
	}

	/**
	* Removes the versioned entry content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content that was removed
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public static VersionedEntryContent remove(long versionedEntryContentId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence().remove(versionedEntryContentId);
	}

	public static VersionedEntryContent updateImpl(
		VersionedEntryContent versionedEntryContent) {
		return getPersistence().updateImpl(versionedEntryContent);
	}

	/**
	* Returns the versioned entry content with the primary key or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public static VersionedEntryContent findByPrimaryKey(
		long versionedEntryContentId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException {
		return getPersistence().findByPrimaryKey(versionedEntryContentId);
	}

	/**
	* Returns the versioned entry content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content, or <code>null</code> if a versioned entry content with the primary key could not be found
	*/
	public static VersionedEntryContent fetchByPrimaryKey(
		long versionedEntryContentId) {
		return getPersistence().fetchByPrimaryKey(versionedEntryContentId);
	}

	public static java.util.Map<java.io.Serializable, VersionedEntryContent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the versioned entry contents.
	*
	* @return the versioned entry contents
	*/
	public static List<VersionedEntryContent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @return the range of versioned entry contents
	*/
	public static List<VersionedEntryContent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of versioned entry contents
	*/
	public static List<VersionedEntryContent> findAll(int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of versioned entry contents
	*/
	public static List<VersionedEntryContent> findAll(int start, int end,
		OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the versioned entry contents from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of versioned entry contents.
	*
	* @return the number of versioned entry contents
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VersionedEntryContentPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<VersionedEntryContentPersistence, VersionedEntryContentPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(VersionedEntryContentPersistence.class);

		ServiceTracker<VersionedEntryContentPersistence, VersionedEntryContentPersistence> serviceTracker =
			new ServiceTracker<VersionedEntryContentPersistence, VersionedEntryContentPersistence>(bundle.getBundleContext(),
				VersionedEntryContentPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}