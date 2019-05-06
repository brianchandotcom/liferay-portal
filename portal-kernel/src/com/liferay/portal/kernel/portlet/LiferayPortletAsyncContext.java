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

package com.liferay.portal.kernel.portlet;

import javax.portlet.PortletAsyncContext;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;

/**
 * @author     Dante Wang
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public interface LiferayPortletAsyncContext extends PortletAsyncContext {

	public void addListener(AsyncListener asyncListener);

	public void doStart();

	public boolean isCalledDispatch();

	public void removeListener(AsyncListener asyncListener);

	public void reset(AsyncContext asyncContext);

}