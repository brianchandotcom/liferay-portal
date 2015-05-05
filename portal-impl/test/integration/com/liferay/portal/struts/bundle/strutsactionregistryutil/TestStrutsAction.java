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

package com.liferay.portal.struts.bundle.strutsactionregistryutil;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.StackTraceUtil;

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 */
@Component(
		immediate = true,
		property = {
			"path=StrutsActionRegistryUtilTest1",
			"service.ranking:Integer=" + Integer.MAX_VALUE
		}
)
public class TestStrutsAction implements StrutsAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse
		response) {

		return null;
	}

	@Override
	public String execute(StrutsAction originalStrutsAction, HttpServletRequest
		request, HttpServletResponse response) {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Reference(target = "(test=AtomicState)")
	protected void getAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}