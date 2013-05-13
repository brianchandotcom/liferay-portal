/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.audit;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Michael C. Han
 */
@DoPrivileged
public class AuditRouterProxyBean extends BaseProxyBean implements AuditRouter {

	@Override
	public void route(AuditMessage auditMessage) {
		throw new UnsupportedOperationException();
	}

}