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
	key: string;
	name_i18n: {
		[key: string]: string;
	};
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddListTypeEntry: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		key: '',
		name_i18n: {
			[defaultLanguageId]: '',
		},
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveListTypeEntry = () => {
		const {key, name_i18n} = formState;

		Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				key,
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

	const handleOpenListTypeEntryModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addListTypeEntry', handleOpenListTypeEntryModal);

		return () => {
			Liferay.detach('addListTypeEntry', handleOpenListTypeEntryModal);
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
						{Liferay.Language.get('new-item')}
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
							<label htmlFor="listTypeEntryLabel">
								{Liferay.Language.get('name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="listTypeEntryLabel"
								onChange={({target: {value}}) =>
									handleChangeForm('name_i18n', {
										[defaultLanguageId]: value,
									})
								}
								type="text"
								value={formState.name_i18n[defaultLanguageId]}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="listTypeEntryKey">
								{Liferay.Language.get('key')}

								<RequiredMask />
							</label>

							<ClayInput
								id="listTypeEntryKey"
								onChange={({target: {value}}) =>
									handleChangeForm('key', value)
								}
								type="text"
								value={formState.key}
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
									onClick={() => handleSaveListTypeEntry()}
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
			<ModalAddListTypeEntry apiURL={apiURL} spritemap={spritemap} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
