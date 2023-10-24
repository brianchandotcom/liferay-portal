/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Edge, Elements, Node, isEdge, isNode} from 'react-flow-renderer';

import {TYPES} from '../ModelBuilderContext/typesEnum';

import './RightSidebarObjectRelationshipDetails.scss';

import {
	API,
	Input,
	SingleSelect,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';

import {defaultLanguageId} from '../../../utils/constants';
import {firstLetterUppercase} from '../../../utils/string';
import {ModalDeleteObjectRelationship} from '../../ObjectRelationship/ModalDeleteObjectRelationship';
import {
	OBJECT_RELATIONSHIP_TYPES,
	useObjectRelationshipForm,
} from '../../ObjectRelationship/ObjectRelationshipFormBase';
import SelectObjectRelationship from '../../ObjectRelationship/SelectObjectRelationship';
import {getUpdatedModelBuilderStructurePayload} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {ObjectRelationshipEdgeData} from '../types';

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
	const [objectDefinition1, setObjectDefinition1] = useState<
		Partial<ObjectDefinition>
	>();
	const [objectDefinition2, setObjectDefinition2] = useState<
		Partial<ObjectDefinition>
	>();
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

	const {
		errors,
		handleValidate,
		setValues,
		values,
	} = useObjectRelationshipForm({
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
				const selectedObjectRelationshipResponse = (await API.getObjectRelationship(
					selectedObjectRelationship.data!.objectRelationshipId
				)) as ObjectRelationship;

				setValues(selectedObjectRelationshipResponse);

				const objectDefinition1 = await API.getObjectDefinitionById(
					selectedObjectRelationshipResponse.objectDefinitionId1
				);

				const objectDefinition2 = await API.getObjectDefinitionById(
					selectedObjectRelationshipResponse.objectDefinitionId2
				);

				setObjectDefinition1(objectDefinition1);

				setObjectDefinition2(objectDefinition2);

				const url = createResourceURL(baseResourceURL, {
					objectDefinitionId: objectDefinition1.id,
					p_p_resource_id:
						'/object_definitions/get_object_relationship_info',
				}).href;

				const {
					parameterRequired,
					restContextPath,
				} = await API.fetchJSON<{
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
						!(nodeObjectDefinition1 as Node<
							ObjectDefinitionNodeData
						>).data?.hasObjectDefinitionUpdateResourcePermission ||
						selectedObjectRelationshipResponse.reverse;

					setReadOnly(readOnly);
				}
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onSubmit = async (
		editedObjectRelationship?: Partial<ObjectRelationship>
	) => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			const objectRelationship = editedObjectRelationship ?? values;

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

			let newObjectRelationship = {};

			const isSelfObjectRelationship =
				objectRelationship.objectDefinitionId1 ===
				objectRelationship.objectDefinitionId2;

			const updatedElements = elements.map((element) => {
				if (isEdge(element)) {
					const edgeData = (element as Edge<
						ObjectRelationshipEdgeData
					>).data;

					const objectRelationshipId = edgeData?.objectRelationshipId;
					const selfObjectRelationships =
						edgeData?.selfObjectRelationships;

					const newSelfObjectRelationships = selfObjectRelationships?.map(
						(selfObjectRelationship) => {
							if (
								objectRelationship?.id ===
								selfObjectRelationship.id
							) {
								return {
									...selfObjectRelationship,
									label: objectRelationship.label,
								};
							}

							return selfObjectRelationship;
						}
					);

					if (objectRelationshipId === objectRelationship?.id) {
						newObjectRelationship = {
							...edgeData,
							deletionType: objectRelationship.deletionType,
							label:
								isSelfObjectRelationship &&
								selfObjectRelationships &&
								selfObjectRelationships.length > 1
									? selfObjectRelationships.length.toString()
									: getLocalizableLabel(
											defaultLanguageId,
											objectRelationship.label,
											objectRelationship.name
									  ),
							selfObjectRelationships: newSelfObjectRelationships,
						};
					}
					else {
						newObjectRelationship = {
							...edgeData,
							selfObjectRelationships: newSelfObjectRelationships,
						};
					}

					return {
						...element,
						data: newObjectRelationship,
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
		}
	};

	const updateModelBuilderStructure = async () => {
		const payload = await getUpdatedModelBuilderStructurePayload(
			selectedObjectFolder.name
		);

		dispatch({
			payload: {...payload, rightSidebarType: 'empty'},
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
				<InputLocalized
					disableFlag={readOnly}
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onBlur={(event) => {
						event.stopPropagation();

						onSubmit();
					}}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<Input
					disabled
					label={Liferay.Language.get('name')}
					required
					value={values.name}
				/>

				<Input
					disabled
					label={Liferay.Language.get('type')}
					required
					value={
						OBJECT_RELATIONSHIP_TYPES.find(
							({value}) => value === values.type
						)?.label
					}
				/>

				<Input
					disabled
					label={
						values.type === 'manyToMany'
							? Liferay.Language.get('many-records-of')
							: Liferay.Language.get('one-record-of')
					}
					required
					value={objectDefinition1?.name}
				/>

				<Input
					disabled
					label={Liferay.Language.get('many-records-of')}
					required
					value={objectDefinition2?.name}
				/>

				<SingleSelect
					disabled={readOnly}
					label={Liferay.Language.get('deletion-type')}
					onBlur={(event) => {
						event.stopPropagation();

						onSubmit();
					}}
					onChange={(deletionType) =>
						setValues({deletionType: deletionType.value})
					}
					options={objectRelationshipDeletionTypes}
					required
					value={
						values.deletionType &&
						firstLetterUppercase(values.deletionType)
					}
				/>

				{objectRelationshipParameterRequired &&
					selectedObjectRelationship?.data?.type === 'oneToMany' && (
						<>
							<Input
								label={Liferay.Language.get('api-endpoint')}
								readOnly
								value={objectRelationshipRestContextPath}
							/>

							<SelectObjectRelationship
								error={errors.parameterObjectFieldName}
								objectDefinitionExternalReferenceCode1={
									values.objectDefinitionExternalReferenceCode2 as string
								}
								onChange={(parameterObjectFieldName) => {
									setValues({
										parameterObjectFieldName,
									});

									onSubmit({
										...values,
										parameterObjectFieldName,
									});
								}}
								value={values.parameterObjectFieldName}
							/>
						</>
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
