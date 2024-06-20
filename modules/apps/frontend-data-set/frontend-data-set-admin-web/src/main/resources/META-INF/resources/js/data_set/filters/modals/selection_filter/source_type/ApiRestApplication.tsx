/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import RequiredMark from '../../../../../components/RequiredMark';
import ValidationFeedback from '../../../../../components/ValidationFeedback';
import RESTApplicationDropdownItem from '../../../../../components/rest/RESTApplicationDropdownItem';
import RESTApplicationDropdownMenu from '../../../../../components/rest/RESTApplicationDropdownMenu';
import RESTEndpointDropdownMenu from '../../../../../components/rest/RESTEndpointDropdownMenu';
import RESTSchemaDropdownMenu from '../../../../../components/rest/RESTSchemaDropdownMenu';
import {
	ALLOWED_ENDPOINTS_PARAMETERS,
	FUZZY_OPTIONS,
} from '../../../../../utils/constants';
import getFields from '../../../../../utils/getFields';
import openDefaultFailureToast from '../../../../../utils/openDefaultFailureToast';
import {IField, ISelectionFilter} from '../../../../../utils/types';

interface IApiRestApplicationModalContentProps {
	filter?: ISelectionFilter;
	itemKeyValidationError: boolean;
	itemLabelValidationError: boolean;
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
		selectedRESTApplication: string | null;
		selectedRESTEndpoint: string | null;
		selectedRESTSchema: string | null;
	}) => void;
	requiredRESTApplicationValidationError: boolean;
	restApplications: string[];
	restEndpointValidationError: boolean;
	restSchemaValidationError: boolean;
}

function ApiRestApplication({
	filter,
	itemKeyValidationError,
	itemLabelValidationError,
	namespace,
	onChange,
	requiredRESTApplicationValidationError,
	restApplications,
	restEndpointValidationError,
	restSchemaValidationError,
}: IApiRestApplicationModalContentProps) {
	const [fields, setFields] = useState<IField[]>([]);
	const [selectedItemKey, setSelectedItemKey] = useState<string>(
		filter?.itemKey ? filter.itemKey : ''
	);
	const [selectedItemLabel, setSelectedItemLabel] = useState<string>(
		filter?.itemLabel ? filter.itemLabel : ''
	);
	const [
		noEnpointsRESTApplicationValidationError,
		setNoEnpointsRESTApplicationValidationError,
	] = useState(false);
	const [restSchemaEndpoints, setRESTSchemaEndpoints] = useState<
		Map<string, Array<string>>
	>(new Map());
	const [selectedRESTApplication, setSelectedRESTApplication] = useState<
		string | null
	>(filter?.restApplication ? filter.restApplication : null);
	const [selectedRESTSchema, setSelectedRESTSchema] = useState<string | null>(
		filter?.restSchema ? filter.restSchema : null
	);
	const [selectedRESTEndpoint, setSelectedRESTEndpoint] = useState<
		string | null
	>(filter?.restEndpoint ? filter.restEndpoint : null);

	const isPathValid = (
		path: string,
		allowedParameters: string[]
	): boolean => {
		const paramsMatcher = RegExp('{(.*?)}', 'g');
		let matches;

		while ((matches = paramsMatcher.exec(path)) !== null) {
			if (!allowedParameters.includes(matches[1])) {
				return false;
			}
		}

		return true;
	};

	const getRESTSchemas = async (restApplication: string) => {
		if (!restApplication) {
			return;
		}
		const response = await fetch(`/o${restApplication}/openapi.json`);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJson = await response.json();

		const paths = Object.keys(responseJson.paths ?? []);
		const schemaNames = Object.keys(responseJson.components?.schemas ?? []);

		const schemaEndpoints: Map<string, Array<string>> = new Map();

		schemaNames.forEach((schemaName) => {
			paths.forEach((path: string) => {
				if (!isPathValid(path, ALLOWED_ENDPOINTS_PARAMETERS)) {
					return;
				}

				if (
					responseJson.paths[path]?.get?.responses.default.content[
						'application/json'
					]?.schema?.$ref?.endsWith(`/Page${schemaName}`)
				) {
					const endpoints = schemaEndpoints.get(schemaName) ?? [];

					endpoints.push(path);

					if (endpoints.length === 1) {
						schemaEndpoints.set(schemaName, endpoints);
					}
				}
			});
		});

		if (schemaEndpoints.size === 0) {
			setSelectedRESTSchema(null);
			setSelectedRESTEndpoint(null);

			setNoEnpointsRESTApplicationValidationError(true);

			onChange({
				selectedItemKey,
				selectedItemLabel,
				selectedRESTApplication: restApplication,
				selectedRESTEndpoint: null,
				selectedRESTSchema: null,
			});
		}
		else if (schemaEndpoints.size === 1) {
			const schema = schemaEndpoints.keys().next().value;

			setSelectedRESTSchema(schema);

			const paths = schemaEndpoints.get(schema);

			if (paths?.length === 1) {
				setSelectedRESTEndpoint(paths[0]);
			}

			setNoEnpointsRESTApplicationValidationError(false);

			onChange({
				selectedItemKey,
				selectedItemLabel,
				selectedRESTApplication: restApplication,
				selectedRESTEndpoint:
					paths?.length === 1 ? paths[0] : selectedRESTEndpoint,
				selectedRESTSchema: schema,
			});
		}
		else {
			setSelectedRESTSchema(null);
			setSelectedRESTEndpoint(null);

			setNoEnpointsRESTApplicationValidationError(false);

			onChange({
				selectedItemKey,
				selectedItemLabel,
				selectedRESTApplication: restApplication,
				selectedRESTEndpoint: null,
				selectedRESTSchema: null,
			});
		}

		setRESTSchemaEndpoints(schemaEndpoints);
	};

	const RestApplicationDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className:
					'fds-entries-dropdown-menu fds-filter-rest-application-menu',
			}}
			trigger={
				<ClayButton
					aria-labelledby={`${namespace}restApplicationsLabel`}
					className="form-control form-control-select form-control-select-secondary"
					displayType="secondary"
					id={`${namespace}restApplicationsSelect`}
				>
					{selectedRESTApplication ? (
						<RESTApplicationDropdownItem
							query=""
							restApplication={selectedRESTApplication}
						/>
					) : (
						Liferay.Language.get('choose-an-option')
					)}
				</ClayButton>
			}
		>
			<RESTApplicationDropdownMenu
				onItemClick={(item: string) => {
					setSelectedRESTApplication(item);
					getRESTSchemas(item);

					onChange({
						selectedItemKey,
						selectedItemLabel,
						selectedRESTApplication: item,
						selectedRESTEndpoint,
						selectedRESTSchema,
					});
				}}
				restApplications={restApplications}
			/>
		</ClayDropDown>
	);

	const RestSchemaDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className:
					'fds-entries-dropdown-menu fds-filter-rest-schema-menu',
			}}
			trigger={
				<ClayButton
					aria-labelledby={`${namespace}restSchema`}
					className="form-control form-control-select form-control-select-secondary"
					displayType="secondary"
					id={`${namespace}restSchemaSelect`}
				>
					{selectedRESTSchema ||
						Liferay.Language.get('choose-an-option')}
				</ClayButton>
			}
		>
			<RESTSchemaDropdownMenu
				onItemClick={(item: string) => {
					setSelectedRESTSchema(item);

					const endpoints = restSchemaEndpoints.get(item);
					let endpoint;

					if (endpoints?.length === 1) {
						endpoint = endpoints[0];
						setSelectedRESTEndpoint(endpoint);
					}
					else {
						endpoint = null;
					}

					onChange({
						selectedItemKey,
						selectedItemLabel,
						selectedRESTApplication,
						selectedRESTEndpoint:
							endpoints?.length === 1
								? endpoint
								: selectedRESTEndpoint,
						selectedRESTSchema: item,
					});
				}}
				restSchemas={Array.from(restSchemaEndpoints.keys())}
			/>
		</ClayDropDown>
	);

	const RestEndpointDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className:
					'fds-entries-dropdown-menu fds-filter-rest-endpoint-menu',
			}}
			trigger={
				<ClayButton
					aria-labelledby={`${namespace}restEndpoint`}
					className="form-control form-control-select form-control-select-secondary"
					displayType="secondary"
					id={`${namespace}restEndpointSelect`}
				>
					{selectedRESTEndpoint ||
						Liferay.Language.get('choose-an-option')}
				</ClayButton>
			}
		>
			<RESTEndpointDropdownMenu
				onItemClick={(item: string) => {
					setSelectedRESTEndpoint(item);

					onChange({
						selectedItemKey,
						selectedItemLabel,
						selectedRESTApplication,
						selectedRESTEndpoint: item,
						selectedRESTSchema,
					});
				}}
				restEndpoints={
					restSchemaEndpoints.get(selectedRESTSchema ?? '') ?? []
				}
			/>
		</ClayDropDown>
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

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedRESTSchema]);

	const ItemKeyDropdownMenu = ({
		itemKeys: initialItemKeys,
		onItemClick,
	}: {
		itemKeys: (string | undefined)[];
		onItemClick: Function;
	}) => {
		const [itemKeys, setItemKeys] =
			useState<(string | undefined)[]>(initialItemKeys);
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
		const [itemLabels, setItemLabels] =
			useState<(string | undefined)[]>(initialItemLabels);
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
			{restApplications && (
				<ClayForm.Group
					className={classNames({
						'has-error':
							requiredRESTApplicationValidationError ||
							noEnpointsRESTApplicationValidationError,
					})}
				>
					<label
						htmlFor={`${namespace}restApplicationsSelect`}
						id={`${namespace}restApplicationsLabel`}
					>
						{Liferay.Language.get('rest-application')}

						<RequiredMark />
					</label>

					<RestApplicationDropdown />

					{requiredRESTApplicationValidationError && (
						<ValidationFeedback />
					)}

					{noEnpointsRESTApplicationValidationError && (
						<ValidationFeedback
							message={Liferay.Language.get(
								'there-are-no-usable-endpoints'
							)}
						/>
					)}
				</ClayForm.Group>
			)}

			{restSchemaEndpoints.size > 0 && (
				<ClayForm.Group
					className={classNames({
						'has-error': restSchemaValidationError,
					})}
				>
					<label
						htmlFor={`${namespace}restSchemaSelect`}
						id={`${namespace}restSchema`}
					>
						{Liferay.Language.get('rest-schema')}

						<RequiredMark />
					</label>

					<RestSchemaDropdown />

					{restSchemaValidationError && <ValidationFeedback />}
				</ClayForm.Group>
			)}

			{selectedRESTSchema && (
				<ClayForm.Group
					className={classNames({
						'has-error': restEndpointValidationError,
					})}
				>
					<label
						htmlFor={`${namespace}restEndpointSelect`}
						id={`${namespace}restEndpoint`}
					>
						{Liferay.Language.get('rest-endpoint')}

						<RequiredMark />
					</label>

					<RestEndpointDropdown />

					{restEndpointValidationError && <ValidationFeedback />}
				</ClayForm.Group>
			)}

			{selectedRESTSchema && (
				<>
					<ClayForm.Group
						className={classNames('form-group-autofit', {
							'has-error':
								itemKeyValidationError ||
								itemLabelValidationError,
						})}
					>
						<div className="form-group-item">
							<label>
								{Liferay.Language.get('item-key')}

								<RequiredMark />
							</label>

							<ClayDropDown
								className="fds-filter-item-key"
								menuElementAttrs={{
									className: 'fds-entries-dropdown-menu',
								}}
								trigger={
									<ClayButton
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
									>
										{selectedItemKey ? (
											<RESTApplicationDropdownItem
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

							{itemKeyValidationError && <ValidationFeedback />}
						</div>

						<div className="form-group-item">
							<label>
								{Liferay.Language.get('item-label')}

								<RequiredMark />
							</label>

							<ClayDropDown
								className="fds-filter-item-label"
								menuElementAttrs={{
									className: 'fds-entries-dropdown-menu',
								}}
								trigger={
									<ClayButton
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
									>
										{selectedItemLabel ? (
											<RESTApplicationDropdownItem
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

							{itemLabelValidationError && <ValidationFeedback />}
						</div>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}

export default ApiRestApplication;
