import getCN from 'classnames';
import Radio from './Radio';
import React from 'react';
import {noop} from 'lodash';

const isReactNodeArray = (
	value: React.ReactNode | React.ReactNode[]
): value is React.ReactNode[] =>
	(value as React.ReactNode[]).length !== undefined;

const Subsection: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className
}) => (
	<div className={getCN('radio-group-subsection-root', className)}>
		{children}
	</div>
);

interface IOptionProps
	extends React.ComponentProps<typeof Radio>,
		React.HTMLAttributes<HTMLInputElement> {
	onChange?: (value: any) => void;
	value: any;
}

const Option: React.FC<IOptionProps> = ({onChange, value, ...otherProps}) => (
	<Radio
		{...otherProps}
		onChange={() => {
			onChange && onChange(value);
		}}
	/>
);

interface IRadioGroupProps extends React.HTMLAttributes<HTMLElement> {
	checked: any;
	disabled?: boolean;
	inline?: boolean;
	name: string;
	onChange: (value: any) => void;
}

const RadioGroup: React.FC<IRadioGroupProps> = ({
	checked,
	children,
	className,
	disabled = false,
	inline = false,
	name,
	onChange = noop
}) => (
	<div className={getCN('radio-group-root', className, {disabled})}>
		{React.Children.map(children, (child: React.ReactElement) =>
			React.cloneElement(child, {
				checked: checked === child.props.value,
				disabled,
				displayInline:
					inline && isReactNodeArray(children) && children.length > 0,
				name,
				onChange
			})
		)}
	</div>
);

export default Object.assign(RadioGroup, {
	Option,
	Subsection
});
