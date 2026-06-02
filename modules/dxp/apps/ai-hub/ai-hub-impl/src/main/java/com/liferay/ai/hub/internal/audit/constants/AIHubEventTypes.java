/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.audit.constants;

/**
 * @author Feliphe Marinho
 * @author Pedro Leite
 */
public interface AIHubEventTypes {

	public static final String AI_HUB_GUARDRAIL_VIOLATION =
		"AI_HUB_GUARDRAIL_VIOLATION";

	public static final String AI_HUB_RAG_CONTENT_RETRIEVE =
		"AI_HUB_RAG_CONTENT_RETRIEVE";

	public static final String AI_HUB_WORKFLOW_DEFINITION_ADD =
		"AI_HUB_WORKFLOW_DEFINITION_ADD";

	public static final String AI_HUB_WORKFLOW_DEFINITION_DELETE =
		"AI_HUB_WORKFLOW_DEFINITION_DELETE";

	public static final String AI_HUB_WORKFLOW_DEFINITION_UPDATE =
		"AI_HUB_WORKFLOW_DEFINITION_UPDATE";

}