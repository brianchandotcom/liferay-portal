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

package com.liferay.portal.workflow.kaleo.forms.constants;

/**
 * Holds the Kaleo Forms request attribute keys.
 *
 * @author Marcellus Tavares
 */
public class KaleoFormsWebKeys {

	/**
	 * {@value #DYNAMIC_DATA_MAPPING_STRUCTURE} is the key to use to retrieve
	 * the DDM structure from the request attribute.
	 */
	public static final String DYNAMIC_DATA_MAPPING_STRUCTURE =
		"DYNAMIC_DATA_MAPPING_STRUCTURE";

	/**
	 * {@value #KALEO_DRAFT_DEFINITION_CONTENT} is the key to use to retrieve
	 * the content of the Kaleo draft definition from the request attribute.
	 */
	public static final String KALEO_DRAFT_DEFINITION_CONTENT =
		"KALEO_DRAFT_DEFINITION_CONTENT";

	/**
	 * {@value #KALEO_DRAFT_DEFINITION_ID} is the key to use to retrieve the
	 * primary key of the Kaleo draft definition from the request attribute.
	 */
	public static final String KALEO_DRAFT_DEFINITION_ID =
		"KALEO_DRAFT_DEFINITION_ID";

	/**
	 * {@value #KALEO_PROCESS} is the key to use to retrieve the Kaleo process
	 * from the request attribute.
	 */
	public static final String KALEO_PROCESS = "KALEO_PROCESS";

	/**
	 * {@value #KALEO_PROCESS_LINK} is the key to use to retrieve the Kaleo
	 * process link from the request attribute.
	 */
	public static final String KALEO_PROCESS_LINK = "KALEO_PROCESS_LINK";

	/**
	 * {@value #WORKFLOW_INSTANCE} is the key to use to retrieve the workflow
	 * instance from the request attribute.
	 */
	public static final String WORKFLOW_INSTANCE = "WORKFLOW_INSTANCE";

	/**
	 * {@value #WORKFLOW_TASK} is the key to use to retrieve the workflow task
	 * from the request attribute.
	 */
	public static final String WORKFLOW_TASK = "WORKFLOW_TASK";

}