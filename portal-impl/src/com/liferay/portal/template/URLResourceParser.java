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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;

import java.io.IOException;

import java.net.URL;

/**
 * @author Tina Tian
 */
public abstract class URLResourceParser implements TemplateResourceParser {

	@Override
	public TemplateResource getTemplateResource(String templateId)
		throws TemplateException {

		try {
			URL url = getURL(templateId);

			if (url == null) {
				return null;
			}

			return new URLTemplateResource(templateId, url);
		}
		catch (IOException ioe) {
			throw new TemplateException(ioe);
		}
	}

	public abstract URL getURL(String templateId) throws IOException;

}