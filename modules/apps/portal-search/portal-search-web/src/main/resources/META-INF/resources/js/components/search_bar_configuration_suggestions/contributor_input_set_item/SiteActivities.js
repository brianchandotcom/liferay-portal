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
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext} from 'react';

import LearnMessage from '../../../shared/LearnMessage';
import SearchContext from '../../../shared/SearchContext';
import {
	CONTRIBUTOR_TYPES,
	CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES,
} from '../../../utils/types/contributorTypes';
import InputSetItemHeader from './InputSetItemHeader';
import CharacterThresholdInput from './inputs/CharacterThresholdInput';
import DisplayGroupNameInput from './inputs/DisplayGroupNameInput';
import MinimumSearchesInput from './inputs/MinimumSearchesInput';
import SizeInput from './inputs/SizeInput';

function getSiteActivitiesContributorActivityOptions(learnMessages) {
	return [
		{
			contributorName: CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS,
			description: (
				<>
					{Liferay.Language.get('top-searches-help')}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-site-activities"
					/>
				</>
			),
			title: Liferay.Language.get('top-searches'),
		},
		{
			contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS,
			description: (
				<>
					{Liferay.Language.get('trending-searches-help')}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-site-activities"
					/>
				</>
			),
			title: Liferay.Language.get('trending-searches'),
		},
	];
}

function SiteActivities({index, onBlur, onInputSetItemChange, touched, value}) {
	const {learnMessages} = useContext(SearchContext);

	const SITE_ACTIVITIES_CONTRIBUTOR_ACTIVITY_OPTIONS = getSiteActivitiesContributorActivityOptions(
		learnMessages
	);

	const _handleChangeAttribute = (property) => (event) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: event.target.value,
			},
		});
	};

	const _handleActivityInputClick = (contributorName) => () => {
		onInputSetItemChange(index, {
			contributorName,
			displayGroupName:
				CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES[
					contributorName
				] || '',
		});
	};

	return (
		<>
			<InputSetItemHeader>
				<InputSetItemHeader.Title>
					{Liferay.Language.get(
						'site-activities-suggestions-contributor'
					)}
				</InputSetItemHeader.Title>

				<InputSetItemHeader.Description>
					{Liferay.Language.get(
						'site-activities-suggestions-contributor-help'
					)}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-site-activities"
					/>
				</InputSetItemHeader.Description>
			</InputSetItemHeader>

			<div className="c-mb-3 form-group-autofit">
				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('activity')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayDropDown
						closeOnClick
						menuWidth="sm"
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get(
									'suggestion-contributor'
								)}
								className="form-control form-control-select"
								displayType="unstyled"
							>
								{
									SITE_ACTIVITIES_CONTRIBUTOR_ACTIVITY_OPTIONS.find(
										({contributorName}) =>
											contributorName ===
											value.contributorName
									).title
								}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList
							items={SITE_ACTIVITIES_CONTRIBUTOR_ACTIVITY_OPTIONS}
						>
							{(item) => (
								<ClayDropDown.Item
									active={
										value.contributorName ===
										item.contributorName
									}
									key={item.name}
									onClick={_handleActivityInputClick(
										item.contributorName
									)}
								>
									<div>{item.title}</div>

									<div className="text-2">
										{item.description}
									</div>
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayInput.GroupItem>
			</div>

			<div className="c-mb-3 form-group-autofit">
				<DisplayGroupNameInput
					onBlur={onBlur('displayGroupName')}
					onChange={onInputSetItemChange(index, 'displayGroupName')}
					touched={touched.displayGroupName}
					value={value.displayGroupName}
				/>

				<SizeInput
					onBlur={onBlur('size')}
					onChange={onInputSetItemChange(index, 'size')}
					touched={touched.size}
					value={value.size}
				/>
			</div>

			<div className="c-mb-0 form-group-autofit">
				<CharacterThresholdInput
					onBlur={onBlur('attributes.characterThreshold')}
					onChange={_handleChangeAttribute('characterThreshold')}
					touched={touched['attributes.characterThreshold']}
					value={value.attributes?.characterThreshold}
				/>

				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('match-display-language')}

						<ClayTooltipProvider>
							<span
								className="c-ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'match-display-language-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get(
							'match-display-language'
						)}
						onChange={_handleChangeAttribute(
							'matchDisplayLanguageId'
						)}
						value={value.attributes?.matchDisplayLanguageId}
					>
						<ClaySelect.Option
							label={Liferay.Language.get('true')}
							value={true}
						/>

						<ClaySelect.Option
							label={Liferay.Language.get('false')}
							value={false}
						/>
					</ClaySelect>
				</ClayInput.GroupItem>

				<MinimumSearchesInput
					onBlur={onBlur('attributes.minCounts')}
					onChange={_handleChangeAttribute('minCounts')}
					touched={touched['attributes.minCounts']}
					value={value.attributes?.minCounts}
				/>
			</div>
		</>
	);
}

export default SiteActivities;
