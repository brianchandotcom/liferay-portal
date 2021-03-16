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

package com.liferay.portal.search.tuning.blueprints.suggestions.attributes;

import java.util.Map;
import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface SuggestionsAttributes {

	public Optional<Object> getAttributeOptional(String key);

	public Map<String, Object> getAttributes();

	public Long getCompanyId();

	public Long getGroupId();

	public String getKeywords();

	public String getLanguageId();

	public Integer getSize();

	public Long getUserId();

}