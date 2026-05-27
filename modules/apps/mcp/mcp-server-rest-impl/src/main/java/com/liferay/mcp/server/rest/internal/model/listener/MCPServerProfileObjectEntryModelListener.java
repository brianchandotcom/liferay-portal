/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.model.listener;

import com.liferay.mcp.server.rest.internal.constants.MCPServerConstants;
import com.liferay.mcp.server.rest.internal.servlet.MCPServerServlet;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;

import jakarta.servlet.Servlet;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class MCPServerProfileObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return MCPServerConstants.EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE;
	}

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

		mcpServerServlet.invalidate(
			objectEntry.getCompanyId(), _getName(objectEntry));
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

		mcpServerServlet.invalidate(
			objectEntry.getCompanyId(), _getName(objectEntry));
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

		mcpServerServlet.invalidate(
			objectEntry.getCompanyId(), _getName(originalObjectEntry));
	}

	private String _getName(ObjectEntry objectEntry) {
		Map<String, Serializable> values = objectEntry.getValues();

		return (String)values.get("name");
	}

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.rest.internal.servlet.MCPServerServlet)"
	)
	private Servlet _servlet;

}