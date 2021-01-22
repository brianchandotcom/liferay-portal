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

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.List;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface BatchDependentJob extends Job {

	public List<AxisTestClassGroup> getDependentAxisTestClassGroups();

	public Set<String> getDependentBatchNames();

	public List<BatchTestClassGroup> getDependentBatchTestClassGroups();

	public Set<String> getDependentSegmentNames();

	public List<SegmentTestClassGroup> getDependentSegmentTestClassGroups();

}