/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {LearnMessage} from 'frontend-js-components-web';
import React from 'react';

import {
	PORTAL_TOOLTIP_TRIGGER_CLASS,
	SCOPE_TYPES,
} from '../../utils/constants.es';
import SelectScope from './SelectScope.es';

export default function Scope({
	disabled,
	initialGroupExternalReferenceCode,
	initialSxpBlueprintExternalReferenceCode,
	onChange,
	onResultsUpdate,
	selectedType,
	touched,
}) {
	const _handleScopeTypeChange = (type) =>
		onChange({
			touched: false,
			type,
			value: '',
		});

	const _renderRadioButton = (value, helptext, label) => (
		<div className="custom-control custom-radio">
			<label>
				<input
					checked={value === selectedType}
					className="custom-control-input"
					disabled={disabled}
					onChange={() => _handleScopeTypeChange(value)}
					role="radio"
					type="radio"
				/>

				<span className="custom-control-label">
					<span className="custom-control-label-text">
						{label}

						<span
							className={getCN(
								'input-label-help-icon',
								'text-secondary',
								PORTAL_TOOLTIP_TRIGGER_CLASS
							)}
							data-title={helptext}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</span>
				</span>
			</label>
		</div>
	);

	return (
		<div className="results-rankings-scope-selector">
			{_renderRadioButton(
				SCOPE_TYPES.EVERYWHERE,
				Liferay.Language.get('result-rankings-scope-everything-help'),
				Liferay.Language.get('everything')
			)}

			{Liferay.FeatureFlags['LPS-157988'] && (
				<>
					{_renderRadioButton(
						SCOPE_TYPES.SITE,
						Liferay.Language.get('result-rankings-scope-site-help'),
						Liferay.Language.get('site')
					)}

					<SelectScope
						disabled={disabled}
						fetchByURL="/o/headless-admin-user/v1.0/sites/by-external-reference-code/"
						fetchURL="/o/headless-admin-user/v1.0/sites"
						hidden={selectedType !== SCOPE_TYPES.SITE}
						initialSelected={initialGroupExternalReferenceCode}
						locator={{
							id: 'externalReferenceCode',
							label: 'descriptiveName',
						}}
						onSelect={onChange}
						title={Liferay.Language.get('select-site')}
						touched={touched}
						type={SCOPE_TYPES.SITE}
					/>
				</>
			)}

			{Liferay.FeatureFlags['LPS-159650'] && (
				<>
					{_renderRadioButton(
						SCOPE_TYPES.SXP_BLUEPRINT,
						Liferay.Language.get(
							'result-rankings-scope-blueprint-help'
						),
						Liferay.Language.get('blueprint')
					)}

					<SelectScope
						disabled={disabled}
						fetchByURL="/o/search-experiences-rest/v1.0/sxp-blueprints/by-external-reference-code/"
						fetchURL="/o/search-experiences-rest/v1.0/sxp-blueprints"
						hidden={selectedType !== SCOPE_TYPES.SXP_BLUEPRINT}
						initialSelected={
							initialSxpBlueprintExternalReferenceCode
						}
						locator={{
							id: 'externalReferenceCode',
							label: 'title',
						}}
						onSelect={onChange}
						title={Liferay.Language.get('select-blueprint')}
						touched={touched}
						type={SCOPE_TYPES.SXP_BLUEPRINT}
					/>
				</>
			)}

			<div className="c-mt-3 sheet-text text-3">
				<span className="text-secondary">
					{Liferay.Language.get('result-rankings-scope-help')}

					<LearnMessage
						className="c-ml-1"
						resource="portal-search-tuning-rankings-web"
						resourceKey="result-rankings"
					/>
				</span>
			</div>

			<ClayButton
				disabled={disabled}
				displayType="secondary"
				onClick={onResultsUpdate}
			>
				{Liferay.Language.get('update-results')}
			</ClayButton>
		</div>
	);
}
