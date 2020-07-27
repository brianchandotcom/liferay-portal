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

package com.liferay.app.builder.workflow.service.persistence;

import com.liferay.app.builder.workflow.exception.NoSuchTaskLinkException;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the app builder workflow task link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLinkUtil
 * @generated
 */
@ProviderType
public interface AppBuilderWorkflowTaskLinkPersistence
	extends BasePersistence<AppBuilderWorkflowTaskLink> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderWorkflowTaskLinkUtil} to access the app builder workflow task link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId);

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public void removeByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder workflow task links
	 */
	public int countByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId);

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByA_A_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByA_A_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink[] findByA_A_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 */
	public void removeByA_A(long appBuilderAppId, long appBuilderAppVersionId);

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the number of matching app builder workflow task links
	 */
	public int countByA_A(long appBuilderAppId, long appBuilderAppVersionId);

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName);

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByA_A_W_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_W_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByA_A_W_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_W_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink[] findByA_A_W_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId, String workflowTaskName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException;

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 */
	public void removeByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName);

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching app builder workflow task links
	 */
	public int countByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName);

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or throws a <code>NoSuchTaskLinkException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink findByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName);

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName,
		boolean useFinderCache);

	/**
	 * Removes the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the app builder workflow task link that was removed
	 */
	public AppBuilderWorkflowTaskLink removeByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching app builder workflow task links
	 */
	public int countByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName);

	/**
	 * Caches the app builder workflow task link in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 */
	public void cacheResult(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink);

	/**
	 * Caches the app builder workflow task links in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLinks the app builder workflow task links
	 */
	public void cacheResult(
		java.util.List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks);

	/**
	 * Creates a new app builder workflow task link with the primary key. Does not add the app builder workflow task link to the database.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key for the new app builder workflow task link
	 * @return the new app builder workflow task link
	 */
	public AppBuilderWorkflowTaskLink create(long appBuilderWorkflowTaskLinkId);

	/**
	 * Removes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink remove(long appBuilderWorkflowTaskLinkId)
		throws NoSuchTaskLinkException;

	public AppBuilderWorkflowTaskLink updateImpl(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink);

	/**
	 * Returns the app builder workflow task link with the primary key or throws a <code>NoSuchTaskLinkException</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink findByPrimaryKey(
			long appBuilderWorkflowTaskLinkId)
		throws NoSuchTaskLinkException;

	/**
	 * Returns the app builder workflow task link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link, or <code>null</code> if a app builder workflow task link with the primary key could not be found
	 */
	public AppBuilderWorkflowTaskLink fetchByPrimaryKey(
		long appBuilderWorkflowTaskLinkId);

	/**
	 * Returns all the app builder workflow task links.
	 *
	 * @return the app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findAll();

	/**
	 * Returns a range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder workflow task links
	 */
	public java.util.List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the app builder workflow task links from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of app builder workflow task links.
	 *
	 * @return the number of app builder workflow task links
	 */
	public int countAll();

}