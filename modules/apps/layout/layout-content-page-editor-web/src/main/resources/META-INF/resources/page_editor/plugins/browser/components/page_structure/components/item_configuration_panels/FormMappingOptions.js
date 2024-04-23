/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useControlledState} from '@liferay/layout-js-components-web';
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
		...config.formTypes
			.filter((formType) => !formType.isRestricted)
			.map((formType) => ({
				...formType,
				subtypes: formType.subtypes.filter(
					(subtype) => !subtype.isRestricted
				),
			})),
	];

	if (config.layoutType === LAYOUT_TYPES.display) {
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

	const [classNameId, setClassNameId] = useControlledState(
		item.config.classNameId
	);
	const [classTypeId, setClassTypeId] = useControlledState(
		item.config.classTypeId
	);

	const selectedType = formTypes.find(({value}) => value === classNameId);

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
					setClassNameId(classNameId);

					const type = formTypes.find(
						({value}) => value === classNameId
					);

					if (type?.subtypes?.length) {
						return;
					}

					onValueSelect({
						classNameId,
						classTypeId,
						formConfig: FORM_MAPPING_SOURCES.otherContentType,
					});
				}}
				value={classNameId}
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
					onValueSelect={(_name, classTypeId) => {
						setClassTypeId(classTypeId);

						onValueSelect({
							classNameId,
							classTypeId,
							formConfig: FORM_MAPPING_SOURCES.otherContentType,
						});
					}}
					value={classTypeId}
				/>
			)}
		</>
	);
}
