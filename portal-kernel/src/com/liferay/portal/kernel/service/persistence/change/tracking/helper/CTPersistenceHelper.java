/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.change.tracking.helper;

import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface CTPersistenceHelper {

	public <T extends CTModel<T>> boolean isInsert(T ctModel);

	public <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass);

	public <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass, Serializable primaryKey);

	public <T extends CTModel<T>> boolean isRemove(T ctModel);

}