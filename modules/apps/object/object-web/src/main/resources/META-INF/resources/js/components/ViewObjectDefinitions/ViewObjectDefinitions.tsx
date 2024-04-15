/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {API, Card, stringUtils} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import statusDataRenderer from '../FDSPropsTransformer/FDSDataRenderers/StatusDataRenderer';
import ModalImport, {ModalImportKeys} from '../ModalImport/ModalImport';
import ModalObjectFieldDeletionNotAllowed from '../ModalObjectFieldDeletionNotAllowed';
import ViewObjectDefinitionsLabelRenderer from '../ViewObjectDefinitionsLabelRenderer';
import objectDefinitionModifiedDateDataRenderer from './FDSDataRenderers/ObjectDefinitionModifiedDateDataRenderer';
import objectDefinitionSystemDataRenderer from './FDSDataRenderers/ObjectDefinitionSystemDataRenderer';
import {ModalAddObjectDefinition} from './ModalAddObjectDefinition';
import {ModalAddObjectFolder} from './ModalAddObjectFolder';
import {ModalBindToRootObjectDefinition} from './ModalBindToRootObjectDefinition';
import {ModalDeleteObjectDefinition} from './ModalDeleteObjectDefinition';
import {ModalDeleteObjectFolder} from './ModalDeleteObjectFolder';
import {ModalEditObjectFolder} from './ModalEditObjectFolder';
import {ModalMoveObjectDefinition} from './ModalMoveObjectDefinition';
import {ModalUnbindObjectDefinition} from './ModalUnbindObjectDefinition';
import ObjectFolderCardHeader from './ObjectFolderCardHeader';
import ObjectFoldersSideBar from './ObjectFoldersSidebar';
import {
	deleteObjectDefinition,
	getObjectFolderActions,
} from './objectDefinitionUtil';

import './ViewObjectDefinitions.scss';

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
	objectDefinitionsFDSActionDropdownItems: any[];
	objectDefinitionsFDSName: any;
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

	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();

	const [loading, setLoading] = useState(true);

	const [modalImportProperties, setModalImportProperties] = useState<
		ModalImportProperties
	>({
		JSONInputId: '',
		apiURL: '',
		importURL: '',
		modalImportKey: 'objectDefinition',
	});

	const [
		moveObjectDefinition,
		setMoveObjectDefinition,
	] = useState<ObjectDefinition | null>();

	const [objectDefinitionsActions, setObjectDefinitionActions] = useState<
		Actions
	>();

	const [objectFoldersRequestInfo, setObjectFoldersRequestInfo] = useState<
		ObjectFoldersRequestInfo
	>(initialValues);

	const [reloadFDS, setReloadFDS] = useState(false);

	const [selectedObjectDefinition, setSelectedObjectDefinition] = useState<
		ObjectDefinition
	>();

	const [selectedObjectFolder, setSelectedObjectFolder] = useState<
		Partial<ObjectFolder>
	>(initialValues);

	const [showModal, setShowModal] = useState<ViewObjectDefinitionsModals>({
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
		setShowModal((previousState: ViewObjectDefinitionsModals) => ({
			...previousState,
			deleteObjectDefinition: true,
		}));
	}

	function objectDefinitionLabelDataRenderer({
		itemData,
		value,
	}: fdsItem<ObjectDefinition>) {
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
					method: 'update',
					permissionKey: 'update',
				},
				href: null,
				icon: 'move-folder',
				label: 'Move',
				target: null,
				type: 'item',
			});
			setUpdatedFDSItemsActions(itemsActions);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFoldersRequestInfo?.items.length]);

	const dataSetProps = {
		...defaultDataSetProps,
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
			image: '/states/empty_state.gif',
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

				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
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

				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					moveObjectDefinition: true,
				}));
			}

			if (
				action.data.id === 'unbind' &&
				Liferay.FeatureFlags['LPS-187142']
			) {
				setSelectedObjectDefinition(itemData);

				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
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

			const objectFolderNameSearchParam = currentURL.searchParams.get(
				'objectFolderName'
			);

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
			setShowModal((previousState: ViewObjectDefinitionsModals) => ({
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
												objectDefinitionActions: objectDefinitionsActions as Actions,
												objectFolderActions: selectedObjectFolder.actions as Actions,
											},
											baseResourceURL,
											importObjectDefinitionURL,
											objectFolderExternalReferenceCode: selectedObjectFolder.externalReferenceCode as string,
											objectFolderId: selectedObjectFolder.id as number,
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
									{...dataSetProps}
									key={
										selectedObjectFolder.externalReferenceCode
									}
								/>
							)}
						</Card>
					</>
				)}
			</div>

			{showModal.addObjectDefinition && (
				<ModalAddObjectDefinition
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addObjectDefinition: false,
							})
						);
					}}
					learnResourceContext={learnResourceContext}
					objectDefinitionsStorageTypes={
						objectDefinitionsStorageTypes
					}
					objectFolderExternalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					onAfterSubmit={() => {
						setReloadFDS(true);
					}}
				/>
			)}

			{showModal.importModal && (
				<ModalImport
					{...(modalImportProperties.modalImportKey ===
						'objectDefinition' && {
						onAfterImport: () => setReloadFDS(true),
					})}
					JSONInputId={modalImportProperties.JSONInputId}
					apiURL={modalImportProperties.apiURL}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								importModal: false,
							})
						);
					}}
					importExtendedInfo={
						modalImportProperties.importExtendedInfo as KeyValueObject
					}
					importURL={modalImportProperties.importURL}
					modalImportKey={modalImportProperties.modalImportKey}
					nameMaxLength={nameMaxLength}
					objectFolderExternalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					portletNamespace={portletNamespace}
					showModal={showModal.importModal}
				/>
			)}
			{showModal.addObjectFolder && (
				<ModalAddObjectFolder
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addObjectFolder: false,
							})
						);
					}}
					setObjectFoldersRequestInfo={setObjectFoldersRequestInfo}
					setSelectedObjectFolder={setSelectedObjectFolder}
				/>
			)}
			{showModal.bindToRootObjectDefinition &&
				Liferay.FeatureFlags['LPS-187142'] && (
					<ModalBindToRootObjectDefinition
						baseResourceURL={baseResourceURL}
						onVisibilityChange={() => {
							setShowModal(
								(
									previousState: ViewObjectDefinitionsModals
								) => ({
									...previousState,
									bindToRootObjectDefinition: false,
								})
							);
						}}
						selectedObjectDefinitionToBind={
							selectedObjectDefinition
						}
					/>
				)}
			{showModal.deleteObjectDefinition && (
				<ModalDeleteObjectDefinition
					handleDeleteObjectDefinition={() =>
						setDeletedObjectDefinition
					}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								deleteObjectDefinition: false,
							})
						);
					}}
					objectDefinition={
						deletedObjectDefinition as DeletedObjectDefinition
					}
					onAfterDeleteObjectDefinition={() => setReloadFDS(true)}
				/>
			)}
			{showModal.deleteObjectFolder && (
				<ModalDeleteObjectFolder
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								deleteObjectFolder: false,
							})
						);
					}}
					objectFolder={selectedObjectFolder as ObjectFolder}
				/>
			)}
			{showModal.editObjectFolder && (
				<ModalEditObjectFolder
					externalReferenceCode={
						selectedObjectFolder.externalReferenceCode as string
					}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								editObjectFolder: false,
							})
						);
					}}
					id={selectedObjectFolder.id as number}
					initialLabel={selectedObjectFolder.label}
					name={selectedObjectFolder.name}
					onAfterSubmit={(editedObjectFolder) => {
						setSelectedObjectFolder(editedObjectFolder);
						setObjectFoldersRequestInfo({
							...objectFoldersRequestInfo,
							items: objectFoldersRequestInfo.items.map(
								(objectFolder) => {
									if (
										objectFolder.name ===
										editedObjectFolder.name
									) {
										return {
											...objectFolder,
											externalReferenceCode:
												editedObjectFolder.externalReferenceCode,
											label: editedObjectFolder.label,
										};
									}

									return objectFolder;
								}
							),
						});
					}}
				/>
			)}
			{showModal.moveObjectDefinition && (
				<ModalMoveObjectDefinition
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								moveObjectDefinition: false,
							})
						);
					}}
					objectDefinitionId={moveObjectDefinition?.id as number}
					objectFolders={objectFoldersRequestInfo.items}
					onAfterMoveObjectDefinition={() => setReloadFDS(true)}
					setMoveObjectDefinition={setMoveObjectDefinition}
				/>
			)}
			{showModal.objectFieldDeletionNotAllowed &&
				selectedObjectDefinition &&
				Liferay.FeatureFlags['LPS-187142'] && (
					<ModalObjectFieldDeletionNotAllowed
						content={
							<span
								dangerouslySetInnerHTML={{
									__html: sub(
										Liferay.Language.get(
											'x-is-being-used-by-a-root-object-and-cannot-be-deleted'
										),
										`<strong>"${stringUtils.getLocalizableLabel(
											selectedObjectDefinition.defaultLanguageId,
											selectedObjectDefinition.label,
											selectedObjectDefinition.name
										)}"</strong>`
									),
								}}
							/>
						}
						onVisibilityChange={() =>
							setShowModal(
								(
									previousState: ViewObjectDefinitionsModals
								) => ({
									...previousState,
									objectFieldDeletionNotAllowed: false,
								})
							)
						}
					/>
				)}
			{showModal.unbindFromRootObjectDefinition &&
				Liferay.FeatureFlags['LPS-187142'] && (
					<ModalUnbindObjectDefinition
						baseResourceURL={baseResourceURL}
						onVisibilityChange={() => {
							setShowModal(
								(
									previousState: ViewObjectDefinitionsModals
								) => ({
									...previousState,
									unbindFromRootObjectDefinition: false,
								})
							);
						}}
						selectedObjectDefinitionToUnbind={
							selectedObjectDefinition
						}
					/>
				)}
		</>
	);
}
