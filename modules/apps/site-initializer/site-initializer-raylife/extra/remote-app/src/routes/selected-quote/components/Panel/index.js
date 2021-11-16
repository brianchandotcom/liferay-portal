import classNames from 'classnames';
import {useContext} from 'react';
import {SelectedQuoteContext} from '~/routes/selected-quote/context/SelectedQuoteContextProvider';

const Panel = ({
	children,
	id,
	PanelMiddle = () => null,
	PanelRight = () => null,
	title = '',
}) => {
	const [{panel}] = useContext(SelectedQuoteContext);
	const {checked, expanded = false} = panel[id];

	return (
		<div className="panel-container">
			<div className="panel-header">
				<div className="panel-left">{title}</div>

				<PanelMiddle checked={checked} expanded={expanded} />

				<PanelRight checked={checked} expanded={expanded} />
			</div>

			<div
				className={classNames('panel-content', {
					show: expanded,
				})}
			>
				{expanded && children}
			</div>
		</div>
	);
};

export default Panel;
