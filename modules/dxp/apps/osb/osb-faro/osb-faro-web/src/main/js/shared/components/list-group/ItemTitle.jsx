import React from 'react';

export default class ItemTitle extends React.Component {
	render() {
		const {children, className, ...otherProps} = this.props;

		return (
			<h4
				{...otherProps}
				className={`list-group-title${
					className ? ` ${className}` : ''
				}`}
			>
				{children}
			</h4>
		);
	}
}
