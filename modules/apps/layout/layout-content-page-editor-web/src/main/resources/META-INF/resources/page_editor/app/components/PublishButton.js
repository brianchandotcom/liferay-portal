/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from '../config/index';
import useCheckFormsValidity from '../utils/useCheckFormsValidity';
import {FormValidationModal} from './FormValidationModal';

export default function PublishButton({canPublish, formRef, label, onPublish}) {
	const checkFormsValidity = useCheckFormsValidity();

	const [openFormValidationModal, setOpenFormValidationModal] = useState(
		false
	);

	const submitURL =
		config.singleSegmentsExperienceMode &&
		config.saveVariantSegmentsExperienceURL
			? config.saveVariantSegmentsExperienceURL
			: config.publishURL;

	return (
		<>
			<form action={submitURL} method="POST" ref={formRef}>
				<input
					name={`${config.portletNamespace}redirect`}
					type="hidden"
					value={config.redirectURL}
				/>

				<ClayButton
					aria-label={label}
					disabled={config.pending || !canPublish}
					displayType="primary"
					onClick={() => {
						checkFormsValidity().then((valid) => {
							if (valid) {
								onPublish();
							}
							else {
								setOpenFormValidationModal(true);
							}
						});
					}}
					size="sm"
				>
					{label}
				</ClayButton>
			</form>

			{openFormValidationModal && (
				<FormValidationModal
					onCloseModal={() => setOpenFormValidationModal(false)}
					onPublish={onPublish}
				/>
			)}
		</>
	);
}

PublishButton.propTypes = {
	canPublish: PropTypes.bool,
	formRef: PropTypes.object,
	label: PropTypes.string,
	onPublish: PropTypes.func,
};
