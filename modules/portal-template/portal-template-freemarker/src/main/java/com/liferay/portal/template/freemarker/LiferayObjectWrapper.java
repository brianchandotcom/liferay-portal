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

package com.liferay.portal.template.freemarker;

import com.liferay.portal.kernel.templateparser.TemplateNode;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Mika Koivisto
 */
public class LiferayObjectWrapper extends DefaultObjectWrapper {

	public LiferayObjectWrapper() {
		super(Configuration.getVersion());
	}

	@Override
	public TemplateModel wrap(Object object) throws TemplateModelException {
		if (object instanceof TemplateNode) {
			return new LiferayTemplateModel((TemplateNode)object, this);
		}

		return super.wrap(object);
	}

}