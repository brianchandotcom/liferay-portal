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

import {ClayButtonWithIcon} from '@clayui/button';
import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import FriendlyURLHistoryModal from './FriendlyURLHistoryModal';

export default function FriendlyURLHistory({
	disabled: initialDisabled = false,
	elementId,
	localizable = false,
	...restProps
}) {
	const [showModal, setShowModal] = useState(false);
	const [selectedLanguageId, setSelectedLanguageId] = useState();
	const [disabled, setDisabled] = useState(initialDisabled);

	const inputRef = useRef(document.getElementById(elementId));

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const input = inputRef.current;

		if (input) {
			const mutationObserver = new MutationObserver((mutations) => {
				mutations.forEach((mutation) => {
					if (
						mutation.type === 'attributes' &&
						mutation.attributeName === 'disabled'
					) {
						setDisabled(mutation.target.disabled);
					}
				});
			});

			mutationObserver.observe(input, {
				attributeFilter: ['disabled'],
				attributes: true,
			});

			return () => {
				mutationObserver.disconnect(input);
			};
		}
	}, []);

	return (
		<>
			<ClayButtonWithIcon
				borderless
				className={classNames('btn-url-history', {
					['btn-url-history-localizable']: localizable,
				})}
				disabled={disabled}
				displayType="secondary"
				onClick={() => {
					if (localizable) {
						setSelectedLanguageId(
							Liferay.component(elementId).getSelectedLanguageId()
						);
					}
					setShowModal(true);
				}}
				outline
				small
				symbol="restore"
			/>
			{showModal && (
				<FriendlyURLHistoryModal
					{...restProps}
					elementId={elementId}
					initialLanguageId={selectedLanguageId}
					localizable={localizable}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

FriendlyURLHistory.propTypes = {
	disabled: PropTypes.bool,
	elementId: PropTypes.string.isRequired,
	localizable: PropTypes.bool,
};
