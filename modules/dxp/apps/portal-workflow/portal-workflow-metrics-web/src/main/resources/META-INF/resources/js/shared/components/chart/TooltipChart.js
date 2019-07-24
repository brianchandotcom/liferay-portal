import Circle from './Circle';
import getCN from 'classnames';
import React from 'react';

const CLASSNAME = 'workflow-tooltip-chart';

const Body = ({children, className = ''}) => (
	<tbody className={`${CLASSNAME}-body ${className}`}>{children}</tbody>
);

class Column extends React.Component {
	static defaultProps = {
		align: 'left',
		truncated: false,
		weight: 'normal'
	};

	render() {
		const {
			align,
			children,
			className,
			truncated,
			weight,
			...otherProps
		} = this.props;
		const classes = getCN(`${CLASSNAME}-column`, className, {
			[`text-${align}`]: align,
			[`font-weight-${weight}`]: weight
		});

		return (
			<td {...otherProps}>
				<div className={`${CLASSNAME}-content ${classes}`}>
					{truncated ? (
						<div className={`${CLASSNAME}-truncated`}>
							{children}
						</div>
					) : (
						children
					)}
				</div>
			</td>
		);
	}
}

const Header = ({children, className = ''}) => (
	<thead className={`${CLASSNAME}-header ${className}`}>{children}</thead>
);

const Row = ({children, className = ''}) => (
	<tr className={`${CLASSNAME}-row ${className}`}>{children}</tr>
);

class TooltipTmpl extends React.Component {
	render() {
		const {children, className} = this.props;
		return (
			<table className={getCN(CLASSNAME, className)}>{children}</table>
		);
	}
}

TooltipTmpl.Body = Body;
TooltipTmpl.Column = Column;
TooltipTmpl.Header = Header;
TooltipTmpl.Row = Row;

class TooltipChart extends React.Component {
	renderLabel(label) {
		if (typeof label === 'function') {
			return label();
		}

		return label;
	}

	renderColumn(column, className = '', index = 0) {
		return (
			<TooltipTmpl.Row className={className} key={`rows-${index}`}>
				{column.map(
					(
						{
							align,
							className,
							color,
							colspan,
							label,
							truncated,
							weight,
							width
						},
						index
					) => (
						<TooltipTmpl.Column
							align={align}
							className={className}
							colSpan={colspan}
							key={`column-${index}`}
							style={width && {minWidth: `${width}px`}}
							truncated={truncated}
							weight={weight}
						>
							{color && <Circle color={color} />}{' '}
							{this.renderLabel(label)}
						</TooltipTmpl.Column>
					)
				)}
			</TooltipTmpl.Row>
		);
	}

	renderHeader(header) {
		return (
			<TooltipTmpl.Header>{this.renderColumn(header)}</TooltipTmpl.Header>
		);
	}

	renderRows(rows) {
		return (
			<TooltipTmpl.Body>
				{rows.map(({className, columns}, index) =>
					this.renderColumn(columns, className, index)
				)}
			</TooltipTmpl.Body>
		);
	}

	render() {
		const {className, header, rows} = this.props;

		return (
			<TooltipTmpl className={className}>
				{!!header && this.renderHeader(header)}
				{!!rows && this.renderRows(rows)}
			</TooltipTmpl>
		);
	}
}

export {TooltipChart, TooltipTmpl};
export default TooltipChart;
