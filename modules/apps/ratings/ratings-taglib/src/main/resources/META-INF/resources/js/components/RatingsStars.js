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

import Lang from '../utils/lang';

const RATING_TYPE = 'stars';

const RatingsStars = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	initialAverageScore = 0,
	initialTotalEntries = 0,
	numberOfStars,
	signedIn,
	url,
	userScore,
}) => {
	const startScores = Array.from(Array(numberOfStars)).map((_, index) => {
		const number = index + 1;

		return {
			label: number,
			value: (1 / numberOfStars) * number,
		};
	});

	const getLabelScore = useCallback(
		score => {
			const startScore = startScores.find(({value}) => score === value);

			return (startScore && startScore.label) || 0;
		},
		[startScores]
	);

	const formatAverageScore = useCallback(
		averageScore => (averageScore * numberOfStars).toFixed(1),
		[numberOfStars]
	);

	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(getLabelScore(userScore));
	const [averageScore, setAverageScore] = useState(
		formatAverageScore(initialAverageScore)
	);
	const [totalEntries, setTotalEntries] = useState(initialTotalEntries);

	const handleOnClick = index => {
		const {label, value} = startScores[index];

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
				.then(({averageScore, score, totalEntries}) => {
					if (averageScore && score && totalEntries) {
						setTotalEntries(totalEntries);
						setAverageScore(formatAverageScore(averageScore));
						setScore(getLabelScore(score));
					}
				});
		},
		[className, classPK, formatAverageScore, getLabelScore, url]
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
	}, [signedIn, inTrash, enabled, score, numberOfStars]);

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
					onActiveChange={isActive => setIsDropdownOpen(isActive)}
					trigger={
						<ClayButton
							aria-pressed={!!score}
							borderless
							className="ratings-stars-dropdown-toggle"
							disabled={!signedIn || !enabled}
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
						{startScores.map(({label}, index) => {
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
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	initialAverageScore: PropTypes.number,
	initialTotalEntries: PropTypes.number,
	numberOfStars: PropTypes.number.isRequired,
	signedIn: PropTypes.bool.isRequired,
	url: PropTypes.string.isRequired,
	userScore: PropTypes.number,
};

export default RatingsStars;
