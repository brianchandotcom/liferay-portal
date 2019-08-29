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

/**
 * Recurrent function to modify a `variants` array so their `split` values sum 100.
 *
 * The current behaviour locks any changes in the `variants[editedIndex]` because
 * it's the user choice. Also it picks the previous variant in the `variants` array
 * as the next one to fix.
 *
 */
function _fixSplitArray(variants, editedIndex, indexToFix, totalSplit) {
	const nextIndexToFix =
		indexToFix === 0 ? variants.length - 1 : indexToFix - 1;

	if (editedIndex === indexToFix) {
		return _fixSplitArray(
			variants,
			editedIndex,
			nextIndexToFix,
			totalSplit
		);
	}

	if (totalSplit > 100) {
		const correctedSplit = variants[indexToFix].split - (totalSplit - 100);

		if (correctedSplit < 0) {
			variants[indexToFix] = {
				...variants[indexToFix],
				split: 0
			};

			return _fixSplitArray(
				variants,
				editedIndex,
				nextIndexToFix,
				100 - (correctedSplit - variants[indexToFix].split)
			);
		} else {
			variants[indexToFix] = {
				...variants[indexToFix],
				split: correctedSplit
			};
		}
	}

	if (totalSplit < 100) {
		const correctedSplit = variants[indexToFix].split + (100 - totalSplit);

		if (correctedSplit > 100) {
			variants[indexToFix] = {
				...variants[indexToFix],
				split: 100
			};

			return _fixSplitArray(
				variants,
				editedIndex,
				nextIndexToFix,
				totalSplit - correctedSplit
			);
		} else {
			variants[indexToFix] = {
				...variants[indexToFix],
				split: correctedSplit
			};
		}
	}

	return variants;
}

/**
 * The action to change a variant `split` value in a `variants` array.
 *
 * It updates the `variant.split` value and gathers some information to
 * make ajustments to the `split` values of the rest of the `variants`.
 */
function changeSplitValue(splitVariants, variantId, value) {
	const newSplitVariants = [];
	let totalSplit = 0;
	const lastIndex = splitVariants.length - 1;
	let editedIndex = null;

	for (let i = 0; i < splitVariants.length; i++) {
		const variant = splitVariants[i];

		if (variantId === variant.segmentsExperimentRelId) {
			newSplitVariants.push({...variant, split: value});
			totalSplit += value;
			editedIndex = i;
		} else {
			newSplitVariants.push(variant);
			totalSplit += variant.split;
		}
	}

	const definetiveSplitVariants = _fixSplitArray(
		newSplitVariants,
		editedIndex,
		lastIndex,
		totalSplit
	);

	return definetiveSplitVariants;
}

export {changeSplitValue};
