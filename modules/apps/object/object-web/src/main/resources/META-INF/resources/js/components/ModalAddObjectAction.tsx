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

import {CustomItem} from './Form/CustomSelect/CustomSelect';
import ObjectActionFormBase, {
	useObjectActionForm,
} from './ObjectActionFormBase';

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const ModalAddObjectAction: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
	observer,
	onClose,
}) => {
	const [error, setError] = useState<string>('');

	const onSubmit = async (objectAction: ObjectAction) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify(objectAction),
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
			const {title} = (await response.json()) as {title?: string};

			setError(title ?? Liferay.Language.get('an-error-occurred'));
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectActionForm({
		initialValues: {active: true},
		onSubmit,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-action')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<ObjectActionFormBase
						errors={errors}
						handleChange={handleChange}
						objectAction={values}
						objectActionExecutors={objectActionExecutors}
						objectActionTriggers={objectActionTriggers}
						setValues={setValues}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
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
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	observer: any;
	onClose: () => void;
}

const ModalWithProvider: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectAction', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectAction', () => setVisibleModal(true));
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectAction
					apiURL={apiURL}
					objectActionExecutors={objectActionExecutors}
					objectActionTriggers={objectActionTriggers}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
