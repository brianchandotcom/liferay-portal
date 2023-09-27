/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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