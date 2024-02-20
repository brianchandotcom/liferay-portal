/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

export default function LockedArticleModal({isAdmin, open, portletNamespace}) {
	const [showModal, setShowModal] = useState(open);

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const bridgeComponentId = `${portletNamespace}LockedKBArticleModal`;

		if (!Liferay.component(bridgeComponentId)) {
			Liferay.component(
				bridgeComponentId,
				{
					open: () => {
						setShowModal(true);
					},
				},
				{
					destroyOnNavigate: true,
				}
			);
		}

		return () => {
			Liferay.destroyComponent(bridgeComponentId);
		};
	}, [portletNamespace]);

	return (
		<>
			{showModal && (
				<ClayModal observer={observer} size="md" status="info">
					<ClayModal.Header>
						{Liferay.Language.get('article-in-edition')}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							{isAdmin
								? Liferay.Language.get(
										'article-in-edition-admin-description'
								  )
								: Liferay.Language.get(
										'article-in-edition-description'
								  )}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							isAdmin ? (
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={onClose}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>

									<ClayButton
										displayType="primary"
										onClick={onClose}
									>
										{Liferay.Language.get('take-control')}
									</ClayButton>
								</ClayButton.Group>
							) : (
								<ClayButton
									displayType="primary"
									onClick={onClose}
								>
									{Liferay.Language.get('ok')}
								</ClayButton>
							)
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
