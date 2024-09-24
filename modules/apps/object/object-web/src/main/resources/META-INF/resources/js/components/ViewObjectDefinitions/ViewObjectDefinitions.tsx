/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {API, Card, stringUtils} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultFDSDataSetProps, formatActionURL} from '../../utils/fds';
import statusDataRenderer from '../FDSPropsTransformer/FDSDataRenderers/StatusDataRenderer';
import {ModalImportKeys} from '../ModalImport/ModalImport';
import ViewObjectDefinitionsLabelRenderer from '../ViewObjectDefinitionsLabelRenderer';
import objectDefinitionModifiedDateDataRenderer from './FDSDataRenderers/ObjectDefinitionModifiedDateDataRenderer';
import objectDefinitionSystemDataRenderer from './FDSDataRenderers/ObjectDefinitionSystemDataRenderer';
import ObjectFolderCardHeader from './ObjectFolderCardHeader';
import ObjectFoldersSideBar from './ObjectFoldersSidebar';
import {
	deleteObjectDefinition,
	getObjectFolderActions,
} from './objectDefinitionUtil';

import './ViewObjectDefinitions.scss';
import {ViewObjectDefinitionsModals} from './ViewObjectDefinitionsModals';

import type {IItemsActions} from '@liferay/frontend-data-set-web';

import type {FDSItem, IFDSTableProps} from '../../utils/fds';

export interface ModalImportProperties {
	JSONInputId: string;
	apiURL: string;
	importExtendedInfo?: KeyValueObject;
	importURL: string;
	modalImportKey: ModalImportKeys;
}

interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	importObjectDefinitionURL: string;
	importObjectFolderURL: string;
	learnResourceContext: any;
	modelBuilderURL: string;
	nameMaxLength: string;
	objectDefinitionsCreationMenu: {
		primaryItems?: any[];
		secondaryItems?: any[];
	};
	objectDefinitionsFDSActionDropdownItems: IItemsActions[];
	objectDefinitionsFDSName: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderPermissionsURL: string;
	portletNamespace: string;
}

export default function ViewObjectDefinitions({
	baseResourceURL,
	editObjectDefinitionURL,
	importObjectDefinitionURL,
	importObjectFolderURL,
	learnResourceContext,
	modelBuilderURL,
	nameMaxLength,
	objectDefinitionsCreationMenu,
	objectDefinitionsFDSActionDropdownItems,
	objectDefinitionsFDSName,
	objectDefinitionsStorageTypes,
	objectFolderPermissionsURL,
	portletNamespace,
}: ViewObjectDefinitionsProps) {
	const emptyAction = {href: '', method: ''};

	const initialValues: ObjectFoldersRequestInfo = {
		actions: {
			delete: emptyAction,
			get: emptyAction,
			permissions: emptyAction,
			update: emptyAction,
		},
		items: [],
	};

	const [deletedObjectDefinition, setDeletedObjectDefinition] =
		useState<DeletedObjectDefinition | null>();

	const [loading, setLoading] = useState(true);

	const [modalImportProperties, setModalImportProperties] =
		useState<ModalImportProperties>({
			JSONInputId: '',
			apiURL: '',
			importURL: '',
			modalImportKey: 'objectDefinition',
		});

	const [moveObjectDefinition, setMoveObjectDefinition] =
		useState<ObjectDefinition | null>();

	const [objectDefinitionsActions, setObjectDefinitionActions] =
		useState<Actions>();

	const [objectFoldersRequestInfo, setObjectFoldersRequestInfo] =
		useState<ObjectFoldersRequestInfo>(initialValues);

	const [reloadFDS, setReloadFDS] = useState(false);

	const [selectedObjectDefinition, setSelectedObjectDefinition] =
		useState<ObjectDefinition>();

	const [selectedObjectFolder, setSelectedObjectFolder] =
		useState<Partial<ObjectFolder>>(initialValues);

	const [showModal, setShowModal] = useState<ShowObjectDefinitionsModals>({
		addObjectDefinition: false,
		addObjectField: false,
		addObjectFolder: false,
		bindToRootObjectDefinition: false,
		deleteObjectDefinition: false,
		deleteObjectFolder: false,
		editObjectFolder: false,
		importModal: false,
		moveObjectDefinition: false,
		objectFieldDeletionNotAllowed: false,
		unbindFromRootObjectDefinition: false,
	});

	const [updatedFDSItemsActions, setUpdatedFDSItemsActions] = useState(
		objectDefinitionsFDSActionDropdownItems
	);

	function handleShowDeleteObjectDefinitionModal() {
		setShowModal((previousState) => ({
			...previousState,
			deleteObjectDefinition: true,
		}));
	}

	function objectDefinitionLabelDataRenderer({
		itemData,
		value,
	}: FDSItem<ObjectDefinition>) {
		return (
			<ViewObjectDefinitionsLabelRenderer
				url={formatActionURL(editObjectDefinitionURL, itemData.id)}
				value={value}
			/>
		);
	}
	const getURL = () => {
		let url: string = '';

		if (selectedObjectFolder.externalReferenceCode) {
			url = `/o/object-admin/v1.0/object-definitions?${stringUtils.stringToURLParameterFormat(
				`filter=objectFolderExternalReferenceCode eq '${selectedObjectFolder.externalReferenceCode}'`
			)}`;
		}

		return url;
	};

	useEffect(() => {
		if (objectFoldersRequestInfo?.items.length > 1) {
			const itemsActions = [...objectDefinitionsFDSActionDropdownItems];

			itemsActions.push({
				data: {
					id: 'moveObjectDefinition',
					method: 'patch',
					permissionKey: 'update',
				},
				href: undefined,
				icon: 'move-folder',
				label: 'Move',
				target: undefined,
				type: 'item',
			});

			setUpdatedFDSItemsActions(itemsActions);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFoldersRequestInfo?.items.length]);

	const frontendDataSetProps = {
		...defaultFDSDataSetProps,
		apiURL: getURL(),
		creationMenu: objectDefinitionsCreationMenu,
		customDataRenderers: {
			objectDefinitionLabelDataRenderer,
			objectDefinitionModifiedDateDataRenderer,
			objectDefinitionSystemDataRenderer,
			statusDataRenderer,
		},
		emptyState: {
			description: Liferay.Language.get(
				'create-your-first-object-or-import-an-existing-one-to-start-working-with-object-folders'
			),
			image: '/states/empty_state.svg',
			title: Liferay.Language.get('no-objects-created-yet'),
		},
		id: objectDefinitionsFDSName,
		itemsActions: updatedFDSItemsActions,
		namespace:
			'_com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_',
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: ObjectDefinition;
		}) {
			if (
				action.data.id === 'bind' &&
				Liferay.FeatureFlags['LPS-187142']
			) {
				setSelectedObjectDefinition(itemData);

				setShowModal((previousState) => ({
					...previousState,
					bindToRootObjectDefinition: true,
				}));
			}

			if (action.data.id === 'deleteObjectDefinition') {
				deleteObjectDefinition({
					baseResourceURL,
					handleDeleteObjectDefinition: setDeletedObjectDefinition,
					handleShowDeleteObjectDefinitionModal,
					objectDefinitionId: itemData.id,
					objectDefinitionName: itemData.name,
					onAfterDeleteObjectDefinition: () => setReloadFDS(true),
				});
			}

			if (action.data.id === 'moveObjectDefinition') {
				setMoveObjectDefinition(itemData);

				setShowModal((previousState) => ({
					...previousState,
					moveObjectDefinition: true,
				}));
			}

			if (
				action.data.id === 'unbind' &&
				Liferay.FeatureFlags['LPS-187142']
			) {
				setSelectedObjectDefinition(itemData);

				setShowModal((previousState) => ({
					...previousState,
					unbindFromRootObjectDefinition: true,
				}));
			}
		},
		portletId:
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
		sidePanelId: 'none',
		style: 'fluid' as 'fluid',
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							contentRenderer:
								'objectDefinitionLabelDataRenderer',
							expand: false,
							fieldName: 'label',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: true,
						},
						{
							expand: false,
							fieldName: 'scope',
							label: Liferay.Language.get('scope'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer:
								'objectDefinitionSystemDataRenderer',
							expand: false,
							fieldName: 'system',
							label: Liferay.Language.get('system'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer:
								'objectDefinitionModifiedDateDataRenderer',
							expand: false,
							fieldName: 'dateModified',
							label: Liferay.Language.get('modified-date'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer: 'statusDataRenderer',
							expand: false,
							fieldName: 'status',
							label: Liferay.Language.get('status'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	const setDefaultToSearchParams = (
		allObjectFolders: ObjectFoldersRequestInfo,
		currentURL: URL
	) => {
		currentURL.searchParams.set('objectFolderName', 'Default');

		window.history.replaceState(null, '', currentURL.href);

		setSelectedObjectFolder(allObjectFolders.items[0]);
	};

	useEffect(() => {
		const makeFetch = async () => {
			const allObjectFolders = await API.getAllObjectFolders();

			setObjectFoldersRequestInfo(allObjectFolders);

			const objectDefinitions = await API.getAllObjectDefinitions();

			setObjectDefinitionActions(objectDefinitions.actions);

			const currentURL = new URL(window.location.href);

			const objectFolderNameSearchParam =
				currentURL.searchParams.get('objectFolderName');

			const newSelectedObjectFolder = allObjectFolders.items.find(
				(objectFolder) =>
					objectFolder.name === objectFolderNameSearchParam
			);

			if (newSelectedObjectFolder) {
				setSelectedObjectFolder(newSelectedObjectFolder);
			}
			else {
				setDefaultToSearchParams(allObjectFolders, currentURL);
			}

			setLoading(false);
		};

		makeFetch();

		Liferay.on('addObjectDefinition', () =>
			setShowModal((previousState) => ({
				...previousState,
				addObjectDefinition: true,
			}))
		);

		return () => {
			Liferay.detach('addObjectDefinition');
		};
	}, []);

	useEffect(() => {
		if (reloadFDS) {
			setTimeout(() => setReloadFDS(false), 200);
		}
	}, [reloadFDS]);

	return (
		<>
			<div className="lfr__object-web-view-object-definitions">
				{loading ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
					<>
						<ObjectFoldersSideBar
							baseResourceURL={baseResourceURL}
							importObjectFolderURL={importObjectFolderURL}
							objectDefinitionsActions={
								objectDefinitionsActions as Actions
							}
							objectFoldersRequestInfo={objectFoldersRequestInfo}
							portletNamespace={portletNamespace}
							selectedObjectFolder={
								selectedObjectFolder as ObjectFolder
							}
							setModalImportProperties={setModalImportProperties}
							setSelectedObjectFolder={setSelectedObjectFolder}
							setShowModal={setShowModal}
						/>
						<Card
							className="lfr__object-web-view-object-definitions-card"
							customHeader={
								<ObjectFolderCardHeader
									externalReferenceCode={
										selectedObjectFolder.externalReferenceCode
									}
									items={
										getObjectFolderActions({
											actions: {
												objectDefinitionActions:
													objectDefinitionsActions as Actions,
												objectFolderActions:
													selectedObjectFolder.actions as Actions,
											},
											baseResourceURL,
											importObjectDefinitionURL,
											objectFolderExternalReferenceCode:
												selectedObjectFolder.externalReferenceCode as string,
											objectFolderId:
												selectedObjectFolder.id as number,
											objectFolderPermissionsURL,
											portletNamespace,
											setModalImportProperties,
											setShowModal,
										}) as IItem[]
									}
									label={selectedObjectFolder.label}
									modelBuilderURL={modelBuilderURL}
									name={selectedObjectFolder.name}
								/>
							}
							viewMode="no-header-border"
						>
							{reloadFDS ? (
								<ClayLoadingIndicator
									displayType="secondary"
									size="sm"
								/>
							) : (
								<FrontendDataSet
									{...frontendDataSetProps}
									key={
										selectedObjectFolder.externalReferenceCode
									}
								/>
							)}
						</Card>
					</>
				)}
			</div>

			<ViewObjectDefinitionsModals
				baseResourceURL={baseResourceURL}
				deletedObjectDefinition={deletedObjectDefinition}
				learnResourceContext={learnResourceContext}
				modalImportProperties={modalImportProperties}
				moveObjectDefinition={moveObjectDefinition}
				nameMaxLength={nameMaxLength}
				objectDefinitionsStorageTypes={objectDefinitionsStorageTypes}
				objectFoldersRequestInfo={objectFoldersRequestInfo}
				portletNamespace={portletNamespace}
				selectedObjectDefinition={selectedObjectDefinition}
				selectedObjectFolder={selectedObjectFolder}
				setDeletedObjectDefinition={setDeletedObjectDefinition}
				setMoveObjectDefinition={setMoveObjectDefinition}
				setObjectFoldersRequestInfo={setObjectFoldersRequestInfo}
				setReloadFDS={setReloadFDS}
				setSelectedObjectFolder={setSelectedObjectFolder}
				setShowModal={setShowModal}
				showModal={showModal}
			/>
		</>
	);
}
