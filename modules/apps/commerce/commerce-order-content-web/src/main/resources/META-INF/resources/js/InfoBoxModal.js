/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React from 'react';

import InfoBoxModalTextInput from './info_box/modal/InfoBoxModalTextInput';

const InfoBoxModal = ({
	handleSubmit,
	id,
	inputValue,
	label,
	observer,
	onOpenChange,
	open,
	setInputValue,
	spritemap,
}) => {
	return (
		<>
			{open && (
				<ClayModal
					id={id}
					observer={observer}
					size="lg"
					spritemap={spritemap}
				>
					<ClayForm onSubmit={handleSubmit}>
						<ClayModal.Header>{label}</ClayModal.Header>

						<ClayModal.Body>
							<ClayForm.Group>
								<InfoBoxModalTextInput
									inputValue={inputValue}
									label={label}
									setInputValue={setInputValue}
								/>
							</ClayForm.Group>
						</ClayModal.Body>

						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={() => onOpenChange(false)}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>

									<ClayButton type="submit">
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</ClayForm>
				</ClayModal>
			)}
		</>
	);
};

export default InfoBoxModal;
