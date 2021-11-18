import classNames from 'classnames';

const Skeleton = ({align, count = 1, height, width, ...props}) => {
	return (
		<div {...props}>
			{[...new Array(count)].map((_a, index) => (
				<div
					className={classNames(
						'rounded skeleton',
						{
							'ml-auto': align === 'right',
							'mr-auto': align === 'left',
							'mx-auto': align === 'center',
						},
						{
							'mt-3': index > 0,
						}
					)}
					key={index}
					style={{
						height: `${height}px`,
						width: `${width - index * 100}px`,
					}}
				/>
			))}
		</div>
	);
};

Skeleton.Rounded = ({height, width}) => {
	return (
		<div
			className="rounded-sm skeleton"
			style={{height: `${height}px`, width: `${width}px`}}
		/>
	);
};

Skeleton.Square = ({height, width}) => (
	<div
		className="skeleton"
		style={{height: `${height}px`, width: `${width}px`}}
	/>
);

export default Skeleton;
