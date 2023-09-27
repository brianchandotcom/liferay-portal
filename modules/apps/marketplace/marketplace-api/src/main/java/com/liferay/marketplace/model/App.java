/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the App service. Represents a row in the &quot;Marketplace_App&quot; database table, with each column mapped to a property of this class.
 *
 * @author Ryan Park
 * @see AppModel
 * @generated
 */
@ImplementationClassName("com.liferay.marketplace.model.impl.AppImpl")
@ProviderType
public interface App extends AppModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.marketplace.model.impl.AppImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<App, Long> APP_ID_ACCESSOR =
		new Accessor<App, Long>() {

			@Override
			public Long get(App app) {
				return app.getAppId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<App> getTypeClass() {
				return App.class;
			}

		};

	public String[] addContextName(String contextName);

	public String[] getContextNames();

	public String getFileDir();

	public String getFileName();

	public String getFilePath();

	public boolean isDownloaded()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isInstalled();

}