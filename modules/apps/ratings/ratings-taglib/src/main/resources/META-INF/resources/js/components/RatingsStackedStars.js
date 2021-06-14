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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {Fragment} from 'react';

import Lang from '../utils/lang';

export default function RatingsStackedStars({
	averageScore,
	disabled,
	getSrAverageMessage,
	getTitle,
	numberOfStars,
	onVote,
	randomNamespace,
	score,
	starScores,
	totalEntries,
}) {
	return (
		<div className="ratings-stacked-stars ratings-stars">
			<div
				className="ratings-stars-average"
				title={averageScore.toFixed(1)}
			>
				<span className="inline-item inline-item-before">
					{starScores.map(({label: score}, index, array) => {
						const previousScore =
							array[index - 1] && array[index - 1].label;
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
				<fieldset title={getTitle()}>
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
											onVote(index);
										}}
										type="radio"
										value={value}
									/>
									<label
										className={
											full
												? 'ratings-stars-star-full'
												: ''
										}
										htmlFor={id}
									>
										<ClayIcon
											className="ratings-stars-icon-full"
											symbol="star"
										/>
										<ClayIcon
											className="ratings-stars-icon-empty"
											symbol="star-o"
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
				</fieldset>

				{score !== 0 && (
					<ClayButton
						className="ratings-stacked-stars-delete"
						disabled={disabled}
						displayType="unstyled"
						onClick={onVote}
						title={Liferay.Language.get('delete')}
					>
						<ClayIcon
							className="lexicon-icon-vertical-align"
							symbol="times-circle"
						/>
					</ClayButton>
				)}
			</div>
		</div>
	);
}
