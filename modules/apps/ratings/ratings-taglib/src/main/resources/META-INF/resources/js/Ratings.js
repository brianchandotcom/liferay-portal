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

import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import TYPES from './RATINGS_TYPES';
import RatingsLike from './components/RatingsLike';
import RatingsStars from './components/RatingsStars';
import RatingsThumbs from './components/RatingsThumbs';
import {errorToast} from './utils/toast';

const Ratings = ({
	className,
	classPK,
	contentTitle,
	enabled = false,
	inTrash = false,
	signedIn,
	type,
	url,
	...restProps
}) => {
	const getDefaultTitle = () => {
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
	};

	const sendVoteRequest = useCallback(
		(score) => {
			if (Liferay?.Session?.get('sessionState') === 'expired') {
				errorToast(
					`${Liferay.Language.get('you-must-be-signed-in-to-rate')}`
				);

				return Promise.resolve();
			}

			Liferay.fire('ratings:vote', {
				className,
				classPK,
				contentTitle,
				ratingType: type,
				score,
			});

			const body = objectToFormData({
				className,
				classPK,
				p_auth: Liferay.authToken,
				p_l_id: themeDisplay.getPlid(),
				score,
			});

			return fetch(url, {
				body,
				method: 'POST',
			})
				.then((response) => response.json())
				.catch(() => {
					errorToast();
				});
		},
		[className, classPK, contentTitle, type, url]
	);

	const RatingsTypes = {
		[TYPES.LIKE]: RatingsLike,
		[TYPES.STARS]: RatingsStars,
		[TYPES.STACKED_STARS]: RatingsStars,
		[TYPES.THUMBS]: RatingsThumbs,
	};

	const RatingsComponent = RatingsTypes[type];

	return (
		<RatingsComponent
			{...restProps}
			disabled={!signedIn || !enabled}
			inititalTitle={getDefaultTitle()}
			sendVoteRequest={sendVoteRequest}
			type={type}
		/>
	);
};

Ratings.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	inTrash: PropTypes.bool,
	signedIn: PropTypes.bool.isRequired,
	type: PropTypes.string.isRequired,
	url: PropTypes.string.isRequired,
};

export default Ratings;
