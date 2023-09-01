/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
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
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';

import {formatActionURL} from '../../../utils/fds';
import {ModalAddObjectField} from '../../ObjectField/ModalAddObjectField';
import {ModalAddObjectRelationship} from '../../ObjectRelationship/ModalAddObjectRelationship';
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
			selectedObjectDefinitionNode,
		},
		dispatch,
	] = useObjectFolderContext();
	const store = useStore();

	const nodeHandleBottomRef = useRef<HTMLDivElement>(null);
	const nodeHandleLeftRef = useRef<HTMLDivElement>(null);
	const nodeHandleRightRef = useRef<HTMLDivElement>(null);
	const nodeHandleTopRef = useRef<HTMLDivElement>(null);

	const displayNodeHandles = (display: boolean) => {
		if (
			nodeHandleBottomRef.current &&
			nodeHandleLeftRef.current &&
			nodeHandleRightRef.current &&
			nodeHandleTopRef.current
		) {
			if (display) {
				nodeHandleBottomRef.current.style.opacity = '1';
				nodeHandleLeftRef.current.style.opacity = '1';
				nodeHandleRightRef.current.style.opacity = '1';
				nodeHandleTopRef.current.style.opacity = '1';
			}
			else {
				nodeHandleBottomRef.current.style.opacity = '0';
				nodeHandleLeftRef.current.style.opacity = '0';
				nodeHandleRightRef.current.style.opacity = '0';
				nodeHandleTopRef.current.style.opacity = '0';
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

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedObjectDefinitionNode) {
				const url = createResourceURL(baseResourceURL, {
					objectDefinitionId: selectedObjectDefinitionNode.id,
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
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedObjectDefinitionNode]);

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
					setShowModal={setShowModal}
					showAllObjectFields={showAllObjectFields}
				/>

				<>
					<Handle
						className="lfr-objects__model-builder-node-handle"
						id={id.toString()}
						position={Position.Bottom}
						ref={nodeHandleBottomRef}
						style={{
							background: '#80ACFF',
							bottom: '-18px',
							height: '12px',
							width: '12px',
						}}
						type="source"
					/>
					<Handle
						className="lfr-objects__model-builder-node-handle"
						id={id.toString()}
						position={Position.Left}
						ref={nodeHandleLeftRef}
						style={{
							background: '#80ACFF',
							height: '12px',
							left: '-18px',
							width: '12px',
						}}
						type="source"
					/>

					<Handle
						className="lfr-objects__model-builder-node-handle"
						id={id.toString()}
						position={Position.Right}
						ref={nodeHandleRightRef}
						style={{
							background: '#80ACFF',
							height: '12px',
							right: '-18px',
							width: '12px',
						}}
						type="source"
					/>

					<Handle
						className="lfr-objects__model-builder-node-handle"
						id={id.toString()}
						position={Position.Top}
						ref={nodeHandleTopRef}
						style={{
							background: '#80ACFF',
							height: '12px',
							top: ' -18px',
							width: '12px',
						}}
						type="source"
					/>
				</>

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

			{showModal.addObjectField && (
				<ModalAddObjectField
					creationLanguageId={defaultLanguageId}
					objectDefinitionExternalReferenceCode={
						externalReferenceCode
					}
					objectFieldTypes={[]}
					objectName={name}
					onAfterSubmit={(newObjectField) => {
						const {edges, nodes} = store.getState();

						dispatch({
							payload: {
								edges,
								newObjectField,
								nodes,
								objectDefinitionExternalReferenceCode: externalReferenceCode,
							},
							type: TYPES.ADD_NEW_OBJECT_FIELD,
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
					objectDefinitionExternalReferenceCode={
						selectedObjectDefinitionNode?.data
							?.externalReferenceCode as string
					}
					objectRelationshipParameterRequired={
						objectRelationshipParameterRequired
					}
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
