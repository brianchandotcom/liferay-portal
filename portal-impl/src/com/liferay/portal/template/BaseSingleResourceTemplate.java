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

package com.liferay.portal.template;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Writer;

import java.util.Map;

/**
 * @author Miroslav Ligas
 */
public abstract class BaseSingleResourceTemplate extends BaseTemplate {

	public BaseSingleResourceTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper,
		TemplateResourceCache templateResourceCache) {

		super(errorTemplateResource, context, templateContextHelper);

		if (templateResource == null) {
			throw new IllegalArgumentException("Template resource is null");
		}

		this.templateResource = templateResource;

		if (templateResourceCache.isEnabled()) {
			cacheTemplateResource(templateResourceCache);
		}
	}

	@Override
	public void doProcessTemplate(Writer writer) throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		put(TemplateConstants.WRITER, unsyncStringWriter);

		processTemplate(templateResource, unsyncStringWriter);

		StringBundler sb = unsyncStringWriter.getStringBundler();

		sb.writeTo(writer);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		if (errorTemplateResource == null) {
			try {
				processTemplate(templateResource, writer);

				return;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process template " +
						templateResource.getTemplateId(),
					e);
			}
		}

		write(writer);
	}

	protected void cacheTemplateResource(
		TemplateResourceCache templateResourceCache) {

		TemplateResource cachedTemplateResource =
			templateResourceCache.getTemplateResource(
				templateResource.getTemplateId());

		if ((cachedTemplateResource == null) ||
			!templateResource.equals(cachedTemplateResource)) {

			templateResourceCache.put(
				templateResource.getTemplateId(), templateResource);
		}

		if (errorTemplateResource == null) {
			return;
		}

		TemplateResource cachedErrorTemplateResource =
			templateResourceCache.getTemplateResource(
				errorTemplateResource.getTemplateId());

		if ((cachedErrorTemplateResource == null) ||
			!errorTemplateResource.equals(cachedErrorTemplateResource)) {

			templateResourceCache.put(
				errorTemplateResource.getTemplateId(), errorTemplateResource);
		}
	}

	protected abstract void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception;

	protected TemplateResource templateResource;

}