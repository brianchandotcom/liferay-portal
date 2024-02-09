/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.locked.items.renderer;

import java.util.List;

/**
 * @author Marco Galluzzi
 */
public interface LockedItemsRendererRegistry {

	public LockedItemsRenderer getLockedItemsRenderer(String key);

	public List<LockedItemsRenderer> getLockedItemsRenderers();

	public int getLockedItemsRenderersCount();

}