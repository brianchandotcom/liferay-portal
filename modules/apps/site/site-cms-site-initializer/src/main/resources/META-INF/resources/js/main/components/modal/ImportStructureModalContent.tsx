/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';

import {FieldFile} from '../forms';

const JSON_EXTENSION = '.json';

export default function ImportStructureModalContent({
	closeModal,
}: {
	closeModal: () => void;
}) {
	const [warning, setWarning] = useState(true);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('import-and-override-structure')}
			</ClayModal.Header>

			{warning && (
				<ClayAlert
					displayType="warning"
					onClose={() => {
						setWarning(false);
					}}
					title={`${Liferay.Language.get('warning')}:`}
					variant="stripe"
				>
					{Liferay.Language.get(
						'import-and-override-structure-warning-message'
					)}
				</ClayAlert>
			)}

			<ClayModal.Body>
				<FieldFile
					label={Liferay.Language.get('json-file')}
					validExtensions={JSON_EXTENSION}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary">
							{Liferay.Language.get('import-and-override')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
