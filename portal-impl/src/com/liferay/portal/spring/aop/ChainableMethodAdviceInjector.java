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

package com.liferay.portal.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class ChainableMethodAdviceInjector {

	public void inject() {
		if (!_injectCondition) {
			return;
		}

		_injectCondition = false;

		if (_newChainableMethodAdvice == null) {
			throw new IllegalArgumentException(
				"New Chainable method advice is null");
		}

		if (_parentChainableMethodAdvice == null) {
			throw new IllegalArgumentException(
				"Parent chainable method advice is null");
		}

		if (_childMethodInterceptor == null) {
			_newChainableMethodAdvice.nextMethodInterceptor =
				_parentChainableMethodAdvice.nextMethodInterceptor;
			_parentChainableMethodAdvice.nextMethodInterceptor =
				_newChainableMethodAdvice;

			return;
		}

		ChainableMethodAdvice parentChainableMethodAdvice =
			_parentChainableMethodAdvice;

		while ((parentChainableMethodAdvice != null) &&
			   (parentChainableMethodAdvice.nextMethodInterceptor !=
				   _childMethodInterceptor)) {

			MethodInterceptor methodInterceptor =
				parentChainableMethodAdvice.nextMethodInterceptor;

			if (!(methodInterceptor instanceof ChainableMethodAdvice)) {
				break;
			}

			parentChainableMethodAdvice =
				(ChainableMethodAdvice)methodInterceptor;
		}

		if (parentChainableMethodAdvice.nextMethodInterceptor !=
				_childMethodInterceptor) {

			throw new IllegalArgumentException(
				"Unable to find " + _childMethodInterceptor + " from " +
					_parentChainableMethodAdvice);
		}

		_newChainableMethodAdvice.nextMethodInterceptor =
			parentChainableMethodAdvice.nextMethodInterceptor;

		parentChainableMethodAdvice.nextMethodInterceptor =
			_newChainableMethodAdvice;
	}

	public void setChildMethodInterceptor(
		MethodInterceptor childMethodInterceptor) {

		_childMethodInterceptor = childMethodInterceptor;
	}

	public void setInjectCondition(boolean injectCondition) {
		_injectCondition = injectCondition;
	}

	public void setNewChainableMethodAdvice(
		ChainableMethodAdvice newChainableMethodAdvice) {

		_newChainableMethodAdvice = newChainableMethodAdvice;
	}

	public void setParentChainableMethodAdvice(
		ChainableMethodAdvice parentChainableMethodAdvice) {

		_parentChainableMethodAdvice = parentChainableMethodAdvice;
	}

	private MethodInterceptor _childMethodInterceptor;
	private boolean _injectCondition;
	private ChainableMethodAdvice _newChainableMethodAdvice;
	private ChainableMethodAdvice _parentChainableMethodAdvice;

}