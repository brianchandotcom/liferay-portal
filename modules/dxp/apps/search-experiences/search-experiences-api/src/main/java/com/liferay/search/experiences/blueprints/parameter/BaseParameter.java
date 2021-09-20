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

/**
 * @author Petteri Karttunen
 */
public abstract class BaseParameter {

	public BaseParameter(String name, boolean templateVariable) {
		this.name = name;
		this.templateVariable = templateVariable;
	}

	public String getName() {
		return name;
	}

	public String getTemplateVariable() {
		if (isTemplateVariable()) {
			return "${" + name + "}";
		}

		return null;
	}

	public boolean isTemplateVariable() {
		return templateVariable;
	}

	protected final String name;
	protected final boolean templateVariable;

}