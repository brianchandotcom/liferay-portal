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

package com.liferay.portal.scripting.ruby;

import org.jruby.Ruby;
import org.jruby.javasupport.Java;
import org.jruby.javasupport.JavaObject;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.IAccessor;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author Alberto Montero
 */
class BeanGlobalVariable implements IAccessor {

	public BeanGlobalVariable(Ruby ruby, Object bean, Class<?> type) {
		_ruby = ruby;
		_type = type;

		_bean = JavaUtil.convertJavaToRuby(_ruby, bean, _type);

		if (_bean instanceof JavaObject) {
			_bean = Java.wrap(_ruby, _bean);
		}
	}

	@Override
	public IRubyObject getValue() {
		return _bean;
	}

	@Override
	public IRubyObject setValue(IRubyObject bean) {
		_bean = bean;

		return bean;
	}

	private IRubyObject _bean;
	private final Ruby _ruby;
	private final Class<?> _type;

}