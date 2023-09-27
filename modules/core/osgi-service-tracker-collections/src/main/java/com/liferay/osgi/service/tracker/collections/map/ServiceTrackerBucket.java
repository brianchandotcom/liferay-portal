/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;

/**
 * @author Carlos Sierra Andrés
 */
public interface ServiceTrackerBucket<SR, TS, R> {

	public R getContent();

	public boolean isDisposable();

	public void remove(
		ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple);

	public void store(
		ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple);

}