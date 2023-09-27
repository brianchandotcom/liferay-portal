/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useMutation} from 'graphql-hooks';
import React, {useEffect, useState} from 'react';

import {
	createVoteMessageQuery,
	createVoteThreadQuery,
} from '../utils/client.es';
import {normalize, normalizeRating} from '../utils/utils.es';

export default function Rating({
	aggregateRating,
	disabled = false,
	entityId,
	myRating,
	type,
}) {
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
				aria-label={Liferay.Language.get('upvote')}
				className={userRating === 1 ? ' text-primary' : 'text-reset'}
				disabled={disabled || !Liferay.ThemeDisplay.isSignedIn()}
				displayType="unstyled"
				monospaced
				onClick={() => voteChange(1)}
			>
				<ClayIcon symbol="caret-top" />
			</ClayButton>

			<span className="c-px-2">{rating || 0}</span>

			<ClayButton
				aria-label={Liferay.Language.get('downvote')}
				className={userRating === -1 ? ' text-primary' : 'text-reset'}
				disabled={disabled || !Liferay.ThemeDisplay.isSignedIn()}
				displayType="unstyled"
				monospaced
				onClick={() => voteChange(-1)}
			>
				<ClayIcon symbol="caret-bottom" />
			</ClayButton>
		</div>
	);
}
