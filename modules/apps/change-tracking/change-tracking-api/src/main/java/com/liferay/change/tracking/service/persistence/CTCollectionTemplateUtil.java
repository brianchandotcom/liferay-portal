/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ct collection template service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTCollectionTemplatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplatePersistence
 * @generated
 */
public class CTCollectionTemplateUtil {

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
	public static void clearCache(CTCollectionTemplate ctCollectionTemplate) {
		getPersistence().clearCache(ctCollectionTemplate);
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
	public static Map<Serializable, CTCollectionTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTCollectionTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTCollectionTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTCollectionTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTCollectionTemplate update(
		CTCollectionTemplate ctCollectionTemplate) {

		return getPersistence().update(ctCollectionTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTCollectionTemplate update(
		CTCollectionTemplate ctCollectionTemplate,
		ServiceContext serviceContext) {

		return getPersistence().update(ctCollectionTemplate, serviceContext);
	}

	/**
	 * Returns all the ct collection templates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct collection templates
	 */
	public static List<CTCollectionTemplate> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of matching ct collection templates
	 */
	public static List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct collection templates
	 */
	public static List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct collection templates
	 */
	public static List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection template
	 * @throws NoSuchCollectionTemplateException if a matching ct collection template could not be found
	 */
	public static CTCollectionTemplate findByCompanyId_First(
			long companyId,
			OrderByComparator<CTCollectionTemplate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection template, or <code>null</code> if a matching ct collection template could not be found
	 */
	public static CTCollectionTemplate fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection template
	 * @throws NoSuchCollectionTemplateException if a matching ct collection template could not be found
	 */
	public static CTCollectionTemplate findByCompanyId_Last(
			long companyId,
			OrderByComparator<CTCollectionTemplate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection template, or <code>null</code> if a matching ct collection template could not be found
	 */
	public static CTCollectionTemplate fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ct collection templates before and after the current ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param ctCollectionTemplateId the primary key of the current ct collection template
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public static CTCollectionTemplate[] findByCompanyId_PrevAndNext(
			long ctCollectionTemplateId, long companyId,
			OrderByComparator<CTCollectionTemplate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().findByCompanyId_PrevAndNext(
			ctCollectionTemplateId, companyId, orderByComparator);
	}

	/**
	 * Returns all the ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct collection templates that the user has permission to view
	 */
	public static List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of matching ct collection templates that the user has permission to view
	 */
	public static List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct collection templates that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct collection templates that the user has permission to view
	 */
	public static List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the ct collection templates before and after the current ct collection template in the ordered set of ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param ctCollectionTemplateId the primary key of the current ct collection template
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public static CTCollectionTemplate[] filterFindByCompanyId_PrevAndNext(
			long ctCollectionTemplateId, long companyId,
			OrderByComparator<CTCollectionTemplate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			ctCollectionTemplateId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ct collection templates where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct collection templates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct collection templates
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct collection templates that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Caches the ct collection template in the entity cache if it is enabled.
	 *
	 * @param ctCollectionTemplate the ct collection template
	 */
	public static void cacheResult(CTCollectionTemplate ctCollectionTemplate) {
		getPersistence().cacheResult(ctCollectionTemplate);
	}

	/**
	 * Caches the ct collection templates in the entity cache if it is enabled.
	 *
	 * @param ctCollectionTemplates the ct collection templates
	 */
	public static void cacheResult(
		List<CTCollectionTemplate> ctCollectionTemplates) {

		getPersistence().cacheResult(ctCollectionTemplates);
	}

	/**
	 * Creates a new ct collection template with the primary key. Does not add the ct collection template to the database.
	 *
	 * @param ctCollectionTemplateId the primary key for the new ct collection template
	 * @return the new ct collection template
	 */
	public static CTCollectionTemplate create(long ctCollectionTemplateId) {
		return getPersistence().create(ctCollectionTemplateId);
	}

	/**
	 * Removes the ct collection template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template that was removed
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public static CTCollectionTemplate remove(long ctCollectionTemplateId)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().remove(ctCollectionTemplateId);
	}

	public static CTCollectionTemplate updateImpl(
		CTCollectionTemplate ctCollectionTemplate) {

		return getPersistence().updateImpl(ctCollectionTemplate);
	}

	/**
	 * Returns the ct collection template with the primary key or throws a <code>NoSuchCollectionTemplateException</code> if it could not be found.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public static CTCollectionTemplate findByPrimaryKey(
			long ctCollectionTemplateId)
		throws com.liferay.change.tracking.exception.
			NoSuchCollectionTemplateException {

		return getPersistence().findByPrimaryKey(ctCollectionTemplateId);
	}

	/**
	 * Returns the ct collection template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template, or <code>null</code> if a ct collection template with the primary key could not be found
	 */
	public static CTCollectionTemplate fetchByPrimaryKey(
		long ctCollectionTemplateId) {

		return getPersistence().fetchByPrimaryKey(ctCollectionTemplateId);
	}

	/**
	 * Returns all the ct collection templates.
	 *
	 * @return the ct collection templates
	 */
	public static List<CTCollectionTemplate> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of ct collection templates
	 */
	public static List<CTCollectionTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collection templates
	 */
	public static List<CTCollectionTemplate> findAll(
		int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct collection templates
	 */
	public static List<CTCollectionTemplate> findAll(
		int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct collection templates from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct collection templates.
	 *
	 * @return the number of ct collection templates
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTCollectionTemplatePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CTCollectionTemplatePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CTCollectionTemplatePersistence _persistence;

}