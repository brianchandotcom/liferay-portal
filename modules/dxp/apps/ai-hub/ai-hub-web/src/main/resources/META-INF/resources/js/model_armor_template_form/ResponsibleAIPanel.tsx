/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {RAI_FILTERS, RAI_LEVEL_OPTIONS} from './constants';
import {ModelArmorTemplate, RAILevel} from './types/ModelArmorTemplate';

interface IProps {
	readOnly: boolean;
	setField: <K extends keyof ModelArmorTemplate>(
		field: K,
		value: ModelArmorTemplate[K]
	) => void;
	values: ModelArmorTemplate;
}

const ResponsibleAIPanel: React.FC<IProps> = ({readOnly, setField, values}) => {
	return (
		<ClayPanel
			className="model-armor-template-form-rai"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="model-armor-template-form-header">
					<h2>{Liferay.Language.get('responsible-ai')}</h2>
				</div>

				{RAI_FILTERS.map(({field, helpText, label}) => (
					<div
						className="model-armor-template-form-rai-row"
						key={field}
					>
						<label htmlFor={field}>
							{label}

							<Icon
								className="lfr-portal-tooltip ml-1 text-secondary"
								data-title={helpText}
								focusable="false"
								role="dialog"
								symbol="question-circle-full"
								tabIndex={0}
							/>
						</label>

						<Picker
							className="model-armor-template-form-picker"
							disabled={readOnly}
							id={field}
							items={RAI_LEVEL_OPTIONS}
							onSelectionChange={(value) =>
								setField(field, value as RAILevel)
							}
							selectedKey={values[field] || 'none'}
						>
							{({description, label, value}) => (
								<Option key={value} textValue={label}>
									<div className="model-armor-template-form-option">
										<div className="model-armor-template-form-option-label">
											{label}
										</div>

										<div className="model-armor-template-form-option-description">
											{description}
										</div>
									</div>
								</Option>
							)}
						</Picker>
					</div>
				))}
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default ResponsibleAIPanel;
