/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {DETECTIONS} from './constants';
import {ModelArmorTemplate} from './types/ModelArmorTemplate';

interface IProps {
	readOnly: boolean;
	setField: <K extends keyof ModelArmorTemplate>(
		field: K,
		value: ModelArmorTemplate[K]
	) => void;
	values: ModelArmorTemplate;
}

const DetectionsPanel: React.FC<IProps> = ({readOnly, setField, values}) => {
	return (
		<ClayPanel
			className="model-armor-template-form-detections"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="model-armor-template-form-header">
					<h2>{Liferay.Language.get('detections')}</h2>
				</div>

				{DETECTIONS.map(({description, field, label}) => (
					<ClayForm.Group key={field}>
						<ClayCheckbox
							checked={values[field]}
							disabled={readOnly}
							label={label}
							onChange={(event) =>
								setField(field, event.target.checked)
							}
						/>

						<p className="model-armor-template-form-detection-description">
							{description}
						</p>
					</ClayForm.Group>
				))}
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default DetectionsPanel;
