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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.templateparser.TemplateNode;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Mika Koivisto
 */
public class LiferayTemplateModel extends SimpleHash {

	public LiferayTemplateModel(
		TemplateNode templateNode, ObjectWrapper objectWrapper) {

		super(templateNode, objectWrapper);

		_beanModel = new BeanModel(templateNode, (BeansWrapper)objectWrapper);
	}

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		TemplateModel templateModel = super.get(key);

		if (templateModel != null) {
			return templateModel;
		}

		return _beanModel.get(key);
	}

	private final BeanModel _beanModel;

}