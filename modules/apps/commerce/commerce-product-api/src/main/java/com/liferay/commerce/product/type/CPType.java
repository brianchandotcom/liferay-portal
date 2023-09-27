/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public interface CPType {

	public void deleteCPDefinition(long cpDefinitionId) throws PortalException;

	public String getLabel(Locale locale);

	public String getName();

	public default boolean isActive() {
		return true;
	}

	public default boolean isConfigurationEnabled() {
		return true;
	}

	public default boolean isDetailsEnabled() {
		return true;
	}

	public default boolean isMediaEnabled() {
		return true;
	}

	public default boolean isOptionsEnabled() {
		return true;
	}

	public default boolean isProductGroupsEnabled() {
		return true;
	}

	public default boolean isProductRelationsEnabled() {
		return true;
	}

	public default boolean isSkusEnabled() {
		return true;
	}

	public default boolean isSubscriptionEnabled() {
		return true;
	}

	public default boolean isVisibilityEnabled() {
		return true;
	}

}