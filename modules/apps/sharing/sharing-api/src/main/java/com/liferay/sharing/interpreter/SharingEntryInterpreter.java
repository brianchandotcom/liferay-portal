/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.interpreter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;

import java.util.Locale;

/**
 * @author Sergio González
 */
public interface SharingEntryInterpreter {

	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale)
		throws PortalException;

	public SharingEntryEditRenderer getSharingEntryEditRenderer();

	public SharingEntryViewRenderer getSharingEntryViewRenderer();

	public String getTitle(SharingEntry sharingEntry);

	public boolean isVisible(SharingEntry sharingEntry) throws PortalException;

}