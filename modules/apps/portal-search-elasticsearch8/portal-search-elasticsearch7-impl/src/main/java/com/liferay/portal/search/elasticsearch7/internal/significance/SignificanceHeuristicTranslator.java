/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.significance;

import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
import com.liferay.portal.search.significance.ChiSquareSignificanceHeuristic;
import com.liferay.portal.search.significance.GNDSignificanceHeuristic;
import com.liferay.portal.search.significance.JLHScoreSignificanceHeuristic;
import com.liferay.portal.search.significance.MutualInformationSignificanceHeuristic;
import com.liferay.portal.search.significance.PercentageScoreSignificanceHeuristic;
import com.liferay.portal.search.significance.ScriptSignificanceHeuristic;
import com.liferay.portal.search.significance.SignificanceHeuristic;

import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.ChiSquare;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.GND;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.JLHScore;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.MutualInformation;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.PercentageScore;
import org.elasticsearch.search.aggregations.bucket.terms.heuristic.ScriptHeuristic;

/**
 * @author Michael C. Han
 */
public class SignificanceHeuristicTranslator {

	public org.elasticsearch.search.aggregations.bucket.terms.heuristic.
		SignificanceHeuristic translate(
			SignificanceHeuristic significanceHeuristic) {

		if (significanceHeuristic instanceof ChiSquareSignificanceHeuristic) {
			ChiSquareSignificanceHeuristic chiSquareSignificanceHeuristic =
				(ChiSquareSignificanceHeuristic)significanceHeuristic;

			return new ChiSquare(
				chiSquareSignificanceHeuristic.isIncludeNegatives(),
				chiSquareSignificanceHeuristic.isBackgroundIsSuperset());
		}

		if (significanceHeuristic instanceof GNDSignificanceHeuristic) {
			GNDSignificanceHeuristic gndSignificanceHeuristic =
				(GNDSignificanceHeuristic)significanceHeuristic;

			return new GND(gndSignificanceHeuristic.isBackgroundIsSuperset());
		}

		if (significanceHeuristic instanceof JLHScoreSignificanceHeuristic) {
			return new JLHScore();
		}

		if (significanceHeuristic instanceof
				MutualInformationSignificanceHeuristic) {

			MutualInformationSignificanceHeuristic
				mutualInformationSignificanceHeuristic =
					(MutualInformationSignificanceHeuristic)
						significanceHeuristic;

			return new MutualInformation(
				mutualInformationSignificanceHeuristic.isIncludeNegatives(),
				mutualInformationSignificanceHeuristic.
					isBackgroundIsSuperset());
		}

		if (significanceHeuristic instanceof
				PercentageScoreSignificanceHeuristic) {

			return new PercentageScore();
		}

		if (significanceHeuristic instanceof ScriptSignificanceHeuristic) {
			ScriptSignificanceHeuristic scriptSignificanceHeuristic =
				(ScriptSignificanceHeuristic)significanceHeuristic;

			Script script = _scriptTranslator.translate(
				scriptSignificanceHeuristic.getScript());

			return new ScriptHeuristic(script);
		}

		throw new IllegalArgumentException(
			"Invalid significance heuristic: " + significanceHeuristic);
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}