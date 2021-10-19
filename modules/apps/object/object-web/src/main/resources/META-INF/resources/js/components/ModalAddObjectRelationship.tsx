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
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {
	firstLetterLowercase,
	firstLetterUppercase,
	removeAllSpecialCharacters,
} from '../utils/string';
import CustomSelect from './form/CustomSelect';
import Input from './form/Input';
import Select from './form/Select';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	objectDefinitions: TObjectDefinition[];
}

type TObjectDefinition = {
	id: string;
	name: string;
	system: boolean;
};

type TFormState = {
	generateAutoName: boolean;
	label: {
		[key: string]: string;
	};
	name: string;
	objectDefinitionId2: number;
	type: {
		label: string;
		value: string;
	};
};

const objectRelationshipTypes = [

	/* {
		description: Liferay.Language.get(
			"one-object's-entry-interacts-only-with-one-other-object's-entry"
		),
		label: Liferay.Language.get('one-to-one'),
		value: 'oneToOne',
	},*/
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
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

type TFormatName = (str: string) => string;

const formatName: TFormatName = (str) => {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return firstLetterLowercase(removeAllSpecialCharacters(join));
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddObjectRelationship: React.FC<IProps> = ({
	apiURL,
	objectDefinitions,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		generateAutoName: true,
		label: {
			[defaultLanguageId]: '',
		},
		name: '',
		objectDefinitionId2: 0,
		type: {label: '', value: ''},
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveObjectRelationship = async () => {
		const {label, name, objectDefinitionId2, type} = formState;

		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				label: label ?? {[defaultLanguageId]: name},
				name,
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
			} = await response.json();

			setError(title);
		}
	};

	const handleOpenObjectRelationshipModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectRelationship', handleOpenObjectRelationshipModal);

		return () => {
			Liferay.detach(
				'addObjectRelationship',
				handleOpenObjectRelationshipModal
			);
		};
	}, []);

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayModal.Header>
						{Liferay.Language.get('new-relationship')}
					</ClayModal.Header>

					<ClayModal.Body>
						{error && (
							<ClayAlert displayType="danger">{error}</ClayAlert>
						)}

						<Input
							id="objectRelationshipLabel"
							label={Liferay.Language.get('label')}
							name="objectRelationshipLabel"
							onChange={({target: {value}}: any) => {
								setFormState({
									...formState,
									...(formState.generateAutoName && {
										name: formatName(value),
									}),
									label: {
										[defaultLanguageId]: value,
									},
								});

								error && setError('');
							}}
							required
							value={formState.label[defaultLanguageId]}
						/>

						<Input
							id="objectRelationshipName"
							label={Liferay.Language.get('relationship-name')}
							name="objectRelationshipName"
							onChange={({target: {value}}: any) => {
								setFormState({
									...formState,
									name: value,
								});

								error && setError('');
							}}
							required
							value={formState.name}
						/>

						<CustomSelect
							label={Liferay.Language.get('type')}
							onChange={(type: any) => {
								setFormState({
									...formState,
									type,
								});

								error && setError('');
							}}
							options={objectRelationshipTypes}
							required
							value={formState.type.label}
						>
							{({description, label}) => (
								<>
									<div>{label}</div>
									<span className="text-small">
										{description}
									</span>
								</>
							)}
						</CustomSelect>

						<Select
							id="objectDefinitionId2"
							label={Liferay.Language.get('object')}
							onChange={({target: {value}}: any) => {
								const {id} = objectDefinitions[
									Number(value) - 1
								];

								setFormState({
									...formState,
									objectDefinitionId2: Number(id),
								});

								error && setError('');
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

								<ClayButton
									displayType="primary"
									onClick={() =>
										handleSaveObjectRelationship()
									}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [objectDefinitions, setObjectDefinitions] = useState<
		TObjectDefinition[]
	>([]);

	useEffect(() => {
		const makeRequest = async () => {
			const result = await Liferay.Util.fetch(
				'/o/object-admin/v1.0/object-definitions',
				{
					headers,
					method: 'GET',
				}
			);

			const {items = []} = await result.json();

			const objectDefinitions = items
				.map(({id, name, system}: TObjectDefinition) => ({
					id,
					name,
					system,
				}))
				.filter(({system}: TObjectDefinition) => !system);

			setObjectDefinitions(objectDefinitions);
		};

		makeRequest();
	}, []);

	return (
		<ClayModalProvider>
			<ModalAddObjectRelationship
				apiURL={apiURL}
				objectDefinitions={objectDefinitions}
			/>
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
