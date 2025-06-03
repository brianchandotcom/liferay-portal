/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

/**
 * @author Iván Zaera Avellón
 */
public interface BaseRequestHelperInfo {

	/**
	 * The etag that unambiguously identifies a resource.
	 *
	 * Note that it can be null for resources where the computation of the etag
	 * can be very costly.
	 *
	 * @return the etag or null
	 * @review
	 */
	public String getETag();

	/**
	 * The max-age for a resource.
	 *
	 * Note that for immutable resources it may be ignored, as the max-age is
	 * set to the maximum possible value.
	 * @review
	 */
	public long getMaxAge();

	/**
	 * Whether the request is for an immutable resource.
	 *
	 * This is used to decide whether to send an infinitely cacheable or a
	 * time based cacheable response.
	 * @review
	 */
	public boolean isImmutable();

	/**
	 * Whether to use no-cache (instead of must-revalidate).
	 *
	 * Note that for immutable resources it may be ignored, as immutable
	 * resources are not meant to be revalidated but cached forever.
	 * @review
	 */
	public boolean isSendNoCache();

}