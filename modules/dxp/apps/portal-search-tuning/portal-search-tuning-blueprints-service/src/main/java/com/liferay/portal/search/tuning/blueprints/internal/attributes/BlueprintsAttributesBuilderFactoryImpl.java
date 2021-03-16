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

package com.liferay.portal.search.tuning.blueprints.internal.attributes;

import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributesBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintsAttributesBuilderFactory.class)
public class BlueprintsAttributesBuilderFactoryImpl
	implements BlueprintsAttributesBuilderFactory {

	@Override
	public BlueprintsAttributesBuilder builder() {
		return new BlueprintsAttributesBuilderImpl();
	}

	@Override
	public BlueprintsAttributesBuilder builder(
		BlueprintsAttributes blueprintsAttributes) {

		return new BlueprintsAttributesBuilderImpl(blueprintsAttributes);
	}

}