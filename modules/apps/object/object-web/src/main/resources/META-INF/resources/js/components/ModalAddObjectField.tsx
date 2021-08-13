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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect, ClayToggle} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import RequiredMask from './RequiredMask';

interface IErrorFeedback extends React.HTMLAttributes<HTMLElement> {
	description: string;
	spritemap: string;
}

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	spritemap: string;
}

const objectFieldTypes = [
	'BigDecimal',
	'Blob',
	'Boolean',
	'Date',
	'Double',
	'Integer',
	'Long',
	'String',
];

type THandleFormStateFn = (
	key: string,
	value: boolean | string
) => void;

type TFormState = {
	name: string;
	required: boolean;
	type: string;
};

const ErrorFeedback: React.FC<IErrorFeedback> = ({description, spritemap}) => {
	return (
		<ClayForm.FeedbackGroup>
			<ClayForm.FeedbackItem>
				<ClayForm.FeedbackIndicator
					spritemap={spritemap}
					symbol="exclamation-full"
				/>
				{description}
			</ClayForm.FeedbackItem>
		</ClayForm.FeedbackGroup>
	);
};

const ModalAddObjectField: React.FC<IProps> = ({apiURL, spritemap}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		name: '',
		required: false,
		type: '',
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveObjectField = () => {
		const {name, required, type} = formState;

		Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				indexed: false,
				indexedAsKeyword: false,
				indexedLanguageId: null,
				name,
				required,
				type,
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

	const handleOpenObjectFieldModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectField', handleOpenObjectFieldModal);

		return () => {
			Liferay.detach('addObjectField', handleOpenObjectFieldModal);
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
						{Liferay.Language.get('new-field')}
					</ClayModal.Header>

					<ClayModal.Body>
						<ClayForm.Group
							className={classNames({
								'has-error': error && !error.includes('type'),
							})}
						>
							<label htmlFor="objectFieldName">
								{Liferay.Language.get('name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="objectFieldName"
								onChange={({target: {value}}) =>
									handleChangeForm('name', value)
								}
								type="text"
								value={formState.name}
							/>

							{error && !error.includes('type') && (
								<ErrorFeedback
									description={error}
									spritemap={spritemap}
								/>
							)}
						</ClayForm.Group>

						<ClayForm.Group
							className={classNames({
								'has-error': error && error.includes('type'),
							})}
						>
							<label htmlFor="objectFieldType">
								{Liferay.Language.get('type')}

								<RequiredMask />
							</label>

							<ClaySelect
								id="objectFieldType"
								onChange={({target: {value}}) =>
									handleChangeForm('type', value)
								}
							>
								{[
									Liferay.Language.get('choose-an-option'),
									...objectFieldTypes,
								].map((type) => (
									<ClaySelect.Option
										key={type}
										label={type}
										value={type}
									/>
								))}
							</ClaySelect>

							{error && error.includes('type') && (
								<ErrorFeedback
									description={error}
									spritemap={spritemap}
								/>
							)}
						</ClayForm.Group>

						<ClayToggle
							label={Liferay.Language.get('mandatory')}
							onToggle={() =>
								handleChangeForm(
									'required',
									!formState.required
								)
							}
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
