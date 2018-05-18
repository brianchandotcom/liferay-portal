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

package com.liferay.commerce.notification.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the commerce notification queue entry service. This utility wraps {@link com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationQueueEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryPersistence
 * @see com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationQueueEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationQueueEntryUtil {
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
	public static void clearCache(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		getPersistence().clearCache(commerceNotificationQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationQueueEntry update(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		return getPersistence().update(commerceNotificationQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationQueueEntry update(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceNotificationQueueEntry, serviceContext);
	}

	/**
	* Returns all the commerce notification queue entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce notification queue entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63;.
	*
	* @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry[] findByGroupId_PrevAndNext(
		long commerceNotificationQueueEntryId, long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceNotificationQueueEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce notification queue entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce notification queue entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce notification queue entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns a range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry[] findByCommerceNotificationTemplateId_PrevAndNext(
		long commerceNotificationQueueEntryId,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_PrevAndNext(commerceNotificationQueueEntryId,
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	* Removes all the commerce notification queue entries where commerceNotificationTemplateId = &#63; from the database.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	*/
	public static void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		getPersistence()
			.removeByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns the number of commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the number of matching commerce notification queue entries
	*/
	public static int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return getPersistence()
				   .countByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns all the commerce notification queue entries where sent = &#63;.
	*
	* @param sent the sent
	* @return the matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findBySent(boolean sent) {
		return getPersistence().findBySent(sent);
	}

	/**
	* Returns a range of all the commerce notification queue entries where sent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sent the sent
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end) {
		return getPersistence().findBySent(sent, start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sent the sent
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().findBySent(sent, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sent the sent
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySent(sent, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	*
	* @param sent the sent
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findBySent_First(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findBySent_First(sent, orderByComparator);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	*
	* @param sent the sent
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchBySent_First(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchBySent_First(sent, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	*
	* @param sent the sent
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findBySent_Last(boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findBySent_Last(sent, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	*
	* @param sent the sent
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchBySent_Last(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchBySent_Last(sent, orderByComparator);
	}

	/**
	* Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sent = &#63;.
	*
	* @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	* @param sent the sent
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry[] findBySent_PrevAndNext(
		long commerceNotificationQueueEntryId, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findBySent_PrevAndNext(commerceNotificationQueueEntryId,
			sent, orderByComparator);
	}

	/**
	* Removes all the commerce notification queue entries where sent = &#63; from the database.
	*
	* @param sent the sent
	*/
	public static void removeBySent(boolean sent) {
		getPersistence().removeBySent(sent);
	}

	/**
	* Returns the number of commerce notification queue entries where sent = &#63;.
	*
	* @param sent the sent
	* @return the number of matching commerce notification queue entries
	*/
	public static int countBySent(boolean sent) {
		return getPersistence().countBySent(sent);
	}

	/**
	* Returns all the commerce notification queue entries where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @return the matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByLtS(Date sentDate) {
		return getPersistence().findByLtS(sentDate);
	}

	/**
	* Returns a range of all the commerce notification queue entries where sentDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sentDate the sent date
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByLtS(
		Date sentDate, int start, int end) {
		return getPersistence().findByLtS(sentDate, start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sentDate the sent date
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByLtS(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence()
				   .findByLtS(sentDate, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sentDate the sent date
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findByLtS(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLtS(sentDate, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByLtS_First(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findByLtS_First(sentDate, orderByComparator);
	}

	/**
	* Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByLtS_First(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchByLtS_First(sentDate, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry findByLtS_Last(Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().findByLtS_Last(sentDate, orderByComparator);
	}

	/**
	* Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByLtS_Last(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().fetchByLtS_Last(sentDate, orderByComparator);
	}

	/**
	* Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	*
	* @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	* @param sentDate the sent date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry[] findByLtS_PrevAndNext(
		long commerceNotificationQueueEntryId, Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByLtS_PrevAndNext(commerceNotificationQueueEntryId,
			sentDate, orderByComparator);
	}

	/**
	* Removes all the commerce notification queue entries where sentDate &lt; &#63; from the database.
	*
	* @param sentDate the sent date
	*/
	public static void removeByLtS(Date sentDate) {
		getPersistence().removeByLtS(sentDate);
	}

	/**
	* Returns the number of commerce notification queue entries where sentDate &lt; &#63;.
	*
	* @param sentDate the sent date
	* @return the number of matching commerce notification queue entries
	*/
	public static int countByLtS(Date sentDate) {
		return getPersistence().countByLtS(sentDate);
	}

	/**
	* Caches the commerce notification queue entry in the entity cache if it is enabled.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	*/
	public static void cacheResult(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		getPersistence().cacheResult(commerceNotificationQueueEntry);
	}

	/**
	* Caches the commerce notification queue entries in the entity cache if it is enabled.
	*
	* @param commerceNotificationQueueEntries the commerce notification queue entries
	*/
	public static void cacheResult(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {
		getPersistence().cacheResult(commerceNotificationQueueEntries);
	}

	/**
	* Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	*
	* @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	* @return the new commerce notification queue entry
	*/
	public static CommerceNotificationQueueEntry create(
		long commerceNotificationQueueEntryId) {
		return getPersistence().create(commerceNotificationQueueEntryId);
	}

	/**
	* Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry that was removed
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry remove(
		long commerceNotificationQueueEntryId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence().remove(commerceNotificationQueueEntryId);
	}

	public static CommerceNotificationQueueEntry updateImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		return getPersistence().updateImpl(commerceNotificationQueueEntry);
	}

	/**
	* Returns the commerce notification queue entry with the primary key or throws a {@link NoSuchNotificationQueueEntryException} if it could not be found.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry
	* @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry findByPrimaryKey(
		long commerceNotificationQueueEntryId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException {
		return getPersistence()
				   .findByPrimaryKey(commerceNotificationQueueEntryId);
	}

	/**
	* Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	*/
	public static CommerceNotificationQueueEntry fetchByPrimaryKey(
		long commerceNotificationQueueEntryId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceNotificationQueueEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceNotificationQueueEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce notification queue entries.
	*
	* @return the commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce notification queue entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findAll(int start,
		int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findAll(int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification queue entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce notification queue entries
	*/
	public static List<CommerceNotificationQueueEntry> findAll(int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce notification queue entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce notification queue entries.
	*
	* @return the number of commerce notification queue entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceNotificationQueueEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationQueueEntryPersistence, CommerceNotificationQueueEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationQueueEntryPersistence.class);

		ServiceTracker<CommerceNotificationQueueEntryPersistence, CommerceNotificationQueueEntryPersistence> serviceTracker =
			new ServiceTracker<CommerceNotificationQueueEntryPersistence, CommerceNotificationQueueEntryPersistence>(bundle.getBundleContext(),
				CommerceNotificationQueueEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}