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
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const RATING_TYPE = 'thumbs';
const SCORE_UP = 1;
const SCORE_DOWN = 0;
const SCORE_UNVOTE = -1;

const RatingsThumbs = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	initialNegativeVotes = 0,
	initialPositiveVotes = 0,
	signedIn,
	thumbDown = false,
	thumbUp = false,
	url,
}) => {
	const [pressed, setPressed] = useState(
		thumbDown ? 'down' : thumbUp ? 'up' : null
	);
	const [positiveVotes, setPositiveVotes] = useState(initialPositiveVotes);
	const [negativeVotes, setNegativeVotes] = useState(initialNegativeVotes);

	const voteUp = () => {
		if (pressed === 'down') {
			setNegativeVotes(negativeVotes - 1);
		}

		if (pressed === 'up') {
			setPositiveVotes(positiveVotes - 1);
			setPressed(null);
		}
		else {
			setPressed('up');
			setPositiveVotes(positiveVotes + 1);
		}

		const score = pressed !== 'up' ? SCORE_UP : SCORE_UNVOTE;
		sendVoteRequest(score);
	};

	const voteDown = () => {
		if (pressed === 'up') {
			setPositiveVotes(positiveVotes - 1);
		}

		if (pressed === 'down') {
			setNegativeVotes(negativeVotes - 1);
			setPressed(null);
		}
		else {
			setNegativeVotes(negativeVotes + 1);
			setPressed('down');
		}

		const score = pressed !== 'down' ? SCORE_DOWN : SCORE_UNVOTE;
		sendVoteRequest(score);
	};

	const getTitle = () => {
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

		return '';
	};

	const sendVoteRequest = score => {
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
			.then(({totalEntries, totalScore}) => {
				setNegativeVotes(totalEntries - totalScore);
				setPositiveVotes(totalScore);
			});
	};

	return (
		<div className="ratings-thumbs">
			<ClayButton
				aria-pressed={pressed === 'up'}
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={voteUp}
				small
				title={getTitle()}
				value={positiveVotes}
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="thumbs-up" />
				</span>
				{positiveVotes}
			</ClayButton>
			<ClayButton
				aria-pressed={pressed === 'down'}
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={voteDown}
				small
				title={getTitle()}
				value={negativeVotes}
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="thumbs-down" />
				</span>
				{negativeVotes}
			</ClayButton>
		</div>
	);
};

RatingsThumbs.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	signedIn: PropTypes.bool.isRequired,
	thumbDown: PropTypes.bool,
	thumbUp: PropTypes.bool,
	url: PropTypes.string.isRequired,
};

export default function(props) {
	return <RatingsThumbs {...props} />;
}
