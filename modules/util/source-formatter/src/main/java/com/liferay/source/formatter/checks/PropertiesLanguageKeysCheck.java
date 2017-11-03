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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Peter Shin
 */
public class PropertiesLanguageKeysCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/content/Language.properties")) {
			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String[] array = line.split("=", 2);

				if (array.length < 2) {
					continue;
				}

				String key = array[0];

				if (ArrayUtil.contains(_LEGACY_LANGUAGE_KEYS, key)) {
					continue;
				}

				String value = array[1];

				if (value.matches("(?s).*<a\\b[^>]*>.*?</a>.*")) {
					addMessage(
						fileName, "Remove HTML markup for '" + key + "'",
						"language_keys.markdown",
						getLineCount(content, content.indexOf(line)));
				}
			}
		}

		return content;
	}

	private static final String[] _LEGACY_LANGUAGE_KEYS = {
		"a-connection-between-nodes-in-the-cluster-x-is-broken",
		"a-user-requested-membership-on-project-x", "application-adapter-help",
		"are-you-having-problems", "by-x-x",
		"check-your-email-or-configure-email-accounts",
		"click-here-to-save-it-now",
		"customer-must-have-purchased-the-base-subscription-from-liferay-or-a-business-partner-subject-to-the-liferay-enterprise-services-agreement-and-subscription-services-appendix-that-include-terms-for-both-instance-pricing-and-lcs-the-agreement-available-on-the-liferay-legal-page",
		"do-not-notify-me-when-this-article-is-updated",
		"do-you-find-these-metrics-useful",
		"each-elastic-deployment-instance-is-subject-to-terms-of-the-eula-and-not-the-eula-for-the-base-subscription",
		"fix-packs-for-clustered-environments-can-be-downloaded-from-the-environment-page",
		"for-additional-options-please-visit-your-personal-liferay-projects-page",
		"get-url", "get-url-or-webdav-url",
		"high-page-load-time-may-cause-a-poor-user-experience",
		"i-agree-to-elastic-deployment-terms-and-conditions",
		"if-you-do-not-have-an-active-enterprise-subscription-please-contact-your-account-executive-or-x",
		"if-you-have-a-liferay-enterprise-subscription-and-you-have-questions-or-issues-please-open-a-ticket-in-lesa-under-the-liferay-connected-services-component",
		"if-you-want-to-see-your-latest-page-metrics-you-need-to-install-the-latest-liferay-connected-services-client-and-reconnect-your-portal",
		"liferay-connected-services-is-a-set-of-online-tools-and-services-that-lets-you-manage-and-monitor-your-liferay-installations",
		"liferay-will-issue-an-invoice-to-customer-after-each-calendar-quarter-that-customer-deploys-installs-uses-or-executes-elastic-deployment-instances-and-customer-will-pay-such-invoices-in-accordance-with-the-agreement",
		"monitoring-is-unavailable-on-the-server-x", "need-help",
		"new-fix-pack-is-available-to-install-on-x",
		"new-liferay-connected-services-client-is-available-to-install-on-x",
		"new-patching-tool-is-available-to-install-on-x",
		"new-project-x-is-available", "notify-me-when-this-article-is-updated",
		"please-download-and-install-the-latest-version-of-liferay-connected-services-client",
		"please-see-user-documentation-for-more-details", "recaptcha-help",
		"set-up-the-communication-among-the-portlets-that-use-public-render-parameters",
		"the-page-will-be-refreshed-when-you-close-this-dialog.alternatively-you-can-hide-this-dialog-x",
		"the-patching-tool-is-unavailable-on-the-server-x",
		"the-server-x-unexpectedly-shut-down",
		"the-server-x-was-manually-shut-down", "the-time-zone-is-x",
		"this-organization-is-already-assigned-to-password-policy-x",
		"this-service-is-disabled",
		"this-user-is-already-assigned-to-password-policy-x",
		"to-add-a-new-member-to-this-project-please-open-a-ticket-in-lesa-under-the-liferay-connected-services-or-the-project-administration-component",
		"to-enable-or-disable-services-you-need-to-regenerate-the-token",
		"uploaded-by-x-x", "use-my-account-to-change-regular-account-settings",
		"webdav-help", "webdav-windows-help", "x-added-a-comment",
		"x-joined-the-project-x", "x-was-added-to-the-project-x",
		"xuggler-help", "you-can-also-forcibly-disable-remote-staging",
		"you-have-to-be-signed-in-to-register-for-this-meetup",
		"your-request-for-membership-on-the-project-x-was-accepted",
		"your-server-needs-to-have-liferay-connected-services-client-x-or-a-later-version-installed"
	};

}