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

package com.liferay.gradle.plugins.lang.merger.internal.work;

import com.liferay.gradle.plugins.lang.merger.tasks.MergePropertiesSetting;

import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

/**
 * @author Peter Shin
 */
public interface MergePropertiesWorkParameters extends WorkParameters {

	public Property<String> getCharsetName();

	public Property<Boolean> getCopyAllowed();

	public DirectoryProperty getDestinationDir();

	public ConfigurableFileCollection getDestinationFiles();

	public MapProperty<String, MergePropertiesSetting> getSettings();

	public ConfigurableFileCollection getSourceDirs();

}