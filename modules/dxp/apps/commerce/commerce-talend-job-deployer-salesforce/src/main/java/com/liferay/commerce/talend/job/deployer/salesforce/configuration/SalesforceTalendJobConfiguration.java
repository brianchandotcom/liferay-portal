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

package com.liferay.commerce.talend.job.deployer.salesforce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Danny Situ
 */
@ExtendedObjectClassDefinition(
	category = "salesforce-connector",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.talend.job.deployer.salesforce.configuration.SalesforceTalendJobConfiguration",
	localization = "content/Language",
	name = "salesforce-talend-job-deployer-configuration-name"
)
public interface SalesforceTalendJobConfiguration {

	@Meta.AD(deflt = "5000", name = "cache-size", required = false)
	public int cacheSize();

	@Meta.AD(
		deflt = "META-INF/", name = "salesforce-talend-job-file-path",
		required = false
	)
	public String salesforceTalendJobFilePath();

}