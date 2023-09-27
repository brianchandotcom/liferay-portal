/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React from 'react';

import ExportFormModalBody from './ExportFormModalBody';

const ExportFormModal: React.FC<IProps> = ({
	csvExport,
	exportFormURL,
	fileExtensions,
	observer,
	onClose,
	portletNamespace,
}) => {
	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('export')}
			</ClayModal.Header>

			<form action={exportFormURL} method="post">
				<ClayModal.Body>
					<ExportFormModalBody
						csvExport={csvExport}
						fileExtensions={fileExtensions}
						portletNamespace={portletNamespace}
					/>
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
								displayType="primary"
								onClick={onClose}
								type="submit"
							>
								{Liferay.Language.get('ok')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</form>
		</ClayModal>
	);
};

interface IProps {
	csvExport: string;
	exportFormURL: string;
	fileExtensions: string[];
	observer: any;
	onClose: () => void;
	portletNamespace: string;
}

export default ExportFormModal;
