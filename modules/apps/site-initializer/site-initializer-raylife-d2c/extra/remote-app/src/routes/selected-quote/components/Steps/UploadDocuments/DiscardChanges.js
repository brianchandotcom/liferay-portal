/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<div className="d-flex flex-row justify-content-end order-2 order-lg-2 order-md-3 order-xl-3 panel-right">
			<div className="change-link">
				{checked && !hasError && !expanded && (
					<span
						className="mr-4"
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
					<span
						className="mr-4"
						onClick={() => setShowDiscardFilesModal(true)}
					>
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
