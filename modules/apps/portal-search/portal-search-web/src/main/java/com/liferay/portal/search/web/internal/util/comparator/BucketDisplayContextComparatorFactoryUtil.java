/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.util.comparator;

import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;

import java.util.Comparator;

/**
 * @author Bryan Engler
 */
public class BucketDisplayContextComparatorFactoryUtil {

	public static Comparator<BucketDisplayContext>
		getBucketDisplayContextComparator(String order) {

		if (order.equals("count:asc")) {
			return _COMPARATOR_FREQUENCY_ASC;
		}
		else if (order.equals("count:desc")) {
			return _COMPARATOR_FREQUENCY_DESC;
		}
		else if (order.equals("key:asc")) {
			return _COMPARATOR_BUCKET_TEXT_ASC;
		}
		else if (order.equals("key:desc")) {
			return _COMPARATOR_BUCKET_TEXT_DESC;
		}

		throw new IllegalArgumentException("Invalid order: " + order);
	}

	private static int _compareBucketText(
		String bucketText1, String bucketText2) {

		return bucketText1.compareTo(bucketText2);
	}

	private static final Comparator<BucketDisplayContext>
		_COMPARATOR_BUCKET_TEXT_ASC = new Comparator<BucketDisplayContext>() {

			@Override
			public int compare(
				BucketDisplayContext bucketDisplayContext1,
				BucketDisplayContext bucketDisplayContext2) {

				int result = _compareBucketText(
					bucketDisplayContext1.getBucketText(),
					bucketDisplayContext2.getBucketText());

				if (result == 0) {
					return bucketDisplayContext2.getFrequency() -
						bucketDisplayContext1.getFrequency();
				}

				return result;
			}

		};

	private static final Comparator<BucketDisplayContext>
		_COMPARATOR_BUCKET_TEXT_DESC = new Comparator<BucketDisplayContext>() {

			@Override
			public int compare(
				BucketDisplayContext bucketDisplayContext1,
				BucketDisplayContext bucketDisplayContext2) {

				int result = _compareBucketText(
					bucketDisplayContext2.getBucketText(),
					bucketDisplayContext1.getBucketText());

				if (result == 0) {
					return bucketDisplayContext2.getFrequency() -
						bucketDisplayContext1.getFrequency();
				}

				return result;
			}

		};

	private static final Comparator<BucketDisplayContext>
		_COMPARATOR_FREQUENCY_ASC = new Comparator<BucketDisplayContext>() {

			public int compare(
				BucketDisplayContext bucketDisplayContext1,
				BucketDisplayContext bucketDisplayContext2) {

				int result =
					bucketDisplayContext1.getFrequency() -
						bucketDisplayContext2.getFrequency();

				if (result == 0) {
					return _compareBucketText(
						bucketDisplayContext1.getBucketText(),
						bucketDisplayContext2.getBucketText());
				}

				return result;
			}

		};

	private static final Comparator<BucketDisplayContext>
		_COMPARATOR_FREQUENCY_DESC = new Comparator<BucketDisplayContext>() {

			@Override
			public int compare(
				BucketDisplayContext bucketDisplayContext1,
				BucketDisplayContext bucketDisplayContext2) {

				int result =
					bucketDisplayContext2.getFrequency() -
						bucketDisplayContext1.getFrequency();

				if (result == 0) {
					return _compareBucketText(
						bucketDisplayContext1.getBucketText(),
						bucketDisplayContext2.getBucketText());
				}

				return result;
			}

		};

}