import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import ViewFilesPanel from './ViewFilesPanel';

const Panel = ({
	changeable,
	children,
	defaultExpanded = false,
	hasError = false,
	sections,
	setDiscardChanges,
	stepChecked,
	title = '',
}) => {
	const [showContentPanel, setShowContentPanel] = useState(defaultExpanded);
	const [showDiscardChanges, setShowDiscardChanges] = useState(false);

	useEffect(() => {
		setShowContentPanel(false);

		if ((!stepChecked && defaultExpanded) || hasError) {
			setShowContentPanel(true);
		}
	}, [stepChecked, defaultExpanded, hasError]);

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
		<div className="panel-container">
			<div className="panel-header">
				<div className="panel-left">{title}</div>

				<div className="panel-middle">
					{!showContentPanel && stepChecked && (
						<ViewFilesPanel sections={sections} />
					)}
				</div>

				<div className="panel-right">
					{changeable && stepChecked && !hasError && (
						<div className="change-link">
							{!showContentPanel ? (
								<a
									onClick={() => {
										setShowContentPanel(true);
									}}
								>
									Change
								</a>
							) : (
								showDiscardChanges && (
									<a
										onClick={() => {
											setDiscardChanges();
										}}
									>
										Discard Changes
									</a>
								)
							)}
						</div>
					)}

					<div
						className={classNames('panel-right-icon', {
							stepChecked:
								stepChecked && !hasError && !showContentPanel,
						})}
					>
						<ClayIcon symbol="check" />
					</div>
				</div>
			</div>

			<div
				className={classNames('panel-content', {
					show: showContentPanel,
				})}
			>
				{children}
			</div>
		</div>
	);
};

export default Panel;
