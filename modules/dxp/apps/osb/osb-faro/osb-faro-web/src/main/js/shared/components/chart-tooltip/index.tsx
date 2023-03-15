import Circle from 'shared/components/Circle';
import React from 'react';
import TooltipTemplate from './TooltipTemplate';
import {Alignments, Column, Weights} from './types';

export interface IChartTooltipProps extends React.HTMLAttributes<HTMLElement> {
	header?: {
		className?: string;
		columns: Column[];
	}[];
	rows?: {
		className?: string;
		columns: Column[];
	}[];
}

const ChartTooltip: React.FC<IChartTooltipProps> = ({
	className,
	header,
	rows
}) => {
	const renderColumn = (
		columns: Column[],
		className?: string,
		index: number = 0
	) => (
		<TooltipTemplate.Row className={className} key={`rows-${index}`}>
			{columns.map(
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
				) => {
					const labelValue =
						typeof label === 'function' ? label() : label;

					return (
						<TooltipTemplate.Column
							align={align}
							className={className}
							colSpan={colspan}
							key={`column-${index}`}
							style={width && {minWidth: `${width}px`}}
							truncated={truncated}
							weight={weight}
						>
							{color && <Circle color={color} />}

							{labelValue}
						</TooltipTemplate.Column>
					);
				}
			)}
		</TooltipTemplate.Row>
	);

	return (
		<TooltipTemplate className={className}>
			{!!header && (
				<TooltipTemplate.Header>
					{header.map(({className, columns}, index) =>
						renderColumn(columns, className, index)
					)}
				</TooltipTemplate.Header>
			)}

			{!!rows && (
				<TooltipTemplate.Body>
					{rows.map(({className, columns}, index) =>
						renderColumn(columns, className, index)
					)}
				</TooltipTemplate.Body>
			)}
		</TooltipTemplate>
	);
};

export default ChartTooltip;
export {Alignments, Weights};
