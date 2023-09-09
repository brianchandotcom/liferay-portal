/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {LearnMessage} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {PORTAL_TOOLTIP_TRIGGER_CLASS, SCOPE_TYPES} from '../utils/constants.es';
import {sub} from '../utils/language.es';
import ScopeSelect from './scope/ScopeSelect.es';

const SCOPE_LABELS = {
	[SCOPE_TYPES.EVERYWHERE]: Liferay.Language.get('everywhere'),
	[SCOPE_TYPES.SITE]: Liferay.Language.get('site'),
	[SCOPE_TYPES.SXP_BLUEPRINT]: Liferay.Language.get('blueprint'),
};

export default function ResultRankingsAdd({cancelUrl, formName, namespace}) {
	const [searchQuery, setSearchQuery] = useState('');
	const [errors, setErrors] = useState({});
	const [touched, setTouched] = useState({});
	const [scopeType, setScopeType] = useState(SCOPE_TYPES.EVERYWHERE);
	const [scope, setScope] = useState('');

	const _getScopeTypeOptions = () => {
		const options = [SCOPE_TYPES.EVERYWHERE];

		if (Liferay.FeatureFlags['LPS-157988']) {
			options.push(SCOPE_TYPES.SITE);
		}

		if (Liferay.FeatureFlags['LPS-159650']) {
			options.push(SCOPE_TYPES.SXP_BLUEPRINT);
		}

		return options;
	};

	const _handleBlur = (fieldName) => () => {
		setTouched({...touched, [fieldName]: true});
	};

	const _handleCancel = () => {
		navigate(cancelUrl);
	};

	const _handleSearchQueryChange = (event) => {
		setSearchQuery(event.target.value);
	};

	const _handleScopeChange = (value) => {
		setScope(value);
	};

	const _handleScopeTypeChange = (value) => {
		setScopeType(value);
		setScope('');
		setTouched({...touched, scope: false});
	};

	const _handleSubmit = () => {
		if (!Object.keys(errors).length) {
			submitForm(document[formName]);
		}
	};

	useEffect(() => {
		const _handleValidate = () => {
			const errors = {};

			if (!searchQuery.trim()) {
				errors['searchQuery'] = sub(
					Liferay.Language.get('the-x-field-is-required'),
					[Liferay.Language.get('search-query')]
				);
			}

			if (scopeType !== SCOPE_TYPES.EVERYWHERE && !scope) {
				errors['scope'] = Liferay.Language.get(
					'this-field-is-required'
				);
			}

			setErrors(errors);
		};

		_handleValidate();
	}, [scope, searchQuery, scopeType]);

	return (
		<div className="panel-group panel-group-flush">
			<div className="sheet-text">
				{Liferay.Language.get(
					'customize-how-users-see-results-for-a-given-search-query'
				)}
			</div>

			<ClayForm.Group
				className={getCN({
					'has-error': !!errors.searchQuery && touched.searchQuery,
				})}
			>
				<label htmlFor="searchQuery">
					{Liferay.Language.get('search-query')}

					<ClayIcon
						className="c-ml-1 reference-mark"
						symbol="asterisk"
					/>
				</label>

				<ClayInput
					id="searchQuery"
					name={`${namespace}keywords`}
					onBlur={_handleBlur('searchQuery')}
					onChange={_handleSearchQueryChange}
					type="text"
					value={searchQuery}
				/>

				{errors.searchQuery && touched.searchQuery && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{errors.searchQuery}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			{(Liferay.FeatureFlags['LPS-159650'] ||
				Liferay.FeatureFlags['LPS-157988']) && (
				<ClayForm.Group>
					<label htmlFor="searchScope">
						{Liferay.Language.get('scope')}

						<ClayIcon
							className="c-ml-1 reference-mark"
							symbol="asterisk"
						/>

						<span
							className={getCN(
								'input-label-help-icon',
								PORTAL_TOOLTIP_TRIGGER_CLASS
							)}
							data-title={Liferay.Language.get(
								'result-rankings-scope-help'
							)}
						>
							<ClayIcon
								className="c-ml-1 text-secondary"
								symbol="question-circle-full"
							/>
						</span>
					</label>

					<ClayDropDown
						closeOnClick={true}
						closeOnClickOutside
						menuWidth="full"
						trigger={
							<button className="form-control form-control-select">
								{SCOPE_LABELS[scopeType]}
							</button>
						}
					>
						<ClayDropDown.ItemList items={_getScopeTypeOptions()}>
							{(item) => (
								<ClayDropDown.Item
									key={item}
									onClick={() => {
										_handleScopeTypeChange(item);
									}}
								>
									{SCOPE_LABELS[item]}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<div className="c-mt-1 sheet-text text-3">
						<span className="text-secondary">
							{Liferay.Language.get('result-rankings-scope-help')}

							<LearnMessage
								className="c-ml-1"
								resource="portal-search-tuning-rankings-web"
								resourceKey="result-rankings"
							/>
						</span>
					</div>
				</ClayForm.Group>
			)}

			{scopeType === SCOPE_TYPES.SITE && (
				<>
					<ScopeSelect
						disabled={false}
						error={errors.scope}
						fetchItemsUrl="/o/headless-admin-user/v1.0/sites"
						hidden={scopeType !== SCOPE_TYPES.SITE}
						locator={{
							id: 'externalReferenceCode',
							label: 'descriptiveName',
						}}
						onBlur={_handleBlur('scope')}
						onSelect={_handleScopeChange}
						selected={scope}
						title={Liferay.Language.get('select-site')}
						touched={touched.scope}
						type={SCOPE_TYPES.SITE}
					/>

					<input
						id={`${namespace}groupExternalReferenceCode`}
						key="groupExternalReferenceCode"
						name={`${namespace}groupExternalReferenceCode`}
						readOnly
						type="hidden"
						value={scope}
					/>
				</>
			)}

			{scopeType === SCOPE_TYPES.SXP_BLUEPRINT && (
				<>
					<ScopeSelect
						disabled={false}
						error={errors.scope}
						fetchItemsUrl="/o/search-experiences-rest/v1.0/sxp-blueprints"
						locator={{
							id: 'externalReferenceCode',
							label: 'title',
						}}
						onBlur={_handleBlur('scope')}
						onSelect={_handleScopeChange}
						selected={scope}
						title={Liferay.Language.get('select-blueprint')}
						touched={touched.scope}
						type={SCOPE_TYPES.SXP_BLUEPRINT}
					/>

					<input
						id={`${namespace}sxpBlueprintExternalReferenceCode`}
						key="sxpBlueprintExternalReferenceCode"
						name={`${namespace}sxpBlueprintExternalReferenceCode`}
						readOnly
						type="hidden"
						value={scope}
					/>
				</>
			)}

			<div className="sheet-footer">
				<ClayButton
					displayType="primary"
					onClick={_handleSubmit}
					type="submit"
				>
					{Liferay.Language.get('customize-results')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={_handleCancel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</div>
	);
}
