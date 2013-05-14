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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePollerProcessor implements PollerProcessor {

	@Override
	public void receive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws PollerException {

		try {
			doReceive(pollerRequest, pollerResponse);
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	@Override
	public void send(PollerRequest pollerRequest) throws PollerException {
		try {
			doSend(pollerRequest);
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	protected abstract void doReceive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception;

	protected abstract void doSend(PollerRequest pollerRequest)
		throws Exception;

	protected boolean getBoolean(PollerRequest pollerRequest, String name) {
		return getBoolean(pollerRequest, name, GetterUtil.DEFAULT_BOOLEAN);
	}

	protected boolean getBoolean(
		PollerRequest pollerRequest, String name, boolean defaultValue) {

		return GetterUtil.getBoolean(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected double getDouble(PollerRequest pollerRequest, String name) {
		return getDouble(pollerRequest, name, -1);
	}

	protected double getDouble(
		PollerRequest pollerRequest, String name, double defaultValue) {

		return GetterUtil.getDouble(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected int getInteger(PollerRequest pollerRequest, String name) {
		return getInteger(pollerRequest, name, -1);
	}

	protected int getInteger(
		PollerRequest pollerRequest, String name, int defaultValue) {

		return GetterUtil.getInteger(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected long getLong(PollerRequest pollerRequest, String name) {
		return getLong(pollerRequest, name, -1);
	}

	protected long getLong(
		PollerRequest pollerRequest, String name, long defaultValue) {

		return GetterUtil.getLong(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected String getString(PollerRequest pollerRequest, String name) {
		return getString(pollerRequest, name, null);
	}

	protected String getString(
		PollerRequest pollerRequest, String name, String defaultValue) {

		return GetterUtil.getString(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

}