/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FaroChannel service. Represents a row in the &quot;OSBFaro_FaroChannel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthew Kong
 * @see FaroChannelModel
 * @generated
 */
@ImplementationClassName("com.liferay.osb.faro.model.impl.FaroChannelImpl")
@ProviderType
public interface FaroChannel extends FaroChannelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.model.impl.FaroChannelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FaroChannel, Long> FARO_CHANNEL_ID_ACCESSOR =
		new Accessor<FaroChannel, Long>() {

			@Override
			public Long get(FaroChannel faroChannel) {
				return faroChannel.getFaroChannelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FaroChannel> getTypeClass() {
				return FaroChannel.class;
			}

		};

}