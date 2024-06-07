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

export const TYPE_BOOLEAN = 'TYPE_BOOLEAN';
export const TYPE_STRING = 'TYPE_STRING';

const BOOLEAN_VALUE_ITEMS = [
	{label: Liferay.Language.get('true'), value: true},
	{label: Liferay.Language.get('false'), value: false},
];

const TYPE_ITEMS = [
	{label: Liferay.Language.get('string'), value: TYPE_STRING},
	{label: Liferay.Language.get('boolean'), value: TYPE_BOOLEAN},
];

interface IProps {
	disabled?: boolean;
	index: number;
	name: string;
	onAddClick: (index: number) => void;
	onAttributeChange: (index: number, updatedValue: Object) => void;
	onRemoveClick: (index: number) => void;
	portletNamespace: string;
	type: string;
	value: string | boolean;
}

export default function AttributeFields({
	disabled,
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
					disabled={disabled}

					// @ts-ignore

					errorMessage={errorMessage}
					id={nameId}

					// @ts-ignore

					label={
						<>
							{Liferay.Language.get('attribute')}

							<span className="sr-only">
								{Liferay.Language.get('spaces-are-not-allowed')}
							</span>
						</>
					}
					required
				>
					<ClayInput
						aria-describedby={`${nameId}fieldFeedback`}
						aria-required={true}
						data-testid={`testId_${index}`}
						defaultValue={name}
						disabled={disabled}
						id={nameId}
						onChange={(event) => {
							const value = event.target.value
								.split(/\s/)
								.join('');

							if (value.toLowerCase() === 'src') {
								setErrorMessage(
									Liferay.Language.get(
										'use-the-javascript-url-field'
									)
								);
							}
							else {
								setErrorMessage(null);
							}

							onAttributeChange(index, {
								name: value,
							});
						}}
						type="text"
						value={name}
					/>
				</FieldBase>
			</ClayLayout.Col>

			<ClayLayout.Col size={4}>
				<FieldBase
					className="mb-0"
					disabled={disabled}
					id={typeId}
					label={Liferay.Language.get('type')}
				>
					<Picker
						aria-labelledby="picker-label"
						defaultSelectedKey={type}
						disabled={disabled}
						id={typeId}
						items={TYPE_ITEMS}

						// @ts-ignore

						onSelectionChange={(type) =>
							onAttributeChange(index, {
								type,
								value:
									type !== TYPE_BOOLEAN
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
							disabled={disabled}
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
							disabled={disabled}
							id={valueId}
							items={BOOLEAN_VALUE_ITEMS}

							// @ts-ignore

							onSelectionChange={(value) =>
								onAttributeChange(index, {
									value: JSON.parse(value),
								})
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
						<label
							className={disabled ? 'disabled' : ''}
							htmlFor={valueId}
						>
							{Liferay.Language.get('value')}
						</label>

						<div>
							{index > 0 && (
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'remove-attribute'
									)}
									className="btn btn-primary btn-xs dm-field-repeatable-delete-button rounded-pill"
									disabled={disabled}
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
								disabled={disabled}
								onClick={() =>
									name.trim()
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
