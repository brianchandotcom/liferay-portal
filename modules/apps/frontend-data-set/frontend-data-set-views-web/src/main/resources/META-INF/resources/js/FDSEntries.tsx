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

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useRef, useState} from 'react';

import '../css/FDSEntries.scss';
import {
	API_URL,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	PAGINATION_PROPS,
} from './Constants';
import {FDSViewType} from './FDSViews';
import RequiredMark from './components/RequiredMark';
import ValidationFeedback from './components/ValidationFeedback';

type FDSEntryType = {
	[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW]: Array<FDSViewType>;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	id: string;
	label: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
};

const RESTApplicationItem = ({
	query,
	restApplication,
}: {
	query: string;
	restApplication: string;
}) => {
	const fuzzyMatch = fuzzy.match(query, restApplication, FUZZY_OPTIONS);

	return (
		<ClayLayout.ContentRow>
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				<span>{restApplication}</span>
			)}
		</ClayLayout.ContentRow>
	);
};

const ViewsCountRenderer = ({itemData}: {itemData: FDSEntryType}) => {
	const count = itemData[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW].length;

	return (
		<ClayBadge displayType={!count ? 'warning' : 'info'} label={count} />
	);
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
				aria-label={Liferay.Language.get('search')}
				onChange={onSearch}
				value={query}
			/>

			<ClayDropDown.ItemList items={restSchemas} role="listbox">
				{(item: string) => {
					const fuzzymatch = fuzzy.match(query, item, FUZZY_OPTIONS);

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
					const fuzzymatch = fuzzy.match(query, item, FUZZY_OPTIONS);

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

interface IAddFDSEntryModalContentInterface {
	closeModal: Function;
	loadData: Function;
	namespace: string;
	restApplications: Array<string>;
}

const AddFDSEntryModalContent = ({
	closeModal,
	loadData,
	namespace,
	restApplications,
}: IAddFDSEntryModalContentInterface) => {
	const [labelValidationError, setLabelValidationError] = useState(false);
	const [
		requiredRESTApplicationValidationError,
		setRequiredRESTApplicationValidationError,
	] = useState(false);
	const [
		noEnpointsRESTApplicationValidationError,
		setNoEnpointsRESTApplicationValidationError,
	] = useState(false);
	const [restSchemaValidationError, setRESTSchemaValidationError] = useState(
		false
	);
	const [
		restEndpointValidationError,
		setRESTEndpointValidationError,
	] = useState(false);
	const [restSchemaEndpoints, setRESTSchemaEndpoints] = useState<
		Map<string, Array<string>>
	>(new Map());
	const [selectedRESTApplication, setSelectedRESTApplication] = useState<
		string | null
	>();
	const [selectedRESTSchema, setSelectedRESTSchema] = useState<
		string | null
	>();
	const [selectedRESTEndpoint, setSelectedRESTEndpoint] = useState<
		string | null
	>();

	const fdsEntryLabelRef = useRef<HTMLInputElement>(null);

	const addFDSEntry = async () => {
		if (!selectedRESTApplication) {
			return;
		}

		selectedRESTApplication;

		const body = {
			label: fdsEntryLabelRef.current?.value,
			restApplication: selectedRESTApplication,
			restEndpoint: selectedRESTEndpoint,
			restSchema: selectedRESTSchema,
		};

		const response = await fetch(API_URL.FDS_ENTRIES, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		const fdsEntry = await response.json();

		if (response.ok && fdsEntry?.id) {
			closeModal();

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});

			loadData();
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
		}
	};

	const getRESTSchemas = async (restApplication: string) => {
		if (!restApplication) {
			return;
		}

		const response = await fetch(`/o${restApplication}/openapi.json`);

		const responseJson = await response.json();

		if (response.ok) {
			const paths = Object.keys(responseJson.paths ?? []);
			const schemaNames = Object.keys(
				responseJson.components?.schemas ?? []
			);

			const schemaEndpoints: Map<string, Array<string>> = new Map();

			schemaNames.forEach((schemaName) => {
				paths.forEach((path: string) => {
					if (path.includes('{')) {
						return;
					}

					if (
						responseJson.paths[
							path
						]?.get?.responses.default.content[
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
			}
			else if (schemaEndpoints.size === 1) {
				const schema = schemaEndpoints.keys().next().value;

				setSelectedRESTSchema(schema);

				const paths = schemaEndpoints.get(schema);

				if (paths?.length === 1) {
					setSelectedRESTEndpoint(paths[0]);
				}

				setNoEnpointsRESTApplicationValidationError(false);
			}
			else {
				setSelectedRESTSchema(null);

				setSelectedRESTEndpoint(null);

				setNoEnpointsRESTApplicationValidationError(false);
			}

			setRESTSchemaEndpoints(schemaEndpoints);
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
		}
	};

	const validate = () => {
		if (!fdsEntryLabelRef.current?.value) {
			setLabelValidationError(true);

			return false;
		}

		if (!selectedRESTApplication) {
			setRequiredRESTApplicationValidationError(true);

			return false;
		}

		if (noEnpointsRESTApplicationValidationError) {
			return false;
		}

		if (!selectedRESTSchema) {
			setRESTSchemaValidationError(true);

			return false;
		}

		if (!selectedRESTEndpoint) {
			setRESTEndpointValidationError(true);

			return false;
		}

		return true;
	};

	const RestApplicationDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu',
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

					setRequiredRESTApplicationValidationError(false);

					getRESTSchemas(item);
				}}
				restApplications={restApplications}
			/>
		</ClayDropDown>
	);

	const RestSchemaDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu',
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

					if (endpoints?.length === 1) {
						setSelectedRESTEndpoint(endpoints[0]);
					}
					else {
						setSelectedRESTEndpoint(null);
					}

					setRESTSchemaValidationError(false);
				}}
				restSchemas={Array.from(restSchemaEndpoints.keys())}
			/>
		</ClayDropDown>
	);

	const RestEndpointDropdown = () => (
		<ClayDropDown
			menuElementAttrs={{
				className: 'fds-entries-dropdown-menu',
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

					setRESTEndpointValidationError(false);
				}}
				restEndpoints={
					restSchemaEndpoints.get(selectedRESTSchema ?? '') ?? []
				}
			/>
		</ClayDropDown>
	);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-dataset')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group
					className={classNames({
						'has-error': labelValidationError,
					})}
				>
					<label htmlFor={`${namespace}fdsEntryLabelInput`}>
						{Liferay.Language.get('name')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsEntryLabelInput`}
						onBlur={() => {
							setLabelValidationError(
								!fdsEntryLabelRef.current?.value
							);
						}}
						ref={fdsEntryLabelRef}
						type="text"
					/>

					{labelValidationError && <ValidationFeedback />}
				</ClayForm.Group>

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
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								const success = validate();

								if (success) {
									addFDSEntry();
								}
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

interface IFDSEntriesInterface {
	fdsViewsURL: string;
	namespace: string;
	restApplications: Array<string>;
}

const FDSEntries = ({
	fdsViewsURL,
	namespace,
	restApplications,
}: IFDSEntriesInterface) => {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-dataset'),
				onClick: ({loadData}: {loadData: Function}) => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<AddFDSEntryModalContent
								closeModal={closeModal}
								loadData={loadData}
								namespace={namespace}
								restApplications={restApplications}
							/>
						),
					});
				},
			},
		],
	};

	const onViewClick = ({itemData}: {itemData: FDSEntryType}) => {
		const url = new URL(fdsViewsURL);

		url.searchParams.set(`${namespace}fdsEntryId`, itemData.id);
		url.searchParams.set(`${namespace}fdsEntryLabel`, itemData.label);

		navigate(url);
	};

	const onDeleteClick = ({
		itemData,
		loadData,
	}: {
		itemData: FDSEntryType;
		loadData: Function;
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'deleting-a-dataset-is-an-action-that-cannot-be-reversed'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						fetch(itemData.actions.delete.href, {
							method: itemData.actions.delete.method,
						})
							.then(() => {
								openToast({
									message: Liferay.Language.get(
										'your-request-completed-successfully'
									),
									type: 'success',
								});

								loadData();
							})
							.catch(() =>
								openToast({
									message: Liferay.Language.get(
										'your-request-failed-to-complete'
									),
									type: 'danger',
								})
							);
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-dataset'),
		});
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{fieldName: 'label', label: Liferay.Language.get('name')},
					{
						fieldName: 'restApplication',
						label: Liferay.Language.get('rest-application'),
					},
					{
						fieldName: 'restSchema',
						label: Liferay.Language.get('rest-schema'),
					},
					{
						fieldName: 'restEndpoint',
						label: Liferay.Language.get('rest-endpoint'),
					},
					{
						contentRenderer: 'viewsCount',
						fieldName: OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW,
						label: Liferay.Language.get('views'),
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
					},
				],
			},
		},
	];

	return (
		<div className="fds-entries">
			<FrontendDataSet
				apiURL={`${API_URL.FDS_ENTRIES}?nestedFields=${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW}`}
				creationMenu={creationMenu}
				customDataRenderers={{
					viewsCount: ViewsCountRenderer,
				}}
				id={`${namespace}FDSEntries`}
				itemsActions={[
					{
						icon: 'view',
						label: Liferay.Language.get('view'),
						onClick: onViewClick,
					},
					{
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDeleteClick,
					},
				]}
				style="fluid"
				views={views}
				{...PAGINATION_PROPS}
			/>
		</div>
	);
};

export {FDSEntryType};
export default FDSEntries;
