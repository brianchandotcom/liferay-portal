/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox, ClayInput, ClayToggle} from '@clayui/form';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {FormikErrors, FormikTouched} from 'formik';
import {FieldBase, InputLocalized} from 'frontend-js-components-web';
import React, {FocusEventHandler} from 'react';

import {maskExternalReferenceCode} from '../utils/externalReferenceCode';
import {GUARDRAIL_TYPE_OPTIONS} from './constants';
import {ModelArmorTemplate} from './types/ModelArmorTemplate';

interface IProps {
	errors: FormikErrors<ModelArmorTemplate>;
	handleBlur: FocusEventHandler;
	readOnly: boolean;
	setField: <K extends keyof ModelArmorTemplate>(
		field: K,
		value: ModelArmorTemplate[K]
	) => void;
	setFieldTouched: (field: string, isTouched?: boolean) => void;
	touched: FormikTouched<ModelArmorTemplate>;
	values: ModelArmorTemplate;
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
			className="model-armor-template-form-details"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="model-armor-template-form-header">
					<h2>{Liferay.Language.get('details')}</h2>

					<ClayToggle
						disabled={readOnly}
						label={Liferay.Language.get('active')}
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
					placeholder={Liferay.Language.get('title')}
					required
					translations={values.title_i18n || {}}
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
								maskExternalReferenceCode(event.target.value)
							)
						}
						required
						type="text"
						value={values.externalReferenceCode}
					/>
				</FieldBase>

				<ClayForm.Group>
					<label htmlFor="description">
						{Liferay.Language.get('description')}
					</label>

					<textarea
						className="form-control"
						disabled={readOnly}
						id="description"
						onChange={(event) =>
							setField('description', event.target.value)
						}
						rows={3}
						value={values.description}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="guardrailType">
						{Liferay.Language.get('guardrail-type')}

						<Icon
							className="lfr-portal-tooltip ml-1 text-secondary"
							data-title={Liferay.Language.get(
								'guardrail-type-help'
							)}
							focusable="false"
							role="dialog"
							symbol="question-circle-full"
							tabIndex={0}
						/>
					</label>

					<Picker
						className="model-armor-template-form-picker"
						disabled={readOnly}
						id="guardrailType"
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
						checked={values.multilanguageDetectionEnabled}
						disabled={readOnly}
						label={Liferay.Language.get('multilanguage-detection')}
						onChange={(event) =>
							setField(
								'multilanguageDetectionEnabled',
								event.target.checked
							)
						}
					/>
				</ClayForm.Group>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default DetailsPanel;
