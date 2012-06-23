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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.template.TemplateContextHelper;

import freemarker.template.Configuration;

import java.io.Writer;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class PACLFreeMarkerTemplate extends FreeMarkerTemplate {

	public PACLFreeMarkerTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		Configuration configuration,
		TemplateContextHelper templateContextHelper, PACLPolicy paclPolicy) {

		super(
			templateResource, errorTemplateResource, context, configuration,
			templateContextHelper);

		_paclPolicy = paclPolicy;
	}

	@Override
	public boolean processTemplate(Writer writer) throws TemplateException {
		PACLPolicy initialPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			PortalSecurityManagerThreadLocal.setPACLPolicy(_paclPolicy);

			return super.processTemplate(writer);
		}
		finally {
			PortalSecurityManagerThreadLocal.setPACLPolicy(initialPolicy);
		}
	}

	private PACLPolicy _paclPolicy;

}