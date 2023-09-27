/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {TEXT_EMBEDDING_PROVIDER_TYPES} from './constants';

/**
 * A button to test the connection for the semantic search settings page.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
function TestConfigurationButton({
	accessToken,
	availableTextEmbeddingProviders,
	basicAuthPassword,
	basicAuthUsername,
	disabled,
	embeddingVectorDimensions,
	errors,
	hostAddress,
	languageIds,
	maxCharacterCount,
	model,
	modelClassNames,
	modelTimeout,
	textEmbeddingProvider,
	textTruncationStrategy,
}) {
	const [loading, setLoading] = useState(false);
	const [testResultsMessage, setTestResultsMessage] = useState({}); // {message, type}

	/**
	 * Clear the message if any input values change.
	 */
	useEffect(() => {
		setTestResultsMessage({});
	}, [
		accessToken,
		basicAuthPassword,
		basicAuthUsername,
		embeddingVectorDimensions,
		hostAddress,
		languageIds,
		maxCharacterCount,
		model,
		modelClassNames,
		modelTimeout,
		textEmbeddingProvider,
		textTruncationStrategy,
	]);

	/**
	 * Used for the `/text-embeddings/validate-provider-configuration` endpoint
	 * to conditionally send the appropriate data according to the user-selected
	 * text embedding provider type.
	 * @returns {object}
	 */
	const _getTextEmbeddingProviderSettings = () => {
		if (
			textEmbeddingProvider ===
			TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API
		) {
			return {
				accessToken,
				model,
				modelTimeout,
			};
		}

		if (
			textEmbeddingProvider ===
			TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_ENDPOINT
		) {
			return {
				accessToken,
				hostAddress,
			};
		}

		if (textEmbeddingProvider === TEXT_EMBEDDING_PROVIDER_TYPES.TXTAI) {
			return {
				basicAuthPassword,
				basicAuthUsername,
				hostAddress,
			};
		}

		return {};
	};

	const _handleTestConfigurationButtonClick = () => {
		setLoading(true);

		fetch(
			'/o/search-experiences-rest/v1.0/text-embeddings/validate-provider-configuration',
			{
				body: JSON.stringify({
					attributes: {
						maxCharacterCount,
						textTruncationStrategy,
						..._getTextEmbeddingProviderSettings(),
					},
					embeddingVectorDimensions,
					languageIds,
					modelClassNames,
					providerName: textEmbeddingProvider,
				}),
				headers: new Headers({
					'Accept': 'application/json',
					'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				}),
				method: 'POST',
			}
		)
			.then((response) => response.json())
			.then((responseData) => {

				// If there is an error with the connection.
				//
				// Example `errorMessage` string (Can vary based on text
				//  embedding provider):
				// '{"error": "Authorization header is correct, but the token seems invalid"}'
				// '[{\"generated_text":\"com.liferay.portal.kernel.util.Http$Body@7e13...\"}]'

				if (responseData.errorMessage) {
					try {
						const errorMessage =
							typeof responseData.errorMessage === 'string'
								? JSON.parse(responseData.errorMessage)
								: responseData.errorMessage;

						// If `errorMessage` has `error` property that is a string.

						if (
							errorMessage?.error &&
							typeof errorMessage?.error === 'string'
						) {
							return setTestResultsMessage({
								message: errorMessage?.error,
								type: 'warning',
							});
						}

						// If `errorMessage` value is an object or array.

						return setTestResultsMessage({
							message: sub(
								Liferay.Language.get(
									'unable-to-connect-to-x.-connection-failed-with-x'
								),
								[
									availableTextEmbeddingProviders[
										textEmbeddingProvider
									],
									JSON.stringify(errorMessage),
								]
							),
							type: 'warning',
						});
					}
					catch {

						// If `errorMessage` is a string.

						return setTestResultsMessage({
							message: sub(
								Liferay.Language.get(
									'unable-to-connect-to-x.-connection-failed-with-x'
								),
								[
									availableTextEmbeddingProviders[
										textEmbeddingProvider
									],
									responseData.errorMessage,
								]
							),
							type: 'warning',
						});
					}
				}

				// If the user has no permissions for the REST endpoint.
				// Example: {"message": "Access denied to com.liferay.search.experiences.rest.internal.resource.v1_0.TextEmbeddingProviderValidationResultResourceImpl#postTextEmbeddingValidateConfiguration"}

				if (responseData.message) {
					throw new Error(responseData.message);
				}

				// If the response expected dimensions is 0. This means no
				// results were returned from the text embedding provider.

				if (Number(responseData.expectedDimensions === 0)) {
					return setTestResultsMessage({
						message: Liferay.Language.get(
							'the-text-embedding-provider-returned-no-results'
						),
						type: 'danger',
					});
				}

				// If the expected dimensions don't match the configure
				// dimensions.

				if (
					Number(responseData.expectedDimensions) !==
					Number(embeddingVectorDimensions)
				) {
					return setTestResultsMessage({
						message: sub(
							Liferay.Language.get(
								'the-dimensions-from-the-connection-do-not-match-the-configured-embedding-vector-dimensions'
							),
							[
								embeddingVectorDimensions,
								responseData.expectedDimensions,
							]
						),
						type: 'warning',
					});
				}

				// If the none of the previous checks are caught, assume a
				// successful connection.

				setTestResultsMessage({
					message: Liferay.Language.get('connection-is-successful'),
					type: 'success',
				});
			})
			.catch((error) => {
				setTestResultsMessage({
					message: Liferay.Language.get(
						'unable-to-test-configuration-due-to-an-unexpected-error'
					),
					type: 'danger',
				});

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const isMissingRequiredFields = () => {
		if (
			textEmbeddingProvider ===
			TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API
		) {
			return (
				errors?.attributes?.accessToken ||
				errors?.attributes?.model ||
				errors?.attributes?.modelTimeout
			);
		}

		if (
			textEmbeddingProvider ===
			TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_ENDPOINT
		) {
			return (
				errors?.attributes?.accessToken ||
				errors?.attributes?.hostAddress
			);
		}

		if (textEmbeddingProvider === TEXT_EMBEDDING_PROVIDER_TYPES.TXTAI) {
			return errors?.attributes?.hostAddress;
		}

		return false;
	};

	return (
		<div className="test-configuration-button-root">
			<ClayTooltipProvider>
				<ClayButton
					aria-disabled={
						loading || isMissingRequiredFields() || disabled
					}
					aria-label={Liferay.Language.get('test-configuration')}
					className={
						loading || isMissingRequiredFields() || disabled
							? 'disabled'
							: ''
					}
					displayType="secondary"
					onClick={_handleTestConfigurationButtonClick}
					{...(isMissingRequiredFields()
						? {
								title: Liferay.Language.get(
									'required-fields-missing'
								),
						  }
						: {})}
				>
					{loading && (
						<span className="inline-item inline-item-before">
							<ClayLoadingIndicator small />
						</span>
					)}

					{Liferay.Language.get('test-configuration')}
				</ClayButton>
			</ClayTooltipProvider>

			{!!testResultsMessage.message && (
				<ClayAlert
					className="mt-2"
					displayType={testResultsMessage.type}
					title={testResultsMessage.message}
					variant="feedback"
				/>
			)}
		</div>
	);
}

export default TestConfigurationButton;
