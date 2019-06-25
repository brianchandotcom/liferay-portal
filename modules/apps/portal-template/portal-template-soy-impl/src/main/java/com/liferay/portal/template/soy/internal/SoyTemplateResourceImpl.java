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

package com.liferay.portal.template.soy.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.soy.SoyTemplateResource;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.List;

/**
 * @author Tina Tian
 */
public class SoyTemplateResourceImpl implements SoyTemplateResource {

	public SoyTemplateResourceImpl(List<TemplateResource> templateResources) {
		_templateResources = templateResources;
	}

	@Override
	public long getLastModified() {
		throw new UnsupportedOperationException(
			"Soy template resource does not support this method");
	}

	@Override
	public Reader getReader() {
		throw new UnsupportedOperationException(
			"Soy template resource does not support this method");
	}

	@Override
	public String getTemplateId() {
		String templateId = _templateId;

		if (templateId != null) {
			return templateId;
		}

		StringBundler sb = new StringBundler(_templateResources.size() * 2 - 1);

		for (TemplateResource templateResource : _templateResources) {
			if (sb.index() > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(templateResource.getTemplateId());
		}

		templateId = sb.toString();

		_templateId = templateId;

		return templateId;
	}

	public List<TemplateResource> getTemplateResources() {
		return _templateResources;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		for (TemplateResource templateResource : _templateResources) {
			templateResource.readExternal(objectInput);
		}
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		for (TemplateResource templateResource : _templateResources) {
			templateResource.writeExternal(objectOutput);
		}
	}

	private volatile String _templateId;
	private final List<TemplateResource> _templateResources;

}