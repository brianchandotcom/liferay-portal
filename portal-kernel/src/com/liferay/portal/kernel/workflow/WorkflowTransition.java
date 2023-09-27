/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public interface WorkflowTransition {

	public String getLabel(Locale locale);

	public Map<Locale, String> getLabelMap();

	public String getName();

	public String getSourceNodeName();

	public String getTargetNodeName();

}