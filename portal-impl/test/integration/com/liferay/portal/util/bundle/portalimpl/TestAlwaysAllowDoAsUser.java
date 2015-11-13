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

package com.liferay.portal.util.bundle.portalimpl;

import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.portal.kernel.util.StackTraceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestAlwaysAllowDoAsUser implements AlwaysAllowDoAsUser {

	public static final String ACTION_NAME =
		"/TestAlwaysAllowDoAsUser/action/name";

	public static final String MVC_RENDER_COMMMAND_NAME =
		"/TestAlwaysAllowDoAsUser/mvc/render/command/name";

	public static final String PATH = "/TestAlwaysAllowDoAsUser/";

	public static final String STRUTS_ACTION =
		"/TestAlwaysAllowDoAsUser/struts/action";

	@Override
	public Collection<String> getActionNames() {
		_atomicReference.set(StackTraceUtil.getCallerKey());

		Collection<String> actionNames = new ArrayList<>();

		actionNames.add(ACTION_NAME);

		return actionNames;
	}

	@Override
	public Collection<String> getMVCRenderCommandNames() {
		_atomicReference.set(StackTraceUtil.getCallerKey());

		Collection<String> mvcRenderCommandNames = new ArrayList<>();

		mvcRenderCommandNames.add(MVC_RENDER_COMMMAND_NAME);

		return mvcRenderCommandNames;
	}

	@Override
	public Collection<String> getPaths() {
		_atomicReference.set(StackTraceUtil.getCallerKey());

		Collection<String> paths = new ArrayList<>();

		paths.add(PATH);

		return paths;
	}

	@Override
	public Collection<String> getStrutsActions() {
		_atomicReference.set(StackTraceUtil.getCallerKey());

		Collection<String> strutsActions = new ArrayList<>();

		strutsActions.add(STRUTS_ACTION);

		return strutsActions;
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}