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

package com.liferay.documentum.repository.model;

import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.common.DfException;

import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Iván Zaera
 */
public class DocumentumFolder
	extends DocumentumObject implements ExtRepositoryFolder {

	public DocumentumFolder(IDfFolder idfFolder, boolean root) {
		super(idfFolder);

		_idfFolder = idfFolder;
		_root = root;
	}

	public IDfFolder getIDfFolder() {
		return _idfFolder;
	}

	@Override
	public String getName() {
		try {
			return _idfFolder.getObjectName();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public boolean isRoot() {
		return _root;
	}

	private final IDfFolder _idfFolder;
	private final boolean _root;

}