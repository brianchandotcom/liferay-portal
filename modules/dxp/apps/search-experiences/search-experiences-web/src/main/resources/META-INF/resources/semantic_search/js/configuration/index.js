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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useFormik} from 'formik';
import React from 'react';

import {LearnMessageWithoutContext} from '../../../sxp_blueprint_admin/js/shared/LearnMessage';
import sub from '../../../sxp_blueprint_admin/js/utils/language/sub';
import Input from './Input';
import TestConfigurationButton from './TestConfigurationButton';
import {TEXT_EMBEDDING_PROVIDER_TYPES} from './constants';

const DEFAULT_TEXT_EMBEDDING_PROVIDER_CONFIGURATIONS = {
	attributes: {
		maxCharacterCount: 500,
		modelTimeout: 25,
	},
	embeddingVectorDimensions: 768,
	languageIds: ['en_US'],
	modelClassNames: [
		'com.liferay.blogs.model.BlogsEntry',
		'com.liferay.journal.model.JournalArticle',
		'com.liferay.knowledge.base.model.KBArticle',
		'com.liferay.wiki.model.WikiPage',
	],
	providerName: TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API,
};

function parseJSONString(jsonString) {
	if (typeof jsonString === 'undefined' || jsonString === '') {
		return '';
	}

	try {
		return JSON.parse(jsonString);
	}
	catch (error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}

		return jsonString;
	}
}

/**
 * Converts an array of JSON strings into an array of JSON objects.
 *
 * Example:
 * parseArrayOfJSONStrings(["{}"]);
 * => [{}]
 * @param {Array} array
 * @returns {Array}
 */
function parseArrayOfJSONStrings(array) {
	return array.map((string) => parseJSONString(string));
}

/**
 * Determines the value of textEmbeddingProviderConfigurationJSONs based
 * on the initial prop and set of available providers.
 * @param {Array} initialTextEmbeddingProviderConfigurationJSONs
 * Initial configurations of the text embedding provider, as an
 * array of stringified objects.
 * @param {object} availableProviders
 * @returns {Array} Cleaned up list of provider configurations.
 */
function resolveInitialTextEmbeddingProviderConfigurationJSONs(
	initialTextEmbeddingProviderConfigurationJSONs,
	availableTextEmbeddingProviders
) {
	const initialTextEmbeddingProviderConfigurationsArray = parseArrayOfJSONStrings(
		initialTextEmbeddingProviderConfigurationJSONs
	);

	if (!initialTextEmbeddingProviderConfigurationsArray.length) {
		return [
			{
				...DEFAULT_TEXT_EMBEDDING_PROVIDER_CONFIGURATIONS,
				providerName: resolveProviderName(
					availableTextEmbeddingProviders
				),
			},
		];
	}

	return initialTextEmbeddingProviderConfigurationsArray.map(
		(configurations) => ({
			...configurations,
			providerName: resolveProviderName(
				availableTextEmbeddingProviders,
				configurations.providerName
			),
		})
	);
}

/**
 * Gets the valid string that should be set for providerName. This covers cases
 * where the providerName should not be set to a provider that's been
 * blacklisted.
 * @param {object} availableProviders
 * @param {string} [providerName]
 * @returns {string}
 */
function resolveProviderName(availableProviders, providerName) {
	if (!providerName || !availableProviders[providerName]) {
		return Object.keys(availableProviders)[0];
	}

	return providerName;
}

/**
 * Formats the object into an array of label and value, important for inputs
 * that offer selection. If object is actually a flat array, this formats
 * the items into label-value pairs.
 *
 * Examples:
 * transformToLabelValueArray({en_US: 'English', es_ES: 'Spanish'})
 * => [{label: 'English', value: 'en_US'}, {label: 'Spanish', value: 'es_ES'}]
 * transformToLabelValueArray(['one', 'two'])
 * => [{label: 'one', value: 'one'}, {label: 'two', value: 'two'}]
 *
 * @param {Array|object} items
 * @return {Array}
 */
const transformToLabelValueArray = (items = {}) => {
	if (Array.isArray(items)) {
		return items.map((item) =>
			item.value && item.label
				? item
				: {
						label: item,
						value: item,
				  }
		);
	}

	return Object.entries(items).map(([value, label]) => ({
		label,
		value,
	}));
};

/**
 * Form within semantic search settings page, configures text embedding provider and
 * indexing settings.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
export default function ({
	availableEmbeddingVectorDimensions,
	availableLanguageDisplayNames,
	availableModelClassNames,
	availableTextEmbeddingProviders,
	availableTextTruncationStrategies,
	initialTextEmbeddingCacheTimeout,
	initialTextEmbeddingProviderConfigurationJSONs,
	initialTextEmbeddingsEnabled,
	learnMessages,
	namespace = '',
}) {
	const _handleFormikValidate = (values) => {
		const errors = {
			textEmbeddingProviderConfigurationJSONs: [{attributes: {}}],
		}; // Sets empty values to avoid undefined errors when setting values.

		values.textEmbeddingProviderConfigurationJSONs?.map(
			(textEmbeddingProviderConfigurationJSON, index) => {

				// Validate "Types" field.

				if (
					!textEmbeddingProviderConfigurationJSON.modelClassNames
						?.length
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].modelClassNames = sub(
						Liferay.Language.get('the-x-field-is-required'),
						[Liferay.Language.get('types')]
					);
				}

				// Validate "Hugging Face Access Token" field.

				if (
					!textEmbeddingProviderConfigurationJSON.attributes
						?.accessToken ||
					textEmbeddingProviderConfigurationJSON.attributes
						?.accessToken === ''
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].attributes.accessToken = Liferay.Language.get(
						'this-field-is-required'
					);
				}

				// Validate "Languages" field.

				if (
					!textEmbeddingProviderConfigurationJSON.languageIds?.length
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].languageIds = sub(
						Liferay.Language.get('the-x-field-is-required'),
						[Liferay.Language.get('languages')]
					);
				}

				// Validate "Max Character Count" field.

				if (
					!textEmbeddingProviderConfigurationJSON.attributes
						?.maxCharacterCount === ''
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].attributes.maxCharacterCount = Liferay.Language.get(
						'this-field-is-required'
					);
				}
				else {
					if (
						textEmbeddingProviderConfigurationJSON.attributes
							?.maxCharacterCount < 50
					) {
						errors.textEmbeddingProviderConfigurationJSONs[
							index
						].attributes.maxCharacterCount = sub(
							Liferay.Language.get(
								'please-enter-a-value-greater-than-or-equal-to-x'
							),
							['50']
						);
					}

					if (
						textEmbeddingProviderConfigurationJSON.attributes
							?.maxCharacterCount > 10000
					) {
						errors.textEmbeddingProviderConfigurationJSONs[
							index
						].attributes.maxCharacterCount = sub(
							Liferay.Language.get(
								'please-enter-a-value-less-than-or-equal-to-x'
							),
							['10000']
						);
					}
				}

				// Validate "Model" field.

				if (
					!textEmbeddingProviderConfigurationJSON.attributes?.model ||
					textEmbeddingProviderConfigurationJSON.attributes?.model ===
						''
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].attributes.model = Liferay.Language.get(
						'this-field-is-required'
					);
				}

				// Validate "Model Timeout" field.

				if (
					!textEmbeddingProviderConfigurationJSON.attributes
						?.modelTimeout ||
					(textEmbeddingProviderConfigurationJSON.attributes
						?.modelTimeout === '' &&
						textEmbeddingProviderConfigurationJSON?.providerName ===
							TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API)
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].attributes.modelTimeout = Liferay.Language.get(
						'this-field-is-required'
					);
				}
				else {
					if (
						textEmbeddingProviderConfigurationJSON.attributes
							?.modelTimeout < 0
					) {
						errors.textEmbeddingProviderConfigurationJSONs[
							index
						].attributes.modelTimeout = sub(
							Liferay.Language.get(
								'please-enter-a-value-greater-than-or-equal-to-x'
							),
							['0']
						);
					}

					if (
						textEmbeddingProviderConfigurationJSON.attributes
							?.modelTimeout > 60
					) {
						errors.textEmbeddingProviderConfigurationJSONs[
							index
						].attributes.modelTimeout = sub(
							Liferay.Language.get(
								'please-enter-a-value-less-than-or-equal-to-x'
							),
							['60']
						);
					}
				}

				// Validate "Host Address" field.

				if (
					!textEmbeddingProviderConfigurationJSON.attributes
						?.hostAddress ||
					textEmbeddingProviderConfigurationJSON.attributes
						?.hostAddress === ''
				) {
					errors.textEmbeddingProviderConfigurationJSONs[
						index
					].attributes.hostAddress = Liferay.Language.get(
						'this-field-is-required'
					);
				}
			}
		);

		// Validate "Text Embedding Cache Timeout" field.

		if (values.textEmbeddingCacheTimeout === '') {
			errors.textEmbeddingCacheTimeout = Liferay.Language.get(
				'this-field-is-required'
			);
		}
		else if (values.textEmbeddingCacheTimeout < 0) {
			errors.textEmbeddingCacheTimeout = sub(
				Liferay.Language.get(
					'please-enter-a-value-greater-than-or-equal-to-x'
				),
				['0']
			);
		}

		return errors;
	};

	const formik = useFormik({
		initialValues: {
			textEmbeddingCacheTimeout: initialTextEmbeddingCacheTimeout,
			textEmbeddingProviderConfigurationJSONs: resolveInitialTextEmbeddingProviderConfigurationJSONs(
				initialTextEmbeddingProviderConfigurationJSONs,
				availableTextEmbeddingProviders
			),
			textEmbeddingsEnabled: initialTextEmbeddingsEnabled,
		},
		validate: _handleFormikValidate,
		validateOnMount: true,
	});

	const _handleCheckboxChange = (name) => (event) => {
		formik.setFieldValue(name, event.target.checked);
	};

	const _handleInputBlur = (name) => () => {
		formik.setFieldTouched(name);
	};

	const _handleInputChange = (name) => (val) => {
		formik.setFieldValue(name, val);
	};

	const _renderEmbeddingProviderConfigurationInputs = (index) => {
		return (
			<>
				<div className="sheet-section">
					<h3 className="sheet-subtitle">
						{Liferay.Language.get(
							'text-embedding-provider-settings'
						)}
					</h3>

					<ClayForm.Group>
						<ClayCheckbox
							aria-label={Liferay.Language.get(
								'text-embeddings-enabled'
							)}
							checked={!!formik.values.textEmbeddingsEnabled}
							label={Liferay.Language.get(
								'text-embeddings-enabled'
							)}
							name={`${namespace}textEmbeddingsEnabled`}
							onChange={_handleCheckboxChange(
								'textEmbeddingsEnabled'
							)}
							value={!!formik.values.textEmbeddingsEnabled}
						/>
					</ClayForm.Group>

					<Input
						error={
							formik.errors
								?.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.providerName
						}
						items={transformToLabelValueArray(
							availableTextEmbeddingProviders
						)}
						label={Liferay.Language.get('text-embedding-provider')}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].providerName`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].providerName`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].providerName`
						)}
						type="select"
						value={
							formik.values
								?.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.providerName
						}
					>
						{formik.values
							.textEmbeddingProviderConfigurationJSONs?.[index]
							?.providerName ===
							TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API && (
							<ClayForm.FeedbackGroup>
								<ClayForm.Text>
									{Liferay.Language.get(
										'text-embedding-provider-hugging-face-help'
									)}

									<LearnMessageWithoutContext
										className="ml-1"
										learnMessages={learnMessages}
										resourceKey="semantic-search"
									/>
								</ClayForm.Text>
							</ClayForm.FeedbackGroup>
						)}
					</Input>

					{formik.values.textEmbeddingProviderConfigurationJSONs?.[
						index
					]?.providerName === TEXT_EMBEDDING_PROVIDER_TYPES.TXTAI && (
						<>
							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-host-address-help'
								)}
								label={Liferay.Language.get('host-address')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`
								)}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
							/>

							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.basicAuthUsername
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-basic-auth-username-help'
								)}
								label={Liferay.Language.get(
									'basic-auth-username'
								)}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthUsername`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthUsername`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthUsername`
								)}
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.basicAuthUsername
								}
							/>

							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.basicAuthPassword
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-basic-auth-password-help'
								)}
								label={Liferay.Language.get(
									'basic-auth-password'
								)}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthPassword`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthPassword`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.basicAuthPassword`
								)}
								type="basicAuthPassword"
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.basicAuthPassword
								}
							/>
						</>
					)}

					{formik.values.textEmbeddingProviderConfigurationJSONs?.[
						index
					]?.providerName ===
						TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_API && (
						<>
							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
								label={Liferay.Language.get('access-token')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`
								)}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
							/>

							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.model
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-model-help'
								)}
								label={Liferay.Language.get('model')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.model`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.model`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.model`
								)}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.model
								}
								type="model"
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.model
								}
							>
								<ClayForm.FeedbackGroup>
									<ClayForm.Text>
										{Liferay.Language.get(
											'begin-typing-and-select-a-model'
										)}
									</ClayForm.Text>
								</ClayForm.FeedbackGroup>
							</Input>

							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.modelTimeout
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-model-timeout-help'
								)}
								label={Liferay.Language.get('model-timeout')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.modelTimeout`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.modelTimeout`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.modelTimeout`
								)}
								options={{max: 60, min: 0}}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.modelTimeout
								}
								type="number"
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.modelTimeout
								}
							/>
						</>
					)}

					{formik.values.textEmbeddingProviderConfigurationJSONs?.[
						index
					]?.providerName ===
						TEXT_EMBEDDING_PROVIDER_TYPES.HUGGING_FACE_INFERENCE_ENDPOINT && (
						<>
							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
								label={Liferay.Language.get('access-token')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.accessToken`
								)}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.accessToken
								}
							/>
							<Input
								error={
									formik.errors
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
								helpText={Liferay.Language.get(
									'text-embedding-provider-host-address-help'
								)}
								label={Liferay.Language.get('host-address')}
								name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`}
								onBlur={_handleInputBlur(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`
								)}
								onChange={_handleInputChange(
									`textEmbeddingProviderConfigurationJSONs[${index}].attributes.hostAddress`
								)}
								required
								touched={
									formik.touched
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
								value={
									formik.values
										.textEmbeddingProviderConfigurationJSONs?.[
										index
									]?.attributes?.hostAddress
								}
							/>
						</>
					)}

					<Input
						error={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.embeddingVectorDimensions
						}
						helpText={Liferay.Language.get(
							'text-embedding-provider-embedding-vector-dimensions-help'
						)}
						items={transformToLabelValueArray(
							availableEmbeddingVectorDimensions
						)}
						label={Liferay.Language.get(
							'embedding-vector-dimensions'
						)}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].embeddingVectorDimensions`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].embeddingVectorDimensions`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].embeddingVectorDimensions`
						)}
						type="select"
						value={
							formik.values
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.embeddingVectorDimensions
						}
					/>

					<TestConfigurationButton
						accessToken={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes?.accessToken
						}
						availableTextEmbeddingProviders={
							availableTextEmbeddingProviders
						}
						basicAuthPassword={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes.basicAuthPassword
						}
						basicAuthUsername={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes.basicAuthUsername
						}
						embeddingVectorDimensions={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.embeddingVectorDimensions
						}
						errors={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]
						}
						hostAddress={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes.hostAddress
						}
						languageIds={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.languageIds
						}
						maxCharacterCount={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes?.maxCharacterCount
						}
						model={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes?.model
						}
						modelClassNames={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.modelClassNames
						}
						modelTimeout={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes?.modelTimeout
						}
						textEmbeddingCacheTimeout={
							formik.values.textEmbeddingCacheTimeout
						}
						textEmbeddingProvider={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.providerName
						}
						textEmbeddingsEnabled={
							formik.values.textEmbeddingsEnabled
						}
						textTruncationStrategy={
							formik.values
								.textEmbeddingProviderConfigurationJSONs[index]
								?.attributes.textTruncationStrategy
						}
					/>
				</div>

				<div className="sheet-section">
					<h3 className="sheet-subtitle">
						{Liferay.Language.get('index-settings')}
					</h3>

					<Input
						error={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.attributes?.maxCharacterCount
						}
						helpText={Liferay.Language.get(
							'text-embedding-provider-max-character-count-help'
						)}
						label={Liferay.Language.get('max-character-count')}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.maxCharacterCount`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].attributes.maxCharacterCount`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].attributes.maxCharacterCount`
						)}
						options={{max: 10000, min: 50}}
						required
						touched={
							formik.touched
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.attributes?.maxCharacterCount
						}
						type="number"
						value={
							formik.values
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.attributes?.maxCharacterCount
						}
					/>

					<Input
						error={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.attributes?.textTruncationStrategy
						}
						helpText={Liferay.Language.get(
							'text-embedding-provider-text-truncation-strategy-help'
						)}
						items={transformToLabelValueArray(
							availableTextTruncationStrategies
						)}
						label={Liferay.Language.get('text-truncation-strategy')}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].attributes.textTruncationStrategy`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].attributes.textTruncationStrategy`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].attributes.textTruncationStrategy`
						)}
						type="select"
						value={
							formik.values
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.attributes?.textTruncationStrategy
						}
					/>

					<Input
						error={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.modelClassNames
						}
						helpText={Liferay.Language.get(
							'text-embedding-provider-types-help'
						)}
						items={transformToLabelValueArray(
							availableModelClassNames
						)}
						label={Liferay.Language.get('types')}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].modelClassNames`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].modelClassNames`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].modelClassNames`
						)}
						required
						touched={
							formik.touched
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.modelClassNames
						}
						type="multiple"
						value={
							formik.values
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.modelClassNames
						}
					/>

					<Input
						error={
							formik.errors
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.languageIds
						}
						helpText={Liferay.Language.get(
							'text-embedding-provider-languages-help'
						)}
						items={transformToLabelValueArray(
							availableLanguageDisplayNames
						)}
						label={Liferay.Language.get('languages')}
						name={`textEmbeddingProviderConfigurationJSONs[${index}].languageIds`}
						onBlur={_handleInputBlur(
							`textEmbeddingProviderConfigurationJSONs[${index}].languageIds`
						)}
						onChange={_handleInputChange(
							`textEmbeddingProviderConfigurationJSONs[${index}].languageIds`
						)}
						required
						touched={
							formik.touched
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.languageIds
						}
						type="multiple"
						value={
							formik.values
								.textEmbeddingProviderConfigurationJSONs?.[
								index
							]?.languageIds
						}
					/>
				</div>

				<div className="sheet-section">
					<h3 className="sheet-subtitle">
						{Liferay.Language.get('search-settings')}
					</h3>

					<Input
						error={formik.errors.textEmbeddingCacheTimeout}
						helpText={Liferay.Language.get(
							'text-embedding-cache-timeout-help'
						)}
						label={Liferay.Language.get(
							'text-embedding-cache-timeout'
						)}
						name={`${namespace}textEmbeddingCacheTimeout`}
						onBlur={_handleInputBlur('textEmbeddingCacheTimeout')}
						onChange={_handleInputChange(
							'textEmbeddingCacheTimeout'
						)}
						options={{min: 0}}
						required
						touched={formik.touched.textEmbeddingCacheTimeout}
						type="number"
						value={formik.values.textEmbeddingCacheTimeout}
					/>
				</div>
			</>
		);
	};

	return (
		<div className="semantic-search-settings-root">
			{_renderEmbeddingProviderConfigurationInputs(0)}

			<input
				name={`${namespace}textEmbeddingProviderConfigurationJSONs`}
				type="hidden"
				value={formik.values.textEmbeddingProviderConfigurationJSONs
					.map((configurationObject) =>
						JSON.stringify(configurationObject)
					)
					.join('|')}
			/>
		</div>
	);
}
