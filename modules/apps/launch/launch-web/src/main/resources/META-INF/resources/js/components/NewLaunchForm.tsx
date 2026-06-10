/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {Launch, createLaunch} from '../api/launches';

interface Props {
	onCancel: () => void;
	onCreated: (launch: Launch) => void;
}

export default function NewLaunchForm({onCancel, onCreated}: Props) {
	const [description, setDescription] = useState('');
	const [error, setError] = useState<string | null>(null);
	const [name, setName] = useState('');
	const [submitting, setSubmitting] = useState(false);

	const handleCreate = async () => {
		const trimmedName = name.trim();

		if (!trimmedName) {
			setError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('name')
				)
			);

			return;
		}

		setError(null);
		setSubmitting(true);

		try {
			const launch = await createLaunch({
				description: description.trim(),
				name: trimmedName,
			});

			onCreated(launch);
		}
		catch (exception) {
			setError((exception as Error).message);
		}
		finally {
			setSubmitting(false);
		}
	};

	return (
		<div className="launch-new-form">
			<div className="management-bar management-bar-light navbar">
				<div className="align-items-center container-fluid d-flex">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('back')}
						borderless
						className="mr-2"
						displayType="secondary"
						onClick={onCancel}
						symbol="angle-left"
					/>

					<span className="font-weight-bold">
						{Liferay.Language.get('new-launch')}
					</span>

					<div className="ml-auto">
						<ClayButton
							borderless
							className="mr-2"
							disabled={submitting}
							displayType="secondary"
							onClick={onCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={submitting}
							displayType="primary"
							onClick={handleCreate}
						>
							{Liferay.Language.get('create')}
						</ClayButton>
					</div>
				</div>
			</div>

			<div className="container py-5">
				<ClayForm.Group>
					<ClayInput
						aria-label={Liferay.Language.get('launch-name')}
						className="bg-transparent border-0 p-0 shadow-none text-9 text-weight-bold"
						onChange={(event) => setName(event.target.value)}
						placeholder={Liferay.Language.get('untitled-launch')}
						value={name}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="mt-5">
					<label htmlFor="launch-description">
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						component="textarea"
						id="launch-description"
						onChange={(event) => setDescription(event.target.value)}
						placeholder={Liferay.Language.get('enter-description')}
						value={description}
					/>
				</ClayForm.Group>
			</div>

			{error ? (
				<div
					style={{
						bottom: '1rem',
						left: '1rem',
						position: 'fixed',
						zIndex: 1050,
					}}
				>
					<ClayAlert
						displayType="warning"
						onClose={() => setError(null)}
					>
						{error}
					</ClayAlert>
				</div>
			) : null}
		</div>
	);
}
