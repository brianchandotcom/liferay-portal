/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {LearnMessage} from 'frontend-js-components-web';
import React, {useRef, useState} from 'react';

import {
	CONTRIBUTOR_TYPES,
	CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES,
	CONTRIBUTOR_TYPES_DEFAULT_ATTRIBUTES,
} from '../../../utils/types/contributorTypes';
import InputSetItemHeader from './InputSetItemHeader';
import CharacterThresholdInput from './inputs/CharacterThresholdInput';
import ContentTypeInput from './inputs/ContentTypeInput';
import DisplayGroupNameInput from './inputs/DisplayGroupNameInput';
import MatchDisplayLanguageInput from './inputs/MatchDisplayLanguageInput';
import MinimumSearchesInput from './inputs/MinimumSearchesInput';
import SizeInput from './inputs/SizeInput';
import TimeRangeInput from './inputs/TimeRangeInput';

function getSiteActivitiesContributorActivityOptions() {
	const options = [
		{
			contributorName: CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS,
			description: Liferay.Language.get('top-searches-help'),
			learnMessageResourceKey: 'search-bar-suggestions-site-activities',
			title: Liferay.Language.get('top-searches'),
		},
		{
			contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS,
			description: Liferay.Language.get('trending-searches-help'),
			learnMessageResourceKey: 'search-bar-suggestions-site-activities',
			title: Liferay.Language.get('trending-searches'),
		},
	];

	if (Liferay.FeatureFlags['LPS-176691']) {
		return options.concat([
			{
				contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES,
				description: Liferay.Language.get('recent-searches-help'),
				learnMessageResourceKey:
					'search-bar-suggestions-site-activities',
				title: Liferay.Language.get('recent-searches'),
			},
			{
				contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES,
				description: Liferay.Language.get('recent-pages-help'),
				learnMessageResourceKey:
					'search-bar-suggestions-site-activities',
				title: Liferay.Language.get('recent-pages'),
			},
			{
				contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_SITES,
				description: Liferay.Language.get('recent-sites-help'),
				learnMessageResourceKey:
					'search-bar-suggestions-site-activities',
				title: Liferay.Language.get('recent-sites'),
			},
			{
				contributorName: CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS,
				description: Liferay.Language.get('recently-viewed-help'),
				learnMessageResourceKey:
					'search-bar-suggestions-site-activities',
				title: Liferay.Language.get('recently-viewed'),
			},
		]);
	}

	return options;
}

function SiteActivities({index, onBlur, onInputSetItemChange, touched, value}) {
	const [showActivityDropdown, setShowActivityDropdown] = useState(false);

	const alignElementRef = useRef();

	const SITE_ACTIVITIES_CONTRIBUTOR_ACTIVITY_OPTIONS = getSiteActivitiesContributorActivityOptions();

	const _handleChangeAttributeInput = (property) => (event) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: event.target.value,
			},
		});
	};

	const _handleChangeAttributeValue = (property) => (newValue) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: newValue,
			},
		});
	};

	const _handleActivityDropdownClick = () => {
		setShowActivityDropdown(!showActivityDropdown);
	};

	const _handleActivityInputClick = (contributorName) => () => {
		const attributes = {};

		Object.entries(
			CONTRIBUTOR_TYPES_DEFAULT_ATTRIBUTES[contributorName]
		).forEach(([attr, attrValue]) => {
			attributes[attr] = value.attributes[attr] || attrValue;
		});

		onInputSetItemChange(index, {
			attributes,
			contributorName,
			displayGroupName:
				CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES[
					contributorName
				] || '',
		});

		setShowActivityDropdown(false);
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
						resource="portal-search-web"
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

					<ClayButton
						aria-label={Liferay.Language.get(
							'suggestion-contributor'
						)}
						className="form-control form-control-select"
						displayType="unstyled"
						onClick={_handleActivityDropdownClick}
						ref={alignElementRef}
					>
						{
							SITE_ACTIVITIES_CONTRIBUTOR_ACTIVITY_OPTIONS.find(
								({contributorName}) =>
									contributorName === value.contributorName
							)?.title
						}
					</ClayButton>

					<ClayDropDown.Menu
						active={showActivityDropdown}
						alignElementRef={alignElementRef}
						closeOnClickOutside
						onSetActive={setShowActivityDropdown}
						style={{
							maxWidth:
								alignElementRef.current &&
								alignElementRef.current.clientWidth + 'px',
						}}
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

										<LearnMessage
											className="c-ml-1"
											resource="portal-search-web"
											resourceKey={
												item.learnMessageResourceKey
											}
										/>
									</div>
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown.Menu>
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

			{[
				CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS,
				CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS,
			].includes(value.contributorName) ? (
				<div className="c-mb-0 form-group-autofit">
					<CharacterThresholdInput
						onBlur={onBlur('attributes.characterThreshold')}
						onChange={_handleChangeAttributeInput(
							'characterThreshold'
						)}
						touched={touched['attributes.characterThreshold']}
						value={value.attributes?.characterThreshold}
					/>

					<MatchDisplayLanguageInput
						onChange={_handleChangeAttributeInput(
							'matchDisplayLanguageId'
						)}
						value={value.attributes?.matchDisplayLanguageId}
					/>

					<MinimumSearchesInput
						onBlur={onBlur('attributes.minCounts')}
						onChange={_handleChangeAttributeInput('minCounts')}
						touched={touched['attributes.minCounts']}
						value={value.attributes?.minCounts}
					/>
				</div>
			) : [
					CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES,
					CONTRIBUTOR_TYPES.ASAH_RECENT_SITES,
			  ].includes(value.contributorName) ? (
				<div className="c-mb-0 form-group-autofit">
					<CharacterThresholdInput
						onBlur={onBlur('attributes.characterThreshold')}
						onChange={_handleChangeAttributeInput(
							'characterThreshold'
						)}
						touched={touched['attributes.characterThreshold']}
						value={value.attributes?.characterThreshold}
					/>

					<TimeRangeInput
						onBlur={onBlur('attributes.rangeKey')}
						onChange={_handleChangeAttributeValue('rangeKey')}
						touched={touched['attributes.rangeKey']}
						value={value.attributes?.rangeKey}
					/>
				</div>
			) : (
				<>
					<div className="c-mb-3 form-group-autofit">
						<CharacterThresholdInput
							onBlur={onBlur('attributes.characterThreshold')}
							onChange={_handleChangeAttributeInput(
								'characterThreshold'
							)}
							touched={touched['attributes.characterThreshold']}
							value={value.attributes?.characterThreshold}
						/>

						{value.contributorName ===
							CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES && (
							<MatchDisplayLanguageInput
								onChange={_handleChangeAttributeInput(
									'matchDisplayLanguageId'
								)}
								value={value.attributes?.matchDisplayLanguageId}
							/>
						)}

						{value.contributorName ===
							CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS && (
							<ContentTypeInput
								onBlur={onBlur('attributes.contentType')}
								onChange={_handleChangeAttributeValue(
									'contentType'
								)}
								touched={touched['attributes.contentType']}
								value={value.attributes?.contentType}
							/>
						)}
					</div>

					<div className="c-mb-0 form-group-autofit">
						{value.contributorName ===
							CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES && (
							<MinimumSearchesInput
								onBlur={onBlur('attributes.minCounts')}
								onChange={_handleChangeAttributeInput(
									'minCounts'
								)}
								touched={touched['attributes.minCounts']}
								value={value.attributes?.minCounts}
							/>
						)}

						<TimeRangeInput
							onChange={_handleChangeAttributeValue('rangeKey')}
							value={value.attributes?.rangeKey}
						/>
					</div>
				</>
			)}
		</>
	);
}

export default SiteActivities;
