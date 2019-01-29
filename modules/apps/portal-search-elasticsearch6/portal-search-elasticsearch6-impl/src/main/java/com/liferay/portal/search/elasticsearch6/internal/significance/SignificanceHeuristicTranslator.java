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

package com.liferay.portal.search.elasticsearch6.internal.significance;

import com.liferay.portal.search.elasticsearch6.internal.script.ScriptTranslator;
import com.liferay.portal.search.significance.ChiSquareSignifanceHeuristic;
import com.liferay.portal.search.significance.GNDSignificanceHeuristic;
import com.liferay.portal.search.significance.JLHScoreSignifanceHeuristic;
import com.liferay.portal.search.significance.MutualInformationSignifanceHeuristic;
import com.liferay.portal.search.significance.PercentageScoreSignifanceHeuristic;
import com.liferay.portal.search.significance.ScriptSignifanceHeuristic;
import com.liferay.portal.search.significance.SignificanceHeuristic;

import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.ChiSquare;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.GND;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.JLHScore;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.MutualInformation;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.PercentageScore;
import org.elasticsearch.search.aggregations.bucket.significant.heuristics.ScriptHeuristic;

/**
 * @author Michael C. Han
 */
public class SignificanceHeuristicTranslator {

	public org.elasticsearch.search.aggregations.bucket.significant.heuristics.
		SignificanceHeuristic translate(
			SignificanceHeuristic significanceHeuristic) {

		if (significanceHeuristic instanceof ChiSquareSignifanceHeuristic) {
			ChiSquareSignifanceHeuristic chiSquareSignifanceHeuristic =
				(ChiSquareSignifanceHeuristic)significanceHeuristic;

			return new ChiSquare(
				chiSquareSignifanceHeuristic.isIncludeNegatives(),
				chiSquareSignifanceHeuristic.isBackgroundIsSuperset());
		}
		else if (significanceHeuristic instanceof GNDSignificanceHeuristic) {
			GNDSignificanceHeuristic gndSignificanceHeuristic =
				(GNDSignificanceHeuristic)significanceHeuristic;

			return new GND(gndSignificanceHeuristic.isBackgroundIsSuperset());
		}
		else if (significanceHeuristic instanceof JLHScoreSignifanceHeuristic) {
			return new JLHScore();
		}
		else if (significanceHeuristic instanceof
					MutualInformationSignifanceHeuristic) {

			MutualInformationSignifanceHeuristic
				mutualInformationSignifanceHeuristic =
					(MutualInformationSignifanceHeuristic)significanceHeuristic;

			return new MutualInformation(
				mutualInformationSignifanceHeuristic.isIncludeNegatives(),
				mutualInformationSignifanceHeuristic.isBackgroundIsSuperset());
		}
		else if (significanceHeuristic instanceof
					PercentageScoreSignifanceHeuristic) {

			return new PercentageScore();
		}
		else if (significanceHeuristic instanceof ScriptSignifanceHeuristic) {
			ScriptSignifanceHeuristic scriptSignifanceHeuristic =
				(ScriptSignifanceHeuristic)significanceHeuristic;

			Script script = _scriptTranslator.translate(
				scriptSignifanceHeuristic.getScript());

			return new ScriptHeuristic(script);
		}

		throw new IllegalArgumentException(
			"Invalid signficance heuristic:" + significanceHeuristic);
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}