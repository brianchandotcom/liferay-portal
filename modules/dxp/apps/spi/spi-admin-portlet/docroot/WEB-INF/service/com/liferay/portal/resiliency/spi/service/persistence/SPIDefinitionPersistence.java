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

package com.liferay.portal.resiliency.spi.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.resiliency.spi.exception.NoSuchDefinitionException;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the spi definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinitionUtil
 * @generated
 */
@ProviderType
public interface SPIDefinitionPersistence
	extends BasePersistence<SPIDefinition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SPIDefinitionUtil} to access the spi definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition[] findByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition[] filterFindByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Removes all the spi definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByC_N(long companyId, String name)
		throws NoSuchDefinitionException;

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByC_N(long companyId, String name);

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the spi definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the spi definition that was removed
	 */
	public SPIDefinition removeByC_N(long companyId, String name)
		throws NoSuchDefinitionException;

	/**
	 * Returns the number of spi definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching spi definitions
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(long companyId, int status);

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByC_S_First(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByC_S_Last(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition[] findByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int status);

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition[] filterFindByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
				orderByComparator)
		throws NoSuchDefinitionException;

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses);

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public java.util.List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int[] statuses);

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public java.util.List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the spi definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByC_S(long companyId, int status);

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions
	 */
	public int countByC_S(long companyId, int status);

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions
	 */
	public int countByC_S(long companyId, int[] statuses);

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public int filterCountByC_S(long companyId, int status);

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public int filterCountByC_S(long companyId, int[] statuses);

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public SPIDefinition findByCA_CP(String connectorAddress, int connectorPort)
		throws NoSuchDefinitionException;

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort);

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort, boolean useFinderCache);

	/**
	 * Removes the spi definition where connectorAddress = &#63; and connectorPort = &#63; from the database.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the spi definition that was removed
	 */
	public SPIDefinition removeByCA_CP(
			String connectorAddress, int connectorPort)
		throws NoSuchDefinitionException;

	/**
	 * Returns the number of spi definitions where connectorAddress = &#63; and connectorPort = &#63;.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the number of matching spi definitions
	 */
	public int countByCA_CP(String connectorAddress, int connectorPort);

	/**
	 * Caches the spi definition in the entity cache if it is enabled.
	 *
	 * @param spiDefinition the spi definition
	 */
	public void cacheResult(SPIDefinition spiDefinition);

	/**
	 * Caches the spi definitions in the entity cache if it is enabled.
	 *
	 * @param spiDefinitions the spi definitions
	 */
	public void cacheResult(java.util.List<SPIDefinition> spiDefinitions);

	/**
	 * Creates a new spi definition with the primary key. Does not add the spi definition to the database.
	 *
	 * @param spiDefinitionId the primary key for the new spi definition
	 * @return the new spi definition
	 */
	public SPIDefinition create(long spiDefinitionId);

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition remove(long spiDefinitionId)
		throws NoSuchDefinitionException;

	public SPIDefinition updateImpl(SPIDefinition spiDefinition);

	/**
	 * Returns the spi definition with the primary key or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public SPIDefinition findByPrimaryKey(long spiDefinitionId)
		throws NoSuchDefinitionException;

	/**
	 * Returns the spi definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition, or <code>null</code> if a spi definition with the primary key could not be found
	 */
	public SPIDefinition fetchByPrimaryKey(long spiDefinitionId);

	/**
	 * Returns all the spi definitions.
	 *
	 * @return the spi definitions
	 */
	public java.util.List<SPIDefinition> findAll();

	/**
	 * Returns a range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of spi definitions
	 */
	public java.util.List<SPIDefinition> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of spi definitions
	 */
	public java.util.List<SPIDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of spi definitions
	 */
	public java.util.List<SPIDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SPIDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the spi definitions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of spi definitions.
	 *
	 * @return the number of spi definitions
	 */
	public int countAll();

}