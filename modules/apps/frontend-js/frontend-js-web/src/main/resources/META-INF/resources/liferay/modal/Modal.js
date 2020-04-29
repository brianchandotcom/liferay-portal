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
import ClayModal, {useModal} from '@clayui/modal';
import {render} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import './Modal.scss';

const openModal = (props) => {
	if (
		props &&
		props.url &&
		props.bodyHTML &&
		process.env.NODE_ENV === 'development'
	) {
		console.warn(
			'url and bodyHTML props are both set. bodyHTML will be ignored. Please use one or another.'
		);
	}

	// Mount in detached node; Clay will take care of appending to `document.body`.
	// See: https://github.com/liferay/clay/blob/master/packages/clay-shared/src/Portal.tsx

	render(Modal, props, document.createElement('div'));
};

const Modal = ({bodyHTML, buttons, id, onClose, size, title, url}) => {
	const [visible, setVisible] = useState(true);

	const {observer} = useModal({
		onClose: () => {
			processClose();
		},
	});

	const getIframeUrl = () => {
		if (!url) {
			return null;
		}

		const iframeURL = new URL(url);

		const namespace = iframeURL.searchParams.get('p_p_id');

		iframeURL.searchParams.set(
			`_${namespace}_bodyCssClass`,
			'dialog-iframe-popup'
		);

		return iframeURL.toString();
	};

	const onButtonClick = ({formId, type}) => {
		if (type === 'cancel') {
			processClose();
		}
		else if (url && type === 'submit') {
			const iframe = document.querySelector('.liferay-modal iframe');

			if (iframe) {
				const iframeDocument = iframe.contentWindow.document;

				const forms = iframeDocument.querySelectorAll('form');

				if (
					forms.length !== 1 &&
					process.env.NODE_ENV === 'development'
				) {
					console.warn('There should be one form within a modal.');
				}

				if (formId) {
					const form = iframeDocument.getElementById(formId);

					if (form) {
						form.submit();
					}
				}
				else if (forms.length >= 1) {
					forms[0].submit();
				}
			}
		}
	};

	const processClose = () => {
		setVisible(false);

		if (onClose) {
			onClose();
		}
	};

	const BodyHTML = () => {
		const bodyRef = useRef();

		useEffect(() => {
			const fragment = document
				.createRange()
				.createContextualFragment(bodyHTML);

			bodyRef.current.innerHTML = '';

			bodyRef.current.appendChild(fragment);
		}, []);

		return <div ref={bodyRef}></div>;
	};

	return (
		<>
			{visible && (
				<ClayModal
					className="liferay-modal"
					id={id}
					observer={observer}
					size={url && !size ? 'full-screen' : size}
				>
					<ClayModal.Header>{title}</ClayModal.Header>
					<ClayModal.Body url={getIframeUrl()}>
						{bodyHTML && <BodyHTML />}
					</ClayModal.Body>
					{buttons && (
						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									{buttons.map(
										(
											{
												displayType,
												formId,
												id,
												label,
												type,
											},
											index
										) => (
											<ClayButton
												displayType={displayType}
												id={id}
												key={index}
												onClick={() => {
													onButtonClick({
														formId,
														type,
													});
												}}
												type={
													type === 'cancel'
														? 'button'
														: type
												}
											>
												{label}
											</ClayButton>
										)
									)}
								</ClayButton.Group>
							}
						/>
					)}
				</ClayModal>
			)}
		</>
	);
};

Modal.propTypes = {
	bodyHTML: PropTypes.string,
	buttons: PropTypes.arrayOf(
		PropTypes.shape({
			displayType: PropTypes.oneOf([
				'link',
				'primary',
				'secondary',
				'unstyled',
			]),
			formId: PropTypes.string,
			id: PropTypes.string,
			label: PropTypes.string,
			type: PropTypes.oneOf(['cancel', 'submit']),
		})
	),
	id: PropTypes.string,
	onClose: PropTypes.func,
	size: PropTypes.oneOf(['full-screen', 'lg', 'sm']),
	title: PropTypes.string,
	url: PropTypes.string,
};

export {Modal, openModal};
