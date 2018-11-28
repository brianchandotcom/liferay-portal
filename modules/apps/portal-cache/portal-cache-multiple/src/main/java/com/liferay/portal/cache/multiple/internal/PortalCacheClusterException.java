/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.cache.multiple.internal;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterException extends RuntimeException {

	public PortalCacheClusterException() {
	}

	public PortalCacheClusterException(String msg) {
		super(msg);
	}

	public PortalCacheClusterException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PortalCacheClusterException(Throwable cause) {
		super(cause);
	}

}