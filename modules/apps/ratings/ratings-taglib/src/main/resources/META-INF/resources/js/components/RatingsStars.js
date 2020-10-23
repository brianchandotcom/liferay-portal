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

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import {useCallback, useState} from 'react';

import TYPES from '../RATINGS_TYPES';
import Lang from '../utils/lang';
import RatingsSelectStars from './RatingsSelectStars';
import RatingsStackedStars from './RatingsStackedStars';

const SCORE_UNVOTE = -1;

const RatingsStars = ({
	disabled = true,
	initialAverageScore = 0,
	initialTotalEntries = 0,
	inititalTitle,
	numberOfStars,
	randomNamespace,
	sendVoteRequest,
	type,
	userScore,
}) => {
	const starScores = Array.from(Array(numberOfStars)).map((_, index) => {
		const number = index + 1;

		return {
			label: number,
			value: number / numberOfStars,
		};
	});

	const getLabelScore = useCallback(
		(score) => {
			const starScore = starScores.find(({value}) => score === value);

			return (starScore && starScore.label) || 0;
		},
		[starScores]
	);

	const formatAverageScore = useCallback(
		(averageScore) => Number((averageScore * numberOfStars).toFixed(1)),
		[numberOfStars]
	);

	const [score, setScore] = useState(getLabelScore(userScore));
	const [averageScore, setAverageScore] = useState(
		formatAverageScore(initialAverageScore)
	);
	const [totalEntries, setTotalEntries] = useState(initialTotalEntries);
	const isMounted = useIsMounted();

	const handleSendVoteRequest = useCallback(
		(score) => {
			sendVoteRequest(score).then(
				({averageScore, score, totalEntries} = {}) => {
					if (
						isMounted() &&
						averageScore !== undefined &&
						score !== undefined &&
						totalEntries !== undefined
					) {
						setTotalEntries(totalEntries);
						setAverageScore(formatAverageScore(averageScore));
						setScore(getLabelScore(score));
					}
				}
			);
		},
		[formatAverageScore, getLabelScore, isMounted, sendVoteRequest]
	);

	const handleVote = (index) => {
		let value, label;
		const starScore = starScores[index];

		if (starScore) {
			value = starScore.value;
			label = starScore.label;
		}
		else {
			value = SCORE_UNVOTE;
			label = getLabelScore(SCORE_UNVOTE);
		}

		setScore(label);
		handleSendVoteRequest(value);
	};

	const getTitle = useCallback(() => {
		if (inititalTitle !== undefined) {
			return inititalTitle;
		}
		else if (score <= 0) {
			return Liferay.Language.get('vote');
		}
		else if (score > 0) {
			const title =
				score === 1
					? Liferay.Language.get(
							'you-have-rated-this-x-star-out-of-x'
					  )
					: Liferay.Language.get(
							'you-have-rated-this-x-stars-out-of-x'
					  );

			return Lang.sub(title, [score, numberOfStars]);
		}

		return '';
	}, [inititalTitle, score, numberOfStars]);

	const getSrAverageMessage = () => {
		const srAverageMessage =
			averageScore === 1
				? Liferay.Language.get('the-average-rating-is-x-star-out-of-x')
				: Liferay.Language.get(
						'the-average-rating-is-x-stars-out-of-x'
				  );

		return Lang.sub(srAverageMessage, [averageScore, numberOfStars]);
	};

	const RatingsStarsTypes = {
		[TYPES.STACKED_STARS]: RatingsStackedStars,
		[TYPES.STARS]: RatingsSelectStars,
	};

	const RatingsStarsUI = RatingsStarsTypes[type];

	return RatingsStarsUI({
		averageScore,
		disabled,
		getSrAverageMessage,
		getTitle,
		numberOfStars,
		onVote: handleVote,
		randomNamespace,
		score,
		starScores,
		totalEntries,
	});
};

RatingsStars.propTypes = {
	disabled: PropTypes.bool,
	initialAverageScore: PropTypes.number,
	initialTotalEntries: PropTypes.number,
	inititalTitle: PropTypes.string,
	numberOfStars: PropTypes.number.isRequired,
	positiveVotes: PropTypes.number,
	randomNamespace: PropTypes.string.isRequired,
	sendVoteRequest: PropTypes.func.isRequired,
	userScore: PropTypes.number,
};

export default RatingsStars;
