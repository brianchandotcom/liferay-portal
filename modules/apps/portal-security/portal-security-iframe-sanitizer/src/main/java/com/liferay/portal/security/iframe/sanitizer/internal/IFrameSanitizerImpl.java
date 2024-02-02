/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.iframe.sanitizer.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.iframe.sanitizer.configuration.IFrameConfiguration;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.portal.security.iframe.sanitizer.configuration.IFrameConfiguration",
	service = Sanitizer.class
)
public class IFrameSanitizerImpl implements Sanitizer {

	@Override
	public String sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, String content,
		Map<String, Object> options) {

		if (!_iFrameConfiguration.enabled() || Validator.isNull(content) ||
			Validator.isNull(contentType) ||
			!contentType.equals(ContentTypes.TEXT_HTML)) {

			return content;
		}

		Document document = _getDocument(content);

		for (Element iframe : document.getElementsByTag("iframe")) {
			if (_iFrameConfiguration.removeIFrameTags()) {
				iframe.remove();
			}
			else {
				iframe.attr(
					"sandbox",
					StringUtil.merge(
						_iFrameConfiguration.sandboxAttributeValues(),
						StringPool.SPACE));
			}
		}

		Element body = document.body();

		StringBundler sb = new StringBundler(body.childNodeSize());

		for (Node childNode : body.childNodes()) {
			sb.append(childNode.toString());
		}

		return sb.toString();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_iFrameConfiguration = ConfigurableUtil.createConfigurable(
			IFrameConfiguration.class, properties);
	}

	private Document _getDocument(String content) {
		Document document = Jsoup.parse(content);

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		return document;
	}

	private volatile IFrameConfiguration _iFrameConfiguration;

}