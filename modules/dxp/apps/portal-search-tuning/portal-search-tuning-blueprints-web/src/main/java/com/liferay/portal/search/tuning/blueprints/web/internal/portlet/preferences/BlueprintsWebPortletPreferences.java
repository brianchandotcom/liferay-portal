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

package com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface BlueprintsWebPortletPreferences {

	public long getBlueprintId();

	public Optional<Long> getBlueprintIdOptional();

	public int getDidYouMeanHitsThreshold();

	public int getKeywordIndexingHitsThreshold();

	public int getMaxDidYouMeanSuggestions();

	public int getMaxTypeaheadSuggestions();

	public String getTitleTypeaheadEntryClassNames();

	public boolean isDidYouMeanEnabled();

	public boolean isKeywordIndexingEnabled();

	public boolean isMisspellingsEnabled();

	public boolean isTypeaheadEnabled();

}