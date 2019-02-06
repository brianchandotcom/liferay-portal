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

package com.liferay.user.associated.data.display;

/**
 * Declares a hierarchical relationship between multiple UADDisplays.
 *
 * @author Drew Brokke
 * @see UADDisplay#getParentContainerId(Object)
 * @see UADDisplay#getTopLevelContainer(Class, Serializable, Object)
 */
public interface UADHierarchyDeclaration {

	/**
	 * Returns an array of UADDisplays that correspond to a container type.
	 * Order is significant here. The first item should represent the top-most
	 * type in the hierarchy, and each subsequent item should step down the
	 * hierarchy. The items retrieved using these UADDisplays will be shown
	 * before the items retrieved using the UADDisplays from
	 * {@link #getNoncontainerUADDisplays()}. Often an array of just one item is
	 * sufficient (in the case of folders and files, only a UADDisplay
	 * correlating to the folder type would be returned).
	 *
	 * @return an array of UADDisplays that correspond to a container type.
	 * @review
	 */
	public UADDisplay<?>[] getContainerUADDisplays();

	/**
	 * Returns an array of field names to be rendered as columns in the
	 * hierarchy view of the UADPortlet.  The corresponding data for each field
	 * name should be retrievable inside the
	 * {@link UADDisplay#getFieldValues(Object, String[])} method of each
	 * UADDisplay returned from {@link #getContainerUADDisplays()} and
	 * {@link #getNoncontainerUADDisplays()}.
	 *
	 * @return
	 * @review
	 */
	public default String[] getExtraColumnNames() {
		return new String[0];
	}

	/**
	 * Returns an array of UADDisplays that correspond to a non-container type.
	 * The item types retrieved from these UADDisplays will be displayed in the
	 * same order as the array, and after the item types retrieved from
	 * {@link #getContainerUADDisplays()}. For example, in a folder and file
	 * structure, this would return the UADDisplay related to files.
	 *
	 * @return an array of UADDisplays that correspond to a non-container type.
	 * @review
	 */
	public UADDisplay<?>[] getNoncontainerUADDisplays();

}