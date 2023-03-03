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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

import LearnMessage from '../../shared/LearnMessage';
import {CONTRIBUTOR_TYPES} from '../../utils/types/contributorTypes';

function SuggestionContributorAddButton(learnMessages, onClick) {
	const _handleOnClick = (contributorName) => () => {
		if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
			onClick({
				attributes: {
					characterThreshold: '',
				},
				contributorName,
				displayGroupName: '',
				size: '',
			});
		} else if (contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT) {
			onClick({
				attributes: {
					characterThreshold: '',
					fields: [],
					includeAssetSearchSummary: true,
					includeAssetURL: true,
					sxpBlueprintId: '',
				},
				contributorName,
				displayGroupName: '',
				size: '',
			});
		}
	};

	return (
		<ClayDropDown
			closeOnClick
			menuWidth="sm"
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('suggestion-contributor')}
					displayType="secondary"
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>

					{Liferay.Language.get('add-suggestions')}
				</ClayButton>
			}
		>
			<ClayDropDown.Item
				onClick={_handleOnClick(CONTRIBUTOR_TYPES.BASIC)}
			>
				<div>{Liferay.Language.get('basic')}</div>

				<div className="text-2">
					{Liferay.Language.get('basic-suggestions-contributor-help')}
				</div>
			</ClayDropDown.Item>

			<ClayDropDown.Item
				onClick={_handleOnClick(CONTRIBUTOR_TYPES.SXP_BLUEPRINT)}
			>
				<div>{Liferay.Language.get('blueprint')}</div>

				<div className="text-2">
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</div>
			</ClayDropDown.Item>
		</ClayDropDown>
	);
}

export default SuggestionContributorAddButton;
