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

const RATING_TYPE = 'stars';

const RatingsStars = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	signedIn,
	url,
	userScore,
}) => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(getLabelScore(userScore));

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
			});
			// .then(response => response.json())
		},
		[className, classPK, url]
	);

	const getTitle = useCallback(() => {
		if (inTrash) {
			return Liferay.Language.get(
				'ratings-are-disabled-because-this-entry-is-in-the-recycle-bin'
			);
		}
		else if (!enabled) {
			return Liferay.Language.get('ratings-are-disabled-in-staging');
		}

		return '';
	}, [inTrash, enabled]);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			onActiveChange={isActive => setIsDropdownOpen(isActive)}
			trigger={
				<ClayButton
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
	);
};

RatingsStars.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	signedIn: PropTypes.bool.isRequired,
	thumbDown: PropTypes.bool,
	thumbUp: PropTypes.bool,
	url: PropTypes.string.isRequired,
	userScore: PropTypes.number,
};

export default RatingsStars;
