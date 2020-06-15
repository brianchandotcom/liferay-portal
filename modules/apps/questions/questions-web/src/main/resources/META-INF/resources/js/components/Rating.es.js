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

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {
	createVoteMessageQuery,
	createVoteThreadQuery,
} from '../utils/client.es';
import {normalize, normalizeRating} from '../utils/utils.es';

export default ({aggregateRating, entityId, myRating, type}) => {
	const [userRating, setUserRating] = useState(0);
	const [rating, setRating] = useState(0);

	useEffect(() => {
		setRating(normalizeRating(aggregateRating));
	}, [aggregateRating]);

	useEffect(() => {
		setUserRating(myRating === null ? 0 : normalize(myRating));
	}, [myRating]);

	const [createVoteMessage] = useMutation(createVoteMessageQuery);
	const [createVoteThread] = useMutation(createVoteThreadQuery);

	const voteChange = (value) => {
		if (userRating === value) {
			return;
		}

		const newUserRating = userRating + value;
		const normalizedValue = (userRating + value + 1) / 2;

		setUserRating(newUserRating);
		setRating(rating - userRating + newUserRating);

		if (type === 'Thread') {
			createVoteThread({
				variables: {
					messageBoardThreadId: entityId,
					ratingValue: normalizedValue,
				},
			});
		}
		else {
			createVoteMessage({
				variables: {
					messageBoardMessageId: entityId,
					ratingValue: normalizedValue,
				},
			});
		}
	};

	return (
		<div className="align-items-center d-inline-flex flex-md-column justify-content-center text-secondary">
			<ClayButton
				className={
					'text-reset' + (userRating === 1 ? ' text-primary' : '')
				}
				displayType="unstyled"
				monospaced
				onClick={() => voteChange(1)}
			>
				<ClayIcon symbol="caret-top" />
			</ClayButton>

			<span className="c-px-2">{rating || 0}</span>

			<ClayButton
				className={
					'text-reset' + (userRating === -1 ? ' text-primary' : '')
				}
				displayType="unstyled"
				monospaced
				onClick={() => voteChange(-1)}
			>
				<ClayIcon symbol="caret-bottom" />
			</ClayButton>
		</div>
	);
};
