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

import './NodeContainer.scss';

import {
	API,
	ModalEditExternalReferenceCode,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';

import {formatActionURL} from '../../../utils/fds';
import {ModalDeleteObjectDefinition} from '../../ViewObjectDefinitions/ModalDeleteObjectDefinition';
import {DeletedObjectDefinition} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import {getObjectDefinitionNodeActions} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import ObjectDefinitionNodeFooter from './ObjectDefinitionNodeFooter';
import ObjectDefinitionNodeHeader from './ObjectDefinitionNodeHeader';
import ObjectDefinitionNodeFields from './ObjectDefinitionNodeObjectFields';
import {RedirectToEditObjectDetailsModal} from './RedirectToEditObjectDetailsModal';

const selfRelationshipHandleStyle = {
	background: 'transparent',
	border: '2px transparent',
	borderRadius: '50%',
};

export function ObjectDefinitionNode({
	data: {
		defaultLanguageId,
		externalReferenceCode,
		hasObjectDefinitionDeleteResourcePermission,
		hasObjectDefinitionManagePermissionsResourcePermission,
		hasSelfObjectRelationships,
		id,
		label,
		linkedObjectDefinition,
		name,
		objectFields,
		selected,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>) {
	const [showAllObjectFields, setShowAllObjectFields] = useState<boolean>(
		false
	);
	const [
		{
			baseResourceURL,
			editObjectDefinitionURL,
			elements,
			objectDefinitionPermissionsURL,
		},
		dispatch,
	] = useObjectFolderContext();
	const store = useStore();

	const [showModal, setShowModal] = useState<Partial<ModelBuilderModals>>({
		deleteObjectDefinition: false,
		editObjectDefinitionExternalReferenceCode: false,
	});
	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();

	const [newExternalReferenceCode, setNewExternalReferenceCode] = useState(
		externalReferenceCode
	);

	const handleShowDeleteObjectDefinitionModal = () => {
		setShowModal({
			deleteObjectDefinition: true,
		});
	};

	const handleShowEditObjectDefinitionExternalReferenceCodeModal = () => {
		setShowModal({
			editObjectDefinitionExternalReferenceCode: true,
		});
	};

	const handleShowRedirectObjectDefinitionModal = () => {
		setShowModal({
			redirectToEditObjectDefinitionDetails: true,
		});
	};

	const viewObjectDetailsURL = formatActionURL(editObjectDefinitionURL, id);

	return (
		<>
			<div
				className={classNames(
					'lfr-objects__model-builder-node-container',
					{
						'lfr-objects__model-builder-node-container--link': linkedObjectDefinition,
						'lfr-objects__model-builder-node-container--selected': selected,
					}
				)}
				onClick={() => {
					const {edges, nodes} = store.getState();

					dispatch({
						payload: {
							edges,
							nodes,
							selectedObjectDefinitionId: id.toString(),
						},
						type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE,
					});
				}}
			>
				<ObjectDefinitionNodeHeader
					dropDownItems={getObjectDefinitionNodeActions({
						baseResourceURL,
						handleShowDeleteObjectDefinitionModal,
						handleShowEditObjectDefinitionExternalReferenceCodeModal,
						handleShowRedirectObjectDefinitionModal,
						hasObjectDefinitionDeleteResourcePermission,
						hasObjectDefinitionManagePermissionsResourcePermission,
						objectDefinitionId: id,
						objectDefinitionName: name,
						objectDefinitionPermissionsURL,
						setDeletedObjectDefinition,
						status,
					})}
					isLinkedObjectDefinition={linkedObjectDefinition}
					objectDefinitionLabel={getLocalizableLabel(
						defaultLanguageId,
						label,
						name
					)}
					status={status!}
					system={system}
				/>

				<ObjectDefinitionNodeFields
					defaultLanguageId={defaultLanguageId}
					objectFields={objectFields}
					showAllObjectFields={showAllObjectFields}
				/>

				<ObjectDefinitionNodeFooter
					isLinkedObjectDefinition={linkedObjectDefinition}
					setShowAllObjectFields={setShowAllObjectFields}
					showAllObjectFields={showAllObjectFields}
				/>

				<Handle
					className="lfr-objects__model-builder-node-handle"
					hidden
					id={id.toString()}
					position={Position.Left}
					style={{
						background: '#80ACFF',
						height: '12px',
						left: '-30px',
						width: '12px',
					}}
					type="source"
				/>

				{hasSelfObjectRelationships && (
					<>
						<Handle
							className="lfr-objects__model-builder-node-handle"
							id="fixedLeftHandle"
							position={Position.Left}
							style={{
								...selfRelationshipHandleStyle,
								left: '10px',
								top: '50%',
							}}
							type="source"
						/>

						<Handle
							className="lfr-objects__model-builder-node-handle"
							id="fixedRightHandle"
							position={Position.Right}
							style={{
								...selfRelationshipHandleStyle,
								right: '4px',
								top: '50%',
							}}
							type="target"
						/>
					</>
				)}
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

			{showModal.editObjectDefinitionExternalReferenceCode && (
				<ModalEditExternalReferenceCode
					externalReferenceCode={newExternalReferenceCode as string}
					handleOnClose={() => {
						setShowModal(
							(previousState: Partial<ModelBuilderModals>) => ({
								...previousState,
								editObjectDefinitionExternalReferenceCode: false,
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

			{showModal.redirectToEditObjectDefinitionDetails && (
				<RedirectToEditObjectDetailsModal
					handleOnClose={() => {
						setShowModal({
							redirectToEditObjectDefinitionDetails: false,
						});
					}}
					viewObjectDetailsURL={viewObjectDetailsURL}
				/>
			)}
		</>
	);
}
