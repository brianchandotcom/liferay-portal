import classNames from 'classnames';
import {useContext} from 'react';
import {SelectedQuoteContext} from '../../context/SelectedQuoteContextProvider';

const Panel = ({
	children,
	id,
	Middle = () => null,
	Right = () => null,
	title = '',
	hasError = false,
}) => {
	const [{panel}] = useContext(SelectedQuoteContext);
	const {checked, expanded = false} = panel[id];

	const show = expanded || hasError;

	return (
		<div className="panel-container">
			<div className="panel-header">
				<div className="panel-left">{title}</div>

				<Middle checked={checked} expanded={expanded} />

				<Right checked={checked} expanded={expanded} />
			</div>

			<div
				className={classNames('panel-content', {
					show,
				})}
			>
				{show && children}
			</div>
		</div>
	);
};

export default Panel;
