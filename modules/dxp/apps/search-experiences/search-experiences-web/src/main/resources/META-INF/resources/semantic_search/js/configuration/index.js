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
import {sub} from '../../../sxp_blueprint_admin/js/utils/language';
import Input from './Input';
import TestConfigurationButton from './TestConfigurationButton';
import {SENTENCE_TRANSFORMER_TYPES} from './constants';

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
 * Form within semantic search settings page, configures sentence transformer and
 * indexing settings.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
export default function ({
	assetEntryClassNames,
	availableAssetEntryClassNames,
	availableEmbeddingVectorDimensions,
	availableLanguageDisplayNames,
	availableSentenceTransformers,
	availableTextTruncationStrategies,
	cacheTimeout = '',
	embeddingVectorDimensions,
	huggingFaceAccessToken,
	learnMessages,
	languageIds,
	maxCharacterCount = '',
	model,
	modelTimeout = '',
	namespace = '',
	sentenceTransformer = SENTENCE_TRANSFORMER_TYPES.HUGGING_FACE_INFERENCE_API,
	sentenceTransformerEnabled,
	textTruncationStrategy,
	txtaiHostAddress,
	txtaiPassword,
	txtaiUsername,
}) {
	const _handleFormikValidate = (values) => {
		const errors = {};

		// Validate "Asset Entry Class Names" field.

		if (!values.assetEntryClassNames?.length) {
			errors.assetEntryClassNames = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('asset-entry-class-names')]
			);
		}

		// Validate "Cache Timeout" field.

		if (values.cacheTimeout === '') {
			errors.cacheTimeout = Liferay.Language.get(
				'this-field-is-required'
			);
		}
		else {
			if (values.cacheTimeout < 0) {
				errors.cacheTimeout = sub(
					Liferay.Language.get(
						'please-enter-a-value-greater-than-or-equal-to-x'
					),
					['0']
				);
			}
		}

		// Validate "Hugging Face Access Token" field.

		if (values.huggingFaceAccessToken === '') {
			errors.huggingFaceAccessToken = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		// Validate "Language IDs" field.

		if (!values.languageIds?.length) {
			errors.languageIds = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('language-ids')]
			);
		}

		// Validate "Max Character Count" field.

		if (!values.maxCharacterCount === '') {
			errors.maxCharacterCount = Liferay.Language.get(
				'this-field-is-required'
			);
		}
		else {
			if (values.maxCharacterCount < 50) {
				errors.maxCharacterCount = sub(
					Liferay.Language.get(
						'please-enter-a-value-greater-than-or-equal-to-x'
					),
					['50']
				);
			}

			if (values.maxCharacterCount > 10000) {
				errors.maxCharacterCount = sub(
					Liferay.Language.get(
						'please-enter-a-value-less-than-or-equal-to-x'
					),
					['10000']
				);
			}
		}

		// Validate "Model" field.

		if (values.model === '') {
			errors.model = Liferay.Language.get('this-field-is-required');
		}

		// Validate "Model Timeout" field.

		if (
			values.modelTimeout === '' &&
			values.sentenceTransformer ===
				SENTENCE_TRANSFORMER_TYPES.HUGGING_FACE_INFERENCE_API
		) {
			errors.modelTimeout = Liferay.Language.get(
				'this-field-is-required'
			);
		}
		else {
			if (values.modelTimeout < 0) {
				errors.modelTimeout = sub(
					Liferay.Language.get(
						'please-enter-a-value-greater-than-or-equal-to-x'
					),
					['0']
				);
			}

			if (values.modelTimeout > 60) {
				errors.modelTimeout = sub(
					Liferay.Language.get(
						'please-enter-a-value-less-than-or-equal-to-x'
					),
					['60']
				);
			}
		}

		// Validate "txtai Host Address" field.

		if (values.txtaiHostAddress === '') {
			errors.txtaiHostAddress = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		return errors;
	};

	const formik = useFormik({
		initialValues: {
			assetEntryClassNames,
			cacheTimeout,
			embeddingVectorDimensions,
			huggingFaceAccessToken,
			languageIds,
			maxCharacterCount,
			model,
			modelTimeout,
			sentenceTransformer,
			sentenceTransformerEnabled,
			textTruncationStrategy,
			txtaiHostAddress,
			txtaiPassword,
			txtaiUsername,
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

	return (
		<div className="semantic-search-settings-root">
			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('sentence-transformer-settings')}
				</h3>

				<ClayForm.Group>
					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'sentence-transformer-enabled'
						)}
						checked={!!formik.values.sentenceTransformerEnabled}
						label={Liferay.Language.get(
							'sentence-transformer-enabled'
						)}
						name={`${namespace}sentenceTransformerEnabled`}
						onChange={_handleCheckboxChange(
							'sentenceTransformerEnabled'
						)}
						value={!!formik.values.sentenceTransformerEnabled}
					/>
				</ClayForm.Group>

				<Input
					error={formik.errors.sentenceTransformer}
					items={transformToLabelValueArray(
						availableSentenceTransformers
					)}
					label={Liferay.Language.get('sentence-transformer')}
					name={`${namespace}sentenceTransformer`}
					onBlur={_handleInputBlur('sentenceTransformer')}
					onChange={_handleInputChange('sentenceTransformer')}
					type="select"
					value={formik.values.sentenceTransformer}
				>
					{formik.values.sentenceTransformer ===
						SENTENCE_TRANSFORMER_TYPES.HUGGING_FACE_INFERENCE_API && (
						<ClayForm.FeedbackGroup>
							<ClayForm.Text>
								{Liferay.Language.get(
									'sentence-transformer-hugging-face-help'
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

				{formik.values.sentenceTransformer ===
					SENTENCE_TRANSFORMER_TYPES.TXTAI && (
					<>
						<Input
							error={formik.errors.txtaiHostAddress}
							helpText={Liferay.Language.get(
								'sentence-transformer-txtai-host-address-help'
							)}
							label={Liferay.Language.get('txtai-host-address')}
							name={`${namespace}txtaiHostAddress`}
							onBlur={_handleInputBlur('txtaiHostAddress')}
							onChange={_handleInputChange('txtaiHostAddress')}
							required
							touched={formik.touched.txtaiHostAddress}
							value={formik.values.txtaiHostAddress}
						/>

						<Input
							error={formik.errors.txtaiUsername}
							helpText={Liferay.Language.get(
								'sentence-transformer-txtai-username-help'
							)}
							label={Liferay.Language.get('username')}
							name={`${namespace}txtaiUsername`}
							onBlur={_handleInputBlur('txtaiUsername')}
							onChange={_handleInputChange('txtaiUsername')}
							value={formik.values.txtaiUsername}
						/>

						<Input
							error={formik.errors.txtaiPassword}
							helpText={Liferay.Language.get(
								'sentence-transformer-txtai-password-help'
							)}
							label={Liferay.Language.get('password')}
							name={`${namespace}txtaiPassword`}
							onBlur={_handleInputBlur('txtaiPassword')}
							onChange={_handleInputChange('txtaiPassword')}
							type="password"
							value={formik.values.txtaiPassword}
						/>
					</>
				)}

				{formik.values.sentenceTransformer ===
					SENTENCE_TRANSFORMER_TYPES.HUGGING_FACE_INFERENCE_API && (
					<>
						<Input
							error={formik.errors.huggingFaceAccessToken}
							label={Liferay.Language.get(
								'hugging-face-access-token'
							)}
							name={`${namespace}huggingFaceAccessToken`}
							onBlur={_handleInputBlur('huggingFaceAccessToken')}
							onChange={_handleInputChange(
								'huggingFaceAccessToken'
							)}
							required
							touched={formik.touched.huggingFaceAccessToken}
							value={formik.values.huggingFaceAccessToken}
						/>

						<Input
							error={formik.errors.model}
							helpText={Liferay.Language.get(
								'sentence-transformer-model-help'
							)}
							label={Liferay.Language.get('model')}
							name={`${namespace}model`}
							onBlur={_handleInputBlur('model')}
							onChange={_handleInputChange('model')}
							required
							touched={formik.touched.model}
							type="model"
							value={formik.values.model}
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
							error={formik.errors.modelTimeout}
							helpText={Liferay.Language.get(
								'sentence-transformer-model-timeout-help'
							)}
							label={Liferay.Language.get('model-timeout')}
							name={`${namespace}modelTimeout`}
							onBlur={_handleInputBlur('modelTimeout')}
							onChange={_handleInputChange('modelTimeout')}
							options={{max: 60, min: 0}}
							required
							touched={formik.touched.modelTimeout}
							type="number"
							value={formik.values.modelTimeout}
						/>
					</>
				)}

				<Input
					error={formik.errors.embeddingVectorDimensions}
					helpText={Liferay.Language.get(
						'sentence-transformer-embedding-vector-dimensions-help'
					)}
					items={transformToLabelValueArray(
						availableEmbeddingVectorDimensions
					)}
					label={Liferay.Language.get('embedding-vector-dimensions')}
					name={`${namespace}embeddingVectorDimensions`}
					onBlur={_handleInputBlur('embeddingVectorDimensions')}
					onChange={_handleInputChange('embeddingVectorDimensions')}
					type="select"
					value={formik.values.embeddingVectorDimensions}
				/>

				<TestConfigurationButton
					assetEntryClassNames={formik.values.assetEntryClassNames}
					availableSentenceTransformers={
						availableSentenceTransformers
					}
					cacheTimeout={formik.values.cacheTimeout}
					embeddingVectorDimensions={
						formik.values.embeddingVectorDimensions
					}
					errors={formik.errors}
					huggingFaceAccessToken={
						formik.values.huggingFaceAccessToken
					}
					languageIds={formik.values.languageIds}
					maxCharacterCount={formik.values.maxCharacterCount}
					model={formik.values.model}
					modelTimeout={formik.values.modelTimeout}
					sentenceTransformer={formik.values.sentenceTransformer}
					sentenceTransformerEnabled={
						formik.values.sentenceTransformerEnabled
					}
					textTruncationStrategy={
						formik.values.textTruncationStrategy
					}
					txtaiHostAddress={formik.values.txtaiHostAddress}
					txtaiPassword={formik.values.txtaiPassword}
					txtaiUsername={formik.values.txtaiUsername}
				/>
			</div>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('index-settings')}
				</h3>

				<Input
					error={formik.errors.maxCharacterCount}
					helpText={Liferay.Language.get(
						'sentence-transformer-max-character-count-help'
					)}
					label={Liferay.Language.get('max-character-count')}
					name={`${namespace}maxCharacterCount`}
					onBlur={_handleInputBlur('maxCharacterCount')}
					onChange={_handleInputChange('maxCharacterCount')}
					options={{max: 10000, min: 50}}
					required
					touched={formik.touched.maxCharacterCount}
					type="number"
					value={formik.values.maxCharacterCount}
				/>

				<Input
					error={formik.errors.textTruncationStrategy}
					helpText={Liferay.Language.get(
						'sentence-transformer-text-truncation-strategy-help'
					)}
					items={transformToLabelValueArray(
						availableTextTruncationStrategies
					)}
					label={Liferay.Language.get('text-truncation-strategy')}
					name={`${namespace}textTruncationStrategy`}
					onBlur={_handleInputBlur('textTruncationStrategy')}
					onChange={_handleInputChange('textTruncationStrategy')}
					type="select"
					value={formik.values.textTruncationStrategy}
				/>

				<Input
					error={formik.errors.assetEntryClassNames}
					helpText={Liferay.Language.get(
						'sentence-transformer-asset-entry-class-names-help'
					)}
					items={transformToLabelValueArray(
						availableAssetEntryClassNames
					)}
					label={Liferay.Language.get('asset-entry-class-names')}
					name={`${namespace}assetEntryClassNames`}
					onBlur={_handleInputBlur('assetEntryClassNames')}
					onChange={_handleInputChange('assetEntryClassNames')}
					required
					touched={formik.touched.assetEntryClassNames}
					type="multiple"
					value={formik.values.assetEntryClassNames}
				/>

				<Input
					error={formik.errors.languageIds}
					helpText={Liferay.Language.get(
						'sentence-transformer-language-ids-help'
					)}
					items={transformToLabelValueArray(
						availableLanguageDisplayNames
					)}
					label={Liferay.Language.get('language-ids')}
					name={`${namespace}languageIds`}
					onBlur={_handleInputBlur('languageIds')}
					onChange={_handleInputChange('languageIds')}
					required
					touched={formik.touched.languageIds}
					type="multiple"
					value={formik.values.languageIds}
				/>
			</div>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('search-settings')}
				</h3>

				<Input
					error={formik.errors.cacheTimeout}
					helpText={Liferay.Language.get(
						'sentence-transformer-cache-timeout-help'
					)}
					label={Liferay.Language.get('cache-timeout')}
					name={`${namespace}cacheTimeout`}
					onBlur={_handleInputBlur('cacheTimeout')}
					onChange={_handleInputChange('cacheTimeout')}
					options={{min: 0}}
					required
					touched={formik.touched.cacheTimeout}
					type="number"
					value={formik.values.cacheTimeout}
				/>
			</div>
		</div>
	);
}
