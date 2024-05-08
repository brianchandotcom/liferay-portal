/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.helper;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.remote.json.web.service.JSONWebServiceActionMapping;
import com.liferay.portal.remote.json.web.service.JSONWebServiceActionsManager;

import java.util.List;
import java.util.Set;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerUtil {

	public static Set<String> getContextNames() {
		JSONWebServiceActionsManager jsonWebServiceActionsManager =
			_jsonWebServiceActionsManagerSnapshot.get();

		return jsonWebServiceActionsManager.getContextNames();
	}

	public static JSONWebServiceActionMapping getJSONWebServiceActionMapping(
		String signature) {

		JSONWebServiceActionsManager jsonWebServiceActionsManager =
			_jsonWebServiceActionsManagerSnapshot.get();

		return jsonWebServiceActionsManager.getJSONWebServiceActionMapping(
			signature);
	}

	public static List<JSONWebServiceActionMapping>
		getJSONWebServiceActionMappings(String contextName) {

		JSONWebServiceActionsManager jsonWebServiceActionsManager =
			_jsonWebServiceActionsManagerSnapshot.get();

		return jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
			contextName);
	}

	private static final Snapshot<JSONWebServiceActionsManager>
		_jsonWebServiceActionsManagerSnapshot = new Snapshot<>(
			JSONWebServiceActionsManagerUtil.class,
			JSONWebServiceActionsManager.class);

}