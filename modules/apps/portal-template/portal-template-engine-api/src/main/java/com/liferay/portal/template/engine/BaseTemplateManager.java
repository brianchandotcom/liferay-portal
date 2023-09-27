/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.engine;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public abstract class BaseTemplateManager implements TemplateManager {

	@Override
	public String[] getRestrictedVariables() {
		return new String[0];
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return doGetTemplate(
			templateResource, restricted, getHelperUtilities(restricted));
	}

	protected abstract Template doGetTemplate(
		TemplateResource templateResource, boolean restricted,
		Map<String, Object> helperUtilities);

	protected Map<String, Object> getHelperUtilities(boolean restricted) {
		TemplateContextHelper templateContextHelper =
			getTemplateContextHelper();

		return templateContextHelper.getHelperUtilities(restricted);
	}

	protected abstract TemplateContextHelper getTemplateContextHelper();

}