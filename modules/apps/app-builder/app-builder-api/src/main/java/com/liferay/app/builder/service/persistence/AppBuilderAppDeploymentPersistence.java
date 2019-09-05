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

package com.liferay.app.builder.service.persistence;

import com.liferay.app.builder.exception.NoSuchAppDeploymentException;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the app builder app deployment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentUtil
 * @generated
 */
@ProviderType
public interface AppBuilderAppDeploymentPersistence
	extends BasePersistence<AppBuilderAppDeployment> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppDeploymentUtil} to access the app builder app deployment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId);

	/**
	 * Returns a range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of matching app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment findByAppBuilderAppId_First(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException;

	/**
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator);

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment findByAppBuilderAppId_Last(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException;

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator);

	/**
	 * Returns the app builder app deployments before and after the current app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the current app builder app deployment
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public AppBuilderAppDeployment[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppDeploymentId, long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException;

	/**
	 * Removes all the app builder app deployments where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public void removeByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app deployments
	 */
	public int countByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment findByA_T(long appBuilderAppId, String type)
		throws NoSuchAppDeploymentException;

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type);

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type, boolean useFinderCache);

	/**
	 * Removes the app builder app deployment where appBuilderAppId = &#63; and type = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the app builder app deployment that was removed
	 */
	public AppBuilderAppDeployment removeByA_T(
			long appBuilderAppId, String type)
		throws NoSuchAppDeploymentException;

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63; and type = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the number of matching app builder app deployments
	 */
	public int countByA_T(long appBuilderAppId, String type);

	/**
	 * Caches the app builder app deployment in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 */
	public void cacheResult(AppBuilderAppDeployment appBuilderAppDeployment);

	/**
	 * Caches the app builder app deployments in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployments the app builder app deployments
	 */
	public void cacheResult(
		java.util.List<AppBuilderAppDeployment> appBuilderAppDeployments);

	/**
	 * Creates a new app builder app deployment with the primary key. Does not add the app builder app deployment to the database.
	 *
	 * @param appBuilderAppDeploymentId the primary key for the new app builder app deployment
	 * @return the new app builder app deployment
	 */
	public AppBuilderAppDeployment create(long appBuilderAppDeploymentId);

	/**
	 * Removes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public AppBuilderAppDeployment remove(long appBuilderAppDeploymentId)
		throws NoSuchAppDeploymentException;

	public AppBuilderAppDeployment updateImpl(
		AppBuilderAppDeployment appBuilderAppDeployment);

	/**
	 * Returns the app builder app deployment with the primary key or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public AppBuilderAppDeployment findByPrimaryKey(
			long appBuilderAppDeploymentId)
		throws NoSuchAppDeploymentException;

	/**
	 * Returns the app builder app deployment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment, or <code>null</code> if a app builder app deployment with the primary key could not be found
	 */
	public AppBuilderAppDeployment fetchByPrimaryKey(
		long appBuilderAppDeploymentId);

	/**
	 * Returns all the app builder app deployments.
	 *
	 * @return the app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findAll();

	/**
	 * Returns a range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app deployments
	 */
	public java.util.List<AppBuilderAppDeployment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDeployment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the app builder app deployments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of app builder app deployments.
	 *
	 * @return the number of app builder app deployments
	 */
	public int countAll();

}