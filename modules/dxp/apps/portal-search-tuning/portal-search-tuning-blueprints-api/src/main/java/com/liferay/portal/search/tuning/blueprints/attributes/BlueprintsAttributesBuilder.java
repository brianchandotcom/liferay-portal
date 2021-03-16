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

package com.liferay.portal.search.tuning.blueprints.attributes;

import java.util.Locale;

/**
 * @author Petteri Karttunen
 */
public interface BlueprintsAttributesBuilder {

	public BlueprintsAttributesBuilder addAttribute(String key, Object value);

	public BlueprintsAttributes build();

	public BlueprintsAttributesBuilder companyId(long companyId);

	public BlueprintsAttributesBuilder keywords(String keywords);

	public BlueprintsAttributesBuilder locale(Locale locale);

	public BlueprintsAttributesBuilder userId(long userId);

}