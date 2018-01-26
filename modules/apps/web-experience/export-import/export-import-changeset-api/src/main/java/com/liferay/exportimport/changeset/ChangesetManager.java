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

/**
 * @author Mate Thurzo
 */
public interface ChangesetManager {

	public void addChangeset(Changeset changeset);

	public void clearChangesets();

	public boolean hasChangeset(String changesetUuid);

	public Optional<Changeset> peekChangeset(String changesetUuid);

	public Optional<Changeset> popChangeset(String changesetUuid);

	public void publishChangeset(
		Changeset changeset, ChangesetEnvironment environment);

}