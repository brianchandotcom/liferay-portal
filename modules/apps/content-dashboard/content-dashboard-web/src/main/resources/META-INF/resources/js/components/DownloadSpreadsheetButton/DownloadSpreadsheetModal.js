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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

const DownloadSpreadsheetModal = ({
	disableSecondaryButton,
	secondaryButtonClickCallback,
	secondaryButtonText,
	setVisibilityCallback,
	show,
}) => {
	const {observer, onClose} = useModal({
		onClose: () => setVisibilityCallback(false),
	});

	return (
		<>
			{show && (
				<ClayModal
					center
					observer={observer}
					status="info"
					zIndex={2040}
				>
					<ClayModal.Header>
						{Liferay.Language.get('file-generation-in-progress')}
					</ClayModal.Header>

					<ClayModal.Body>
						<p>
							{Liferay.Language.get(
								'your-xls-file-is-being-generated.-leaving-this-page-will-cancel-the-process.-do-you-want-to-wait-for-the-file'
							)}
						</p>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									className="align-items-center d-flex"
									disabled={disableSecondaryButton}
									displayType="secondary"
									onClick={() =>
										secondaryButtonClickCallback(onClose)
									}
								>
									{disableSecondaryButton && (
										<ClayLoadingIndicator
											className="c-mr-2 m-0"
											small
										/>
									)}

									{` ${secondaryButtonText}`}
								</ClayButton>

								<ClayButton
									displayType="info"
									onClick={onClose}
								>
									{Liferay.Language.get('wait')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

DownloadSpreadsheetModal.defaultProps = {
	disableSecondaryButton: false,
	secondaryButtonClickCallback: () => {},
	secondaryButtonText: '',
	setVisibilityCallback: () => {},
};

DownloadSpreadsheetModal.propTypes = {
	disableSecondaryButton: PropTypes.bool,
	secondaryButtonClickCallback: PropTypes.func,
	secondaryButtonText: PropTypes.string,
	setVisibilityCallback: PropTypes.func,
	show: PropTypes.bool.isRequired,
};

export default DownloadSpreadsheetModal;
