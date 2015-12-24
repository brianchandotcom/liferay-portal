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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLContent;

import java.util.List;

/**
 * The persistence utility for the document library content service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLContentPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLContentPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLContentPersistenceImpl
 * @generated
 */
@ProviderType
public class DLContentUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DLContent dlContent) {
		getPersistence().clearCache(dlContent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLContent update(DLContent dlContent) {
		return getPersistence().update(dlContent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLContent update(DLContent dlContent,
		ServiceContext serviceContext) {
		return getPersistence().update(dlContent, serviceContext);
	}

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @return the matching document library contents
	*/
	public static List<DLContent> findByC_R(long companyId, long repositoryId) {
		return getPersistence().findByC_R(companyId, repositoryId);
	}

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	*/
	public static List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end) {
		return getPersistence().findByC_R(companyId, repositoryId, start, end);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end, OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .findByC_R(companyId, repositoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R(long companyId, long repositoryId,
		int start, int end, OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_R(companyId, repositoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_First(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_First(companyId, repositoryId, orderByComparator);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_First(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_First(companyId, repositoryId, orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_Last(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_Last(companyId, repositoryId, orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_Last(long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_Last(companyId, repositoryId, orderByComparator);
	}

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws NoSuchContentException if a document library content with the primary key could not be found
	*/
	public static DLContent[] findByC_R_PrevAndNext(long contentId,
		long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_PrevAndNext(contentId, companyId, repositoryId,
			orderByComparator);
	}

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	*/
	public static void removeByC_R(long companyId, long repositoryId) {
		getPersistence().removeByC_R(companyId, repositoryId);
	}

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @return the number of matching document library contents
	*/
	public static int countByC_R(long companyId, long repositoryId) {
		return getPersistence().countByC_R(companyId, repositoryId);
	}

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the matching document library contents
	*/
	public static List<DLContent> findByC_R_P(long companyId,
		long repositoryId, java.lang.String path) {
		return getPersistence().findByC_R_P(companyId, repositoryId, path);
	}

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	*/
	public static List<DLContent> findByC_R_P(long companyId,
		long repositoryId, java.lang.String path, int start, int end) {
		return getPersistence()
				   .findByC_R_P(companyId, repositoryId, path, start, end);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R_P(long companyId,
		long repositoryId, java.lang.String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .findByC_R_P(companyId, repositoryId, path, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R_P(long companyId,
		long repositoryId, java.lang.String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_R_P(companyId, repositoryId, path, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_P_First(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_P_First(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_P_First(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_P_First(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_P_Last(long companyId, long repositoryId,
		java.lang.String path, OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_P_Last(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_P_Last(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_P_Last(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws NoSuchContentException if a document library content with the primary key could not be found
	*/
	public static DLContent[] findByC_R_P_PrevAndNext(long contentId,
		long companyId, long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_P_PrevAndNext(contentId, companyId, repositoryId,
			path, orderByComparator);
	}

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	*/
	public static void removeByC_R_P(long companyId, long repositoryId,
		java.lang.String path) {
		getPersistence().removeByC_R_P(companyId, repositoryId, path);
	}

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the number of matching document library contents
	*/
	public static int countByC_R_P(long companyId, long repositoryId,
		java.lang.String path) {
		return getPersistence().countByC_R_P(companyId, repositoryId, path);
	}

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the matching document library contents
	*/
	public static List<DLContent> findByC_R_LikeP(long companyId,
		long repositoryId, java.lang.String path) {
		return getPersistence().findByC_R_LikeP(companyId, repositoryId, path);
	}

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	*/
	public static List<DLContent> findByC_R_LikeP(long companyId,
		long repositoryId, java.lang.String path, int start, int end) {
		return getPersistence()
				   .findByC_R_LikeP(companyId, repositoryId, path, start, end);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R_LikeP(long companyId,
		long repositoryId, java.lang.String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .findByC_R_LikeP(companyId, repositoryId, path, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library contents
	*/
	public static List<DLContent> findByC_R_LikeP(long companyId,
		long repositoryId, java.lang.String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_R_LikeP(companyId, repositoryId, path, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_LikeP_First(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_LikeP_First(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_LikeP_First(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_LikeP_First(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_LikeP_Last(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_LikeP_Last(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_LikeP_Last(long companyId,
		long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence()
				   .fetchByC_R_LikeP_Last(companyId, repositoryId, path,
			orderByComparator);
	}

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws NoSuchContentException if a document library content with the primary key could not be found
	*/
	public static DLContent[] findByC_R_LikeP_PrevAndNext(long contentId,
		long companyId, long repositoryId, java.lang.String path,
		OrderByComparator<DLContent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_LikeP_PrevAndNext(contentId, companyId,
			repositoryId, path, orderByComparator);
	}

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	*/
	public static void removeByC_R_LikeP(long companyId, long repositoryId,
		java.lang.String path) {
		getPersistence().removeByC_R_LikeP(companyId, repositoryId, path);
	}

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the number of matching document library contents
	*/
	public static int countByC_R_LikeP(long companyId, long repositoryId,
		java.lang.String path) {
		return getPersistence().countByC_R_LikeP(companyId, repositoryId, path);
	}

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or throws a {@link NoSuchContentException} if it could not be found.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the matching document library content
	* @throws NoSuchContentException if a matching document library content could not be found
	*/
	public static DLContent findByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .findByC_R_P_V(companyId, repositoryId, path, version);
	}

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version) {
		return getPersistence()
				   .fetchByC_R_P_V(companyId, repositoryId, path, version);
	}

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	*/
	public static DLContent fetchByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_R_P_V(companyId, repositoryId, path, version,
			retrieveFromCache);
	}

	/**
	* Removes the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the document library content that was removed
	*/
	public static DLContent removeByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence()
				   .removeByC_R_P_V(companyId, repositoryId, path, version);
	}

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the number of matching document library contents
	*/
	public static int countByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version) {
		return getPersistence()
				   .countByC_R_P_V(companyId, repositoryId, path, version);
	}

	/**
	* Caches the document library content in the entity cache if it is enabled.
	*
	* @param dlContent the document library content
	*/
	public static void cacheResult(DLContent dlContent) {
		getPersistence().cacheResult(dlContent);
	}

	/**
	* Caches the document library contents in the entity cache if it is enabled.
	*
	* @param dlContents the document library contents
	*/
	public static void cacheResult(List<DLContent> dlContents) {
		getPersistence().cacheResult(dlContents);
	}

	/**
	* Creates a new document library content with the primary key. Does not add the document library content to the database.
	*
	* @param contentId the primary key for the new document library content
	* @return the new document library content
	*/
	public static DLContent create(long contentId) {
		return getPersistence().create(contentId);
	}

	/**
	* Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content that was removed
	* @throws NoSuchContentException if a document library content with the primary key could not be found
	*/
	public static DLContent remove(long contentId)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence().remove(contentId);
	}

	public static DLContent updateImpl(DLContent dlContent) {
		return getPersistence().updateImpl(dlContent);
	}

	/**
	* Returns the document library content with the primary key or throws a {@link NoSuchContentException} if it could not be found.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content
	* @throws NoSuchContentException if a document library content with the primary key could not be found
	*/
	public static DLContent findByPrimaryKey(long contentId)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException {
		return getPersistence().findByPrimaryKey(contentId);
	}

	/**
	* Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	*/
	public static DLContent fetchByPrimaryKey(long contentId) {
		return getPersistence().fetchByPrimaryKey(contentId);
	}

	public static java.util.Map<java.io.Serializable, DLContent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the document library contents.
	*
	* @return the document library contents
	*/
	public static List<DLContent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of document library contents
	*/
	public static List<DLContent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library contents
	*/
	public static List<DLContent> findAll(int start, int end,
		OrderByComparator<DLContent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library contents
	*/
	public static List<DLContent> findAll(int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the document library contents from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library contents.
	*
	* @return the number of document library contents
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLContentPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLContentPersistence)PortalBeanLocatorUtil.locate(DLContentPersistence.class.getName());

			ReferenceRegistry.registerReference(DLContentUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLContentPersistence _persistence;
}