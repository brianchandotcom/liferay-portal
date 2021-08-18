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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import RequiredMask from './RequiredMask';

declare global {
	const Liferay: any;
}

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	spritemap: string;
}

type THandleFormStateFn = (
	key: string,
	value: boolean | string | TLocalizableLable
) => void;

type TLocalizableLable = {
	[key: string]: string;
};

type TFormState = {
	label: {
		[key: string]: string;
	};
	pluralLabel: {
		[key: string]: string;
	};
	name: string;
	required: boolean;
	type: string;
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddObjectDefinition: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		label: {
			[defaultLanguageId]: '',
		},
		name: '',
		pluralLabel: {
			[defaultLanguageId]: '',
		},
		required: false,
		type: '',
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveObjectDefinition = () => {
		const {label, name, pluralLabel} = formState;

		Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				label,
				name,
				objectFields: [],
				pluralLabel,
			}),
			headers: new Headers({
				Accept: 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		})
			.then((response: any) => {
				if (response.ok) {
					onClose();

					window.location.reload();
				}
				else {
					return response.json();
				}
			})
			.then(({title}: {title: string}) => {
				setError(title);
			});
	};

	const handleOpenObjectDefinitionModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectDefinition', handleOpenObjectDefinitionModal);

		return () => {
			Liferay.detach(
				'addObjectDefinition',
				handleOpenObjectDefinitionModal
			);
		};
	}, []);

	const handleChangeForm: THandleFormStateFn = (key, value) => {
		setError('');

		setFormState({
			...formState,
			[key]: value,
		});
	};

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayModal.Header>
						{Liferay.Language.get('new-custom-object')}
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
							<label htmlFor="objectDefinitionLabel">
								{Liferay.Language.get('label')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectDefinitionLabel"
								onChange={({target: {value}}) =>
									handleChangeForm('label', {
										[defaultLanguageId]: value,
									})
								}
								type="text"
								value={formState.label[defaultLanguageId]}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="objectDefinitionPluralLabel">
								{Liferay.Language.get('plural-label')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectDefinitionPluralLabel"
								onChange={({target: {value}}) =>
									handleChangeForm('pluralLabel', {
										[defaultLanguageId]: value,
									})
								}
								type="text"
								value={formState.pluralLabel[defaultLanguageId]}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="objectDefinitionName">
								{Liferay.Language.get('object-name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectDefinitionName"
								onChange={({target: {value}}) =>
									handleChangeForm('name', value)
								}
								type="text"
								value={formState.name}
							/>
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
									onClick={() => handleSaveObjectDefinition()}
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
	return (
		<ClayModalProvider>
			<ModalAddObjectDefinition apiURL={apiURL} spritemap={spritemap} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
