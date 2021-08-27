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

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	spritemap: string;
}

type THandleFormStateFn = (
	key: string,
	value: boolean | string | TLocalizableName
) => void;

type TLocalizableName = {
	[key: string]: string;
};

type TFormState = {
	name_i18n: {
		[key: string]: string;
	};
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddListTypeDefinition: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		name_i18n: {
			[defaultLanguageId]: '',
		},
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveListTypeDefinition = () => {
		const {name_i18n} = formState;

		Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				name_i18n,
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

	const handleOpenListTypeDefinitionModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addListTypeDefinition', handleOpenListTypeDefinitionModal);

		return () => {
			Liferay.detach(
				'addListTypeDefinition',
				handleOpenListTypeDefinitionModal
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
						{Liferay.Language.get('new-picklist')}
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
							<label htmlFor="listTypeDefinitionName">
								{Liferay.Language.get('name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="listTypeDefinitionName"
								onChange={({target: {value}}) =>
									handleChangeForm('name_i18n', {
										[defaultLanguageId]: value,
									})
								}
								type="text"
								value={formState.name_i18n[defaultLanguageId]}
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
									onClick={() =>
										handleSaveListTypeDefinition()
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
	return (
		<ClayModalProvider>
			<ModalAddListTypeDefinition apiURL={apiURL} spritemap={spritemap} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
