/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.wsdd.builder.util;

import java.io.File;

import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.types.FileSet;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteTask {

	public static void deleteDirectory(File dir) {
		Delete delete = new Delete();

		delete.setProject(AntUtil.getProject());
		delete.setDir(dir);
		delete.setFailOnError(false);

		delete.execute();
	}

	public static void deleteDirectory(String dir) {
		deleteDirectory(new File(dir));
	}

	public static void deleteFile(File file) {
		Delete delete = new Delete();

		delete.setProject(AntUtil.getProject());
		delete.setFile(file);
		delete.setFailOnError(false);

		delete.execute();
	}

	public static void deleteFile(String file) {
		deleteFile(new File(file));
	}

	public static void deleteFiles(File dir, String includes, String excludes) {
		Delete delete = new Delete();

		delete.setProject(AntUtil.getProject());
		delete.setFailOnError(false);

		FileSet fileSet = new FileSet();

		fileSet.setDir(dir);
		fileSet.setIncludes(includes);
		fileSet.setExcludes(excludes);

		delete.addFileset(fileSet);

		delete.execute();
	}

	public static void deleteFiles(
		String dir, String includes, String excludes) {

		deleteFiles(new File(dir), includes, excludes);
	}

}