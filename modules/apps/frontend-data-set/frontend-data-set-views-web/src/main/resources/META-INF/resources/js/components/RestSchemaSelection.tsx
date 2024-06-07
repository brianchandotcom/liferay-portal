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
import React, {useState} from 'react';

import '../../css/FDSEntries.scss';
import {ALLOWED_ENDPOINTS_PARAMETERS, FUZZY_OPTIONS} from '../utils/constants';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import {ISelectionFilter} from '../utils/types';
import RequiredMark from './RequiredMark';
import RESTApplicationItem from './RestApplicationItem';
import ValidationFeedback from './ValidationFeedback';

interface IRestSchemaSelectionProps {
	filter?: ISelectionFilter;
	namespace: string;
	onChange: Function;
	requiredRESTApplicationValidationError: boolean;
	restApplications: string[];
	restEndpointValidationError: boolean;
	restSchemaValidationError: boolean;
}

function RestSchemaSelection({
	filter,
	namespace,
	onChange,
	requiredRESTApplicationValidationError,
	restApplications,
	restEndpointValidationError,
	restSchemaValidationError,
}: IRestSchemaSelectionProps) {
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
				selectedRESTApplication: restApplication,
				selectedRESTEndpoint: null,
				selectedRESTSchema: null,
			});
		}

		setRESTSchemaEndpoints(schemaEndpoints);
	};

	const RestApplicationDropdownMenu = ({
		onItemClick,
		restApplications: initialRESTApplications,
	}: {
		onItemClick: Function;
		restApplications: Array<string>;
	}) => {
		const [restApplications, setRESTApplications] = useState<Array<string>>(
			initialRESTApplications || []
		);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');

			setRESTApplications(
				query
					? initialRESTApplications.filter((restApplication) =>
							restApplication.match(regexp)
					  ) || []
					: initialRESTApplications
			);
		};

		return (
			<>
				<ClayDropDown.Search
					aria-label={Liferay.Language.get('search')}
					onChange={onSearch}
					value={query}
				/>

				<ClayDropDown.ItemList items={restApplications} role="listbox">
					{(item: string) => (
						<ClayDropDown.Item
							key={item}
							onClick={() => onItemClick(item)}
							roleItem="option"
						>
							<RESTApplicationItem
								query={query}
								restApplication={item}
							/>
						</ClayDropDown.Item>
					)}
				</ClayDropDown.ItemList>
			</>
		);
	};

	const RestApplicationDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu fds-filter-rest-application-menu',
			}}
			trigger={
				<ClayButton
					aria-labelledby={`${namespace}restApplicationsLabel`}
					className="form-control form-control-select form-control-select-secondary"
					displayType="secondary"
					id={`${namespace}restApplicationsSelect`}
				>
					{selectedRESTApplication ? (
						<RESTApplicationItem
							query=""
							restApplication={selectedRESTApplication}
						/>
					) : (
						Liferay.Language.get('choose-an-option')
					)}
				</ClayButton>
			}
		>
			<RestApplicationDropdownMenu
				onItemClick={(item: string) => {
					setSelectedRESTApplication(item);
					getRESTSchemas(item);

					onChange({
						selectedRESTApplication: item,
						selectedRESTEndpoint,
						selectedRESTSchema,
					});
				}}
				restApplications={restApplications}
			/>
		</ClayDropDown>
	);

	const RestSchemaDropdownMenu = ({
		onItemClick,
		restSchemas: initialRESTSchemas,
	}: {
		onItemClick: Function;
		restSchemas: Array<string>;
	}) => {
		const [restSchemas, setRESTSchemas] = useState<Array<string>>(
			initialRESTSchemas
		);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');

			setRESTSchemas(
				query
					? initialRESTSchemas.filter((restSchema) => {
							return restSchema.match(regexp);
					  }) || []
					: initialRESTSchemas
			);
		};

		return (
			<>
				<ClayDropDown.Search
					className='fds-filter-rest-schemas-search'
					aria-label={Liferay.Language.get('search')}
					onChange={onSearch}
					value={query}
				/>

				<ClayDropDown.ItemList items={restSchemas} role="listbox">
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

	const RestSchemaDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu fds-filter-rest-schema-menu',
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
			<RestSchemaDropdownMenu
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

	const RestEndpointDropdownMenu = ({
		onItemClick,
		restEndpoints: initialRESTEndpoints,
	}: {
		onItemClick: Function;
		restEndpoints: Array<string>;
	}) => {
		const [restEndpoints, setRESTEndpoints] = useState<Array<string>>(
			initialRESTEndpoints || []
		);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');

			setRESTEndpoints(
				query
					? initialRESTEndpoints.filter((restEndpoint) => {
							return restEndpoint.match(regexp);
					  }) || []
					: initialRESTEndpoints
			);
		};

		return (
			<>
				<ClayDropDown.Search
					aria-label={Liferay.Language.get('search')}
					onChange={onSearch}
					value={query}
				/>

				<ClayDropDown.ItemList items={restEndpoints} role="listbox">
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

	const RestEndpointDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu fds-filter-rest-endpoint-menu',
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
			<RestEndpointDropdownMenu
				onItemClick={(item: string) => {
					setSelectedRESTEndpoint(item);

					onChange({
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
		</>
	);
}

export default RestSchemaSelection;
