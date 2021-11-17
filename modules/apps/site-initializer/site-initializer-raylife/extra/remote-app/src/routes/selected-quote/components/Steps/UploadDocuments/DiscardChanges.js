import React, {useContext, useEffect, useState} from 'react';
import {
	ACTIONS,
	SelectedQuoteContext,
} from '../../../context/SelectedQuoteContextProvider';
import CheckButton from '../../Panel/CheckButton';

const DiscardChanges = ({checked, expanded, hasError}) => {
	const [showDiscardChanges, setShowDiscardChanges] = useState(false);
	const [{sections}, dispatch] = useContext(SelectedQuoteContext);

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
						}}
					>
						Change
					</span>
				)}

				{!checked && expanded && showDiscardChanges && (
					<span onClick={onDiscardChanges}>Discard Changes</span>
				)}
			</div>

			<CheckButton
				checked={checked}
				expanded={expanded}
				hasError={hasError}
			/>
		</div>
	);
};

export default DiscardChanges;
