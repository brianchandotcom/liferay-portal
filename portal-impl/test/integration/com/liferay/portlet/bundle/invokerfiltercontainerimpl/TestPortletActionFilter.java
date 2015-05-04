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

package com.liferay.portlet.bundle.invokerfiltercontainerimpl;

import com.liferay.portal.kernel.util.StackTraceUtil;

import java.util.concurrent.atomic.AtomicReference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 * @author Peter Fellwock
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name=testPortletFilter",
			"preinitialized.filter=false",
			"service.ranking:Integer=" + Integer.MAX_VALUE
		},
		service = PortletFilter.class
)
public class TestPortletActionFilter implements PortletFilter, ActionFilter {

	@Override
	public void destroy() {
		return;
	}

	@Override
	public void doFilter(
		ActionRequest arg0, ActionResponse arg1, FilterChain arg2) {

		return;
	}

	@Override
	public void init(FilterConfig arg0) throws PortletException {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Reference(target = "(test=AtomicState)")
	protected void getAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}