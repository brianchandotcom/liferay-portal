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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {fetch, openToast, sub} from 'frontend-js-web';
import React, {useState} from 'react';

interface DeleteAPIApplicationModal {
	closeModal: voidReturn;
	itemData: ItemData;
	loadData: voidReturn;
}

export function DeleteAPIApplicationModalContent({
	closeModal,
	itemData,
	loadData,
}: DeleteAPIApplicationModal) {
	const [confirmed, setConfirmed] = useState(false);
	const [displayError, setDisplayError] = useState(false);

	async function handleDelete() {
		const deleteURL = itemData.actions.delete.href;

		fetch(deleteURL.replace('{id}', String(itemData.id)), {
			method: 'DELETE',
		})
			.then(({ok}) => {
				if (ok) {
					closeModal();
					loadData();
					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});
				}
				else {
					throw new Error();
				}
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	}

	const handleConfirmationInput = (value: string) => {
		if (value === itemData.title) {
			setDisplayError(false);
			setConfirmed(true);
		}
		else if (value === '') {
			setDisplayError(false);
			setConfirmed(false);
		}
		else {
			setDisplayError(true);
			setConfirmed(false);
		}
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('delete-api-application')}
			</ClayModal.Header>

			<div className="modal-body">
				<p>
					{Liferay.Language.get(
						'this-action-cannot-be-undone-and-will-permanently-delete-all-the-related-schemas-and-endpoints-within-this-api'
					)}
				</p>

				<p>
					{Liferay.Language.get(
						'also-all-the-assets-that-used-it-will-not-work'
					)}
				</p>

				<p
					dangerouslySetInnerHTML={{
						__html: sub(
							Liferay.Language.get(
								'please-type-the-api-title-x-to-confirm'
							),
							`<strong id="titleConfirmationElement">${itemData.title}</strong>`
						),
					}}
				/>

				<ClayForm.Group
					className={classNames({
						'has-error': displayError,
					})}
				>
					<ClayInput
						onChange={({target: {value}}) => {
							handleConfirmationInput(value);
						}}
					/>

					<ClayForm.FeedbackGroup>
						{displayError && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-type-the-api-title-mentioned-above'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</ClayForm.Group>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalCancelButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!confirmed}
							displayType="danger"
							id="modalDeleteButton"
							onClick={handleDelete}
							type="button"
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
