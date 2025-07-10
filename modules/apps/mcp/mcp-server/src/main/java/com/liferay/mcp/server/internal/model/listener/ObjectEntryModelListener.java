/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.model.listener;

import com.liferay.mcp.server.internal.servlet.MCPServerServlet;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;

import jakarta.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class ObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "MCP_PROMPT";
	}

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_restartMCPServlet();
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_restartMCPServlet();
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_restartMCPServlet();
	}

	private void _restartMCPServlet() {
		try {
			if ((_servlet != null) && (_servlet instanceof MCPServerServlet)) {
				MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

				mcpServerServlet.reset();
			}
		}
		catch (Exception exception) {
			_log.error("Unable to restart MCPServerServlet", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelListener.class);

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.internal.servlet.MCPServerServlet)"
	)
	private Servlet _servlet;

}