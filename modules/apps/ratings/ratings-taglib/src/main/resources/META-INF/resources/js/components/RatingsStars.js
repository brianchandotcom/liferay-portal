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
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {Fragment, useCallback, useState} from 'react';

import {TYPES} from '../constants';
import Lang from '../utils/lang';

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

	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(getLabelScore(userScore));
	const [averageScore, setAverageScore] = useState(
		formatAverageScore(initialAverageScore)
	);
	const [totalEntries, setTotalEntries] = useState(initialTotalEntries);
	const isMounted = useIsMounted();

	const handleOnClick = (index) => {
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
		setIsDropdownOpen(false);
	};

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

	return type !== TYPES.STACKED_STARS ? (
		<ClayLayout.ContentRow
			className="ratings ratings-stars"
			noGutters
			verticalAlign="center"
		>
			<ClayLayout.ContentCol>
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
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol>
				<span className="ratings-stars-average">
					<span className="inline-item inline-item-before">
						<ClayIcon
							className="ratings-stars-average-icon"
							symbol="star"
						/>
					</span>
					<span className="inline-item ratings-stars-average-text">
						{averageScore.toFixed(1)}
						{!!totalEntries &&
							` (${totalEntries} ${
								totalEntries === 1
									? Liferay.Language.get('vote')
									: Liferay.Language.get('votes')
							})`}
					</span>
					<span className="sr-only">{getSrAverageMessage()}</span>
				</span>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	) : (
		<fieldset className="rating ratings-stacked-stars ratings-stars">
			<div
				className="ratings-stars-average"
				title={averageScore.toFixed(1)}
			>
				<span className="inline-item inline-item-before">
					{starScores.map(({label: score}, index, arr) => {
						const previousScore =
							arr[index - 1] && arr[index - 1].label;
						let symbol = 'star-o';

						if (averageScore >= score) {
							symbol = 'star';
						}
						else if (averageScore > previousScore) {
							symbol = 'star-half';
						}

						return (
							<ClayIcon
								className="ratings-stars-average-icon"
								key={score}
								symbol={symbol}
							/>
						);
					})}
				</span>
				<span className="inline-item ratings-stars-average-text">
					{!!totalEntries &&
						` (${totalEntries} ${
							totalEntries === 1
								? Liferay.Language.get('vote')
								: Liferay.Language.get('votes')
						})`}
				</span>
				<span className="sr-only">{getSrAverageMessage()}</span>
			</div>

			<div
				className={classNames({disabled}, 'ratings-stacked-stars-vote')}
			>
				<div className="ratings-stacked-stars-vote-stars">
					{starScores.reverse().map(({label, value}, index) => {
						const id = `${randomNamespace}star${label}`;
						const srMessage =
							index === 0
								? Liferay.Language.get(
										'rate-this-x-star-out-of-x'
								  )
								: Liferay.Language.get(
										'rate-this-x-stars-out-of-x'
								  );
						const full = label <= score;

						return (
							<Fragment key={value}>
								<input
									checked={label === score}
									className="sr-only"
									disabled={disabled}
									id={id}
									name={`${randomNamespace}rating`}
									onChange={() => {
										handleOnClick(index);
									}}
									type="radio"
									value={value}
								/>
								<label
									className={
										full ? 'ratings-stars-star-full' : ''
									}
									htmlFor={id}
								>
									<ClayIcon
										className="ratings-stars-icon-full"
										symbol={'star'}
									/>
									<ClayIcon
										className="ratings-stars-icon-empty"
										symbol={'star-o'}
									/>
									<span className="sr-only">
										{Lang.sub(srMessage, [
											label,
											numberOfStars,
										])}
									</span>
								</label>
							</Fragment>
						);
					})}
				</div>

				{score !== 0 && (
					<ClayButton
						className="lfr-portal-tooltip ratings-stacked-stars-delete"
						disabled={disabled}
						displayType="unstyled"
						onClick={() => {
							handleOnClick();
						}}
						title={Liferay.Language.get('delete')}
					>
						<ClayIcon symbol="times-circle" />
					</ClayButton>
				)}
			</div>
		</fieldset>
	);
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
