/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {FormikErrors, FormikTouched} from 'formik';
import {FieldBase, InputLocalized} from 'frontend-js-components-web';
import React, {FocusEventHandler} from 'react';

import {AgentDefinition} from './types/AgentDefinition';

interface IProps {
	errors: FormikErrors<AgentDefinition>;
	handleBlur: FocusEventHandler;
	readOnly: boolean;
	setField: <K extends keyof AgentDefinition>(
		field: K,
		value: AgentDefinition[K]
	) => void;
	setFieldTouched: (field: string, isTouched?: boolean) => void;
	touched: FormikTouched<AgentDefinition>;
	values: AgentDefinition;
}

const DetailsPanel: React.FC<IProps> = ({
	errors,
	handleBlur,
	readOnly,
	setField,
	setFieldTouched,
	touched,
	values,
}) => {
	return (
		<ClayPanel
			className="agent-definition-form-details"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('details')}</h2>

					<ClayToggle
						disabled={readOnly}
						label={Liferay.Language.get('enable-agent')}
						onToggle={(toggled) => setField('active', toggled)}
						toggled={values.active}
					/>
				</div>

				<InputLocalized
					disabled={readOnly}
					error={
						touched.title_i18n
							? (errors.title_i18n as string | undefined)
							: undefined
					}
					id="title"
					label={Liferay.Language.get('title')}
					name="title_i18n"
					onBlur={() => setFieldTouched('title_i18n', true)}
					onChange={(value) => setField('title_i18n', value)}
					onSelectedLocaleChange={() => {}}
					placeholder={Liferay.Language.get('title')}
					required
					translations={
						(values.title_i18n as Record<string, string>) || {}
					}
				/>

				<FieldBase
					errorMessage={
						touched.externalReferenceCode
							? errors.externalReferenceCode
							: undefined
					}
					id="externalReferenceCode"
					label={Liferay.Language.get('external-reference-code')}
					required
				>
					<ClayInput
						className={
							touched.externalReferenceCode &&
							errors.externalReferenceCode
								? 'is-invalid'
								: ''
						}
						disabled={readOnly}
						id="externalReferenceCode"
						name="externalReferenceCode"
						onBlur={handleBlur}
						onChange={(event) =>
							setField(
								'externalReferenceCode',
								event.target.value
							)
						}
						placeholder={Liferay.Language.get(
							'external-reference-code'
						)}
						required
						type="text"
						value={values.externalReferenceCode}
					/>
				</FieldBase>

				<FieldBase
					errorMessage={
						touched.description ? errors.description : undefined
					}
					id="description"
					label={Liferay.Language.get('description')}
					required
				>
					<textarea
						className={`form-control ${
							touched.description && errors.description
								? 'is-invalid'
								: ''
						}`}
						disabled={readOnly}
						id="description"
						name="description"
						onBlur={handleBlur}
						onChange={(event) =>
							setField('description', event.target.value)
						}
						placeholder={Liferay.Language.get('add-a-description')}
						rows={4}
						value={values.description}
					/>
				</FieldBase>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default DetailsPanel;
