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

import React, {useContext, useEffect, useState} from 'react';
import {
	ACTIONS,
	SelectedQuoteContext,
} from '../../../context/SelectedQuoteContextProvider';
import DiscardSelectedFiles from '../../DiscardChangesModal';
import CheckButton from '../../Panel/CheckButton';

const DiscardChanges = ({checked, expanded, hasError}) => {
	const [showDiscardChanges, setShowDiscardChanges] = useState(false);
	const [{sections}, dispatch] = useContext(SelectedQuoteContext);
	const [showDiscardFilesModal, setShowDiscardFilesModal] = useState(false);

	const onDiscardChanges = () => {
		dispatch({
			payload: sections?.map((section) => {
				section.files = section.files.filter((file) => file.documentId);

				return section;
			}),
			type: ACTIONS.SET_SECTIONS,
		});
	};

	useEffect(() => {
		let filesChanged = false;

		sections?.forEach((section) => {
			const noFileDocumentsId = section.files?.some(
				(file) => !file.documentId
			);

			if (noFileDocumentsId) {
				filesChanged = true;
			}
		});

		setShowDiscardChanges(filesChanged);
	}, [sections]);

	return (
		<div className="panel-right">
			<div className="change-link">
				{checked && !hasError && !expanded && (
					<span
						onClick={() => {
							dispatch({
								payload: {
									panelKey: 'uploadDocuments',
									value: true,
								},
								type: ACTIONS.SET_EXPANDED,
							});

							dispatch({
								payload: {
									panelKey: 'uploadDocuments',
									value: false,
								},
								type: ACTIONS.SET_STEP_CHECKED,
							});
							dispatch({
								payload: {
									panelKey: 'selectPaymentMethod',
									value: false,
								},
								type: ACTIONS.SET_EXPANDED,
							});
						}}
					>
						Change
					</span>
				)}

				{!checked && expanded && showDiscardChanges && (
					<span onClick={() => setShowDiscardFilesModal(true)}>
						Discard Changes
					</span>
				)}
			</div>

			<CheckButton
				checked={checked}
				expanded={expanded}
				hasError={hasError}
			/>

			<DiscardSelectedFiles
				onClose={() => setShowDiscardFilesModal(false)}
				onDiscardChanges={onDiscardChanges}
				show={showDiscardFilesModal}
			/>
		</div>
	);
};

export default DiscardChanges;
