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

import {sub} from '../../../sxp_blueprint_admin/js/utils/language';
import Input from './Input';

const SENTENCE_TRANSFORM_PROVIDER_TYPES = {
	HUGGING_FACE: 'huggingFace',
	TXTAI: 'txtai',
};

/**
 * Formats the object into an array of label and value, important for inputs
 * that offer selection. If object is actually a flat array, this formats
 * the items into label-value pairs.
 *
 * Examples:
 * getEntries({en_US: 'English', es_ES: 'Spanish'})
 * => [{label: 'English', value: 'en_US'}, {label: 'Spanish', value: 'es_ES'}]
 * getEntries(['one', 'two'])
 * => [{label: 'one', value: 'one'}, {label: 'two', value: 'two'}]
 *
 * @param {Object} items
 * @return {Array}
 */
const getEntries = (items = {}) => {
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
 * Form within semantic search settings page, configures transform provider and
 * indexing settings.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
export default function ({
	assetEntryClassNames,
	availableAssetEntryClassNames,
	availableEmbeddingVectorDimensions,
	availableLanguageDisplayNames,
	availableSentenceTransformProviders,
	availableTextTruncationStrategies,
	cacheTimeout = '',
	embeddingVectorDimensions,
	enableGPU,
	huggingFaceAccessToken,
	languageIds,
	maxCharacterCount = '',
	model,
	modelTimeout = '',
	namespace = '',
	sentenceTransformProvider,
	sentenceTransformerEnabled,
	textTruncationStrategy,
	txtaiHostAddress,
}) {
	const _handleFormikValidate = (values) => {
		const errors = {};

		if (
			!values.modelTimeout &&
			values.sentenceTransformProvider ===
				SENTENCE_TRANSFORM_PROVIDER_TYPES.HUGGING_FACE
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

		if (!values.maxCharacterCount) {
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

		if (!values.assetEntryClassNames?.length) {
			errors.assetEntryClassNames = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('asset-entry-class-names')]
			);
		}

		if (!values.languageIds?.length) {
			errors.languageIds = sub(
				Liferay.Language.get('the-x-field-is-required'),
				[Liferay.Language.get('language-ids')]
			);
		}

		if (!values.cacheTimeout) {
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

		return errors;
	};

	const formik = useFormik({
		initialValues: {
			assetEntryClassNames,
			cacheTimeout,
			embeddingVectorDimensions,
			enableGPU,
			huggingFaceAccessToken,
			languageIds,
			maxCharacterCount,
			model,
			modelTimeout,
			sentenceTransformProvider,
			sentenceTransformerEnabled,
			textTruncationStrategy,
			txtaiHostAddress,
		},
		validate: _handleFormikValidate,
	});

	const _handleBlur = (name) => () => formik.setFieldTouched(name);

	const _handleChange = (name) => (val) => formik.setFieldValue(name, val);

	const _handleCheckboxChange = (name) => (event) =>
		formik.setFieldValue(name, event.target.checked);

	return (
		<div className="semantic-search-settings-root">
			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('transform-provider-settings')}
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
					error={formik.errors.sentenceTransformProvider}
					items={getEntries(availableSentenceTransformProviders)}
					label={Liferay.Language.get('sentence-transform-provider')}
					name={`${namespace}sentenceTransformProvider`}
					onBlur={_handleBlur('sentenceTransformProvider')}
					onChange={_handleChange('sentenceTransformProvider')}
					type="select"
					value={formik.values.sentenceTransformProvider}
				/>

				{formik.values.sentenceTransformProvider ===
					SENTENCE_TRANSFORM_PROVIDER_TYPES.TXTAI && (
					<Input
						error={formik.errors.txtaiHostAddress}
						helpText={Liferay.Language.get(
							'sentence-transformer-txtai-host-address-help'
						)}
						label={Liferay.Language.get('txtai-host-address')}
						name={`${namespace}txtaiHostAddress`}
						onBlur={_handleBlur('txtaiHostAddress')}
						onChange={_handleChange('txtaiHostAddress')}
						value={formik.values.txtaiHostAddress}
					/>
				)}

				{formik.values.sentenceTransformProvider ===
					SENTENCE_TRANSFORM_PROVIDER_TYPES.HUGGING_FACE && (
					<>
						<Input
							error={formik.errors.huggingFaceAccessToken}
							label={Liferay.Language.get(
								'hugging-face-access-token'
							)}
							name={`${namespace}huggingFaceAccessToken`}
							onBlur={_handleBlur('huggingFaceAccessToken')}
							onChange={_handleChange('huggingFaceAccessToken')}
							value={formik.values.huggingFaceAccessToken}
						/>

						<Input
							error={formik.errors.model}
							formText={Liferay.Language.get(
								'begin-typing-and-select-a-model'
							)}
							helpText={Liferay.Language.get(
								'sentence-transformer-model-help'
							)}
							label={Liferay.Language.get('model')}
							name={`${namespace}model`}
							onBlur={_handleBlur('model')}
							onChange={_handleChange('model')}
							touched={formik.touched.model}
							type="model"
							value={formik.values.model}
						/>

						<Input
							error={formik.errors.modelTimeout}
							helpText={Liferay.Language.get(
								'sentence-transformer-model-timeout-help'
							)}
							label={Liferay.Language.get('model-timeout')}
							name={`${namespace}modelTimeout`}
							onBlur={_handleBlur('modelTimeout')}
							onChange={_handleChange('modelTimeout')}
							options={{max: 60, min: 0}}
							required={true}
							touched={formik.touched.modelTimeout}
							type="number"
							value={formik.values.modelTimeout}
						/>

						<ClayForm.Group>
							<ClayCheckbox
								aria-label={Liferay.Language.get('enable-gpu')}
								checked={!!formik.values.enableGPU}
								label={Liferay.Language.get('enable-gpu')}
								name={`${namespace}enableGPU`}
								onChange={_handleCheckboxChange('enableGPU')}
								value={!!formik.values.enableGPU}
							/>
						</ClayForm.Group>
					</>
				)}

				<Input
					error={formik.errors.embeddingVectorDimensions}
					items={getEntries(availableEmbeddingVectorDimensions)}
					label={Liferay.Language.get('embedding-vector-dimensions')}
					name={`${namespace}embeddingVectorDimensions`}
					onBlur={_handleBlur('embeddingVectorDimensions')}
					onChange={_handleChange('embeddingVectorDimensions')}
					type="select"
					value={formik.values.embeddingVectorDimensions}
				/>
			</div>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('indexing-settings')}
				</h3>

				<Input
					error={formik.errors.maxCharacterCount}
					helpText={Liferay.Language.get(
						'sentence-transformer-max-character-count-help'
					)}
					label={Liferay.Language.get('max-character-count')}
					name={`${namespace}maxCharacterCount`}
					onBlur={_handleBlur('maxCharacterCount')}
					onChange={_handleChange('maxCharacterCount')}
					options={{max: 10000, min: 50}}
					required={true}
					touched={formik.touched.maxCharacterCount}
					type="number"
					value={formik.values.maxCharacterCount}
				/>

				<Input
					error={formik.errors.textTruncationStrategy}
					helpText={Liferay.Language.get(
						'sentence-transformer-text-truncation-strategy-help'
					)}
					items={getEntries(availableTextTruncationStrategies)}
					label={Liferay.Language.get('text-truncation-strategy')}
					name={`${namespace}textTruncationStrategy`}
					onBlur={_handleBlur('textTruncationStrategy')}
					onChange={_handleChange('textTruncationStrategy')}
					type="select"
					value={formik.values.textTruncationStrategy}
				/>

				<Input
					error={formik.errors.assetEntryClassNames}
					helpText={Liferay.Language.get(
						'sentence-transformer-asset-entry-class-names-help'
					)}
					items={getEntries(availableAssetEntryClassNames)}
					label={Liferay.Language.get('asset-entry-class-names')}
					name={`${namespace}assetEntryClassNames`}
					onBlur={_handleBlur('assetEntryClassNames')}
					onChange={_handleChange('assetEntryClassNames')}
					required={true}
					touched={formik.touched.assetEntryClassNames}
					type="multiple"
					value={formik.values.assetEntryClassNames}
				/>

				<Input
					error={formik.errors.languageIds}
					helpText={Liferay.Language.get(
						'sentence-transformer-language-ids-help'
					)}
					items={getEntries(availableLanguageDisplayNames)}
					label={Liferay.Language.get('language-ids')}
					name={`${namespace}languageIds`}
					onBlur={_handleBlur('languageIds')}
					onChange={_handleChange('languageIds')}
					required={true}
					touched={formik.touched.languageIds}
					type="multiple"
					value={formik.values.languageIds}
				/>
			</div>

			<Input
				error={formik.errors.cacheTimeout}
				label={Liferay.Language.get('cache-timeout')}
				name={`${namespace}cacheTimeout`}
				onBlur={_handleBlur('cacheTimeout')}
				onChange={_handleChange('cacheTimeout')}
				options={{min: 0}}
				required={true}
				touched={formik.touched.cacheTimeout}
				type="number"
				value={formik.values.cacheTimeout}
			/>
		</div>
	);
}
