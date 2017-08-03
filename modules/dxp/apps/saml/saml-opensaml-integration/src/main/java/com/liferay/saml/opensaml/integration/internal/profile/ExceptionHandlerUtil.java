/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.saml.runtime.SamlException;

/**
 * @author Michael C. Han
 */
public class ExceptionHandlerUtil {

	public static void handleException(Exception e) throws PortalException {
		if (e instanceof PortalException) {
			throw (PortalException)e;
		}
		else if (e instanceof SystemException) {
			throw (SystemException)e;
		}
		else {
			throw new SamlException(e);
		}
	}

}