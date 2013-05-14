/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Juan Fernández
 */
@DoPrivileged
public class TemplateHandlerRegistryImpl implements TemplateHandlerRegistry {

	@Override
	public long[] getClassNameIds() {
		long[] classNameIds = new long[_templateHandlers.size()];

		int i = 0;

		for (Map.Entry<String, TemplateHandler> entry :
				_templateHandlers.entrySet()) {

			TemplateHandler templateHandler = entry.getValue();

			classNameIds[i++] = PortalUtil.getClassNameId(
				templateHandler.getClassName());
		}

		return classNameIds;
	}

	@Override
	public TemplateHandler getTemplateHandler(long classNameId) {
		String className = PortalUtil.getClassName(classNameId);

		return _templateHandlers.get(className);
	}

	@Override
	public TemplateHandler getTemplateHandler(String className) {
		return _templateHandlers.get(className);
	}

	@Override
	public List<TemplateHandler> getTemplateHandlers() {
		return ListUtil.fromMapValues(_templateHandlers);
	}

	@Override
	public void register(TemplateHandler templateHandler) {
		_templateHandlers.put(templateHandler.getClassName(), templateHandler);
	}

	@Override
	public void unregister(TemplateHandler templateHandler) {
		_templateHandlers.remove(templateHandler.getClassName());
	}

	private Map<String, TemplateHandler> _templateHandlers =
		new HashMap<String, TemplateHandler>();

}