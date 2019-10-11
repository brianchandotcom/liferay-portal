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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ddm template link service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkPersistence
 * @generated
 */
public class DDMTemplateLinkUtil {

	/**
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
	public static void clearCache(DDMTemplateLink ddmTemplateLink) {
		getPersistence().clearCache(ddmTemplateLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, DDMTemplateLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMTemplateLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMTemplateLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMTemplateLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMTemplateLink update(DDMTemplateLink ddmTemplateLink) {
		return getPersistence().update(ddmTemplateLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMTemplateLink update(
		DDMTemplateLink ddmTemplateLink, ServiceContext serviceContext) {

		return getPersistence().update(ddmTemplateLink, serviceContext);
	}

	/**
	 * Returns all the ddm template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching ddm template links
	 */
	public static List<DDMTemplateLink> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	 * Returns a range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end) {

		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().findByClassNameId(
			classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByClassNameId(
			classNameId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink findByClassNameId_First(
			long classNameId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByClassNameId_First(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the first ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByClassNameId_First(
		long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().fetchByClassNameId_First(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink findByClassNameId_Last(
			long classNameId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByClassNameId_Last(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByClassNameId_Last(
		long classNameId,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().fetchByClassNameId_Last(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the ddm template links before and after the current ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current ddm template link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public static DDMTemplateLink[] findByClassNameId_PrevAndNext(
			long templateLinkId, long classNameId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByClassNameId_PrevAndNext(
			templateLinkId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the ddm template links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	 * Returns the number of ddm template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching ddm template links
	 */
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	 * Returns all the ddm template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching ddm template links
	 */
	public static List<DDMTemplateLink> findByTemplateId(long templateId) {
		return getPersistence().findByTemplateId(templateId);
	}

	/**
	 * Returns a range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end) {

		return getPersistence().findByTemplateId(templateId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().findByTemplateId(
			templateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template links
	 */
	public static List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByTemplateId(
			templateId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink findByTemplateId_First(
			long templateId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByTemplateId_First(
			templateId, orderByComparator);
	}

	/**
	 * Returns the first ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByTemplateId_First(
		long templateId, OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().fetchByTemplateId_First(
			templateId, orderByComparator);
	}

	/**
	 * Returns the last ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink findByTemplateId_Last(
			long templateId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByTemplateId_Last(
			templateId, orderByComparator);
	}

	/**
	 * Returns the last ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByTemplateId_Last(
		long templateId, OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().fetchByTemplateId_Last(
			templateId, orderByComparator);
	}

	/**
	 * Returns the ddm template links before and after the current ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current ddm template link
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public static DDMTemplateLink[] findByTemplateId_PrevAndNext(
			long templateLinkId, long templateId,
			OrderByComparator<DDMTemplateLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByTemplateId_PrevAndNext(
			templateLinkId, templateId, orderByComparator);
	}

	/**
	 * Removes all the ddm template links where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	public static void removeByTemplateId(long templateId) {
		getPersistence().removeByTemplateId(templateId);
	}

	/**
	 * Returns the number of ddm template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching ddm template links
	 */
	public static int countByTemplateId(long templateId) {
		return getPersistence().countByTemplateId(templateId);
	}

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchTemplateLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink findByC_C(long classNameId, long classPK)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public static DDMTemplateLink fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the ddm template link where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the ddm template link that was removed
	 */
	public static DDMTemplateLink removeByC_C(long classNameId, long classPK)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of ddm template links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ddm template links
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Caches the ddm template link in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLink the ddm template link
	 */
	public static void cacheResult(DDMTemplateLink ddmTemplateLink) {
		getPersistence().cacheResult(ddmTemplateLink);
	}

	/**
	 * Caches the ddm template links in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLinks the ddm template links
	 */
	public static void cacheResult(List<DDMTemplateLink> ddmTemplateLinks) {
		getPersistence().cacheResult(ddmTemplateLinks);
	}

	/**
	 * Creates a new ddm template link with the primary key. Does not add the ddm template link to the database.
	 *
	 * @param templateLinkId the primary key for the new ddm template link
	 * @return the new ddm template link
	 */
	public static DDMTemplateLink create(long templateLinkId) {
		return getPersistence().create(templateLinkId);
	}

	/**
	 * Removes the ddm template link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link that was removed
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public static DDMTemplateLink remove(long templateLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().remove(templateLinkId);
	}

	public static DDMTemplateLink updateImpl(DDMTemplateLink ddmTemplateLink) {
		return getPersistence().updateImpl(ddmTemplateLink);
	}

	/**
	 * Returns the ddm template link with the primary key or throws a <code>NoSuchTemplateLinkException</code> if it could not be found.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public static DDMTemplateLink findByPrimaryKey(long templateLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateLinkException {

		return getPersistence().findByPrimaryKey(templateLinkId);
	}

	/**
	 * Returns the ddm template link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link, or <code>null</code> if a ddm template link with the primary key could not be found
	 */
	public static DDMTemplateLink fetchByPrimaryKey(long templateLinkId) {
		return getPersistence().fetchByPrimaryKey(templateLinkId);
	}

	/**
	 * Returns all the ddm template links.
	 *
	 * @return the ddm template links
	 */
	public static List<DDMTemplateLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of ddm template links
	 */
	public static List<DDMTemplateLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm template links
	 */
	public static List<DDMTemplateLink> findAll(
		int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm template links
	 */
	public static List<DDMTemplateLink> findAll(
		int start, int end,
		OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm template links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm template links.
	 *
	 * @return the number of ddm template links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMTemplateLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMTemplateLinkPersistence, DDMTemplateLinkPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMTemplateLinkPersistence.class);

		ServiceTracker<DDMTemplateLinkPersistence, DDMTemplateLinkPersistence>
			serviceTracker =
				new ServiceTracker
					<DDMTemplateLinkPersistence, DDMTemplateLinkPersistence>(
						bundle.getBundleContext(),
						DDMTemplateLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}