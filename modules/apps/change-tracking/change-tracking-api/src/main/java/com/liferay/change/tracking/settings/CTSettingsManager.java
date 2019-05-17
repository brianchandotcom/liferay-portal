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

package com.liferay.change.tracking.settings;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides an API to handle global and user level change tracking settings in a
 * general way
 *
 * @author Gergely Mathe
 * @review
 */
@ProviderType
public interface CTSettingsManager {

	/**
	 * Retrieves a global change tracking setting's value for a given company
	 * and a key or <code>null</code> if it can not be determined.
	 *
	 * @param  companyId the primary key of the company
	 * @param  key the key of the setting's value
	 * @return the global change tracking setting's value for the given company
	 *         and key or <code>null</code> if it can not be determined
	 */
	public String getGlobalCTSetting(long companyId, String key);

	/**
	 * Retrieves a global change tracking setting's value for a given company
	 * and a key or <code>defaultValue</code> if it can not be determined.
	 *
	 * @param  companyId the primary key of the company
	 * @param  key the key of the setting's value
	 * @param  defaultValue the default value to return if the actual value can
	 *         not be determined
	 * @return the global change tracking setting's value for the given company
	 *         and key or <code>defaultValue</code> if it can not be determined
	 */
	public String getGlobalCTSetting(
		long companyId, String key, String defaultValue);

	/**
	 * Retrieves a user change tracking setting's value for a given user and a
	 * key. If it can not be determined, it looks for the same key as a global
	 * setting. If it still can not be found it returns <code>null</code>.
	 *
	 * @param  userId the primary key of the user
	 * @param  key the key of the setting's value
	 * @return the user change tracking setting's value for the given user and
	 *         key or the global change tracking setting's value for the given
	 *         user and key or <code>null</code> if it can not be determined
	 */
	public String getUserCTSetting(long userId, String key);

	/**
	 * Retrieves a user change tracking setting's value for a given user and a
	 * key. If it can not be determined, it looks for the same key as a global
	 * setting. If it still can not be found it returns
	 * <code>defaultValue</code>.
	 *
	 * @param  userId the primary key of the user
	 * @param  key the key of the setting's value
	 * @param  defaultValue the default value to return if the actual value can
	 *         not be determined
	 * @return the user change tracking setting's value for the given user and
	 *         key or the global change tracking setting's value for the given
	 *         user and key or <code>defaultValue</code> if it can not be
	 *         determined
	 */
	public String getUserCTSetting(
		long userId, String key, String defaultValue);

	/**
	 * Sets a global change tracking setting's value for a given company and a
	 * key.
	 *
	 * @param companyId the primary key of the company
	 * @param key the key of the setting's value
	 * @param value the setting's value
	 */
	public void setGlobalCTSetting(long companyId, String key, String value);

	/**
	 * Sets a user change tracking setting's value for a given user and a key.
	 *
	 * @param userId the primary key of the user
	 * @param key the key of the setting's value
	 * @param value the setting's value
	 */
	public void setUserCTSetting(long userId, String key, String value);

}