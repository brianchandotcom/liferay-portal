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
	stepChecked,
	title = '',
}) => {
	const [showContentPanel, setShowContentPanel] = useState(defaultExpanded);

	useEffect(() => {
		setShowContentPanel(false);

		if ((!stepChecked && defaultExpanded) || hasError) {
			setShowContentPanel(true);
		}
	}, [stepChecked, defaultExpanded, hasError]);

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
							<a
								onClick={() =>
									setShowContentPanel(!showContentPanel)
								}
							>
								Change
							</a>
						</div>
					)}

					<div
						className={classNames('panel-right-icon', {
							stepChecked: stepChecked && !hasError,
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
