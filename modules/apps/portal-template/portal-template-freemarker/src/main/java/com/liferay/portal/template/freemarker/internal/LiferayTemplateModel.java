/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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