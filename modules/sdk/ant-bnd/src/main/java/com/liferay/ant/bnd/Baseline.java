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

package com.liferay.ant.bnd;

import aQute.bnd.differ.Baseline.BundleInfo;
import aQute.bnd.differ.Baseline.Info;
import aQute.bnd.differ.DiffPluginImpl;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.diff.Delta;
import aQute.bnd.service.diff.Diff;
import aQute.bnd.version.Version;

import aQute.lib.io.IO;

import aQute.service.reporter.Reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
public abstract class Baseline {

	public boolean execute() throws Exception {
		boolean match = true;

		_headerPrinted = false;
		_printWriter = null;

		if (_logFile.exists()) {
			_logFile.delete();
		}

		File logDir = _logFile.getParentFile();

		logDir.mkdirs();

		BaselineProcessor baselineProcessor = new BaselineProcessor();

		baselineProcessor.setProperties(_properties);

		Jar newJar = new Jar(_newJarFile);

		Jar oldJar = null;

		if (_oldJarFile != null) {
			if (!_oldJarFile.exists() || _oldJarFile.isDirectory() ||
				!_oldJarFile.canRead()) {

				baselineProcessor.warning(
					"Baseline file %s is invalid. Check if it exists, is " +
						"readable, and is not a directory.",
					_oldJarFile);
			}
			else {
				oldJar = new Jar(_oldJarFile);
			}
		}
		else {
			oldJar = baselineProcessor.getBaselineJar();
		}

		try {
			if (oldJar == null) {
				return match;
			}

			aQute.bnd.differ.Baseline baseline = new aQute.bnd.differ.Baseline(
				baselineProcessor, new DiffPluginImpl());

			Set<Info> infos = baseline.baseline(newJar, oldJar, null);

			if (infos.isEmpty()) {
				return match;
			}

			BundleInfo bundleInfo = baseline.getBundleInfo();

			if (_forceCalculatedVersion) {
				bundleInfo.suggestedVersion = calculateVersion(
					bundleInfo.olderVersion, infos);
			}
			else if (hasPackageRemoved(infos)) {
				bundleInfo.suggestedVersion = new Version(
					bundleInfo.olderVersion.getMajor() + 1, 0, 0);
			}

			int compare = bundleInfo.suggestedVersion.compareTo(
				bundleInfo.newerVersion.getWithoutQualifier());

			if ((compare > 0) || (_forceCalculatedVersion && (compare != 0))) {
				bundleInfo.mismatch = true;
			}

			if (bundleInfo.mismatch) {
				match = false;

				updateBundleVersion(
					bundleInfo.newerVersion, bundleInfo.suggestedVersion);
			}

			Info[] infosArray = infos.toArray(new Info[infos.size()]);

			Arrays.sort(
				infosArray,
				new Comparator<Info>() {

					@Override
					public int compare(Info info1, Info info2) {
						return info1.packageName.compareTo(info2.packageName);
					}

				});

			doHeader(bundleInfo);

			for (Info info : infosArray) {
				if (info.mismatch) {
					match = false;
				}

				Diff packageDiff = info.packageDiff;

				Delta delta = packageDiff.getDelta();

				if (_forceVersionOneOnAddedPackages && (delta == Delta.ADDED) &&
					bundleInfo.newerVersion.equals(info.newerVersion)) {

					info.suggestedVersion = Version.ONE;
				}

				String warnings = "-";

				Version newerVersion = info.newerVersion;
				Version suggestedVersion = info.suggestedVersion;

				if (suggestedVersion != null) {
					if (newerVersion.compareTo(suggestedVersion) > 0) {
						match = false;

						warnings = "EXCESSIVE VERSION INCREASE";
					}
					else if (newerVersion.compareTo(suggestedVersion) < 0) {
						warnings = "VERSION INCREASE REQUIRED";
					}
				}

				if (delta == Delta.REMOVED) {
					warnings = "PACKAGE REMOVED";
				}
				else if (delta == Delta.UNCHANGED) {
					boolean newVersionSuggested = false;

					if (suggestedVersion.compareTo(newerVersion) > 0) {
						warnings = "VERSION INCREASE SUGGESTED";

						newVersionSuggested = true;
					}
					else if (suggestedVersion.compareTo(newerVersion) < 0) {
						warnings = "EXCESSIVE VERSION INCREASE";

						newVersionSuggested = true;
					}

					if (!newVersionSuggested && !info.mismatch) {
						continue;
					}
				}

				boolean correctPackageInfo = generatePackageInfo(info, delta);

				if (!correctPackageInfo) {
					if (delta == Delta.ADDED) {
						warnings = "PACKAGE ADDED, MISSING PACKAGEINFO";
					}
					else if (delta == Delta.REMOVED) {
						warnings = "PACKAGE REMOVED, UNNECESSARY PACKAGEINFO";
					}
				}

				if (((!_reportDiff || _reportOnlyDirtyPackages) &&
					 warnings.equals("-")) ||
					(_reportOnlyDirtyPackages && correctPackageInfo &&
					 (delta == Delta.REMOVED))) {

					continue;
				}

				doInfo(bundleInfo, info, warnings);

				if (_reportDiff && (delta != Delta.REMOVED)) {
					doPackageDiff(packageDiff);
				}
			}
		}
		finally {
			log(baselineProcessor);

			baselineProcessor.close();
			newJar.close();

			if (oldJar != null) {
				oldJar.close();
			}

			if (_printWriter != null) {
				_printWriter.close();
			}
		}

		return match;
	}

	public Properties getProperties() {
		return _properties;
	}

	public void setBndFile(File bndFile) {
		_bndFile = bndFile;
	}

	public void setForceCalculatedVersion(boolean forceCalculatedVersion) {
		_forceCalculatedVersion = forceCalculatedVersion;
	}

	public void setForcePackageInfo(boolean forcePackageInfo) {
		_forcePackageInfo = forcePackageInfo;
	}

	public void setForceVersionOneOnAddedPackages(
		boolean forceVersionOneOnAddedPackages) {

		_forceVersionOneOnAddedPackages = forceVersionOneOnAddedPackages;
	}

	public void setLogFile(File logFile) {
		_logFile = logFile;
	}

	public void setNewJarFile(File newJarFile) {
		_newJarFile = newJarFile;
	}

	public void setOldJarFile(File oldJarFile) {
		_oldJarFile = oldJarFile;
	}

	public void setReportDiff(boolean reportDiff) {
		_reportDiff = reportDiff;
	}

	public void setReportOnlyDirtyPackages(boolean reportOnlyDirtyPackages) {
		_reportOnlyDirtyPackages = reportOnlyDirtyPackages;
	}

	public void setSourceDir(File sourceDir) {
		_sourceDir = sourceDir;
	}

	protected Version calculateVersion(Version version, Set<Info> infos)
		throws IOException {

		Delta highestDelta = Delta.UNCHANGED;

		Set<String> movedPackages = getMovedPackages();

		for (Info info : infos) {
			Delta delta = info.packageDiff.getDelta();

			if ((delta == Delta.ADDED) || (delta == Delta.CHANGED)) {
				delta = Delta.MICRO;
			}
			else if (delta == Delta.REMOVED) {
				if (movedPackages.contains(info.packageName)) {
					delta = Delta.MICRO;
				}
				else {
					delta = Delta.MAJOR;
				}
			}

			if (delta.compareTo(highestDelta) > 0) {
				highestDelta = delta;
			}
		}

		if (highestDelta == Delta.MAJOR) {
			version = new Version(version.getMajor() + 1, 0, 0);
		}
		else if (highestDelta == Delta.MINOR) {
			version = new Version(
				version.getMajor(), version.getMinor() + 1, 0);
		}
		else {
			version = new Version(
				version.getMajor(), version.getMinor(), version.getMicro() + 1);
		}

		return version;
	}

	protected void doDiff(Diff diff, StringBuilder sb) {
		String type = String.valueOf(diff.getType());

		String output = String.format(
			"%s%-3s %-10s %s", sb, getShortDelta(diff.getDelta()),
			type.toLowerCase(), diff.getName());

		log(output);

		if (_printWriter != null) {
			_printWriter.println(output);
		}

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}

		sb.deleteCharAt(sb.length() - 1);
	}

	protected void doHeader(BundleInfo bundleInfo) throws IOException {
		if (!bundleInfo.mismatch) {
			return;
		}

		String output = "[Baseline Report] Mode: ";

		if (_reportDiff) {
			output += "diff";
		}
		else {
			output += "standard";
		}

		if (_logFile != null) {
			output += " (persisted)";
		}

		log(output);

		output =
			"[Baseline Warning] Bundle Version Change Recommended: " +
				bundleInfo.suggestedVersion;

		log(output);

		persistLog(output);
	}

	protected void doInfo(BundleInfo bundleInfo, Info info, String warnings)
		throws IOException {

		doPackagesHeader(bundleInfo);

		reportLog(
			String.valueOf(info.mismatch ? '*' : ' '), info.packageName,
			String.valueOf(info.packageDiff.getDelta()),
			String.valueOf(info.newerVersion),
			String.valueOf(info.olderVersion),
			String.valueOf(
				(info.suggestedVersion == null) ? "-" : info.suggestedVersion),
			warnings, String.valueOf(info.attributes));
	}

	protected void doPackageDiff(Diff diff) {
		StringBuilder sb = new StringBuilder();

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}
	}

	protected void doPackagesHeader(BundleInfo bundleInfo) throws IOException {
		if (_headerPrinted) {
			return;
		}

		_headerPrinted = true;

		reportLog(
			" ", "PACKAGE_NAME", "DELTA", "CUR_VER", "BASE_VER", "REC_VER",
			"WARNINGS", "ATTRIBUTES");

		reportLog(
			"=", "==================================================",
			"==========", "==========", "==========", "==========",
			"==========", "==========");
	}

	protected boolean generatePackageInfo(Info info, Delta delta)
		throws Exception {

		boolean correct = true;

		File packageDir = new File(
			_sourceDir, info.packageName.replace('.', File.separatorChar));

		if (!_forcePackageInfo && !packageDir.exists()) {
			return correct;
		}

		File packageInfoFile = new File(packageDir, "packageinfo");

		if (delta == Delta.REMOVED) {
			if (packageInfoFile.exists()) {
				correct = false;

				packageInfoFile.delete();
			}
		}
		else {
			if (!packageInfoFile.exists()) {
				correct = false;
			}

			packageDir.mkdirs();

			FileOutputStream fileOutputStream = new FileOutputStream(
				packageInfoFile);

			String content = "version " + info.suggestedVersion;

			fileOutputStream.write(content.getBytes());

			fileOutputStream.close();
		}

		return correct;
	}

	protected Set<String> getMovedPackages() throws IOException {
		File movedPackagesFile = new File(
			_bndFile.getParentFile(), "moved-packages.txt");

		if (!movedPackagesFile.exists()) {
			return Collections.emptySet();
		}

		Set<String> movedPackages = new HashSet<>();

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(movedPackagesFile))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				movedPackages.add(line);
			}
		}

		return movedPackages;
	}

	protected String getShortDelta(Delta delta) {
		if (delta == Delta.ADDED) {
			return "+";
		}
		else if (delta == Delta.CHANGED) {
			return "~";
		}
		else if (delta == Delta.MAJOR) {
			return ">";
		}
		else if (delta == Delta.MICRO) {
			return "0xB5";
		}
		else if (delta == Delta.MINOR) {
			return "<";
		}
		else if (delta == Delta.REMOVED) {
			return "-";
		}

		String deltaString = delta.toString();

		return String.valueOf(deltaString.charAt(0));
	}

	protected boolean hasPackageRemoved(Iterable<Info> infos)
		throws IOException {

		Set<String> movedPackages = getMovedPackages();

		for (Info info : infos) {
			if ((info.packageDiff.getDelta() == Delta.REMOVED) &&
				!movedPackages.contains(info.packageName)) {

				return true;
			}
		}

		return false;
	}

	protected abstract void log(Reporter reporter);

	protected abstract void log(String output);

	protected void persistLog(String output) throws IOException {
		if (_logFile == null) {
			return;
		}

		if (_printWriter == null) {
			_logFile.createNewFile();

			_printWriter = new PrintWriter(_logFile);
		}

		_printWriter.println(output);
	}

	protected void reportLog(
			String string1, String string2, String string3, String string4,
			String string5, String string6, String string7, String string8)
		throws IOException {

		String output = String.format(
			"%s %-50s %-10s %-10s %-10s %-10s %-10s", string1, string2, string3,
			string4, string5, string6, string7);

		log(output);

		persistLog(output);
	}

	protected void updateBundleVersion(Version oldVersion, Version newVersion)
		throws IOException {

		if (_bndFile == null) {
			return;
		}

		String content = IO.collect(_bndFile);

		content = content.replace(
			Constants.BUNDLE_VERSION + ": " + oldVersion,
			Constants.BUNDLE_VERSION + ": " + newVersion);

		IO.store(content, _bndFile);
	}

	private File _bndFile;
	private boolean _forceCalculatedVersion;
	private boolean _forcePackageInfo;
	private boolean _forceVersionOneOnAddedPackages = true;
	private boolean _headerPrinted;
	private File _logFile;
	private File _newJarFile;
	private File _oldJarFile;
	private PrintWriter _printWriter;
	private final Properties _properties = new Properties();
	private boolean _reportDiff;
	private boolean _reportOnlyDirtyPackages;
	private File _sourceDir;

}