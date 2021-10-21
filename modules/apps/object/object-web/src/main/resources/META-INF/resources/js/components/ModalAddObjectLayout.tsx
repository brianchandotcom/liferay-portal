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

import RequiredMask from './form/RequiredMask';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
}

type TFormState = {
	name: {
		[key: string]: string;
	};
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddListTypeDefinition: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		name: {
			[defaultLanguageId]: '',
		},
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveListTypeDefinition = async () => {
		const {name} = formState;

		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				name,
			}),
			headers: new Headers({
				'Accept': 'application/json',
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

	const handleOpenObjectLayoutModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectLayout', handleOpenObjectLayoutModal);

		return () => {
			Liferay.detach('addObjectLayout', handleOpenObjectLayoutModal);
		};
	}, []);

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayModal.Header>
						{Liferay.Language.get('new-layout')}
					</ClayModal.Header>
					<ClayModal.Body>
						{error && (
							<ClayAlert displayType="danger">{error}</ClayAlert>
						)}

						<ClayForm.Group>
							<label htmlFor="listObjectLayoutName">
								{Liferay.Language.get('name')}

								<RequiredMask />
							</label>

							<ClayInput
								id="listObjectLayoutName"
								onChange={({target: {value}}) => {
									setFormState({
										...formState,
										name: {
											[defaultLanguageId]: value,
										},
									});

									error && setError('');
								}}
								type="text"
								value={formState.name[defaultLanguageId]}
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

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	return (
		<ClayModalProvider>
			<ModalAddListTypeDefinition apiURL={apiURL} />
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
