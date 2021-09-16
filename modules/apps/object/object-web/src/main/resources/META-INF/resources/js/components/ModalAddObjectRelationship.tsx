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
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {
	firstLetterLowercase,
	firstLetterUppercase,
	removeAllSpecialCharacters,
} from '../utils/string';
import RequiredMask from './RequiredMask';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	objectDefinitions: TObjectDefinition[];
	spritemap: string;
}

type TObjectDefinition = {
	id: string;
	name: string;
};

type TFormState = {
	generateAutoName: boolean;
	label: {
		[key: string]: string;
	};
	name: string;
	objectDefinitionId2: number;
	type: string;
};

const objectRelationshipTypes = ['one_to_one', 'one_to_many', 'many_to_many'];

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
	spritemap,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		generateAutoName: true,
		label: {
			[defaultLanguageId]: '',
		},
		name: '',
		objectDefinitionId2: 0,
		type: '',
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
				type,
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
							<ClayAlert
								displayType="danger"
								spritemap={spritemap}
							>
								{error}
							</ClayAlert>
						)}

						<ClayForm.Group>
							<label htmlFor="objectRelationshipLabel">
								{Liferay.Language.get('label')}
							</label>

							<ClayInput
								id="objectRelationshipLabel"
								onChange={({target: {value}}) => {
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
								type="text"
								value={formState.label[defaultLanguageId]}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="objectRelationshipName">
								{Liferay.Language.get('relationship-name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectRelationshipName"
								onChange={({target: {value}}) => {
									setFormState({
										...formState,
										name: value,
									});

									error && setError('');
								}}
								type="text"
								value={formState.name}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="objectRelationshipType">
								{Liferay.Language.get('type')}

								<RequiredMask />
							</label>

							<ClaySelect
								id="objectRelationshipType"
								onChange={async ({target: {value}}) => {
									setFormState({
										...formState,
										type: value,
									});

									error && setError('');
								}}
							>
								<ClaySelect.Option
									key={0}
									label={Liferay.Language.get(
										'choose-an-option'
									)}
									value={Liferay.Language.get(
										'choose-an-option'
									)}
								/>

								{objectRelationshipTypes.map((type) => (
									<ClaySelect.Option
										key={type}
										label={type}
										value={type}
									/>
								))}
							</ClaySelect>
						</ClayForm.Group>
						<ClayForm.Group>
							<label htmlFor="objectDefinitionId2">
								{Liferay.Language.get('object')}

								<RequiredMask />
							</label>

							<ClaySelect
								id="objectDefinitionId2"
								onChange={async ({target: {value}}) => {
									setFormState({
										...formState,
										objectDefinitionId2: Number(value),
									});

									error && setError('');
								}}
							>
								<ClaySelect.Option
									key={0}
									label={Liferay.Language.get(
										'choose-an-option'
									)}
									value={Liferay.Language.get(
										'choose-an-option'
									)}
								/>

								{objectDefinitions.map(({id, name}) => (
									<ClaySelect.Option
										key={id}
										label={name}
										value={id}
									/>
								))}
							</ClaySelect>
						</ClayForm.Group>
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

const ModalWithProvider: React.FC<IProps> = ({apiURL, spritemap}) => {
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

			const objectDefinitions = items.map(
				({id, name}: TObjectDefinition) => ({
					id,
					name,
				})
			);

			setObjectDefinitions(objectDefinitions);
		};

		makeRequest();
	}, []);

	return (
		<ClayModalProvider>
			<ModalAddObjectRelationship
				apiURL={apiURL}
				objectDefinitions={objectDefinitions}
				spritemap={spritemap}
			/>
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
