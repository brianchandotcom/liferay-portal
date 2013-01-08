/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.safe;

import com.liferay.portal.kernel.safe.model.Item;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.List;

/**
 * Utility class for {@link PortalSafe} service implementation. This is the main
 * entry point into Portal Safe.
 *
 * @author Tomas Polesovsky
 */
public class PortalSafeUtil {

	/**
	 * Returns instance of the wrapped {@link PortalSafe} instance.
	 *
	 * @return instance of the portal safe
	 */
	public static PortalSafe getPortalSafe() {
		PortalRuntimePermission.checkGetBeanProperty(PortalSafe.class);

		return _portalSafe;
	}

	/**
	 * Returns names of items stored in the company's group.
	 *
	 * <p>
	 * The method tries to get company's groupId using {@link
	 * #getCompanyGroupId(long)} and continues calling {@link
	 * #listItemsNames(long, long)}.
	 * </p>
	 *
	 * @param  companyId ID of the company to list the names from
	 * @return names of items in the company's default group
	 * @throws PortalSafeException if some exception occurs
	 */
	public static List<String> listItemsNames(long companyId)
		throws PortalSafeException {

		long groupId = getCompanyGroupId(companyId);

		return listItemsNames(companyId, groupId);
	}

	/**
	 * Returns names of items stored under the companyId and the groupId. The
	 * method uses wrapped instance to delegate the call to.
	 *
	 * @param  companyId ID of the company to list the names from
	 * @param  groupId ID of the group inside the company
	 * @return names of items
	 * @throws PortalSafeException if some exception occurs
	 */
	public static List<String> listItemsNames(long companyId, long groupId)
		throws PortalSafeException {

		return getPortalSafe().listItemsNames(companyId, groupId);
	}

	/**
	 * Loads an item with the name, saved inside the company's group.
	 *
	 * <p>
	 * The method tries to get company's groupId using {@link
	 * #getCompanyGroupId(long)} and continues calling {@link #loadItem(long,
	 * long, String)}.
	 * </p>
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to read the item </li>
	 * <li> {@link com.liferay.portal.kernel.safe.storage.StorageException} if
	 * there is a problem with loading the item from the underlying storage
	 * </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to load items from
	 * @param  name name of the item to be loaded
	 * @return descendant of {@link Item} interface
	 * @throws PortalSafeException if an error occurs
	 */
	public static Item loadItem(long companyId, String name)
		throws PortalSafeException {

		long groupId = getCompanyGroupId(companyId);

		return getPortalSafe().loadItem(companyId, groupId, name);
	}

	/**
	 * Loads and returns an item described by the companyId, the groupId and the
	 * item's name. The method uses wrapped instance to delegate the call to.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to read the item </li>
	 * <li> {@link com.liferay.portal.kernel.safe.storage.StorageException} if
	 * there is a problem with loading the item from the underlying storage
	 * </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to load items from
	 * @param  name name of the item to be loaded
	 * @param  groupId ID of group inside the company to load items from
	 * @return descendant of {@link Item} interface
	 * @throws PortalSafeException if an other error occurs
	 */
	public static Item loadItem(long companyId, String name, long groupId)
		throws PortalSafeException {

		return getPortalSafe().loadItem(companyId, groupId, name);
	}

	/**
	 * Removes the item from the safe, item must be saved in the company's
	 * group.
	 *
	 * <p>
	 * The method tries to get company's groupId using {@link
	 * #getCompanyGroupId(long)} and continues calling {@link #removeItem(long,
	 * long, String)}.
	 * </p>
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with accessing the item int the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to remove the item from
	 * @param  name name of the item to be removed
	 * @throws PortalSafeException if some error occurs during deleting
	 */
	public static void removeItem(long companyId, String name)
		throws PortalSafeException {

		long groupId = getCompanyGroupId(companyId);

		getPortalSafe().removeItem(companyId, groupId, name);
	}

	/**
	 * Removes the item from the safe. Item is described by the companyId, the
	 * groupId and the item's name. The method uses wrapped instance to delegate
	 * the call to.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with accessing the item int the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to remove the item from
	 * @param  name name of the item to be removed
	 * @param  groupId ID of group inside the company to remove the item from
	 * @throws PortalSafeException if some error occurs during deleting
	 */
	public static void removeItem(long companyId, String name, long groupId)
		throws PortalSafeException {

		getPortalSafe().removeItem(companyId, groupId, name);
	}

	/**
	 * Stores the item into safe using company's group.
	 *
	 * <p>
	 * The method tries to get company's groupId using {@link
	 * #getCompanyGroupId(long)} and continues calling {@link #saveItem(long,
	 * long, com.liferay.portal.kernel.safe.model.Item)}.
	 * </p>
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to convert the item
	 * into its binary form </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with saving the item into the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to save the item to
	 * @param  item the item to be saved
	 * @throws PortalSafeException if an other error occurs
	 */
	public static void saveItem(long companyId, Item item)
		throws PortalSafeException {

		long groupId = getCompanyGroupId(companyId);

		getPortalSafe().saveItem(companyId, groupId, item);
	}

	/**
	 * Stores the item into safe.The method uses wrapped instance to delegate
	 * the call to.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to convert the item
	 * into its binary form </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with saving the item into the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to save the item to
	 * @param  groupId ID of group inside the company to save the item to
	 * @param  item the item to be saved
	 * @throws PortalSafeException if an other error occurs
	 */
	public static void saveItem(long companyId, long groupId, Item item)
		throws PortalSafeException {

		getPortalSafe().saveItem(companyId, groupId, item);
	}

	/**
	 * Setup wrapped {@link PortalSafe} instance
	 *
	 * @param portalSafe the instance to be used as the wrapped service
	 */
	public void setPortalSafe(PortalSafe portalSafe) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalSafe = portalSafe;
	}

	/**
	 * Tries to load company's group via {@link GroupLocalServiceUtil}.
	 *
	 * @param  companyId the company ID to be used
	 * @return groupId of the company or {@link CompanyConstants#SYSTEM} if the
	 *         companyId is {@link CompanyConstants#SYSTEM}
	 * @throws PortalSafeException if {@link GroupLocalServiceUtil} throws the
	 *         exception
	 */
	private static long getCompanyGroupId(long companyId)
		throws PortalSafeException {

		if (companyId != CompanyConstants.SYSTEM) {
			try {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					companyId);

				return companyGroup.getGroupId();
			}
			catch (Exception e) {
				throw new PortalSafeException(
					"Unable to load company groupId", e);
			}
		}

		return CompanyConstants.SYSTEM;
	}

	/**
	 * Wrapped {@link PortalSafe} instance
	 */
	private static PortalSafe _portalSafe;

}