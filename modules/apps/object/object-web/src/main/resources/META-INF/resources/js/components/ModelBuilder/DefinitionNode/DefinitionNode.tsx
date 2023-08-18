/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';
import {Handle, NodeProps, Position, useStore} from 'react-flow-renderer';

import './DefinitionNode.scss';
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
		hasObjectDefinitionDeleteResourcePermission,
		hasObjectDefinitionManagePermissionsResourcePermission,
		id,
		isLinkedNode,
		label,
		name,
		nodeSelected,
		objectDefinitionId,
		objectDefinitionPermissionsURL,
		objectFields,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>) {
	const [showAllFields, setShowAllFields] = useState<boolean>(false);
	const [_, dispatch] = useFolderContext();
	const store = useStore();

	const [showModal, setShowModal] = useState<
		Partial<ViewObjectDefinitionsModals>
	>({
		deleteObjectDefinition: false,
	});
	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();

	const [{baseResourceURL}] = useFolderContext();

	const handleShowDeleteDefinitionModal = () => {
		setShowModal({
			deleteObjectDefinition: true,
		});
	};

	const handleShowRedirectModal = () => {
		setShowModal({
			redirectEditObjectDefinition: true,
		});
	};

	const viewDetailsUrl = formatActionURL(
		editObjectDefinitionURL,
		objectDefinitionId
	);

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
					dropDownItems={getDefinitionNodeActions(
						baseResourceURL,
						objectDefinitionId,
						name,
						hasObjectDefinitionDeleteResourcePermission,
						hasObjectDefinitionManagePermissionsResourcePermission,
						objectDefinitionPermissionsURL,
						status,
						setDeletedObjectDefinition,
						handleShowDeleteDefinitionModal,
						handleShowRedirectModal
					)}
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

			{showModal.redirectEditObjectDefinition && (
				<RedirectModal
					handleOnClose={() => {
						setShowModal({
							redirectEditObjectDefinition: false,
						});
					}}
					viewDetailsUrl={viewDetailsUrl}
				/>
			)}
		</>
	);
}
