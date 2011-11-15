/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ServletVelocityResourceListener extends VelocityResourceListener {

	@Override
	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		try {
			return doGetResourceStream(source);
		}
		catch (Exception e) {
			throw new ResourceNotFoundException(source);
		}
	}

	protected InputStream doGetResourceStream(String source) throws Exception {
		int pos = source.indexOf(SERVLET_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String servletContextName = source.substring(0, pos);

		if (Validator.isNull(servletContextName)) {
			servletContextName = PortalUtil.getPathContext();
		}

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			_log.error(
				source + " is not valid because " + servletContextName +
					" does not map to a servlet context");

			return null;
		}

		String name = source.substring(pos + SERVLET_SEPARATOR.length());

		if (_log.isDebugEnabled()) {
			_log.debug(
				name + " is associated with the servlet context " +
					servletContextName + " " + servletContext);
		}

		InputStream inputStream = servletContext.getResourceAsStream(name);

		if ((inputStream == null) && name.endsWith("/init_custom.vm")) {
			if (_log.isWarnEnabled()) {
				_log.warn("The template " + name + " should be created");
			}

			return new UnsyncByteArrayInputStream(new byte[0]);
		}
		else {
			return inputStream;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletVelocityResourceListener.class);

}