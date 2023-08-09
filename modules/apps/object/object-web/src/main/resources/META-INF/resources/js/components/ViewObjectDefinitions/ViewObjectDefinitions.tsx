/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	API,
	Card,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import CardHeader from './CardHeader';
import objectDefinitionModifiedDateDataRenderer from './FDSDataRenderers/ObjectDefinitionModifiedDateDataRenderer';
import objectDefinitionStatusDataRenderer from './FDSDataRenderers/ObjectDefinitionStatusDataRenderer';
import objectDefinitionSystemDataRenderer from './FDSDataRenderers/ObjectDefinitionSystemDataRenderer';
import FoldersListSideBar from './FoldersListSidebar';
import {ModalAddFolder} from './ModalAddFolder';
import {ModalAddObjectDefinition} from './ModalAddObjectDefinition';
import {ModalDeleteObjectDefinition} from './ModalDeleteObjectDefinition';
import {ModalEditFolder} from './ModalEditFolder';
import {deleteObjectDefinition, getFolderActions} from './objectDefinitionUtil';

import './ViewObjectDefinitions.scss';
import {ModalDeleteFolder} from './ModalDeleteFolder';
import {ModalMoveObjectDefinition} from './ModalMoveObjectDefinition';

interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	objectFolderPermissionsURL: string;
	storages: LabelTypeObject[];
}

export type ViewObjectDefinitionsModals = {
	addFolder: boolean;
	addObjectDefinition: boolean;
	deleteFolder: boolean;
	deleteObjectDefinition: boolean;
	editFolder: boolean;
	moveObjectDefinition: boolean;
};

export interface DeletedObjectDefinition extends ObjectDefinition {
	hasObjectRelationship: boolean;
	objectEntriesCount: number;
}

export default function ViewObjectDefinitions({
	apiURL,
	baseResourceURL,
	id,
	items,
	objectFolderPermissionsURL,
	sorting,
	storages,
	url,
}: ViewObjectDefinitionsProps) {
	const initialValues: Folder = {
		actions: {},
		dateCreated: '',
		dateModified: '',
		externalReferenceCode: '',
		id: 0,
		label: {en_US: ''},
		name: '',
	};
	const [showModal, setShowModal] = useState<ViewObjectDefinitionsModals>({
		addFolder: false,
		addObjectDefinition: false,
		deleteFolder: false,
		deleteObjectDefinition: false,
		editFolder: false,
		moveObjectDefinition: false,
	});
	const [selectedFolder, setSelectedFolder] = useState<Partial<Folder>>(
		initialValues
	);
	const [foldersList, setFoldersList] = useState<Partial<Folder>[]>([
		initialValues,
	]);
	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();

	const [
		moveObjectDefinition,
		setMoveObjectDefinition,
	] = useState<ObjectDefinition | null>();

	const [loading, setLoading] = useState(true);

	function objectDefinitionLabelDataRenderer({
		itemData,
		value,
	}: fdsItem<ObjectDefinition>) {
		const handleEditDefinition = () => {
			window.location.href = formatActionURL(url, itemData.id);
		};

		return (
			<div className="table-list-title">
				<a href="#" onClick={handleEditDefinition}>
					{getLocalizableLabel(
						itemData.defaultLanguageId as Liferay.Language.Locale,
						value
					)}
				</a>
			</div>
		);
	}

	const getURL = () => {
		let url: string = '';

		if (selectedFolder.externalReferenceCode) {
			url = `/o/object-admin/v1.0/object-folders/by-external-reference-code/${selectedFolder.externalReferenceCode}/object-definitions`;
		}

		return url;
	};

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL: Liferay.FeatureFlags['LPS-148856'] ? getURL() : apiURL,
		creationMenu: {
			primaryItems: [
				{
					href: 'addObjectDefinition',
					id: 'addObjectDefinition',
					label: Liferay.Language.get('create-new-object'),
					target: 'event',
					type: 'item',
				},
			],
		},
		customDataRenderers: {
			objectDefinitionLabelDataRenderer,
			objectDefinitionModifiedDateDataRenderer,
			objectDefinitionStatusDataRenderer,
			objectDefinitionSystemDataRenderer,
		},
		emptyState: {
			description: Liferay.Language.get(
				'create-your-first-object-or-import-an-existing-one-to-start-working-with-object-folders'
			),
			image: '/states/empty_state.gif',
			title: Liferay.Language.get('no-objects-created-yet'),
		},
		id,
		itemsActions: items,
		namespace:
			'_com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_',
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: ObjectDefinition;
		}) {
			if (action.data.id === 'deleteObjectDefinition') {
				const getDeleteObjectDefinition = async () => {
					const url = createResourceURL(baseResourceURL, {
						objectDefinitionId: itemData.id,
						p_p_resource_id:
							'/object_definitions/get_object_definition_delete_info',
					}).href;

					const {
						hasObjectRelationship,
						objectEntriesCount,
					} = await API.fetchJSON<{
						hasObjectRelationship: boolean;
						objectEntriesCount: number;
					}>(url);

					if (itemData.status.code !== 0) {
						await deleteObjectDefinition(
							itemData.id,
							itemData.name
						);
						setTimeout(() => window.location.reload(), 1000);

						return;
					}

					setDeletedObjectDefinition({
						...itemData,
						hasObjectRelationship,
						objectEntriesCount,
					});

					setShowModal(
						(previousState: ViewObjectDefinitionsModals) => ({
							...previousState,
							deleteObjectDefinition: true,
						})
					);
				};

				getDeleteObjectDefinition();
			}

			if (action.data.id === 'moveObjectDefinition') {
				setMoveObjectDefinition(itemData);

				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					moveObjectDefinition: true,
				}));
			}
		},
		portletId:
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
		sidePanelId: 'none',
		sorting,
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
							contentRenderer:
								'objectDefinitionStatusDataRenderer',
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

	useEffect(() => {
		if (Liferay.FeatureFlags['LPS-148856']) {
			const makeFetch = async () => {
				API.getAllObjectFolders().then((response) => {
					setFoldersList(response);
					setSelectedFolder(response[0]);
					setLoading(false);
				});
			};

			makeFetch();
		}
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

	return (
		<>
			{Liferay.FeatureFlags['LPS-148856'] ? (
				<div className="lfr__object-web-view-object-definitions">
					{loading ? (
						<ClayLoadingIndicator
							displayType="secondary"
							size="sm"
						/>
					) : (
						<>
							<FoldersListSideBar
								foldersList={foldersList as Folder[]}
								selectedFolder={selectedFolder as Folder}
								setSelectedFolder={setSelectedFolder}
								setShowModal={setShowModal}
							/>
							<Card
								className="lfr__object-web-view-object-definitions-card"
								customHeader={
									<CardHeader
										externalReferenceCode={
											selectedFolder.externalReferenceCode
										}
										items={
											getFolderActions(
												selectedFolder.id ?? 0,
												objectFolderPermissionsURL,
												setShowModal,
												selectedFolder.actions
											) as IItem[]
										}
										label={selectedFolder.label}
									/>
								}
								viewMode="no-header-border"
							>
								<FrontendDataSet {...dataSetProps} />
							</Card>
						</>
					)}
				</div>
			) : (
				<FrontendDataSet {...dataSetProps} />
			)}

			{showModal.addObjectDefinition && (
				<ModalAddObjectDefinition
					apiURL={apiURL as string}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addObjectDefinition: false,
							})
						);
					}}
					objectFolderExternalReferenceCode={
						selectedFolder.externalReferenceCode
					}
					storages={storages}
				/>
			)}

			{showModal.deleteObjectDefinition && (
				<ModalDeleteObjectDefinition
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
					setDeletedObjectDefinition={setDeletedObjectDefinition}
				/>
			)}

			{showModal.addFolder && (
				<ModalAddFolder
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addFolder: false,
							})
						);
					}}
				/>
			)}

			{showModal.editFolder && (
				<ModalEditFolder
					externalReferenceCode={
						selectedFolder.externalReferenceCode as string
					}
					folderID={selectedFolder.id as number}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								editFolder: false,
							})
						);
					}}
					initialLabel={selectedFolder.label}
					name={selectedFolder.name}
				/>
			)}

			{showModal.deleteFolder && (
				<ModalDeleteFolder
					folder={selectedFolder as Folder}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								deleteFolder: false,
							})
						);
					}}
				/>
			)}

			{showModal.moveObjectDefinition && (
				<ModalMoveObjectDefinition
					foldersList={foldersList as Folder[]}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								moveObjectDefinition: false,
							})
						);
					}}
					objectDefinition={moveObjectDefinition as ObjectDefinition}
					selectedFolder={selectedFolder}
					setMoveObjectDefinition={setMoveObjectDefinition}
				/>
			)}
		</>
	);
}
