/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.processor;

/**
 * Locates the available {@link AMAsyncProcessor}.
 *
 * @author Adolfo Pérez
 */
public interface AMAsyncProcessorLocator {

	public <M> AMAsyncProcessor<M, ?> locateForClass(Class<M> clazz);

}