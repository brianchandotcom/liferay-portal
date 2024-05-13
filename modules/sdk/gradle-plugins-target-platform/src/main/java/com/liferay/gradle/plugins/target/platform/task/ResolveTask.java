/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.target.platform.task;

import aQute.bnd.build.model.clauses.HeaderClause;
import aQute.bnd.build.model.conversions.Converter;
import aQute.bnd.gradle.AbstractBndrun;
import aQute.bnd.gradle.BndUtils;
import aQute.bnd.osgi.Constants;

import biz.aQute.resolve.Bndrun;
import biz.aQute.resolve.ResolveProcess;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;

import java.io.File;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;

import org.osgi.service.resolver.ResolutionException;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class ResolveTask extends AbstractBndrun {

	/**
	 * @deprecated as of 4.0.0, replaced by bndrun property
	 */
	@Deprecated
	@InputFile
	public File getBndrunFile() {
		RegularFileProperty regularFileProperty = getBndrun();

		return GradleUtil.toFile(getProject(), regularFileProperty.get());
	}

	@InputFile
	public File getDistroFile() {
		return _distroFileCollection.getSingleFile();
	}

	@Override
	public MapProperty<String, Object> getProperties() {
		Project project = getProject();

		ObjectFactory objects = project.getObjects();

		MapProperty<String, Object> mapProperty = objects.mapProperty(
			String.class, Object.class);

		mapProperty.put("project", project);

		Map<String, ?> properties = project.getProperties();

		Map<String, Object> bundle = (Map<String, Object>)properties.get(
			"bundle");

		for (Map.Entry<String, Object> stringObjectEntry : bundle.entrySet()) {
			mapProperty.put(
				String.format("project.bundle.%s", stringObjectEntry.getKey()),
				stringObjectEntry.getValue());
		}

		File distroFile = getDistroFile();

		mapProperty.put(
			"targetPlatformDistro",
			distroFile.getAbsolutePath() + ";version=file");

		mapProperty.putAll(super.getProperties());

		return mapProperty;
	}

	@Input
	public List<String> getRunBundles() {
		return _runBundles;
	}

	@Input
	public boolean isFailOnChanges() {
		return GradleUtil.toBoolean(_failOnChanges);
	}

	/**
	 * @deprecated as of 4.0.0, with no direct replacement
	 */
	@Deprecated
	@Input
	public boolean isOffline() {
		return GradleUtil.toBoolean(_offline);
	}

	@Input
	public boolean isReportOptional() {
		return GradleUtil.toBoolean(_reportOptional);
	}

	/**
	 * @deprecated as of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public void resolve() throws Exception {
	}

	/**
	 * @deprecated as of 4.0.0, replaced by bndrun property
	 */
	@Deprecated
	public void setBndrunFile(Object bndrunFile) {
		RegularFileProperty regularFileProperty = getBndrun();

		regularFileProperty.set(GradleUtil.toFile(getProject(), bndrunFile));
	}

	public void setDistro(FileCollection distroFileCollection) {
		_distroFileCollection = distroFileCollection;
	}

	public void setFailOnChanges(Object failOnChanges) {
		_failOnChanges = failOnChanges;
	}

	/**
	 * @deprecated as of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public void setOffline(Object offline) {
		_offline = offline;
	}

	public void setReportOptional(Object reportOptional) {
		_reportOptional = reportOptional;
	}

	@Override
	protected void worker(aQute.bnd.build.Project run) throws Exception {
		if (!(run instanceof Bndrun)) {
			throw new GradleException(
				"Resolving a project's bnd.bnd file is not supported by this " +
					"task. This task can only resolve a bndrun file.");
		}

		_worker((Bndrun)run);
	}

	private void _worker(Bndrun bndrun) throws Exception {
		Logger logger = getLogger();

		try {
			logger.info(
				"Resolving bundles required for {}",
				bndrun.getPropertiesFile());

			_runBundles = bndrun.resolve(
				isFailOnChanges(), false, _runbundlesFormatter);

			Stream<String> stream = _runBundles.stream();

			logger.lifecycle(
				"{}:\n    {}", Constants.RUNBUNDLES,
				stream.collect(Collectors.joining("\n    ")));
		}
		catch (ResolutionException resolutionException) {
			logger.error(
				ResolveProcess.format(resolutionException, isReportOptional()));

			throw new GradleException(
				bndrun.getPropertiesFile() + " resolution exception",
				resolutionException);
		}
		finally {
			BndUtils.logReport(bndrun, logger);
		}

		if (!bndrun.isOk()) {
			throw new GradleException(
				bndrun.getPropertiesFile() + " resolution failure");
		}
	}

	private static final Converter
		<List<String>, Collection<? extends HeaderClause>>
			_runbundlesFormatter =
				new Converter
					<List<String>, Collection<? extends HeaderClause>>() {

					@Override
					public List<String> convert(
							Collection<? extends HeaderClause> input)
						throws IllegalArgumentException {

						Stream<? extends HeaderClause> stream = input.stream();

						return stream.map(
							HeaderClause::toString
						).collect(
							Collectors.toList()
						);
					}

					@Override
					public List<String> error(String msg) {
						return null;
					}

				};

	private FileCollection _distroFileCollection;
	private Object _failOnChanges = Boolean.FALSE;
	private Object _offline;
	private Object _reportOptional = Boolean.TRUE;
	private List<String> _runBundles = Collections.emptyList();

}