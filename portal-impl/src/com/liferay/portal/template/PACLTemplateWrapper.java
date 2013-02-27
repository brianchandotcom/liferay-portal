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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 */
public class PACLTemplateWrapper implements Template {

	public static Template getTemplate(Template template) {
		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();
		ClassLoader portalClassLoder = ClassLoaderUtil.getPortalClassLoader();

		if (contextClassLoader == portalClassLoder) {
			return template;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			contextClassLoader);

		return new PACLTemplateWrapper(template, paclPolicy);
	}

	public PACLTemplateWrapper(Template template, PACLPolicy policy) {
		if (template == null) {
			throw new IllegalArgumentException("Template is null");
		}

		_template = template;
		_paclPolicy = policy;
	}

	public Object get(String key) {
		return _template.get(key);
	}

	public String[] getKeys() {
		return _template.getKeys();
	}

	public void prepare(HttpServletRequest request) {
		_template.prepare(request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			PortalSecurityManagerThreadLocal.setPACLPolicy(_paclPolicy);

			return _template.processTemplate(writer);
		}
		finally {
			PortalSecurityManagerThreadLocal.setPACLPolicy(paclPolicy);
		}
	}

	public void put(String key, Object value) {
		_template.put(key, value);
	}

	private PACLPolicy _paclPolicy;
	private Template _template;

}