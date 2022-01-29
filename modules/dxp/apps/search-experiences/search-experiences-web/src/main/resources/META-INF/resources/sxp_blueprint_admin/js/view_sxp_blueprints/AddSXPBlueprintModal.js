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
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, navigate} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';
import {
	DEFAULT_ADVANCED_CONFIGURATION,
	DEFAULT_HIGHLIGHT_CONFIGURATION,
	DEFAULT_PARAMETER_CONFIGURATION,
	DEFAULT_SORT_CONFIGURATION,
} from '../utils/data';
import {fetchData} from '../utils/fetch';
import {filterAndSortClassNames} from '../utils/utils';
import PortletContext from './PortletContext';

const AddSXPBlueprintModal = ({children}) => {
	const {defaultLocale, editSXPBlueprintURL, namespace} = useContext(
		PortletContext
	);

	const isMounted = useIsMounted();

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const [descriptionInputValue, setDescriptionInputValue] = useState('');
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [titleInputValue, setTitleInputValue] = useState('');
	const [visibleModal, setVisibleModal] = useState(false);

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
					setProperty(filterAndSortClassNames(responseContent.items)),
				() => setProperty([])
			)
		);
	}, []);

	if (
		!keywordQueryContributors ||
		!modelPrefilterContributors ||
		!queryPrefilterContributors
	) {
		return null;
	}

	const _handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || DEFAULT_ERROR);

		setLoadingResponse(false);
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		fetch('/o/search-experiences-rest/v1.0/sxp-blueprints', {
			body: JSON.stringify({
				configuration: {
					advancedConfiguration: DEFAULT_ADVANCED_CONFIGURATION,
					aggregationConfiguration: {},
					generalConfiguration: {
						clauseContributorsExcludes: [],
						clauseContributorsIncludes: [
							...keywordQueryContributors,
							...modelPrefilterContributors,
							...queryPrefilterContributors,
						],
						searchableAssetTypes: [],
					},
					highlightConfiguration: DEFAULT_HIGHLIGHT_CONFIGURATION,
					parameterConfiguration: DEFAULT_PARAMETER_CONFIGURATION,
					queryConfiguration: {
						applyIndexerClauses: true,
					},
					sortConfiguration: DEFAULT_SORT_CONFIGURATION,
				},
				description_i18n: {[defaultLocale]: descriptionInputValue},
				elementInstances: [],
				title_i18n: {[defaultLocale]: titleInputValue},
			}),
			headers: new Headers({
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		})
			.then((response) => {
				if (!response.ok) {
					_handleFormError();
				}

				return response.json();
			})
			.then((responseContent) => {
				if (isMounted()) {
					if (responseContent.error) {
						_handleFormError(responseContent);
					}
					else {
						onClose();

						if (responseContent.id) {
							const url = new URL(editSXPBlueprintURL);

							url.searchParams.set(
								`${namespace}sxpBlueprintId`,
								responseContent.id
							);

							navigate(url);
						}
						else {
							navigate(window.location.href);
						}
					}
				}
			})
			.catch((response) => {
				_handleFormError(response);
			});

		setLoadingResponse(true);
	};

	return (
		<>
			{visibleModal && (
				<ClayModal
					className="sxp-blueprint-edit-title-modal"
					observer={observer}
					size="md"
				>
					<ClayModal.Header>
						{Liferay.Language.get('new-search-blueprint')}
					</ClayModal.Header>

					<form id={`${namespace}form`} onSubmit={_handleSubmit}>
						<ClayModal.Body>
							{errorMessage && (
								<ClayAlert
									displayType="danger"
									onClose={() => setErrorMessage('')}
								>
									{errorMessage}
								</ClayAlert>
							)}

							<div className="form-group">
								<label
									className="control-label"
									htmlFor={`${namespace}title`}
								>
									{Liferay.Language.get('title')}

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
										setTitleInputValue(event.target.value)
									}
									required
									type="text"
									value={titleInputValue}
								/>

								<input
									id={`${namespace}title_${defaultLocale}`}
									name={`${namespace}title_${defaultLocale}`}
									type="hidden"
									value={titleInputValue}
								/>
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
										setDescriptionInputValue(
											event.target.value
										)
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

										{Liferay.Language.get('create')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</form>
				</ClayModal>
			)}

			<div onClick={() => setVisibleModal(!visibleModal)}>{children}</div>
		</>
	);
};

export default AddSXPBlueprintModal;
