/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCacheListener;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public interface PortalCacheReplicator
	<K extends Serializable, V extends Serializable>
		extends PortalCacheListener<K, V> {

	public static final boolean DEFAULT_REPLICATE_PUTS = true;

	public static final boolean DEFAULT_REPLICATE_PUTS_VIA_COPY = false;

	public static final boolean DEFAULT_REPLICATE_REMOVALS = true;

	public static final boolean DEFAULT_REPLICATE_UPDATES = true;

	public static final boolean DEFAULT_REPLICATE_UPDATES_VIA_COPY = false;

	public static final String REPLICATE_PUTS = "replicatePuts";

	public static final String REPLICATE_PUTS_VIA_COPY = "replicatePutsViaCopy";

	public static final String REPLICATE_REMOVALS = "replicateRemovals";

	public static final String REPLICATE_UPDATES = "replicateUpdates";

	public static final String REPLICATE_UPDATES_VIA_COPY =
		"replicateUpdatesViaCopy";

	public static final String REPLICATOR = "replicator";

}