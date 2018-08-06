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

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Ligas
 */
public abstract class BaseMultiTemplateManager extends BaseTemplateManager {

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources, boolean restricted) {

		return getTemplate(templateResources, null, restricted);
	}

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, boolean restricted) {

		return doGetTemplate(
			templateResources, errorTemplateResource, restricted,
			getHelperUtilities(restricted));
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return getTemplate(
			Collections.singletonList(templateResource), null, restricted);
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		return getTemplate(
			Collections.singletonList(templateResource), errorTemplateResource,
			restricted);
	}

	protected abstract Template doGetTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected Template doGetTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged) {

		return doGetTemplate(
			templateResources, errorTemplateResource, restricted,
			helperUtilities);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected class DoGetMultiTemplatePrivilegedAction
		extends DoGetAbstractTemplatePrivilegedAction {

		public DoGetMultiTemplatePrivilegedAction(
			List<TemplateResource> templateResources,
			TemplateResource errorTemplateResource, boolean restricted,
			Map<String, Object> helperUtilities) {

			super(errorTemplateResource, restricted, helperUtilities);

			_templateResources = templateResources;
		}

		@Override
		public Template run() {
			return doGetTemplate(
				_templateResources, errorTemplateResource, restricted,
				helperUtilities);
		}

		private final List<TemplateResource> _templateResources;

	}

}