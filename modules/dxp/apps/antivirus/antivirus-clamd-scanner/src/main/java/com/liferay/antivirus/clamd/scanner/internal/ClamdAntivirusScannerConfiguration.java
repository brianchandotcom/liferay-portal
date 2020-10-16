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

package com.liferay.antivirus.clamd.scanner.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Shuyang Zhou
 */
@ExtendedObjectClassDefinition(category = "antivirus")
@Meta.OCD(
	id = "com.liferay.antivirus.clamd.scanner.internal.ClamdAntivirusScannerConfiguration",
	localization = "content/Language",
	name = "antivirus-clamd-scanner-configuration-name"
)
public interface ClamdAntivirusScannerConfiguration {

	@Meta.AD(deflt = "", name = "hostname")
	public String hostname();

	@Meta.AD(deflt = "3310", name = "port", required = false)
	public int port();

	@Meta.AD(deflt = "10000", name = "timeout", required = false)
	public int timeout();

}