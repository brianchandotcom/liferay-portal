/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (springScopedBean == null) {
			SpringScopedBeanManager springScopedBeanManager =
				SpringScopedBeanManagerThreadLocal.
					getCurrentScopedBeanManager();

			springScopedBean = new SpringScopedBean(
				objectFactory.getObject(),
				springScopedBeanManager.unsetDestructionCallback(name),
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

		SpringScopedBeanManager springScopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		springScopedBeanManager.setDestructionCallback(
			name, destructionCallback);
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