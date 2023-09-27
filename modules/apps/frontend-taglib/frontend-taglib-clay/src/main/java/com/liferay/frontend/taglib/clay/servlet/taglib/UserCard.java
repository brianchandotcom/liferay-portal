/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

/**
 * @author Eudaldo Alonso
 */
public interface UserCard extends BaseClayCard {

	public default String getIcon() {
		return null;
	}

	public default String getImageAlt() {
		return null;
	}

	public default String getImageSrc() {
		return null;
	}

	public String getName();

	public default String getSubtitle() {
		return null;
	}

	public default String getUserColorClass() {
		return null;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getUserColorClass()}
	 */
	@Deprecated
	public default String getUserColorCssClass() {
		return null;
	}

}