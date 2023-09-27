/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
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
