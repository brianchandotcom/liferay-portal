/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class Action implements LifecycleAction {

	@Override
	public final void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		run(lifecycleEvent.getRequest(), lifecycleEvent.getResponse());
	}

	public abstract void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException;

	public void run(RenderRequest renderRequest, RenderResponse renderResponse)
		throws ActionException {

		try {
			run(
				PortalUtil.getHttpServletRequest(renderRequest),
				PortalUtil.getHttpServletResponse(renderResponse));
		}
		catch (ActionException actionException) {
			throw actionException;
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

}