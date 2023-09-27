/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.finder;

/**
 * A marker interface that implementations of {@link AMFinder} must extend to
 * provide a way to create {@link AMQuery} instances.
 *
 * @author Adolfo Pérez
 */
public interface AMQueryBuilder<M, T> {
}