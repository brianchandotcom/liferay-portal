/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.importer;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public interface KBArchive {

	public Collection<Folder> getFolders();

	public interface File extends Resource {

		public String getContent();

	}

	public interface Folder extends Resource {

		public Collection<File> getFiles();

		public File getIntroFile();

		public File getParentFolderIntroFile();

	}

	public interface Resource {

		public String getName();

	}

}