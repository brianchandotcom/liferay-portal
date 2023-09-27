/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.significance;

import com.liferay.portal.search.script.Script;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 */
@ProviderType
public interface SignificanceHeuristics {

	public ChiSquareSignificanceHeuristic chiSquare(
		boolean backgroundIsSuperset, boolean includeNegatives);

	public GNDSignificanceHeuristic gnd(boolean backgroundIsSuperset);

	public JLHScoreSignificanceHeuristic jlhScore();

	public MutualInformationSignificanceHeuristic mutualInformation(
		boolean backgroundIsSuperset, boolean includeNegatives);

	public PercentageScoreSignificanceHeuristic percentageScore();

	public ScriptSignificanceHeuristic script(Script script);

}