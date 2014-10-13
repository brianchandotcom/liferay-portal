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

package com.liferay.portal.spring.jndi;

import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author Brian Wing Shun Chan
 */
public class JndiObjectFactoryBean
	extends org.springframework.jndi.JndiObjectFactoryBean {

	@Override
	protected Object lookup() {
		try {
			Properties properties = PropsUtil.getProperties(
				PropsKeys.JNDI_ENVIRONMENT, true);

			Context context = new InitialContext(properties);

			return JNDIUtil.lookup(context, getJndiName());
		}
		catch (Exception e) {
			_log.error("Unable to lookup " + getJndiName());

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JndiObjectFactoryBean.class);

}