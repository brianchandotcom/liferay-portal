/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.internal.serializer.BaseFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
public abstract class BaseAPIURLFDSSerializer
	extends BaseFDSSerializer implements FDSSerializer<String> {

	public FDSAPIURLBuilder createFDSAPIURLBuilder(
		HttpServletRequest httpServletRequest, String restApplication,
		String restEndpoint, String restSchema) {

		return new FDSAPIURLBuilderImpl(
			httpServletRequest, restApplication, restEndpoint, restSchema);
	}

	@Override
	public String getKey() {
		return "apiURL";
	}

	public interface FDSAPIURLBuilder {

		public FDSAPIURLBuilder addParameter(String name, String value);

		public FDSAPIURLBuilder addQueryString(String queryString);

		public String build();

	}

	public class FDSAPIURLBuilderImpl implements FDSAPIURLBuilder {

		public FDSAPIURLBuilderImpl(
			HttpServletRequest httpServletRequest, String restApplication,
			String restEndpoint, String restSchema) {

			_httpServletRequest = httpServletRequest;
			_restApplication = restApplication;
			_restEndpoint = restEndpoint;
			_restSchema = restSchema;
		}

		@Override
		public FDSAPIURLBuilder addParameter(String name, String value) {
			if (Validator.isNotNull(name) && Validator.isNotNull(value)) {
				_queryStringItems.add(name + CharPool.EQUAL + value);
			}

			return this;
		}

		@Override
		public FDSAPIURLBuilder addQueryString(String queryString) {
			if (Validator.isNotNull(queryString)) {
				_queryStringItems.add(queryString);
			}

			return this;
		}

		@Override
		public String build() {
			StringBundler sb = new StringBundler(
				3 + (_queryStringItems.size() * 2));

			sb.append("/o");
			sb.append(
				StringUtil.replaceLast(
					_restApplication, "/v1.0", StringPool.BLANK));
			sb.append(_restEndpoint);

			_appendParameters(sb);

			return _interpolateURL(_resolveParameters(sb.toString()));
		}

		private void _appendParameters(StringBundler sb) {
			if (_queryStringItems.isEmpty()) {
				return;
			}

			sb.append(CharPool.QUESTION);

			int count = 0;

			for (String parameter : _queryStringItems) {
				sb.append(parameter);

				count++;

				if (count < _queryStringItems.size()) {
					sb.append(CharPool.AMPERSAND);
				}
			}
		}

		private String _interpolateURL(String apiURL) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			apiURL = StringUtil.replace(
				apiURL, "{siteId}",
				String.valueOf(themeDisplay.getScopeGroupId()));
			apiURL = StringUtil.replace(
				apiURL, "{scopeKey}",
				String.valueOf(themeDisplay.getScopeGroupId()));
			apiURL = StringUtil.replace(
				apiURL, "{userId}", String.valueOf(themeDisplay.getUserId()));

			if (StringUtil.contains(apiURL, "{") && _log.isWarnEnabled()) {
				_log.warn("Unsupported parameter in API URL: " + apiURL);
			}

			return apiURL;
		}

		private String _resolveParameters(String apiURL) {
			FDSAPIURLResolver fdsAPIURLResolver =
				fdsAPIURLResolverRegistry.getFDSAPIURLResolver(
					_restApplication, _restSchema);

			if (fdsAPIURLResolver == null) {
				return apiURL;
			}

			try {
				return fdsAPIURLResolver.resolve(apiURL, _httpServletRequest);
			}
			catch (PortalException portalException) {
				_log.error(portalException);

				return apiURL;
			}
		}

		private static final Log _log = LogFactoryUtil.getLog(
			FDSAPIURLBuilderImpl.class);

		private final HttpServletRequest _httpServletRequest;
		private final List<String> _queryStringItems = new LinkedList<>();
		private final String _restApplication;
		private final String _restEndpoint;
		private final String _restSchema;

	}

	@Reference
	protected FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry;

}