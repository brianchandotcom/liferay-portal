/* eslint-disable no-console */
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import ViewFilesPanel from './ViewFilesPanel';

const Panel = ({
	children,
	defaultExpanded = false,
	sections,
	stepChecked,
	title = '',
}) => {
	const [showContentPanel, setShowContentPanel] = useState(defaultExpanded);

	useEffect(() => {
		setShowContentPanel(false);
		if (!stepChecked && defaultExpanded) {
			setShowContentPanel(true);
		}
	}, [stepChecked, defaultExpanded]);

	useEffect(() => {
		console.log('showContentPanel', showContentPanel);
	}, [showContentPanel]);

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
					{stepChecked && (
						<div className="change-link">
							<a onClick={() => setShowContentPanel(true)}>
								change
							</a>
						</div>
					)}

					<div
						className={classNames('panel-right-icon', {
							stepChecked,
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
