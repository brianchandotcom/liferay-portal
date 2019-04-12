/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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