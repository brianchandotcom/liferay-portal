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

import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {FRAGMENT_CONFIGURATION_ROLES} from '../../../../../../app/config/constants/fragmentConfigurationRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateFragmentConfiguration from '../../../../../../app/thunks/updateFragmentConfiguration';
import {getLayoutDataItemPropTypes} from '../../../../../../prop-types/index';
import {FieldSet} from './FieldSet';

export const FragmentGeneralPanel = ({item}) => {
	const dispatch = useDispatch();

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const fieldSets = fragmentEntryLink.configuration?.fieldSets.filter(
		(fieldSet) =>
			fieldSet.configurationRole !== FRAGMENT_CONFIGURATION_ROLES.style
	);

	const defaultConfigurationValues =
		fragmentEntryLink.defaultConfigurationValues;

	const onValueSelect = useCallback(
		(name, value) => {
			const configurationValues = getConfigurationValues(
				defaultConfigurationValues,
				fragmentEntryLink
			);

			const localizable =
				fieldSets?.some((fieldSet) =>
					fieldSet.fields.some(
						(field) => field.name === name && field.localizable
					)
				) ?? false;

			const currentValue = configurationValues[name];

			const nextConfigurationValues = {
				...configurationValues,
				[name]: localizable
					? {
							...(typeof currentValue === 'object'
								? currentValue
								: {[config.defaultLanguageId]: currentValue}),
							[languageId]: value,
					  }
					: value,
			};

			dispatch(
				updateFragmentConfiguration({
					configurationValues: nextConfigurationValues,
					fragmentEntryLink,
					languageId,
					segmentsExperienceId,
				})
			);
		},
		[
			defaultConfigurationValues,
			dispatch,
			fieldSets,
			fragmentEntryLink,
			languageId,
			segmentsExperienceId,
		]
	);

	return (
		<>
			{fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
						languageId={languageId}
						onValueSelect={onValueSelect}
						values={getConfigurationValues(
							defaultConfigurationValues,
							fragmentEntryLink
						)}
					/>
				);
			})}
		</>
	);
};

FragmentGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};

function getConfigurationValues(defaultConfigurationValues, fragmentEntryLink) {
	return {
		...defaultConfigurationValues,
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		],
	};
}
