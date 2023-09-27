/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SocialActivityAchievement service. Represents a row in the &quot;SocialActivityAchievement&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievementModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.social.model.impl.SocialActivityAchievementImpl"
)
@ProviderType
public interface SocialActivityAchievement
	extends PersistedModel, SocialActivityAchievementModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.social.model.impl.SocialActivityAchievementImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialActivityAchievement, Long>
		ACTIVITY_ACHIEVEMENT_ID_ACCESSOR =
			new Accessor<SocialActivityAchievement, Long>() {

				@Override
				public Long get(
					SocialActivityAchievement socialActivityAchievement) {

					return socialActivityAchievement.getActivityAchievementId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SocialActivityAchievement> getTypeClass() {
					return SocialActivityAchievement.class;
				}

			};

}