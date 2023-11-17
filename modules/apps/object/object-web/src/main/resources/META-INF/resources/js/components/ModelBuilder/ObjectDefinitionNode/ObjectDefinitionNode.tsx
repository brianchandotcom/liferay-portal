/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useRef} from 'react';
import {Handle, NodeProps, Position, useStore} from 'react-flow-renderer';

import {getObjectDefinitionNodeActions} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import ObjectDefinitionNodeFooter from './ObjectDefinitionNodeFooter';
import ObjectDefinitionNodeHeader from './ObjectDefinitionNodeHeader';
import ObjectDefinitionNodeFields from './ObjectDefinitionNodeObjectFields';

import './NodeContainer.scss';

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
		id,
		label,
		linkedObjectDefinition,
		name,
		objectFields,
		selected,
		showAllObjectFields,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>) {
	const [
		{baseResourceURL, modelBuilderModals, objectDefinitionPermissionsURL},
		dispatch,
	] = useObjectFolderContext();

	const store = useStore();

	const nodeHandlePosition: {
		[key: string]: Position;
	} = {
		bottom: Position.Bottom,
		left: Position.Left,
		right: Position.Right,
		top: Position.Top,
	};

	const nodeHandleRefs: {
		[key: string]: React.RefObject<HTMLDivElement>;
	} = {
		bottom: useRef<HTMLDivElement>(null),
		left: useRef<HTMLDivElement>(null),
		right: useRef<HTMLDivElement>(null),
		top: useRef<HTMLDivElement>(null),
	};

	const displayNodeHandles = (display: boolean) => {
		for (const key in nodeHandleRefs) {
			const handleRef = nodeHandleRefs[key].current;

			if (handleRef) {
				handleRef.style.opacity = display ? '1' : '0';
			}
		}
	};

	const handleDeleteObjectDefinition = (
		deleteObjectDefinition: DeletedObjectDefinition
	) => {
		dispatch({
			payload: {
				newDeleteObjectDefinition: deleteObjectDefinition,
			},
			type: TYPES.SET_DELETE_OBJECT_DEFINITION,
		});
	};

	const handleSelectObjectDefinitionNode = () => {
		const {edges, nodes} = store.getState();

		dispatch({
			payload: {
				objectDefinitionNodes: nodes,
				objectRelationshipEdges: edges,
				selectedObjectDefinitionId: id.toString(),
			},
			type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE,
		});
	};

	const handleShowDeleteObjectDefinitionModal = () => {
		dispatch({
			payload: {
				modelBuilderModals: {
					...modelBuilderModals,
					deleteObjectDefinition: true,
				},
			},
			type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
		});
	};

	const handleShowEditObjectDefinitionExternalReferenceCodeModal = () => {
		dispatch({
			payload: {
				modelBuilderModals: {
					...modelBuilderModals,
					editObjectDefinitionExternalReferenceCode: true,
				},
			},
			type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
		});
	};

	const handleShowRedirectObjectDefinitionModal = () => {
		dispatch({
			payload: {
				modelBuilderModals: {
					...modelBuilderModals,
					redirectToEditObjectDefinitionDetails: true,
				},
			},
			type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
		});
	};

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
				onMouseEnter={() => {
					displayNodeHandles(true);
				}}
				onMouseLeave={() => {
					displayNodeHandles(false);
				}}
			>
				<ObjectDefinitionNodeHeader
					dropDownItems={getObjectDefinitionNodeActions({
						baseResourceURL,
						handleDeleteObjectDefinition,
						handleShowDeleteObjectDefinitionModal,
						handleShowEditObjectDefinitionExternalReferenceCodeModal,
						handleShowRedirectObjectDefinitionModal,
						hasObjectDefinitionDeleteResourcePermission,
						hasObjectDefinitionManagePermissionsResourcePermission,
						objectDefinitionId: id,
						objectDefinitionName: name,
						objectDefinitionPermissionsURL,
						status,
					})}
					handleSelectObjectDefinitionNode={
						handleSelectObjectDefinitionNode
					}
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
					selectedObjectDefinitionId={id}
					showAllObjectFields={showAllObjectFields}
				/>

				<ObjectDefinitionNodeFooter
					externalReferenceCode={externalReferenceCode}
					handleSelectObjectDefinitionNode={
						handleSelectObjectDefinitionNode
					}
					isLinkedObjectDefinition={linkedObjectDefinition}
					showAllObjectFields={showAllObjectFields}
				/>

				<>
					{Object.keys(nodeHandleRefs).map((position) => (
						<Handle
							className="lfr-objects__model-builder-node-handle"
							id={`${id}_${position}`}
							key={`${id}_${position}`}
							position={nodeHandlePosition[position]}
							ref={nodeHandleRefs[position]}
							style={{
								background: '#80ACFF',
								height: '12px',
								opacity: 0,
								[position]: '-18px',
								width: '12px',
							}}
							type="source"
						/>
					))}
				</>
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
			</div>
		</>
	);
}
