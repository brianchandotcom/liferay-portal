/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import getCN from 'classnames';
import React, {useState} from 'react';

import FieldList from './FieldList';

const DEFAULT_ATTRIBUTES = {
	blueprintKey: '',
	fields: [],
	includeAssetSummary: true,
	includeAssetURL: true,
};

/**
 * Cleans up the fields array by removing those that do not have the required
 * fields (contributorName, displayGroupName, size). If blueprint, check
 * for blueprintKey as well.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({attributes, contributorName, displayGroupName, size}) => {
		if (contributorName === 'basic') {
			return displayGroupName && size;
		}

		return (
			contributorName &&
			displayGroupName &&
			size &&
			attributes?.blueprintKey
		);
	});

function Inputs({onChange, onReplace, contributorOptions, value = {}}) {
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [multiSelectItems, setMultiSelectItems] = useState(
		(value.attributes?.fields || []).map((field) => ({
			label: field,
			value: field,
		}))
	);

	const [touched, setTouched] = useState({
		blueprintKey: false,
		displayGroupName: false,
		size: false,
	});

	const _handleBlur = (field) => () => {
		setTouched({...touched, [field]: true});
	};

	const _handleChange = (property) => (event) => {
		onChange({[property]: event.target.value});
	};

	const _handleChangeAttribute = (property) => (event) => {
		onChange({
			attributes: {...value.attributes, [property]: event.target.value},
		});
	};

	const _handleChangeContributorName = (event) => {
		if (event.target.value === 'basic') {
			onReplace({
				contributorName: event.target.value,
				displayGroupName: value.displayGroupName,
				size: value.size,
			});
		}
		else {
			onChange({
				attributes: DEFAULT_ATTRIBUTES,
				contributorName: event.target.value,
				displayGroupName: value.displayGroupName,
				size: value.size,
			});
		}
	};

	const _handleChangeMultiSelect = (newValue) => {
		onChange({
			attributes: {
				...value.attributes,
				fields: newValue.map((item) => item.value),
			},
		});
		setMultiSelectItems(newValue);
	};

	const _handleBlurMultiSelect = () => {
		if (multiSelectValue) {
			_handleChangeMultiSelect([
				...multiSelectItems,
				{
					label: multiSelectValue,
					value: multiSelectValue,
				},
			]);

			setMultiSelectValue('');
		}
	};

	return (
		<ClayInput.GroupItem>
			<div className="form-group-autofit">
				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('suggestion-contributor')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get(
							'suggestion-contributor'
						)}
						onChange={_handleChangeContributorName}
						required
						value={value.contributorName}
					>
						{contributorOptions}
					</ClaySelect>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem
					className={getCN({
						'has-error':
							!value.displayGroupName && touched.displayGroupName,
					})}
				>
					<label>
						{Liferay.Language.get('display-group-name')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						onBlur={_handleBlur('displayGroupName')}
						onChange={_handleChange('displayGroupName')}
						required
						type="text"
						value={value.displayGroupName || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem
					className={getCN('size-input', {
						'has-error': !value.size && touched.size,
					})}
				>
					<label>
						{Liferay.Language.get('size')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('size')}
						onBlur={_handleBlur('size')}
						onChange={_handleChange('size')}
						required
						type="number"
						value={value.size || ''}
					/>
				</ClayInput.GroupItem>
			</div>

			{value.contributorName === 'blueprint' && (
				<>
					<div className="form-group-autofit">
						<ClayInput.GroupItem
							className={getCN({
								'has-error':
									!value.attributes?.blueprintKey &&
									touched.blueprintKey,
							})}
						>
							<label>
								{Liferay.Language.get('blueprint-key')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClayInput
								onBlur={_handleBlur('blueprintKey')}
								onChange={_handleChangeAttribute(
									'blueprintKey'
								)}
								required
								type="text"
								value={value.attributes?.blueprintKey || ''}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem>
							<label>
								{Liferay.Language.get('include-asset-url')}
							</label>

							<ClaySelect
								aria-label={Liferay.Language.get(
									'include-asset-url'
								)}
								onChange={_handleChangeAttribute(
									'includeAssetURL'
								)}
								value={value.attributes?.includeAssetURL}
							>
								<ClaySelect.Option
									label={Liferay.Language.get('true')}
									value={true}
								/>

								<ClaySelect.Option
									label={Liferay.Language.get('false')}
									value={false}
								/>
							</ClaySelect>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem>
							<label>
								{Liferay.Language.get('include-asset-summary')}
							</label>

							<ClaySelect
								aria-label={Liferay.Language.get(
									'include-asset-summary'
								)}
								onChange={_handleChangeAttribute(
									'includeAssetSummary'
								)}
								value={value.attributes?.includeAssetSummary}
							>
								<ClaySelect.Option
									label={Liferay.Language.get('true')}
									value={true}
								/>

								<ClaySelect.Option
									label={Liferay.Language.get('false')}
									value={false}
								/>
							</ClaySelect>
						</ClayInput.GroupItem>
					</div>

					<div className="form-group-autofit">
						<ClayInput.GroupItem>
							<label>{Liferay.Language.get('fields')}</label>

							<ClayMultiSelect
								items={multiSelectItems}
								onBlur={_handleBlurMultiSelect}
								onChange={setMultiSelectValue}
								onItemsChange={_handleChangeMultiSelect}
								value={multiSelectValue}
							/>
						</ClayInput.GroupItem>
					</div>
				</>
			)}
		</ClayInput.GroupItem>
	);
}

function SearchBarConfigurationSuggestions({
	initialSuggestionsContributorConfiguration = '[]',
	namespace = '',
	suggestionsContributorConfigurationName = '',
}) {
	const [
		suggestionsContributorConfiguration,
		setSuggestionsContributorConfiguration,
	] = useState(
		JSON.parse(initialSuggestionsContributorConfiguration).map(
			(item, index) => ({
				...item,
				id: index, // For FieldList item `key` when reordering.
			})
		)
	);

	const _getContributorOptions = (index) => {
		const indexOfBasic = suggestionsContributorConfiguration.findIndex(
			(value) => value.contributorName === 'basic'
		);

		if (indexOfBasic > -1 && index !== indexOfBasic) {
			return (
				<ClaySelect.Option
					label={Liferay.Language.get('blueprint')}
					value="blueprint"
				/>
			);
		}

		return (
			<>
				<ClaySelect.Option
					label={Liferay.Language.get('basic')}
					value="basic"
				/>

				<ClaySelect.Option
					label={Liferay.Language.get('blueprint')}
					value="blueprint"
				/>
			</>
		);
	};

	const _getDefaultValue = () => {
		if (
			suggestionsContributorConfiguration.some(
				(config) => config.contributorName === 'basic'
			)
		) {
			return {
				attributes: DEFAULT_ATTRIBUTES,
				contributorName: 'blueprint',
				displayGroupName: '',
				size: '',
			};
		}

		return {
			contributorName: 'basic',
			displayGroupName: '',
			size: '',
		};
	};

	return (
		<div className="search-bar-configuration-suggestions">
			{removeEmptyFields(suggestionsContributorConfiguration).length >
			0 ? (
				removeEmptyFields(
					suggestionsContributorConfiguration
				).map(({id, ...item}) => (
					<input
						hidden
						key={id}
						name={`${namespace}${suggestionsContributorConfigurationName}`}
						readOnly
						value={JSON.stringify(item)}
					/>
				))
			) : (
				<input
					hidden
					name={`${namespace}${suggestionsContributorConfigurationName}`}
					readOnly
					value=""
				/>
			)}

			<FieldList
				addButtonLabel={Liferay.Language.get('add-contributor')}
				defaultValue={_getDefaultValue()}
				onChange={setSuggestionsContributorConfiguration}
				renderInputs={({index, onChange, onReplace, value}) => (
					<Inputs
						contributorOptions={_getContributorOptions(index)}
						key={index}
						onChange={onChange}
						onReplace={onReplace}
						value={value}
					/>
				)}
				value={suggestionsContributorConfiguration}
			/>
		</div>
	);
}

export default SearchBarConfigurationSuggestions;
