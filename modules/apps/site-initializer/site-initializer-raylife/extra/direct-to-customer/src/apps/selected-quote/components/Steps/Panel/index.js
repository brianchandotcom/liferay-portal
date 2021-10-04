import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useState} from 'react';
import Styles from '~/apps/selected-quote/styles/index.scss';

const Panel = ({children, title = ''}) => {
	const [showContentPanel, setShowContentPanel] = useState(false);

	return (
		<>
			<style>{Styles}</style>
			<div className="panel-container">
				<div
					className="panel-header"
					onClick={() => setShowContentPanel(!showContentPanel)}
				>
					<div className="panel-title">{title}</div>
					<div className="panel-title-icon">
						<ClayIcon symbol="check" />
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
		</>
	);
};

export default Panel;
