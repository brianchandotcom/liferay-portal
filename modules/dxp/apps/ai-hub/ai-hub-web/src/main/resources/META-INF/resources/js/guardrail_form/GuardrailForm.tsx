/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import Link from '@clayui/link';
import React from 'react';

import './GuardrailForm.scss';
import Toolbar from '../components/ToolBar';
import DetailsPanel from './DetailsPanel';
import DetectionsPanel from './DetectionsPanel';
import ResponsibleAIPanel from './ResponsibleAIPanel';
import {useGuardrailForm} from './hooks/useGuardrailForm';

const FORM_ID = 'guardrailForm';

interface IProps {
	accountEntryExternalReferenceCode: string;
	backURL: string;
	externalReferenceCode: string;
	readOnly: boolean;
}

export default function GuardrailForm({
	backURL,
	externalReferenceCode,
	readOnly,
}: IProps) {
	const {
		errors,
		handleBlur,
		handleSubmit,
		isSubmitting,
		setField,
		setFieldTouched,
		touched,
		values,
	} = useGuardrailForm({
		externalReferenceCode,
	});

	return (
		<>
			<Toolbar
				backURL={backURL}
				title={
					externalReferenceCode
						? Liferay.Language.get('edit-guardrail')
						: Liferay.Language.get('new-guardrail')
				}
			>
				<Toolbar.Item>
					<Link
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-label={Liferay.Language.get('save')}
						disabled={isSubmitting || readOnly}
						form={FORM_ID}
						size="sm"
						type="submit"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>

			<ClayLayout.ContainerFluid className="guardrail-form">
				<ClayForm id={FORM_ID} onSubmit={handleSubmit}>
					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<DetailsPanel
								errors={errors}
								handleBlur={handleBlur}
								readOnly={readOnly}
								setField={setField}
								setFieldTouched={setFieldTouched}
								touched={touched}
								values={values}
							/>

							{values.guardrailType === 'input' && (
								<DetectionsPanel
									readOnly={readOnly}
									setField={setField}
									values={values}
								/>
							)}

							{values.guardrailType === 'output' && (
								<ResponsibleAIPanel
									readOnly={readOnly}
									setField={setField}
									values={values}
								/>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
