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
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../js/utils/constants';
import {FRAGMENT_CONFIGURATION_FIELD_TYPES} from '../config/constants/fragmentConfigurationFieldTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import updateFragmentConfiguration from '../thunks/updateFragmentConfiguration';

const SEGMENT_EXPERIENCE_ID_PREFIX = 'segments-experience-id-';

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
					<ClayForm.Group key={index}>
						<FieldComponent
							field={field}
							onValueSelect={onValueSelect}
							value={fieldValue}
						/>
					</ClayForm.Group>
				);
			})}
		</>
	);
};

export const FragmentConfigurationPanel = ({item}) => {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const {fragmentEntryLinks, segmentsExperienceId} = useContext(StoreContext);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];
	const prefixedSegmentsExperienceId =
		SEGMENT_EXPERIENCE_ID_PREFIX + segmentsExperienceId;

	const configuration = fragmentEntryLink.configuration;
	const defaultConfigurationValues =
		fragmentEntryLink.defaultConfigurationValues;

	const configurationValues = {
		...defaultConfigurationValues,
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		][prefixedSegmentsExperienceId]
	};

	const onValueSelect = (name, value) => {
		const nextConfigurationValues = {
			...configurationValues,
			[name]: value
		};

		dispatch(
			updateFragmentConfiguration({
				config,
				configurationValues: nextConfigurationValues,
				fragmentEntryLink,
				segmentsExperienceId: prefixedSegmentsExperienceId
			})
		);
	};

	return (
		<div className="floating-toolbar-configuration-panel">
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
			<RestoreButton />
		</div>
	);
};

const RestoreButton = () => (
	<ClayButton borderless className="w-100" displayType="secondary" small>
		<ClayIcon symbol="restore" />
		<span className="ml-2">{Liferay.Language.get('restore-values')}</span>
	</ClayButton>
);
