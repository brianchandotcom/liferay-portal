/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.exception.ModelListenerException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@ProviderType
public interface ModelListener<T> {

	public Class<?> getModelClass();

	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException;

	public void onAfterCreate(T model) throws ModelListenerException;

	public void onAfterRemove(T model) throws ModelListenerException;

	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException;

	public void onAfterUpdate(T originalModel, T model)
		throws ModelListenerException;

	public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException;

	public void onBeforeCreate(T model) throws ModelListenerException;

	public void onBeforeRemove(T model) throws ModelListenerException;

	public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException;

	public void onBeforeUpdate(T originalModel, T model)
		throws ModelListenerException;

}