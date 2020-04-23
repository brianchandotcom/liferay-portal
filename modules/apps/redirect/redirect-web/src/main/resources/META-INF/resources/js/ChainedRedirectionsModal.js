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
import {ClayCheckbox} from '@clayui/form';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const noop = () => {};

const ChainedRedirectionsModal = ({
	observer,
	onModalClose = noop,
	callback = noop,
}) => {
	const [updateReferences, setUpdateReferences] = useState(true);

	const handleSubmit = event => {
		event.preventDefault();
		callback(updateReferences);
	};

	return (
		<ClayModal
			className="portlet-redirect-modal"
			observer={observer}
			size="md"
			status="warning"
		>
			<ClayModal.Header>
				{Liferay.Language.get('redirect-chain')}
			</ClayModal.Header>

			<form onSubmit={handleSubmit}>
				<ClayModal.Body>
					<p className="portlet-redirect-modal-title">
						<strong>
							{Liferay.Language.get(
								'do-you-want-to-create-the-redirect-and-update-the-references'
							)}
						</strong>
					</p>
					<div className="portlet-redirect-modal-description">
						<p>
							{Liferay.Language.get(
								'there-are-other-redirects-pointing-to-the-source-url-of-this-redirect.-this-will-create-a-redirect-chain-so-users-will-be-redirected-multiple-times-before-reaching-the-destination'
							)}
						</p>
						<p>
							{Liferay.Language.get(
								'to-avoid-this-chain-update-the-references-of-the-other-redirects'
							)}
						</p>
					</div>
					<fieldset className="fieldset">
						<ClayCheckbox
							checked={updateReferences}
							label={Liferay.Language.get('update-references')}
							onChange={() => setUpdateReferences(val => !val)}
						/>
					</fieldset>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('create')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</form>
		</ClayModal>
	);
};

ChainedRedirectionsModal.propTypes = {
	callback: PropTypes.func,
	observer: PropTypes.object.isRequired,
	onModalClose: PropTypes.func,
};

export default ChainedRedirectionsModal;
