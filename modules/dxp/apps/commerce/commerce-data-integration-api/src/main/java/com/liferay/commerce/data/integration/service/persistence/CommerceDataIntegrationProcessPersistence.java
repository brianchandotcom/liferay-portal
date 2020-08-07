/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service.persistence;

import com.liferay.commerce.data.integration.exception.NoSuchDataIntegrationProcessException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce data integration process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessUtil
 * @generated
 */
@ProviderType
public interface CommerceDataIntegrationProcessPersistence
	extends BasePersistence<CommerceDataIntegrationProcess> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDataIntegrationProcessUtil} to access the commerce data integration process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess[] findByCompanyId_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess[] filterFindByCompanyId_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess findByC_N(long companyId, String name)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name);

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the commerce data integration process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce data integration process that was removed
	 */
	public CommerceDataIntegrationProcess removeByC_N(
			long companyId, String name)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce data integration processes
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess findByC_T_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByC_T_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess findByC_T_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public CommerceDataIntegrationProcess fetchByC_T_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess[] findByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	public java.util.List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess[] filterFindByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, String type);

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes
	 */
	public int countByC_T(long companyId, String type);

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	public int filterCountByC_T(long companyId, String type);

	/**
	 * Caches the commerce data integration process in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 */
	public void cacheResult(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess);

	/**
	 * Caches the commerce data integration processes in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcesses the commerce data integration processes
	 */
	public void cacheResult(
		java.util.List<CommerceDataIntegrationProcess>
			commerceDataIntegrationProcesses);

	/**
	 * Creates a new commerce data integration process with the primary key. Does not add the commerce data integration process to the database.
	 *
	 * @param commerceDataIntegrationProcessId the primary key for the new commerce data integration process
	 * @return the new commerce data integration process
	 */
	public CommerceDataIntegrationProcess create(
		long commerceDataIntegrationProcessId);

	/**
	 * Removes the commerce data integration process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess remove(
			long commerceDataIntegrationProcessId)
		throws NoSuchDataIntegrationProcessException;

	public CommerceDataIntegrationProcess updateImpl(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess);

	/**
	 * Returns the commerce data integration process with the primary key or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess findByPrimaryKey(
			long commerceDataIntegrationProcessId)
		throws NoSuchDataIntegrationProcessException;

	/**
	 * Returns the commerce data integration process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process, or <code>null</code> if a commerce data integration process with the primary key could not be found
	 */
	public CommerceDataIntegrationProcess fetchByPrimaryKey(
		long commerceDataIntegrationProcessId);

	/**
	 * Returns all the commerce data integration processes.
	 *
	 * @return the commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findAll();

	/**
	 * Returns a range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration processes
	 */
	public java.util.List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce data integration processes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce data integration processes.
	 *
	 * @return the number of commerce data integration processes
	 */
	public int countAll();

}