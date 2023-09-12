/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';
import {
	Handle,
	Node,
	NodeProps,
	Position,
	isNode,
	useStore,
} from 'react-flow-renderer';

import './DefinitionNode.scss';

import {
	API,
	ModalEditExternalReferenceCode,
} from '@liferay/object-js-components-web';

import {formatActionURL} from '../../../utils/fds';
import {ModalDeleteObjectDefinition} from '../../ViewObjectDefinitions/ModalDeleteObjectDefinition';
import {
	DeletedObjectDefinition,
	ViewObjectDefinitionsModals,
} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import {getDefinitionNodeActions} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectDefinitionNodeData} from '../types';
import NodeFields from './NodeFields';
import NodeFooter from './NodeFooter';
import NodeHeader from './NodeHeader';
import {RedirectModal} from './RedirectModal';

export function DefinitionNode({
	data: {
		defaultLanguageId,
		editObjectDefinitionURL,
		externalReferenceCode,
		hasObjectDefinitionDeleteResourcePermission,
		hasObjectDefinitionManagePermissionsResourcePermission,
		id,
		isLinkedNode,
		label,
		name,
		nodeSelected,
		objectDefinitionPermissionsURL,
		objectFields,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>) {
	const [showAllFields, setShowAllFields] = useState<boolean>(false);
	const [{elements}, dispatch] = useFolderContext();
	const store = useStore();

	const [showModal, setShowModal] = useState<
		Partial<ViewObjectDefinitionsModals>
	>({
		deleteObjectDefinition: false,
		editERC: false,
	});
	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();

	const [newExternalReferenceCode, setNewExternalReferenceCode] = useState(
		externalReferenceCode
	);

	const [{baseResourceURL}] = useFolderContext();

	const handleShowDeleteModal = () => {
		setShowModal({
			deleteObjectDefinition: true,
		});
	};

	const handleShowEditERCModal = () => {
		setShowModal({
			editERC: true,
		});
	};

	const handleShowRedirectModal = () => {
		setShowModal({
			redirectEditObjectDefinition: true,
		});
	};

	const viewDetailsURL = formatActionURL(editObjectDefinitionURL, id);

	return (
		<>
			<div
				className={classNames(
					'lfr-objects__model-builder-node-container',
					{
						'lfr-objects__model-builder-node-container--selected': nodeSelected,
					}
				)}
				onClick={() => {
					const {edges, nodes} = store.getState();

					dispatch({
						payload: {
							edges,
							nodes,
							selectedObjectDefinitionId: id!.toString(),
						},
						type: TYPES.SET_SELECTED_NODE,
					});
				}}
			>
				<NodeHeader
					dropDownItems={getDefinitionNodeActions({
						baseResourceURL,
						handleShowDeleteModal,
						handleShowEditERCModal,
						handleShowRedirectModal,
						hasObjectDefinitionDeleteResourcePermission,
						hasObjectDefinitionManagePermissionsResourcePermission,
						objectDefinitionId: id,
						objectDefinitionName: name,
						objectDefinitionPermissionsURL,
						setDeletedObjectDefinition,
						status,
					})}
					isLinkedNode={isLinkedNode}
					objectDefinitionLabel={label}
					status={status!}
					system={system}
				/>

				<NodeFields
					defaultLanguageId={defaultLanguageId}
					objectFields={objectFields}
					showAll={showAllFields}
				/>

				<NodeFooter
					setShowAllFields={setShowAllFields}
					showAllFields={showAllFields}
				/>

				<Handle
					className="lfr-objects__model-builder-node-handle"
					hidden
					id={name}
					position={Position.Left}
					style={{
						background: '#80ACFF',
						height: '12px',
						left: '-30px',
						width: '12px',
					}}
					type="source"
				/>
			</div>

			{showModal.deleteObjectDefinition && (
				<ModalDeleteObjectDefinition
					handleOnClose={() => {
						setShowModal({
							deleteObjectDefinition: false,
						});
					}}
					objectDefinition={
						deletedObjectDefinition as DeletedObjectDefinition
					}
					setDeletedObjectDefinition={setDeletedObjectDefinition}
				/>
			)}

			{showModal.editERC && (
				<ModalEditExternalReferenceCode
					externalReferenceCode={newExternalReferenceCode as string}
					handleOnClose={() => {
						setShowModal(
							(
								previousState: Partial<
									ViewObjectDefinitionsModals
								>
							) => ({
								...previousState,
								editERC: false,
							})
						);
					}}
					helpMessage={Liferay.Language.get(
						'unique-key-for-referencing-the-object-definition'
					)}
					onExternalReferenceCodeChange={(
						externalReferenceCode: string
					) => {
						const updatedElements = elements.map((element) => {
							if (
								isNode(element) &&
								(element as Node<ObjectDefinitionNodeData>)
									.id === id?.toString()
							) {
								return {
									...element,
									data: {
										...element.data,
										externalReferenceCode,
									},
								};
							}

							return element;
						});

						dispatch({
							payload: {
								newElements: updatedElements,
							},
							type: TYPES.SET_ELEMENTS,
						});
					}}
					onGetEntity={() => API.getObjectDefinitionById(id)}
					saveURL={`/o/object-admin/v1.0/object-definitions/${id}`}
					setExternalReferenceCode={setNewExternalReferenceCode}
				/>
			)}

			{showModal.redirectEditObjectDefinition && (
				<RedirectModal
					handleOnClose={() => {
						setShowModal({
							redirectEditObjectDefinition: false,
						});
					}}
					viewDetailsURL={viewDetailsURL}
				/>
			)}
		</>
	);
}
