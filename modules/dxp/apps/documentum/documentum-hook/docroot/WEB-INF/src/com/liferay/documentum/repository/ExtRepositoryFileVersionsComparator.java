/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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