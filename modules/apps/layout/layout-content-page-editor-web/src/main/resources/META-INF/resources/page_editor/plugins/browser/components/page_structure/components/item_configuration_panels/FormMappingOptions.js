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

import {sub} from 'frontend-js-web';
import React from 'react';

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {FORM_MAPPING_SOURCES} from '../../../../../../app/config/constants/formMappingSources';
import {LAYOUT_TYPES} from '../../../../../../app/config/constants/layoutTypes';
import {config} from '../../../../../../app/config/index';

export default function FormMappingOptions({
	hideLabel = false,
	item,
	onValueSelect,
}) {
	let formTypes = [
		{
			label: Liferay.Language.get('none'),
			value: '0',
		},
		...(Liferay.FeatureFlags['LPS-169923']
			? config.formTypes.filter((formType) => !formType?.isRestricted)
			: config.formTypes),
	];

	if (
		Liferay.FeatureFlags['LPS-183727'] &&
		config.layoutType === LAYOUT_TYPES.display
	) {
		formTypes = formTypes.map((formType) => {
			if (formType.value === config.selectedMappingTypes.type.id) {
				return {
					...formType,
					label: sub(
						Liferay.Language.get('x-default'),
						config.selectedMappingTypes.type.label
					),
				};
			}

			return formType;
		});
	}

	const {classNameId, classTypeId} = item.config;

	const selectedType = formTypes.find(({value}) => value === classNameId);

	const selectedSubtype = selectedType?.subtypes?.find(
		({value}) => value === classTypeId
	);

	return (
		<>
			<SelectField
				field={{
					hideLabel,
					label: Liferay.Language.get('content-type'),
					name: 'classNameId',
					typeOptions: {
						validValues: formTypes.map(({label, value}) => ({
							label,
							value,
						})),
					},
				}}
				onValueSelect={(_name, classNameId) => {
					const type = formTypes.find(
						({value}) => value === classNameId
					);

					return onValueSelect({
						classNameId,
						classTypeId: type?.subtypes?.[0]?.value || '0',
						formConfig: FORM_MAPPING_SOURCES.otherContentType,
					});
				}}
				value={selectedType.value}
			/>

			{selectedType?.subtypes?.length > 0 && (
				<SelectField
					field={{
						hideLabel,
						label: Liferay.Language.get('subtype'),
						name: 'classTypeId',
						typeOptions: {
							validValues: [
								{
									label: Liferay.Language.get('none'),
									value: '',
								},
								...selectedType.subtypes,
							],
						},
					}}
					onValueSelect={(_name, classTypeId) =>
						onValueSelect({
							classNameId: item.config.classNameId,
							classTypeId,
							formConfig: FORM_MAPPING_SOURCES.otherContentType,
						})
					}
					value={selectedSubtype ? classTypeId : ''}
				/>
			)}
		</>
	);
}
