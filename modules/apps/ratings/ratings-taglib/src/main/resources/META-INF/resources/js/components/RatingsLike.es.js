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
import React, {useState} from 'react';

const RatingsLike = ({
	disabled = false,
	enabled = false,
	inTrash = false,
	positiveVotes = 0,
	signedIn
}) => {
	const [active, setActive] = useState(false);

	const toggleActive = () => {
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
			return LanguageUtil.get(
				'ratings-are-disabled-in-staging'
			);
		}

		return active
			? Liferay.Language.get('unlike-this')
			: Liferay.Language.get('like-this');
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

				<strong className="ml-2">
					{active ? positiveVotes + 1 : positiveVotes}
				</strong>
			</ClayButton>
		</div>
	);
};

export default function(props) {
	return <RatingsLike {...props} />;
}