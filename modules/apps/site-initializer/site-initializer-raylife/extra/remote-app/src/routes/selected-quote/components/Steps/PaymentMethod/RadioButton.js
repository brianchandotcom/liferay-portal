import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';

const RadioButton = ({children, onSelected, selected = false, value}) => (
	<div
		className={classNames(
			'align-self-center align-items-center border d-flex pay-card px-3 py-2 rounded-sm user-select-auto',
			{
				'bg-brand-primary-lighten-5  border-primary rounded-sm': selected,
				'border-white': !selected,
			}
		)}
		onClick={() => {
			onSelected(value);
		}}
	>
		<ClayRadio
			checked={selected}
			id={`radio-${value}`}
			name="radio"
			type="radio"
			value={value}
		/>

		{children}
	</div>
);

export default RadioButton;
