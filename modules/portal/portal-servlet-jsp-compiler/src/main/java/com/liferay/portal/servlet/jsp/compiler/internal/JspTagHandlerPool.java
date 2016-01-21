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

package com.liferay.portal.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.jasper.Constants;
import org.apache.jasper.runtime.TagHandlerPool;

import org.glassfish.jsp.api.ResourceInjector;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 *
 * @see com.liferay.support.tomcat.jasper.runtime.TagHandlerPool
 */
public class JspTagHandlerPool extends TagHandlerPool {

	@Override
	public <T extends JspTag> JspTag get(Class<T> jspTagClass)
		throws JspException {

		JspTag jspTag = _jspTags.poll();

		if (jspTag == null) {
			try {
				if (_resourceInjector == null) {
					jspTag = jspTagClass.newInstance();
				}
				else {
					jspTag = _resourceInjector.createTagHandlerInstance(
						jspTagClass);
				}
			}
			catch (Exception e) {
				throw new JspException(e);
			}
		}
		else {
			_counter.getAndDecrement();
		}

		return jspTag;
	}

	@Override
	public void release() {
		JspTag jspTag = null;

		while ((jspTag = _jspTags.poll()) != null) {
			if (jspTag instanceof Tag) {
				Tag tag = (Tag)jspTag;

				tag.release();
			}

			if (_resourceInjector != null) {
				_resourceInjector.preDestroy(jspTag);
			}
		}
	}

	@Override
	public void reuse(JspTag jspTag) {
		if (_counter.get() < _maxSize) {
			_counter.getAndIncrement();

			_jspTags.offer(jspTag);
		}
		else if (jspTag instanceof Tag) {
			Tag tag = (Tag)jspTag;

			tag.release();

			if (_resourceInjector != null) {
				_resourceInjector.preDestroy(jspTag);
			}
		}
	}

	@Override
	protected void init(ServletConfig config) {
		_maxSize = GetterUtil.getInteger(
			getOption(config, OPTION_MAXSIZE, null), Constants.MAX_POOL_SIZE);

		ServletContext servletContext = config.getServletContext();

		_resourceInjector = (ResourceInjector)servletContext.getAttribute(
			Constants.JSP_RESOURCE_INJECTOR_CONTEXT_ATTRIBUTE);
	}

	private final AtomicInteger _counter = new AtomicInteger();
	private final Queue<JspTag> _jspTags = new ConcurrentLinkedQueue<>();
	private int _maxSize;
	private ResourceInjector _resourceInjector;

}