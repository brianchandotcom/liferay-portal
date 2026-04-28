/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox, ClayInput, ClayToggle} from '@clayui/form';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {FormikErrors} from 'formik';
import React from 'react';

import {GUARDRAIL_TYPE_OPTIONS} from './constants';
import {ModelArmorTemplate} from './types/ModelArmorTemplate';

interface IProps {
	errors: FormikErrors<ModelArmorTemplate>;
	handleChange: (event: React.ChangeEvent<any>) => void;
	setField: <K extends keyof ModelArmorTemplate>(
		field: K,
		value: ModelArmorTemplate[K]
	) => void;
	values: ModelArmorTemplate;
}

const DetailsPanel: React.FC<IProps> = ({
	errors,
	handleChange,
	setField,
	values,
}) => {
	return (
		<ClayPanel
			className="model-armor-template-form-details"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="model-armor-template-form-header">
					<h2>{Liferay.Language.get('details')}</h2>

					<ClayToggle
						label={Liferay.Language.get('active')}
						onToggle={(toggled) => setField('active', toggled)}
						toggled={values.active}
					/>
				</div>

				<ClayForm.Group>
					<label htmlFor="title">
						{Liferay.Language.get('title')}

						<span className="ml-1 reference-mark text-warning">
							<Icon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						className={errors.title ? 'is-invalid' : ''}
						id="title"
						name="title"
						onChange={handleChange}
						required
						type="text"
						value={values.title || ''}
					/>

					{errors.title && (
						<div className="d-block invalid-feedback">
							{errors.title}
						</div>
					)}
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="externalReferenceCode">
						{Liferay.Language.get('external-reference-code')}

						<span className="ml-1 reference-mark text-warning">
							<Icon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						className={
							errors.externalReferenceCode ? 'is-invalid' : ''
						}
						id="externalReferenceCode"
						name="externalReferenceCode"
						onChange={handleChange}
						required
						type="text"
						value={values.externalReferenceCode || ''}
					/>

					{errors.externalReferenceCode && (
						<div className="d-block invalid-feedback">
							{errors.externalReferenceCode}
						</div>
					)}
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="description">
						{Liferay.Language.get('description')}
					</label>

					<textarea
						className="form-control"
						id="description"
						name="description"
						onChange={handleChange}
						rows={3}
						value={values.description || ''}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="guardrailType">
						{Liferay.Language.get('guardrail-type')}

						<span
							className="model-armor-template-form-help-icon"
							data-tooltip-align="top"
							title={Liferay.Language.get('guardrail-type-help')}
						>
							<Icon symbol="question-circle-full" />
						</span>
					</label>

					<Picker
						className="model-armor-template-form-picker"
						items={GUARDRAIL_TYPE_OPTIONS}
						onSelectionChange={(value) =>
							setField('guardrailType', value as string)
						}
						selectedKey={values.guardrailType}
					>
						{({label, value}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						checked={values.multiLanguageDetectionEnabled}
						label={Liferay.Language.get('multi-language-detection')}
						onChange={() =>
							setField(
								'multiLanguageDetectionEnabled',
								!values.multiLanguageDetectionEnabled
							)
						}
					/>
				</ClayForm.Group>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default DetailsPanel;
