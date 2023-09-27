/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.significance;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.significance.ChiSquareSignificanceHeuristic;
import com.liferay.portal.search.significance.GNDSignificanceHeuristic;
import com.liferay.portal.search.significance.JLHScoreSignificanceHeuristic;
import com.liferay.portal.search.significance.MutualInformationSignificanceHeuristic;
import com.liferay.portal.search.significance.PercentageScoreSignificanceHeuristic;
import com.liferay.portal.search.significance.ScriptSignificanceHeuristic;
import com.liferay.portal.search.significance.SignificanceHeuristics;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(service = SignificanceHeuristics.class)
public class SignificanceHeuristicsImpl implements SignificanceHeuristics {

	@Override
	public ChiSquareSignificanceHeuristic chiSquare(
		boolean backgroundIsSuperset, boolean includeNegatives) {

		return new ChiSquareSignificanceHeuristicImpl(
			backgroundIsSuperset, includeNegatives);
	}

	@Override
	public GNDSignificanceHeuristic gnd(boolean backgroundIsSuperset) {
		return new GNDSignificanceHeuristicImpl(backgroundIsSuperset);
	}

	@Override
	public JLHScoreSignificanceHeuristic jlhScore() {
		return new JLHScoreSignificanceHeuristicImpl();
	}

	@Override
	public MutualInformationSignificanceHeuristic mutualInformation(
		boolean backgroundIsSuperset, boolean includeNegatives) {

		return new MutualInformationSignificanceHeuristicImpl(
			backgroundIsSuperset, includeNegatives);
	}

	@Override
	public PercentageScoreSignificanceHeuristic percentageScore() {
		return new PercentageScoreSignificanceHeuristicImpl();
	}

	@Override
	public ScriptSignificanceHeuristic script(Script script) {
		return new ScriptSignificanceHeuristicImpl(script);
	}

}