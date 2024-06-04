/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import RequiredMark from '../../../../../components/RequiredMark';
import RESTApplicationItem from '../../../../../components/RestApplicationItem';
import RestSchemaSelection from '../../../../../components/RestSchemaSelection';
import {FUZZY_OPTIONS} from '../../../../../utils/constants';
import getFields from '../../../../../utils/getFields';
import {IField, ISelectionFilter} from '../../../../../utils/types';

interface IApiRestApplicationModalContentProps {
	filter?: ISelectionFilter;
	namespace: string;
	onChange: ({
		selectedItemKey,
		selectedItemLabel,
		selectedRESTApplication,
		selectedRESTEndpoint,
		selectedRESTSchema,
	}: {
		selectedItemKey: string;
		selectedItemLabel: string;
		selectedRESTApplication: string | undefined;
		selectedRESTEndpoint: string | undefined;
		selectedRESTSchema: string | undefined;
	}) => void;
	requiredRESTApplicationValidationError: boolean;
	restApplications: string[];
	restEndpointValidationError: boolean;
	restSchemaValidationError: boolean;
}

function ApiRestApplication({
	filter,
	namespace,
	onChange,
	requiredRESTApplicationValidationError,
	restApplications,
	restEndpointValidationError,
	restSchemaValidationError,
}: IApiRestApplicationModalContentProps) {
	const [fields, setFields] = useState<IField[]>([]);
	const [selectedRESTApplication, setSelectedRESTApplication] = useState<
		string | undefined
	>(filter?.restApplication ? filter.restApplication : undefined);
	const [selectedRESTSchema, setSelectedRESTSchema] = useState<
		string | undefined
	>(filter?.restSchema ? filter.restSchema : undefined);
	const [selectedRESTEndpoint, setSelectedRESTEndpoint] = useState<
		string | undefined
	>(filter?.restEndpoint ? filter.restEndpoint : undefined);
	const [selectedItemKey, setSelectedItemKey] = useState<string>(
		filter?.itemKey ? filter.itemKey : ''
	);
	const [selectedItemLabel, setSelectedItemLabel] = useState<string>(
		filter?.itemLabel ? filter.itemLabel : ''
	);

	useEffect(() => {
		if (selectedRESTApplication && selectedRESTSchema) {
			getFields({
				restApplication: selectedRESTApplication,
				restSchema: selectedRESTSchema,
			}).then((fields: IField[]) => {
				if (fields) {
					setFields(
						fields.filter(
							(field) =>
								field.type !== 'array' &&
								field.type !== 'object'
						)
					);
				}
			});
		}
	}, [selectedRESTApplication, selectedRESTSchema]);

	const ItemKeyDropdownMenu = ({
		itemKeys: initialItemKeys,
		onItemClick,
	}: {
		itemKeys: (string | undefined)[];
		onItemClick: Function;
	}) => {
		const [itemKeys, setItemKeys] = useState<(string | undefined)[]>(
			initialItemKeys
		);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');
			setItemKeys(
				query
					? initialItemKeys.filter((itemKey) => {
							return itemKey?.match(regexp);
					  }) || []
					: initialItemKeys
			);
		};

		return (
			<>
				<ClayDropDown.Search
					aria-label={Liferay.Language.get('search')}
					onChange={onSearch}
					value={query}
				/>

				<ClayDropDown.ItemList items={itemKeys} role="listbox">
					{(item: string) => {
						const fuzzymatch = fuzzy.match(
							query,
							item,
							FUZZY_OPTIONS
						);

						return (
							<ClayDropDown.Item
								key={item}
								onClick={() => onItemClick(item)}
								roleItem="option"
							>
								{fuzzymatch ? (
									<span
										dangerouslySetInnerHTML={{
											__html: fuzzymatch.rendered,
										}}
									/>
								) : (
									item
								)}
							</ClayDropDown.Item>
						);
					}}
				</ClayDropDown.ItemList>
			</>
		);
	};

	const ItemLabelDropdownMenu = ({
		itemLabels: initialItemLabels = [],
		onItemClick,
	}: {
		itemLabels: (string | undefined)[];
		onItemClick: Function;
	}) => {
		const [itemLabels, setItemLabels] = useState<(string | undefined)[]>(
			initialItemLabels
		);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');
			setItemLabels(
				query
					? initialItemLabels.filter((itemLabel) => {
							return itemLabel?.match(regexp);
					  }) || []
					: initialItemLabels
			);
		};

		return (
			<>
				<ClayDropDown.Search
					aria-label={Liferay.Language.get('search')}
					onChange={onSearch}
					value={query}
				/>

				<ClayDropDown.ItemList items={itemLabels} role="listbox">
					{(item: string) => {
						const fuzzymatch = fuzzy.match(
							query,
							item,
							FUZZY_OPTIONS
						);

						return (
							<ClayDropDown.Item
								key={item}
								onClick={() => onItemClick(item)}
								roleItem="option"
							>
								{fuzzymatch ? (
									<span
										dangerouslySetInnerHTML={{
											__html: fuzzymatch.rendered,
										}}
									/>
								) : (
									item
								)}
							</ClayDropDown.Item>
						);
					}}
				</ClayDropDown.ItemList>
			</>
		);
	};

	return (
		<>
			<RestSchemaSelection
				filter={filter}
				namespace={namespace}
				onChange={({
					selectedRESTApplication: selectedRESTApplication,
					selectedRESTEndpoint: selectedRESTEndpoint,
					selectedRESTSchema: selectedRESTSchema,
				}: {
					selectedRESTApplication: string;
					selectedRESTEndpoint: string;
					selectedRESTSchema: string;
				}) => {
					setSelectedRESTApplication(selectedRESTApplication);
					setSelectedRESTEndpoint(selectedRESTEndpoint);
					setSelectedRESTSchema(selectedRESTSchema);

					onChange({
						selectedItemKey,
						selectedItemLabel,
						selectedRESTApplication,
						selectedRESTEndpoint,
						selectedRESTSchema,
					});
				}}
				requiredRESTApplicationValidationError={
					requiredRESTApplicationValidationError
				}
				restApplications={restApplications}
				restEndpointValidationError={restEndpointValidationError}
				restSchemaValidationError={restSchemaValidationError}
			/>

			{selectedRESTSchema && (
				<>
					<ClayForm.Group className="form-group-autofit">
						<div className="form-group-item">
							<label>
								{Liferay.Language.get('item-key')}

								<RequiredMark />
							</label>

							<ClayDropDown
								menuElementAttrs={{
									className: 'fds-entries-dropdown-menu',
								}}
								trigger={
									<ClayButton
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
									>
										{selectedItemKey ? (
											<RESTApplicationItem
												query=""
												restApplication={
													selectedItemKey
												}
											/>
										) : (
											Liferay.Language.get(
												'choose-an-option'
											)
										)}
									</ClayButton>
								}
							>
								<ItemKeyDropdownMenu
									itemKeys={fields.map((field) => field.name)}
									onItemClick={(item: string) => {
										setSelectedItemKey(item);

										onChange({
											selectedItemKey: item,
											selectedItemLabel,
											selectedRESTApplication,
											selectedRESTEndpoint,
											selectedRESTSchema,
										});
									}}
								/>
							</ClayDropDown>
						</div>

						<div className="form-group-item">
							<label>
								{Liferay.Language.get('item-label')}

								<RequiredMark />
							</label>

							<ClayDropDown
								menuElementAttrs={{
									className: 'fds-entries-dropdown-menu',
								}}
								trigger={
									<ClayButton
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
									>
										{selectedItemLabel ? (
											<RESTApplicationItem
												query=""
												restApplication={
													selectedItemLabel
												}
											/>
										) : (
											Liferay.Language.get(
												'choose-an-option'
											)
										)}
									</ClayButton>
								}
							>
								<ItemLabelDropdownMenu
									itemLabels={fields.map(
										(field) => field.label
									)}
									onItemClick={(item: string) => {
										setSelectedItemLabel(item);

										onChange({
											selectedItemKey,
											selectedItemLabel: item,
											selectedRESTApplication,
											selectedRESTEndpoint,
											selectedRESTSchema,
										});
									}}
								/>
							</ClayDropDown>
						</div>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}

export default ApiRestApplication;
