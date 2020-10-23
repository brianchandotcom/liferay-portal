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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import AnimatedCounter from './AnimatedCounter';

const SCORE_LIKE = 1;
const SCORE_UNLIKE = -1;

const RatingsLike = ({
	disabled = true,
	initialLiked = false,
	inititalTitle,
	positiveVotes = 0,
	sendVoteRequest,
}) => {
	const [liked, setLiked] = useState(initialLiked);
	const [totalLikes, setTotalLikes] = useState(positiveVotes);
	const [animatedButton, setAnimatedButton] = useState(false);

	const isMounted = useIsMounted();

	const HandleAnimationEnd = () => {
		setAnimatedButton(false);
	};

	const handleSendVoteRequest = (score) => {
		sendVoteRequest(score).then(({totalScore} = {}) => {
			if (isMounted() && totalScore) {
				setTotalLikes(Math.round(totalScore));
			}
		});
	};

	const toggleLiked = () => {
		if (!liked) {
			setAnimatedButton(true);
		}

		const score = liked ? SCORE_UNLIKE : SCORE_LIKE;

		setLiked(!liked);
		setTotalLikes(totalLikes + score);
		handleSendVoteRequest(score);
	};

	const getTitle = () => {
		if (inititalTitle !== undefined) {
			return inititalTitle;
		}

		return liked
			? Liferay.Language.get('unlike-this')
			: Liferay.Language.get('like-this');
	};

	return (
		<ClayTooltipProvider>
			<div className="ratings-like">
				<ClayButton
					aria-pressed={liked}
					borderless
					className={classNames({
						'btn-animated': animatedButton,
					})}
					disabled={disabled}
					displayType="secondary"
					onClick={toggleLiked}
					small
					title={getTitle()}
					value={totalLikes}
				>
					<span className="c-inner" tabIndex="-1">
						<span className="inline-item inline-item-before">
							<span className="off">
								<ClayIcon symbol="heart" />
							</span>
							<span
								className="on"
								onAnimationEnd={HandleAnimationEnd}
							>
								<ClayIcon symbol="heart-full" />
							</span>
						</span>
						<span className="inline-item likes">
							<AnimatedCounter counter={totalLikes} />
						</span>
					</span>
				</ClayButton>
			</div>
		</ClayTooltipProvider>
	);
};

RatingsLike.propTypes = {
	disabled: PropTypes.bool,
	initialLiked: PropTypes.bool,
	inititalTitle: PropTypes.string,
	positiveVotes: PropTypes.number,
	sendVoteRequest: PropTypes.func.isRequired,
};

export default RatingsLike;
