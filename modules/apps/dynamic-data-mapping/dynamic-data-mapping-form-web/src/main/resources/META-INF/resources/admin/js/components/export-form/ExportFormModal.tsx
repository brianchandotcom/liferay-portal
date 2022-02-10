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

import ExportFormModalBody from './ExportFormModalBody';
import {TFileExtensions} from './types';

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
	fileExtensions: TFileExtensions;
	observer: any;
	onClose: () => void;
	portletNamespace: string;
}

export default ExportFormModal;
