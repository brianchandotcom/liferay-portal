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

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.audit.router.LogMessageFormatter;
import com.liferay.portal.security.audit.router.configuration.CSVLogMessageFormatterConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Mika Koivisto
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.router.configuration.CSVLogMessageFormatterConfiguration",
	immediate = true, property = "format=CSV",
	service = LogMessageFormatter.class
)
public class CSVLogMessageFormatter implements LogMessageFormatter {

	@Override
	public String format(AuditMessage auditMessage) {
		StringBundler sb = new StringBundler(_columns.length * 4 - 1);

		JSONObject jsonObject = auditMessage.toJSONObject();

		for (int i = 0; i < _columns.length; i++) {
			sb.append(StringPool.QUOTE);
			sb.append(jsonObject.getString(_columns[i]));
			sb.append(StringPool.QUOTE);

			if ((i + 1) < _columns.length) {
				sb.append(StringPool.COMMA);
			}
		}

		return sb.toString();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CSVLogMessageFormatterConfiguration
			csvLogMessageFormatterConfiguration =
				ConfigurableUtil.createConfigurable(
					CSVLogMessageFormatterConfiguration.class, properties);

		_columns = csvLogMessageFormatterConfiguration.columns();
	}

	private volatile String[] _columns;

}