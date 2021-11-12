/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import getCN from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';
import {
	BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION,
	DEFAULT_ADVANCED_CONFIGURATION,
	DEFAULT_BASELINE_SXP_ELEMENTS,
	DEFAULT_HIGHLIGHT_CONFIGURATION,
	DEFAULT_PARAMETER_CONFIGURATION,
	DEFAULT_SORT_CONFIGURATION,
} from '../utils/data';
import {fetchData} from '../utils/fetch';
import {FRAMEWORK_TYPES} from '../utils/frameworkTypes';
import {getSXPBlueprintForm, getSXPElementOutput} from '../utils/utils';

const DEFAULT_SELECTED_BASELINE_SXP_ELEMENTS = DEFAULT_BASELINE_SXP_ELEMENTS.map(
	getSXPBlueprintForm
);

const FrameworkCard = ({
	checked,
	children,
	description,
	imagePath,
	onChange,
	title,
	value,
}) => {
	return (
		<ClayCard
			className={checked ? 'selected' : ''}
			displayType="file"
			onClick={onChange}
			selectable
		>
			<ClayRadio checked={checked} onChange={onChange} value={value}>
				<ClayCard.AspectRatio className="card-item-first">
					<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid">
						<img alt={title} src={imagePath} />
					</div>
				</ClayCard.AspectRatio>
			</ClayRadio>

			<ClayCard.Body>
				<ClayCard.Row>
					<div className="autofit-col autofit-col-expand">
						<section className="autofit-section">
							<ClayCard.Description displayType="title">
								{title}
							</ClayCard.Description>

							<ClayCard.Description
								displayType="subtitle"
								truncate={false}
							>
								{description}
							</ClayCard.Description>

							{children}
						</section>
					</div>
				</ClayCard.Row>
			</ClayCard.Body>
		</ClayCard>
	);
};

/**
 * A slightly modified version of frontend-js-web module's SimpleInputModal
 * React component to include a description field.
 */
const AddModal = ({
	contextPath,
	closeModal,
	defaultLocale,
	dialogTitle,
	editSXPBlueprintURL,
	initialVisible,
	keywordQueryContributors = [],
	modelPrefilterContributors = [],
	portletNamespace,
	queryPrefilterContributors = [],
	redirectURL = '',
	searchableTypes = [],
	submitButtonLabel = Liferay.Language.get('create'),
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [framework, setFramework] = useState(FRAMEWORK_TYPES.ALL);
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [inputValue, setInputValue] = useState('');
	const [descriptionInputValue, setDescriptionInputValue] = useState('');

	const handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || DEFAULT_ERROR);

		setLoadingResponse(false);
	};

	const _getConfiguration = () => ({
		advanced: DEFAULT_ADVANCED_CONFIGURATION,
		aggregationConfiguration: {},
		facet: {},
		general: {
			clauseContributorsExcludes:
				framework === FRAMEWORK_TYPES.ALL
					? []
					: BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION.excludes,
			clauseContributorsIncludes:
				framework === FRAMEWORK_TYPES.ALL
					? [
							...keywordQueryContributors,
							...modelPrefilterContributors,
							...queryPrefilterContributors,
					  ]
					: BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION.includes,
			searchableAssetTypes: searchableTypes,
		},
		highlight: DEFAULT_HIGHLIGHT_CONFIGURATION,
		parameters: DEFAULT_PARAMETER_CONFIGURATION,
		queryConfiguration: {
			applyIndexerClauses: framework === FRAMEWORK_TYPES.ALL,
			queryEntries:
				framework === FRAMEWORK_TYPES.BASELINE
					? DEFAULT_SELECTED_BASELINE_SXP_ELEMENTS.map(
							getSXPElementOutput
					  )
					: [],
		},
		sortConfiguration: DEFAULT_SORT_CONFIGURATION,
	});

	const _handleSubmit = (event) => {
		event.preventDefault();

		fetch('/o/search-experiences-rest/v1.0/sxp-blueprints', {
			body: JSON.stringify({
				configuration: _getConfiguration(),
				description_i18n: {[defaultLocale]: descriptionInputValue},
				elementInstances: {
					queryConfiguration: {
						queryEntries:
							framework === FRAMEWORK_TYPES.BASELINE
								? DEFAULT_SELECTED_BASELINE_SXP_ELEMENTS
								: [],
					},
				},
				title_i18n: {[defaultLocale]: inputValue},
			}),

			headers: new Headers({
				'Content-Type': 'application/json',
			}),

			method: 'POST',
		})
			.then((response) => {
				if (!response.ok) {
					handleFormError();
				}

				return response.json();
			})
			.then((responseContent) => {
				if (isMounted()) {
					if (responseContent.error) {
						handleFormError(responseContent);
					}
					else {
						setVisible(false);

						closeModal();

						if (responseContent.id) {
							const url = new URL(editSXPBlueprintURL);

							url.searchParams.set(
								`${portletNamespace}sxpBlueprintId`,
								responseContent.id
							);

							navigate(url);
						}
						else {
							navigate(redirectURL);
						}
					}
				}
			})
			.catch((response) => {
				handleFormError(response);
			});

		setLoadingResponse(true);
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			closeModal();
		},
	});

	return (
		visible && (
			<ClayModal
				className="sxp-blueprint-edit-title-modal"
				observer={observer}
				size="md"
			>
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${portletNamespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						<div
							className={getCN('form-group', {
								'has-error': errorMessage,
							})}
						>
							<label
								className="control-label"
								htmlFor={`${portletNamespace}title`}
							>
								{Liferay.Language.get('name')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<input
								autoFocus
								className="form-control"
								disabled={loadingResponse}
								id={`${portletNamespace}title`}
								name={`${portletNamespace}title`}
								onChange={(event) =>
									setInputValue(event.target.value)
								}
								required
								type="text"
								value={inputValue}
							/>

							<input
								id={`${portletNamespace}title_${defaultLocale}`}
								name={`${portletNamespace}title_${defaultLocale}`}
								type="hidden"
								value={inputValue}
							/>

							{errorMessage && (
								<div className="form-feedback-item">
									<ClayIcon
										className="inline-item inline-item-before"
										symbol="exclamation-full"
									/>

									{errorMessage}
								</div>
							)}
						</div>

						<div className="form-group">
							<label
								className="control-label"
								htmlFor={`${portletNamespace}description`}
							>
								{Liferay.Language.get('description')}
							</label>

							<textarea
								className="form-control"
								disabled={loadingResponse}
								id={`${portletNamespace}description`}
								name={`${portletNamespace}description`}
								onChange={(event) =>
									setDescriptionInputValue(event.target.value)
								}
								value={descriptionInputValue}
							/>

							<input
								id={`${portletNamespace}description_${defaultLocale}`}
								name={`${portletNamespace}description_${defaultLocale}`}
								type="hidden"
								value={descriptionInputValue}
							/>
						</div>

						<div className="form-group">
							<label
								className="control-label"
								htmlFor={`${portletNamespace}framework`}
							>
								{Liferay.Language.get('start-with')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClayLayout.Row>
								<ClayLayout.Col size={6}>
									<FrameworkCard
										checked={
											framework === FRAMEWORK_TYPES.ALL
										}
										description={Liferay.Language.get(
											'select-all-clauses-description'
										)}
										imagePath={`${contextPath}/sxp_blueprint_admin/images/all-clauses.svg`}
										onChange={() =>
											setFramework(FRAMEWORK_TYPES.ALL)
										}
										title={Liferay.Language.get(
											'all-clauses'
										)}
										value={FRAMEWORK_TYPES.ALL}
									/>
								</ClayLayout.Col>

								<ClayLayout.Col size={6}>
									<FrameworkCard
										checked={
											framework ===
											FRAMEWORK_TYPES.BASELINE
										}
										description={Liferay.Language.get(
											'select-baseline-clauses-description'
										)}
										imagePath={`${contextPath}/sxp_blueprint_admin/images/baseline-clauses.svg`}
										onChange={() =>
											setFramework(
												FRAMEWORK_TYPES.BASELINE
											)
										}
										title={Liferay.Language.get(
											'baseline-clauses'
										)}
										value={FRAMEWORK_TYPES.BASELINE}
									/>
								</ClayLayout.Col>
							</ClayLayout.Row>
						</div>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									disabled={loadingResponse}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									disabled={loadingResponse}
									displayType="primary"
									type="submit"
								>
									{loadingResponse && (
										<span className="inline-item inline-item-before">
											<span
												aria-hidden="true"
												className="loading-animation"
											></span>
										</span>
									)}

									{submitButtonLabel}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</form>
			</ClayModal>
		)
	);
};

export function AddSXPBlueprintModal({
	closeModal,
	contextPath,
	defaultLocale,
	dialogTitle,
	editSXPBlueprintURL,
	portletNamespace,
	redirectURL,
}) {
	const [searchableTypes, setSearchableTypes] = useState(null);
	const [keywordQueryContributors, setKeywordQueryContributors] = useState(
		null
	);
	const [
		modelPrefilterContributors,
		setModelPrefilterContributors,
	] = useState(null);
	const [
		queryPrefilterContributors,
		setQueryPrefilterContributors,
	] = useState(null);

	useEffect(() => {
		[
			{
				setProperty: setSearchableTypes,
				url: '/o/search-experiences-rest/v1.0/searchable-asset-names',
			},
			{
				setProperty: setKeywordQueryContributors,
				url:
					'/o/search-experiences-rest/v1.0/keyword-query-contributors',
			},
			{
				setProperty: setModelPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/model-prefilter-contributors',
			},
			{
				setProperty: setQueryPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/query-prefilter-contributors',
			},
		].forEach(({setProperty, url}) =>
			fetchData(
				url,
				{method: 'GET'},
				(responseContent) =>
					setProperty(
						responseContent.items
							.map(({className}) => className)
							.filter((item) => item)
							.sort()
					),
				() => setProperty({})
			)
		);
	}, []); //eslint-disable-line

	if (
		!keywordQueryContributors ||
		!modelPrefilterContributors ||
		!queryPrefilterContributors ||
		!searchableTypes
	) {
		return null;
	}

	return (
		<AddModal
			closeModal={closeModal}
			contextPath={contextPath}
			defaultLocale={defaultLocale}
			dialogTitle={dialogTitle}
			editSXPBlueprintURL={editSXPBlueprintURL}
			initialVisible
			keywordQueryContributors={keywordQueryContributors}
			modelPrefilterContributors={modelPrefilterContributors}
			portletNamespace={portletNamespace}
			queryPrefilterContributors={queryPrefilterContributors}
			redirectURL={redirectURL}
			searchableTypes={searchableTypes}
		/>
	);
}

export default AddSXPBlueprintModal;
