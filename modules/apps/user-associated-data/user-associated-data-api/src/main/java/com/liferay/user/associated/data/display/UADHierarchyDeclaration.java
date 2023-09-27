/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.display;

import java.util.Locale;

/**
 * Declares a hierarchical relationship between multiple UAD displays.
 *
 * @author Drew Brokke
 * @see    UADDisplay#getParentContainerId(Object)
 * @see    UADDisplay#getTopLevelContainer(Class, Serializable, Object)
 */
public interface UADHierarchyDeclaration {

	/**
	 * Returns an array of UAD displays that correspond to a container type.
	 * Order is significant here. The first item should represent the top-most
	 * type in the hierarchy, and each subsequent item should step down the
	 * hierarchy. The items retrieved using these UAD displays are shown before
	 * the items retrieved using the UAD displays from {@link
	 * #getNoncontainerUADDisplays()}. Often, an array of just one item is
	 * sufficient (in the case of folders and files, only a UAD display
	 * correlating to the folder type is returned).
	 *
	 * @return an array of UAD displays that correspond to a container type
	 */
	public UADDisplay<?>[] getContainerUADDisplays();

	/**
	 * Returns the translated label describing the entity types of the
	 * hierarchy.
	 *
	 * @param  locale the current locale
	 * @return the label describing the entity types of the hierarchy
	 */
	public String getEntitiesTypeLabel(Locale locale);

	/**
	 * Returns an array of field names to be rendered as columns in the UAD
	 * portlet's hierarchy view. The corresponding data for each field name
	 * should be retrievable inside the {@link UADDisplay#getFieldValues(Object,
	 * String[])} method of each UAD display returned from {@link
	 * #getContainerUADDisplays()} and {@link #getNoncontainerUADDisplays()}.
	 *
	 * @return an array of field names to be rendered as columns in the UAD
	 *         portlet's hierarchy view
	 */
	public default String[] getExtraColumnNames() {
		return new String[0];
	}

	/**
	 * Returns an array of UAD displays that correspond to a non-container type.
	 * The item types retrieved from these UAD displays are displayed in the
	 * same order as the array, and after the item types retrieved from {@link
	 * #getContainerUADDisplays()}. For example, in a folder and file structure,
	 * this returns the UAD display related to files.
	 *
	 * @return an array of UAD displays that correspond to a non-container type
	 */
	public UADDisplay<?>[] getNoncontainerUADDisplays();

}