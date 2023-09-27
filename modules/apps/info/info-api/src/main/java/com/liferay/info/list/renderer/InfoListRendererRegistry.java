/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.list.renderer;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface InfoListRendererRegistry {

	public InfoListRenderer<?> getInfoListRenderer(String key);

	public List<InfoListRenderer<?>> getInfoListRenderers();

	public List<InfoListRenderer<?>> getInfoListRenderers(String itemClassName);

}