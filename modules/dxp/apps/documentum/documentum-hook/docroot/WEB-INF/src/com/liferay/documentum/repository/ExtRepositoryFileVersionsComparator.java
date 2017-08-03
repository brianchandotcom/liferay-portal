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

package com.liferay.documentum.repository;

import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.documentum.repository.model.DocumentumVersionNumber;

import java.util.Comparator;

/**
 * @author Iván Zaera
 */
public class ExtRepositoryFileVersionsComparator
	implements Comparator<ExtRepositoryFileVersion> {

	@Override
	public int compare(
		ExtRepositoryFileVersion extRepositoryFileVersion1,
		ExtRepositoryFileVersion extRepositoryFileVersion2) {

		DocumentumVersionNumber documentumVersionNumber1 =
			new DocumentumVersionNumber(extRepositoryFileVersion1.getVersion());
		DocumentumVersionNumber documentumVersionNumber2 =
			new DocumentumVersionNumber(extRepositoryFileVersion2.getVersion());

		return -documentumVersionNumber1.compareTo(documentumVersionNumber2);
	}

}