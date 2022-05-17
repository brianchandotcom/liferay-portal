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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import React, {useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';

const VIEW_TYPES = {
	columns: 1,
	fullscreen: 2,
	rows: 3,
};

const HTMLEditorModal = ({
	initialContent = '',
	onClose: onCloseCallback,
	onSave,
}) => {
	const [content, setContent] = useState(initialContent);
	const [viewType, setViewType] = useState(VIEW_TYPES.columns);
	const [visible, setVisible] = useState(true);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			onCloseCallback();
		},
	});

	return (
		visible && (
			<ClayModal observer={observer} size="full-screen">
				<ClayModal.Header>
					{Liferay.Language.get('edit-content')}
				</ClayModal.Header>

				<ClayModal.Body className="pb-0">
					<div className="d-flex justify-content-end pr-2 w-100">
						<ClayButton.Group>
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setViewType(VIEW_TYPES.columns)}
								small
								symbol="columns"
								title={Liferay.Language.get(
									'display-vertically'
								)}
							/>

							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setViewType(VIEW_TYPES.rows)}
								small
								symbol="cards"
								title={Liferay.Language.get(
									'display-horizontally'
								)}
							/>

							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() =>
									setViewType(VIEW_TYPES.fullscreen)
								}
								small
								symbol="expand"
								title={Liferay.Language.get('full-screen')}
							/>
						</ClayButton.Group>
					</div>

					<div
						className={classNames(
							'd-flex page-editor__html-editor-modal__editor-container',
							{
								'flex-column': viewType === VIEW_TYPES.rows,
							}
						)}
					>
						<div
							className={classNames({
								'h-50 w-100': viewType === VIEW_TYPES.rows,
								'h-100 w-50': viewType === VIEW_TYPES.columns,
								'h-100 w-100':
									viewType === VIEW_TYPES.fullscreen,
							})}
						>
							<CodeMirrorEditor
								initialContent={initialContent}
								onChange={(nextContent) =>
									setContent(nextContent)
								}
							/>
						</div>

						{viewType !== VIEW_TYPES.fullscreen && (
							<div
								className={classNames({
									'page-editor__html-editor-modal__preview-columns h-100 px-3 w-50':
										viewType === VIEW_TYPES.columns,
									'page-editor__html-editor-modal__preview-rows h-50 py-2 w-100':
										viewType === VIEW_TYPES.rows,
								})}
							>
								<div
									dangerouslySetInnerHTML={{__html: content}}
									onClick={(event) => event.preventDefault()}
								/>
							</div>
						)}
					</div>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => {
									onClose();
								}}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								onClick={() => {
									onSave(content);
									onClose();
								}}
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		)
	);
};

export default HTMLEditorModal;
