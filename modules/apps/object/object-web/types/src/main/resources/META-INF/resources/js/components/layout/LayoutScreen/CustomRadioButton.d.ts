import React from 'react';
interface ICustomRadioButtonProps extends React.HTMLAttributes<HTMLElement> {
	value: string;
	name: string;
	checked?: boolean;
	title: string;
	description: string;
	onChangeValue: (value: string) => void;
}
declare const CustomRadioButton: React.FC<ICustomRadioButtonProps>;
export default CustomRadioButton;
