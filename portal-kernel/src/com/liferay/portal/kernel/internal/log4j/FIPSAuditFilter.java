/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.log4j;

import com.liferay.petra.string.StringBundler;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;

/**
 * @author Jorge García Jiménez
 */
@Plugin(
	category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE,
	name = FIPSAuditFilter.PLUGIN_NAME, printObject = true
)
public final class FIPSAuditFilter extends AbstractFilter {

	public static final String PLUGIN_NAME = "FIPSAuditFilter";

	@PluginBuilderFactory
	public static Builder newBuilder() {
		return new Builder();
	}

	@Override
	public Result filter(LogEvent logEvent) {
		Marker marker = logEvent.getMarker();

		if ((marker == null) ||
			!marker.isInstanceOf(FIPSLog4jUtil.MARKER_NAME)) {

			_logger.error(
				StringBundler.concat(
					"Unable to write the log event from the logger \"",
					logEvent.getLoggerName(),
					"\" to the FIPS audit log because it carries no \"",
					FIPSLog4jUtil.MARKER_NAME, "\" marker"));

			return Result.DENY;
		}

		if (!_hasRecord(logEvent.getMessage())) {
			_logger.error(
				StringBundler.concat(
					"Unable to write the log event from the logger \"",
					logEvent.getLoggerName(),
					"\" to the FIPS audit log because its message carries no ",
					"FIPS audit record"));

			return Result.DENY;
		}

		return Result.ACCEPT;
	}

	public static class Builder
		implements org.apache.logging.log4j.core.util.Builder<FIPSAuditFilter> {

		@Override
		public FIPSAuditFilter build() {
			return new FIPSAuditFilter();
		}

	}

	private FIPSAuditFilter() {
		super(Result.ACCEPT, Result.DENY);
	}

	private boolean _hasRecord(Message message) {
		if (!(message instanceof ObjectMessage)) {
			return false;
		}

		ObjectMessage objectMessage = (ObjectMessage)message;

		if (objectMessage.getParameter() instanceof Map) {
			return true;
		}

		return false;
	}

	private static final Logger _logger = LogManager.getLogger(
		FIPSAuditFilter.class);

}