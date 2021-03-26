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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import getCN from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import {
	DEFAULT_ADVANCED_CONFIGURATION,
	DEFAULT_BASELINE_ELEMENTS,
	DEFAULT_PARAMETER_CONFIGURATION,
	DEFAULT_SORT_CONFIGURATION,
} from '../utils/data';
import {FRAMEWORK_TYPES} from '../utils/frameworkTypes';
import {getElementOutput, getUIConfigurationValues} from '../utils/utils';

const DEFAULT_SELECTED_BASELINE_ELEMENTS = DEFAULT_BASELINE_ELEMENTS.map(
	(element) => ({
		...element,
		uiConfigurationValues: getUIConfigurationValues(
			element.uiConfigurationJSON
		),
	})
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
const AddBlueprintModal = ({
	alert,
	contextPath,
	closeModal,
	defaultLocale,
	dialogTitle,
	formSubmitURL,
	initialVisible,
	namespace,
	onFormSuccess,
	searchableAssetTypesString,
	submitButtonLabel = Liferay.Language.get('create'),
	type,
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [framework, setFramework] = useState(FRAMEWORK_TYPES.DEFAULT);
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [inputValue, setInputValue] = useState('');
	const [descriptionInputValue, setDescriptionInputValue] = useState('');
	const [includeBaselineElements, setIncludeBaselineElements] = useState(
		true
	);

	const handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || '');
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		const formData = new FormData(
			document.querySelector(`#${namespace}form`)
		);

		formData.append(
			`${namespace}configuration`,
			JSON.stringify({
				advanced_configuration: DEFAULT_ADVANCED_CONFIGURATION,
				aggregation_configuration: [],
				facet_configuration: [],
				framework_configuration: {
					apply_indexer_clauses:
						framework === FRAMEWORK_TYPES.DEFAULT,
					searchable_asset_types: JSON.parse(
						searchableAssetTypesString
					),
				},
				parameter_configuration: DEFAULT_PARAMETER_CONFIGURATION,
				query_configuration:
					framework === FRAMEWORK_TYPES.CUSTOM &&
					includeBaselineElements
						? DEFAULT_SELECTED_BASELINE_ELEMENTS.map(
								getElementOutput
						  )
						: [],
				sort_configuration: DEFAULT_SORT_CONFIGURATION,
			})
		);

		formData.append(
			`${namespace}selectedElements`,
			JSON.stringify({
				query_configuration:
					framework === FRAMEWORK_TYPES.CUSTOM &&
					includeBaselineElements
						? DEFAULT_SELECTED_BASELINE_ELEMENTS
						: [],
			})
		);

		fetch(formSubmitURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				if (isMounted()) {
					if (responseContent.error) {
						setLoadingResponse(false);

						handleFormError(responseContent);
					}
					else {
						setVisible(false);

						closeModal();

						if (responseContent.redirectURL) {
							navigate(responseContent.redirectURL);
						}
						else {
							if (onFormSuccess) {
								onFormSuccess({
									...responseContent,
									redirectURL:
										responseContent.redirectURL || '',
								});
							}
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
				className="blueprint-edit-title-modal"
				observer={observer}
				size="md"
			>
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${namespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						{alert && alert.message && alert.title && (
							<ClayAlert
								displayType={alert.style}
								title={alert.title}
							>
								{alert.message}
							</ClayAlert>
						)}

						<input
							name={`${namespace}type`}
							type="hidden"
							value={type}
						/>

						<div
							className={getCN('form-group', {
								'has-error': errorMessage,
							})}
						>
							<label
								className="control-label"
								htmlFor={`${namespace}title`}
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
								id={`${namespace}title`}
								name={`${namespace}title`}
								onChange={(event) =>
									setInputValue(event.target.value)
								}
								required
								type="text"
								value={inputValue}
							/>

							<input
								id={`${namespace}title_${defaultLocale}`}
								name={`${namespace}title_${defaultLocale}`}
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
								htmlFor={`${namespace}description`}
							>
								{Liferay.Language.get('description')}
							</label>

							<textarea
								className="form-control"
								disabled={loadingResponse}
								id={`${namespace}description`}
								name={`${namespace}description`}
								onChange={(event) =>
									setDescriptionInputValue(event.target.value)
								}
								value={descriptionInputValue}
							/>

							<input
								id={`${namespace}description_${defaultLocale}`}
								name={`${namespace}description_${defaultLocale}`}
								type="hidden"
								value={descriptionInputValue}
							/>
						</div>

						<div className="form-group">
							<label
								className="control-label"
								htmlFor={`${namespace}framework`}
							>
								{Liferay.Language.get('framework')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClayLayout.Row>
								<ClayLayout.Col size={6}>
									<FrameworkCard
										checked={
											framework ===
											FRAMEWORK_TYPES.DEFAULT
										}
										description={Liferay.Language.get(
											'compose-elements-on-top-of-liferay-default-search-clauses'
										)}
										imagePath={`${contextPath}/images/liferay-default-clauses.svg`}
										onChange={() =>
											setFramework(
												FRAMEWORK_TYPES.DEFAULT
											)
										}
										title={Liferay.Language.get(
											'liferay-default-clauses'
										)}
										value={FRAMEWORK_TYPES.DEFAULT}
									/>
								</ClayLayout.Col>

								<ClayLayout.Col size={6}>
									<FrameworkCard
										checked={
											framework === FRAMEWORK_TYPES.CUSTOM
										}
										description={Liferay.Language.get(
											'compose-elements-from-the-ground-up'
										)}
										imagePath={`${contextPath}/images/custom-clauses.svg`}
										onChange={() =>
											setFramework(FRAMEWORK_TYPES.CUSTOM)
										}
										title={Liferay.Language.get(
											'custom-clauses'
										)}
										value={FRAMEWORK_TYPES.CUSTOM}
									>
										<ClayTooltipProvider>
											<div
												data-tooltip-align="top"
												onClick={(event) => {
													event.stopPropagation();
												}}
												title={Liferay.Language.get(
													'baseline-elements-emulate-the-behavior-of-liferay-default-clauses'
												)}
											>
												<ClayCheckbox
													aria-label={Liferay.Language.get(
														'include-baseline-elements'
													)}
													checked={
														includeBaselineElements
													}
													disabled={
														framework !==
														FRAMEWORK_TYPES.CUSTOM
													}
													label={Liferay.Language.get(
														'include-baseline-elements'
													)}
													onChange={() =>
														setIncludeBaselineElements(
															!includeBaselineElements
														)
													}
												/>
											</div>
										</ClayTooltipProvider>
									</FrameworkCard>
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

export default AddBlueprintModal;
