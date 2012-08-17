/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.aop;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class MethodInterceptorsBag {

	public MethodInterceptorsBag(
		List<MethodInterceptor> classLevelMethodInterceptors,
		List<MethodInterceptor> mergedMethodInterceptors) {

		_classLevelMethodInterceptors = classLevelMethodInterceptors;
		_mergedMethodInterceptors = mergedMethodInterceptors;
	}

	public List<MethodInterceptor> getClassLevelMethodInterceptors() {
		return _classLevelMethodInterceptors;
	}

	public List<MethodInterceptor> getMergedMethodInterceptors() {
		return _mergedMethodInterceptors;
	}

	public void setClassLevelMethodInterceptors(
		List<MethodInterceptor> classLevelMethodInterceptors) {

		_classLevelMethodInterceptors = classLevelMethodInterceptors;
	}

	public void setMergedMethodInterceptors(
		List<MethodInterceptor> mergedMethodInterceptors) {

		_mergedMethodInterceptors = mergedMethodInterceptors;
	}

	private List<MethodInterceptor> _classLevelMethodInterceptors;
	private List<MethodInterceptor> _mergedMethodInterceptors;

}