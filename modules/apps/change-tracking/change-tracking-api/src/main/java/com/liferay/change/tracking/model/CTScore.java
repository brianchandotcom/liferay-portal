/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CTScore service. Represents a row in the &quot;CTScore&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CTScoreModel
 * @generated
 */
@ImplementationClassName("com.liferay.change.tracking.model.impl.CTScoreImpl")
@ProviderType
public interface CTScore extends CTScoreModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.model.impl.CTScoreImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CTScore, Long> CT_SCORE_ID_ACCESSOR =
		new Accessor<CTScore, Long>() {

			@Override
			public Long get(CTScore ctScore) {
				return ctScore.getCtScoreId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CTScore> getTypeClass() {
				return CTScore.class;
			}

		};

	public String getSizeClassification();

}
// LIFERAY-SERVICE-BUILDER-HASH:1088207382