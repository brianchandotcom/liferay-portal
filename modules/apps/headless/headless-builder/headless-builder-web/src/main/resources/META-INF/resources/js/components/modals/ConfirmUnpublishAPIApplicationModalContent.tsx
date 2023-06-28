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

export function ConfirmUnpublishAPIApplicationModalContent({
	closeModal,
	handlePublish,
}: {
	closeModal: voidReturn;
	handlePublish: voidReturn;
}) {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('unpublish-api-application')}
			</ClayModal.Header>

			<div className="modal-body">
				<p>
					{Liferay.Language.get(
						'unpublishing-will-remove-this-api-from-the-catalog-and-will-also-hide-all-the-schemas-and-endpoints-within-it'
					)}
				</p>

				<p>
					{Liferay.Language.get(
						'also-all-the-assets-that-used-it-will-not-work'
					)}
				</p>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalCancelUnpublishButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							id="modalConfirmUnpublishButton"
							onClick={handlePublish}
							type="button"
						>
							{Liferay.Language.get('unpublish')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
