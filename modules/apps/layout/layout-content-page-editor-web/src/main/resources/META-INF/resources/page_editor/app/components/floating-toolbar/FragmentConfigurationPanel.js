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

import {
	ConfigurationFieldPropTypes,
	getLayoutDataItemPropTypes
} from '../../../prop-types/index';
import {FRAGMENT_CONFIGURATION_FIELD_TYPES} from '../../config/constants/fragmentConfigurationFieldTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/freemarkerFragmentEntryProcessor';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateFragmentConfiguration from '../../thunks/updateFragmentConfiguration';

const FieldSet = ({configurationValues, fields, label, onValueSelect}) => {
	return (
		<>
			{label && <p className="mb-3 sheet-subtitle">{label}</p>}

			{fields.map((field, index) => {
				const FieldComponent =
					field.type &&
					FRAGMENT_CONFIGURATION_FIELD_TYPES[field.type];

				const fieldValue = configurationValues[field.name];

				return (
					<FieldComponent
						field={field}
						key={index}
						onValueSelect={onValueSelect}
						value={fieldValue}
					/>
				);
			})}
		</>
	);
};

FieldSet.propTypes = {
	configurationValues: PropTypes.object,
	fields: PropTypes.arrayOf(PropTypes.shape(ConfigurationFieldPropTypes)),
	label: PropTypes.string,
	onValueSelect: PropTypes.func.isRequired
};

export const FragmentConfigurationPanel = ({item}) => {
	const dispatch = useDispatch();

	const fragmentEntryLink = useSelector(
		state => state.fragmentEntryLinks[item.config.fragmentEntryLinkId]
	);

	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const configuration = fragmentEntryLink.configuration;
	const defaultConfigurationValues =
		fragmentEntryLink.defaultConfigurationValues;

	const configurationValues = {
		...defaultConfigurationValues,
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		][segmentsExperienceId]
	};

	const onRestoreButtonClick = () => {
		dispatch(
			updateFragmentConfiguration({
				configurationValues: defaultConfigurationValues,
				fragmentEntryLink,
				segmentsExperienceId
			})
		);
	};

	const onValueSelect = (name, value) => {
		const nextConfigurationValues = {
			...configurationValues,
			[name]: value
		};

		dispatch(
			updateFragmentConfiguration({
				configurationValues: nextConfigurationValues,
				fragmentEntryLink,
				segmentsExperienceId
			})
		);
	};

	return (
		<>
			{configuration.fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						configurationValues={configurationValues}
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
						onValueSelect={onValueSelect}
					/>
				);
			})}
			<RestoreButton onRestoreButtonClick={onRestoreButtonClick} />
		</>
	);
};

FragmentConfigurationPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired
		}).isRequired
	})
};

const RestoreButton = ({onRestoreButtonClick}) => (
	<ClayButton
		borderless
		className="w-100"
		displayType="secondary"
		onClick={onRestoreButtonClick}
		small
	>
		<ClayIcon symbol="restore" />
		<span className="ml-2">{Liferay.Language.get('restore-values')}</span>
	</ClayButton>
);

RestoreButton.propTypes = {
	onRestoreButtonClick: PropTypes.func.isRequired
};
