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

import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {createVoteMessage, createVoteThread} from '../utils/client.es';
import {normalize, normalizeRating} from "../utils/utils.es";

export default ({aggregateRating, entityId, myRating, ratingChange, type}) => {
	const [userRating, setUserRating] = useState(0);
	const [rating, setRating] = useState(0);

	useEffect(() => {
		setRating(
			normalizeRating(aggregateRating)
		);
	}, [aggregateRating]);

	useEffect(() => {
		setUserRating(myRating === null ? 0 : normalize(myRating));
	}, [myRating]);

	const voteChange = value => {
		if (userRating === value) {
			return;
		}

		const newUserRating = userRating + value;
		const normalizedValue = (newUserRating + 1) / 2;
		const votePromise =
			type === 'Thread'
				? createVoteThread(entityId, normalizedValue)
				: createVoteMessage(entityId, normalizedValue);

		votePromise.then(({ratingValue}) => {
			const denormalizedValue = normalize(ratingValue);

			const newRating = rating - userRating + denormalizedValue;

			setRating(newRating);
			setUserRating(newUserRating);

			if (ratingChange) {
				ratingChange(newRating);
			}
		});
	};

	return (
		<div className="autofit-col text-center">
			<ClayIcon
				className={userRating === 1 ? 'questions-my-rating' : ''}
				onClick={() => voteChange(1)}
				symbol="caret-top"
			/>

			<span>{rating || 0}</span>

			<ClayIcon
				className={userRating === -1 ? 'questions-my-rating' : ''}
				onClick={() => voteChange(-1)}
				symbol="caret-bottom"
			/>
		</div>
	);
};
