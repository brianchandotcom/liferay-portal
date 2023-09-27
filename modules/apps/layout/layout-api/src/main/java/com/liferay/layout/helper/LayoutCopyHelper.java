/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.helper;

import com.liferay.portal.kernel.model.Layout;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pavel Savinov
 */
@ProviderType
public interface LayoutCopyHelper {

	public Layout copyLayoutContent(Layout sourceLayout, Layout targetLayout)
		throws Exception;

	public Layout copyLayoutContent(
			long segmentsExperienceId, Layout sourceLayout, Layout targetLayout)
		throws Exception;

	public Layout copyLayoutContent(
			long[] segmentsExperienceIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception;

}