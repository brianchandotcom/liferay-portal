/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

const CPDefinitionSpecificationOptionValueAutocomplete = ({
	catalogDefaultLanguageId,
	createNewSpecification,
}) => {
	const [specifications, setSpecifications] = useState([]);
	const [listTypeEntries, setListTypeEntries] = useState([]);
	const [search, setSearch] = useState('');
	const [selectedItem, setSelectedItem] = useState(0);

	useEffect(() => {
		window.top.Liferay.fire('is-loading-modal', {isLoading: true});
	}, []);

	useEffect(() => {
		AdminCatalogResource.getSpecifications(search)
			.catch((error) => {
				const message =
					error.message ??
					Liferay.Language.get('an-unexpected-error-occurred');

				window.parent.Liferay.Util.openToast({
					message,
					type: 'danger',
				});
			})
			.then(({items}) => {
				setSpecifications(items);
				window.top.Liferay.fire('is-loading-modal', {isLoading: false});
			});
	}, [search]);

	useEffect(() => {
		if (selectedItem > 0) {
			fetch(
				`/o/headless-admin-list-type/v1.0/list-type-definitions/${selectedItem}/list-type-entries`
			)
				.catch((error) => {
					const message =
						error.message ??
						Liferay.Language.get('an-unexpected-error-occurred');

					window.parent.Liferay.Util.openToast({
						message,
						type: 'danger',
					});
				})
				.then((response) => response.json())
				.then(({items}) => setListTypeEntries(items));
		}
		else {
			setListTypeEntries([]);
		}
	}, [selectedItem]);

	const handleSelectChange = (event) => {
		const selectedValue = event.target.value;
		const selectedListTypeEntry = listTypeEntries.find(
			(listTypeEntry) => listTypeEntry.key === selectedValue
		);
		if (selectedListTypeEntry) {
			Liferay.fire('list-type-entry-selected', {
				name_i18n: selectedListTypeEntry.name_i18n,
			});
		}
	};

	const handleSpecificationChange = (event) => {
		const labelValue = event.target.value;
		Liferay.fire('specification-created', {
			labelValue,
		});
	};

	const handleInputChange = (event) => {
		const inputValue = event.target.value;
		Liferay.fire('input-value-selected', {
			inputValue,
		});
	};

	return (
		<ClayForm.Group aria-required={true}>
			<label
				aria-required={true}
				className="control-label"
				htmlFor="autocomplete"
				id="autocomplete-label"
			>
				{Liferay.Language.get('specification')}

				<span className="reference-mark text-warning">
					<ClayIcon symbol="asterisk" />
				</span>

				<span className="hide-accessible sr-only">
					{Liferay.Language.get('required')}
				</span>
			</label>

			{createNewSpecification && (
				<ClayInput
					aria-required={true}
					id="specificationKey"
					name="specificationKey"
					onChange={handleSpecificationChange}
					placeholder={Liferay.Language.get('specification')}
					required={true}
				/>
			)}

			{!createNewSpecification && (
				<ClayAutocomplete
					allowsCustomValue={false}
					aria-labelledby="autocomplete-label"
					aria-required={true}
					id="autocomplete"
					items={specifications}
					menuTrigger="focus"
					onChange={setSearch}
					required={true}
					value={search}
				>
					{(
						{
							key: specificationKey,
							listTypeDefinitionId,
							optionCategory = {},
							title,
						},
						index
					) => (
						<ClayAutocomplete.Item
							key={index}
							onClick={() => {
								setSelectedItem(listTypeDefinitionId);
								Liferay.fire('specification-selected', {
									optionCategoryId: optionCategory.id ?? 0,
									specificationKey,
								});
							}}
							textValue={title[catalogDefaultLanguageId]}
						/>
					)}
				</ClayAutocomplete>
			)}

			<label
				aria-required={true}
				className="control-label"
				htmlFor="value"
				id="value-label"
			>
				{Liferay.Language.get('value')}

				<span className="reference-mark text-warning">
					<ClayIcon symbol="asterisk" />
				</span>

				<span className="hide-accessible sr-only">
					{Liferay.Language.get('required')}
				</span>
			</label>

			{!!listTypeEntries.length && (
				<ClaySelect
					name="listTypeEntriesSelect"
					onChange={handleSelectChange}
				>
					<ClaySelect.Option label="Select an option" />

					{listTypeEntries.map((listTypeEntry) => (
						<ClaySelect.Option
							key={listTypeEntry}
							label={`${listTypeEntry.name}`}
							value={listTypeEntry.key}
						/>
					))}
				</ClaySelect>
			)}

			{!listTypeEntries.length && (
				<ClayInput onChange={handleInputChange} />
			)}
		</ClayForm.Group>
	);
};

export default CPDefinitionSpecificationOptionValueAutocomplete;
