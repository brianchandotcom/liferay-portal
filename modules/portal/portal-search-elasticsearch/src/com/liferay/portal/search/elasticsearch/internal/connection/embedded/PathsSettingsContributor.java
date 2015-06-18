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

package com.liferay.portal.search.elasticsearch.internal.connection.embedded;

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = SettingsContributor.class
)
public class PathsSettingsContributor extends BaseSettingsContributor {

	public PathsSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		builder.put(
			"path.data",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/indices");
		builder.put("path.logs", _props.get(PropsKeys.LIFERAY_HOME) + "/logs");
		builder.put(
			"path.plugins",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/plugins");
		builder.put(
			"path.repo",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/repo");
		builder.put(
			"path.work", SystemProperties.get(SystemProperties.TMP_DIR));
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private Props _props;

}