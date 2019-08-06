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

package com.liferay.oauth.service.persistence;

import com.liferay.oauth.exception.NoSuchApplicationException;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the o auth application service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplicationUtil
 * @generated
 */
@ProviderType
public interface OAuthApplicationPersistence
	extends BasePersistence<OAuthApplication> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthApplicationUtil} to access the o auth application persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication[] findByCompanyId_PrevAndNext(
			long oAuthApplicationId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Removes all the o auth applications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth applications
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByUserId(long userId);

	/**
	 * Returns a range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication[] findByUserId_PrevAndNext(
			long oAuthApplicationId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Removes all the o auth applications where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth applications
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the o auth application where consumerKey = &#63; or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByConsumerKey(String consumerKey)
		throws NoSuchApplicationException;

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByConsumerKey(String consumerKey);

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByConsumerKey(
		String consumerKey, boolean useFinderCache);

	/**
	 * Removes the o auth application where consumerKey = &#63; from the database.
	 *
	 * @param consumerKey the consumer key
	 * @return the o auth application that was removed
	 */
	public OAuthApplication removeByConsumerKey(String consumerKey)
		throws NoSuchApplicationException;

	/**
	 * Returns the number of o auth applications where consumerKey = &#63;.
	 *
	 * @param consumerKey the consumer key
	 * @return the number of matching o auth applications
	 */
	public int countByConsumerKey(String consumerKey);

	/**
	 * Returns all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByC_N(
		long companyId, String name);

	/**
	 * Returns a range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByC_N(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByC_N_First(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByC_N_First(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByC_N_Last(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByC_N_Last(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication[] findByC_N_PrevAndNext(
			long oAuthApplicationId, long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Removes all the o auth applications where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public void removeByC_N(long companyId, String name);

	/**
	 * Returns the number of o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByU_N(long userId, String name);

	/**
	 * Returns a range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByU_N(
		long userId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByU_N(
		long userId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public java.util.List<OAuthApplication> findByU_N(
		long userId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByU_N_First(
			long userId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByU_N_First(
		long userId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public OAuthApplication findByU_N_Last(
			long userId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public OAuthApplication fetchByU_N_Last(
		long userId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication[] findByU_N_PrevAndNext(
			long oAuthApplicationId, long userId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
				orderByComparator)
		throws NoSuchApplicationException;

	/**
	 * Removes all the o auth applications where userId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param name the name
	 */
	public void removeByU_N(long userId, String name);

	/**
	 * Returns the number of o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	public int countByU_N(long userId, String name);

	/**
	 * Caches the o auth application in the entity cache if it is enabled.
	 *
	 * @param oAuthApplication the o auth application
	 */
	public void cacheResult(OAuthApplication oAuthApplication);

	/**
	 * Caches the o auth applications in the entity cache if it is enabled.
	 *
	 * @param oAuthApplications the o auth applications
	 */
	public void cacheResult(java.util.List<OAuthApplication> oAuthApplications);

	/**
	 * Creates a new o auth application with the primary key. Does not add the o auth application to the database.
	 *
	 * @param oAuthApplicationId the primary key for the new o auth application
	 * @return the new o auth application
	 */
	public OAuthApplication create(long oAuthApplicationId);

	/**
	 * Removes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application that was removed
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication remove(long oAuthApplicationId)
		throws NoSuchApplicationException;

	public OAuthApplication updateImpl(OAuthApplication oAuthApplication);

	/**
	 * Returns the o auth application with the primary key or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public OAuthApplication findByPrimaryKey(long oAuthApplicationId)
		throws NoSuchApplicationException;

	/**
	 * Returns the o auth application with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application, or <code>null</code> if a o auth application with the primary key could not be found
	 */
	public OAuthApplication fetchByPrimaryKey(long oAuthApplicationId);

	/**
	 * Returns all the o auth applications.
	 *
	 * @return the o auth applications
	 */
	public java.util.List<OAuthApplication> findAll();

	/**
	 * Returns a range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of o auth applications
	 */
	public java.util.List<OAuthApplication> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth applications
	 */
	public java.util.List<OAuthApplication> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth applications
	 */
	public java.util.List<OAuthApplication> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthApplication>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the o auth applications from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of o auth applications.
	 *
	 * @return the number of o auth applications
	 */
	public int countAll();

}