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

package com.liferay.portal.search.elasticsearch.shield.internal;

import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true, property = "operation.mode=EMBEDDED",
	service = SettingsContributor.class
)
public class ShieldEmbeddedSettingsContributor extends BaseSettingsContributor {

	public ShieldEmbeddedSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ClientSettingsHelper clientSettingsHelper) {
		clientSettingsHelper.put("shield.enabled", "false");
	}

}