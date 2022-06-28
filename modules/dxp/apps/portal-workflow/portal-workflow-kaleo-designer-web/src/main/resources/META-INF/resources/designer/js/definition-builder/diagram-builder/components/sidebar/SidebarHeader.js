/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import {getModalInfo} from './utils';

export default function SidebarHeader({
	backButtonFunction = () => {},
	contentName,
	deleteButtonFunction = () => {},
	showBackButton,
	showDeleteButton,
	title,
}) {
	const {setElements} = useContext(DefinitionBuilderContext);
	const {selectedItem} = useContext(DiagramBuilderContext);
	const [
		showDeleteConfirmationModal,
		setShowDeleteConfirmationModal,
	] = useState(false);
	const [modalInfo, setModalInfo] = useState({});

	const {observer} = useModal({
		onClose: () => {
			setShowDeleteConfirmationModal(false);
		},
	});

	const deleteItem = () => {
		setElements((elements) =>
			elements.filter(
				(element) =>
					element.id !== selectedItem.id &&
					element.source !== selectedItem.id &&
					element.target !== selectedItem.id
			)
		);
		setShowDeleteConfirmationModal(false);
		backButtonFunction();
	};

	const deleteContent = () => {
		deleteButtonFunction();
		setShowDeleteConfirmationModal(false);
		backButtonFunction();
	};

	const handleKeyDown = (event) => {
		if (
			(event.key === 'Backspace' || event.key === 'Delete') &&
			contentName !== 'assignments' &&
			!document.querySelectorAll('.form-control:focus').length &&
			!document.querySelectorAll('.CodeMirror-focused').length
		) {
			setShowDeleteConfirmationModal(true);
		}
	};

	useEffect(() => {
		if (contentName) {
			setModalInfo(getModalInfo(contentName));

			window.addEventListener('keydown', handleKeyDown);

			return () => {
				window.removeEventListener('keydown', handleKeyDown);
			};
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [contentName]);

	return (
		<div className="sidebar-header">
			{showBackButton && (
				<ClayButtonWithIcon
					className="text-secondary"
					displayType="unstyled"
					onClick={backButtonFunction}
					symbol="angle-left"
				/>
			)}

			<div className="spaced-items">
				<span className="title">{title}</span>

				{showDeleteButton && (
					<ClayButtonWithIcon
						className="text-secondary trash-button"
						disabled={contentName === 'assignments'}
						displayType="unstyled"
						onClick={() => setShowDeleteConfirmationModal(true)}
						symbol="trash"
					/>
				)}
			</div>

			{showDeleteConfirmationModal && (
				<ClayModal center observer={observer} size="sm">
					<ClayModal.Header className="text-secondary">
						{modalInfo.title}
					</ClayModal.Header>

					<ClayModal.Body className="text-secondary">
						{modalInfo.message}
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<>
								<ClayButton
									className="mr-3"
									displayType="secondary"
									onClick={() =>
										setShowDeleteConfirmationModal(false)
									}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="danger"
									onClick={
										deleteButtonFunction
											? deleteContent
											: deleteItem
									}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>
							</>
						}
					/>
				</ClayModal>
			)}
		</div>
	);
}

SidebarHeader.propTypes = {
	backButtonFunction: PropTypes.func,
	deleteButtonFunction: PropTypes.func,
	showBackButton: PropTypes.bool,
	showDeleteButton: PropTypes.bool,
	title: PropTypes.string.isRequired,
};
