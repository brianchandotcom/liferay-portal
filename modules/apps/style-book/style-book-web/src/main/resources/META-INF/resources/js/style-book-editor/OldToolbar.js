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
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {
	StyleErrorsModal,
	useHasStyleErrors,
} from '@liferay/layout-content-page-editor-web';
import classNames from 'classnames';
import React, {useContext, useRef, useState} from 'react';

import {StyleBookContext} from './StyleBookContext';
import {config} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';

const STATUS_TO_LABEL = {
	[DRAFT_STATUS.draftSaved]: Liferay.Language.get('saved'),
	[DRAFT_STATUS.notSaved]: '',
	[DRAFT_STATUS.saving]: `${Liferay.Language.get('saving')}...`,
};

export default function OldToolbar() {
	const {draftStatus} = useContext(StyleBookContext);
	const formRef = useRef();
	const hasStyleErrors = useHasStyleErrors();
	const [openPublishModal, setOpenPublishModal] = useState(false);
	const [openStyleErrorsModal, setOpenStyleErrorsModal] = useState(false);

	const {
		observer: observerPublishModal,
		onClose: onClosePublishModal,
	} = useModal({
		onClose: () => setOpenPublishModal(false),
	});

	const handleSubmit = (event) => {
		if (config.tokenReuseEnabled) {
			if (formRef.current) {
				formRef.current.submit();
			}

			return;
		}

		if (
			!confirm(
				Liferay.Language.get(
					'once-published,-these-changes-will-affect-all-instances-of-the-site-using-these-properties'
				)
			)
		) {
			event.preventDefault();
		}
	};

	return (
		<div className="p-3 style-book-editor__old-toolbar">
			<div>
				<span
					className={classNames(
						'mx-1 style-book-editor__status-text',
						{
							'text-success':
								draftStatus === DRAFT_STATUS.draftSaved,
						}
					)}
				>
					{STATUS_TO_LABEL[draftStatus]}
				</span>

				{draftStatus === DRAFT_STATUS.draftSaved && (
					<ClayIcon
						className="mx-1 style-book-editor__status-icon"
						symbol="check-circle"
					/>
				)}
			</div>

			<form action={config.publishURL} method="POST" ref={formRef}>
				<input
					name={`${config.namespace}redirect`}
					type="hidden"
					value={config.redirectURL}
				/>

				<input
					name={`${config.namespace}styleBookEntryId`}
					type="hidden"
					value={config.styleBookEntryId}
				/>

				<ClayButton
					disabled={config.pending}
					displayType="primary"
					onClick={
						config.tokenReuseEnabled
							? hasStyleErrors
								? () => setOpenStyleErrorsModal(true)
								: () => setOpenPublishModal(true)
							: handleSubmit
					}
					small
					type={config.tokenReuseEnabled ? 'button' : 'submit'}
				>
					{Liferay.Language.get('publish')}
				</ClayButton>

				{config.tokenReuseEnabled && (
					<>
						{openStyleErrorsModal && hasStyleErrors && (
							<StyleErrorsModal
								onCloseModal={() =>
									setOpenStyleErrorsModal(false)
								}
								onSubmit={() => {
									setOpenStyleErrorsModal(false);
									setOpenPublishModal(true);
								}}
							/>
						)}

						{openPublishModal && (
							<ClayModal
								observer={observerPublishModal}
								size="lg"
								status="info"
							>
								<ClayModal.Header>
									{Liferay.Language.get('publishing-info')}
								</ClayModal.Header>

								<ClayModal.Body>
									<p>
										{Liferay.Language.get(
											'once-published-these-changes-will-affect-all-instances-of-the-site-using-these-properties-do-you-want-to-publish-now'
										)}
									</p>
								</ClayModal.Body>

								<ClayModal.Footer
									last={
										<ClayButton.Group spaced>
											<ClayButton
												displayType="secondary"
												onClick={onClosePublishModal}
											>
												{Liferay.Language.get('cancel')}
											</ClayButton>

											<ClayButton
												displayType="info"
												onClick={handleSubmit}
												type="submit"
											>
												{Liferay.Language.get(
													'continue'
												)}
											</ClayButton>
										</ClayButton.Group>
									}
								/>
							</ClayModal>
						)}
					</>
				)}
			</form>
		</div>
	);
}
