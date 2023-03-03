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
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SelectSXPBlueprintModal from '../../select_sxp_blueprint_modal/SelectSXPBlueprintModal';

function SXPBlueprintAttributes({
	index,
	onBlur,
	onInputSetItemChange,
	touched,
	value,
}) {
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
				`/o/search-experiences-rest/v1.0/sxp-blueprints/${value.attributes?.sxpBlueprintId}`,
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

	const _handleChangeAttribute = (property) => (event) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: event.target.value,
			},
		});
	};

	const _handleSXPBlueprintSelectorSubmit = (id, title) => {
		onInputSetItemChange(index, {
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
		onInputSetItemChange(index, {
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
								aria-label={Liferay.Language.get('remove')}
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
			</div>

			<div className="form-group-autofit">
				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('character-threshold')}

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'character-threshold-for-displaying-suggestions-contributor-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('character-threshold')}
						min="0"
						onChange={_handleChangeAttribute('characterThreshold')}
						type="number"
						value={value.attributes?.characterThreshold || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('include-asset-url')}

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'include-asset-url-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

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

				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('include-asset-summary')}

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'include-asset-summary-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get(
							'include-asset-summary'
						)}
						onChange={_handleChangeAttribute(
							'includeAssetSearchSummary'
						)}
						value={value.attributes?.includeAssetSearchSummary}
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
					<label>
						{Liferay.Language.get('fields')}

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'fields-suggestion-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

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

export default SXPBlueprintAttributes;
