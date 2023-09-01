/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	FormError,
	Input,
	REQUIRED_MSG,
	SingleSelect,
	filterArrayByQuery,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import CurrentObjectDefinition from './CurrentObjectDefinition';
import SelectObjectDefinition from './SelectObjectDefinition';

interface ObjectRelationshipFormBaseProps {
	baseResourceURL: string;
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	objectDefinitionExternalReferenceCode: string;
	readonly?: boolean;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

interface UseObjectRelationshipFormProps {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}

export enum ObjectRelationshipType {
	MANY_TO_MANY = 'manyToMany',
	ONE_TO_MANY = 'oneToMany',
	ONE_TO_ONE = 'oneToOne',
}

type ObjectRelationshipTypeInfo = {
	description: string;
	label: string;
	objectInputLabel1: string;
	objectInputLabel2: string;
	value: ObjectRelationshipType;
};

const MANY_TO_MANY = {
	description: Liferay.Language.get(
		"multiple-object's-entries-can-interact-with-many-others-object's-entries"
	),
	label: Liferay.Language.get('many-to-many'),
	objectInputLabel1: Liferay.Language.get('many-records-of'),
	objectInputLabel2: Liferay.Language.get('many-records-of'),
	value: ObjectRelationshipType.MANY_TO_MANY,
};
const ONE_TO_MANY = {
	description: Liferay.Language.get(
		"one-object's-entry-interacts-with-many-others-object's-entries"
	),
	label: Liferay.Language.get('one-to-many'),
	objectInputLabel1: Liferay.Language.get('one-record-of'),
	objectInputLabel2: Liferay.Language.get('many-records-of'),
	value: ObjectRelationshipType.ONE_TO_MANY,
};
const ONE_TO_ONE = {
	description: Liferay.Language.get(
		"one-object's-entry-interacts-only-with-one-other-object's-entry"
	),
	label: Liferay.Language.get('one-to-one'),
	objectInputLabel1: Liferay.Language.get('one-record-of'),
	objectInputLabel2: Liferay.Language.get('one-record-of'),
	value: ObjectRelationshipType.ONE_TO_ONE,
};

const OBJECT_RELATIONSHIP_TYPES = [MANY_TO_MANY, ONE_TO_MANY, ONE_TO_ONE];

export function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: UseObjectRelationshipFormProps) {
	const validate = (relationship: Partial<ObjectRelationship>) => {
		const errors: FormError<ObjectRelationship> = {};

		const label = relationship.label?.[defaultLanguageId];

		if (invalidateRequired(label)) {
			errors.label = REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.name ?? label)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.type)) {
			errors.type = REQUIRED_MSG;
		}

		if (!relationship.objectDefinitionId1) {
			errors.objectDefinitionId1 = REQUIRED_MSG;
		}

		if (!relationship.objectDefinitionId2) {
			errors.objectDefinitionId2 = REQUIRED_MSG;
		}

		if (
			parameterRequired &&
			relationship.type === ObjectRelationshipType.ONE_TO_MANY &&
			!relationship.parameterObjectFieldName
		) {
			errors.parameterObjectFieldName = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}

export function ObjectRelationshipFormBase({
	baseResourceURL,
	errors,
	handleChange,
	objectDefinitionExternalReferenceCode,
	readonly,
	setValues,
	values,
}: ObjectRelationshipFormBaseProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();
	const [currentObjectDefinition, setCurrentObjectDefinition] = useState<
		Partial<ObjectDefinition>
	>();
	const [objectDefinition1, setObjectDefinition1] = useState<
		Partial<ObjectDefinition>
	>();
	const [objectDefinition2, setObjectDefinition2] = useState<
		Partial<ObjectDefinition>
	>();
	const [objectDefinitions, setObjectDefinitions] = useState<
		Partial<ObjectDefinition>[]
	>([]);
	const [objectRelationshipTypes, setObjectRelationshipTypes] = useState<
		ObjectRelationshipTypeInfo[] | undefined
	>();
	const [query, setQuery] = useState<string>('');
	const [reverseOrder, setReverseOrder] = useState<boolean>(false);

	const filteredRelationships = useMemo(() => {
		return filterArrayByQuery({
			array: objectDefinitions,
			creationLanguageId,
			query,
			str: 'label',
		});
	}, [creationLanguageId, objectDefinitions, query]);

	const switchObjects = () => {
		const previousObjectDefinition1 = {
			...objectDefinition1,
		};

		setObjectDefinition1(objectDefinition2);

		setObjectDefinition2(previousObjectDefinition1);
	};

	const handleHideReverseButton = () => {
		return (
			values.type !== 'oneToMany' ||
			!!readonly ||
			currentObjectDefinition?.externalReferenceCode ===
				'L_POSTAL_ADDRESS'
		);
	};

	const handleObjectRelationshipTypes = async (
		objectDefinition: Partial<ObjectDefinition> | undefined
	) => {
		const url = createResourceURL(baseResourceURL, {
			objectDefinitionId: objectDefinition?.id,
			p_p_resource_id: '/object_definitions/get_object_relationship_info',
		}).href;

		const {objectRelationshipTypes} = await API.fetchJSON<{
			objectRelationshipTypes: any;
		}>(url);

		const types = OBJECT_RELATIONSHIP_TYPES.filter((relationshipType) =>
			objectRelationshipTypes?.includes(relationshipType.value)
		);

		if (
			!objectRelationshipTypes.includes(values?.type) &&
			values.objectDefinitionExternalReferenceCode2
		) {
			setValues({type: objectRelationshipTypes[0]});
		}

		setObjectRelationshipTypes(types);
	};

	const handleReverseOrder = () => {
		setValues({
			objectDefinitionExternalReferenceCode1:
				objectDefinition2?.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition1?.externalReferenceCode,
			objectDefinitionId1: objectDefinition2?.id,
			objectDefinitionId2: objectDefinition1?.id,
			objectDefinitionName2: objectDefinition1?.name,
		});

		switchObjects();

		setReverseOrder(!reverseOrder);
	};

	useEffect(() => {
		if (objectDefinition1) {
			handleObjectRelationshipTypes(objectDefinition1);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectDefinitionExternalReferenceCode1]);

	useEffect(() => {
		const fetchObjectDefinition = async () => {
			const object = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode as string
			);

			setCurrentObjectDefinition(object);
			setCreationLanguageId(object.defaultLanguageId);
			setObjectDefinition1(object);
			setValues({
				objectDefinitionExternalReferenceCode1:
					object.externalReferenceCode,
				objectDefinitionId1: object.id,
			});

			handleObjectRelationshipTypes(object);
		};

		fetchObjectDefinition();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitionExternalReferenceCode]);

	useEffect(() => {
		const fetchObjectDefinitions = async () => {
			const items = await API.getAllObjectDefinitions();

			const objectDefinition = items.find(
				({externalReferenceCode}) =>
					objectDefinitionExternalReferenceCode ===
					externalReferenceCode
			)!;

			const objectDefinitions = items.filter(
				({modifiable, parameterRequired, storageType, system}) => {
					if (Liferay.FeatureFlags['LPS-167253']) {
						return (
							(objectDefinition.modifiable || modifiable) &&
							(!Liferay.FeatureFlags['LPS-135430'] ||
								storageType === 'default') &&
							!parameterRequired
						);
					}

					return (
						(!objectDefinition.system || !system) &&
						(!Liferay.FeatureFlags['LPS-135430'] ||
							storageType === 'default') &&
						!parameterRequired
					);
				}
			);

			setCreationLanguageId(objectDefinition.defaultLanguageId);

			setObjectDefinitions(objectDefinitions);
		};

		if (readonly) {
			setObjectDefinitions([
				{
					externalReferenceCode: values.objectDefinitionExternalReferenceCode2 as string,
					id: values.objectDefinitionId2 as number,
					label: values.label as LocalizedValue<string>,
					name: values.objectDefinitionName2 as string,
					system: false,
				},
			]);
		}
		else {
			fetchObjectDefinitions();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitionExternalReferenceCode, readonly]);

	return (
		<>
			<Input
				disabled={readonly}
				error={errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={handleChange}
				required
				value={values.name}
			/>

			<SingleSelect
				disabled={readonly}
				error={errors.type}
				label={Liferay.Language.get('type')}
				onChange={({value}) => {
					if (
						(value === 'manyToMany' || value === 'oneToOne') &&
						currentObjectDefinition?.id !== objectDefinition1?.id
					) {
						setValues({
							objectDefinitionExternalReferenceCode1:
								objectDefinition2?.externalReferenceCode,
							objectDefinitionExternalReferenceCode2:
								objectDefinition1?.externalReferenceCode,
							objectDefinitionId1: objectDefinition2?.id,
							objectDefinitionId2: objectDefinition1?.id,
							objectDefinitionName2: objectDefinition1?.name,
							type: value,
						});

						switchObjects();

						setReverseOrder(!reverseOrder);
					}
					else {
						setValues({type: value});
					}
				}}
				options={objectRelationshipTypes ?? [ONE_TO_MANY]}
				required
				value={
					OBJECT_RELATIONSHIP_TYPES.find(
						({value}) => value === values.type
					)?.label
				}
			/>

			{values.type &&
				(!reverseOrder ? (
					<>
						<CurrentObjectDefinition
							currentObjectDefinition={currentObjectDefinition}
							disableReverseButton={
								!values.objectDefinitionExternalReferenceCode2
							}
							disabled={readonly}
							error={errors.objectDefinitionId1}
							handleReverseOrder={handleReverseOrder}
							hideReverseButton={handleHideReverseButton()}
							label={
								OBJECT_RELATIONSHIP_TYPES.find(
									({value}) => value === values.type
								)?.objectInputLabel1
							}
						/>
						<SelectObjectDefinition
							creationLanguageId={
								creationLanguageId as Liferay.Language.Locale
							}
							disabled={readonly}
							error={errors.objectDefinitionId2}
							filteredRelationships={filteredRelationships}
							label={
								OBJECT_RELATIONSHIP_TYPES.find(
									({value}) => value === values.type
								)?.objectInputLabel2
							}
							objectDefinition={objectDefinition2}
							objectDefinitionExternalReferenceCode={
								values.objectDefinitionExternalReferenceCode2
							}
							query={query}
							readOnly={readonly}
							reverseOrder={reverseOrder}
							setObjectDefinition={setObjectDefinition2}
							setQuery={setQuery}
							setValues={setValues}
						/>
					</>
				) : (
					<>
						<SelectObjectDefinition
							creationLanguageId={
								creationLanguageId as Liferay.Language.Locale
							}
							disabled={readonly}
							error={errors.objectDefinitionId1}
							filteredRelationships={filteredRelationships}
							label={
								OBJECT_RELATIONSHIP_TYPES.find(
									({value}) => value === values.type
								)?.objectInputLabel1
							}
							objectDefinition={objectDefinition1}
							objectDefinitionExternalReferenceCode={
								values.objectDefinitionExternalReferenceCode1
							}
							query={query}
							readOnly={readonly}
							reverseOrder={reverseOrder}
							setObjectDefinition={setObjectDefinition1}
							setQuery={setQuery}
							setValues={setValues}
						/>

						<CurrentObjectDefinition
							currentObjectDefinition={currentObjectDefinition}
							disableReverseButton={
								!values.objectDefinitionExternalReferenceCode2
							}
							error={errors.objectDefinitionId2}
							handleReverseOrder={handleReverseOrder}
							hideReverseButton={handleHideReverseButton()}
							label={
								OBJECT_RELATIONSHIP_TYPES.find(
									({value}) => value === values.type
								)?.objectInputLabel2
							}
						/>
					</>
				))}
		</>
	);
}
