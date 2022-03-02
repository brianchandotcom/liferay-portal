/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import useForm from '../hooks/useForm';
import {toCamelCase} from '../utils/string';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import Select from './Form/Select';

const objectRelationshipTypes = [
	{
		description: Liferay.Language.get(
			"one-object's-entry-interacts-only-with-one-other-object's-entry"
		),
		label: Liferay.Language.get('one-to-one'),
		value: 'oneToOne',
	},
	{
		description: Liferay.Language.get(
			"one-object's-entry-interacts-with-many-others-object's-entries"
		),
		label: Liferay.Language.get('one-to-many'),
		value: 'oneToMany',
	},
	{
		description: Liferay.Language.get(
			"multiple-object's-entries-can-interact-with-many-others-object's-entries"
		),
		label: Liferay.Language.get('many-to-many'),
		value: 'manyToMany',
	},
];

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddObjectRelationship: React.FC<IProps> = ({
	apiURL,
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionId,
	observer,
	onClose,
	system,
}) => {
	const [error, setError] = useState<string>('');
	const [objectDefinitions, setObjectDefinitions] = useState<
		TObjectDefinition[]
	>([]);
	const initialValues: TInitialValues = {
		label: '',
		name: undefined,
		objectDefinitionId2: 0,
		type: {label: '', value: ''},
	};

	const filteredObjectRelationshipTypes = objectRelationshipTypes.filter(
		({value}) => {
			if (system) {
				return value === 'oneToMany';
			}
			else if (!ffOneToOneRelationshipConfigurationEnabled) {
				return value !== 'oneToOne';
			}

			return true;
		}
	);

	const onSubmit = async ({
		label,
		name,
		objectDefinitionId2,
		type,
	}: TInitialValues) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				label: {
					[defaultLanguageId]: label,
				},
				name: name || toCamelCase(label),
				objectDefinitionId2,
				type: type.value,
			}),
			headers,
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = (await response.json()) as {title?: string};

			setError(title);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.label) {
			errors.label = Liferay.Language.get('required');
		}

		if (!(values.name ?? values.label)) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.type.label || !values.type.value) {
			errors.type = Liferay.Language.get('required');
		}

		if (!values.objectDefinitionId2) {
			errors.objectDefinitionId2 = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	const makeRequest = async () => {
		const result = await fetch(
			'/o/object-admin/v1.0/object-definitions?page=-1',
			{
				headers,
				method: 'GET',
			}
		);

		const {items = []} = (await result.json()) as {items?: []};

		const objectDefinitions = items
			.map(({id, name, system}: TObjectDefinition) => ({
				id,
				name,
				system,
			}))
			.filter(({system}: TObjectDefinition) => !system);

		setObjectDefinitions(objectDefinitions);
	};

	const handleChangeManyToMany = () => {
		const newObjectDefinitions = objectDefinitions.filter(
			(objectDefinition) =>
				objectDefinition.id !== Number(objectDefinitionId)
		);

		setObjectDefinitions(newObjectDefinitions);
	};

	useEffect(() => {
		makeRequest();
	}, []);

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-relationship')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						id="objectRelationshipLabel"
						label={Liferay.Language.get('label')}
						name="label"
						onChange={handleChange}
						required
						value={values.label}
					/>

					<Input
						error={errors.name}
						id="objectRelationshipName"
						label={Liferay.Language.get('relationship-name')}
						name="name"
						onChange={handleChange}
						required
						value={values.name ?? toCamelCase(values.label)}
					/>

					<CustomSelect
						error={errors.type}
						label={Liferay.Language.get('type')}
						onChange={(type) => {
							setValues({type});

							type.value === 'manyToMany'
								? handleChangeManyToMany()
								: makeRequest();
						}}
						options={filteredObjectRelationshipTypes}
						required
						value={values.type.label}
					/>

					<Select
						error={errors.objectDefinitionId2}
						id="objectDefinitionId2"
						label={Liferay.Language.get('object')}
						onChange={({target: {value}}: any) => {
							const {id} = objectDefinitions[Number(value) - 1];

							setValues({objectDefinitionId2: Number(id)});
						}}
						options={objectDefinitions.map(({name}) => name)}
						required
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	ffOneToOneRelationshipConfigurationEnabled: boolean;
	objectDefinitionId: number;
	observer: any;
	onClose: () => void;
	system: boolean;
}

type TObjectDefinition = {
	id: number;
	name: string;
	system: boolean;
};

type TInitialValues = {
	label: string;
	name?: string;
	objectDefinitionId2: number;
	type: {
		label: string;
		value: string;
	};
};

const ModalWithProvider: React.FC<IProps> = ({
	apiURL,
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionId,
	system,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectRelationship', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectRelationship');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectRelationship
					apiURL={apiURL}
					ffOneToOneRelationshipConfigurationEnabled={
						ffOneToOneRelationshipConfigurationEnabled
					}
					objectDefinitionId={objectDefinitionId}
					observer={observer}
					onClose={onClose}
					system={system}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
