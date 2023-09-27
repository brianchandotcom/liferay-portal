/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useContext, useState} from 'react';

import {EditSchemaContext} from '../EditAPIApplicationContext';
import {BUSINESS_TYPES_TO_SYMBOLS} from '../utils/constants';

interface BaseAPISchemaPropertyProps {
	added: boolean;
	objectDefinitionName: string;
	objectField: ObjectField;
	objectRelationshipName?: string;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}

export default function BaseAPISchemaProperty({
	added,
	objectDefinitionName,
	objectField,
	objectRelationshipName,
	setSchemaUIData,
}: BaseAPISchemaPropertyProps) {
	const {apiSchemaId} = useContext(EditSchemaContext);
	const [focused, setFocused] = useState(false);

	const localizedPropertyName = objectField.label[
		Liferay.ThemeDisplay.getDefaultLanguageId()
	]!;

	const handleClick = () => {
		setSchemaUIData((previous) => {
			if (previous.schemaProperties) {
				previous.schemaProperties.unshift({
					businessType: objectField.businessType,
					name: localizedPropertyName,
					objectDefinitionName,
					objectFieldERC: objectField.externalReferenceCode,
					objectFieldId: objectField.id,
					objectFieldName: objectField.name,
					r_apiSchemaToAPIProperties_c_apiSchemaId: apiSchemaId,
					type: 'treeViewItem',
					...(objectRelationshipName && {
						objectRelationshipNames: objectRelationshipName,
					}),
				});

				return {
					...previous,
					schemaProperties: [...previous.schemaProperties],
				};
			}

			return previous;
		});
	};

	return (
		<ClayButton
			aria-label={sub(
				Liferay.Language.get('add-x-property'),
				localizedPropertyName
			)}
			className="property-container"
			displayType="unstyled"
			onBlur={() => setFocused(false)}
			onClick={() => !added && handleClick()}
			onFocus={() => setFocused(true)}
		>
			<div
				className={classNames({
					'disabled': added,
					'icon-container': true,
				})}
			>
				<ClayIcon
					symbol={BUSINESS_TYPES_TO_SYMBOLS[objectField.businessType]}
				/>
			</div>

			<div
				className={classNames({
					'disabled': added,
					'label-container': true,
					'text-truncate': true,
				})}
			>
				{objectField.label[Liferay.ThemeDisplay.getDefaultLanguageId()]}
			</div>

			{!added && (
				<div
					className={classNames({
						'focused-parent': focused,
						'icon-container': true,
						'plus-icon': true,
					})}
				>
					<ClayIcon symbol="plus" />
				</div>
			)}
		</ClayButton>
	);
}
