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
import React, {useCallback, useReducer} from 'react';

import AnimatedCounter from './AnimatedCounter';

const PRESSED_DOWN = 'DOWN';
const PRESSED_UP = 'UP';
const RATING_TYPE = 'thumbs';
const SCORE_DOWN = 0;
const SCORE_UNVOTE = -1;
const SCORE_UP = 1;

const VOTE_DOWN = 'VOTE_DOWN';
const VOTE_UP = 'VOTE_UP';
const UPDATE_VOTES = 'VOTES_UPDATE';

function reducer(state, action) {
	switch (action.type) {
		case VOTE_UP:
			return {
				negativeVotes:
					state.pressed !== PRESSED_DOWN
						? state.negativeVotes
						: state.negativeVotes - 1,
				positiveVotes:
					state.pressed === PRESSED_UP
						? state.positiveVotes - 1
						: state.positiveVotes + 1,
				pressed: state.pressed !== PRESSED_UP ? PRESSED_UP : null,
			};
		case VOTE_DOWN:
			return {
				negativeVotes:
					state.pressed !== PRESSED_DOWN
						? state.negativeVotes + 1
						: state.negativeVotes - 1,
				positiveVotes:
					state.pressed !== PRESSED_UP
						? state.positiveVotes
						: state.positiveVotes - 1,
				pressed: state.pressed !== PRESSED_DOWN ? PRESSED_DOWN : null,
			};
		case UPDATE_VOTES:
			return {
				...state,
				negativeVotes: action.payload.negativeVotes,
				positiveVotes: action.payload.positiveVotes,
			};
		default:
			return state;
	}
}

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
	const [state, dispatch] = useReducer(reducer, {
		negativeVotes: initialNegativeVotes,
		positiveVotes: initialPositiveVotes,
		pressed: thumbDown ? PRESSED_DOWN : thumbUp ? PRESSED_UP : null,
	});

	const {negativeVotes, positiveVotes, pressed} = state;

	const voteUp = useCallback(() => {
		dispatch({type: VOTE_UP});

		const score = pressed !== PRESSED_UP ? SCORE_UP : SCORE_UNVOTE;
		sendVoteRequest(score);
	}, [pressed, sendVoteRequest]);

	const voteDown = useCallback(() => {
		dispatch({type: VOTE_DOWN});

		const score = pressed !== PRESSED_DOWN ? SCORE_DOWN : SCORE_UNVOTE;
		sendVoteRequest(score);
	}, [pressed, sendVoteRequest]);

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

	const getTitleThumbsUp = useCallback(() => {
		if (!signedIn) {
			return '';
		}

		const title = getTitle();

		if (!title) {
			if (pressed === PRESSED_UP) {
				return Liferay.Language.get('you-have-rated-this-as-good');
			}
			else {
				return Liferay.Language.get('rate-this-as-good');
			}
		}
	}, [getTitle, pressed, signedIn]);

	const getTitleThumbsDown = useCallback(() => {
		if (!signedIn) {
			return '';
		}

		const title = getTitle();

		if (!title) {
			if (pressed === PRESSED_DOWN) {
				return Liferay.Language.get('you-have-rated-this-as-bad');
			}
			else {
				return Liferay.Language.get('rate-this-as-bad');
			}
		}
	}, [getTitle, pressed, signedIn]);

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
				.then(({totalEntries, totalScore}) => {
					if (totalEntries && totalScore) {
						dispatch({
							payload: {
								negativeVotes: totalEntries - totalScore,
								positiveVotes: totalScore,
							},
							type: UPDATE_VOTES,
						});
					}
				});
		},
		[className, classPK, url]
	);

	return (
		<div className="ratings ratings-thumbs">
			<ClayButton
				aria-pressed={pressed === PRESSED_UP}
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={voteUp}
				small
				title={getTitleThumbsUp()}
				value={positiveVotes}
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="thumbs-up" />
				</span>
				<AnimatedCounter counter={positiveVotes} />
			</ClayButton>
			<ClayButton
				aria-pressed={pressed === PRESSED_DOWN}
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={voteDown}
				small
				title={getTitleThumbsDown()}
				value={negativeVotes}
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="thumbs-down" />
				</span>
				<AnimatedCounter counter={negativeVotes} />
			</ClayButton>
		</div>
	);
};

RatingsThumbs.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	initialNegativeVotes: PropTypes.number,
	initialPositiveVotes: PropTypes.number,
	signedIn: PropTypes.bool.isRequired,
	thumbDown: PropTypes.bool,
	thumbUp: PropTypes.bool,
	url: PropTypes.string.isRequired,
};

export default function(props) {
	return <RatingsThumbs {...props} />;
}
