/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React from 'react';

export interface IFilterModalFooterProps {
	closeModal: Function;
	handleSave: Function;
	saveButtonDisabled: boolean;
}

function FilterModalFooter({
	closeModal,
	handleSave,
	saveButtonDisabled,
}: IFilterModalFooterProps) {
	return (
		<ClayModal.Footer
			last={
				<ClayButton.Group spaced>
					<ClayButton
						disabled={saveButtonDisabled}
						onClick={() => handleSave()}
						type="submit"
					>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => closeModal()}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			}
		/>
	);
}

export default FilterModalFooter;
