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
 * The extended model interface for the SocialActivityLimit service. Represents a row in the &quot;SocialActivityLimit&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.social.model.impl.SocialActivityLimitImpl"
)
@ProviderType
public interface SocialActivityLimit
	extends PersistedModel, SocialActivityLimitModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.social.model.impl.SocialActivityLimitImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialActivityLimit, Long>
		ACTIVITY_LIMIT_ID_ACCESSOR = new Accessor<SocialActivityLimit, Long>() {

			@Override
			public Long get(SocialActivityLimit socialActivityLimit) {
				return socialActivityLimit.getActivityLimitId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SocialActivityLimit> getTypeClass() {
				return SocialActivityLimit.class;
			}

		};

	public int getCount();

	public int getCount(int limitPeriod);

	public void setCount(int limitPeriod, int count);

}