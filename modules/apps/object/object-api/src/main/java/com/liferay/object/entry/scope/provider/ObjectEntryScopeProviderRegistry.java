/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.scope.provider;

/**
 * @author Carolina Barbosa
 */
public interface ObjectEntryScopeProviderRegistry {

	public ObjectEntryScopeProvider getObjectEntryScopeProvider(
		String objectDefinitionExternalReferenceCode);

}