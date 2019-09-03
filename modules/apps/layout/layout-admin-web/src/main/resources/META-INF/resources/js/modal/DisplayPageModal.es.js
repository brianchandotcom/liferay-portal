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

import ClayModal, {useModal} from '@clayui/modal';
import {fetch, navigate} from 'frontend-js-web';
import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useState, useCallback, useRef} from 'react';
import DisplayPageModalForm from './DisplayPageModalForm.es';

const DisplayPageModal = props => {
	const {formSubmitURL, onClose} = props;

	const form = useRef();
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);
	const {observer} = useModal({onClose});

	const handleSubmit = useCallback(
		event => {
			event.preventDefault();

			setLoading(true);

			fetch(formSubmitURL, {
				body: new FormData(form.current),
				method: 'POST'
			})
				.then(response => response.json())
				.then(responseContent => {
					if (responseContent.error) {
						setLoading(false);
						setError(responseContent.error);
					}

					if (responseContent.redirectURL) {
						navigate(responseContent.redirectURL, {
							beforeScreenFlip: onClose
						});
					}
				});
		},
		[form, formSubmitURL, onClose]
	);

	const visible = observer.mutation;

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>{props.title}</ClayModal.Header>
			<ClayModal.Body>
				{visible && (
					<DisplayPageModalForm
						displayPageName={props.displayPageName}
						error={error}
						mappingTypes={props.mappingTypes}
						namespace={props.namespace}
						onSubmit={handleSubmit}
						ref={form}
					/>
				)}
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							displayType="primary"
							onClick={handleSubmit}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

DisplayPageModal.propTypes = {
	displayPageName: PropTypes.string,
	formSubmitURL: PropTypes.string.isRequired,
	mappingTypes: PropTypes.array,
	namespace: PropTypes.string.isRequired,
	onClose: PropTypes.func.isRequired,
	title: PropTypes.func.isRequired
};

export {DisplayPageModal};
export default DisplayPageModal;
