import getCN from 'classnames';
import React, {useEffect, useRef} from 'react';

interface ICheckboxProps extends React.HTMLAttributes<HTMLInputElement> {
	checked?: boolean;
	disabled?: boolean;
	displayInline?: boolean;
	indeterminate?: boolean;
	label?: React.ReactNode;
	name?: string;
}

const Checkbox: React.FC<ICheckboxProps> = ({
	checked,
	className,
	disabled,
	displayInline,
	indeterminate,
	label,
	name,
	onChange,
	...otherProps
}) => {
	const _checkboxRef = useRef<HTMLInputElement>();

	useEffect(() => {
		_checkboxRef.current.indeterminate = indeterminate;
	}, [indeterminate]);

	const classes = getCN('custom-control', 'custom-checkbox', className, {
		['custom-control-inline']: displayInline
	});

	const handleEventPropagation = event => {
		event.stopPropagation();
	};

	return (
		// We are disabling the following rules as we don't actually want
		// this elment to be explicitly interactable. It only serves to
		// stop the propagation of the event to prevent the row from being
		// select.

		/* eslint-disable jsx-a11y/no-noninteractive-element-interactions, jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions */
		<div className={classes} onClick={handleEventPropagation}>
			{/* eslint-enable jsx-a11y/no-noninteractive-element-interactions, jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions */}
			<label>
				<input
					{...otherProps}
					checked={checked}
					className='custom-control-input'
					disabled={disabled}
					name={name}
					onChange={onChange}
					ref={_checkboxRef}
					type='checkbox'
					value={String(!!checked)}
				/>

				<span className='custom-control-label'>
					{label && (
						<span className='custom-control-label-text'>
							{label}
						</span>
					)}
				</span>
			</label>
		</div>
	);
};

export default Checkbox;
