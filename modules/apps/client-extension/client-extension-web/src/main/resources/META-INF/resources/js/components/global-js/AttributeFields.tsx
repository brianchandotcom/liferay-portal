/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {FieldBase} from 'frontend-js-components-web';
import React, {useState} from 'react';

interface IProps {
	index: number;
	name: string;
	onAddClick: (index: number) => void;
	onAttributeChange: (index: number, updatedValue: Object) => void;
	onRemoveClick: (index: number) => void;
	portletNamespace: string;
	type: string;
	value: string | boolean;
}

const BOOLEAN_VALUE_ITEMS = [
	{label: Liferay.Language.get('true'), value: true},
	{label: Liferay.Language.get('false'), value: false},
];

const TYPE_ITEMS = [
	{label: Liferay.Language.get('string'), value: 'String'},
	{label: Liferay.Language.get('boolean'), value: 'Boolean'},
];

export default function AttributeFields({
	index,
	name,
	onAddClick,
	onAttributeChange,
	onRemoveClick,
	portletNamespace,
	type,
	value,
}: IProps) {
	const nameId = `${portletNamespace}name_${index}`;
	const typeId = `${portletNamespace}type_${index}`;
	const valueId = `${portletNamespace}value_${index}`;

	const [errorMessage, setErrorMessage] = useState<string | null>(null);

	return (
		<ClayLayout.Row className="mb-3">
			<ClayLayout.Col
				className={classNames({'has-error': errorMessage})}
				size={4}
			>
				<FieldBase
					className="mb-0"
					errorMessage={errorMessage}
					id={nameId}
					label={Liferay.Language.get('attribute')}
					required
				>
					<ClayInput
						aria-required={true}
						defaultValue={name}
						id={nameId}
						onChange={({target}) => {
							if (target.value.toLowerCase().trim() === 'src') {
								setErrorMessage(
									Liferay.Language.get(
										'use-the-javascript-url-field-above'
									)
								);
							}
							else {
								setErrorMessage(null);

								onAttributeChange(index, {name: target.value});
							}
						}}
						type="text"
					/>
				</FieldBase>
			</ClayLayout.Col>

			<ClayLayout.Col size={4}>
				<FieldBase
					className="mb-0"
					id={typeId}
					label={Liferay.Language.get('type')}
				>
					<Picker
						aria-labelledby="picker-label"
						defaultSelectedKey={type}
						id={typeId}
						items={TYPE_ITEMS}
						onSelectionChange={(type) =>
							onAttributeChange(index, {
								type,
								value:
									type !== 'Boolean'
										? ''
										: BOOLEAN_VALUE_ITEMS[0].value,
							})
						}
					>
						{(item) => (
							<Option key={item.value}>{item.label}</Option>
						)}
					</Picker>
				</FieldBase>
			</ClayLayout.Col>

			<ClayLayout.Col size={4}>
				<ClayForm.Group className="d-flex flex-column-reverse justify-content-end">
					{type !== TYPE_BOOLEAN ? (
						<ClayInput
							id={valueId}
							onChange={({target}) =>
								onAttributeChange(index, {value: target.value})
							}
							type="text"
							value={value.toString()}
						/>
					) : (
						<Picker
							aria-labelledby="picker-label"
							defaultSelectedKey={value.toString()}
							id={valueId}
							items={BOOLEAN_VALUE_ITEMS}
							onSelectionChange={(value) =>
								onAttributeChange(index, {value})
							}
						>
							{(item) => (
								<Option key={item.value.toString()}>
									{item.label}
								</Option>
							)}
						</Picker>
					)}

					<div className="d-flex justify-content-between">
						<label htmlFor={valueId}>
							{Liferay.Language.get('value')}
						</label>

						<div>
							{index > 0 && (
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'remove-attribute'
									)}
									className="btn btn-primary btn-xs dm-field-repeatable-delete-button rounded-pill"
									onClick={() => onRemoveClick(index)}
									symbol="hr"
									title={Liferay.Language.get('remove')}
									type="button"
								/>
							)}

							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'add-new-attribute'
								)}
								className="btn btn-primary btn-xs dm-field-repeatable-add-button ml-1 rounded-pill"
								onClick={() =>
									name
										? onAddClick(index)
										: setErrorMessage(
												Liferay.Language.get(
													'attribute-field-is-required'
												)
										  )
								}
								symbol="plus"
								title={Liferay.Language.get('add')}
								type="button"
							/>
						</div>
					</div>
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}
