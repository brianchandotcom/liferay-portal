/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;

/**
 * @author André de Oliveira
 */
public class DestinationBuilderImpl implements DestinationBuilder {

	public DestinationBuilderImpl(String urlString, Http http) {
		_http = http;

		_urlString = StringUtil.replace(
			URLCodec.decodeURL(urlString), CharPool.PLUS, CharPool.SPACE);
	}

	public String build() {
		return _urlString;
	}

	@Override
	public DestinationBuilder replace(String oldSub, String newSub) {
		if (Validator.isNotNull(oldSub) && Validator.isNotNull(newSub)) {
			_urlString = StringUtil.replace(_urlString, oldSub, newSub);
		}

		return this;
	}

	@Override
	public DestinationBuilder replaceParameter(
		String parameter, String newValue) {

		_urlString = _http.setParameter(_urlString, parameter, newValue);

		return this;
	}

	private final Http _http;
	private String _urlString;

}