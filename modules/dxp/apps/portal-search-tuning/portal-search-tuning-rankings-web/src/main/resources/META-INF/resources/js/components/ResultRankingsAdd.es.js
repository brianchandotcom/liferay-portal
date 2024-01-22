/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {
	FeatureIndicator,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import {navigate, openSelectionModal} from 'frontend-js-web';
import React, {useContext, useEffect, useRef, useState} from 'react';

import NamespaceContext from '../NamespaceContext';
import {SCOPE_TYPES} from '../utils/constants.es';
import {sub} from '../utils/language.es';
import ScopeSelect from './scope/ScopeSelect.es';

const SCOPE_INFO = {
	[SCOPE_TYPES.EVERYTHING]: {
		description: Liferay.Language.get(
			'result-rankings-scope-everything-help'
		),
		label: Liferay.Language.get('everything'),
	},
	[SCOPE_TYPES.SITE]: {
		description: Liferay.Language.get('result-rankings-scope-site-help'),
		label: Liferay.Language.get('site'),
		showBetaBadge: true,
	},
	[SCOPE_TYPES.SXP_BLUEPRINT]: {
		description: Liferay.Language.get(
			'result-rankings-scope-blueprint-help'
		),
		label: Liferay.Language.get('blueprint'),
		showBetaBadge: true,
	},
};

function ResultRankingsAdd({
	cancelURL,
	enterpriseSearchEnabled = true,
	formName,
	selectSitesURL,
}) {
	const [errors, setErrors] = useState({});
	const [groupName, setGroupName] = useState('');
	const [scopeType, setScopeType] = useState(SCOPE_TYPES.EVERYTHING);
	const [scope, setScope] = useState('');
	const [scopeDropdownActive, setScopeDropdownActive] = useState(false);
	const [searchQuery, setSearchQuery] = useState('');
	const [touched, setTouched] = useState({});

	const alignElementRef = useRef();

	const {namespace} = useContext(NamespaceContext);

	const scopeItemsList = enterpriseSearchEnabled
		? [SCOPE_TYPES.EVERYTHING, SCOPE_TYPES.SITE, SCOPE_TYPES.SXP_BLUEPRINT]
		: [SCOPE_TYPES.EVERYTHING, SCOPE_TYPES.SITE];

	const _getErrors = (searchQuery, scopeType, scope) => {
		const errors = {};

		if (!searchQuery.trim()) {
			errors['searchQuery'] = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('search-query')]
			);
		}

		if (scopeType !== SCOPE_TYPES.EVERYTHING && !scope) {
			errors['scope'] = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('scope')]
			);
		}

		return errors;
	};

	const _handleBlur = (fieldName) => {
		setTouched({...touched, [fieldName]: true});
	};

	const _handleCancel = () => {
		navigate(cancelURL);
	};

	const _handleSearchQueryChange = (event) => {
		setSearchQuery(event.target.value);
	};

	const _handleScopeChange = (value) => {
		setScope(value);
	};

	const _handleScopeDropdownChange = () => {
		setScopeDropdownActive(!scopeDropdownActive);
	};

	const _handleScopeTypeChange = (value) => {
		setScopeType(value);
		setScope('');
		setGroupName('');
		setTouched({...touched, scope: false});

		setScopeDropdownActive(false);
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		const newErrors = _getErrors(searchQuery, scopeType, scope);

		if (Object.keys(newErrors).length) {
			setErrors(newErrors);

			setTouched({scope: true, searchQuery: true});

			return;
		}

		submitForm(document[namespace + formName]);
	};

	useEffect(() => {
		setErrors(_getErrors(searchQuery, scopeType, scope));
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
					onBlur={() => _handleBlur('searchQuery')}
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

			{Liferay.FeatureFlags['LPD-6368'] && (
				<ClayForm.Group>
					<label htmlFor="searchScopeType">
						{Liferay.Language.get('scope')}

						<ClayIcon
							className="c-ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<ClayButton
						aria-label={Liferay.Language.get('scope')}
						className="form-control form-control-select"
						displayType="unstyled"
						id="searchScopeType"
						onClick={_handleScopeDropdownChange}
						ref={alignElementRef}
					>
						{SCOPE_INFO[scopeType].label}
					</ClayButton>

					<ClayDropDown.Menu
						active={scopeDropdownActive}
						alignElementRef={alignElementRef}
						closeOnClickOutside
						onActiveChange={setScopeDropdownActive}
						style={{
							maxWidth: '100%',
							width:
								alignElementRef.current &&
								alignElementRef.current.clientWidth + 'px',
						}}
					>
						<ClayDropDown.ItemList items={scopeItemsList}>
							{(item) => (
								<ClayDropDown.Item
									key={item}
									onClick={() => {
										_handleScopeTypeChange(item);
									}}
								>
									<div className="autofit-col-expand">
										<div className="align-items-center d-flex list-group-text text-dark">
											{SCOPE_INFO[item].label}

											{SCOPE_INFO[item].showBetaBadge && (
												<span className="c-ml-1">
													<FeatureIndicator type="beta" />
												</span>
											)}
										</div>

										<div className="c-mt-0 list-group-subtext text-2">
											{SCOPE_INFO[item].description}
										</div>
									</div>
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown.Menu>

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
				<ClayForm.Group
					className={getCN({
						'has-error': !!errors.scope && touched.scope,
					})}
				>
					<label htmlFor="groupName">
						{Liferay.Language.get('select-site')}

						<ClayIcon
							className="c-ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem
							className="d-none d-sm-block"
							prepend
						>
							<ClayInput
								disabled
								id="groupName"
								type="text"
								value={groupName}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButton
								displayType="secondary"
								onClick={() => {
									openSelectionModal({
										id: `${namespace}selectSite`,
										onClose: () => {
											_handleBlur('scope');
										},
										onSelect: (selectedItem) => {
											_handleBlur('scope');

											if (!selectedItem) {
												return;
											}

											setGroupName(
												selectedItem.groupdescriptivename
											);
											setScope(
												selectedItem.groupexternalreferencecode
											);
										},
										selectEventName: `${namespace}selectSite`,
										title: Liferay.Language.get(
											'select-site'
										),
										url: selectSitesURL,
									});
								}}
								type="button"
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					{errors.scope && touched.scope && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{errors.scope}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}

					<input
						id={`${namespace}groupExternalReferenceCode`}
						key="groupExternalReferenceCode"
						name={`${namespace}groupExternalReferenceCode`}
						readOnly
						type="hidden"
						value={scope}
					/>
				</ClayForm.Group>
			)}

			{scopeType === SCOPE_TYPES.SXP_BLUEPRINT && (
				<>
					<ScopeSelect
						disabled={false}
						error={errors.scope}
						fetchItemsUrl={`${
							window.location.origin
						}${Liferay.ThemeDisplay.getPathContext()}/o/search-experiences-rest/v1.0/sxp-blueprints`}
						locator={{
							id: 'externalReferenceCode',
							label: 'title',
						}}
						onBlur={() => _handleBlur('scope')}
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

export default function ({
	cancelURL,
	enterpriseSearchEnabled,
	formName,
	learnResources,
	namespace = '',
	selectSitesURL,
}) {
	return (
		<LearnResourcesContext.Provider value={learnResources}>
			<NamespaceContext.Provider value={{namespace}}>
				<ResultRankingsAdd
					cancelURL={cancelURL}
					enterpriseSearchEnabled={enterpriseSearchEnabled}
					formName={formName}
					selectSitesURL={selectSitesURL}
				/>
			</NamespaceContext.Provider>
		</LearnResourcesContext.Provider>
	);
}
