import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const CheckButton = ({checked, expanded, hasError = false}) => {
	const isChecked = checked && !hasError && !expanded;

	return (
		<div className="panel-right">
			<div
				className={classNames(
					'align-items-center d-flex icon justify-content-center rounded-circle',
					{
						'bg-neutral-3': !isChecked,
						'bg-success': isChecked,
					}
				)}
			>
				<ClayIcon className="text-neutral-0" symbol="check" />
			</div>
		</div>
	);
};

export default CheckButton;
