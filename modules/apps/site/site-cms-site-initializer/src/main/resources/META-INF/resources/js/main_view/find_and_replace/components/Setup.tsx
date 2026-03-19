/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {LanguagePicker} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {FieldFeedback, Locale, useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {Key, useContext, useState} from 'react';

import {FindAndReplaceContext} from '../contexts/FindAndReplaceContext';

export function Setup() {
	const {
		closeModal,
		items,
		localeId,
		locales,
		replacement,
		search,
		setLocaleId,
		setReplacement,
		setView,
	} = useContext(FindAndReplaceContext);

	const [hasError, setHasError] = useState(false);

	const inputId = useId();

	const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		if (!replacement) {
			setHasError(true);

			return;
		}

		setHasError(false);

		setView('summary');
	};

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('find-and-replace')}
			</ClayModal.Header>

			<ClayForm onSubmit={onSubmit}>
				<ClayModal.Body>
					<p className="text-secondary">
						{sub(
							Liferay.Language.get(
								'find-and-replace-text-across-x-selected-items'
							),
							items?.length
						)}
					</p>

					<ClayForm.Group className={hasError ? 'has-error' : ''}>
						<span className="font-weight-semi-bold text-3">
							{Liferay.Language.get('find')}
						</span>

						<p>{search}</p>

						<label htmlFor={inputId}>
							{Liferay.Language.get('replace-with-field-label')}
						</label>

						<ClayInput
							id={inputId}
							onChange={(event) => {
								const nextValue = event.target.value;

								setReplacement(nextValue);

								setHasError(!nextValue);
							}}
							placeholder={Liferay.Language.get(
								'enter-replacement-text'
							)}
							value={replacement}
						/>

						{hasError ? (
							<FieldFeedback
								errorMessage={Liferay.Language.get(
									'this-field-is-required'
								)}
							/>
						) : null}
					</ClayForm.Group>

					<LanguagePicker
						classNamesTrigger="mt-4"
						locales={[
							{
								id: 'all',
								label: 'All Languages',
							},
							...locales,
						]}
						onSelectedLocaleChange={(id: Key) => {
							setLocaleId(id as Locale['id']);
						}}
						selectedLocaleId={localeId}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={closeModal}
								type="button"
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('review-changes')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</>
	);
}
