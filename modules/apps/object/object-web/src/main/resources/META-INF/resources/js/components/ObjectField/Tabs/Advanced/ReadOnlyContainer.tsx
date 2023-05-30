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

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {
	Card,
	ExpressionBuilder,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import React from 'react';

interface ReadOnlyContainerProps {
	disabled?: boolean;
	readOnlySidebarElements: SidebarCategory[];
	requiredField: boolean;
	setValues: (value: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function ReadOnlyContainer({
	disabled,
	readOnlySidebarElements,
	requiredField,
	setValues,
	values,
}: ReadOnlyContainerProps) {
	const setReadOnly = (value: ReadOnlyFieldValue) => {
		setValues({
			readOnly: value,
			required:
				value === 'true' || value === 'conditional'
					? false
					: requiredField,
		});
	};

	return (
		<>
			{values.readOnly && (
				<Card
					disabled={disabled}
					title={Liferay.Language.get('read-only')}
				>
					<ClayRadioGroup defaultValue={values?.readOnly}>
						<ClayRadio
							disabled={disabled}
							label={Liferay.Language.get('true')}
							onClick={() => setReadOnly('true')}
							value="true"
						/>

						<ClayRadio
							disabled={disabled}
							label={Liferay.Language.get('false')}
							onClick={() => setReadOnly('false')}
							value="false"
						/>

						<ClayRadio
							disabled={disabled}
							label={Liferay.Language.get('conditional')}
							onClick={() => setReadOnly('conditional')}
							value="conditional"
						/>
					</ClayRadioGroup>

					{values.readOnly === 'conditional' && (
						<ExpressionBuilder
							feedbackMessage={Liferay.Language.get(
								'use-expressions-to-create-a-condition'
							)}
							label={Liferay.Language.get('expression-builder')}
							onChange={({target: {value}}) => {
								setValues({
									readOnlyConditionExpression: value,
								});
							}}
							onOpenModal={() => {
								const parentWindow = Liferay.Util.getOpener();

								parentWindow.Liferay.fire(
									'openExpressionBuilderModal',
									{
										eventSidebarElements: readOnlySidebarElements,
										header: Liferay.Language.get(
											'expression-builder'
										),
										onSave: (script: string) => {
											setValues({
												readOnlyConditionExpression: script,
											});
										},
										placeholder: `<#-- ${Liferay.Language.get(
											'create-the-condition-of-the-read-only-state-using-expression-builder'
										)} -->`,
										required: false,
										source:
											values.readOnlyConditionExpression ??
											'',
										validateExpressionURL: '',
									}
								);
							}}
							placeholder={Liferay.Language.get(
								'create-an-expression'
							)}
							value={values.readOnlyConditionExpression ?? ''}
						/>
					)}
				</Card>
			)}
		</>
	);
}
