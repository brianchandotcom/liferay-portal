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

import ClayButton from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import getCN from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import FieldList from './FieldList';
import SelectSXPBlueprintModal from './select_sxp_blueprint_modal/SelectSXPBlueprintModal';

const CONTRIBUTORS = {
	BASIC: 'basic',
	SXP_BLUEPRINT: 'blueprint',
};

const DEFAULT_ATTRIBUTES = {
	fields: [],
	includeAssetSummary: true,
	includeAssetURL: true,
	sxpBlueprintId: '',
};

/**
 * Cleans up the fields array by removing those that do not have the required
 * fields (contributorName, displayGroupName, size). If blueprint, check
 * for sxpBlueprintId as well.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({attributes, contributorName, displayGroupName, size}) => {
		if (contributorName === CONTRIBUTORS.BASIC) {
			return displayGroupName && size;
		}

		return (
			contributorName &&
			displayGroupName &&
			size &&
			attributes?.sxpBlueprintId
		);
	});

function SXPBlueprintAttributes({onBlur, onChange, touched, value}) {
	const [showModal, setShowModal] = useState(false);
	const [sxpBlueprint, setSXPBlueprint] = useState({
		loading: false,
		title: '',
	});

	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [multiSelectItems, setMultiSelectItems] = useState(
		(value.attributes?.fields || []).map((field) => ({
			label: field,
			value: field,
		}))
	);

	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	useEffect(() => {

		// Fetch the blueprint title using sxpBlueprintId inside attributes, since
		// title is not saved within initialSuggestionsContributorConfiguration.

		if (value.attributes?.sxpBlueprintId) {
			setSXPBlueprint({loading: true, title: ''});

			fetch(
				`${window.location.origin}/o/search-experiences-rest/v1.0/sxp-blueprints/${value.attributes?.sxpBlueprintId}`,
				{
					headers: new Headers({
						'Accept': 'application/json',
						'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
						'Content-Type': 'application/json',
					}),
					method: 'GET',
				}
			)
				.then((response) =>
					response.json().then((data) => ({
						data,
						ok: response.ok,
					}))
				)
				.then(({data, ok}) => {
					setSXPBlueprint({
						loading: false,
						title:
							!ok || data.status === 'NOT_FOUND'
								? `${value.attributes?.sxpBlueprintId}`
								: data.title,
					});
				})
				.catch(() => {
					setSXPBlueprint({
						loading: false,
						title: `${value.attributes?.sxpBlueprintId}`,
					});
				});
		}
	}, []); //eslint-disable-line

	const _handleSXPBlueprintSelectorSubmit = (id, title) => {
		onChange({
			attributes: {
				...value.attributes,
				sxpBlueprintId: id,
			},
		});

		setSXPBlueprint({loading: false, title});
	};

	const _handleSXPBlueprintSelectorClickRemove = () => {
		_handleSXPBlueprintSelectorSubmit('', '');

		onBlur('sxpBlueprintId')();
	};

	const _handleSXPBlueprintSelectorClickSelect = () => {
		setShowModal(true);
	};

	const _handleSXPBlueprintSelectorChange = (event) => {

		// To use validation from 'required' field, keep the onChange and value
		// properties but make its behavior resemble readOnly (input can only be
		// changed with the selector modal).

		event.preventDefault();
	};

	const _handleChangeAttribute = (property) => (event) => {
		onChange({
			attributes: {...value.attributes, [property]: event.target.value},
		});
	};

	const _handleMultiSelectBlur = () => {
		if (multiSelectValue) {
			_handleMultiSelectChange([
				...multiSelectItems,
				{
					label: multiSelectValue,
					value: multiSelectValue,
				},
			]);

			setMultiSelectValue('');
		}
	};

	const _handleMultiSelectChange = (newValue) => {
		onChange({
			attributes: {
				...value.attributes,
				fields: newValue.map((item) => item.value),
			},
		});
		setMultiSelectItems(newValue);
	};

	return (
		<>
			{showModal && (
				<SelectSXPBlueprintModal
					observer={observer}
					onClose={onClose}
					onSubmit={_handleSXPBlueprintSelectorSubmit}
					selectedId={value.attributes?.sxpBlueprintId || ''}
				/>
			)}

			<div className="form-group-autofit">
				<ClayInput.GroupItem
					className={getCN({
						'has-error':
							!value.attributes?.sxpBlueprintId &&
							touched.sxpBlueprintId,
					})}
				>
					<label>
						{Liferay.Language.get('blueprint')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<div className="select-sxp-blueprint">
						{sxpBlueprint.loading ? (
							<div className="form-control" readOnly>
								<ClayLoadingIndicator small />
							</div>
						) : (
							<ClayInput
								onBlur={onBlur('sxpBlueprintId')}
								onChange={_handleSXPBlueprintSelectorChange}
								required
								type="text"
								value={sxpBlueprint.title}
							/>
						)}

						{sxpBlueprint.title && (
							<ClayButton
								className="remove-sxp-blueprint"
								displayType="secondary"
								onClick={_handleSXPBlueprintSelectorClickRemove}
								small
							>
								<ClayIcon symbol="times-circle" />
							</ClayButton>
						)}

						<ClayButton
							displayType="secondary"
							onClick={_handleSXPBlueprintSelectorClickSelect}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</div>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="include-input">
					<label>{Liferay.Language.get('include-asset-url')}</label>

					<ClaySelect
						aria-label={Liferay.Language.get('include-asset-url')}
						onChange={_handleChangeAttribute('includeAssetURL')}
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

				<ClayInput.GroupItem className="include-input">
					<label>
						{Liferay.Language.get('include-asset-summary')}
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get(
							'include-asset-summary'
						)}
						onChange={_handleChangeAttribute('includeAssetSummary')}
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
						onBlur={_handleMultiSelectBlur}
						onChange={setMultiSelectValue}
						onItemsChange={_handleMultiSelectChange}
						value={multiSelectValue}
					/>
				</ClayInput.GroupItem>
			</div>
		</>
	);
}

function Inputs({onChange, onReplace, contributorOptions, value = {}}) {
	const [touched, setTouched] = useState({
		displayGroupName: false,
		size: false,
		sxpBlueprintId: false,
	});

	const _handleBlur = (field) => () => {
		setTouched({...touched, [field]: true});
	};

	const _handleChange = (property) => (event) => {
		onChange({[property]: event.target.value});
	};

	const _handleChangeContributorName = (event) => {
		if (event.target.value === CONTRIBUTORS.BASIC) {
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

			{value.contributorName === CONTRIBUTORS.SXP_BLUEPRINT && (
				<SXPBlueprintAttributes
					onBlur={_handleBlur}
					onChange={onChange}
					touched={touched}
					value={value}
				/>
			)}
		</ClayInput.GroupItem>
	);
}

function SearchBarConfigurationSuggestions({
	initialSuggestionsContributorConfiguration = '[]',
	isDXP = false,
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
		if (!isDXP) {
			return (
				<ClaySelect.Option
					label={Liferay.Language.get('basic')}
					value={CONTRIBUTORS.BASIC}
				/>
			);
		}

		const indexOfBasic = suggestionsContributorConfiguration.findIndex(
			(value) => value.contributorName === CONTRIBUTORS.BASIC
		);

		if (indexOfBasic > -1 && index !== indexOfBasic) {
			return (
				<ClaySelect.Option
					label={Liferay.Language.get('blueprint')}
					value={CONTRIBUTORS.SXP_BLUEPRINT}
				/>
			);
		}

		return (
			<>
				<ClaySelect.Option
					label={Liferay.Language.get('basic')}
					value={CONTRIBUTORS.BASIC}
				/>

				<ClaySelect.Option
					label={Liferay.Language.get('blueprint')}
					value={CONTRIBUTORS.SXP_BLUEPRINT}
				/>
			</>
		);
	};

	const _getDefaultValue = () => {
		if (
			suggestionsContributorConfiguration.some(
				(config) => config.contributorName === CONTRIBUTORS.BASIC
			)
		) {
			return {
				attributes: DEFAULT_ATTRIBUTES,
				contributorName: CONTRIBUTORS.SXP_BLUEPRINT,
				displayGroupName: '',
				size: '',
			};
		}

		return {
			contributorName: CONTRIBUTORS.BASIC,
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
				showAddButton={isDXP}
				showDeleteButton={isDXP}
				showDragButton={isDXP}
				value={suggestionsContributorConfiguration}
			/>
		</div>
	);
}

export default SearchBarConfigurationSuggestions;
