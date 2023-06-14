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

package com.liferay.antivirus.async.store.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(category = "antivirus")
@Meta.OCD(
	id = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	localization = "content/Language",
	name = "async-antivirus-configuration-name"
)
public interface AntivirusAsyncConfiguration {

	@Meta.AD(
		deflt = "0 0 23 * * ?", description = "batch-scan-cron-expression-help",
		name = "batch-scan-cron-expression", required = false
	)
	public String batchScanCronExpression();

	@Meta.AD(
		description = "maximum-queue-size-help", name = "maximum-queue-size",
		required = false
	)
	public int maximumQueueSize();

	@Meta.AD(
		deflt = "0 0/5 * * * ?", description = "retry-cron-expression-help",
		name = "retry-cron-expression", required = false
	)
	public String retryCronExpression();

}