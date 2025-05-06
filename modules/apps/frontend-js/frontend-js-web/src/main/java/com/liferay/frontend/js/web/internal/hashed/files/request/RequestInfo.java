/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request;

/**
 * @author Iván Zaera Avellón
 */
public interface RequestInfo {

	/**
	 * The hash than unambiguously identifies a resource. Note that it can be
	 * null for virtual resources.
	 *
	 * @return the hash or null
	 */
	public String getHash();

	/**
	 * The max-age of a virtual resource. Note that for hashed resources it is
	 * usually ignored as the max-age is set to the maximum possible value.
	 */
	public long getMaxAge();

	/**
	 * Whether to use no-cache (instead of must-revalidate) for virtual
	 * resources.
	 */
	public boolean getSendNoCache();

	/**
	 * Whether the request is for a virtual resource, i.e., a non hashed
	 * resource path.
	 *
	 * When a virtual resource is requested, the caching is based on max-age
	 * and must-revalidate/no-cache directives in the Cache-Control header and
	 * the hash can be null (if unknown). If the hash is not null it is used as
	 * its ETag.
	 *
	 * When a hashed resource is requested, the hash identifies the physical
	 * resource unambiguously and the immutable directive and the maximum
	 * possible max-age are sent in the Cache-Control header.
	 */
	public boolean isVirtual();

}