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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.UUID;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Geyson Silva
 */
@Component(service = TokenManager.class)
public class TokenManager {

	public void clearToken(String token) {
		_tokens.removeValue(token);
	}

	public void clearToken(String dataSourceId, long faroProjectId) {
		_tokens.remove(_getKey(dataSourceId, faroProjectId));
	}

	public String getDataSourceId(String token) {
		String key = _tokens.getKey(token);

		if (key == null) {
			return null;
		}

		String[] parts = StringUtil.split(key, StringPool.POUND);

		String dataSourceId = parts[0];

		if (Validator.isNull(dataSourceId)) {
			return null;
		}

		return dataSourceId;
	}

	public Long getFaroProjectId(String token) {
		String key = _tokens.getKey(token);

		if (key == null) {
			return null;
		}

		String[] parts = StringUtil.split(
			_tokens.getKey(token), StringPool.POUND);

		return GetterUtil.getLong(parts[1]);
	}

	public String getToken(String dataSourceId, long faroProjectId) {
		return _tokens.computeIfAbsent(
			_getKey(dataSourceId, faroProjectId),
			key -> {
				UUID uuid = UUID.randomUUID();

				return uuid.toString();
			});
	}

	public void setDataSourceId(String dataSourceId, String token) {
		if (!_tokens.containsValue(token)) {
			return;
		}

		Long faroProjectId = getFaroProjectId(token);

		clearToken(token);

		_tokens.put(_getKey(dataSourceId, faroProjectId), token);
	}

	private String _getKey(String dataSourceId, long faroProjectId) {
		return dataSourceId + StringPool.POUND + faroProjectId;
	}

	private static final BidiMap<String, String> _tokens =
		new DualHashBidiMap<>();

}