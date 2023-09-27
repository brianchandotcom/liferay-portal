/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * Can be implemented by any service needing to be notified when the
 * {@link NPMRegistry} is updated.
 *
 * @author Iván Zaera Avellón
 * @review
 */
public interface NPMRegistryUpdatesListener {

	public void onAfterUpdate();

}