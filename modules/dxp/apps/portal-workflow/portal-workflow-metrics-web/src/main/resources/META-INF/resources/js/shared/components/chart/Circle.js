import React from 'react';

class Circle extends React.Component {
	static defaultProps = {
		size: 8
	};

	render() {
		const {children, color, size} = this.props;

		const style = {
			backgroundColor: color,
			height: `${size}px`,
			width: `${size}px`
		};

		return (
			<span className="circle" style={style}>
				{children}
			</span>
		);
	}
}

export default Circle;
