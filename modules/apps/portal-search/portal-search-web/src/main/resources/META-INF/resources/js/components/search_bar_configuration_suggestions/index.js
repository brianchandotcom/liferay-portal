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

import ClayDropDown from '@clayui/drop-down';
import React, {useMemo} from 'react';

import LearnMessage from '../../shared/LearnMessage';
import InputSets, {useInputSets} from '../../shared/input_sets/index';
import cleanSuggestionsContributorConfiguration from '../../utils/clean_suggestions_contributor_configuration';
import {CONTRIBUTOR_TYPES} from '../../utils/types/contributorTypes';
import ContributorInputSetItem from './ContributorInputSetItem';
import SuggestionContributorAddButton from './SuggestionContributorAddButton';

/**
 * Cleans up the fields array by removing those that do not have the required
 * fields (contributorName, displayGroupName, size). If blueprint, check
 * for sxpBlueprintId as well.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({attributes, contributorName, displayGroupName, size}) => {
		if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
			return displayGroupName && size;
		}

		return (
			contributorName &&
			displayGroupName &&
			size &&
			attributes?.sxpBlueprintId
		);
	});

function SearchBarConfigurationSuggestions({
	initialSuggestionsContributorConfiguration = '[]',
	isDXP = false,
	isSearchExperiencesSupported = true,
	learnMessages,
	namespace = '',
	suggestionsContributorConfigurationName = '',
}) {
	const preparedSuggestionsContributorConfiguration = useMemo(
		() =>
			cleanSuggestionsContributorConfiguration(
				initialSuggestionsContributorConfiguration,
				isSearchExperiencesSupported
			),
		[
			initialSuggestionsContributorConfiguration,
			isSearchExperiencesSupported,
		]
	);

	const {
		getInputSetItemProps,
		onInputSetItemChange,
		onInputSetsAdd,
		value: suggestionsContributorConfiguration,
	} = useInputSets(preparedSuggestionsContributorConfiguration);

	const contributorOptions = useMemo(() => {
		const BASIC_OPTION = {
			contributorName: CONTRIBUTOR_TYPES.BASIC,
			description: Liferay.Language.get(
				'basic-suggestions-contributor-help'
			),
			title: Liferay.Language.get('basic'),
		};

		const BLUEPRINT_OPTION = {
			contributorName: CONTRIBUTOR_TYPES.SXP_BLUEPRINT,
			description: (
				<>
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</>
			),
			title: Liferay.Language.get('blueprint'),
		};

		const options = [];

		const basicContributorExists =
			suggestionsContributorConfiguration.findIndex(
				(value) => value.contributorName === CONTRIBUTOR_TYPES.BASIC
			) > -1;

		if (!basicContributorExists) {
			options.push(BASIC_OPTION);
		}

		if (isDXP && isSearchExperiencesSupported) {
			options.push(BLUEPRINT_OPTION);
		}

		return options;
	}, [suggestionsContributorConfiguration.length]); // eslint-disable-line react-hooks/exhaustive-deps

	const _handleInputSetAdd = (contributorName) => () => {
		if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
			onInputSetsAdd({
				attributes: {
					characterThreshold: '',
				},
				contributorName,
				displayGroupName: '',
				size: '',
			});
		}
		else if (contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT) {
			onInputSetsAdd({
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
		<div className="search-bar-configuration-suggestions-root">
			{removeEmptyFields(suggestionsContributorConfiguration).length ? (
				removeEmptyFields(
					suggestionsContributorConfiguration
				).map(({id, ...item}) => (
					<input
						hidden
						key={id}
						name={`${namespace}${suggestionsContributorConfigurationName}`}
						readOnly
						value={JSON.stringify(item)}
					/>
				))
			) : (
				<input
					hidden
					name={`${namespace}${suggestionsContributorConfigurationName}`}
					readOnly
					value=""
				/>
			)}

			<InputSets>
				{suggestionsContributorConfiguration.map(
					(valueItem, valueIndex) => (
						// eslint-disable-next-line react/jsx-key
						<InputSets.Item
							{...getInputSetItemProps(valueItem, valueIndex)}
						>
							<ContributorInputSetItem
								index={valueIndex}
								learnMessages={learnMessages}
								onInputSetItemChange={onInputSetItemChange}
								value={valueItem}
							/>
						</InputSets.Item>
					)
				)}

				{!!contributorOptions.length && (
					<SuggestionContributorAddButton>
						{contributorOptions.map((option, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={_handleInputSetAdd(
									option.contributorName
								)}
							>
								<div>{option.title}</div>

								<div className="text-2">
									{option.description}
								</div>
							</ClayDropDown.Item>
						))}
					</SuggestionContributorAddButton>
				)}
			</InputSets>
		</div>
	);
}

export default SearchBarConfigurationSuggestions;
