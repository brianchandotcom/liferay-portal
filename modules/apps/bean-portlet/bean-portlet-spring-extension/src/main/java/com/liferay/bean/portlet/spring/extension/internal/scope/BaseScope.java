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

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * @author Neil Griffin
 */
public abstract class BaseScope implements Scope {

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		SpringScopedBean springScopedBean = getSpringScopedBean(name);

		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		if (springScopedBean == null) {
			springScopedBean = new SpringScopedBean(
				objectFactory.getObject(),
				scopedBeanManager.unsetDestructionCallback(name),
				getScopeName());

			setSpringScopedBean(name, springScopedBean);
		}

		return springScopedBean.getContainerCreatedInstance();
	}

	@Override
	public String getConversationId() {
		return null;
	}

	public abstract String getScopeName();

	@Override
	public void registerDestructionCallback(
		String name, Runnable destructionCallback) {

		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		scopedBeanManager.setDestructionCallback(name, destructionCallback);
	}

	@Override
	public Object remove(String name) {
		return null;
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	protected abstract SpringScopedBean getSpringScopedBean(String name);

	protected abstract void setSpringScopedBean(
		String name, SpringScopedBean springScopedBean);

}