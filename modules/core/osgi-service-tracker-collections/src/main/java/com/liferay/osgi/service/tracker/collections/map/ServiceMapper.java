/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

/**
 * @author Carlos Sierra Andrés
 */
public interface ServiceMapper<K, S> {

	public void map(S service, ServiceReferenceMapper.Emitter<K> emitter);

}