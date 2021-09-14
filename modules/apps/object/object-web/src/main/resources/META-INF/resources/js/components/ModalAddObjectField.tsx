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
import ClayForm, {ClayInput, ClaySelect, ClayToggle} from '@clayui/form';
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
	spritemap: string;
}

type TLocalizableLable = {
	[key: string]: string;
};

type TPicklist = {
	id: string;
	name: string;
};

type TFormState = {
	generateAutoName: boolean;
	label: TLocalizableLable;
	name: string;
	picklist: TPicklist[];
	required: boolean;
	listTypeDefinitionId: number;
	type: string;
};

type TFormatName = (str: string) => string;

const objectFieldTypes = [
	'BigDecimal',
	'Boolean',
	'Date',
	'Double',
	'Integer',
	'Long',
	'Picklist',
	'String',
];

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const headers = new Headers({
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

const formatName: TFormatName = (str) => {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return firstLetterLowercase(removeAllSpecialCharacters(join));
};

const ModalAddObjectField: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		generateAutoName: true,
		label: {
			[defaultLanguageId]: '',
		},
		listTypeDefinitionId: 0,
		name: '',
		picklist: [],
		required: false,
		type: '',
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveObjectField = async () => {
		const {label, listTypeDefinitionId, name, required, type} = formState;

		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: null,
				label,
				listTypeDefinitionId,
				name,
				required,
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

	const handleOpenObjectFieldModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectField', handleOpenObjectFieldModal);

		return () => {
			Liferay.detach('addObjectField', handleOpenObjectFieldModal);
		};
	}, []);

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayModal.Header>
						{Liferay.Language.get('new-field')}
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
							<label htmlFor="objectFieldLabel">
								{Liferay.Language.get('label')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectFieldLabel"
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
							<label htmlFor="objectFieldName">
								{Liferay.Language.get('field-name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectFieldName"
								onChange={({target: {value}}) => {
									setFormState({
										...formState,
										generateAutoName: !value,
										name: value,
									});

									error && setError('');
								}}
								type="text"
								value={formState.name}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="objectFieldType">
								{Liferay.Language.get('type')}

								<RequiredMask />
							</label>

							<ClaySelect
								id="objectFieldType"
								onChange={async ({target: {value}}) => {
									if (value === 'Picklist') {
										const result = await Liferay.Util.fetch(
											'/o/headless-admin-list-type/v1.0/list-type-definitions',
											{
												headers,
												method: 'GET',
											}
										);

										const {
											items = [],
										} = await result.json();

										setFormState({
											...formState,
											picklist: items.map(
												({id, name}: TPicklist) => ({
													id,
													name,
												})
											),
											type: 'String',
										});
									}
									else {
										setFormState({
											...formState,
											type: value,
										});
									}

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

								{objectFieldTypes.map((type) => (
									<ClaySelect.Option
										key={type}
										label={type}
										value={type}
									/>
								))}
							</ClaySelect>
						</ClayForm.Group>

						{!!formState.picklist.length && (
							<ClayForm.Group>
								<label htmlFor="objectFieldPicklist">
									{Liferay.Language.get('picklist')}

									<RequiredMask />
								</label>

								<ClaySelect
									id="objectFieldPicklist"
									onChange={({target: {value}}) => {
										setFormState({
											...formState,
											listTypeDefinitionId: Number(value),
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

									{formState.picklist.map(({id, name}) => (
										<ClaySelect.Option
											key={id}
											label={name}
											value={id}
										/>
									))}
								</ClaySelect>
							</ClayForm.Group>
						)}

						<ClayToggle
							label={Liferay.Language.get('mandatory')}
							onToggle={() => {
								setFormState({
									...formState,
									required: !formState.required,
								});

								error && setError('');
							}}
							toggled={formState.required}
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
									onClick={() => handleSaveObjectField()}
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
			<ModalAddObjectField apiURL={apiURL} spritemap={spritemap} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
