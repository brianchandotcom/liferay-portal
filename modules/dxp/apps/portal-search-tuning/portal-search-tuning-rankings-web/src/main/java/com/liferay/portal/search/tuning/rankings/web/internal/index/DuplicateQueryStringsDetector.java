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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import java.util.Collection;

/**
 * @author André de Oliveira
 */
public interface DuplicateQueryStringsDetector {

	public Criteria.Builder builder();

	public boolean detect(Criteria criteria);

	public interface Criteria {

		public String getIndex();

		public Collection<String> getQueryStrings();

		public String getUnlessRankingId();

		public interface Builder {

			public Criteria build();

			public Builder index(String index);

			public Builder queryStrings(Collection<String> queryStrings);

			public Builder unlessRankingId(String unlessRankingId);

		}

	}

}