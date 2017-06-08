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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.chemistry.opencmis.client.api.Session;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = Object.class)
public class CMISSessionCleanupAction {

	@Deactivate
	protected void deactivate() {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return;
		}

		String prefix = Session.class.getName();

		Enumeration<String> attributeNames = httpSession.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			if (attributeName.startsWith(prefix)) {
				httpSession.removeAttribute(attributeName);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCMISAtomPubRepositoryDefiner(
		CMISAtomPubRepositoryDefiner cmisAtomPubRepositoryDefiner) {
	}

	@Reference(unbind = "-")
	protected void setCMISWebServicesRepositoryDefiner(
		CMISWebServicesRepositoryDefiner cmisWebServicesRepositoryDefiner) {
	}

}