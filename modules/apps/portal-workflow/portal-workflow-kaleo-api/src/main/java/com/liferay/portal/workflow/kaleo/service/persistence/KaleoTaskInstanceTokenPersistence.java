/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskInstanceTokenException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo task instance token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenUtil
 * @generated
 */
@ProviderType
public interface KaleoTaskInstanceTokenPersistence
	extends BasePersistence<KaleoTaskInstanceToken>,
			CTPersistence<KaleoTaskInstanceToken> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoTaskInstanceTokenUtil} to access the kaleo task instance token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByKaleoInstanceId_First(
			long kaleoInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	public void removeByKaleoInstanceId(long kaleoInstanceId);

	/**
	 * Returns the number of kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByKaleoInstanceId(long kaleoInstanceId);

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByC_U_First(
			long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByC_U_First(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	public void removeByC_U(long companyId, long userId);

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByC_U(long companyId, long userId);

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or throws a <code>NoSuchTaskInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByKII_KTI(
			long kaleoInstanceId, long kaleoTaskId)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByKII_KTI(
		long kaleoInstanceId, long kaleoTaskId, boolean useFinderCache);

	/**
	 * Removes the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the kaleo task instance token that was removed
	 */
	public KaleoTaskInstanceToken removeByKII_KTI(
			long kaleoInstanceId, long kaleoTaskId)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the number of kaleo task instance tokens where kaleoInstanceId = &#63; and kaleoTaskId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByKII_KTI(long kaleoInstanceId, long kaleoTaskId);

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByCN_CPK_First(
			String className, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByCN_CPK_First(
		String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	public void removeByCN_CPK(String className, long classPK);

	/**
	 * Returns the number of kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByCN_CPK(String className, long classPK);

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public java.util.List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken findByC_U_C_First(
			long companyId, long userId, boolean completed,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public KaleoTaskInstanceToken fetchByC_U_C_First(
		long companyId, long userId, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator);

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 */
	public void removeByC_U_C(long companyId, long userId, boolean completed);

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @return the number of matching kaleo task instance tokens
	 */
	public int countByC_U_C(long companyId, long userId, boolean completed);

	/**
	 * Creates a new kaleo task instance token with the primary key. Does not add the kaleo task instance token to the database.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key for the new kaleo task instance token
	 * @return the new kaleo task instance token
	 */
	public KaleoTaskInstanceToken create(long kaleoTaskInstanceTokenId);

	/**
	 * Removes the kaleo task instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token that was removed
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	public KaleoTaskInstanceToken remove(long kaleoTaskInstanceTokenId)
		throws NoSuchTaskInstanceTokenException;

	public KaleoTaskInstanceToken updateImpl(
		KaleoTaskInstanceToken kaleoTaskInstanceToken);

	/**
	 * Returns the kaleo task instance token with the primary key or throws a <code>NoSuchTaskInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	public KaleoTaskInstanceToken findByPrimaryKey(
			long kaleoTaskInstanceTokenId)
		throws NoSuchTaskInstanceTokenException;

	/**
	 * Returns the kaleo task instance token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token, or <code>null</code> if a kaleo task instance token with the primary key could not be found
	 */
	public KaleoTaskInstanceToken fetchByPrimaryKey(
		long kaleoTaskInstanceTokenId);

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	public default KaleoTaskInstanceToken fetchByKII_KTI(
		long kaleoInstanceId, long kaleoTaskId) {

		return fetchByKII_KTI(kaleoInstanceId, kaleoTaskId, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken>
		findByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoTaskInstanceToken> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return findByKaleoInstanceId(
			kaleoInstanceId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return findByKaleoInstanceId(kaleoInstanceId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator) {

		return findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId) {

		return findByC_U(
			companyId, userId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK) {

		return findByCN_CPK(
			className, classPK,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end) {

		return findByCN_CPK(className, classPK, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator) {

		return findByCN_CPK(
			className, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @return the matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed) {

		return findByC_U_C(
			companyId, userId, completed,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end) {

		return findByC_U_C(
			companyId, userId, completed, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	public default java.util.List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskInstanceToken>
			orderByComparator) {

		return findByC_U_C(
			companyId, userId, completed, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-255624863