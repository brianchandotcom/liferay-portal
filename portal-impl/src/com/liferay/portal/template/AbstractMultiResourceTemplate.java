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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Writer;

import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Ligas
 */
public abstract class AbstractMultiResourceTemplate extends AbstractTemplate {

	public AbstractMultiResourceTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper, String templateManagerName,
		long interval) {

		super(
			errorTemplateResource, context, templateContextHelper,
			templateManagerName);

		if (ListUtil.isEmpty(templateResources)) {
			throw new IllegalArgumentException("Template resource is null");
		}

		this.templateResources = templateResources;
	}

	@Override
	public void doProcessTemplate(Writer writer) throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		put(TemplateConstants.WRITER, unsyncStringWriter);

		processTemplates(templateResources, unsyncStringWriter);

		StringBundler sb = unsyncStringWriter.getStringBundler();

		sb.writeTo(writer);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		if (errorTemplateResource == null) {
			try {
				processTemplates(templateResources, writer);

				return;
			}
			catch (Exception e) {
				StringBuilder sb = new StringBuilder();

				for (TemplateResource templateResource : templateResources) {
					sb.append(templateResource.getTemplateId());
					sb.append(",");
				}

				throw new TemplateException("Unable to process templates", e);
			}
		}

		write(writer);
	}

	protected abstract void processTemplates(
			List<TemplateResource> templateResource, Writer writer)
		throws Exception;

	protected List<TemplateResource> templateResources;

}