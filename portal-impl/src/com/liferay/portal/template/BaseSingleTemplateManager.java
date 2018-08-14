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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public abstract class BaseSingleTemplateManager extends BaseTemplateManager {

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources, boolean restricted) {

		return getTemplate(templateResources, null, restricted);
	}

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, boolean restricted) {

		throw new UnsupportedOperationException(
			"Template type does not support multi templates");
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return getTemplate(templateResource, null, restricted);
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		return doGetTemplate(
			templateResource, errorTemplateResource, restricted,
			getHelperUtilities(restricted));
	}

	protected abstract Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #doGetTemplate(TemplateResource, TemplateResource, boolean, Map)}
	 */
	@Deprecated
	protected Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged) {

		return doGetTemplate(
			templateResource, errorTemplateResource, restricted,
			helperUtilities);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected class DoGetSingleTemplatePrivilegedAction
		extends DoGetAbstractTemplatePrivilegedAction {

		public DoGetSingleTemplatePrivilegedAction(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, boolean restricted,
			Map<String, Object> helperUtilities) {

			super(errorTemplateResource, restricted, helperUtilities);

			_templateResource = templateResource;
		}

		@Override
		public Template run() {
			return doGetTemplate(
				_templateResource, errorTemplateResource, restricted,
				helperUtilities);
		}

		private final TemplateResource _templateResource;

	}

}