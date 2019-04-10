/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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