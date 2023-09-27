/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface StagingModelListener<T extends BaseModel<T>> {

	public void onAfterCreate(T model) throws ModelListenerException;

	public void onAfterRemove(T model) throws ModelListenerException;

	public void onAfterUpdate(T model) throws ModelListenerException;

}