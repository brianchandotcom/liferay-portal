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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Raymond Augé
 */
public class PACLVelocityTemplate extends VelocityTemplate {

	public PACLVelocityTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, VelocityContext velocityContext,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper, PACLPolicy paclPolicy) {

		super(
			templateResource, errorTemplateResource, velocityContext,
			velocityEngine, templateContextHelper);

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