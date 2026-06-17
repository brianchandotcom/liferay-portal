/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.HTTPRequestNode;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Setting;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Fabian Bouché
 * @author Iliyan Peychev
 */
@Component(service = NodeValidator.class)
public class HTTPRequestNodeValidator
	extends BaseNodeValidator<HTTPRequestNode> {

	@Override
	public NodeType getNodeType() {
		return NodeType.HTTP_REQUEST;
	}

	@Override
	protected void doValidate(
			Definition definition, HTTPRequestNode httpRequestNode)
		throws KaleoDefinitionValidationException {

		String httpMethod = null;
		String url = null;

		for (Setting setting : httpRequestNode.getSettings()) {
			if (Objects.equals(setting.getName(), "httpMethod")) {
				httpMethod = setting.getValue();
			}
			else if (Objects.equals(setting.getName(), "url")) {
				url = setting.getValue();
			}
		}

		if ((httpMethod == null) || !_httpMethods.contains(httpMethod)) {
			throw new KaleoDefinitionValidationException(
				StringBundler.concat(
					"The ", httpRequestNode.getDefaultLabel(),
					" node must have a valid HTTP method"));
		}

		if (Validator.isNull(url)) {
			throw new KaleoDefinitionValidationException(
				StringBundler.concat(
					"The ", httpRequestNode.getDefaultLabel(),
					" node must have a URL"));
		}

		try {
			URL urlObject = new URL(url);

			if (!PortalRunMode.isTestMode() &&
				InetAddressUtil.isLocalInetAddress(
					InetAddressUtil.getInetAddressByName(
						urlObject.getHost()))) {

				throw new KaleoDefinitionValidationException(
					StringBundler.concat(
						"The ", httpRequestNode.getDefaultLabel(),
						" node must not have a local URL: ", url));
			}
		}
		catch (MalformedURLException | UnknownHostException exception) {
			throw new KaleoDefinitionValidationException(
				StringBundler.concat(
					"The ", httpRequestNode.getDefaultLabel(),
					" node has an invalid URL: ", url),
				exception);
		}

		if (httpRequestNode.getOutgoingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetOutgoingTransition(httpRequestNode.getDefaultLabel());
		}

		if (httpRequestNode.getOutgoingTransitionsCount() > 1) {
			throw new KaleoDefinitionValidationException.
				MustNotSetMultipleOutgoingTransitions(
					httpRequestNode.getDefaultLabel());
		}
	}

	private static final Set<String> _httpMethods = Set.of(
		"DELETE", "GET", "PATCH", "POST", "PUT");

}