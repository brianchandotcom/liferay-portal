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

package com.liferay.portal.workflow.kaleo.forms.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessLinkException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo process link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkUtil
 * @generated
 */
@ProviderType
public interface KaleoProcessLinkPersistence
	extends BasePersistence<KaleoProcessLink> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoProcessLinkUtil} to access the kaleo process link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the matching kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId);

	/**
	 * Returns a range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of matching kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findByKaleoProcessId(
		long kaleoProcessId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink findByKaleoProcessId_First(
			long kaleoProcessId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
				orderByComparator)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink fetchByKaleoProcessId_First(
		long kaleoProcessId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator);

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink findByKaleoProcessId_Last(
			long kaleoProcessId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
				orderByComparator)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink fetchByKaleoProcessId_Last(
		long kaleoProcessId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator);

	/**
	 * Returns the kaleo process links before and after the current kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessLinkId the primary key of the current kaleo process link
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	public KaleoProcessLink[] findByKaleoProcessId_PrevAndNext(
			long kaleoProcessLinkId, long kaleoProcessId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
				orderByComparator)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Removes all the kaleo process links where kaleoProcessId = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 */
	public void removeByKaleoProcessId(long kaleoProcessId);

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the number of matching kaleo process links
	 */
	public int countByKaleoProcessId(long kaleoProcessId);

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or throws a <code>NoSuchKaleoProcessLinkException</code> if it could not be found.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink findByKPI_WTN(
			long kaleoProcessId, String workflowTaskName)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink fetchByKPI_WTN(
		long kaleoProcessId, String workflowTaskName);

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	public KaleoProcessLink fetchByKPI_WTN(
		long kaleoProcessId, String workflowTaskName, boolean useFinderCache);

	/**
	 * Removes the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the kaleo process link that was removed
	 */
	public KaleoProcessLink removeByKPI_WTN(
			long kaleoProcessId, String workflowTaskName)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching kaleo process links
	 */
	public int countByKPI_WTN(long kaleoProcessId, String workflowTaskName);

	/**
	 * Caches the kaleo process link in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLink the kaleo process link
	 */
	public void cacheResult(KaleoProcessLink kaleoProcessLink);

	/**
	 * Caches the kaleo process links in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLinks the kaleo process links
	 */
	public void cacheResult(java.util.List<KaleoProcessLink> kaleoProcessLinks);

	/**
	 * Creates a new kaleo process link with the primary key. Does not add the kaleo process link to the database.
	 *
	 * @param kaleoProcessLinkId the primary key for the new kaleo process link
	 * @return the new kaleo process link
	 */
	public KaleoProcessLink create(long kaleoProcessLinkId);

	/**
	 * Removes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link that was removed
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	public KaleoProcessLink remove(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException;

	public KaleoProcessLink updateImpl(KaleoProcessLink kaleoProcessLink);

	/**
	 * Returns the kaleo process link with the primary key or throws a <code>NoSuchKaleoProcessLinkException</code> if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	public KaleoProcessLink findByPrimaryKey(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException;

	/**
	 * Returns the kaleo process link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link, or <code>null</code> if a kaleo process link with the primary key could not be found
	 */
	public KaleoProcessLink fetchByPrimaryKey(long kaleoProcessLinkId);

	/**
	 * Returns all the kaleo process links.
	 *
	 * @return the kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findAll();

	/**
	 * Returns a range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo process links
	 */
	public java.util.List<KaleoProcessLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcessLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo process links from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo process links.
	 *
	 * @return the number of kaleo process links
	 */
	public int countAll();

}