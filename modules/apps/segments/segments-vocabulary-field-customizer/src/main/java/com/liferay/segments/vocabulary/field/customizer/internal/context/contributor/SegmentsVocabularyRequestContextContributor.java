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

package com.liferay.segments.vocabulary.field.customizer.internal.context.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyRequestContextContributorConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyRequestContextContributorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "request.context.contributor.type=collection",
	service = {
		RequestContextContributor.class,
		SegmentsVocabularyRequestContextContributor.class
	}
)
public class SegmentsVocabularyRequestContextContributor
	implements RequestContextContributor {

	@Override
	public void contribute(
		Context context, HttpServletRequest httpServletRequest) {

		if (_type.equals("cookie")) {
			Stream.of(
				httpServletRequest.getCookies()
			).filter(
				cookie -> _name.equals(cookie.getName())
			).findFirst(
			).ifPresent(
				cookie -> context.put(_name, cookie.getValue())
			);
		}
		else if (_type.equals("header")) {
			List<String> list = Collections.list(
				httpServletRequest.getHeaders(_name));

			context.put(_name, StringUtil.merge(list, StringPool.COMMA));
		}
		else if (_type.equals("parameter")) {
			context.put(
				_name,
				StringUtil.merge(
					httpServletRequest.getParameterValues(_name),
					StringPool.COMMA));
		}
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SegmentsVocabularyRequestContextContributorConfiguration
			segmentsVocabularyRequestContextContributorConfiguration =
				ConfigurableUtil.createConfigurable(
					SegmentsVocabularyRequestContextContributorConfiguration.
						class,
					properties);

		_label =
			segmentsVocabularyRequestContextContributorConfiguration.label();
		_name = segmentsVocabularyRequestContextContributorConfiguration.name();
		_type = segmentsVocabularyRequestContextContributorConfiguration.type();
	}

	private volatile String _label;
	private volatile String _name;
	private volatile String _type;

}