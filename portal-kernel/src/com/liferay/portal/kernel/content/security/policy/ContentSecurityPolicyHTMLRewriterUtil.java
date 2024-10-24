/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.content.security.policy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Iván Zaera Avellón
 */
public class ContentSecurityPolicyHTMLRewriterUtil {

	public static ContentSecurityPolicyHTMLRewriter
		getContentSecurityPolicyHTMLRewriter() {

		return _snapshot.get();
	}

	public static String rewriteInlineEventHandlers(String html, String nonce) {
		ContentSecurityPolicyHTMLRewriter contentSecurityPolicyHTMLRewriter =
			getContentSecurityPolicyHTMLRewriter();

		if (contentSecurityPolicyHTMLRewriter == null) {
			_log.error("Content security policy HTML rewriter is null");

			return html;
		}

		return contentSecurityPolicyHTMLRewriter.rewriteInlineEventHandlers(
			html, nonce);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentSecurityPolicyHTMLRewriterUtil.class);

	private static final Snapshot<ContentSecurityPolicyHTMLRewriter> _snapshot =
		new Snapshot<>(
			ContentSecurityPolicyHTMLRewriterUtil.class,
			ContentSecurityPolicyHTMLRewriter.class);

}