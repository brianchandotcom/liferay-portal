/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect} from 'react';

export default function LockedArticleModal() {
	const {observer, onOpenChange, open} = useModal();

	useEffect(() => {
		onOpenChange(true);
	}, [onOpenChange]);

	return (
		<>
			{open && (
				<ClayModal observer={observer} size="md" status="info">
					<ClayModal.Header>
						{Liferay.Language.get('article-in-edition')}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							{Liferay.Language.get(
								'article-in-edition-description'
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton
								displayType="primary"
								onClick={() => onOpenChange(false)}
							>
								{Liferay.Language.get('ok')}
							</ClayButton>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
