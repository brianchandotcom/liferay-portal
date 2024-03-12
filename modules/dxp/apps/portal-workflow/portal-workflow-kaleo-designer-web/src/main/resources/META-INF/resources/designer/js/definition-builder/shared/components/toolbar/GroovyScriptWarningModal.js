/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

export function GroovyScriptWarningModal({setShowGroovyScriptWarningModal}) {
	const {observer, onClose} = useModal({
		onClose: () => {
			setShowGroovyScriptWarningModal();
		},
	});

	return (
		<ClayModal center observer={observer} size="lg" status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('error-updating-definition')}
			</ClayModal.Header>

			<ClayModal.Body>
				{Liferay.Language.get(
					'workflow-definitions-that-include-groovy-script-are-deactivated-for-your-instance'
				)}
				&nbsp;
				<a>
					{Liferay.Language.get(
						'go-to-system-settings-security-tools-script-management-to-allow-administrators-to-execute-groovy-code'
					)}
				</a>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton
						displayType="warning"
						onClick={(event) => {
							event.preventDefault();

							onClose();
						}}
					>
						{Liferay.Language.get('done')}
					</ClayButton>
				}
			/>
		</ClayModal>
	);
}
