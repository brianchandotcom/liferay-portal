/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.util;

import com.liferay.analytics.message.sender.model.EntityModelListener;

import java.util.Collection;

/**
 * @author     Rachael Koestartyo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public interface EntityModelListenerRegistry {

	public EntityModelListener getEntityModelListener(String className);

	public Collection<EntityModelListener> getEntityModelListeners();

}