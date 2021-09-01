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
import ClayLocalizedInput from '@clayui/localized-input';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import RequiredMask from './RequiredMask';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	spritemap: string;
}

type TFormState = {
	key: string;
	name_i18n: {
		[key: string]: string;
	};
};

type TLocale = {
	label: string;
	symbol: string;
};

const defaultLanguageId: string = Liferay.ThemeDisplay.getDefaultLanguageId();

const availableLocales: TLocale[] = Object.keys(Liferay.Language.available).map(
	(language) => {
		const formattedLocales = language.replace('_', '-');

		return {
			label: formattedLocales,
			symbol: formattedLocales.toLowerCase(),
		};
	}
);

const ModalAddListTypeEntry: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		key: '',
		name_i18n: {
			[defaultLanguageId.replace('_', '-')]: '',
		},
	});
	const [error, setError] = useState<string>('');
	const [selectedLocale, setSelectedLocale] = useState<TLocale>(
		availableLocales[0]
	);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveListTypeEntry = async () => {
		const {key, name_i18n} = formState;

		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				key,
				name_i18n,
			}),
			headers: new Headers({
				Accept: 'application/json',
				'Content-Type': 'application/json',
			}),
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

	const handleOpenListTypeEntryModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addListTypeEntry', handleOpenListTypeEntryModal);

		return () => {
			Liferay.detach('addListTypeEntry', handleOpenListTypeEntryModal);
		};
	}, []);

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

						<ClayLocalizedInput
							id="locale"
							label={Liferay.Language.get('name')}
							locales={availableLocales}
							onSelectedLocaleChange={setSelectedLocale}
							onTranslationsChange={(value) => {
								setFormState({
									...formState,
									name_i18n: value,
								});

								error && setError('');
							}}
							required
							selectedLocale={selectedLocale}
							spritemap={spritemap}
							translations={formState.name_i18n}
						/>

						<ClayForm.Group>
							<label htmlFor="listTypeEntryKey">
								{Liferay.Language.get('key')}

								<RequiredMask />
							</label>

							<ClayInput
								id="listTypeEntryKey"
								onChange={({target: {value}}) => {
									setFormState({
										...formState,
										key: value,
									});

									error && setError('');
								}}
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
