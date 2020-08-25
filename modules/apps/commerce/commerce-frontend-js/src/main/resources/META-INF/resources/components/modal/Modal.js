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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	CLOSE_MODAL,
	IS_LOADING_MODAL,
	OPEN_MODAL,
} from '../../utilities/eventsDefinitions';
import {isPageInIframe} from '../../utilities/iframes';
import {liferayNavigate} from '../../utilities/index';
import {INITIAL_MODAL_SIZE} from '../../utilities/modals/constants';
import {resolveModalHeight} from '../../utilities/modals/index';

function Modal(props) {
	const [visible, setVisible] = useState(false);
	const [loading, setLoading] = useState(false);
	const [onClose, setOnClose] = useState(null);
	const [title, setTitle] = useState(props.title);
	const [url, setUrl] = useState(props.url);
	const [size, setSize] = useState(INITIAL_MODAL_SIZE);

	const {observer, onClose: close} = useModal({
		onClose: (notification) => {
			if (onClose) {
				onClose(notification);
			}

			setLoading(false);
			setVisible(false);
		},
	});

	useEffect(() => {
		function handleOpenEvent(data) {
			if (props.id !== data.id || visible || isPageInIframe()) {
				return;
			}

			setLoading(true);
			setVisible(true);

			if (data.url) {
				setUrl(data.url);
			}

			if (data.onClose) {
				setOnClose(() => data.onClose);
			}

			if (data.title) {
				setTitle(data.title);
			}

			if (!data.size) {
				setSize(INITIAL_MODAL_SIZE);
			}
		}

		function handleCloseModal({
			redirectURL = '',
			successNotification = {},
		}) {
			if (!visible) {
				return;
			}

			if (redirectURL) {
				liferayNavigate(redirectURL);
			}
			else {
				close(successNotification);
			}
		}

		function handleSetLoading(data) {
			const {isLoading} = data;

			setLoading(isLoading || false);
		}

		function cleanUpListeners() {
			Liferay.detach(OPEN_MODAL, handleOpenEvent);
			Liferay.detach(CLOSE_MODAL, handleCloseModal);
			Liferay.detach(IS_LOADING_MODAL, handleSetLoading);
			Liferay.detach('destroyPortlet', cleanUpListeners);
		}

		if (Liferay.on) {
			Liferay.on(OPEN_MODAL, handleOpenEvent);
			Liferay.on(CLOSE_MODAL, handleCloseModal);
			Liferay.on(IS_LOADING_MODAL, handleSetLoading);
			Liferay.on('destroyPortlet', cleanUpListeners);
		}

		return () => cleanUpListeners();
	}, [close, props.id, visible]);

	useEffect(() => {
		setOnClose(() => props.onClose);
	}, [props.onClose]);

	return (
		<>
			{visible && (
				<ClayModal
					className="commerce-modal"
					observer={observer}
					size={size}
					spritemap={props.spritemap}
					status={props.status}
				>
					{title && <ClayModal.Header>{title}</ClayModal.Header>}
					<div
						className="modal-body modal-body-iframe"
						style={{
							height: resolveModalHeight(size),
							maxHeight: '100%',
						}}
					>
						<iframe src={url} title={title} />
						{loading && (
							<div className="loader-container">
								<ClayLoadingIndicator />
							</div>
						)}
					</div>
				</ClayModal>
			)}
		</>
	);
}

Modal.propTypes = {
	closeOnSubmit: PropTypes.bool,
	id: PropTypes.string.isRequired,
	onClose: PropTypes.func,
	portletId: PropTypes.string,
	size: PropTypes.string,
	spritemap: PropTypes.string,
	status: PropTypes.string,
	title: PropTypes.string,
	url: PropTypes.string,
};

export default Modal;
