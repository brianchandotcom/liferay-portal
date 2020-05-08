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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import isNumber from '../utils/isNumber';
import Lang from '../utils/lang';

const SCORE_UNVOTE = -1;

const RatingsStars = ({
	disabled = true,
	initialAverageScore = 0,
	initialTotalEntries = 0,
	inititalTitle,
	numberOfStars,
	sendVoteRequest,
	userScore,
}) => {
	const starScores = Array.from(Array(numberOfStars)).map((_, index) => {
		const number = index + 1;

		return {
			label: number,
			value: (1 / numberOfStars) * number,
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
		(averageScore) => (averageScore * numberOfStars).toFixed(1),
		[numberOfStars]
	);

	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(getLabelScore(userScore));
	const [averageScore, setAverageScore] = useState(
		formatAverageScore(initialAverageScore)
	);
	const [totalEntries, setTotalEntries] = useState(initialTotalEntries);
	const isMounted = useIsMounted();

	const handleOnClick = (index) => {
		let value, label;

		if (index) {
			const starScore = starScores[index];

			value = starScore.value;
			label = starScore.label;
		}
		else {
			value = SCORE_UNVOTE;
			label = getLabelScore(SCORE_UNVOTE);
		}

		setScore(label);
		handleSendVoteRequest(value);
		setIsDropdownOpen(false);
	};

	const handleSendVoteRequest = useCallback(
		(score) => {
			sendVoteRequest(score).then(
				({averageScore, score, totalEntries} = {}) => {
					if (
						isMounted() &&
						isNumber(averageScore) &&
						isNumber(score) &&
						isNumber(totalEntries)
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
			averageScore === 1.0
				? Liferay.Language.get('the-average-rating-is-x-star-out-of-x')
				: Liferay.Language.get(
						'the-average-rating-is-x-stars-out-of-x'
				  );

		return Lang.sub(srAverageMessage, [averageScore, numberOfStars]);
	};

	return (
		<div className="autofit-padded-no-gutters autofit-row autofit-row-center ratings ratings-stars">
			<div className="autofit-col">
				<ClayDropDown
					active={isDropdownOpen}
					menuElementAttrs={{
						className: 'ratings-stars-dropdown',
					}}
					onActiveChange={(isActive) => setIsDropdownOpen(isActive)}
					trigger={
						<ClayButton
							aria-pressed={!!score}
							borderless
							className="ratings-stars-dropdown-toggle"
							disabled={disabled}
							displayType="secondary"
							small
							title={getTitle()}
							value={score}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={score ? 'star' : 'star-o'} />
							</span>
							<span className="inline-item ratings-stars-button-text">
								{score || '-'}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{starScores.map(({label}, index) => {
							const srMessage =
								index === 0
									? Liferay.Language.get(
											'rate-this-x-star-out-of-x'
									  )
									: Liferay.Language.get(
											'rate-this-x-stars-out-of-x'
									  );

							return (
								<ClayDropDown.Item
									active={label === score}
									key={index}
									onClick={() => {
										handleOnClick(index);
									}}
								>
									{label}
									<span className="sr-only">
										{Lang.sub(srMessage, [
											index + 1,
											numberOfStars,
										])}
									</span>
								</ClayDropDown.Item>
							);
						})}

						<ClayDropDown.Item
							disabled={score === 0}
							onClick={() => {
								handleOnClick();
							}}
						>
							{Liferay.Language.get('delete')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>
			<div className="autofit-col">
				<span className="ratings-stars-average">
					<span className="inline-item inline-item-before">
						<ClayIcon
							className="ratings-stars-average-icon"
							symbol="star"
						/>
					</span>
					<span className="inline-item ratings-stars-average-text">
						{averageScore}
						{!!totalEntries &&
							` (${totalEntries} ${
								totalEntries === 1
									? Liferay.Language.get('vote')
									: Liferay.Language.get('votes')
							})`}
					</span>
					<span className="sr-only">{getSrAverageMessage()}</span>
				</span>
			</div>
		</div>
	);
};

RatingsStars.propTypes = {
	disabled: PropTypes.bool,
	initialAverageScore: PropTypes.number,
	initialTotalEntries: PropTypes.number,
	inititalTitle: PropTypes.string,
	numberOfStars: PropTypes.number.isRequired,
	positiveVotes: PropTypes.number,
	sendVoteRequest: PropTypes.func.isRequired,
	userScore: PropTypes.number,
};

export default RatingsStars;
