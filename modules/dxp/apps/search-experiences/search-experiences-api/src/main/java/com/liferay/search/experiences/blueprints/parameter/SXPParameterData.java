/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.blueprints.parameter;

import java.util.List;
import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameterData {

	public Optional<SXPParameter> getByNameOptional(String name);

	public Optional<SXPParameter> getByTemplateVariableNameOptional(
		String name);

	public int getFrom();

	public String getKeywords();

	public List<SXPParameter> getParameters();

	public int getSize();

}