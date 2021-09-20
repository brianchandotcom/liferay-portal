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

import com.liferay.search.experiences.blueprints.parameter.visitor.EvaluationVisitor;
import com.liferay.search.experiences.blueprints.parameter.visitor.ToStringVisitor;
import com.liferay.search.experiences.exception.SXPParameterEvaluationException;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameter {

	public boolean accept(EvaluationVisitor visitor)
		throws SXPParameterEvaluationException;

	public String accept(ToStringVisitor visitor, Map<String, String> options)
		throws Exception;

	public String getName();

	public String getTemplateVariable();

	public Object getValue();

	public boolean isTemplateVariable();

}