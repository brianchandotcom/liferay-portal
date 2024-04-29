/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {
	Dispatch,
	SetStateAction,
	useContext,
	useEffect,
	useState,
} from 'react';

import {EditSchemaContext} from '../EditAPIApplicationContext';
import {
	BUSINESS_TYPES_TO_SYMBOLS,
	UNMODIFIABLE_OBJECTS_WHITELIST,
} from '../utils/constants';

interface BaseAPISchemaPropertyProps {
	added: boolean;
	objectField: ObjectField;
	objectRelationshipName?: string;
	parentObjectDefinitionData: ParentObjectDefinitionProps;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}

interface ParentObjectDefinitionProps {
	modifiable?: boolean;
	objectDefinitionName: string;
	objectDefinitionERC: string;
}

export default function BaseAPISchemaProperty({
	added,
	objectField,
	objectRelationshipName,
	parentObjectDefinitionData,
	setSchemaUIData,
}: BaseAPISchemaPropertyProps) {
	const {apiSchemaId} = useContext(EditSchemaContext);
	const [disabled, setDisabled] = useState(false);
	const [focused, setFocused] = useState(false);

	const {
		modifiable,
		objectDefinitionERC,
		objectDefinitionName,
	} = parentObjectDefinitionData;

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

	useEffect(() => {
		setDisabled(
			added ||
				(!modifiable &&
					!UNMODIFIABLE_OBJECTS_WHITELIST.includes(
						objectDefinitionERC
					))
		);
	}, [added, parentObjectDefinitionData]);

	return (
		<ClayButton
			aria-label={sub(
				Liferay.Language.get('add-x-property'),
				localizedPropertyName
			)}
			className="property-container"
			displayType="unstyled"
			onBlur={() => setFocused(false)}
			onClick={() => !disabled && handleClick()}
			onFocus={() => setFocused(true)}
		>
			<div
				className={classNames({
					disabled,
					'icon-container': true,
				})}
			>
				<ClayIcon
					symbol={BUSINESS_TYPES_TO_SYMBOLS[objectField.businessType]}
				/>
			</div>

			<div
				className={classNames({
					disabled,
					'label-container': true,
					'text-truncate': true,
				})}
			>
				{objectField.label[Liferay.ThemeDisplay.getDefaultLanguageId()]}
			</div>

			{!disabled && (
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
