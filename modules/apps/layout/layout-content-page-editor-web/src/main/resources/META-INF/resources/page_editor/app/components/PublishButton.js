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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from '../config/index';
import {useHasStyleErrors} from '../contexts/StyleErrorsContext';
import {StyleErrorsModal} from './StyleErrorsModal';

export default function PublishButton({
	canPublish,
	formRef,
	handleSubmit,
	label,
}) {
	const hasStyleErrors = useHasStyleErrors();
	const [openStyleErrorsModal, setOpenStyleErrorsModal] = useState(false);

	return (
		<>
			<form action={config.publishURL} method="POST" ref={formRef}>
				<input
					name={`${config.portletNamespace}redirect`}
					type="hidden"
					value={config.redirectURL}
				/>

				<ClayButton
					aria-label={label}
					disabled={config.pending || !canPublish}
					displayType="primary"
					onClick={
						hasStyleErrors
							? () => setOpenStyleErrorsModal(true)
							: handleSubmit
					}
					small
					type={hasStyleErrors ? 'button' : 'submit'}
				>
					{label}
				</ClayButton>
			</form>

			{openStyleErrorsModal && hasStyleErrors && (
				<StyleErrorsModal
					onCloseModal={() => setOpenStyleErrorsModal(false)}
					onSubmit={handleSubmit}
				/>
			)}
		</>
	);
}

PublishButton.propTypes = {
	canPublish: PropTypes.bool,
	formRef: PropTypes.object,
	handleSubmit: PropTypes.func,
	label: PropTypes.string,
};
