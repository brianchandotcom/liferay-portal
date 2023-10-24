/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class TemplateHandlerRegistryUtil {

	public static long[] getClassNameIds() {
		TemplateHandlerRegistry templateHandlerRegistry =
			_templateHandlerRegistrySnapshot.get();

		return templateHandlerRegistry.getClassNameIds();
	}

	public static TemplateHandler getTemplateHandler(long classNameId) {
		TemplateHandlerRegistry templateHandlerRegistry =
			_templateHandlerRegistrySnapshot.get();

		return templateHandlerRegistry.getTemplateHandler(classNameId);
	}

	public static TemplateHandler getTemplateHandler(String className) {
		TemplateHandlerRegistry templateHandlerRegistry =
			_templateHandlerRegistrySnapshot.get();

		return templateHandlerRegistry.getTemplateHandler(className);
	}

	public static List<TemplateHandler> getTemplateHandlers() {
		TemplateHandlerRegistry templateHandlerRegistry =
			_templateHandlerRegistrySnapshot.get();

		return templateHandlerRegistry.getTemplateHandlers();
	}

	private static final Snapshot<TemplateHandlerRegistry>
		_templateHandlerRegistrySnapshot = new Snapshot<>(
			TemplateHandlerRegistryUtil.class, TemplateHandlerRegistry.class);

}