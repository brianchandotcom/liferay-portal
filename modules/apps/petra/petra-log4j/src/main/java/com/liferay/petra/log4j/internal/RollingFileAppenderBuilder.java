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

package com.liferay.petra.log4j.internal;

import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.bridge.AppenderWrapper;
import org.apache.log4j.bridge.LayoutAdapter;
import org.apache.log4j.bridge.LayoutWrapper;
import org.apache.log4j.builders.BuilderManager;
import org.apache.log4j.config.PropertiesConfiguration;
import org.apache.log4j.xml.XmlConfiguration;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DirectWriteRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import org.w3c.dom.Element;

/**
 * @author Dante Wang
 */
@Plugin(
	category = BuilderManager.CATEGORY,
	name = "org.apache.log4j.rolling.RollingFileAppender"
)
public class RollingFileAppenderBuilder
	extends org.apache.log4j.builders.appender.RollingFileAppenderBuilder {

	@Override
	public Appender parseAppender(
		Element appenderElement, XmlConfiguration xmlConfiguration) {

		RollingFileAppender.Builder builder = RollingFileAppender.newBuilder();

		builder.setName(
			appenderElement.getAttribute(XmlConfiguration.NAME_ATTR)
		).setConfiguration(
			xmlConfiguration
		);

		XmlConfiguration.forEachElement(
			appenderElement.getChildNodes(),
			currentElement -> {
				String tagName = currentElement.getTagName();

				if (Objects.equals(tagName, XmlConfiguration.LAYOUT_TAG)) {
					Layout layout = xmlConfiguration.parseLayout(
						currentElement);

					if (layout instanceof LayoutWrapper) {
						LayoutWrapper layoutWrapper = (LayoutWrapper)layout;

						builder.setLayout(layoutWrapper.getLayout());
					}
					else if (layout != null) {
						builder.setLayout(new LayoutAdapter(layout));
					}
				}
				else if (Objects.equals(tagName, "rollingPolicy")) {
					XmlConfiguration.forEachElement(
						currentElement.getElementsByTagName(
							XmlConfiguration.PARAM_TAG),
						paramElement -> {
							String paramName = paramElement.getAttribute(
								XmlConfiguration.NAME_ATTR);

							if (Objects.equals(paramName, "FileNamePattern")) {
								builder.withFilePattern(
									paramElement.getAttribute(
										XmlConfiguration.VALUE_ATTR));
							}
						});
				}
			});

		TimeBasedTriggeringPolicy.Builder timeBasedTriggeringPolicyBuilder =
			TimeBasedTriggeringPolicy.newBuilder();

		builder.withPolicy(timeBasedTriggeringPolicyBuilder.build());

		DirectWriteRolloverStrategy.Builder directWriteRolloverStrategyBuilder =
			DirectWriteRolloverStrategy.newBuilder();

		builder.withStrategy(
			directWriteRolloverStrategyBuilder.withConfig(
				xmlConfiguration
			).build());

		return new AppenderWrapper(builder.build());
	}

	@Override
	public Appender parseAppender(
		String name, String appenderPrefix, String layoutPrefix,
		String filterPrefix, Properties props,
		PropertiesConfiguration configuration) {

		throw new UnsupportedOperationException("Not implemented");
	}

}