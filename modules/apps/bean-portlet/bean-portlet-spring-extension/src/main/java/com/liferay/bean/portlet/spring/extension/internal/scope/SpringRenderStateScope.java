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

package com.liferay.bean.portlet.spring.extension.internal.scope;

import javax.portlet.annotations.RenderStateScoped;

/**
 * @author Neil Griffin
 */
public class SpringRenderStateScope extends BaseScope {

	@Override
	public String getScopeName() {
		return RenderStateScoped.class.getSimpleName();
	}

	@Override
	protected SpringScopedBean getSpringScopedBean(String name) {
		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		return scopedBeanManager.getRenderStateScopedBean(name);
	}

	@Override
	protected void setSpringScopedBean(
		String name, SpringScopedBean springScopedBean) {

		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		scopedBeanManager.setRenderStateScopedBean(name, springScopedBean);
	}

}