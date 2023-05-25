/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.task;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.GUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.util.CollectionUtils;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildDBTask extends JavaExec {

	public BuildDBTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.portal.tools.DBBuilder");

		setMaxHeapSize("384m");
		systemProperty(
			"external-properties",
			"com/liferay/portal/tools/dependencies/portal-tools.properties");
	}

	public BuildDBTask databaseTypes(Iterable<Object> databaseTypes) {
		GUtil.addToCollection(_databaseTypes, databaseTypes);

		return this;
	}

	public BuildDBTask databaseTypes(Object... databaseTypes) {
		return databaseTypes(Arrays.asList(databaseTypes));
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@Input
	public String getDatabaseName() {
		return GradleUtil.toString(_databaseName);
	}

	@Input
	public List<String> getDatabaseTypes() {
		return GradleUtil.toStringList(_databaseTypes);
	}

	@OutputDirectories
	public Iterable<File> getOutputDirs() {
		File sqlDir = getSqlDir();

		return Arrays.asList(
			new File(sqlDir, "create"), new File(sqlDir, "create-bare"),
			new File(sqlDir, "indexes"), new File(sqlDir, "tables"));
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSqlDir() {
		return GradleUtil.toFile(getProject(), _sqlDir);
	}

	public void setDatabaseName(Object databaseName) {
		_databaseName = databaseName;
	}

	public void setDatabaseTypes(Iterable<Object> databaseTypes) {
		_databaseTypes.clear();

		databaseTypes(databaseTypes);
	}

	public void setDatabaseTypes(Object... databaseTypes) {
		setDatabaseTypes(Arrays.asList(databaseTypes));
	}

	public void setSqlDir(Object sqlDir) {
		_sqlDir = sqlDir;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("db.database.name=" + getDatabaseName());
		args.add(
			"db.database.types=" +
				CollectionUtils.join(",", getDatabaseTypes()));
		args.add("db.sql.dir=" + FileUtil.getAbsolutePath(getSqlDir()));

		return args;
	}

	private Object _databaseName;
	private final Set<Object> _databaseTypes = new LinkedHashSet<>();
	private Object _sqlDir;

}