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
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {fetch, navigate, objectToFormData} from 'frontend-js-web';
import React, {useState} from 'react';

import PublicationStatus from './PublicationStatus';

export default function MoveChangesModal({
	changes,
	ctCollectionId,
	moveChangesURL,
	namespace,
	publications,
	spritemap,
	trigger,
}) {
	const [showModal, setShowModal] = useState(false);
	const [status, setStatus] = useState(null);
	const [toCTCollectionId, setToCTCollectionId] = useState(null);
	const {observer, onClose} = useModal({
		onClose: () => {
			setShowModal(false);
			setStatus(null);
		},
	});

	const handleSubmit = (event) => {
		event.preventDefault();

		const ctEntryIds = [];

		for (let i = 0; i < changes.length; i++) {
			ctEntryIds.push(changes[i].ctEntryId);
		}

		const formData = objectToFormData({
			[`${namespace}ctEntryIds`]: ctEntryIds.join(','),
			[`${namespace}fromCTCollectionId`]: ctCollectionId,
			[`${namespace}toCTCollectionId`]: toCTCollectionId,
		});

		fetch(moveChangesURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				return response.json();
			})
			.then((json) => {
				setStatus(json);
			});
	};

	const renderClose = (response) => {
		let title;

		if (response.displayType === 'success') {
			title = Liferay.Language.get('the-changes-were-moved-successfully');
		}
		else if (
			response.displayType === 'danger' &&
			response.label === 'conflict'
		) {
			title = Liferay.Language.get(
				'one-or-more-changes-conflict-with-existing-changes-in-the-destination-publication'
			);
		}
		else {
			title = Liferay.Language.get('the-changes-could-not-be-moved');
		}

		return (
			<>
				<ClayAlert
					actions={
						<ClayButton
							alert
							displayType="primary"
							onClick={() => {
								navigate(status.redirectURL);
							}}
						>
							{Liferay.Language.get('close')}
						</ClayButton>
					}
					displayType={response.displayType}
					spritemap={spritemap}
					title={title}
					variant="inline"
				/>
			</>
		);
	};

	const resetForm = () => {
		setToCTCollectionId(null);
	};

	const renderStatus = () => {
		return (
			<PublicationStatus
				dataURL={status.statusURL}
				displayType={status.displayType}
				label={status.label}
				renderComplete={renderClose}
				spritemap={spritemap}
			/>
		);
	};

	const renderTrigger = () => {
		if (trigger) {
			return trigger;
		}

		if (ctCollectionId !== 0) {
			return (
				<ClayButton
					aria-label="Show Move Changes Modal"
					data-tooltip-align="top"
					displayType="secondary"
					onClick={() => setShowModal(true)}
					small
					title={Liferay.Language.get('move-changes')}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol="move-folder" />
					</span>

					{Liferay.Language.get('move-changes')}
				</ClayButton>
			);
		}
	};

	if (!showModal) {
		return renderTrigger();
	}
	else {
		return (
			<>
				<ClayModal
					className="publications-move-changes-modal"
					observer={observer}
					size="lg"
					spritemap={spritemap}
				>
					<ClayForm onSubmit={handleSubmit}>
						<ClayModal.Header>
							<div className="autofit-row">
								<div className="autofit-col">
									<ClaySticker
										className="sticker-use-icon user-icon-color-0"
										displayType="secondary"
										shape="circle"
									>
										<ClayIcon symbol="move-folder" />
									</ClaySticker>
								</div>

								<div className="autofit-col">
									<div className="modal-title">
										{Liferay.Language.get('move-changes')}
									</div>
								</div>
							</div>
						</ClayModal.Header>

						<div className="inline-scroller modal-body publications-invite-users-modal-body">
							<ClayAlert
								displayType="info"
								spritemap={spritemap}
								title={Liferay.Language.get(
									'select-the-publication-to-move-the-selected-changes-to'
								)}
							/>

							<span>{`${changes.length} change${
								changes.length === 1 ? ' has' : 's have'
							} been selected to move to another Publication.`}</span>

							<br />

							<ClaySelect
								aria-label="Select the publication you would like to move your changes to."
								defaultValue={0}
								id="toPublicationSelect"
								onChange={(event) => {
									setToCTCollectionId(event.target.value);
								}}
							>
								<ClaySelect.Option
									disabled
									hidden
									label={Liferay.Language.get(
										'select-the-target-publication'
									)}
									value={0}
								/>

								{publications &&
									publications
										.filter(
											(publication) =>
												publication.ctCollectionId !==
												ctCollectionId
										)
										.map((publication) => (
											<ClaySelect.Option
												key={publication.ctCollectionId}
												label={publication.name}
												value={
													publication.ctCollectionId
												}
											/>
										))}
							</ClaySelect>
						</div>

						<ClayModal.Footer
							last={
								status ? (
									renderStatus()
								) : (
									<ClayButton.Group spaced>
										<ClayButton
											displayType="secondary"
											onClick={() => {
												onClose();
												resetForm();
											}}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>

										<ClayButton
											disabled={
												!toCTCollectionId ||
												!changes.length > 0
											}
											displayType="primary"
											type="submit"
										>
											{Liferay.Language.get('move')}
										</ClayButton>
									</ClayButton.Group>
								)
							}
						/>
					</ClayForm>
				</ClayModal>
			</>
		);
	}
}
