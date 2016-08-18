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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildDBTask extends JavaExec {

	public BuildDBTask() {
		setMain("com.liferay.portal.tools.DBBuilder");
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