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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.HtmlImpl;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class AutoEscapeBeanHandlerTest {

	@Before
	public void setUp() throws Exception {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		_bean = new BeanImpl(_UNESCAPED_TEXT);
	}

	@Test
	public void testToEscapedBean() {
		Bean escapedBean = _bean.toEscapedBean();

		Assert.assertEquals(_ESCAPED_TEXT, escapedBean.getAttribute());
		Assert.assertEquals(
			_UNESCAPED_TEXT, escapedBean.getUnescapedAttribute());
	}

	private static final String _ESCAPED_TEXT = "Old Mc&#039;Donald";

	private static final String _UNESCAPED_TEXT = "Old Mc'Donald";

	private Bean _bean;

	private static class BeanImpl implements Bean {

		public BeanImpl(String attribute) {
			_attribute = attribute;
			_unescapedAttribute = attribute;
		}

		@Override
		public String getAttribute() {
			return _attribute;
		}

		@Override
		public String getUnescapedAttribute() {
			return _unescapedAttribute;
		}

		@Override
		public Bean toEscapedBean() {
			Class<?> clazz = getClass();

			return (Bean)ProxyUtil.newProxyInstance(
				clazz.getClassLoader(), new Class<?>[] {Bean.class},
				new AutoEscapeBeanHandler(this));
		}

		private final String _attribute;
		private final String _unescapedAttribute;

	}

	private interface Bean extends Serializable {

		@AutoEscape
		public String getAttribute();

		public String getUnescapedAttribute();

		public Bean toEscapedBean();

	}

}