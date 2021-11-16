import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const CheckButton = ({checked, expanded, hasError = false}) => (
	<div className="panel-right">
		<div
			className={classNames('panel-right-icon', {
				'step-checked': checked && !hasError && !expanded,
			})}
		>
			<ClayIcon symbol="check" />
		</div>
	</div>
);

export default CheckButton;
