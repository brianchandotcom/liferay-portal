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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState, useCallback, useRef} from 'react';

import DisplayPageModalForm from './DisplayPageModalForm.es';

const DisplayPageModal = props => {
	const {formSubmitURL, onClose} = props;

	const form = useRef();
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);
	const {observer} = useModal({onClose});

	const validateForm = useCallback(
		form => {
			const {elements} = form;
			const error = {};

			const errorMessage = Liferay.Language.get('this-field-is-required');

			const nameField = elements[`${props.namespace}name`];

			if (!nameField.value) {
				error.name = errorMessage;
			}

			const classNameIdField = elements[`${props.namespace}classNameId`];

			if (classNameIdField.selectedIndex === 0) {
				error.classNameId = errorMessage;
			}

			const classTypeIdField = elements[`${props.namespace}classTypeId`];

			if (classTypeIdField && classTypeIdField.selectedIndex === 0) {
				error.classTypeId = errorMessage;
			}

			return error;
		},
		[props.namespace]
	);

	const handleSubmit = useCallback(
		event => {
			event.preventDefault();

			const error = validateForm(form.current);

			if (Object.keys(error).length !== 0) {
				setError(error);
				return;
			}

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
					} else if (responseContent.redirectURL) {
						navigate(responseContent.redirectURL, {
							beforeScreenFlip: onClose
						});
					}
				})
				.catch(() =>
					setError({
						other: Liferay.Language.get(
							'an-unexpected-error-occurred-while-creating-the-display-page'
						)
					})
				);
		},
		[formSubmitURL, onClose, validateForm]
	);

	const visible = observer.mutation;

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>{props.title}</ClayModal.Header>
			<ClayModal.Body>
				{error && error.other && (
					<ClayAlert
						displayType="danger"
						onClose={() => {}}
						title={Liferay.Language.get('error')}
					>
						{error.other}
					</ClayAlert>
				)}
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
