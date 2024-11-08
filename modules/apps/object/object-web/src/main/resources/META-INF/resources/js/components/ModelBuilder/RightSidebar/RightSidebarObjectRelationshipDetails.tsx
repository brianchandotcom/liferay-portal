/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {API, openToast, stringUtils} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {isEdge, isNode} from 'react-flow-renderer';

import {defaultLanguageId} from '../../../utils/constants';
import {EditObjectRelationshipContent} from '../../ObjectRelationship/EditObjectRelationshipContent';
import {ModalDeleteObjectRelationship} from '../../ObjectRelationship/ModalDeleteObjectRelationship';
import {useObjectRelationshipForm} from '../../ObjectRelationship/useObjectRelationshipForm';
import {getUpdatedModelBuilderStructurePayload} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './RightSidebarObjectRelationshipDetails.scss';

import type {Edge, Elements, Node} from 'react-flow-renderer';

import type {ObjectRelationshipEdgeData} from '../types';

interface RightSidebarObjectRelationshipDetailsProps {
	objectRelationshipDeletionTypes: LabelValueObject[];
}

export function RightSidebarObjectRelationshipDetails({
	objectRelationshipDeletionTypes,
}: RightSidebarObjectRelationshipDetailsProps) {
	const [
		{
			baseResourceURL,
			elements,
			selectedObjectFolder,
			selectedObjectRelationship,
		},
		dispatch,
	] = useObjectFolderContext();
	const [loading, setLoading] = useState(false);
	const [
		objectRelationshipParameterRequired,
		setObjectRelationshipParameterRequired,
	] = useState(false);
	const [
		objectRelationshipRestContextPath,
		setObjectRelationshipRestContextPath,
	] = useState('');
	const [readOnly, setReadOnly] = useState(true);

	const [showModal, setShowModal] = useState<Partial<ModelBuilderModals>>({
		deleteObjectRelationship: false,
	});

	const {errors, handleChange, handleValidate, setValues, values} =
		useObjectRelationshipForm({
			initialValues: {
				id: 0,
				label: {},
				name: '',
			},
			onSubmit: () => {},
			parameterRequired: false,
		});

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedObjectRelationship) {
				setLoading(true);
				const selectedObjectRelationshipResponse =
					(await API.getObjectRelationship(
						selectedObjectRelationship.id
					)) as ObjectRelationship;

				setValues(selectedObjectRelationshipResponse);

				const url = createResourceURL(baseResourceURL, {
					objectDefinitionId:
						selectedObjectRelationshipResponse.objectDefinitionId1,
					p_p_resource_id:
						'/object_definitions/get_object_relationship_info',
				}).href;

				const {parameterRequired, restContextPath} =
					await API.fetchJSON<{
						parameterRequired: boolean;
						restContextPath: string;
					}>(url);

				setObjectRelationshipParameterRequired(parameterRequired);
				setObjectRelationshipRestContextPath(restContextPath ?? '');

				const nodeObjectDefinition1 = elements.find(
					(element) =>
						isNode(element) &&
						element.id ===
							selectedObjectRelationshipResponse.objectDefinitionId1.toString()
				);

				if (nodeObjectDefinition1) {
					const readOnly =
						!(
							nodeObjectDefinition1 as Node<ObjectDefinitionNodeData>
						).data?.hasObjectDefinitionUpdateResourcePermission ||
						selectedObjectRelationshipResponse.reverse;

					setReadOnly(readOnly);
				}
				setLoading(false);
			}
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedObjectRelationship?.id]);

	const onSubmit = async (
		objectRelationship: Partial<ObjectRelationship> = values
	) => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			try {
				await API.putObjectRelationship(objectRelationship);

				dispatch({
					payload: {
						updatedShowChangesSaved: true,
					},
					type: TYPES.SET_SHOW_CHANGES_SAVED,
				});
			}
			catch (error: unknown) {
				const {message} = error as Error;

				openToast({message, type: 'danger'});
			}

			if (!objectRelationship || !objectRelationship?.id) {
				return;
			}

			const updatedElements = elements.map((currentElement) => {
				if (isEdge(currentElement)) {
					return {
						...currentElement,
						data: (
							currentElement as Edge<ObjectRelationshipEdgeData[]>
						).data?.map((objectRelationshipEdgeData) => {
							if (
								objectRelationshipEdgeData.id ===
								objectRelationship?.id
							) {
								return {
									...objectRelationshipEdgeData,
									label: stringUtils.getLocalizableLabel(
										defaultLanguageId,
										objectRelationship.label,
										objectRelationship.name
									),
								};
							}

							return objectRelationshipEdgeData;
						}),
					};
				}

				return currentElement;
			}) as Elements<
				ObjectDefinitionNodeData | ObjectRelationshipEdgeData[]
			>;

			dispatch({
				payload: {
					newElements: updatedElements,
				},
				type: TYPES.SET_ELEMENTS,
			});
		}
	};

	const updateModelBuilderStructure = async () => {
		const payload = await getUpdatedModelBuilderStructurePayload(
			baseResourceURL,
			selectedObjectFolder.name
		);

		dispatch({
			payload: {...payload, dispatch, rightSidebarType: 'empty'},
			type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
		});
	};

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-object-relationship-title-container">
				<div className="lfr-objects__model-builder-right-sidebar-object-relationship-title">
					<span>
						{sub(
							Liferay.Language.get('x-details'),
							Liferay.Language.get('relationship')
						)}
					</span>
				</div>

				<div className="lfr-objects__model-builder-right-sidebar-object-relationship-title-buttons-container">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('delete-relationship')}
						className="lfr-objects__model-builder-right-sidebar-object-relationship-title-delete-button"
						displayType="secondary"
						onClick={() =>
							setShowModal({
								deleteObjectRelationship: true,
							})
						}
						symbol="trash"
						title={Liferay.Language.get('delete-relationship')}
					/>
				</div>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-object-relationship-content">
				{!loading && values.objectDefinitionExternalReferenceCode1 ? (
					<EditObjectRelationshipContent
						autoSave
						baseResourceURL={baseResourceURL}
						containerWrapper={ClayPanel}
						errors={errors}
						handleChange={handleChange}
						objectDefinitionExternalReferenceCode={
							values.objectDefinitionExternalReferenceCode1
						}
						objectRelationshipDeletionTypes={
							objectRelationshipDeletionTypes
						}
						onSubmit={onSubmit}
						parameterRequired={objectRelationshipParameterRequired}
						readOnly={readOnly}
						restContextPath={objectRelationshipRestContextPath}
						setValues={setValues}
						values={values}
					/>
				) : (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				)}
			</div>

			{showModal.deleteObjectRelationship && (
				<ModalDeleteObjectRelationship
					handleOnClose={() =>
						setShowModal({
							deleteObjectRelationship: false,
						})
					}
					objectRelationship={values as ObjectRelationship}
					onAfterSubmit={() => updateModelBuilderStructure()}
					reload={false}
				/>
			)}
		</>
	);
}
