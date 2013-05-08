/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.nonblocking;

import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.nio.channels.SelectionKey;

/**
 * @author Shuyang Zhou
 */
class SelectionKeyRegistrationReference implements RegistrationReference {

	public void cancelRegistration() {
		readSelectionKey.cancel();
		writeSelectionKey.cancel();
	}

	public Intraband getIntraband() {
		return intraband;
	}

	public boolean isValid() {
		return writeSelectionKey.isValid();
	}

	protected SelectionKeyRegistrationReference(
		Intraband intraband, SelectionKey readSelectionKey,
		SelectionKey writeSelectionKey) {

		this.intraband = intraband;
		this.readSelectionKey = readSelectionKey;
		this.writeSelectionKey = writeSelectionKey;
	}

	protected final Intraband intraband;
	protected final SelectionKey readSelectionKey;
	protected final SelectionKey writeSelectionKey;

}