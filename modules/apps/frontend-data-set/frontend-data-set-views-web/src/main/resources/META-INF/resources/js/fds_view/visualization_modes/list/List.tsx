/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {IBaseVisualizationMode} from '../VisualizationModes';

import classNames from 'classnames';

export interface IList extends IBaseVisualizationMode<'list'> {}

interface IListField {
	fieldId: string;
	label: string;
}

const LIST_VISUALIZATION_MODE_FIELDS: IListField[] = [
	{fieldId: 'title', label: Liferay.Language.get('title')},
	{fieldId: 'description', label: Liferay.Language.get('description')},
	{fieldId: 'symbol', label: Liferay.Language.get('symbol')},
	{fieldId: 'link', label: Liferay.Language.get('link')},
	{fieldId: 'label', label: Liferay.Language.get('label')},
];

const LABEL_CELL_WIDTH = '30%';

export default function List() {
	const [fieldValues, setFieldValues] = React.useState<
		Record<IListField['fieldId'], string>
	>({});

	const onValueChange = (fieldId: IListField['fieldId'], value: string) => {
		setFieldValues({
			...fieldValues,
			[fieldId]: value,
		});
	};

	return (
		<ClayLayout.ContentCol className="c-gap-4">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="stripe"
			>
				{Liferay.Language.get(
					'this-visualization-mode-wont-be-shown-until-you-assign-at-least-one-field-to-a-list-element'
				)}
			</ClayAlert>

			<Table className="mb-0" columnsVisibility={false}>
				<Head>
					<Cell width={LABEL_CELL_WIDTH}>
						{Liferay.Language.get('list-element')}
					</Cell>

					<Cell className="border-left-0">
						{Liferay.Language.get('field')}
					</Cell>
				</Head>

				<Body>
					{LIST_VISUALIZATION_MODE_FIELDS.map((field) => (
						<ListField
							field={field}
							key={field.fieldId}
							onValueChange={(value) =>
								onValueChange(field.fieldId, value)
							}
							value={fieldValues[field.fieldId]}
						/>
					))}
				</Body>
			</Table>
		</ClayLayout.ContentCol>
	);
}

interface IListFieldProps {
	field: IListField;
	onValueChange: (value: string) => void;
	value?: string;
}

function ListField({field, onValueChange, value}: IListFieldProps) {
	const onClick = () => {
		onValueChange(field.fieldId);
	};

	return (
		<Row>
			<Cell width={LABEL_CELL_WIDTH}>
				<strong>{field.label}</strong>
			</Cell>

			<Cell>
				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<p
							className={classNames(
								'align-items-center d-flex mb-0',
								{'text-secondary': !value}
							)}
						>
							{value || Liferay.Language.get('not-assigned')}
						</p>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('assign-field')}
							displayType="secondary"
							onClick={onClick}
							symbol="plus"
							title={Liferay.Language.get('assign-field')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</Cell>
		</Row>
	);
}
