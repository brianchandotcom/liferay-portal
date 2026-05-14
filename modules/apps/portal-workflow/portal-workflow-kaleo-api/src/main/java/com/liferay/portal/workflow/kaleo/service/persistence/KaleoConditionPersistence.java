/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchConditionException;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo condition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoConditionUtil
 * @generated
 */
@ProviderType
public interface KaleoConditionPersistence
	extends BasePersistence<KaleoCondition>, CTPersistence<KaleoCondition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoConditionUtil} to access the kaleo condition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the kaleo conditions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo conditions
	 */
	public java.util.List<KaleoCondition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo condition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo condition
	 * @throws NoSuchConditionException if a matching kaleo condition could not be found
	 */
	public KaleoCondition findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
				orderByComparator)
		throws NoSuchConditionException;

	/**
	 * Returns the first kaleo condition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo condition, or <code>null</code> if a matching kaleo condition could not be found
	 */
	public KaleoCondition fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
			orderByComparator);

	/**
	 * Removes all the kaleo conditions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo conditions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo conditions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns an ordered range of all the kaleo conditions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo conditions
	 */
	public java.util.List<KaleoCondition> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo condition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo condition
	 * @throws NoSuchConditionException if a matching kaleo condition could not be found
	 */
	public KaleoCondition findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
				orderByComparator)
		throws NoSuchConditionException;

	/**
	 * Returns the first kaleo condition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo condition, or <code>null</code> if a matching kaleo condition could not be found
	 */
	public KaleoCondition fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
			orderByComparator);

	/**
	 * Removes all the kaleo conditions where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo conditions where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo conditions
	 */
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the kaleo condition where kaleoNodeId = &#63; or throws a <code>NoSuchConditionException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo condition
	 * @throws NoSuchConditionException if a matching kaleo condition could not be found
	 */
	public KaleoCondition findByKaleoNodeId(long kaleoNodeId)
		throws NoSuchConditionException;

	/**
	 * Returns the kaleo condition where kaleoNodeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo condition, or <code>null</code> if a matching kaleo condition could not be found
	 */
	public KaleoCondition fetchByKaleoNodeId(
		long kaleoNodeId, boolean useFinderCache);

	/**
	 * Removes the kaleo condition where kaleoNodeId = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the kaleo condition that was removed
	 */
	public KaleoCondition removeByKaleoNodeId(long kaleoNodeId)
		throws NoSuchConditionException;

	/**
	 * Returns the number of kaleo conditions where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the number of matching kaleo conditions
	 */
	public int countByKaleoNodeId(long kaleoNodeId);

	/**
	 * Creates a new kaleo condition with the primary key. Does not add the kaleo condition to the database.
	 *
	 * @param kaleoConditionId the primary key for the new kaleo condition
	 * @return the new kaleo condition
	 */
	public KaleoCondition create(long kaleoConditionId);

	/**
	 * Removes the kaleo condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition that was removed
	 * @throws NoSuchConditionException if a kaleo condition with the primary key could not be found
	 */
	public KaleoCondition remove(long kaleoConditionId)
		throws NoSuchConditionException;

	public KaleoCondition updateImpl(KaleoCondition kaleoCondition);

	/**
	 * Returns the kaleo condition with the primary key or throws a <code>NoSuchConditionException</code> if it could not be found.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition
	 * @throws NoSuchConditionException if a kaleo condition with the primary key could not be found
	 */
	public KaleoCondition findByPrimaryKey(long kaleoConditionId)
		throws NoSuchConditionException;

	/**
	 * Returns the kaleo condition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition, or <code>null</code> if a kaleo condition with the primary key could not be found
	 */
	public KaleoCondition fetchByPrimaryKey(long kaleoConditionId);

	/**
	 * Returns the kaleo condition where kaleoNodeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo condition, or <code>null</code> if a matching kaleo condition could not be found
	 */
	public default KaleoCondition fetchByKaleoNodeId(long kaleoNodeId) {
		return fetchByKaleoNodeId(kaleoNodeId, true);
	}

	/**
	 * Returns all the kaleo conditions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo conditions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @return the range of matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo conditions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the kaleo conditions where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition>
		findByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the kaleo conditions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @return the range of matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the kaleo conditions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo conditions
	 */
	public default java.util.List<KaleoCondition>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoCondition>
				orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-481743255