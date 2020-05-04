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

package com.liferay.exportimport.changeset;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface ChangesetManager {

	public void addChangeset(Changeset changeset);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void clearChangesets();

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean hasChangeset(String changesetUuid);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Optional<Changeset> peekChangeset(String changesetUuid);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #removeChangeset(String changesetUuid)}
	 */
	@Deprecated
	public Optional<Changeset> popChangeset(String changesetUuid);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public long publishChangeset(
		Changeset changeset, ChangesetEnvironment changesetEnvironment);

	public Changeset removeChangeset(String changesetUuid);

}