/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RatingsStats service. Represents a row in the &quot;RatingsStats&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.ratings.model.impl.RatingsStatsImpl"
)
@ProviderType
public interface RatingsStats extends PersistedModel, RatingsStatsModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.ratings.model.impl.RatingsStatsImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RatingsStats, Long> STATS_ID_ACCESSOR =
		new Accessor<RatingsStats, Long>() {

			@Override
			public Long get(RatingsStats ratingsStats) {
				return ratingsStats.getStatsId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RatingsStats> getTypeClass() {
				return RatingsStats.class;
			}

		};

}