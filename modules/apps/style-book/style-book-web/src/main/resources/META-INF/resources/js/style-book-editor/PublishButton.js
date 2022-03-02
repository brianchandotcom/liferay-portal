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
import ClayModal, {useModal} from '@clayui/modal';
import {
	StyleErrorsModal,
	useHasStyleErrors,
} from '@liferay/layout-content-page-editor-web';
import React, {useRef, useState} from 'react';

import {config} from './config';

export default function PublishButton() {
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

	const handleSubmit = () => {
		if (formRef.current) {
			formRef.current.submit();
		}
	};

	return (
		<>
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
					aria-label={Liferay.Language.get('publish')}
					disabled={config.pending}
					displayType="primary"
					onClick={
						hasStyleErrors
							? () => setOpenStyleErrorsModal(true)
							: () => setOpenPublishModal(true)
					}
					small
					type="button"
				>
					{Liferay.Language.get('publish')}
				</ClayButton>
			</form>

			{openStyleErrorsModal && hasStyleErrors && (
				<StyleErrorsModal
					onCloseModal={() => setOpenStyleErrorsModal(false)}
					onSubmit={() => {
						setOpenStyleErrorsModal(false);
						setOpenPublishModal(true);
					}}
				/>
			)}

			{openPublishModal && (
				<ClayModal
					aria-label={Liferay.Language.get('publishing-info')}
					observer={observerPublishModal}
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
