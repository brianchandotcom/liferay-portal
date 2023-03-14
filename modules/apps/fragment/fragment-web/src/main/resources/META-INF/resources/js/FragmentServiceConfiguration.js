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
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import React, {useState} from 'react';

export default function FragmentServiceConfiguration({
	alreadyPropagateContributedFragmentChanges,
	namespace,
	propagateChanges,
	propagateContributedFragmentChanges,
	propagateContributedFragmentEntriesChangesURL,
}) {
	const [
		disablePropagateChangesButton,
		setDisablePropagateChangesButton,
	] = useState(alreadyPropagateContributedFragmentChanges);

	const [
		propagateContributedFragmentChangesChecked,
		setPropagateContributedFragmentChangesChecked,
	] = useState(propagateContributedFragmentChanges);

	const [propagateChangesChecked, setPropagateChangesChecked] = useState(
		propagateChanges
	);

	const [messageType, setMessageType] = useState(null);

	const handleSubmit = function () {
		fetch(propagateContributedFragmentEntriesChangesURL, {
			body: objectToFormData({
				[`${namespace}propagateChanges`]: propagateChangesChecked,
				[`${namespace}propagateContributedFragmentChanges`]: propagateContributedFragmentChangesChecked,
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				if (response.success) {
					openToast({
						message: Liferay.Language.get(
							'the-changes-in-the-contributed-fragments-have-been-propagated-successfully'
						),
						title: Liferay.Language.get('success'),
						type: 'success',
					});

					setDisablePropagateChangesButton(true);
					setMessageType('success');
				}
				else {
					openToast({
						message: Liferay.Language.get(
							'something-went-wrong-and-the-changes-in-the-contributed-fragments-could-not-be-propagated.-please-try-again-later'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					});

					setMessageType('error');
				}

				setWarningModalVisible(false);
			});
	};

	const [warningModalVisible, setWarningModalVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setWarningModalVisible(false),
	});

	return (
		<>
			<div className="sheet-subtitle">
				{Liferay.Language.get('default-fragments')}
			</div>

			<p className="text-secondary">
				{Liferay.Language.get(
					'default-fragments-are-provided-by-liferay-and-they-are-part-of-the-product-code.-here-you-can-define-their-behavior'
				)}
			</p>

			<ClayCheckbox
				defaultChecked={propagateContributedFragmentChanges}
				label={Liferay.Language.get(
					'propagate-contributed-fragment-changes-automatically'
				)}
				name={`${namespace}propagateContributedFragmentChanges`}
				onChange={({target: {checked}}) => {
					const propagateContributedFragmentChangesContainer = document.getElementById(
						`${namespace}propagateContributedFragmentChangesContainer`
					);

					propagateContributedFragmentChangesContainer.classList.toggle(
						'hide'
					);

					setPropagateContributedFragmentChangesChecked(checked);
				}}
			/>

			<div aria-hidden="true" className="form-feedback-group mb-3">
				<div className="form-text text-weight-normal">
					{Liferay.Language.get(
						'propagate-contributed-fragment-changes-automatically-description'
					)}
				</div>
			</div>

			<div
				className={`${
					propagateContributedFragmentChangesChecked ? 'hide' : ''
				}`}
				id={`${namespace}propagateContributedFragmentChangesContainer`}
			>
				<ClayButton
					disabled={disablePropagateChangesButton}
					displayType="secondary"
					onClick={() => setWarningModalVisible(true)}
				>
					{Liferay.Language.get('propagate-changes')}
				</ClayButton>

				{messageType === 'error' && (
					<span className="font-weight-semi-bold ml-3 text-danger">
						<ClayIcon
							className="mr-1 text-error"
							symbol="exclamation-full"
						/>

						{Liferay.Language.get(
							'the-changes-could-not-be-propagated,-please-try-again'
						)}
					</span>
				)}

				{messageType === 'success' && (
					<span className="font-weight-semi-bold ml-3 text-success">
						<ClayIcon
							className="mr-1 text-success"
							symbol="check-circle-full"
						/>

						{Liferay.Language.get('all-changes-are-propagated')}
					</span>
				)}
			</div>

			<div className="mt-3 sheet-subtitle">
				{Liferay.Language.get('custom-fragments')}
			</div>

			<p className="text-secondary">
				{Liferay.Language.get(
					'custom-fragments-are-those-that-are-created-by-the-user.-here-you-can-define-their-behavior'
				)}
			</p>

			<ClayCheckbox
				defaultChecked={propagateChanges}
				label={Liferay.Language.get(
					'propagate-fragment-changes-automatically'
				)}
				name={`${namespace}propagateChanges`}
				onChange={({target: {checked}}) => {
					setPropagateChangesChecked(checked);
				}}
			/>

			<div aria-hidden="true" className="form-feedback-group">
				<div className="form-text text-weight-normal">
					{Liferay.Language.get(
						'propagate-fragment-changes-automatically-description'
					)}
				</div>
			</div>

			{warningModalVisible && (
				<ClayModal
					observer={observer}
					role="alertdialog"
					size="md"
					status="warning"
				>
					<ClayModal.Header>
						{Liferay.Language.get('propagate-changes')}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							{Liferay.Language.get(
								'please-be-aware-that-if-any-content-creator-is-editing-a-page,-some-changes-may-not-be-saved.-performance-issues-can-also-result-from-this-action'
							)}
						</p>

						<p>
							{Liferay.Language.get(
								'are-you-sure-you-want-to-continue'
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="warning"
									onClick={handleSubmit}
									type="submit"
								>
									{Liferay.Language.get('continue')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
