/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Raymond Augé
 */
public class LiferayTemplateLoader implements TemplateLoader {

	public Object findTemplateSource(String templateId) throws IOException {
		try {
			return TemplateResourceLoaderUtil.getTemplateResource(
				TemplateManager.FREEMARKER, templateId);
		}
		catch (TemplateException te) {
			throw new IOException(te);
		}
	}

	public long getLastModified(Object templateSource) {
		TemplateResource templateResource = (TemplateResource)templateSource;

		return templateResource.getLastModified();
	}

	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		TemplateResource templateResource = (TemplateResource)templateSource;

		return templateResource.getReader();
	}

	public void closeTemplateSource(Object templateSource) throws IOException {
		TemplateResource templateResource = (TemplateResource)templateSource;

		try {
			TemplateResourceLoaderUtil.clearCache(
				TemplateManager.FREEMARKER, templateResource.getTemplateId());
		}
		catch (TemplateException te) {
			throw new IOException(te);
		}
	}

}