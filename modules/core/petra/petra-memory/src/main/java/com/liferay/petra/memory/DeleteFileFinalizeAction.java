/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.memory;

import java.io.File;

import java.lang.ref.Reference;

/**
 * @author Shuyang Zhou
 */
public class DeleteFileFinalizeAction implements FinalizeAction {

	public DeleteFileFinalizeAction(String fileName) {
		_fileName = fileName;
	}

	@Override
	public void doFinalize(Reference<?> reference) {
		File file = new File(_fileName);

		file.delete();
	}

	private final String _fileName;

}