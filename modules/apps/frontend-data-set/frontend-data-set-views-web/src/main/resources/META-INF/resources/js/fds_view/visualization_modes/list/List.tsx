/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {openModal} from 'frontend-js-web';
import React from 'react';

import '../../../../css/ListVisualizationMode.scss';
import {IFDSViewSectionProps} from '../../../FDSView';
import {IBaseVisualizationMode} from '../VisualizationModes';
import {IFDSField} from '../table/Table';
import AddFieldsModalContent from '../table/modal_content/AddFieldsModalContent';

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

export default function List(props: IFDSViewSectionProps) {
	const [fieldValues, setFieldValues] = React.useState<
		Record<IListField['fieldId'], IFDSField>
	>({});

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

			<ClayTable className="mb-0">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="list-visualization-mode-label-cell"
							headingCell
						>
							{Liferay.Language.get('list-element')}
						</ClayTable.Cell>

						<ClayTable.Cell
							className="list-visualization-mode-value-cell"
							headingCell
						>
							{Liferay.Language.get('field')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{LIST_VISUALIZATION_MODE_FIELDS.map((field) => (
						<ListField
							field={field}
							key={field.fieldId}
							modalProps={props}
							onSave={(fdsField) =>
								setFieldValues({
									...fieldValues,
									[field.fieldId]: fdsField,
								})
							}
							value={fieldValues[field.fieldId]}
						/>
					))}
				</ClayTable.Body>
			</ClayTable>
		</ClayLayout.ContentCol>
	);
}

interface IListFieldProps {
	field: IListField;
	modalProps: IFDSViewSectionProps;
	onSave: (fdsField: IFDSField) => void;
	value?: IFDSField;
}

function ListField({field, modalProps, onSave, value}: IListFieldProps) {
	const onClick = () => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFieldsModalContent
					{...modalProps}
					closeModal={closeModal}
					onSave={({createdFDSFields: [createdFDSField]}) =>
						onSave(createdFDSField)
					}
					savedFDSFields={value ? [value] : []}
					selectionMode="single"
				/>
			),
		});
	};

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="list-visualization-mode-label-cell">
				<strong>{field.label}</strong>
			</ClayTable.Cell>

			<ClayTable.Cell className="list-visualization-mode-value-cell">
				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<p
							className={classNames(
								'align-items-center d-flex mb-0',
								{'text-secondary': !value}
							)}
						>
							{value?.label ||
								Liferay.Language.get('not-assigned')}
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
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
