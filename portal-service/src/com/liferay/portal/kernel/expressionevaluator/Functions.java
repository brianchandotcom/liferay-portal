
package com.liferay.portal.kernel.expressionevaluator;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class Functions {

	public static double sum(Double... args) {

		Double sum = 0.0;

		for (Double number : args) {
			sum += number;
		}

		return sum;
	}

	public static long sum(Long... args) {

		Long sum = 0L;

		for (Long number : args) {
			sum += number;
		}

		return sum;
	}

}