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
import ClayModal from '@clayui/modal';
import React from 'react';

export function CancelEditAPIApplicationModalContent({
	closeModal,
}: {
	closeModal: voidReturn;
}) {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('cancel-changes')}
			</ClayModal.Header>

			<div className="modal-body">
				<p>
					{Liferay.Language.get(
						'all-your-unsaved-changes-will-be-lost-unless-you-save-or-publish-them-before-leaving'
					)}
				</p>

				<p>
					{Liferay.Language.get(
						'are-you-sure-you-want-to-continue-without-saving'
					)}
				</p>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalGiveUpCancelEditButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							id="modalConfirmCancelEditButton"
							onClick={() => history.back()}
							type="button"
						>
							{Liferay.Language.get('continue-without-saving')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
