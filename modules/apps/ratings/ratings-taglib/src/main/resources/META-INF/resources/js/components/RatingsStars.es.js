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
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import Lang from '../utils/lang.es';

const STAR_SCORES = [
	{label: 1, value: 0.2},
	{label: 2, value: 0.4},
	{label: 3, value: 0.6},
	{label: 4, value: 0.8},
	{label: 5, value: 1},
];

const getLabelScore = score => {
	const startScore = STAR_SCORES.find(({value}) => score === value);

	return (startScore && startScore.label) || 0;
};

const formatAverageScore = averageScore =>
	(averageScore * STAR_SCORES.length).toFixed(1);

const RATING_TYPE = 'stars';

const RatingsStars = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	initialAverageScore = 0,
	initialTotalEntries = 0,
	signedIn,
	url,
	userScore,
}) => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(getLabelScore(userScore));
	const [averageScore, setAverageScore] = useState(
		formatAverageScore(initialAverageScore)
	);
	const [totalEntries, setTotalEntries] = useState(initialTotalEntries);

	const handleOnClick = index => {
		const {label, value} = STAR_SCORES[index];

		setScore(label);
		sendVoteRequest(value);
		setIsDropdownOpen(false);
	};

	const sendVoteRequest = useCallback(
		score => {
			Liferay.fire('ratings:vote', {
				className,
				classPK,
				ratingType: RATING_TYPE,
				score,
			});

			const body = objectToFormData({
				className,
				classPK,
				p_auth: Liferay.authToken,
				p_l_id: themeDisplay.getPlid(),
				score,
			});

			fetch(url, {
				body,
				method: 'POST',
			})
				.then(response => response.json())
				.then(response => {
					setTotalEntries(response.totalEntries);
					setAverageScore(formatAverageScore(response.averageScore));
					setScore(getLabelScore(response.score));
				});
		},
		[className, classPK, url]
	);

	const getTitle = useCallback(() => {
		if (!signedIn) {
			return '';
		}

		if (inTrash) {
			return Liferay.Language.get(
				'ratings-are-disabled-because-this-entry-is-in-the-recycle-bin'
			);
		}
		else if (!enabled) {
			return Liferay.Language.get('ratings-are-disabled-in-staging');
		}
		else if (score <= 0) {
			return Liferay.Language.get('vote');
		}
		else if (score > 0) {
			const title =
				score === 0
					? Liferay.Language.get(
							'you-have-rated-this-x-star-out-of-x'
					  )
					: Liferay.Language.get(
							'you-have-rated-this-x-stars-out-of-x'
					  );

			return Lang.sub(title, [score, STAR_SCORES.length]);
		}

		return '';
	}, [signedIn, inTrash, enabled, score]);

	return (
		<div className="autofit-row autofit-row-center ratings ratings-stars">
			<div className="autofit-col">
				<ClayDropDown
					active={isDropdownOpen}
					menuElementAttrs={{
						className: 'ratings-stars-dropdown',
					}}
					onActiveChange={isActive => setIsDropdownOpen(isActive)}
					trigger={
						<ClayButton
							aria-pressed={!!score}
							borderless
							disabled={!signedIn || !enabled}
							displayType="secondary"
							small
							title={getTitle()}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={score ? 'star' : 'star-o'} />
							</span>
							<span className="inline-item">{score || '-'}</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{STAR_SCORES.map(({label}, index) => (
							<ClayDropDown.Item
								active={label === score}
								key={index}
								onClick={() => {
									handleOnClick(index);
								}}
							>
								{label}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>
			<div className="autofit-col">
				<ClayIcon
					className="ratings-stars-average-icon"
					symbol="star"
				/>
			</div>
			<div className="autofit-col">
				<span className="ratings-stars-average-text">
					{averageScore}
					{!!totalEntries &&
						` (${totalEntries} ${
							totalEntries === 1
								? Liferay.Language.get('vote')
								: Liferay.Language.get('votes')
						})`}
				</span>
			</div>
		</div>
	);
};

RatingsStars.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	initialAverageScore: PropTypes.number,
	initialTotalEntries: PropTypes.number,
	round: PropTypes.bool,
	signedIn: PropTypes.bool.isRequired,
	url: PropTypes.string.isRequired,
	userScore: PropTypes.number,
};

export default RatingsStars;
