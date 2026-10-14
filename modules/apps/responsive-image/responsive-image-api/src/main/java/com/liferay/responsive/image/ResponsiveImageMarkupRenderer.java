/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Every consumer that needs image markup should call this renderer rather than
 * call a specific factory directly.
 *
 * @author Daniel Sanz
 */
@ProviderType
public interface ResponsiveImageMarkupRenderer {

	/**
	 * Returns responsive markup wrapping the image tag, or <code>null</code>
	 * when the framework declines to produce any. The original image tag keeps
	 * its attributes.
	 */
	public String render(
			String originalImgTag,
			ResponsiveImageRequest responsiveImageRequest)
		throws PortalException;

}