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

package com.liferay.portal.tools.upgrade.table.builder;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeTableBuilder {

	public static void main(String[] args) {
		try {
			Map<String, String> arguments =
				ArgumentsUtil.parseArguments(args);

			Boolean bnd = Boolean.valueOf(arguments.get("bnd"));
			String moduleName = arguments.get("module.name");
			String upgradeBaseDir = arguments.get("upgrade.base.dir");
			String upgradeTableDir = arguments.get("upgrade.table.dir");

			new UpgradeTableBuilder(bnd, moduleName, upgradeBaseDir,
				upgradeTableDir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UpgradeTableBuilder(
			boolean bnd, String moduleName, String upgradeBaseDir,
			String upgradeTableDir)
		throws Exception {

		_bnd = bnd;
		_moduleName = moduleName;
		_upgradeBaseDir = upgradeBaseDir;
		_upgradeTableDir = upgradeTableDir;

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_upgradeBaseDir);
		ds.setIncludes(
			new String[] {"**\\upgrade\\v**\\util\\*Table.java"});

		ds.scan();

		String[] filePaths = ds.getIncludedFiles();

		for (String filePath : filePaths) {
			filePath = StringUtil.replace(filePath, "\\", "/");

			String upgradeFileVersion = filePath.replaceFirst(
				".*/upgrade/v(.+)/util.*", "$1");

			upgradeFileVersion = upgradeFileVersion.replaceAll("_", ".");

			if (upgradeFileVersion.contains("to")) {
				upgradeFileVersion = upgradeFileVersion.replaceFirst(
					".+\\.to\\.(.+)", "$1");
			}

			String fileName = FilenameUtils.getName(filePath);

			String upgradeFileName = fileName.replaceFirst("Table",
				"ModelImpl");

			String upgradeFilePath = _upgradeTableDir + "/" + upgradeFileVersion
				+ "/" + upgradeFileName;

			if (!_fileUtil.exists(upgradeFilePath)) {
				if (!upgradeFileVersion.equals(_getModuleVersion())) {
					continue;
				}

				upgradeFilePath = _findUpgradeFilePath(upgradeFileName);

				if (upgradeFilePath == null) {
					continue;
				}
			}

			String content = _fileUtil.read(upgradeFilePath);

			String packagePath =
				"com.liferay.portal.upgrade.v" +
					StringUtil.replace(upgradeFileVersion, ".", "_") + ".util";
			String className = fileName.replaceFirst("\\.java", "");

			String author = _getAuthor(fileName);

			File indexesFile = _getIndexesFile(upgradeFileVersion);

			String[] addIndexes = _getAddIndexes(indexesFile,
				fileName.replaceFirst("Table\\.java", ""));

			content = _getContent(
				packagePath, className, content, author, addIndexes);

			_fileUtil.write(filePath, content);
		}
	}

	private String _findModuleIndexFileName() {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_upgradeBaseDir);
		ds.setIncludes(
			new String[] {
				"**\\" + _moduleName + "-service\\src\\*\\sql\\indexes.sql"});

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		if (fileNames.length > 0) {
			return _upgradeBaseDir + fileNames[0];
		}
		else {
			return null;
		}
	}

	private String _findUpgradeFilePath(String modelName) {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_upgradeBaseDir);

		ds.setIncludes(new String[] {"**\\" + modelName});

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		if (fileNames.length > 0) {
			return fileNames[0];
		}
		else {
			return null;
		}
	}

	private String[] _getAddIndexes(File indexesFile, String tableName)
		throws Exception {

		List<String> addIndexes = new ArrayList<String>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new InputStreamReader(
					new FileInputStream(indexesFile)))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains(" on " + tableName + " (") ||
					line.contains(" on " + tableName + "_ (")) {

					String sql = line.trim();

					if (sql.endsWith(";")) {
						sql = sql.substring(0, sql.length() - 1);
					}

					addIndexes.add(sql);
				}
			}
		}

		return addIndexes.toArray(new String[addIndexes.size()]);
	}

	private String _getAuthor(String fileName) throws Exception {
		if (_fileUtil.exists(fileName)) {
			String content = _fileUtil.read(fileName);

			int x = content.indexOf("* @author ");

			if (x != -1) {
				int y = content.indexOf("*", x + 1);

				if (y != -1) {
					return content.substring(x + 10, y).trim();
				}
			}
		}

		return ServiceBuilder.AUTHOR;
	}

	private String _getContent(
			String packagePath, String className, String content, String author,
			String[] addIndexes)
		throws Exception {

		int x = content.indexOf("public static final String TABLE_NAME =");

		if (x == -1) {
			x = content.indexOf("public static String TABLE_NAME =");
		}

		int y = content.indexOf("public static final String TABLE_SQL_DROP =");

		if (y == -1) {
			y = content.indexOf("public static String TABLE_SQL_DROP =");
		}

		y = content.indexOf(";", y);

		content = content.substring(x, y + 1);

		content = StringUtil.replace(
			content,
			new String[] {
				"\t", "{ \"", "new Integer(Types.", ") }", " }"
			},
			new String[] {
				"", "{\"", "Types.", "}", "}"
			});

		while (content.contains("\n\n")) {
			content = StringUtil.replace(content, "\n\n", "\n");
		}

		StringBundler sb = new StringBundler();

		sb.append(_fileUtil.read("../copyright.txt"));

		sb.append("\n\npackage ");
		sb.append(packagePath);
		sb.append(";\n\n");

		sb.append("import java.sql.Types;\n\n");

		sb.append("/**\n");
		sb.append(" * @author\t  ");
		sb.append(author);
		sb.append("\n");
		sb.append(" * @generated\n");
		sb.append(" */\n");
		sb.append("public class ");
		sb.append(className);
		sb.append(" {\n\n");

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (line.startsWith("public static") ||
				line.startsWith("};")) {

				sb.append("\t");
			}
			else if (line.startsWith("{\"")) {
				sb.append("\t\t");
			}

			sb.append(line);
			sb.append("\n");

			if (line.endsWith(";")) {
				sb.append("\n");
			}
		}

		sb.append("\tpublic static final String[] TABLE_SQL_ADD_INDEXES = {\n");

		for (int i = 0; i < addIndexes.length; i++) {
			String addIndex = addIndexes[i];

			sb.append("\t\t\"");
			sb.append(addIndex);
			sb.append("\"");

			if ((i + 1) < addIndexes.length) {
				sb.append(",");
			}

			sb.append("\n");
		}

		sb.append("\t};\n\n");

		sb.append("}");

		return sb.toString();
	}

	private File _getIndexesFile(String upgradeFileVersion) {
		File indexesFile = new File(_upgradeTableDir + "/" +
			upgradeFileVersion + "/indexes.sql");

		if (_moduleName.equals("portal-impl")) {
			if (!indexesFile.exists()) {
				indexesFile = new File("../sql/indexes.sql");
			}
		}
		else {
			indexesFile = new File(_findModuleIndexFileName());
		}

		return indexesFile;
	}

	private String _getModuleVersion() throws Exception {
		if (_bnd) {
			DirectoryScanner ds = new DirectoryScanner();

			ds.setBasedir(_upgradeBaseDir);
			ds.setIncludes(
				new String[] {"**\\" + _moduleName + "\\*-service\\*.bnd"});

			ds.scan();

			String[] fileNames = ds.getIncludedFiles();

			if (fileNames.length > 0) {
				String content = _fileUtil.read(fileNames[0]);

				Properties properties = PropertiesUtil.load(content);

				return properties.getProperty("Bundle-Version");
			}

			return StringPool.BLANK;
		}
		else {
			return ReleaseInfo.getVersion();
		}
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private Boolean _bnd;
	private String _moduleName;
	private String _upgradeBaseDir;
	private String _upgradeTableDir;

}