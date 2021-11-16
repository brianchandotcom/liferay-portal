import classNames from 'classnames';

const RadioButton = ({children, onSelected, selected = false, value}) => (
	<div
		className={classNames('radio-button-container', {
			selected,
		})}
		onClick={() => {
			onSelected(value);
		}}
	>
		<input
			checked={selected}
			className="radio"
			id={`radio-${value}`}
			name="radio"
			type="radio"
			value={value}
		/>
		{children}
	</div>
);

export default RadioButton;
