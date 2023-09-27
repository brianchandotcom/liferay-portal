/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ActionError} from '..';
import ClayForm from '@clayui/form';
import {
	Card,
	ExpressionBuilder,
	Toggle,
} from '@liferay/object-js-components-web';
import React from 'react';

interface ConditionContainerProps {
	errors: ActionError;
	setValues: (values: Partial<ObjectAction>) => void;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

export function ConditionContainer({
	errors,
	setValues,
	validateExpressionURL,
	values,
}: ConditionContainerProps) {
	const handleSaveCondition = (conditionExpression?: string) => {
		setValues({conditionExpression});
	};

	return (
		<Card
			disabled={values.objectActionTriggerKey === 'standalone'}
			title={Liferay.Language.get('condition')}
		>
			<ClayForm.Group>
				<Toggle
					disabled={values.objectActionTriggerKey === 'standalone'}
					label={Liferay.Language.get('enable-condition')}
					name="condition"
					onToggle={(enable) =>
						setValues({
							conditionExpression: enable ? '' : undefined,
						})
					}
					toggled={!(values.conditionExpression === undefined)}
				/>
			</ClayForm.Group>

			{values.conditionExpression !== undefined && (
				<ExpressionBuilder
					error={errors.conditionExpression}
					feedbackMessage={Liferay.Language.get(
						'use-expressions-to-create-a-condition'
					)}
					label={Liferay.Language.get('expression-builder')}
					name="conditionExpression"
					onChange={({target: {value}}) =>
						setValues({conditionExpression: value})
					}
					onOpenModal={() => {
						const parentWindow = Liferay.Util.getOpener();

						parentWindow.Liferay.fire(
							'openExpressionBuilderModal',
							{
								onSave: handleSaveCondition,
								required: true,
								source: values.conditionExpression,
								validateExpressionURL,
							}
						);
					}}
					placeholder={Liferay.Language.get('create-an-expression')}
					value={values.conditionExpression as string}
				/>
			)}
		</Card>
	);
}
