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

export default function DeleteLocaleModal({observer, onCancel, onConfirm}) {
	return (
		<ClayModal observer={observer} size="sm">
			<ClayModal.Body>
				<h4>
					{Liferay.Language.get(
						'are-you-sure-you-want-to-deactivate-this-language'
					)}
				</h4>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('dismiss')}
						</ClayButton>
						<ClayButton onClick={onConfirm}>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
