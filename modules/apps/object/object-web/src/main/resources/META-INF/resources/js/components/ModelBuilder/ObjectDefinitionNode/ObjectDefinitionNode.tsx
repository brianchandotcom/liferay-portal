/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import {
	Elements,
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
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';

import {formatActionURL} from '../../../utils/fds';
import {ModalAddObjectField} from '../../ObjectField/ModalAddObjectField';
import {ModalAddObjectRelationship} from '../../ObjectRelationship/ModalAddObjectRelationship';
import {ModalDeleteObjectDefinition} from '../../ViewObjectDefinitions/ModalDeleteObjectDefinition';
import {DeletedObjectDefinition} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import {
	getObjectDefinitionNodeActions,
	getUpdatedModelBuilderStructurePayload,
} from '../../ViewObjectDefinitions/objectDefinitionUtil';
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
			selectedObjectDefinitionNode,
			selectedObjectFolder,
		},
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

	const [showModal, setShowModal] = useState<Partial<ModelBuilderModals>>({
		addObjectRelationship: false,
		deleteObjectDefinition: false,
		editObjectDefinitionExternalReferenceCode: false,
	});

	const [
		objectRelationshipParameterRequired,
		setObjectRelationshipParameterRequired,
	] = useState(false);
	const [
		deletedObjectDefinition,
		setDeletedObjectDefinition,
	] = useState<DeletedObjectDefinition | null>();
	const [newExternalReferenceCode, setNewExternalReferenceCode] = useState(
		externalReferenceCode
	);

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

	const updateModelBuilderStructure = async (
		newObjectRelationshipId: number
	) => {
		const {nodes} = store.getState();

		const payload = await getUpdatedModelBuilderStructurePayload(
			selectedObjectFolder.name
		);

		dispatch({
			payload: {
				...payload,
				rightSidebarType: 'objectRelationshipDetails',
				selectedObjectRelationshipEdgeId: newObjectRelationshipId,
			},
			type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
		});

		dispatch({
			payload: {
				objectDefinitionNodes: nodes,
				selectedObjectRelationshipId: newObjectRelationshipId,
			},
			type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
		});
	};

	useEffect(() => {
		const makeFetch = async () => {
			if (selected) {
				const url = createResourceURL(baseResourceURL, {
					objectDefinitionId: id,
					p_p_resource_id:
						'/object_definitions/get_object_relationship_info',
				}).href;

				const {parameterRequired} = await API.fetchJSON<{
					parameterRequired: boolean;
				}>(url);

				setObjectRelationshipParameterRequired(parameterRequired);
			}
		};

		makeFetch();
	}, [baseResourceURL, id, selected]);

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
					handleSelectObjectDefinitionNode={
						handleSelectObjectDefinitionNode
					}
					isLinkedObjectDefinition={linkedObjectDefinition}
					setShowAllObjectFields={setShowAllObjectFields}
					setShowModal={setShowModal}
					showAllObjectFields={showAllObjectFields}
				/>

				<>
					{Object.keys(nodeHandleRefs).map((position, index) => (
						<Handle
							className="lfr-objects__model-builder-node-handle"
							id={id.toString()}
							key={index}
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

			{showModal.addObjectField && (
				<ModalAddObjectField
					baseResourceURL={baseResourceURL}
					creationLanguageId={defaultLanguageId}
					objectDefinitionExternalReferenceCode={
						externalReferenceCode
					}
					objectDefinitionName={name}
					onAfterSubmit={(newObjectField) => {
						const {edges, nodes} = store.getState();

						if (selectedObjectDefinitionNode) {
							dispatch({
								payload: {
									newObjectField,
									objectDefinitionExternalReferenceCode: externalReferenceCode,
									objectDefinitionNodes: nodes,
									objectRelationshipEdges: edges,
									selectedObjectDefinitionNode,
								},
								type: TYPES.ADD_OBJECT_FIELD,
							});

							openToast({
								message: Liferay.Language.get(
									'field-successfully-added'
								),
								type: 'success',
							});
							setShowModal((prevState) => ({
								...prevState,
								addObjectField: false,
							}));
							setShowAllObjectFields(true);
						}
					}}
					setVisibility={() =>
						setShowModal((prevState) => ({
							...prevState,
							addObjectField: false,
						}))
					}
				/>
			)}

			{showModal.addObjectRelationship && (
				<ModalAddObjectRelationship
					baseResourceURL={baseResourceURL}
					handleOnClose={() => {
						setShowModal(
							(previousState: Partial<ModelBuilderModals>) => ({
								...previousState,
								addObjectRelationship: false,
							})
						);
					}}
					objectDefinitionExternalReferenceCode1={
						externalReferenceCode
					}
					objectRelationshipParameterRequired={
						objectRelationshipParameterRequired
					}
					onAfterSubmit={(newObjectRelationshipId: number) =>
						updateModelBuilderStructure(newObjectRelationshipId)
					}
					reload={false}
				/>
			)}

			{showModal.deleteObjectDefinition && (
				<ModalDeleteObjectDefinition
					handleOnClose={() => {
						setShowModal(
							(previousState: Partial<ModelBuilderModals>) => ({
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
						}) as Elements<ObjectDefinitionNodeData>;

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
