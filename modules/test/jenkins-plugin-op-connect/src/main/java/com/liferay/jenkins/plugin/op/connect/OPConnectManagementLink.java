/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.op.connect;

import hudson.Extension;

import hudson.model.ManagementLink;

import hudson.security.Permission;

import jakarta.servlet.ServletException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import jenkins.model.Jenkins;

import org.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * @author Michael Hashimoto
 */
@Extension
public class OPConnectManagementLink extends ManagementLink {

	public OPConnectManagementLink() {
		opConnectDescriptor = new OPConnectDescriptor();

		opConnectDescriptor.load();
	}

	public void doOpConnectConfiguration(
			StaplerRequest staplerRequest, StaplerResponse staplerResponse)
		throws IOException, ServletException {

		JSONObject jsonObject = new JSONObject(
			staplerRequest.getParameter("json"));

		String accessToken = jsonObject.getString("accessToken");
		String connectURL = jsonObject.getString("connectURL");

		opConnectDescriptor.setAccessToken(accessToken);
		opConnectDescriptor.setConnectURL(connectURL);

		List<String> vaultNames = new ArrayList<>();

		for (String key : jsonObject.keySet()) {
			if (key.startsWith(_VAULT_NAME_PREFIX) &&
				jsonObject.optBoolean(key)) {

				vaultNames.add(key.substring(_VAULT_NAME_PREFIX.length()));
			}
		}

		if (!jsonObject.optBoolean("vaultsRendered")) {
			try {
				OPConnectClient opConnectClient = new OPConnectClient(
					accessToken, connectURL);

				for (OPConnectClient.Vault vault :
						opConnectClient.getVaults()) {

					vaultNames.add(vault.getName());
				}
			}
			catch (IOException ioException) {
			}
		}

		opConnectDescriptor.setVaultNames(vaultNames);

		List<String> ignoredValues = new ArrayList<>();

		String ignoredValuesString = jsonObject.optString("ignoredValues");

		for (String ignoredValue : ignoredValuesString.split("\\r?\\n")) {
			ignoredValue = ignoredValue.trim();

			if (!ignoredValue.isEmpty()) {
				ignoredValues.add(ignoredValue);
			}
		}

		opConnectDescriptor.setIgnoredValues(ignoredValues);

		opConnectDescriptor.setRefreshIntervalMinutes(
			jsonObject.optLong("refreshIntervalMinutes", 60));

		opConnectDescriptor.save();

		OPConnectUtil.setOPConnectDescriptor(opConnectDescriptor);

		opConnectDescriptor.refreshSecretValues();

		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if (jenkins != null) {
			staplerResponse.sendRedirect(jenkins.getRootUrl() + getUrlName());
		}
	}

	@Override
	public Category getCategory() {
		return Category.SECURITY;
	}

	@Override
	public String getDescription() {
		return "Connect to a 1Password Connect server and hide the values of " +
			"a chosen vault in build logs";
	}

	@Override
	public String getDisplayName() {
		return "1Password Connect";
	}

	@Override
	public String getIconFileName() {
		return "clipboard.png";
	}

	@Override
	public Permission getRequiredPermission() {
		return Jenkins.ADMINISTER;
	}

	@Override
	public String getUrlName() {
		return "op-connect-configuration";
	}

	public OPConnectDescriptor opConnectDescriptor;

	private static final String _VAULT_NAME_PREFIX = "vault:";

}