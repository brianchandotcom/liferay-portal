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

package com.liferay.portal.security.access.control;

import com.liferay.portal.kernel.security.access.control.AccessControl;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class AccessControlAdvice
	extends AnnotationChainableMethodAdvice<AccessControlled> {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		incrementServiceDepth();

		AccessControlled accessControlled = findAnnotation(methodInvocation);

		if (accessControlled == AccessControl.NULL_ACCESS_CONTROLLED) {
			return null;
		}

		_accessControlAdvisor.accept(methodInvocation, accessControlled);

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		decrementServiceDepth();
	}

	@Override
	public AccessControlled getNullAnnotation() {
		return AccessControl.NULL_ACCESS_CONTROLLED;
	}

	public void setAccessControlAdvisor(
		AccessControlAdvisor accessControlAdvisor) {

		_accessControlAdvisor = accessControlAdvisor;
	}

	protected void decrementServiceDepth() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext == null) {
			return;
		}

		Map<String, Object> settings = accessControlContext.getSettings();

		Integer serviceDepth = (Integer)settings.get(
			AccessControlContext.Settings.SERVICE_DEPTH.toString());

		if (serviceDepth == null) {
			return;
		}

		serviceDepth--;

		settings.put(
			AccessControlContext.Settings.SERVICE_DEPTH.toString(),
			serviceDepth);
	}

	protected void incrementServiceDepth() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext == null) {
			return;
		}

		Map<String, Object> settings = accessControlContext.getSettings();

		Integer serviceDepth = (Integer)settings.get(
			AccessControlContext.Settings.SERVICE_DEPTH.toString());

		if (serviceDepth == null) {
			serviceDepth = Integer.valueOf(1);
		}
		else {
			serviceDepth++;
		}

		settings.put(
			AccessControlContext.Settings.SERVICE_DEPTH.toString(),
			serviceDepth);
	}

	private AccessControlAdvisor _accessControlAdvisor;

}