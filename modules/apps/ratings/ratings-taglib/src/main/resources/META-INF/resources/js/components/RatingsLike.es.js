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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const RATING_TYPE = 'like';
const SCORE_LIKE = 1;
const SCORE_UNLIKE = -1;

const RatingsLike = ({
	className,
	classPK,
	enabled = false,
	inTrash = false,
	positiveVotes = 0,
	signedIn,
	url,
}) => {
	const [active, setActive] = useState(false);
	const [likes, setLikes] = useState(positiveVotes);

	const toggleActive = () => {
		sendVoteRequest(active ? SCORE_UNLIKE : SCORE_LIKE);

		setActive(!active);
	};

	const getTitle = () => {
		if (!signedIn) {
			return '';
		}

		if (inTrash) {
			return LanguageUtil.get(
				'ratings-are-disabled-because-this-entry-is-in-the-recycle-bin'
			);
		} else if (!enabled) {
			return LanguageUtil.get('ratings-are-disabled-in-staging');
		}

		return active
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

		var data = {
			className,
			classPK,
			p_auth: Liferay.authToken,
			p_l_id: themeDisplay.getPlid(),
			score,
		};

		Liferay.Util.fetch(url, {
			body: Liferay.Util.objectToFormData(data),
			method: 'POST',
		})
			.then(response => response.json())
			.then(response => setLikes(response.totalScore));
	};

	return (
		<div className="ratings-like">
			<ClayButton
				borderless
				disabled={!signedIn || !enabled}
				displayType="secondary"
				onClick={toggleActive}
				small
				title={getTitle}
			>
				<ClayIcon className={active ? 'selected' : ''} symbol="heart" />

				<strong className="ml-2">{likes}</strong>
			</ClayButton>
		</div>
	);
};

RatingsLike.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.number.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	positiveVotes: PropTypes.number,
	signedIn: PropTypes.bool.isRequired,
	url: PropTypes.string.isRequired,
};

export default function(props) {
	return <RatingsLike {...props} />;
}