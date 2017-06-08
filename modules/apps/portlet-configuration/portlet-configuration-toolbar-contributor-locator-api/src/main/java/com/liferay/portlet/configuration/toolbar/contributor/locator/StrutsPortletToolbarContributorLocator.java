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

package com.liferay.portlet.configuration.toolbar.contributor.locator;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides an implementation of {@link PortletToolbarContributorLocator} for
 * portlets using Struts as MVC pattern, allowing them to have a different
 * {@link
 * com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor}
 * for different struts actions.
 *
 * <p>
 * PortletToolbarContributor implementations must be registered in the OSGI
 * Registry using the following properties:
 * </p>
 *
 * <ul>
 * <li>
 * &quot;javax.portlet.name&quot; the ID of the portlet whose portlet toolbar to
 * extend.
 * </li>
 * <li>
 * &quot;struts.action&quot; this property is optional. If this property is not
 * present, the portlet toolbar is always extended. If it contains a value
 * (e.g., <code>/blogs/view_entry</code>) the portlet toolbar is extended only
 * for that specific struts action. If the value is &quot;-&quot; the portlet
 * toolbar is extended when there is no <code>strutsAction</code> specified in
 * the request (typically when rendering the first view of the portlet).
 * </li>
 * </ul>
 *
 * <p>
 * A single PortletToolbarContributor implementation can be used for different
 * portlets and struts actions by including multiple times with the same
 * properties.
 * </p>
 *
 * @author Sergio González
 */
@Component(immediate = true, service = PortletToolbarContributorLocator.class)
public class StrutsPortletToolbarContributorLocator
	extends BasePortletToolbarContributorLocator {

	@Activate
	@Override
	protected void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	protected String getParameterName() {
		return "struts_action";
	}

	@Override
	protected String getPropertyName() {
		return "struts.action";
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}