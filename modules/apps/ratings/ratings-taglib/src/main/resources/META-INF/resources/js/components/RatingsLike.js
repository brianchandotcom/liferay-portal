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

import AnimatedCounter from './AnimatedCounter';

const RATING_TYPE = 'like';
const SCORE_LIKE = 1;
const SCORE_UNLIKE = -1;

const RatingsLike = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	initialLiked = false,
	positiveVotes = 0,
	signedIn,
	url,
}) => {
	const [liked, setLiked] = useState(initialLiked);
	const [totalLikes, setTotalLikes] = useState(positiveVotes);

	const toggleLiked = () => {
		const score = liked ? SCORE_UNLIKE : SCORE_LIKE;

		setLiked(!liked);
		setTotalLikes(totalLikes + score);
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

		return liked
			? Liferay.Language.get('unlike-this')
			: Liferay.Language.get('like-this');
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
			.then(({totalScore}) => {
				if (totalScore) {
					setTotalLikes(totalScore);
				}
			});
	};

	return (
		<div className="ratings ratings-like">
			<ClayButton
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={toggleLiked}
				small
				title={getTitle()}
				value={totalLikes}
			>
				<ClayIcon className={liked ? 'liked' : ''} symbol="heart" />

				<strong className="likes">
					<AnimatedCounter counter={totalLikes} />
				</strong>
			</ClayButton>
		</div>
	);
};

RatingsLike.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	initialLiked: PropTypes.bool,
	positiveVotes: PropTypes.number,
	signedIn: PropTypes.bool.isRequired,
	url: PropTypes.string.isRequired,
};

export default RatingsLike;
